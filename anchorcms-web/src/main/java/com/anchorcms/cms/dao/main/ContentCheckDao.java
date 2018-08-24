package com.anchorcms.cms.dao.main;


import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.common.hibernate.Updater;

public interface ContentCheckDao {
	public ContentCheck findById(Long id);

	public ContentCheck save(ContentCheck bean);

	public ContentCheck updateByUpdater(Updater<ContentCheck> updater);
}