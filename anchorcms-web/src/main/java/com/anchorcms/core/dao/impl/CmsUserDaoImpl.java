package com.anchorcms.core.dao.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CmsUserDaoImpl extends HibernateBaseDao<CmsUser, Integer> implements CmsUserDao {
	public Pagination getPage(String username, String email, Integer siteId,
							  Integer groupId, Boolean disabled, Boolean admin, Integer rank,
							  String realName, Integer roleId,
							  Boolean allChannel,
							  int pageNo, int pageSize) {
		Finder f = Finder.create("select bean from CmsUser bean join bean.userExtSet ext ");
		if (siteId != null||allChannel!=null) {
			f.append(" join bean.userSites userSite");
		}
		if(roleId!=null){
			f.append(" join bean.roles role ");
		}
		f.append(" where 1=1");
		if(siteId!=null){
			f.append(" and  userSite.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username");
			f.setParam("username", "%" + username + "%");
		}
		if (!StringUtils.isBlank(email)) {
			f.append(" and bean.email like :email");
			f.setParam("email", "%" + email + "%");
		}
		if (groupId != null) {
			f.append(" and bean.group.id=:groupId");
			f.setParam("groupId", groupId);
		}
		if (disabled != null) {
			f.append(" and bean.isDisabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (admin != null) {
			f.append(" and bean.isAdmin=:admin");
			f.setParam("admin", admin);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		if (!StringUtils.isBlank(realName)) {
			f.append(" and ext.realname like :realname");
			f.setParam("realname", "%" + realName + "%");
		}
		if(roleId!=null){
			f.append(" and role.id=:roleId");
			f.setParam("roleId", roleId);
		}
		if (allChannel != null) {
			f.append(" and userSite.isAllChannel=:allChannel");
			f.setParam("allChannel", allChannel);
		}
		//用户有多个站的管理权限需要去重复
		/*
		if(allChannel!=null){
			f.append(" group by bean having count(bean)=1");
		}
		*/
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<CmsUser> getList(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank) {
		Finder f = Finder.create("select bean from CmsUser bean");
		if (siteId != null) {
			f.append(" join bean.userSites userSite");
			f.append(" where userSite.site.siteId=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username");
			f.setParam("username", "%" + username + "%");
		}
		if (!StringUtils.isBlank(email)) {
			f.append(" and bean.email like :email");
			f.setParam("email", "%" + email + "%");
		}
		if (groupId != null) {
			f.append(" and bean.group.id=:groupId");
			f.setParam("groupId", groupId);
		}
		if (disabled != null) {
			f.append(" and bean.isDisabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (admin != null) {
			f.append(" and bean.isAdmin=:admin");
			f.setParam("admin", admin);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" order by bean.userId desc");
		return find(f);
	}

	@SuppressWarnings("unchecked")
	public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
			Boolean disabled, Integer rank) {
		Finder f = Finder.create("select bean from CmsUser");
		f.append(" bean join bean.userSites us");
		f.append(" where us.usersiteId=:siteId");  //siteId改为usersiteId  2016王姝婷
		f.setParam("siteId", siteId);
		if (allChannel != null) {
			f.append(" and us.isAllChannel=:allChannel");
			f.setParam("allChannel", allChannel);
		}
		if (disabled != null) {
			f.append(" and bean.isDisabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" and bean.isSelfAdmin=true");
		f.append(" order by bean.userId asc");
		return find(f);
	}
	
	public Pagination getAdminsByRoleId(Integer roleId, int pageNo, int pageSize){
		Finder f = Finder.create("select bean from CmsUser");
		f.append(" bean join bean.roles role");
		f.append(" where role.id=:roleId");
		f.setParam("roleId", roleId);
		f.append(" and bean.isAdmin=true");
		f.append(" order by bean.id asc");
		return find(f,pageNo,pageSize);
	}

	public CmsUser findById(Integer id) {
		CmsUser entity = get(id);
		return entity;
	}

	public CmsUser findByUsername(String username) {
		return findUniqueByProperty("username", username);
	}

	public int countByUsername(String username) {
		String hql = "select count(*) from CmsUser bean where bean.username=:username";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}
	public int countMemberByUsername(String username) {
		String hql = "select count(*) from CmsUser bean where bean.username=:username and bean.isAdmin=false";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}

	public int countByEmail(String email) {
		String hql = "select count(*) from CmsUser bean where bean.email=:email";
		Query query = getSession().createQuery(hql);
		query.setParameter("email", email);
		return ((Number) query.iterate().next()).intValue();
	}

	public CmsUser save(CmsUser bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsUser update(CmsUser bean) {
		getSession().update(bean);
		return bean;
	}

	public CmsUser deleteById(Integer id) {
		CmsUser entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsUser> getEntityClass() {
		return CmsUser.class;
	}
}