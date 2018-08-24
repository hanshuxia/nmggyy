package com.anchorcms.cms.dao.assist.impl;

import com.anchorcms.cms.dao.assist.CmsCommentExtDao;
import com.anchorcms.cms.model.assist.CmsCommentExt;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;


@Repository
public class CmsCommentExtDaoImpl extends
		HibernateBaseDao<CmsCommentExt, Integer> implements CmsCommentExtDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public CmsCommentExt findById(Integer id) {
		CmsCommentExt entity = get(id);
		return entity;
	}

	public CmsCommentExt save(CmsCommentExt bean) {
		getSession().save(bean);
		return bean;
	}

	public int deleteByContentId(Integer contentId) {
		String hql = "delete from CmsCommentExt bean where bean.id in"
				+ " (select c.id from CmsComment c where c.content.id=:contentId)";
		return getSession().createQuery(hql).setParameter("contentId",
				contentId).executeUpdate();
	}

	public CmsCommentExt deleteById(Integer id) {
		CmsCommentExt entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsCommentExt> getEntityClass() {
		return CmsCommentExt.class;
	}
}