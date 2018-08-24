package com.anchorcms.core.dao.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.core.dao.CmsConfigDao;
import com.anchorcms.core.model.CmsConfig;
import org.springframework.stereotype.Repository;


@Repository
public class CmsConfigDaoImpl extends HibernateBaseDao<CmsConfig, Integer>
		implements CmsConfigDao {
	public CmsConfig findById(Integer id) {
		CmsConfig entity = get(id);
		return entity;
	}

	@Override
	protected Class<CmsConfig> getEntityClass() {
		return CmsConfig.class;
	}
}