package com.anchorcms.cms.dao.assist;


import com.anchorcms.cms.model.assist.CmsVoteItem;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

public interface CmsVoteItemDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsVoteItem findById(Integer id);

	public CmsVoteItem save(CmsVoteItem bean);

	public CmsVoteItem updateByUpdater(Updater<CmsVoteItem> updater);

	public CmsVoteItem deleteById(Integer id);
}