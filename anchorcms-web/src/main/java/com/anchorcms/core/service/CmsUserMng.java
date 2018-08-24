package com.anchorcms.core.service;

import com.anchorcms.common.email.EmailSender;
import com.anchorcms.common.email.MessageTemplate;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.CmsUserExt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;



public interface CmsUserMng {
	public Pagination getPage(String username, String email, Integer siteId,
							  Integer groupId, Boolean disabled, Boolean admin, Integer rank,
							  String realName, Integer roleId,
							  Boolean allChannel,
							  int pageNo, int pageSize);

	public List<CmsUser> getList(String username, String email, Integer siteId,
								 Integer groupId, Boolean disabled, Boolean admin, Integer rank);

	public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
									  Boolean disabled, Integer rank);

	public Pagination getAdminsByRoleId(Integer roleId, int pageNo, int pageSize);

	public CmsUser findById(Integer id);

	public CmsUser findByUsername(String username);

	public CmsUser registerMember(String username, String email,
								  String password, String ip, Integer groupId, Integer grain, boolean disabled, CmsUserExt userExt, Map<String, String> attr);

	public CmsUser registerMember(String username, String email,
								  String password, String ip, Integer groupId, boolean disabled, CmsUserExt userExt, Map<String, String> attr, Boolean activation, EmailSender sender, MessageTemplate msgTpl) throws UnsupportedEncodingException, MessagingException;

	public void updateLoginInfo(Integer userId, String ip, Date loginTime, String sessionId);

	public void updateUploadSize(Integer userId, Integer size);

	public void updateUser(CmsUser user);

	public void updatePwdEmail(Integer id, String password, String email);

	public boolean isPasswordValid(Integer id, String password);

	public CmsUser saveAdmin(String username, String email, String password,
							 String ip, boolean viewOnly, boolean isSelfAdmin, int rank,
							 Integer groupId, Integer[] roleIds, Integer[] channelIds,
							 Integer[] siteIds, Byte[] steps, Boolean[] allChannels,
							 CmsUserExt userExt);

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
							   Integer groupId, Integer[] roleIds, Integer[] channelIds,
							   Integer[] siteIds, Byte[] steps, Boolean[] allChannels);

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
							   Integer groupId, Integer[] roleIds, Integer[] channelIds,
							   Integer siteId, Byte step, Boolean allChannel);

	public CmsUser updateMember(Integer id, String email, String password,
								Boolean isDisabled, CmsUserExt ext, Integer groupId, Integer grain, Map<String, String> attr);

	public CmsUser updateMember(Integer id, String email, String password, Integer groupId, String realname, String mobile, Boolean sex);

	public CmsUser updateUserConllection(CmsUser user, Integer cid, Integer operate);

	public void addSiteToUser(CmsUser user, CmsSite site, Byte checkStep);

	public CmsUser deleteById(Integer id);

	public CmsUser[] deleteByIds(Integer[] ids);

	public boolean usernameNotExist(String username);

	public boolean usernameNotExistInMember(String username);

	public boolean emailNotExist(String email);

	/**
	 * @param user
	 * @param username
	 * @param email
	 * @param password
	 * @param ip
	 * @param grain
	 * @param disabled
	 * @param userExt
	 * @param attr
	 * @auther lilimin
	 * @descript 子账号注册功能2
	 * @data 2017/1/20
	 */
	CmsUser registerAdminMember(CmsUser user, String username, String email,
								String password, String ip, Integer groupId, Integer grain, boolean disabled, CmsUserExt userExt, Map<String, String> attr);

	/**
	 * @param user
	 * @param username
	 * @param email
	 * @param password
	 * @param ip
	 * @param sender
	 * @param disabled
	 * @param userExt
	 * @param attr
	 * @auther lilimin
	 * @descript 子账号注册功能1
	 * @data 2017/1/20
	 */
	CmsUser registerAdminMember(CmsUser user, String username, String email,
								String password, String ip, Integer groupId, boolean disabled, CmsUserExt userExt, Map<String, String> attr, Boolean activation, EmailSender sender, MessageTemplate msgTpl) throws UnsupportedEncodingException, MessagingException;
}