package com.anchorcms.core.service.impl;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.dao.CmsUserExtDao;
import com.anchorcms.core.service.CmsUserExtMng;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.CmsUserExt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional("transactionManager")
public class CmsUserExtMngImpl implements CmsUserExtMng {
	@Transactional(readOnly = true)
	public CmsUserExt findById(Integer userId){
		return dao.findById(userId);
	}
	
	public CmsUserExt save(CmsUserExt ext, CmsUser user) {
		ext.blankToNull();
		ext.setUser(user);
		dao.save(ext);
		return ext;
	}

	public CmsUserExt update(CmsUserExt ext, CmsUser user) {
		CmsUserExt entity = dao.findById(user.getUserId());
		if (entity == null) {
			entity = save(ext, user);
			user.getUserExtSet().add(entity);
			return entity;
		} else {
			Updater<CmsUserExt> updater = new Updater<CmsUserExt>(ext);
		//	updater.include("gender");
		//	updater.include("birthday");
			ext = dao.updateByUpdater(updater);
			ext.blankToNull();
			return ext;
		}
	}

	@Resource
	private CmsUserExtDao dao;
}