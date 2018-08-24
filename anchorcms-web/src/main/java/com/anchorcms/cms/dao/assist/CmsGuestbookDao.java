package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsGuestbook;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface CmsGuestbookDao {
	public Pagination getPage(Integer siteId, Integer ctgId, Integer ctgIds[],
							  Integer userId, Boolean recommend, Boolean checked, boolean desc,
							  boolean cacheable, int pageNo, int pageSize);
	
	public List<CmsGuestbook> getList(Integer siteId, Integer ctgId,
									  Boolean recommend, Boolean checked, boolean desc,
									  boolean cacheable, int first, int max);

	public CmsGuestbook findById(Integer id);

	public CmsGuestbook save(CmsGuestbook bean);

	public CmsGuestbook updateByUpdater(Updater<CmsGuestbook> updater);

	public CmsGuestbook deleteById(Integer id);
}