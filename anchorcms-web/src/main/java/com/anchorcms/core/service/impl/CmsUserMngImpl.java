package com.anchorcms.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import com.anchorcms.cms.model.main.ChannelCount;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.common.email.EmailSender;
import com.anchorcms.common.email.MessageTemplate;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.service.*;
import com.anchorcms.core.model.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional("transactionManager")
public class CmsUserMngImpl implements CmsUserMng {
	@Transactional(readOnly = true)
	public Pagination getPage(String username, String email, Integer siteId,
							  Integer groupId, Boolean disabled, Boolean admin, Integer rank,
							  String realName, Integer roleId,
							  Boolean allChannel, int pageNo, int pageSize) {
		Pagination page = dao.getPage(username, email, siteId, groupId,
				disabled, admin, rank,realName,roleId, 
				allChannel,pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public List<CmsUser> getList(String username, String email, Integer siteId,
								 Integer groupId, Boolean disabled, Boolean admin, Integer rank) {
		List<CmsUser> list = dao.getList(username, email, siteId, groupId,
				disabled, admin, rank);
		return list;
	}

	@Transactional(readOnly = true)
	public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
			Boolean disabled, Integer rank) {
		return dao.getAdminList(siteId, allChannel, disabled, rank);
	}
	
	@Transactional(readOnly = true)
	public Pagination getAdminsByRoleId(Integer roleId, int pageNo, int pageSize){
		return dao.getAdminsByRoleId(roleId, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public CmsUser findById(Integer id) {
		CmsUser entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public CmsUser findByUsername(String username) {
		CmsUser entity = dao.findByUsername(username);
		return entity;
	}

	public CmsUser registerMember(String username, String email,String password, String ip, Integer groupId, Integer grain, boolean disabled, CmsUserExt userExt, Map<String,String>attr){
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip);
		CmsUser user = new CmsUser();
		user.forMember(unifiedUser);
		user.setAttr(attr);
		user.setIsDisabled(disabled);
		CmsGroup group = null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not found!");
		}
		user.setGroup(group);
		user.init();
		//自动为主帐号
		user.setIsMain("1");
		dao.save(user);
		cmsUserExtMng.save(userExt, user);
		return user;
	}

	
	public CmsUser registerMember(String username, String email,
								  String password, String ip, Integer groupId, boolean disabled, CmsUserExt userExt, Map<String,String>attr,
								  Boolean activation, EmailSender sender, MessageTemplate msgTpl)throws UnsupportedEncodingException, MessagingException{
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip, activation, sender, msgTpl);
		CmsUser user = new CmsUser();
		user.forMember(unifiedUser);
		user.setAttr(attr);
		user.setIsDisabled(disabled);
		CmsGroup group = null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not found!");
		}
		user.setGroup(group);
		user.init();
		dao.save(user);
		cmsUserExtMng.save(userExt, user);
		return user;
	}

	public void updateLoginInfo(Integer userId, String ip,Date loginTime,String sessionId) {
		CmsUser user = findById(userId);
		if (user != null) {
			user.setLoginCount(user.getLoginCount() + 1);
			if(StringUtils.isNotBlank(ip)){
				user.setLastLoginIp(ip);
			}
			if(loginTime!=null){
				user.setLastLoginTime(loginTime);
			}
			user.setSessionId(sessionId);
		}
	}

	public void updateUploadSize(Integer userId, Integer size) {
		CmsUser user = findById(userId);
		user.setUploadTotal(user.getUploadTotal() + size);
		if (user.getUploadDate() != null) {
			if (CmsUser.isToday(user.getUploadDate())) {
				size += user.getUploadSize();
			}
		}
		user.setUploadDate(new java.sql.Date(System.currentTimeMillis()));
		user.setUploadSize(size);
	}
	
	public void updateUser(CmsUser user){
		Updater<CmsUser>updater=new Updater<CmsUser>(user);
		dao.updateByUpdater(updater);
	}

	public boolean isPasswordValid(Integer id, String password) {
		return unifiedUserMng.isPasswordValid(id, password);
	}
	@Transactional(propagation= Propagation.REQUIRES_NEW)
	public void updatePwdEmail(Integer id, String password, String email) {
		CmsUser user = findById(id);
		if (!StringUtils.isBlank(email)) {
			user.setEmail(email);
		} else {
			user.setEmail(null);
		}
		unifiedUserMng.update(id, password, email);
	}

	public CmsUser saveAdmin(String username, String email, String password,
			String ip, boolean viewOnly, boolean isSelfAdmin, int rank,
			Integer groupId,Integer[] roleIds,Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels,
			CmsUserExt userExt) {
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip);
		CmsUser user = new CmsUser();
		user.forAdmin(unifiedUser, viewOnly, isSelfAdmin, rank);
		CmsGroup group = null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not setted!");
		}
		user.setGroup(group);
		user.init();
		dao.save(user);
		cmsUserExtMng.save(userExt, user);
		if (roleIds != null) {
			for (Integer rid : roleIds) {
				user.addToRoles(cmsRoleMng.findById(rid));
			}
		}
		if (channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelMng.findById(cid);
				channel.addToUsers(user);
			}
		}
		if (siteIds != null) {
			CmsSite site;
			for (int i = 0, len = siteIds.length; i < len; i++) {
				site = cmsSiteMng.findById(siteIds[i]);
				cmsUserSiteMng.save(site, user, steps[i], allChannels[i]);
			}
		}
		return user;
	}

	public void addSiteToUser(CmsUser user, CmsSite site, Byte checkStep) {
		cmsUserSiteMng.save(site, user, checkStep, true);
	}

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId, Integer[] roleIds, 
			Integer[] channelIds,
			Integer siteId, Byte step, 
			Boolean allChannel) {
		CmsUser user = updateAdmin(bean, ext, password, 
				groupId,roleIds,channelIds);
		// 更新所属站点
		cmsUserSiteMng.updateByUser(user, siteId, step, allChannel);
		return user;
	}

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId, Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, 
			Boolean[] allChannels) {
		CmsUser user = updateAdmin(bean, ext, password, 
				groupId,roleIds,channelIds);
		// 更新所属站点
		cmsUserSiteMng.updateByUser(user, siteIds, steps,
				allChannels);
		return user;
	}

	private CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer[] roleIds, Integer[] channelIds) {
		Updater<CmsUser> updater = new Updater<CmsUser>(bean);
		updater.include("email");
		CmsUser user = dao.updateByUpdater(updater);
		user.setGroup(cmsGroupMng.findById(groupId));
		cmsUserExtMng.update(ext, user);
		// 更新角色
		user.getRoles().clear();
		if (roleIds != null) {
			for (Integer rid : roleIds) {
				user.addToRoles(cmsRoleMng.findById(rid));
			}
		}
		// 更新栏目权限
		Set<Channel> channels = user.getChannels();
		// 清除
		for (Channel channel : channels) {
			channel.getUsers().remove(user);
		}
		user.getChannels().clear();
		// 添加
		if (channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelMng.findById(cid);
				channel.addToUsers(user);
			}
		}
		unifiedUserMng.update(bean.getUserId(), password, bean.getEmail());
		return user;
	}

	public CmsUser updateMember(Integer id, String email, String password,
			Boolean isDisabled, CmsUserExt ext, Integer groupId,Integer grain,Map<String,String>attr) {
		CmsUser entity = findById(id);
		entity.setEmail(email);
		/*
		if (!StringUtils.isBlank(email)) {
			model.setEmail(email);
		}
		*/
		if (isDisabled != null) {
			entity.setIsDisabled(isDisabled);
		}
		if (groupId != null) {
			entity.setGroup(cmsGroupMng.findById(groupId));
		}
		// 更新属性表
		if (attr != null) {
			Map<String, String> attrOrig = entity.getAttr();
			attrOrig.clear();
			attrOrig.putAll(attr);
		}
		cmsUserExtMng.update(ext, entity);
		unifiedUserMng.update(id, password, email);
		return entity;
	}
	
	public CmsUser updateMember(Integer id, String email, String password,Integer groupId,String realname,String mobile,Boolean sex) {
		CmsUser entity = findById(id);
		CmsUserExt ext =entity.getUserExt();
		if (!StringUtils.isBlank(email)) {
			entity.setEmail(email);
		}
		if (groupId != null) {
			entity.setGroup(cmsGroupMng.findById(groupId));
		}
		if (!StringUtils.isBlank(realname)) {
			ext.setRealname(realname);
		}
		if (!StringUtils.isBlank(mobile)) {
			ext.setMobile(mobile);
		}
		if (sex!=null) {
			ext.setGender(sex);
		}
		cmsUserExtMng.update(ext, entity);
		unifiedUserMng.update(id, password, email);
		return entity;
	}
	
	public CmsUser updateUserConllection(CmsUser user,Integer cid,Integer operate){
		Updater<CmsUser> updater = new Updater<CmsUser>(user);
		user = dao.updateByUpdater(updater);
		if (operate.equals(1)) {
			user.addToCollection(contentMng.findById(cid));
		}// 取消收藏
		else if (operate.equals(0)) {
			user.delFromCollection(contentMng.findById(cid));
		}
		return user;
	}

	public CmsUser deleteById(Integer id) {
		unifiedUserMng.deleteById(id);
		CmsUser bean = dao.deleteById(id);
		//删除收藏信息
		bean.clearCollection();
		//移除栏目权限
		Set<Channel>channels=bean.getChannels();
		for(Channel c:channels){
			c.getUsers().remove(bean);
		}
		return bean;
	}
	//更新个人资料
	public CmsUser update(CmsUser bean) {
		CmsUser entity = dao.update(bean);
		return entity;
	}


	public CmsUser[] deleteByIds(Integer[] ids) {
		CmsUser[] beans = new CmsUser[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public boolean usernameNotExist(String username) {
		return dao.countByUsername(username) <= 0;
	}
	
	public boolean usernameNotExistInMember(String username){
		return dao.countMemberByUsername(username)<= 0;
	}

	public boolean emailNotExist(String email) {
		return dao.countByEmail(email) <= 0;
	}

	/**
	 * @param user
	 * @param username
	 * @param email
	 * @param ip
	 * @param disabled
	 * @param userExt
	 * @param password
	 * @auther lilimin
	 * @descript 子账号注册功能2
	 * @data 2017/1/20
	 */
	public CmsUser registerAdminMember(CmsUser user,String username, String email,String password, String ip, Integer groupId, Integer grain, boolean disabled, CmsUserExt userExt, Map<String,String>attr) {
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip);
		CmsUser newUser = new CmsUser();
		newUser.forMember(unifiedUser);
		newUser.setAttr(attr);
		newUser.setIsDisabled(disabled);
		//保存创建账号的企业信息
		newUser.setCompany(user.getCompany());
		newUser.setGroup(user.getGroup());
		newUser.init();
		dao.save(newUser);
		cmsUserExtMng.save(userExt, newUser);
		return newUser;
	}

	/**
	 * @param user
	 * @param username
	 * @param email
	 * @param password
	 * @param ip
	 * @param groupId
	 * @param disabled
	 * @param userExt
	 * @param attr
	 * @param activation
	 * @param sender
	 * @param msgTpl
	 * @auther lilimin
	 * @descript 子账号注册功能1
	 * @data 2017/1/20
	 */
	public CmsUser registerAdminMember(CmsUser user,String username, String email,
								  String password, String ip, Integer groupId, boolean disabled, CmsUserExt userExt, Map<String,String>attr,
								  Boolean activation, EmailSender sender, MessageTemplate msgTpl)throws UnsupportedEncodingException, MessagingException{
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip, activation, sender, msgTpl);
		CmsUser newNser = new CmsUser();
		newNser.forMember(unifiedUser);
		newNser.setAttr(attr);
		newNser.setIsDisabled(disabled);
		newNser.setCompany(user.getCompany());
		newNser.setGroup(user.getGroup());
		newNser.init();
		dao.save(newNser);
		cmsUserExtMng.save(userExt, newNser);
		return newNser;
	}
	@Resource
	private CmsUserSiteMng cmsUserSiteMng;
	@Resource
	private CmsSiteMng cmsSiteMng;
	@Resource
	private ChannelMng channelMng;
	@Resource
	private CmsRoleMng cmsRoleMng;
	@Resource
	private CmsGroupMng cmsGroupMng;
	@Resource
	private UnifiedUserMng unifiedUserMng;
	@Resource
	private CmsUserExtMng cmsUserExtMng;
	@Resource
	private CmsUserDao dao;
	@Resource
	private ContentMng contentMng;

}