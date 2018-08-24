package com.anchorcms.cms.dao.main;


import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.common.hibernate.Updater;

public interface ContentExtDao {
	public ContentExt findById(Integer id);

	public ContentExt save(ContentExt bean);

	public ContentExt updateByUpdater(Updater<ContentExt> updater);
}