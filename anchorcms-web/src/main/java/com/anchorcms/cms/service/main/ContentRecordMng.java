package com.anchorcms.cms.service.main;


import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentRecord;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;

import java.util.List;


public interface ContentRecordMng {
	public Pagination getPage(int pageNo, int pageSize);
	
	public List<ContentRecord>getListByContentId(Integer contentId);
	
	public ContentRecord record(Content content, CmsUser user, ContentRecord.ContentOperateType operate);

	public ContentRecord findById(Long id);

	public ContentRecord save(ContentRecord bean);

	public ContentRecord update(ContentRecord bean);

	public ContentRecord deleteById(Long id);
	
	public ContentRecord[] deleteByIds(Long[] ids);
}