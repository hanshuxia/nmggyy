package com.anchorcms.cms.dao.assist;


import com.anchorcms.cms.model.assist.CmsCommentExt;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

public interface CmsCommentExtDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsCommentExt findById(Integer id);

	public CmsCommentExt save(CmsCommentExt bean);

	public CmsCommentExt updateByUpdater(Updater<CmsCommentExt> updater);

	public int deleteByContentId(Integer contentId);

	public CmsCommentExt deleteById(Integer id);
}