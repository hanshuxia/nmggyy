package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsDirectiveTpl;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;

public interface CmsDirectiveTplDao {
	public Pagination getPage(int pageNo, int pageSize);
	
	public List<CmsDirectiveTpl> getList(int count);

	public CmsDirectiveTpl findById(Integer id);

	public CmsDirectiveTpl save(CmsDirectiveTpl bean);

	public CmsDirectiveTpl updateByUpdater(Updater<CmsDirectiveTpl> updater);

	public CmsDirectiveTpl deleteById(Integer id);
}