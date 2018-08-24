package com.anchorcms.cms.dao.assist;


import com.anchorcms.cms.model.assist.CmsConfigContentCharge;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

public interface CmsConfigContentChargeDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsConfigContentCharge findById(Integer id);

	public CmsConfigContentCharge save(CmsConfigContentCharge bean);

	public CmsConfigContentCharge updateByUpdater(Updater<CmsConfigContentCharge> updater);

	public CmsConfigContentCharge deleteById(Integer id);
}