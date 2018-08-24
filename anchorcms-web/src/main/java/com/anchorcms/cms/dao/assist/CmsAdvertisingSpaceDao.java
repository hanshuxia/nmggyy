package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsAdvertisingSpace;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;


public interface CmsAdvertisingSpaceDao {
	public List<CmsAdvertisingSpace> getList(Integer siteId);

	public CmsAdvertisingSpace findById(Integer id);

	public CmsAdvertisingSpace save(CmsAdvertisingSpace bean);

	public CmsAdvertisingSpace updateByUpdater(
			Updater<CmsAdvertisingSpace> updater);

	public CmsAdvertisingSpace deleteById(Integer id);
}