package com.anchorcms.cms.dao.assist;


import com.anchorcms.cms.model.assist.CmsScoreGroup;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

public interface CmsScoreGroupDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsScoreGroup findById(Integer id);
	
	public CmsScoreGroup findDefault(Integer siteId);

	public CmsScoreGroup save(CmsScoreGroup bean);

	public CmsScoreGroup updateByUpdater(Updater<CmsScoreGroup> updater);

	public CmsScoreGroup deleteById(Integer id);
}