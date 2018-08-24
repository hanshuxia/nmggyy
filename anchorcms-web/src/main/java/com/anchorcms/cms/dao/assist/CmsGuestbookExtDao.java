package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsGuestbookExt;
import com.anchorcms.common.hibernate.Updater;

public interface CmsGuestbookExtDao {
	public CmsGuestbookExt findById(Integer id);

	public CmsGuestbookExt save(CmsGuestbookExt bean);

	public CmsGuestbookExt updateByUpdater(Updater<CmsGuestbookExt> updater);

	public CmsGuestbookExt deleteById(Integer id);
}