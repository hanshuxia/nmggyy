package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsSite;

import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import com.anchorcms.icloud.model.supplychain.SSpareDemandObj;
import com.anchorcms.icloud.service.supplychain.SSpareReleaseAdminsManageService;

import com.anchorcms.icloud.service.supplychain.SSpareReleaseAdminsManageobjService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Created by DELL on 2016/12/29.
 */
    @Controller
    public class SSpareReleaseAdminsManageController {
        private static final Logger log = LoggerFactory.getLogger(com.anchorcms.icloud.controller.supplychain.SSpareReleaseAdminsManageController.class);

        public static final String TPLDIR_SYNERGYA = "supplychain";
        public static final String REPAIRDEMANDLISTA = "tpl.spareAdminDemandList"; // 列表
        public static final String REPAIRDEMANDDETAILLISTA = "tpl.spareAdminDemandDetailList"; // 明细列表
        public static final String REPAIRDEMANDPREVIEWLISTA = "tpl.spareAdminDemandPreviewList"; // 预览列表

        @Resource
        private SSpareReleaseAdminsManageService service;
        @Resource
        private SSpareReleaseAdminsManageobjService serviceObj;
        /**
         * @author liuyang
         * @Date 2017/1/4 15:09
         * @return
         */
        /**
         * @param requestTheme
         * @param pageNo
         * @param request
         * @param model
         * @return
         * @throws UnsupportedEncodingException
         *
         * @Desc 备品备件审核列表展示
         */
        @RequiresPermissions("member:spareAdminDemandList")
        @RequestMapping(value ="/member/spareAdminDemandList.jspx")
        public String list(String statusType,String requestTheme, Date createDt, Date deadlineDt, Integer pageNo, HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {

            CmsSite site = CmsUtils.getSite(request);
            FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
            // 解决前台传入的中文乱码参数
            if (requestTheme != null) {
                requestTheme = java.net.URLDecoder.decode(requestTheme, "utf-8");
            }
            if (statusType==null){
                statusType="2";
            }
            Pagination pagination = service.getList(statusType,requestTheme,createDt,deadlineDt, cpn(pageNo), CookieUtils.getPageSize(request));
            List paginationList = pagination.getList(); // 必须返回List格式数据
            model.addAttribute("spareAdminDemandList", paginationList);
            model.addAttribute("pagination", pagination);
            model.addAttribute("requestTheme", requestTheme);//增加根据求购主题查询条件
            model.addAttribute("statusType", statusType);
//            if (spareType != null) {
//                 spareType = java.net.URLDecoder.decode(spareType, "utf-8");
//            }
//          Pagination pagination = service.getList(requestTheme,spareType,createDt,deadlineDt, cpn(pageNo), CookieUtils.getPageSize(request));
//            List paginationList = pagination.getList(); // 必须返回List格式数据
//            model.addAttribute("spareAdminDemandList", paginationList);
//            model.addAttribute("pagination", pagination);

//            ArrayList totalPagesList = new ArrayList();
 //           for (int i = -1; i < totalPages; i++) {
  //              totalPagesList.add(i + 1, i + 1);
   //         }
   //         model.addAttribute("totalPagesList", totalPagesList);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGYA, REPAIRDEMANDLISTA);
        }

        /**
         * 根据id获取备品备件审核明细信息
         *
         * @param demandId
         * @param request
         * @param response
         * @param model
         * @return
         */
        @RequiresPermissions("member:spareAdminDemandDetailList")
        @RequestMapping("/member/spareAdminDemandDetailList.jspx")
        public String findDetailById(String demandId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
            CmsSite site = CmsUtils.getSite(request);
            FrontUtils.frontData(request, model, site);
            if (demandId != null) {
                SSpareDemand oRepairDemandEntity = service.findDetailAndPreviewByIdDemand(demandId);
                SSpareDemandObj oRepairDemandObjEntity = serviceObj.findDetailAndPreviewByIdDemandObj(demandId);
                model.addAttribute("oRepairDemandEntity", oRepairDemandEntity);
                model.addAttribute("oRepairDemandObjEntity", oRepairDemandObjEntity);
            }
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGYA, REPAIRDEMANDDETAILLISTA);
        }

        /**
         * 根据id获取备品备件预览信息
         *
         * @param demandId
         * @param request
         * @param response
         * @param model
         * @return
         */
        @RequiresPermissions("member:spareAdminDemandPreviewList")
        @RequestMapping("/member/spareAdminDemandPreviewList.jspx")
        public String findPreviewById(String demandId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
            CmsSite site = CmsUtils.getSite(request);
            FrontUtils.frontData(request, model, site);
            if (demandId != null) {
                SSpareDemand oRepairDemandEntity = service.findDetailAndPreviewByIdDemand(demandId);
                SSpareDemandObj oRepairDemandObjEntity = serviceObj.findDetailAndPreviewByIdDemandObj(demandId);
                model.addAttribute("oRepairDemandEntity", oRepairDemandEntity);
                model.addAttribute("oRepairDemandObjEntity", oRepairDemandObjEntity);
            }
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGYA, REPAIRDEMANDPREVIEWLISTA);
        }

        /**
         * 根据id修改备品备件状态（通过、驳回）
         *
         * @param demandId
         * @param request
         * @param response
         * @param model
         * @return
         */
        @RequestMapping("/member/sspareDemandSetState.jspx")
        public String modifyDemandStatById(String demandId, String state,Date releaseDt, String nextUrl,
                                           HttpServletRequest request, HttpServletResponse response, ModelMap model,String flag,String backReason) throws UnsupportedEncodingException {
            CmsSite site = CmsUtils.getSite(request);
            FrontUtils.frontData(request, model, site);
            if(backReason !=null){
                backReason = java.net.URLDecoder.decode(backReason, "utf-8");
            }
            if (demandId != null) {

                service.setRepairDemandStateById(demandId, state,releaseDt,flag,backReason);
//                service.setRepairDemandStateById(demandId, state,flag);
            }
            return FrontUtils.showSuccess(request, model, nextUrl);
        }
    @RequestMapping("/member/sspareDemandModifyState.jspx")
    public String modifyDemandStatById(String demandId, String state,Date releaseDt, String nextUrl,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model,String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if(backReason != null){
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
        if (demandId != null) {
            demandId = java.net.URLDecoder.decode(demandId, "utf-8");


            service.modifyRepairDemandStateById(demandId, state,releaseDt,backReason);
        //    service.modifyRepairDemandStateById(demandId, state);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

        /**
         * 根据id删除备品备件审核信息
         *
         * @param demandId
         * @param request
         * @param response
         * @param model
         * @return
         */
        @RequestMapping("/member/sspareDemandDel.jspx")
        public String delDemandStatById(String demandId, String nextUrl,
                                        HttpServletRequest request, HttpServletResponse response, ModelMap model) {
            CmsSite site = CmsUtils.getSite(request);
            FrontUtils.frontData(request, model, site);
            if (demandId != null) {
                service.delRepairDemandById(demandId);
            }
            return FrontUtils.showSuccess(request, model, nextUrl);
        }

    }
/**
 * @author liuyang
 * @Date 2017/1/4 11:51
 * @return
 */
