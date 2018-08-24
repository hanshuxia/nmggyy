package com.anchorcms.cms.service.assist;

import com.anchorcms.cms.model.assist.CmsReceiverMessage;
import com.anchorcms.common.page.Pagination;

import java.util.Date;
import java.util.List;



public interface CmsReceiverMessageMng {
	public Pagination getPage(Integer siteId, Integer sendUserId,
							  Integer receiverUserId, String title, Date sendBeginTime,
							  Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
							  int pageNo, int pageSize);
	
	public List<CmsReceiverMessage> getList(Integer siteId, Integer sendUserId,
											Integer receiverUserId, String title, Date sendBeginTime,
											Date sendEndTime, Boolean status, Integer box, Boolean cacheable);
	
	public CmsReceiverMessage find(Integer messageId, Integer box);

	public CmsReceiverMessage findById(Integer id);

	public CmsReceiverMessage save(CmsReceiverMessage bean);

	public CmsReceiverMessage update(CmsReceiverMessage bean);

	public CmsReceiverMessage deleteById(Integer id);

	public CmsReceiverMessage[] deleteByIds(Integer[] ids);
}