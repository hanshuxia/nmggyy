package com.anchorcms.cms.dao.main;

import com.anchorcms.cms.model.assist.CmsWebserviceCallRecord;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

public interface CmsWebserviceCallRecordDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsWebserviceCallRecord findById(Integer id);

	public CmsWebserviceCallRecord save(CmsWebserviceCallRecord bean);

	public CmsWebserviceCallRecord updateByUpdater(Updater<CmsWebserviceCallRecord> updater);

	public CmsWebserviceCallRecord deleteById(Integer id);
}