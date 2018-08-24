package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsJobApply;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

public interface CmsJobApplyDao {
	public Pagination getPage(Integer userId, Integer contentId,
							  Integer siteId, boolean cacheable, int pageNo, int pageSize);

	public CmsJobApply findById(Integer id);

	public CmsJobApply save(CmsJobApply bean);

	public CmsJobApply updateByUpdater(Updater<CmsJobApply> updater);

	public CmsJobApply deleteById(Integer id);
}