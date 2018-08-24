package com.anchorcms.cms.dao.main;

import com.anchorcms.cms.model.main.CmsThirdAccount;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

public interface CmsThirdAccountDao {
	public Pagination getPage(String username, String source, int pageNo, int pageSize);

	public CmsThirdAccount findById(Long id);
	
	public CmsThirdAccount findByKey(String key);

	public CmsThirdAccount save(CmsThirdAccount bean);

	public CmsThirdAccount updateByUpdater(Updater<CmsThirdAccount> updater);

	public CmsThirdAccount deleteById(Long id);
}