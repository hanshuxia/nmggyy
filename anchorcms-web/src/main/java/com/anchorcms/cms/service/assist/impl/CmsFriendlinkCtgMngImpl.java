package com.anchorcms.cms.service.assist.impl;

import java.util.List;

import com.anchorcms.cms.dao.assist.CmsFriendlinkCtgDao;
import com.anchorcms.cms.model.assist.CmsFriendlinkCtg;
import com.anchorcms.cms.service.assist.CmsFriendlinkCtgMng;
import com.anchorcms.common.hibernate.Updater;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CmsFriendlinkCtgMngImpl implements CmsFriendlinkCtgMng {
	@Transactional(readOnly = true)
	public List<CmsFriendlinkCtg> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	public int countBySiteId(Integer siteId) {
		return dao.countBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public CmsFriendlinkCtg findById(Integer id) {
		CmsFriendlinkCtg entity = dao.findById(id);
		return entity;
	}

	public CmsFriendlinkCtg save(CmsFriendlinkCtg bean) {
		dao.save(bean);
		return bean;
	}

	public CmsFriendlinkCtg update(CmsFriendlinkCtg bean) {
		Updater<CmsFriendlinkCtg> updater = new Updater<CmsFriendlinkCtg>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public void updateFriendlinkCtgs(Integer[] ids, String[] names,
			Integer[] priorities) {
		if (ids == null || ids.length == 0) {
			return;
		}
		CmsFriendlinkCtg ctg;
		for (int i = 0; i < ids.length; i++) {
			ctg = dao.findById(ids[i]);
			ctg.setFriendlinkctgName(names[i]);
			ctg.setPriority(priorities[i]);
		}
	}

	public CmsFriendlinkCtg deleteById(Integer id) {
		CmsFriendlinkCtg bean = dao.deleteById(id);
		return bean;
	}

	public CmsFriendlinkCtg[] deleteByIds(Integer[] ids) {
		CmsFriendlinkCtg[] beans = new CmsFriendlinkCtg[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Resource
	private CmsFriendlinkCtgDao dao;

}