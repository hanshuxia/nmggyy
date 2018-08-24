package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsOrigin;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;



public interface CmsOriginDao {
	public Pagination getPage(int pageNo, int pageSize);

	public List<CmsOrigin> getList(String name);

	public CmsOrigin findById(Integer id);

	public CmsOrigin save(CmsOrigin bean);

	public CmsOrigin updateByUpdater(Updater<CmsOrigin> updater);

	public CmsOrigin deleteById(Integer id);
}