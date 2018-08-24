package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsSiteAccessPages;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.Date;

/**
 * @author Tom
 */
public interface CmsSiteAccessPagesDao {

	public CmsSiteAccessPages findAccessPage(String sessionId, Integer pageIndex);
	
	public Pagination findPages(Integer siteId, Integer orderBy, Integer pageNo, Integer pageSize);

	public CmsSiteAccessPages save(CmsSiteAccessPages access);

	public CmsSiteAccessPages updateByUpdater(Updater<CmsSiteAccessPages> updater);

	public void clearByDate(Date date);

}
