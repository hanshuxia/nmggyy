package com.anchorcms.core.service;


import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.CmsUserExt;

public interface CmsUserExtMng {
	public CmsUserExt findById(Integer userId);
	
	public CmsUserExt save(CmsUserExt ext, CmsUser user);

	public CmsUserExt update(CmsUserExt ext, CmsUser user);
}