package com.anchorcms.cms.dao.main;

import com.anchorcms.cms.model.assist.CmsWebservice;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface CmsWebserviceDao {
	public Pagination getPage(int pageNo, int pageSize);
	
	public List<CmsWebservice> getList(String type);

	public CmsWebservice findById(Integer id);

	public CmsWebservice save(CmsWebservice bean);

	public CmsWebservice updateByUpdater(Updater<CmsWebservice> updater);

	public CmsWebservice deleteById(Integer id);
}