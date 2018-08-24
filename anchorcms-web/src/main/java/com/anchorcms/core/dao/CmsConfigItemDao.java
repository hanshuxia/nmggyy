package com.anchorcms.core.dao;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.model.CmsConfigItem;

import java.util.List;

public interface CmsConfigItemDao {
	public List<CmsConfigItem> getList(Integer configId, Integer category);

	public CmsConfigItem findById(Integer id);

	public CmsConfigItem save(CmsConfigItem bean);

	public CmsConfigItem updateByUpdater(Updater<CmsConfigItem> updater);

	public CmsConfigItem deleteById(Integer id);
}