package com.anchorcms.cms.dao.main;


import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.hibernate.Updater;

public interface ContentTxtDao {
	public ContentTxt findById(Integer id);

	public ContentTxt save(ContentTxt bean);

	public ContentTxt updateByUpdater(Updater<ContentTxt> updater);
}