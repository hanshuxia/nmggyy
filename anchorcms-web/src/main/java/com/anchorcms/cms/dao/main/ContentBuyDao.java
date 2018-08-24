package com.anchorcms.cms.dao.main;


import com.anchorcms.cms.model.main.ContentBuy;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

public interface ContentBuyDao {
	public Pagination getPage(String orderNum, Integer buyUserId, Integer authorUserId,
							  Short payMode, int pageNo, int pageSize);
	
	public Pagination getPageByContent(Integer contentId,
									   Short payMode, int pageNo, int pageSize);

	public ContentBuy findById(Long id);
	
	public ContentBuy findByOrderNumber(String orderNumber);
	
	public ContentBuy find(Integer buyUserId, Integer contentId);

	public ContentBuy save(ContentBuy bean);

	public ContentBuy updateByUpdater(Updater<ContentBuy> updater);

	public ContentBuy deleteById(Long id);
}