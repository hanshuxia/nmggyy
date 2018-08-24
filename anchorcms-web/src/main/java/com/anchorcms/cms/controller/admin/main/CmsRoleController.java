package com.anchorcms.cms.controller.admin.main;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsRole;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.security.CmsAuthorizingRealm;
import com.anchorcms.core.service.CmsLogMng;
import com.anchorcms.core.service.CmsRoleMng;
import com.anchorcms.core.service.CmsUserMng;
import com.anchorcms.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.anchorcms.common.page.SimplePage.cpn;

@Controller
public class CmsRoleController {
	private static final Logger log = LoggerFactory.getLogger(CmsRoleController.class);
	/**
	 * 站点id cookie
	 */
	public static final String SITE_COOKIE = "_site_id_cookie";
	
	@RequiresPermissions("role:v_list")
	@RequestMapping("/role/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		List<CmsRole> list = manager.getList();
		model.addAttribute("list", list);
		return "role/list";
	}
	
	@RequiresPermissions("role:v_tree")
	@RequestMapping("/role/v_tree.do")
	public String tree(HttpServletRequest request, ModelMap model) {
		return "role/tree";
	}

	@RequiresPermissions("role:v_add")
	@RequestMapping("/role/v_add.do")
	public String add(ModelMap model) {
		return "role/add";
	}
	

	@RequiresPermissions("role:v_edit")
	@RequestMapping("/role/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("cmsRole", manager.findById(id));
		return "role/edit";
	}

	@RequiresPermissions("role:o_save")
	@RequestMapping("/role/o_save.do")
	public String save(CmsRole bean, String[] perms,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean, splitPerms(perms));
		log.info("save CmsRole id={}", bean.getRoleId());
		cmsLogMng.operating(request, "cmsRole.log.save", "id=" + bean.getRoleId()
				+ ";name=" + bean.getRoleName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("role:o_update")
	@RequestMapping("/role/o_update.do")
	public String update(CmsRole bean, String[] perms,boolean all,
			HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		WebErrors errors = validateUpdate(bean.getRoleId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean, splitPerms(perms));
		//权限更改 清空用户权限缓存
		if(hasChangePermission(all, perms, bean)){
			Set<CmsUser>admins=bean.getUsers();
			for(CmsUser admin:admins){
				authorizingRealm.removeUserAuthorizationInfoCache(admin.getUsername());
			}
		}
		log.info("update CmsRole id={}.", bean.getRoleId());
		cmsLogMng.operating(request, "cmsRole.log.update", "id=" + bean.getRoleId()
				+ ";name=" + bean.getRoleName());
		return list(request, model);
	}

	@RequiresPermissions("role:o_delete")
	@RequestMapping("/role/o_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsRole[] beans = manager.deleteByIds(ids);
		for (CmsRole bean : beans) {
			log.info("delete CmsRole id={}", bean.getRoleId());
			cmsLogMng.operating(request, "cmsRole.log.delete", "id="
					+ bean.getRoleId() + ";name=" + bean.getRoleName());
		}
		return list(request, model);
	}
	
	@RequiresPermissions("role:v_list_members")
	@RequestMapping("/role/v_list_members.do")
	public String list_members(Integer roleId, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		Pagination pagination = userMng.getAdminsByRoleId(roleId,cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("roleId", roleId);
		return "role/members";
	}


	@RequiresPermissions("role:o_delete_member")
	@RequestMapping("/role/o_delete_member.do")
	public String delete_members(Integer roleId,Integer userIds[], Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsRole role=manager.findById(roleId);
		manager.deleteMembers(role, userIds);
		return list_members(roleId, pageNo, request, model);
	}
	
	private class MyComparator  implements Comparator<String>{
		public int compare(String o1, String o2) {  
	        if(Integer.parseInt(o1)>Integer.parseInt(o2)){  
	            return 1;  
	        }else if (Integer.parseInt(o1)==Integer.parseInt(o2)) {  
	            return 0;  
	        }  
	        return -1;  
	    }
	}
	

	private boolean hasChangePermission(boolean all,String[] perms,CmsRole bean){
		CmsRole role=manager.findById(bean.getRoleId());
		if(!role.getIsSuper().equals(all)){
			return true;
		}
		if(!bean.getPerms().toArray().equals(perms)){
			return true;
		}
		return false;
	}

	private WebErrors validateSave(CmsRole bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsRole entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsRole.class, id)) {
			return true;
		}
		return false;
	}

	private Set<String> splitPerms(String[] perms) {
		Set<String> set = new HashSet<String>();
		if (perms != null) {
			for (String perm : perms) {
				for (String p : StringUtils.split(perm, ',')) {
					if (!StringUtils.isBlank(p)) {
						set.add(p);
					}
				}
			}
		}
		return set;
	}
	
	@Resource
	private CmsLogMng cmsLogMng;
	@Resource
	private CmsRoleMng manager;
	@Resource
	private CmsUserMng userMng;

	@Resource
	private CmsAuthorizingRealm authorizingRealm;
}