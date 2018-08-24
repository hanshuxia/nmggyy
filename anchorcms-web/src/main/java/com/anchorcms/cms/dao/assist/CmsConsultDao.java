package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsConsult;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface CmsConsultDao {
	public Pagination getPage(Integer siteId, Integer contentId,
							  Integer greaterThen, Boolean checked, Boolean recommend,
							  boolean desc, int pageNo, int pageSize, boolean cacheable);

	public List<CmsConsult> getList(Integer siteId, Integer contentId,
									Integer parentId, Integer greaterThen, Boolean checked, Boolean recommend,
									boolean desc, int count, boolean cacheable);

	public CmsConsult findById(Integer id);

	public int deleteByContentId(Integer contentId);

	public CmsConsult save(CmsConsult bean);

	public CmsConsult updateByUpdater(Updater<CmsConsult> updater);

	public CmsConsult deleteById(Integer id);

	public Pagination getPageForMember(Integer siteId, Integer contentId, Integer toUserId, Integer fromUserId,
									   Integer greaterThen, Boolean checked, Boolean recommend,
									   boolean desc, int pageNo, int pageSize, boolean cacheable);

	public List<CmsConsult> getListForDel(Integer siteId, Integer userId,
										  Integer consultUserId, String ip);
}