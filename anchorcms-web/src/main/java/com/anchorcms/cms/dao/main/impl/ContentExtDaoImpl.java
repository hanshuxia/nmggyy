package com.anchorcms.cms.dao.main.impl;

import com.anchorcms.cms.dao.main.ContentExtDao;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;


@Repository
public class ContentExtDaoImpl extends HibernateBaseDao<ContentExt, Integer>
		implements ContentExtDao {
	public ContentExt findById(Integer id) {
		ContentExt entity = get(id);
		return entity;
	}

	public ContentExt save(ContentExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentExt> getEntityClass() {
		return ContentExt.class;
	}
}