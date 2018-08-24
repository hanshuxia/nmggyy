package com.anchorcms.cms.service.main;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCharge;
import com.anchorcms.common.page.Pagination;

import java.util.Date;
import java.util.List;


public interface ContentChargeMng {
	
	public List<ContentCharge> getList(String contentTitle, Integer authorUserId,
									   Date buyTimeBegin, Date buyTimeEnd, int orderBy, int count);
	
	public Pagination getPage(String contentTitle, Integer authorUserId,
							  Date buyTimeBegin, Date buyTimeEnd,
							  int orderBy, int pageNo, int pageSize);
	
	public ContentCharge save(Double chargeAmount, Short charge, Content content);
	
	public void afterContentUpdate(Content bean, Short charge, Double chargeAmount);

	public ContentCharge update(ContentCharge charge);
	
	public ContentCharge afterUserPay(Double payAmout, Content content);
}