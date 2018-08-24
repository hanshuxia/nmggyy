package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsWebserviceAuth;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

public interface CmsWebserviceAuthDao {
	public Pagination getPage(int pageNo, int pageSize);
	
	public CmsWebserviceAuth findByUsername(String username);

	public CmsWebserviceAuth findById(Integer id);

	public CmsWebserviceAuth save(CmsWebserviceAuth bean);

	public CmsWebserviceAuth updateByUpdater(Updater<CmsWebserviceAuth> updater);

	public CmsWebserviceAuth deleteById(Integer id);
}