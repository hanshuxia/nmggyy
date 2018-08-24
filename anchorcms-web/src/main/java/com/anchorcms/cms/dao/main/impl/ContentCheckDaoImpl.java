package com.anchorcms.cms.dao.main.impl;

import com.anchorcms.cms.dao.main.ContentCheckDao;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;


@Repository
public class ContentCheckDaoImpl extends HibernateBaseDao<ContentCheck, Long>
		implements ContentCheckDao {
	public ContentCheck findById(Long id) {
		ContentCheck entity = get(id);
		return entity;
	}

	public ContentCheck save(ContentCheck bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentCheck> getEntityClass() {
		return ContentCheck.class;
	}
}