package com.anchorcms.cms.dao.assist.impl;

import java.util.List;

import com.anchorcms.cms.dao.assist.CmsGuestbookDao;
import com.anchorcms.cms.model.assist.CmsGuestbook;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import org.springframework.stereotype.Repository;


@Repository
public class CmsGuestbookDaoImpl extends
		HibernateBaseDao<CmsGuestbook, Integer> implements CmsGuestbookDao {
	public Pagination getPage(Integer siteId, Integer ctgId, Integer ctgIds[],
							  Integer userId, Boolean recommend, Boolean checked, boolean asc,
							  boolean cacheable, int pageNo, int pageSize) {
		Finder f = createFinder(siteId, ctgId, ctgIds,userId,recommend, checked, asc,
				cacheable);
		return find(f, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<CmsGuestbook> getList(Integer siteId, Integer ctgId,
			Boolean recommend, Boolean checked, boolean desc,
			boolean cacheable, int first, int max) {
		Finder f = createFinder(siteId, ctgId, null,null,recommend, checked, desc,
				cacheable);
		f.setFirstResult(first);
		f.setMaxResults(max);
		return find(f);
	}

	private Finder createFinder(Integer siteId, Integer ctgId,Integer ctgIds[],Integer userId,
			Boolean recommend, Boolean checked, boolean desc, boolean cacheable) {
		Finder f = Finder.create("from CmsGuestbook bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.siteId=:siteId");
			f.setParam("siteId", siteId);
		}
		if (ctgId != null) {
			f.append(" and bean.ctg.guestbookctgId =:ctgId");
			f.setParam("ctgId", ctgId);
		}
		if(ctgIds!=null&&ctgIds.length>0){
			f.append(" and bean.ctg.guestbookctgId in(:ctgIds)");
			f.setParamList("ctgIds", ctgIds);
		}
		if (userId != null) {
			f.append(" and bean.member.userId=:userId");
			f.setParam("userId", userId);
		}
		if (recommend != null) {
			f.append(" and bean.isRecommend=:recommend");
			f.setParam("recommend", recommend);
		}
		if (checked != null) {
			f.append(" and bean.isChecked=:checked");
			f.setParam("checked", checked);
		}
		if (desc) {
			f.append(" order by bean.guestbookId desc");
		} else {
			f.append(" order by bean.guestbookId asc");
		}
		f.setCacheable(cacheable);
		return f;
	}

	public CmsGuestbook findById(Integer id) {
		CmsGuestbook entity = get(id);
		return entity;
	}

	public CmsGuestbook save(CmsGuestbook bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsGuestbook deleteById(Integer id) {
		CmsGuestbook entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsGuestbook> getEntityClass() {
		return CmsGuestbook.class;
	}
}