package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsSearchWords;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface CmsSearchWordsDao {
	public Pagination getPage(Integer siteId, String name, Integer recommend,
							  Integer orderBy, int pageNo, int pageSize);

	public List<CmsSearchWords> getList(Integer siteId, String name,
										Integer recommend, Integer orderBy, Integer count, boolean cacheable);

	public CmsSearchWords findById(Integer id);
	
	public CmsSearchWords findByName(String name);

	public CmsSearchWords save(CmsSearchWords bean);

	public CmsSearchWords updateByUpdater(Updater<CmsSearchWords> updater);

	public CmsSearchWords deleteById(Integer id);

	
}