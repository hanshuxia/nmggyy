package com.anchorcms.cms.service.main;


import com.anchorcms.cms.model.main.ContentBuy;
import com.anchorcms.common.page.Pagination;

public interface ContentBuyMng {
	public Pagination getPage(String orderNum, Integer buyUserId, Integer authorUserId
			, Short payMode, int pageNo, int pageSize);
	
	public Pagination getPageByContent(Integer contentId,
									   Short payMode, int pageNo, int pageSize);

	public ContentBuy findById(Long id);
	
	public ContentBuy findByOrderNumber(String orderNumber);
	
	public boolean hasBuyContent(Integer buyUserId, Integer contentId);

	public ContentBuy save(ContentBuy bean);

	public ContentBuy update(ContentBuy bean);

	public ContentBuy deleteById(Long id);
	
	public ContentBuy[] deleteByIds(Long[] ids);
}