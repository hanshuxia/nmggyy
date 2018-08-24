package com.anchorcms.cms.dao.assist.impl;

import com.anchorcms.cms.dao.assist.CmsConsultDao;
import com.anchorcms.cms.model.assist.CmsConsult;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CmsConsultDaoImpl extends HibernateBaseDao<CmsConsult, Integer>
		implements CmsConsultDao {
	public Pagination getPage(Integer siteId, Integer contentId,
							  Integer greaterThen, Boolean checked, Boolean recommend,
							  boolean desc, int pageNo, int pageSize, boolean cacheable) {
		Finder f = getFinder(siteId, contentId,null,null,null,greaterThen, checked,
				recommend, desc, cacheable);
		return find(f, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<CmsConsult> getList(Integer siteId, Integer contentId,
			Integer parentId,Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int count, boolean cacheable) {
		Finder f = getFinder(siteId, contentId,parentId,null,null,greaterThen, checked,
				recommend, desc, cacheable);
		f.setMaxResults(count);
		return find(f);
	}
	public Pagination getPageForMember(Integer siteId, Integer contentId,Integer toUserId,Integer fromUserId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize,boolean cacheable){
		Finder f = getFinder(siteId, contentId,null, toUserId,fromUserId,greaterThen, checked,
				recommend, desc, cacheable);
		return find(f, pageNo, pageSize);
	}
	@SuppressWarnings("unchecked")
	public List<CmsConsult> getListForDel(Integer siteId, Integer userId,
			Integer consultUserId, String ip){
		Finder f = Finder.create("from CmsConsult bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.siteId=:siteId");
			f.setParam("siteId", siteId);
		}
		if(consultUserId!=null){
			f.append(" and bean.consultUser.userId=:consultUserId");
			f.setParam("consultUserId", consultUserId);
		}
		if(userId!=null){
			f.append(" and bean.content.user.userId=:fromUserId");
			f.setParam("fromUserId", userId);
		}
		f.setCacheable(false);
		return find(f);
	}

	private Finder getFinder(Integer siteId, Integer contentId,
			Integer parentId,Integer toUserId,Integer fromUserId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, boolean cacheable) {
		Finder f = Finder.create("from CmsConsult bean where 1=1");
		if(parentId!=null){
			f.append(" and bean.topParentId=:parentId");
			f.setParam("parentId", parentId);
		}else if (contentId != null) {
			//按照内容ID来查询对内容的直接评论
			f.append(" and (bean.content.contentId=:contentId and bean.parent is null )");
			f.setParam("contentId", contentId);
		} else if (siteId != null) {
			f.append(" and bean.site.siteId=:siteId");
			f.setParam("siteId", siteId);
		}
		if(toUserId!=null){
			f.append(" and bean.consultUser.userId=:toUserId");
			f.setParam("toUserId", toUserId);
		}else if(fromUserId!=null){
			f.append(" and bean.content.user.userId=:fromUserId");
			f.setParam("fromUserId", fromUserId);
		}
		if (greaterThen != null) {
			f.append(" and bean.ups>=:greatTo");
			f.setParam("greatTo", greaterThen);
		}
		if (checked != null) {
			f.append(" and bean.isChecked=:checked");
			f.setParam("checked", checked);
		}
		if(recommend!=null){
			f.append(" and bean.isRecommend=:isRecommend");
			f.setParam("isRecommend", recommend);
		}
		if (desc) {
			f.append(" order by bean.consultId desc");
		} else {
			f.append(" order by bean.consultId asc");
		}
		f.setCacheable(cacheable);
		return f;
	}

	public CmsConsult findById(Integer id) {
		CmsConsult entity = get(id);
		return entity;
	}

	public CmsConsult save(CmsConsult bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsConsult deleteById(Integer id) {
		CmsConsult entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public int deleteByContentId(Integer contentId) {
		String hql = "delete from CmsConsult bean where bean.content.id=:contentId";
		return getSession().createQuery(hql).setParameter("contentId",
				contentId).executeUpdate();
	}
	
	@Override
	protected Class<CmsConsult> getEntityClass() {
		return CmsConsult.class;
	}
}