package com.anchorcms.cms.service.assist.impl;

import java.util.Date;

import com.anchorcms.cms.dao.assist.CmsSiteAccessPagesDao;
import com.anchorcms.cms.model.assist.CmsSiteAccessPages;
import com.anchorcms.cms.service.assist.CmsSiteAccessPagesMng;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Tom
 */
@Service
@Transactional
public class CmsSiteAccessPagesMngImpl implements CmsSiteAccessPagesMng {

	public CmsSiteAccessPages findAccessPage(String sessionId, Integer pageIndex) {
		return dao.findAccessPage(sessionId,pageIndex);
	}
	
	public Pagination findPages(Integer siteId, Integer orderBy, Integer pageNo, Integer pageSize){
		return dao.findPages(siteId, orderBy,pageNo,pageSize);
	}

	public void clearByDate(Date date) {
		 dao.clearByDate(date);
	}
	
	public CmsSiteAccessPages save(CmsSiteAccessPages access) {
		return dao.save(access);
	}
	
	public CmsSiteAccessPages update(CmsSiteAccessPages access){
		Updater<CmsSiteAccessPages> updater = new Updater<CmsSiteAccessPages>(access);
		access = dao.updateByUpdater(updater);
		return access;
	}
	
	@Autowired
	private CmsSiteAccessPagesDao dao;

}
