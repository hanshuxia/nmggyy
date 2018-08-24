package com.anchorcms.cms.service.assist;

import com.anchorcms.cms.model.assist.CmsVoteItem;
import com.anchorcms.cms.model.assist.CmsVoteSubTopic;
import com.anchorcms.common.page.Pagination;

import java.util.Collection;


public interface CmsVoteItemMng {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsVoteItem findById(Integer id);

	public Collection<CmsVoteItem> save(Collection<CmsVoteItem> items,
										CmsVoteSubTopic topic);

	public Collection<CmsVoteItem> update(Collection<CmsVoteItem> items,
										  CmsVoteSubTopic topic);

	public CmsVoteItem deleteById(Integer id);

	public CmsVoteItem[] deleteByIds(Integer[] ids);

}