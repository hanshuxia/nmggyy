package com.anchorcms.cms.dao.main;

import com.anchorcms.cms.model.main.ContentType;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;


public interface ContentTypeDao {
	public List<ContentType> getList(boolean containDisabled);

	public ContentType getDef();

	public ContentType findById(Integer id);

	public ContentType save(ContentType bean);

	public ContentType updateByUpdater(Updater<ContentType> updater);

	public ContentType deleteById(Integer id);
}