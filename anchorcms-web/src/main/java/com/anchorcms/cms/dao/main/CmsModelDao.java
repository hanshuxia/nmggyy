package com.anchorcms.cms.dao.main;

import com.anchorcms.cms.model.main.CmsModel;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;


public interface CmsModelDao {
	public List<CmsModel> getList(boolean containDisabled, Boolean hasContent, Integer siteId);

	public CmsModel getDefModel();

	public CmsModel findById(Integer id);
	
	public CmsModel findByPath(String path);

	public CmsModel save(CmsModel bean);

	public CmsModel updateByUpdater(Updater<CmsModel> updater);

	public CmsModel deleteById(Integer id);
}