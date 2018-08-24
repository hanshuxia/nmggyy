package com.anchorcms.cms.dao.main;


import com.anchorcms.cms.model.main.ContentRecord;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface ContentRecordDao {
	public Pagination getPage(int pageNo, int pageSize);

	public ContentRecord findById(Long id);

	public ContentRecord save(ContentRecord bean);

	public ContentRecord updateByUpdater(Updater<ContentRecord> updater);

	public ContentRecord deleteById(Long id);

	public List<ContentRecord> getListByContentId(Integer contentId);
}