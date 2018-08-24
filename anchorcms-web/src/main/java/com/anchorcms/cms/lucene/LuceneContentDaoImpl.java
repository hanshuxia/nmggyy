package com.anchorcms.cms.lucene;


import java.io.IOException;
import java.util.Date;


import com.anchorcms.cms.controller.admin.LuceneContent;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
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
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.hibernate.*;
import org.springframework.stereotype.Repository;


@Repository
public class LuceneContentDaoImpl extends HibernateBaseDao<Content, Integer>
		implements LuceneContentDao {
	public Integer index(IndexWriter writer, Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer max)
			throws CorruptIndexException, IOException {
		Finder f = Finder.create("select bean from Content bean");
		if (channelId != null) {
			f.append(" join bean.channel channel, Channel parent");
			f.append(" where channel.lft between parent.lft and parent.rgt");
			f.append(" and channel.site.id=parent.site.id");
			f.append(" and parent.id=:parentId");
			f.setParam("parentId", channelId);
		} else if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		if (startId != null) {
			f.append(" and bean.id > :startId");
			f.setParam("startId", startId);
		}
		if (startDate != null) {
			f.append(" and bean.contentExt.releaseDate >= :startDate");
			f.setParam("startDate", startDate);
		}
		if (endDate != null) {
			f.append(" and bean.contentExt.releaseDate <= :endDate");
			f.setParam("endDate", endDate);
		}
		f.append(" and bean.status=" + ContentCheck.CHECKED);
		f.append(" order by bean.id asc");
		if (max != null) {
			f.setMaxResults(max);
		}
		Session session = getSession();
		ScrollableResults contents = f.createQuery(getSession()).setCacheMode(
				CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
		int count = 0;
		Content content = null;
		while (contents.next()) {
			content = (Content) contents.get(0);
			writer.addDocument(LuceneContent.createDocument(content));
			if (++count % 20 == 0) {
				session.clear();
			}
		}
		if (count < max || content == null) {
			// 实际数量小于指定数量，代表生成结束。如果没有任何数据，也代表生成结束。
			return null;
		} else {
			// 返回最后一个ID
			return content.getContentId();
		}

	}

	/**
	 * @author ly
	 * @date 2016/1/19 12:50
	 * @desc 全文检索通过contentId查找能力
	 **/
	public SAbility findAbilityByContentId(Integer id) {
		Query query = getSession().createQuery("SELECT bean from SAbility bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		SAbility abilty = (SAbility) query.uniqueResult();
		return abilty;
	}

	/**
	 * @author ly
	 * @date 2016/1/19 14:06
	 * @desc 全文检索通过contentId查找需求
	 **/
	public SDemand findDemandByContentId(Integer id) {
		Query query = getSession().createQuery("SELECT bean from SDemand bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		SDemand demand = (SDemand) query.uniqueResult();
		return demand;
	}

	/**
	 * @author ly
	 * @date 2016/1/19 14:06
	 * @desc 全文检索通过contentId查找政策
	 **/
	public SIcloudPolicy findPolicyByContentId(Integer id) {
		Query query = getSession().createQuery("SELECT bean from SIcloudPolicy bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		SIcloudPolicy policy = (SIcloudPolicy) query.uniqueResult();
		return policy;
	}

	/**
	 * @author ly
	 * @date 2016/1/19 14:06
	 * @desc 全文检索通过contentId查找云资源
	 **/
	public SIcloudManage findResourceByContentId(Integer id) {
		Query query = getSession().createQuery("SELECT bean from SIcloudManage bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		SIcloudManage resource = (SIcloudManage) query.uniqueResult();
		return resource;
	}

	/**
	 * @author ly
	 * @date 2016/1/19 14:06
	 * @desc 全文检索通过contentId查找云需求
	 **/
	public SIcloudDemand findIcloudDemandByContentId(Integer id) {
		Query query = getSession().createQuery("SELECT bean from SIcloudDemand bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		SIcloudDemand icloudDemand = (SIcloudDemand) query.uniqueResult();
		return icloudDemand;
	}

	/**
	 * @author ly
	 * @date 2016/1/19 14:06
	 * @desc 全文检索通过contentId查找软件
	 **/
	public SSoftware findSoftwareDemandByContentId(Integer id) {
		Query query = getSession().createQuery("SELECT bean from SSoftware bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		SSoftware software = (SSoftware) query.uniqueResult();
		return software;
	}

	public SCompany findCompanyByContentId(Integer id) {
		Query query = getSession().createQuery("SELECT bean from SCompany bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		SCompany company = (SCompany) query.uniqueResult();
		return company;
	}

	/**
	 * @author machao
	 * @Date 2017/1/19 23:07
	 * @return
	 * 备品备件
	 */
	public SSpare findSSpareByContentId(Integer id){
		Query query = getSession().createQuery("SELECT bean from SSpare bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		SSpare sSpare = (SSpare) query.uniqueResult();
		return sSpare;
	}
	/**
	 * @author machao
	 * @Date 2017/1/19 23:07
	 * @return
	 * 备品备件求购
	 */
	public SSpareDemand findSSpareDemandByContentId(Integer id){
		Query query = getSession().createQuery("SELECT bean from SSpareDemand bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		SSpareDemand sSpareDemand = (SSpareDemand) query.uniqueResult();
		return sSpareDemand;
	}
	/**
	 * @author machao
	 * @Date 2017/1/19 23:07
	 * @return
	 * 维修资源
	 */
	public SRepairRes findSRepairResByContentId(Integer id){
        Query query = getSession().createQuery("SELECT bean from SRepairRes bean where bean.content.contentId=:contentId ")
                .setParameter("contentId", id);
        SRepairRes sRepairRes = (SRepairRes) query.uniqueResult();
        return sRepairRes;
    }
	/**
	 * @author machao
	 * @Date 2017/1/19 23:07
	 * @return
	 * 维修需求
	 */
	public SRepairDemand findSRepairDemandByContentId(Integer id){
        Query query = getSession().createQuery("SELECT bean from SRepairDemand bean where bean.content.contentId=:contentId ")
                .setParameter("contentId", id);
        SRepairDemand sRepairDemand = (SRepairDemand) query.uniqueResult();
        return sRepairDemand;
    }

	public Integer getSAmplePolicyIdByContentId(Integer id) {
		Query query = getSession().createQuery("SELECT bean.amplePolicyId from SAmplePolicy bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		return ((Integer) query.uniqueResult());
	}

	public Integer getSTenderTrailerIdByContentId(Integer id) {
		Query query = getSession().createQuery("SELECT bean.tenderTrailerId from STenderTrailer bean where bean.content.contentId=:contentId ")
				.setParameter("contentId", id);
		return ((Integer) query.uniqueResult());
	}

	@Override
	protected Class<Content> getEntityClass() {
		return Content.class;
	}
}
