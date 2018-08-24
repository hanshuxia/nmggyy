package com.anchorcms.cms.dao.main.impl;

import com.anchorcms.cms.dao.main.ContentTxtDao;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;


@Repository
public class ContentTxtDaoImpl extends HibernateBaseDao<ContentTxt, Integer>
		implements ContentTxtDao {
	public ContentTxt findById(Integer id) {
		ContentTxt entity = get(id);
		return entity;
	}

	public ContentTxt save(ContentTxt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentTxt> getEntityClass() {
		return ContentTxt.class;
	}
}