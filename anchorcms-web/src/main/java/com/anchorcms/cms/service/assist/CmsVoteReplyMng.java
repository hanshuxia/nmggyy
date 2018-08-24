package com.anchorcms.cms.service.assist;


import com.anchorcms.cms.model.assist.CmsVoteReply;
import com.anchorcms.common.page.Pagination;

public interface CmsVoteReplyMng {

	public Pagination getPage(Integer subTopicId, int pageNo, int pageSize);
	
	public CmsVoteReply findById(Integer id);

	public CmsVoteReply save(CmsVoteReply bean);

	public CmsVoteReply update(CmsVoteReply bean);

	public CmsVoteReply deleteById(Integer id);

	public CmsVoteReply[] deleteByIds(Integer[] ids);
}