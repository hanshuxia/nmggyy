package com.anchorcms.cms.service.assist.impl;

import java.util.List;

import com.anchorcms.cms.dao.assist.CmsAcquisitionHistoryDao;
import com.anchorcms.cms.model.assist.CmsAcquisitionHistory;
import com.anchorcms.cms.service.assist.CmsAcquisitionHistoryMng;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CmsAcquisitionHistoryMngImpl implements CmsAcquisitionHistoryMng {
	@Transactional(readOnly = true)
	public List<CmsAcquisitionHistory> getList(Integer siteId, Integer acquId) {
		return dao.getList(siteId, acquId);
	}
	
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer acquId, Integer pageNo, Integer pageSize) {
		return dao.getPage(siteId, acquId, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public CmsAcquisitionHistory findById(Integer id) {
		CmsAcquisitionHistory entity = dao.findById(id);
		return entity;
	}


	public CmsAcquisitionHistory save(CmsAcquisitionHistory bean) {
		dao.save(bean);
		return bean;
	}

	public CmsAcquisitionHistory update(CmsAcquisitionHistory bean) {
		Updater<CmsAcquisitionHistory> updater = new Updater<CmsAcquisitionHistory>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsAcquisitionHistory deleteById(Integer id) {
		CmsAcquisitionHistory bean = dao.deleteById(id);
		return bean;
	}

	public CmsAcquisitionHistory[] deleteByIds(Integer[] ids) {
		CmsAcquisitionHistory[] beans = new CmsAcquisitionHistory[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public void deleteByAcquisition(Integer acquId) {
		 dao.deleteByAcquisition(acquId);
	}
	
	public Boolean checkExistByProperties(Boolean title, String value){
		return dao.checkExistByProperties(title, value);
	}

	@Resource
	private CmsAcquisitionHistoryDao dao;

}