package com.anchorcms.cms.service.main;


import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;

public interface ContentExtMng {
	public ContentExt save(ContentExt ext, Content content);

	public ContentExt update(ContentExt ext);
}