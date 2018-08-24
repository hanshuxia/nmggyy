package com.anchorcms.core.service;


import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.CmsUserSite;

public interface CmsUserSiteMng {
	public CmsUserSite findById(Integer id);

	public CmsUserSite save(CmsSite site, CmsUser user, Byte step,
							Boolean allChannel);

	public CmsUserSite update(CmsUserSite bean);

	public void updateByUser(CmsUser user, Integer siteId, Byte step,
							 Boolean allChannel);

	public void updateByUser(CmsUser user, Integer[] siteIds, Byte[] steps,
							 Boolean[] allChannels);

	public int deleteBySiteId(Integer siteId);

	public CmsUserSite deleteById(Integer id);

	public CmsUserSite[] deleteByIds(Integer[] ids);
}