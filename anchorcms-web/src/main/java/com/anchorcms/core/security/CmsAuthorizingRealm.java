package com.anchorcms.core.security;

import com.anchorcms.cms.web.CmsThreadVariable;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.UnifiedUser;
import com.anchorcms.core.service.CmsUserMng;
import com.anchorcms.core.service.UnifiedUserMng;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;


/**
 * 自定义DB Realm
 *
 */
public class CmsAuthorizingRealm extends AuthorizingRealm {

	/**
	 * 登录认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		CmsUser user = cmsUserMng.findByUsername(token.getUsername());
		if (user != null) {
			UnifiedUser unifiedUser = unifiedUserMng.findById(user.getUserId());
			return new SimpleAuthenticationInfo(user.getUsername(), unifiedUser.getPassword(), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		CmsUser user = cmsUserMng.findByUsername(username);
		CmsSite site= CmsThreadVariable.getSite();
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		if (user != null) {
			Set<String>viewPermissionSet=new HashSet<String>();
			Set<String> perms = user.getPerms(site.getSiteId(),viewPermissionSet);
			if (!CollectionUtils.isEmpty(perms)) {
				// 权限加入AuthorizationInfo认证对象
				auth.setStringPermissions(perms);
			}
		}
		return auth;
	}

	public void removeUserAuthorizationInfoCache(String username){
		SimplePrincipalCollection pc = new SimplePrincipalCollection();
		pc.add(username, super.getName());
		super.clearCachedAuthorizationInfo(pc);
	}

	@Resource
	protected CmsUserMng cmsUserMng;
	@Resource
	protected UnifiedUserMng unifiedUserMng;
}
