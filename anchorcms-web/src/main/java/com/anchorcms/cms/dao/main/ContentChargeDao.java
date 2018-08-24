package com.anchorcms.cms.dao.main;

import com.anchorcms.cms.model.main.ContentCharge;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.Date;
import java.util.List;


public interface ContentChargeDao {
	
	public List<ContentCharge> getList(String contentTitle, Integer authorUserId,
									   Date buyTimeBegin, Date buyTimeEnd, int orderBy, int count);
	
	public Pagination getPage(String contentTitle, Integer authorUserId,
							  Date buyTimeBegin, Date buyTimeEnd,
							  int orderBy, int pageNo, int pageSize);
	
	public ContentCharge findById(Integer id);

	public ContentCharge save(ContentCharge bean);

	public ContentCharge updateByUpdater(Updater<ContentCharge> updater);
}