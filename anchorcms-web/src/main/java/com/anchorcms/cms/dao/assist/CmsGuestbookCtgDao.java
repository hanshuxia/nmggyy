package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsGuestbookCtg;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;


public interface CmsGuestbookCtgDao {
	public List<CmsGuestbookCtg> getList(Integer siteId);

	public CmsGuestbookCtg findById(Integer id);

	public CmsGuestbookCtg save(CmsGuestbookCtg bean);

	public CmsGuestbookCtg updateByUpdater(Updater<CmsGuestbookCtg> updater);

	public CmsGuestbookCtg deleteById(Integer id);
}