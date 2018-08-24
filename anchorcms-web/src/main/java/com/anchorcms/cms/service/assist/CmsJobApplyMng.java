package com.anchorcms.cms.service.assist;

import com.anchorcms.cms.model.assist.CmsJobApply;
import com.anchorcms.common.page.Pagination;

public interface CmsJobApplyMng {
	public Pagination getPage(Integer userId, Integer contentId, Integer siteId, boolean cacheable, int pageNo, int pageSize);

	public CmsJobApply findById(Integer id);

	public CmsJobApply save(CmsJobApply bean);

	public CmsJobApply update(CmsJobApply bean);

	public CmsJobApply deleteById(Integer id);
	
	public CmsJobApply[] deleteByIds(Integer[] ids);
}