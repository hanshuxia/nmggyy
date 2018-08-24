package com.anchorcms.cms.service.assist;


import com.anchorcms.cms.model.assist.CmsGuestbook;
import com.anchorcms.cms.model.assist.CmsGuestbookExt;

public interface CmsGuestbookExtMng {
	public CmsGuestbookExt save(CmsGuestbookExt ext, CmsGuestbook guestbook);

	public CmsGuestbookExt update(CmsGuestbookExt ext);
}