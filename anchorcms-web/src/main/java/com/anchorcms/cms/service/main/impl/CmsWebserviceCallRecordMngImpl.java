package com.anchorcms.cms.service.main.impl;

import java.util.Calendar;

import com.anchorcms.cms.dao.main.CmsWebserviceCallRecordDao;
import com.anchorcms.cms.model.assist.CmsWebserviceCallRecord;
import com.anchorcms.cms.service.assist.CmsWebserviceAuthMng;
import com.anchorcms.cms.service.main.CmsWebserviceCallRecordMng;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CmsWebserviceCallRecordMngImpl implements CmsWebserviceCallRecordMng {
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public CmsWebserviceCallRecord findById(Integer id) {
		CmsWebserviceCallRecord entity = dao.findById(id);
		return entity;
	}
	
	public CmsWebserviceCallRecord save(String clientUsername,String serviceCode){
		CmsWebserviceCallRecord record=new CmsWebserviceCallRecord();
		record.setAuth(bbsWebserviceAuthMng.findByUsername(clientUsername));
		record.setRecordTime(Calendar.getInstance().getTime());
		record.setServiceCode(serviceCode);
		return save(record);
	}

	public CmsWebserviceCallRecord save(CmsWebserviceCallRecord bean) {
		dao.save(bean);
		return bean;
	}

	public CmsWebserviceCallRecord update(CmsWebserviceCallRecord bean) {
		Updater<CmsWebserviceCallRecord> updater = new Updater<CmsWebserviceCallRecord>(bean);
		CmsWebserviceCallRecord entity = dao.updateByUpdater(updater);
		return entity;
	}

	public CmsWebserviceCallRecord deleteById(Integer id) {
		CmsWebserviceCallRecord bean = dao.deleteById(id);
		return bean;
	}
	
	public CmsWebserviceCallRecord[] deleteByIds(Integer[] ids) {
		CmsWebserviceCallRecord[] beans = new CmsWebserviceCallRecord[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Resource
	private CmsWebserviceAuthMng bbsWebserviceAuthMng;
	@Resource
	private CmsWebserviceCallRecordDao dao;
}