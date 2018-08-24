package com.anchorcms.cms.service.main;


import com.anchorcms.cms.model.assist.CmsWebserviceCallRecord;
import com.anchorcms.common.page.Pagination;

public interface CmsWebserviceCallRecordMng {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsWebserviceCallRecord findById(Integer id);
	
	public CmsWebserviceCallRecord save(String clientUsername, String serviceCode);

	public CmsWebserviceCallRecord save(CmsWebserviceCallRecord bean);

	public CmsWebserviceCallRecord update(CmsWebserviceCallRecord bean);

	public CmsWebserviceCallRecord deleteById(Integer id);
	
	public CmsWebserviceCallRecord[] deleteByIds(Integer[] ids);
}