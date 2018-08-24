package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsTask;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface CmsTaskDao {
	public Pagination getPage(Integer siteId, int pageNo, int pageSize);
	
	public List<CmsTask> getList();

	public CmsTask findById(Integer id);

	public CmsTask save(CmsTask bean);

	public CmsTask updateByUpdater(Updater<CmsTask> updater);

	public CmsTask deleteById(Integer id);
}