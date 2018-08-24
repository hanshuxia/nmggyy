package com.anchorcms.core.dao;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUserAccount;

import java.util.Date;


public interface CmsUserAccountDao {
	
	public Pagination getPage(String username, Date drawTimeBegin, Date drawTimeEnd,
							  int orderBy, int pageNo, int pageSize);
	
	public CmsUserAccount findById(Integer id);

	public CmsUserAccount save(CmsUserAccount bean);

	public CmsUserAccount updateByUpdater(Updater<CmsUserAccount> updater);
}