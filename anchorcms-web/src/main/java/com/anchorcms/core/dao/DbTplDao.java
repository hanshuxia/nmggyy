package com.anchorcms.core.dao;

import com.anchorcms.core.model.DbTpl;

import java.util.List;


public interface DbTplDao {
	public List<DbTpl> getStartWith(String prefix);

	public List<DbTpl> getChild(String path, boolean isDirectory);

	public DbTpl findById(String id);

	public DbTpl save(DbTpl bean);

	public DbTpl deleteById(String id);
}