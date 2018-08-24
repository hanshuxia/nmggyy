package com.anchorcms.cms.dao.assist;


import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsAccountDraw;

import java.util.Date;
import java.util.List;


public interface CmsAccountDrawDao {
	public Pagination getPage(Integer userId, Short applyStatus,
							  Date applyTimeBegin, Date applyTimeEnd, int pageNo, int pageSize);
	
	public List<CmsAccountDraw> getList(Integer userId, Short[] status, Integer count);

	public CmsAccountDraw findById(Integer id);

	public CmsAccountDraw save(CmsAccountDraw bean);

	public CmsAccountDraw updateByUpdater(Updater<CmsAccountDraw> updater);

	public CmsAccountDraw deleteById(Integer id);
}