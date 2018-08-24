package com.anchorcms.cms.lucene;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.anchorcms.cms.controller.admin.LuceneContent;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.constants.Constants;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.web.mvc.RealPathResolver;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.model.cloudcenter.SIcloudPolicy;
import com.anchorcms.icloud.model.software.SSoftware;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SDemand;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class LuceneContentSvcImpl implements LuceneContentSvc {
	@Transactional(readOnly = true)
	public Integer createIndex(Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer max)
			throws IOException, ParseException {
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		return createIndex(siteId, channelId, startDate, endDate, startId, max,
				dir);
	}

	@Transactional(readOnly = true)
	public Integer createIndex(Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer max,
			Directory dir) throws IOException, ParseException {
		boolean exist = IndexReader.indexExists(dir);
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(
				Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
		try {
			if (exist) {
				LuceneContent.delete(siteId, channelId, startDate, endDate,
						writer);
			}
			Integer lastId = luceneContentDao.index(writer, siteId, channelId,
					startDate, endDate, startId, max);
			writer.optimize();
			return lastId;
		} catch (org.apache.lucene.queryParser.ParseException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
		return null;
	}

	@Transactional(readOnly = true)
	public void createIndex(Content content) throws IOException {
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		createIndex(content, dir);
	}

	@Transactional(readOnly = true)
	public void createIndex(Content content, Directory dir) throws IOException {
		boolean exist = IndexReader.indexExists(dir);
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(
				Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
		try {
			writer.addDocument(LuceneContent.createDocument(content));
		} finally {
			writer.close();
		}
	}

	@Transactional(readOnly = true)
	public void deleteIndex(Integer contentId) throws IOException,
			ParseException {
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		deleteIndex(contentId, dir);
	}

	@Transactional(readOnly = true)
	public void deleteIndex(Integer contentId, Directory dir)
			throws IOException, ParseException {
		boolean exist = IndexReader.indexExists(dir);
		if (exist) {
			IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(
					Version.LUCENE_30), false,
					IndexWriter.MaxFieldLength.LIMITED);
			try {
				LuceneContent.delete(contentId, writer);
			} catch (org.apache.lucene.queryParser.ParseException e) {
				e.printStackTrace();
			} finally {
				writer.close();
			}
		}
	}

	public void updateIndex(Content content) throws IOException, ParseException {
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		updateIndex(content, dir);
	}

	public void updateIndex(Content content, Directory dir) throws IOException,
			ParseException {
		boolean exist = IndexReader.indexExists(dir);
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(
				Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
		try {
			if (exist) {
				LuceneContent.delete(content.getContentId(), writer);
			}
			writer.addDocument(LuceneContent.createDocument(content));
		} catch (org.apache.lucene.queryParser.ParseException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	@Transactional(readOnly = true)
	public Pagination searchPage(String path, String queryString, String category, String workplace,
								 Integer siteId, Integer channelId, Date startDate, Date endDate,
								 int pageNo, int pageSize) throws CorruptIndexException,
			IOException, ParseException {
		Directory dir = new SimpleFSDirectory(new File(path));
		return searchPage(dir, queryString,category,workplace, siteId, channelId, startDate,
				endDate, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public Pagination searchPage(Directory dir, String queryString,String category,String workplace,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int pageNo, int pageSize) throws CorruptIndexException,
			IOException, ParseException {
		Searcher searcher = new IndexSearcher(dir);
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			Query query = LuceneContent.createQuery(queryString,category,workplace, siteId,
					channelId, startDate, endDate, analyzer);
			TopDocs docs = searcher.search(query, pageNo * pageSize);
			Pagination p = LuceneContent.getResultPage(searcher, docs, pageNo,
					pageSize);
			List<?> ids = p.getList();
			List<Content> contents = new ArrayList<Content>(ids.size());
			for (Object id : ids) {
				Content content = contentMng.findById((Integer) id);
				if (content.getChannel().getChannelId() == 103) {
					SAbility ability = luceneContentDao.findAbilityByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/xtzzNlczs/xtzz_ability_preview.jspx?id=")
							.append(ability.getAbilityId());
					content.getContentExt().setLink(url.toString());
				}
				if (content.getChannel().getChannelId() == 104) {
					SDemand demand = luceneContentDao.findDemandByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/xtzzNlczs/xtzz_synergy_demand_preview.jspx?id=")
							.append(demand.getDemandId());
					content.getContentExt().setLink(url.toString());
				}
				if (content.getChannel().getChannelId() == 105) {
					SCompany company = luceneContentDao.findCompanyByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/xtzzNlczs/xtzz_synergy_company_preview.jspx?id=")
							.append(company.getCompanyId());
					content.getContentExt().setLink(url.toString());
				}
				if (content.getChannel().getChannelId() == 112) {
					SIcloudPolicy policy = luceneContentDao.findPolicyByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/yzyjyzxYjszc/cloudcenter_policy_info.jspx?policyId=")
							.append(policy.getPolicyId());
					content.getContentExt().setLink(url.toString());
				}
				if (content.getChannel().getChannelId() == 114) {
					SIcloudManage resource = luceneContentDao.findResourceByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/yzyjyzxYzyck/cloudcenter_resource_manager_info.jspx?managerId=")
							.append(resource.getManagerId());
					content.getContentExt().setLink(url.toString());
				}
				if (content.getChannel().getChannelId() == 115) {
					SIcloudDemand icloudDemand = luceneContentDao.findIcloudDemandByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/yzyjyzxYxq/cloudcenter_demand_detail.jspx?demandId=")
							.append(icloudDemand.getDemandId());
					content.getContentExt().setLink(url.toString());
				}
				//软件应用
				if (content.getChannel().getChannelId() == 119) {
					SSoftware software = luceneContentDao.findSoftwareDemandByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/rjyyRjjsym/software_info.jspx?id=")
							.append(software.getSoftwareId());
					content.getContentExt().setLink(url.toString());
				}
				//备品备件
				if (content.getChannel().getChannelId() == 106) {
					SSpare sSpare = luceneContentDao.findSSpareByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/spare/detail.jspx?id=")
							.append(sSpare.getSparepartsId());
					content.getContentExt().setLink(url.toString());
				}
				//备品备件求购
				if (content.getChannel().getChannelId() == 107) {
					SSpareDemand sSpareDemand = luceneContentDao.findSSpareDemandByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/spareDemand.jspx?flag=1&id=")
							.append(sSpareDemand.getDemandId());
					content.getContentExt().setLink(url.toString());
				}
				//维修资源
				if (content.getChannel().getChannelId() == 108) {
					SRepairRes sRepairRes = luceneContentDao.findSRepairResByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/Repair_manager_preview.jspx?id=")
							.append(sRepairRes.getRepairId());
					content.getContentExt().setLink(url.toString());
				}
				//维修需求
				if (content.getChannel().getChannelId() == 109) {
					SRepairDemand sRepairDemand = luceneContentDao.findSRepairDemandByContentId((Integer) id);
					StringBuilder url = new StringBuilder();
					url.append("/repairDemand.jspx?flag=1&id=")
							.append(sRepairDemand.getRepairId());
					content.getContentExt().setLink(url.toString());
				}
				//问题求助
				if (content.getChannel().getChannelId() == 122) {
					StringBuilder url = new StringBuilder();
					url.append("/front/askhelp_comment.jspx?contentId=")
							.append(id);
					content.getContentExt().setLink(url.toString());
				}
				//头脑风暴
				if (content.getChannel().getChannelId() == 123) {
					StringBuilder url = new StringBuilder();
					url.append("/hdzq/brain_storm_preview.jspx?id=")
							.append(id);
					content.getContentExt().setLink(url.toString());
				}
				//意见建议
				if (content.getChannel().getChannelId() == 124) {
					StringBuilder url = new StringBuilder();
					url.append("/hdzq/suggestion_preview.jspx?id=")
							.append(id);
					content.getContentExt().setLink(url.toString());
				}
				//公司公告
				if (content.getChannel().getChannelId() == 125) {
					StringBuilder url = new StringBuilder();
					url.append("/hdzq/company_notice_preview.jspx?id=")
							.append(id);
					content.getContentExt().setLink(url.toString());
				}
				//惠补政策
				if (content.getChannel().getChannelId() == 117) {
					StringBuilder url = new StringBuilder();
					url.append("/commservice/sAmplePolicyDetail.jspx?id=")
							.append(luceneContentDao.getSAmplePolicyIdByContentId((Integer) id));
					content.getContentExt().setLink(url.toString());
				}
				//项目招投标
				if (content.getChannel().getChannelId() == 116) {
					StringBuilder url = new StringBuilder();
					url.append("/commservice/sTenderTrailerDetail.jspx?id=")
							.append(luceneContentDao.getSTenderTrailerIdByContentId((Integer) id));
					content.getContentExt().setLink(url.toString());
				}
				contents.add(content);
			}
			p.setList(contents);
			return p;
		} catch (org.apache.lucene.queryParser.ParseException e) {
			e.printStackTrace();
		} finally {
			searcher.close();
		}
		return null;
	}

	@Transactional(readOnly = true)
	public List<Content> searchList(String path, String queryString,String category,String workplace,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int first, int max) throws CorruptIndexException, IOException,
			ParseException {
		Directory dir = new SimpleFSDirectory(new File(path));
		return searchList(dir, queryString, category,workplace,siteId, channelId, startDate,
				endDate, first, max);
	}

	@Transactional(readOnly = true)
	public List<Content> searchList(Directory dir, String queryString,String category,String workplace,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int first, int max) throws CorruptIndexException, IOException,
			ParseException {
		Searcher searcher = new IndexSearcher(dir);
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			Query query = LuceneContent.createQuery(queryString,category,workplace, siteId,
					channelId, startDate, endDate, analyzer);
			if (first < 0) {
				first = 0;
			}
			if (max < 0) {
				max = 0;
			}
			TopDocs docs = searcher.search(query, first + max);
			List<Integer> ids = LuceneContent.getResultList(searcher, docs,
					first, max);
			List<Content> contents = new ArrayList<Content>(ids.size());
			for (Object id : ids) {
				contents.add(contentMng.findById((Integer) id));
			}
			return contents;
		} catch (org.apache.lucene.queryParser.ParseException e) {
			e.printStackTrace();
		} finally {
			searcher.close();
		}
		return null;
	}

	@Resource
	private RealPathResolver realPathResolver;
	@Resource
	private ContentMng contentMng;
	@Resource
	private LuceneContentDao luceneContentDao;

}
