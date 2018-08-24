package com.anchorcms.cms.service.assist;


import com.anchorcms.cms.model.assist.CmsScoreGroup;
import com.anchorcms.common.page.Pagination;

public interface CmsScoreGroupMng {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsScoreGroup findById(Integer id);
	
	public CmsScoreGroup findDefault(Integer siteId);

	public CmsScoreGroup save(CmsScoreGroup bean);

	public CmsScoreGroup update(CmsScoreGroup bean);

	public CmsScoreGroup deleteById(Integer id);
	
	public CmsScoreGroup[] deleteByIds(Integer[] ids);
}