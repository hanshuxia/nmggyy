package com.anchorcms.core.dao;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.model.CmsGroup;

import java.util.List;


public interface CmsGroupDao {
	public List<CmsGroup> getList();

	public CmsGroup getRegDef();

	public CmsGroup findById(Integer id);
	
	public CmsGroup findByName(String name);

	public CmsGroup save(CmsGroup bean);

	public CmsGroup updateByUpdater(Updater<CmsGroup> updater);

	public CmsGroup deleteById(Integer id);
}