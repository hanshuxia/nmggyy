package com.anchorcms.core.dao;


import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.model.DbFile;

public interface DbFileDao {
	public DbFile findById(String id);

	public DbFile save(DbFile bean);

	public DbFile updateByUpdater(Updater<DbFile> updater);

	public DbFile deleteById(String id);
}