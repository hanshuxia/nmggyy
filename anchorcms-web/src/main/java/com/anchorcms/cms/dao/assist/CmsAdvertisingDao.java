package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsAdvertising;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface CmsAdvertisingDao {
	public Pagination getPage(Integer siteId, Integer adspaceId,
							  Boolean enabled, int pageNo, int pageSize);

	public List<CmsAdvertising> getList(Integer adspaceId, Boolean enabled);

	public CmsAdvertising findById(Integer id);

	public CmsAdvertising save(CmsAdvertising bean);

	public CmsAdvertising updateByUpdater(Updater<CmsAdvertising> updater);

	public CmsAdvertising deleteById(Integer id);
}