package com.anchorcms.cms.service.assist;

import com.anchorcms.cms.model.assist.CmsTask;
import com.anchorcms.common.page.Pagination;

import java.util.List;
import java.util.Map;


public interface CmsTaskMng {
	public Pagination getPage(Integer siteId, int pageNo, int pageSize);

	public List<CmsTask> getList();
	
	public CmsTask findById(Integer id);

	public CmsTask save(CmsTask bean);

	public CmsTask update(CmsTask bean, Map<String, String> attr);

	public CmsTask deleteById(Integer id);
	
	public CmsTask[] deleteByIds(Integer[] ids);

	public String getCronExpressionFromDB(Integer taskId);
}