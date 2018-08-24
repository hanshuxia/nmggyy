package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.icloud.dao.supplychain.SSpareDetailDao;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareStorage;
import com.anchorcms.icloud.service.supplychain.SSpareDetailMng;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hansx on 2016/12/20.
 * 备品备件业务实现类
 */
@Service
@Transactional
public class SSpareDetailMngImpl implements SSpareDetailMng {

	/**
	 * 根据id获取备品备件信息
	 * @param id
	 * @return
	 */
//	@Transactional(readOnly = true)
	public SSpare findById(String id) {
		SSpare entity = dao.findById(id);
		return entity;
	}

	public void save(SSpareStorage sSpareStorage){
		dao.save(sSpareStorage);
	}

	public void updateInvnetCount(SSpare sSpare){
		dao.updateInvnetCount(sSpare);
	}

	@Resource
	private SSpareDetailDao dao;
}