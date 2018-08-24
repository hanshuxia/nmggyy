package com.anchorcms.core.dao;


import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.model.CmsConfig;

public interface CmsConfigDao {
	public CmsConfig findById(Integer id);

	public CmsConfig updateByUpdater(Updater<CmsConfig> updater);
}