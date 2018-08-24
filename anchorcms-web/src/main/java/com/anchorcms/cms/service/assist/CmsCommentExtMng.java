package com.anchorcms.cms.service.assist;


import com.anchorcms.cms.model.assist.CmsComment;
import com.anchorcms.cms.model.assist.CmsCommentExt;

public interface CmsCommentExtMng {
	public CmsCommentExt save(String ip, String text, CmsComment comment);

	public CmsCommentExt update(CmsCommentExt bean);

	public int deleteByContentId(Integer contentId);
}