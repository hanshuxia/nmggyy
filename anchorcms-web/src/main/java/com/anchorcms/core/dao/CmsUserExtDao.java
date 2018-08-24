package com.anchorcms.core.dao;


import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.model.CmsUserExt;

public interface CmsUserExtDao {
	public CmsUserExt findById(Integer id);

	public CmsUserExt save(CmsUserExt bean);

	public CmsUserExt updateByUpdater(Updater<CmsUserExt> updater);
}