package com.anchorcms.core.dao;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsLog;

import java.util.Date;


public interface CmsLogDao {
	public Pagination getPage(Integer category, Integer siteId, Integer userId,
							  String title, String ip, int pageNo, int pageSize);

	public CmsLog findById(Integer id);

	public CmsLog save(CmsLog bean);

	public CmsLog deleteById(Integer id);

	public int deleteBatch(Integer category, Integer siteId, Date before);
}