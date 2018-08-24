package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.icloud.service.supplychain.RePairResListService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Created by DELL on 2016/12/26.
 * liuy
 * 维修需求展示与检索
 */
    @Controller
    public class SrepairResListController {
        private static final Logger log = LoggerFactory.getLogger(com.anchorcms.icloud.controller.supplychain.SrepairResListController.class);

        public static final String TPLDIR_SYNERGY = "supplychain";
        public static final String SUPPLYDETAIL = "tpl.supplychainResList";

        @Resource
        RePairResListService service;

        @RequiresPermissions("member:supplychainResList")
        @RequestMapping("/member/supplychainResList.jspx")
        public String list(String repairType, String addrProvince,
                           String addrCity, String releaseId, String releaseDt,
                           Integer pageNo, HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {

            CmsSite site = CmsUtils.getSite(request);
            FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
            // 解决前台传入的中文乱码参数
            if (repairType != null) {
                repairType = java.net.URLDecoder.decode(repairType, "utf-8");
            } else if (addrProvince != null) {
                addrProvince = java.net.URLDecoder.decode(addrProvince, "utf-8");
            } else if (addrCity != null) {
                addrCity = java.net.URLDecoder.decode(addrCity, "utf-8");
            } else if (releaseId != null) {
                releaseId = java.net.URLDecoder.decode(releaseId, "utf-8");
            } else if (releaseDt != null) {
                releaseDt = java.net.URLDecoder.decode(releaseDt, "utf-8");
            }

            Pagination pagination = service.getList(repairType, addrProvince,
                    addrCity, releaseId, releaseDt, cpn(pageNo),
                    CookieUtils.getPageSize(request));
            List paginationList = pagination.getList(); // 必须返回List格式数据
            model.addAttribute("spareList", paginationList);
//            Integer pageCount = pagination.getTotalCount();
//            Integer pageSize = pagination.getPageSize();
//            Integer pageNumber = pagination.getPageNo();
//            Integer totalPages = (pageCount % pageSize) == 0 ? (pageCount / pageSize) : (pageCount / pageSize + 1);
//            model.addAttribute("pageCount", pageCount);
//            model.addAttribute("pageSize", pageSize);
//            model.addAttribute("pageNumber", pageNumber);
//            model.addAttribute("totalPages", totalPages);

//        String totalPagesStr = new String();
//        int totalPagesStrLen = 1;
//        for (int i = 0; i < totalPages; i ++) {
//            totalPagesStr += i + ",";
//        }
//        if (totalPagesStr.indexOf(",") != -1) {
//            totalPagesStrLen += 1;
//        }

//            ArrayList totalPagesList = new ArrayList();
//        for(int i : totalPagesList)
//        if (totalPages != 1) {
//            for (int i = -1; i < totalPages; i ++) {
//                totalPagesList.add(i + 1, i + 1);
//            }
//        }
            model.addAttribute("pagination", pagination);
//        model.addAttribute("arrPageTotalPages", arrPageTotalPages);
//        model.addAttribute("spareName", spareName);
//        model.addAttribute("spareDesc", spareDesc);
//        model.addAttribute("companyType", companyType);
//        model.addAttribute("addrCity", addrCity);
//        model.addAttribute("pagination", pagination);
//        model.addAttribute("pageNo", pageNo);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGY, SUPPLYDETAIL);
        }
}
