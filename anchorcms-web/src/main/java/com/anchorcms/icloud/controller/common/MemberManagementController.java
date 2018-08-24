package com.anchorcms.icloud.controller.common;

import com.anchorcms.cms.model.assist.CmsWebservice;
import com.anchorcms.cms.service.main.CmsWebserviceMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.common.web.RequestUtils;
import com.anchorcms.core.model.*;
import com.anchorcms.core.service.CmsConfigItemMng;
import com.anchorcms.core.service.CmsGroupMng;
import com.anchorcms.core.service.CmsLogMng;
import com.anchorcms.core.service.CmsUserMng;
import com.anchorcms.core.web.WebErrors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.anchorcms.common.constants.Constants.TPLDIR_COMMON;
import static com.anchorcms.common.page.SimplePage.cpn;
import static com.anchorcms.common.web.ResponseUtils.log;

/**
 * Created by 11239 on 2017/2/16.
 */
@Controller
public class MemberManagementController {

    public static final String LIST_MEMBERLIST = "tpl.memberlist";
    public static final String LIST_MEMBERADD = "tpl.memberadd";
    public static final String LIST_MEMBEREDIT = "tpl.memberedit";

    @RequiresPermissions("member:member_list")
    @RequestMapping("/member/member_list.jspx")
    public String list(String queryUsername, String queryEmail,
                       Integer queryGroupId, Boolean queryDisabled, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        Pagination pagination = manager.getPage(queryUsername, queryEmail,
                null, queryGroupId, queryDisabled, false, null,
                null,null,null,cpn(pageNo),
                CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);

        model.addAttribute("queryUsername", queryUsername);
        model.addAttribute("queryEmail", queryEmail);
        model.addAttribute("queryGroupId", queryGroupId);
        model.addAttribute("queryDisabled", queryDisabled);
        model.addAttribute("groupList", cmsGroupMng.getList());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMON, LIST_MEMBERLIST);
    }

    @RequiresPermissions("member:member_add")
    @RequestMapping("/member/member_add.jspx")
    public String add(ModelMap model,HttpServletRequest request) {
        CmsSite site= CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        List<CmsGroup> groupList = cmsGroupMng.getList();
        List<CmsConfigItem>registerItems=cmsConfigItemMng.getList(site.getConfig().getConfigId(), CmsConfigItem.CATEGORY_REGISTER);
        model.addAttribute("registerItems", registerItems);
        model.addAttribute("groupList", groupList);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMON, LIST_MEMBERADD);
    }

    @RequiresPermissions("member:member_edit")
    @RequestMapping("/member/member_edit.jspx")
    public String edit(Integer id, Integer queryGroupId, Boolean queryDisabled,
                       HttpServletRequest request, ModelMap model) {
        String queryUsername = RequestUtils.getQueryParam(request,
                "queryUsername");
        String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
        CmsSite site=CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        CmsUser user=manager.findById(id);
        List<CmsGroup> groupList = cmsGroupMng.getList();
        List<CmsConfigItem>registerItems=cmsConfigItemMng.getList(site.getConfig().getConfigId(), CmsConfigItem.CATEGORY_REGISTER);
        List<String>userAttrValues=new ArrayList<String>();
        for(CmsConfigItem item:registerItems){
            userAttrValues.add(user.getAttr().get(item.getField()));
        }
        model.addAttribute("queryUsername", queryUsername);
        model.addAttribute("queryEmail", queryEmail);
        model.addAttribute("queryGroupId", queryGroupId);
        model.addAttribute("queryDisabled", queryDisabled);
        model.addAttribute("groupList", groupList);
        model.addAttribute("cmsMember", user);
        model.addAttribute("registerItems", registerItems);
        model.addAttribute("userAttrValues", userAttrValues);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMON, LIST_MEMBEREDIT);
    }

    @RequiresPermissions("member:member_save")
    @RequestMapping("/member/member_save.jspx")
    public String save(CmsUser bean, CmsUserExt ext, String username,
                       String email, String password, Integer groupId, Integer grain,
                       String queryUsername, String queryEmail, Integer queryGroupId,
                       Boolean queryDisabled, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        String ip = RequestUtils.getIpAddr(request);
        Map<String,String> attrs=RequestUtils.getRequestMap(request, "attr_");
        bean = manager.registerMember(username, email, password, ip, groupId,grain,false,ext,attrs);
        cmsWebserviceMng.callWebService("false",username, password, email, ext, CmsWebservice.SERVICE_TYPE_ADD_USER);
        log.info("save CmsMember id={}", bean.getUserId());
        cmsLogMng.operating(request, "cmsMember.log.save", "id=" + bean.getUserId()
                + ";username=" + bean.getUsername());
        return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
                pageNo, request, model);
    }

    @RequiresPermissions("member:member_update")
    @RequestMapping("/member/member_update.jspx")
    public String update(Integer userId, String email, String password,
                         Boolean isDisabled, CmsUserExt ext, Integer groupId,Integer grain,
                         String queryUsername, String queryEmail, Integer queryGroupId,
                         Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
                         ModelMap model) {
        WebErrors errors = validateUpdate(userId, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Map<String,String>attrs=RequestUtils.getRequestMap(request, "attr_");
        CmsUser bean = manager.updateMember(userId, email, password, isDisabled, ext,groupId,grain,attrs);
        cmsWebserviceMng.callWebService("false",bean.getUsername(), password, email, ext,CmsWebservice.SERVICE_TYPE_UPDATE_USER);
        log.info("update CmsMember id={}.", bean.getUserId());
        cmsLogMng.operating(request, "cmsMember.log.update", "id="
                + bean.getUserId() + ";username=" + bean.getUsername());
        return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
                pageNo, request, model);
    }

    @RequiresPermissions("member:member_delete")
    @RequestMapping("/member/member_delete.jspx")
    public String delete(Integer[] ids, Integer queryGroupId,
                         Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
                         ModelMap model) {
        String queryUsername = RequestUtils.getQueryParam(request,
                "queryUsername");
        String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        CmsUser[] beans = manager.deleteByIds(ids);
        for (CmsUser bean : beans) {
            Map<String,String>paramsValues=new HashMap<String, String>();
            paramsValues.put("username", bean.getUsername());
            paramsValues.put("admin", "false");
            cmsWebserviceMng.callWebService(CmsWebservice.SERVICE_TYPE_DELETE_USER, paramsValues);
            log.info("delete CmsMember id={}", bean.getUserId());
            cmsLogMng.operating(request, "cmsMember.log.delete", "id="
                    + bean.getUserId() + ";username=" + bean.getUsername());
        }
        return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
                pageNo, request, model);
    }


    private WebErrors validateSave(CmsUser bean, HttpServletRequest request) {
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
        // TODO 验证是否为管理员，管理员不允许修改。
        return errors;
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if(!errors.ifEmpty(ids, "ids")){
            for (Integer id : ids) {
                vldExist(id, errors);
            }
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        CmsUser entity = manager.findById(id);
        if (errors.ifNotExist(entity, CmsUser.class, id)) {
            return true;
        }
        return false;
    }
    @Resource
    private CmsUserMng manager;
    @Resource
    private CmsLogMng cmsLogMng;
    @Resource
    private CmsGroupMng cmsGroupMng;
    @Resource
    private CmsConfigItemMng cmsConfigItemMng;
    @Resource
    private CmsWebserviceMng cmsWebserviceMng;
}
