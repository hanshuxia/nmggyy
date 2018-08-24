package com.anchorcms.core.dao;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.model.CmsRole;

import java.util.List;


public interface CmsRoleDao {
	public List<CmsRole> getList();

	public CmsRole findById(Integer id);

	public CmsRole save(CmsRole bean);

	public CmsRole updateByUpdater(Updater<CmsRole> updater);

	public CmsRole deleteById(Integer id);
}