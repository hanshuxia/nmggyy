package com.anchorcms.core.service;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsLog;
import com.anchorcms.core.model.CmsUser;

import javax.servlet.http.HttpServletRequest;


public interface CmsLogMng {
	public Pagination getPage(Integer category, Integer siteId,
							  String username, String title, String ip, int pageNo, int pageSize);

	public CmsLog findById(Integer id);

	public CmsLog operating(HttpServletRequest request, String title,
							String content);

	public CmsLog loginFailure(HttpServletRequest request, String content);

	public CmsLog loginSuccess(HttpServletRequest request, CmsUser user);

	public CmsLog save(CmsLog bean);

	public CmsLog deleteById(Integer id);

	public CmsLog[] deleteByIds(Integer[] ids);

	public int deleteBatch(Integer category, Integer siteId, Integer days);
}