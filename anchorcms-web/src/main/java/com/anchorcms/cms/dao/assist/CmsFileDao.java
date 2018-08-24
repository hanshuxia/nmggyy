package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.main.CmsFile;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;


public interface CmsFileDao {
	public List<CmsFile> getList(Boolean valid);

	public CmsFile findById(Integer id);
	
	public CmsFile findByPath(String path);

	public CmsFile save(CmsFile bean);

	public CmsFile updateByUpdater(Updater<CmsFile> updater);

	public CmsFile deleteById(Integer id);
	
	public CmsFile deleteByPath(String path);
	
	public void deleteByContentId(Integer contentId);
}