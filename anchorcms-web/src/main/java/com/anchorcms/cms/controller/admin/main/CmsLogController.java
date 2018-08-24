package com.anchorcms.cms.controller.admin.main;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsLog;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.service.CmsLogMng;
import com.anchorcms.core.web.WebErrors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.anchorcms.common.page.SimplePage.cpn;

//import com.jeecms.common.page.Pagination;
//import com.jeecms.common.web.CookieUtils;
//import com.jeecms.core.entity.CmsLog;
//import com.jeecms.core.entity.CmsSite;
//import com.jeecms.core.manager.CmsLogMng;
//import com.jeecms.core.web.WebErrors;
//import com.jeecms.core.web.util.CmsUtils;

@Controller
public class CmsLogController {
    private static final Logger log = LoggerFactory.getLogger(CmsLogController.class);

    @RequiresPermissions("log:v_list_operating")
    @RequestMapping("/log/v_list_operating.do")
    public String listOperating(String queryUsername, String queryTitle,
                                String queryIp, Integer pageNo, HttpServletRequest request,
                                ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        Pagination pagination = manager.getPage(CmsLog.OPERATING, site.getSiteId(),
                queryUsername, queryTitle, queryIp, cpn(pageNo), CookieUtils
                        .getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", pagination.getPageNo());
        model.addAttribute("queryUsername", queryUsername);
        model.addAttribute("queryTitle", queryTitle);
        model.addAttribute("queryIp", queryIp);
        return "log/list_operating";
    }

    @RequiresPermissions("log:v_list_login_success")
    @RequestMapping("/log/v_list_login_success.do")
    public String listLoginSuccess(String queryUsername, String queryTitle,
                                   String queryIp, Integer pageNo, HttpServletRequest request,
                                   ModelMap model) {
        Pagination pagination = manager.getPage(CmsLog.LOGIN_SUCCESS, null,
                queryUsername, queryTitle, queryIp, cpn(pageNo), CookieUtils
                        .getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", pagination.getPageNo());
        model.addAttribute("queryUsername", queryUsername);
        model.addAttribute("queryTitle", queryTitle);
        model.addAttribute("queryIp", queryIp);
        return "log/list_login_success";
    }

    @RequiresPermissions("log:v_list_login_failure")
    @RequestMapping("/log/v_list_login_failure.do")
    public String listLoginFailure(String queryTitle, String queryIp,
                                   Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = manager.getPage(CmsLog.LOGIN_FAILURE, null,
                null, queryTitle, queryIp, cpn(pageNo), CookieUtils
                        .getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", pagination.getPageNo());
        model.addAttribute("queryTitle", queryTitle);
        model.addAttribute("queryIp", queryIp);
        return "log/list_login_failure";
    }

    @RequiresPermissions("log:o_delete_operating")
    @RequestMapping("/log/o_delete_operating.do")
    public String deleteOperating(String queryUsername, String queryTitle,
                                  String queryIp, Integer[] ids, Integer pageNo,
                                  HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        CmsLog[] beans = manager.deleteByIds(ids);
        for (CmsLog bean : beans) {
            log.info("delete CmsLog id={}", bean.getLogId());
        }
        return listOperating(queryUsername, queryTitle, queryIp, pageNo,
                request, model);
    }

    @RequiresPermissions("log:o_delete_operating_batch")
    @RequestMapping("/log/o_delete_operating_batch.do")
    public String deleteOperatingBatch(Integer days,
                                       HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        manager.deleteBatch(CmsLog.OPERATING, site.getSiteId(), days);
        model.addAttribute("message", "global.success");
        return listOperating(null, null, null, 1, request, model);
    }

    @RequiresPermissions("log:o_delete_login_success")
    @RequestMapping("/log/o_delete_login_success.do")
    public String deleteLoginSuccess(String queryUsername, String queryTitle,
                                     String queryIp, Integer[] ids, Integer pageNo,
                                     HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        CmsLog[] beans = manager.deleteByIds(ids);
        for (CmsLog bean : beans) {
            log.info("delete CmsLog id={}", bean.getLogId());
        }
        return listLoginSuccess(queryUsername, queryTitle, queryIp, pageNo,
                request, model);
    }

    @RequiresPermissions("log:o_delete_login_success_batch")
    @RequestMapping("/log/o_delete_login_success_batch.do")
    public String deleteLoginSuccessBatch(Integer days,
                                          HttpServletRequest request, ModelMap model) {
        if (days == null) {
            days = 0;
        }
        manager.deleteBatch(CmsLog.LOGIN_SUCCESS, null, days);
        model.addAttribute("message", "global.success");
        return listLoginSuccess(null, null, null, 1, request, model);
    }

    @RequiresPermissions("log:o_delete_login_failure")
    @RequestMapping("/log/o_delete_login_failure.do")
    public String deleteLoginFailure(String queryTitle, String queryIp,
                                     Integer[] ids, Integer pageNo, HttpServletRequest request,
                                     ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        CmsLog[] beans = manager.deleteByIds(ids);
        for (CmsLog bean : beans) {
            log.info("delete CmsLog id={}", bean.getLogId());
        }
        return listLoginFailure(queryTitle, queryIp, pageNo, request, model);
    }

    @RequiresPermissions("log:o_delete_login_failure_batch")
    @RequestMapping("/log/o_delete_login_failure_batch.do")
    public String deleteLoginFailureBatch(Integer days,
                                          HttpServletRequest request, ModelMap model) {
        if (days == null) {
            days = 0;
        }
        manager.deleteBatch(CmsLog.LOGIN_FAILURE, null, days);
        model.addAttribute("message", "global.success");
        return listLoginFailure(null, null, 1, request, model);
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        CmsSite site = CmsUtils.getSite(request);
        if (errors.ifEmpty(ids, "ids")) {
            return errors;
        }
        for (Integer id : ids) {
            vldExist(id, site.getSiteId(), errors);
        }
        return errors;
    }

    private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        CmsLog entity = manager.findById(id);
        if (errors.ifNotExist(entity, CmsLog.class, id)) {
            return true;
        }
        return false;
    }

    @Resource
    private CmsLogMng manager;
}
