package com.anchorcms.cms.dao.assist.impl;

import com.anchorcms.cms.dao.assist.CmsConsultExtDao;
import com.anchorcms.cms.model.assist.CmsConsultExt;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;


@Repository
public class CmsConsultExtDaoImpl extends
		HibernateBaseDao<CmsConsultExt, Integer> implements CmsConsultExtDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public CmsConsultExt findById(Integer id) {
		CmsConsultExt entity = get(id);
		return entity;
	}

	public CmsConsultExt save(CmsConsultExt bean) {
		getSession().save(bean);
		return bean;
	}

	public int deleteByContentId(Integer contentId) {
		String hql = "delete from CmsConsultExt bean where bean.id in"
				+ " (select c.id from CmsConsult c where c.content.id=:contentId)";
		return getSession().createQuery(hql).setParameter("contentId",
				contentId).executeUpdate();
	}

	public CmsConsultExt deleteById(Integer id) {
		CmsConsultExt entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsConsultExt> getEntityClass() {
		return CmsConsultExt.class;
	}
}