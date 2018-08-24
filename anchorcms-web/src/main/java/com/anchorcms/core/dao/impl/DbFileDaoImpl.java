package com.anchorcms.core.dao.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.core.dao.DbFileDao;
import com.anchorcms.core.model.DbFile;
import org.springframework.stereotype.Repository;

@Repository
public class DbFileDaoImpl extends HibernateBaseDao<DbFile, String> implements
		DbFileDao {
	public DbFile findById(String id) {
		DbFile entity = get(id);
		return entity;
	}

	public DbFile save(DbFile bean) {
		getSession().save(bean);
		return bean;
	}

	public DbFile deleteById(String id) {
		DbFile entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<DbFile> getEntityClass() {
		return DbFile.class;
	}
}