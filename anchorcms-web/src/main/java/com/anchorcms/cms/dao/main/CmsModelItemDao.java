package com.anchorcms.cms.dao.main;

import com.anchorcms.cms.model.main.CmsModelItem;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;

public interface CmsModelItemDao {
	public List<CmsModelItem> getList(Integer modelId, boolean isChannel,
									  boolean hasDisabled);

	public CmsModelItem findById(Integer id);

	public CmsModelItem save(CmsModelItem bean);

	public CmsModelItem updateByUpdater(Updater<CmsModelItem> updater);

	public CmsModelItem deleteById(Integer id);
}