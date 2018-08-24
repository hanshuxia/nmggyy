package com.anchorcms.cms.dao.assist;


import com.anchorcms.cms.model.assist.CmsConsultExt;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

public interface CmsConsultExtDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsConsultExt findById(Integer id);

	public CmsConsultExt save(CmsConsultExt bean);

	public CmsConsultExt updateByUpdater(Updater<CmsConsultExt> updater);

	public int deleteByContentId(Integer contentId);

	public CmsConsultExt deleteById(Integer id);
}