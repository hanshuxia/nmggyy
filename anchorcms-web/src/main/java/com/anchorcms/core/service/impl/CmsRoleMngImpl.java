package com.anchorcms.core.service.impl;

import java.util.List;
import java.util.Set;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.dao.CmsRoleDao;
import com.anchorcms.core.model.CmsRole;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.service.CmsRoleMng;
import com.anchorcms.core.service.CmsUserMng;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CmsRoleMngImpl implements CmsRoleMng {
	@Transactional(readOnly = true)
	public List<CmsRole> getList() {
		return dao.getList();
	}

	@Transactional(readOnly = true)
	public CmsRole findById(Integer id) {
		CmsRole entity = dao.findById(id);
		return entity;
	}

	public CmsRole save(CmsRole bean, Set<String> perms) {
		bean.setPerms(perms);
		dao.save(bean);
		return bean;
	}

	public CmsRole update(CmsRole bean, Set<String> perms) {
		Updater<CmsRole> updater = new Updater<CmsRole>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setPerms(perms);
		return bean;
	}

	public CmsRole deleteById(Integer id) {
		CmsRole bean = dao.deleteById(id);
		return bean;
	}

	public CmsRole[] deleteByIds(Integer[] ids) {
		CmsRole[] beans = new CmsRole[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	public void deleteMembers(CmsRole role, Integer[] userIds) {
		Updater<CmsRole> updater = new Updater<CmsRole>(role);
		role = dao.updateByUpdater(updater);
		if (userIds != null) {
			CmsUser user;
			for (Integer uid : userIds) {
				user = userMng.findById(uid);
				role.delFromUsers(user);
			}
		}
	}

	@Resource
	private CmsRoleDao dao;
	@Resource
	private CmsUserMng userMng;
}