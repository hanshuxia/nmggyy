package com.anchorcms.icloud.controller.front;

import static com.anchorcms.common.constants.Constants.MESSAGE;
import static com.anchorcms.common.constants.Constants.RES_PATH;
import static com.anchorcms.common.constants.Constants.TPLDIR_COMMON;
import static com.anchorcms.common.constants.Constants.TPL_SUFFIX;
import static com.anchorcms.common.web.ProcessTimeFilter.START_TIME;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.RequestUtils;
import com.anchorcms.common.web.mvc.MessageResolver;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;

/**
 * Created by 11239 on 2017/2/20.
 */
@Controller
public class DynamicPageAct {
    /**
     * 信息提示页面
     */
    public static final String MESSAGE_PAGE_COMPANY = "tpl.messagePageCompany";
    /**
     * 用户
     */
    public static final String USER = "user";
    /**
     * 站点
     */
    public static final String SITE = "site";
    /**
     * 部署路径
     */
    public static final String BASE = "base";
    /**
     * 系统资源路径
     */
    public static final String RES_SYS = "resSys";
    /**
     * 模板资源路径
     */
    public static final String RES_TPL = "res";
    /**
     * 手机模板资源路径
     */
    public static final String MOBILE_RES_TPL = "mobileRes";
    /**
     * 页面完整地址
     */
    public static final String LOCATION = "location";
    @RequiresPermissions("member:company")
    @RequestMapping("/member/company.jspx")
    public String company(String queryUsername,
                       HttpServletRequest request, ModelMap model) {
        CmsUser user = CmsUtils.getUser(request);
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        model.addAttribute("company",1);
        if (user.getCompany() == null) {
            return showCompanyMessage(request, model, "请先到会员中心维护企业基本信息", "/member/index.jspx?redirect=company_edit");
        }else{
            return showCompanyMessage(request, model, "您的公司信息还未审核通过，请耐心等待！", "/member/index.jspx");
        }
    }


    public static String showCompanyMessage(HttpServletRequest request,
                                            Map<String, Object> model, String message, String nextUrl) {
        CmsSite site = CmsUtils.getSite(request);
        frontData(request, model, site);
        model.put(MESSAGE, message);
        if (!StringUtils.isBlank(nextUrl)) {
            model.put("nextUrl", nextUrl);
        }
        return getTplPath(request, site.getSolutionPath(), TPLDIR_COMMON,MESSAGE_PAGE_COMPANY);
    }

    /**
     * 为前台模板设置公用数据
     *
     * @param request
     */
    public static void frontData(HttpServletRequest request,
                                 Map<String, Object> map, CmsSite site) {
        CmsUser user = CmsUtils.getUser(request);
        String location = RequestUtils.getLocation(request);
        Long startTime = (Long) request.getAttribute(START_TIME);
        frontData(map, site, user, location, startTime);
    }
    public static void frontData(Map<String, Object> map, CmsSite site,
                                 CmsUser user, String location, Long startTime) {
        if (startTime != null) {
            map.put(START_TIME, startTime);
        }
        if (user != null) {
            map.put(USER, user);
        }
        map.put(SITE, site);
        //2016年11月14日20:23:37 阁楼麻雀：去掉网站根目录设置
        //String ctx = site.getContextPath() == null ? "" : site.getContextPath();
        String ctx = "";
        map.put(BASE, ctx);
        map.put(RES_SYS, ctx + RES_PATH);
        String res = ctx + RES_PATH + "/" + site.getSitePath() + "/"
                + site.getTplSolution();
        String mobileRes = ctx + RES_PATH + "/" + site.getSitePath() + "/"
                + site.getTplMobileSolution();
        // res路径需要去除第一个字符'/'
        map.put(RES_TPL, res.substring(1));
        map.put(MOBILE_RES_TPL, mobileRes.substring(1));
        map.put(LOCATION, location);
    }
    /**
     * 获得模板路径。将对模板文件名称进行本地化处理。
     *
     * @param request
     * @param solution
     *            方案路径
     * @param dir
     *            模板目录。不本地化处理。
     * @param name
     *            模板名称。本地化处理。
     * @return
     */
    public static String getTplPath(HttpServletRequest request,
                                    String solution, String dir, String name) {
        String equipment=(String) request.getAttribute("ua");
        CmsSite site=CmsUtils.getSite(request);
        if(StringUtils.isNotBlank(equipment)&&equipment.equals("mobile")){
            solution=site.getMobileSolutionPath();
        }
        return solution + "/" + dir + "/"
                + MessageResolver.getMessage(request, name) + TPL_SUFFIX;
    }
}
