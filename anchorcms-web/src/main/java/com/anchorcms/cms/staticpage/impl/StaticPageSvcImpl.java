package com.anchorcms.cms.staticpage.impl;


import com.anchorcms.cms.dao.StaticPageDao;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.cms.staticpage.DistributionThread;
import com.anchorcms.cms.staticpage.StaticPageSvc;
import com.anchorcms.common.constants.Constants;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.mvc.RealPathResolver;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.Ftp;
import com.anchorcms.core.service.CmsSiteMng;
import com.anchorcms.core.service.FtpMng;
import com.anchorcms.icloud.service.staticpage.StaticpageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.UTF8;

@Service
@Transactional
public class StaticPageSvcImpl implements StaticPageSvc, InitializingBean {
	private Logger log = LoggerFactory.getLogger(StaticPageSvcImpl.class);

	public int content(Integer siteId, Integer channelId, Date start,String isAdd)
			throws IOException, TemplateException {
		long time = System.currentTimeMillis();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("isAdd",isAdd);
		int count = staticPageDao.contentStatic(siteId, channelId, start, conf,staticpage,
				data);
		time = System.currentTimeMillis() - time;
		log.info("create content page count {}, in {} ms", count, time);
		return count;
	}

	public boolean content(Content content) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		long time = System.currentTimeMillis();
		boolean generated = staticPageDao.contentStatic(content, conf, data);
		time = System.currentTimeMillis() - time;
		log.info("create content page in {} ms", time);
		return generated;
	}
	
	public void contentRelated(Integer contentId) throws IOException,
	TemplateException{
		Content content=contentMng.findById(contentId);
		contentRelated(content);
	}

	public void contentRelated(Content content) throws IOException,
			TemplateException {
		content(content);
		Channel channel = content.getChannel();
		while (channel != null) {
			channel(channel, true);
			channel = channel.getParent();
		}
		//新增副栏目生成
		Set<Channel> channels=content.getChannelsWithoutMain();
		for(Channel c:channels){
			channel(c, true);
		}
		if (content.getSite().getIsStaticIndex()) {
			index(content.getSite());
		}
	}

	@Transactional(readOnly = true)
	public void deleteContent(Content content) throws IOException, TemplateException {
		String real;
		File file;
		int totalPage = content.getPageCount();
		for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
			real = realPathResolver.get(content.getStaticFilename(pageNo));
			file = new File(real);
			file.delete();
			if(content.getSite().getMobileStaticSync()){
				real = realPathResolver.get(content.getMobileStaticFilename(pageNo));
				file = new File(real);
				file.delete();
			}
		}
		Channel channel = content.getChannel();
		while (channel != null) {
			channel(channel, true);
			channel = channel.getParent();
		}
		if (content.getSite().getIsStaticIndex()) {
			index(content.getSite());
		}
	}

	@Transactional(readOnly = true)
	public int channel(Integer siteId, Integer channelId, boolean containChild)
			throws IOException, TemplateException {
		long time = System.currentTimeMillis();
		Map<String, Object> data = new HashMap<String, Object>();
		int count = staticPageDao.channelStatic(siteId, channelId,
				containChild, conf, data);
		time = System.currentTimeMillis() - time;
		log.info("create channel page count {}, in {} ms", count, time);
		return count;
	}

	@Transactional(readOnly = true)
	public void channel(Channel channel, boolean firstOnly) throws IOException,
			TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		long time = System.currentTimeMillis();
		staticPageDao.channelStatic(channel, firstOnly, conf, data);
		time = System.currentTimeMillis() - time;
		log.info("create channel page in {} ms", time);
	}

	@Transactional(readOnly = true)
	public void deleteChannel(Channel channel) {
		// 如果是外部链接或者不需要生产静态页，则不删除
		if (!StringUtils.isBlank(channel.getLink())
				|| !channel.getStaticChannel()) {
			return;
		}
		// 没有内容或者有子栏目，则只删除一页
		int childs = staticPageDao.childsOfChannel(channel.getChannelId());
		int quantity, totalPage;
		if (!channel.getModel().getHasContent()
				|| (!channel.getListChild() && childs > 0)) {
			totalPage = 1;
		} else {
			if (channel.getListChild()) {
				quantity = childs;
			} else {
				quantity = staticPageDao.contentsOfChannel(channel.getChannelId());
			}
			if (quantity <= 0) {
				totalPage = 1;
			} else {
				totalPage = (quantity - 1) / channel.getPageSize() + 1;
			}
		}
		String real, filename;
		File f;
		for (int i = 1; i <= totalPage; i++) {
			filename = channel.getStaticFilename(i);
			real = realPathResolver.get(filename.toString());
			f = new File(real);
			f.delete();
			if(channel.getSite().getMobileStaticSync()){
				filename = channel.getMobileStaticFilename(i);
				real = realPathResolver.get(filename.toString());
				f = new File(real);
				f.delete();
			}
		}
	}
	
	public void index(Integer siteId) throws IOException, TemplateException{
		CmsSite site=siteMng.findById(siteId);
		index(site);
	}

	public void index(CmsSite site) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		FrontUtils.frontData(data, site, null, site.getUrlStatic(), null);
		/*
		String tpl = FrontUtils.getTplPath(tplMessageSource, site
				.getLocaleAdmin(), site.getSolutionPath(), TPLDIR_INDEX,
				TPL_INDEX);
		*/
		String tpl=site.getTplIndexOrDef();
		index(site, tpl, data,false);
		if(site.getMobileStaticSync()){
			String mobileTpl=site.getMobileTplIndexOrDef();
			index(site, mobileTpl, data,true);
		}
	}

	@Transactional(readOnly = true)
	public void index(CmsSite site, String tpl, Map<String, Object> data, boolean mobile)
			throws IOException, TemplateException {
		long time = System.currentTimeMillis();
		File f ;
		if(mobile){
			f = new File(getMobileIndexPath(site));
		}else{
		    f = new File(getIndexPath(site));
		}
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		Writer out = null;
		try {
			// FileWriter不能指定编码确实是个问题，只能用这个代替了。
			out = new OutputStreamWriter(new FileOutputStream(f), UTF8);
			Template template = conf.getTemplate(tpl);
			template.process(data, out);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		if(site.getPageIsSyncFtp()){
			distributeIndexHtml(site, f, mobile);
		}
		time = System.currentTimeMillis() - time;
		log.info("create index page, in {} ms", time);
	}

	@Transactional(readOnly = true)
	public boolean deleteIndex(CmsSite site) {
		File f ;
		f = new File(getIndexPath(site));
		if(site.getMobileStaticSync()){
			f = new File(getMobileIndexPath(site));
			f.delete();
		}
		return f.delete();
	}
	
	private String getIndexPath(CmsSite site) {
		StringBuilder pathBuff = new StringBuilder();
		if (!site.getIsIndexToRoot()) {
			if (!StringUtils.isBlank(site.getStaticDir())) {
				pathBuff.append(site.getStaticDir());
			}
		}
		pathBuff.append("/").append(Constants.INDEX).append(
				site.getStaticSuffix());
		return realPathResolver.get(pathBuff.toString());
	}
	
	private String getMobileIndexPath(CmsSite site) {
		StringBuilder pathBuff = new StringBuilder();
		boolean indexToRoot=site.getIsIndexToRoot();
		String indexName;
		if (!indexToRoot) {
			if (!StringUtils.isBlank(site.getMobileStaticDir())) {
				pathBuff.append(site.getMobileStaticDir());
			}
			indexName= Constants.INDEX;
		}else{
			indexName= Constants.INDEX_MOBILE;
		}
		pathBuff.append("/").append(indexName).append(
				site.getStaticSuffix());
		return realPathResolver.get(pathBuff.toString());
	}
	
	private void distributeIndexHtml(CmsSite site, File f, boolean mobile) throws FileNotFoundException{
		if(site.getSyncPageFtp()!=null){
			Ftp syncPageFtp=ftpMng.findById(site.getSyncPageFtp().getFtpId());
			String filename;
			if(mobile){
				filename=site.getMobileStaticDir()+"/index.html";
			}else{
				filename=site.getStaticDir()+"/index.html";
			}
			Thread thread = new Thread(new DistributionThread(syncPageFtp,filename, new FileInputStream(f)));
			thread.start();
		}
	}
	@Resource(name = "tplMessageSource")
	private MessageSource tplMessageSource;
	@Resource
	private RealPathResolver realPathResolver;
	@Resource
	private StaticPageDao staticPageDao;

	private Configuration conf;
	@Resource
	private FtpMng ftpMng;
	@Resource
	private ContentMng contentMng;
	@Resource
	private CmsSiteMng siteMng;

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(conf, "freemarker configuration cannot be null!");
		Assert.notNull(tplMessageSource,"tplMessageSource configuration cannot be null!");
	}

	@Resource(name = "frontfreemarkerConfig")
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.conf = freeMarkerConfigurer.getConfiguration();
	}

	public void setTplMessageSource(MessageSource tplMessageSource) {
		this.tplMessageSource = tplMessageSource;
	}

	@Resource
	private StaticpageService staticpage;

}
