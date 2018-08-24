package com.anchorcms.cms.service.assist;

import com.anchorcms.cms.model.assist.CmsOrigin;
import com.anchorcms.common.page.Pagination;

import java.util.List;



public interface CmsOriginMng {
	public Pagination getPage(int pageNo, int pageSize);

	public List<CmsOrigin> getList(String name);
	
	public CmsOrigin findById(Integer id);

	public CmsOrigin save(CmsOrigin bean);

	public CmsOrigin update(CmsOrigin bean);

	public CmsOrigin deleteById(Integer id);
	
	public CmsOrigin[] deleteByIds(Integer[] ids);

}