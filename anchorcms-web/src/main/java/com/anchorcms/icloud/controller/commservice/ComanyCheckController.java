package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.service.commservice.SCompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Hansx on 2017/2/17 0017.
 * 企业审核状态修改
 */
@Controller
public class ComanyCheckController {

    /**
     * @return 根据企业id修改企业推荐状态
     * @author hansx
     * @Date 2017/2/17 0017 下午 3:41
     */
    @RequestMapping(value = "/member/company_check.jspx")
    public String getRepairDemandList(String id, String nextUrl, HttpServletRequest request,  Map<String, Object> model,String status) {


        if(status!=null&&!status.equals("")){
            nextUrl+="?status="+status;
        }
        if (id != null && !id.equals("")) {
            CmsSite site = CmsUtils.getSite(request);
            SCompany company = sCompanyService.findById(id);
            if (company != null) {
                sCompanyService.updateById(id, company.getIsRecommend());
                return FrontUtils.showSuccess(request, model, nextUrl);
            }
        }
        return FrontUtils.showBaseMessage(request, model, "not find message", nextUrl);

    }
    @RequestMapping(value = "/member/company_revokeMany.jspx")
    public String revokeCompanyList(String ids, String nextUrl, HttpServletRequest request,  Map<String, Object> model) {
            CmsSite site = CmsUtils.getSite(request);
            CmsUser user = CmsUtils.getUser(request);
            FrontUtils.frontData(request, model, site);
            if (user == null) {
                return FrontUtils.showLogin(request, model, site);
            }
            //非管理员不能修改
            if (!user.getIsAdmin()) {
                return FrontUtils.showMessage(request, model, "error.noPermission");
            }
            sCompanyService.revoke(ids);
            return FrontUtils.showSuccess(request, model, nextUrl);

    }
    @RequestMapping(value = "/member/company_recommendMany.jspx")
    public String recommendCompanyList(String ids, String nextUrl, HttpServletRequest request,  Map<String, Object> model) {
            CmsSite site = CmsUtils.getSite(request);
            CmsUser user = CmsUtils.getUser(request);
            FrontUtils.frontData(request, model, site);
            if (user == null) {
                return FrontUtils.showLogin(request, model, site);
            }
            //非管理员不能修改
            if (!user.getIsAdmin()) {
                return FrontUtils.showMessage(request, model, "error.noPermission");
            }
            sCompanyService.recommendByIds(ids);
            return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @return 根据状态驳回，退回公司（审核公司）
     * @author hansx
     * @Date 2017/2/17 0017 下午 3:41
     */
    @RequestMapping(value = "/member/company_back.jspx")
    public String setStatus(String id, String nextUrl, HttpServletRequest request,  Map<String, Object> model,String status,String backReason,String relstatus) {


        if(status!=null&&!status.equals("")){
            nextUrl+="?status="+status;
        }
        if (id != null && !id.equals("")) {
            CmsSite site = CmsUtils.getSite(request);
            SCompany company = sCompanyService.findById(id);
            company.setStatus(relstatus);
            company.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
            company.setBackReason(backReason);
            if (company != null) {
                sCompanyService.Update(company);
                return FrontUtils.showSuccess(request, model, nextUrl);
            }
        }
        return FrontUtils.showBaseMessage(request, model, "not find message", nextUrl);

    }

    /**
     * @return 根据批量状态驳回，退回公司（审核公司）
     * @author hansx
     * @Date 2017/2/17 0017 下午 3:41
     */
    @RequestMapping(value = "/member/company_revokeALL.jspx")
    public String revokeALL(String ids, String nextUrl, HttpServletRequest request,  Map<String, Object> model,String backReason,String relstatus) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if(backReason!=null){
            try {
                backReason=java.net.URLDecoder.decode(backReason, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        sCompanyService.revokeAll(ids,backReason,relstatus);
        return FrontUtils.showSuccess(request, model, nextUrl);

    }


    @Resource
    private SCompanyService sCompanyService;
}