package com.anchorcms.icloud.controller.cloudcenter;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterDemandService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.constants.Constants.TPLDIR_CONTENT;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3 0003
 * @Desc
 */
@Controller
public class CloudCenterDemandController {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterDemandController.class);
    public static final String DEMAND_LIST = "tpl.CloudCenterSIcloudDemandList";
    public static final String DEMAND_VIEW = "tpl.CloudCenterSIcloudDemandView";
    public static final String DEMAND_EDIT = "tpl.CloudCenterSIcloudDemandEdit";
    public static final String DEMAND_PREVIEW = "tpl.CloudCenterSIcloudDemandPrview";
    public static final String DEMANDADMIN_VIEW = "tpl.CloudCenterSIcloudDemandAdminView";
    public static final String DEMAND_DETAIL = "tpl.CloudCenterSIcloudDemandDetail";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";

    /**
     * @Author Yhr
     * 查询需求列表
     * @Date 2016/12/23 0023 15:57
     */

    @RequestMapping(value = "/member/cloudcenter_demand_list.jspx")
    public String list(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandId, String demandName,
                       String status, String payType, Integer modelId,
                       Integer queryChannelId, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        String nextUrl = DEMAND_LIST;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        Pagination p = CloudCenterDemandService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(), user.getUserId(), cpn(pageNo), 20, releaseDt, deadlineDt, demandId, demandName, status, payType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("releaseDt", releaseDt);
        model.addAttribute("deadlineDt", deadlineDt);
        model.addAttribute("demandId", demandId);
        model.addAttribute("demandName", demandName);
        model.addAttribute("status", status);
        model.addAttribute("payType", payType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @param demandId 主键id
     * @param request  request对象
     * @param response response对象
     * @param model    model对象
     * @Author jiangshuzhong
     * @Date 2016/1/5
     * @Desc 云需求管理查看 【用户公司一条需求的明细】
     */
    @RequestMapping(value = "/member/cloudcenter_demand_view.jspx")
    public String view(Integer demandId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_VIEW;
        return demandRedirective(demandId, request, response, model, nextUrl);
    }

    /**
     * @param demandId 主键id
     * @param request  request对象
     * @param response response对象
     * @param model    model对象
     * @Author lilimin
     * @Date 2016/1/10
     * @Desc 云需求管理查看 【用户公司一条需求的明细】
     */
    @RequestMapping(value = "/member/cloudcenter_demandAdmin_view.jspx")
    public String viewAdmin(Integer demandId, HttpServletRequest request,
                            HttpServletResponse response, ModelMap model) {
        // String nextUrl = "/cloudcenter_admin_demand_list.jspx";
        String nextUrl = DEMANDADMIN_VIEW;
        return demandRedirective(demandId, request, response, model, nextUrl);
    }

    /**
     * @param demandId 主键id
     * @param request  request对象
     * @param response response对象
     * @param model    model对象
     * @Author jiangshuzhong
     * @Date 2016/1/5
     * @Desc 云需求管理查看 【用户公司一条需求的四级页面预览】
     */
    @RequestMapping(value = "/member/cloudcenter_demand_preview.jspx")
    public String preview(Integer demandId, HttpServletRequest request,
                          HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_PREVIEW;
        return demandRedirective(demandId, request, response, model, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Date 2016/1/10
     * @Desc 云需求展示页--查看详情 【用户公司一条需求的四级页面预览】
     */
    @RequestMapping(value = "/yzyjyzxYxq/cloudcenter_demand_detail.jspx")
    public String detail(Integer demandId, HttpServletRequest request,
                         HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_DETAIL;
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        SIcloudDemand sIcloudDemand = CloudCenterDemandService.byDemandId(demandId);
        String userId = sIcloudDemand.getCreaterId();
        CmsUser cmsuser = userDao.findById(Integer.parseInt(userId));
        String userName = cmsuser.getUsername();
        model.addAttribute("sIcloudDemand", sIcloudDemand);
        model.addAttribute("userName", userName);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("channel", channelMng.findById(115));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CONTENT, nextUrl);
    }

    /***
     * 云需求管理列表--编辑页展示
     *
     * @param model
     * @return
     * @author wanjinyou
     */
    @RequestMapping(value = "/member/cloudcenter_demand_edit.jspx")
    public String edit(Integer demandId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_EDIT;
        CmsUser user = CmsUtils.getUser(request);
        model.addAttribute("user", user);
        return demandRedirective(demandId, request, response, model, nextUrl);
    }

    /***
     * 云需求管理列表--编辑页保存
     *
     * @param model
     * @return
     * @author wanjinyou
     */
    @RequestMapping(value = "/member/cloudcenter_demand_update.jspx")
    public String update(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         String demand_name, String server_type, Integer count, String dealType, String payType, String invoiceType,
                         String freightBorne, String addrProvince, String addrCity, String addrCounty, String addrStreet, String releaseType,
                         String contact, String mobile, String telephone, String email, String fax, String inquiryComp, String createrId, java.sql.Date deadlineDt,
                         java.sql.Date deliverDt, String statusType, String classify_code, String unit, double expect_price, String Status, String DeFlag,String invoiceCompany,
                         String taxRegNo,
                         String registeredAddress,
                         String bankInfo,
                         //cms表相关参数
                         String editor, Integer modelId, String detailDesc, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths,
                         String[] picDescs, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SIcloudDemand sd = new SIcloudDemand();
        sd.setDemandName(demand_name);
        sd.setServerType(server_type);
        sd.setCount(count);
        sd.setDealType(dealType);
        sd.setPayType(payType);
        sd.setInvoiceType(invoiceType);
        sd.setFreightBorne(freightBorne);
        sd.setAddrProvince(addrProvince);
        sd.setAddrCity(addrCity);
        sd.setAddrCounty(addrCounty);
        sd.setAddrStreet(addrStreet);
        sd.setReleaseType(releaseType);
        sd.setContact(contact);
        sd.setMobile(mobile);
        sd.setTelephone(telephone);
        sd.setEmail(email);
        sd.setFax(fax);
        sd.setUnit(unit);
        sd.setClassify_code(classify_code);
        sd.setExpect_price(expect_price);
        sd.setInquiryComp(inquiryComp);
        sd.setDeadlineDt(deadlineDt);
        sd.setDeliverDt(deliverDt);
        sd.setCompany(user.getCompany());
        sd.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
        sd.setStatus(Status);
        sd.setDeFlag(DeFlag);
        sd.setCreateDt(new java.sql.Date(System.currentTimeMillis()));
        sd.setUpdateDt(sd.getReleaseDt());
        sd.setCreaterId(user.getUserId() + "");
        sd.setInvoiceCompany(invoiceCompany);
        sd.setTaxRegNo(taxRegNo);
        sd.setRegisteredAddress(registeredAddress);
        sd.setBankInfo(bankInfo);

        //前往service层保存业务
        CloudCenterDemandService.editSave(id, sd, detailDesc, attachmentPaths, attachmentNames,
                attachmentFilenames, picPaths, picDescs, channelId, user, charge, true);
        //跳转至前台展示页
        String nextUrl = "/member/cloudcenter_demand_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/5 根据demandID获取demand和 content等一些参数放入model，并根据nextUrl跳转的方法
     */
    private String demandRedirective(Integer demandId, HttpServletRequest request,
                                     HttpServletResponse response, ModelMap model, String nextUrl) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SIcloudDemand sIcloudDemand = CloudCenterDemandService.byDemandId(demandId);
        //若不是管理员，需要用户公司 与 需求所属公司 相等
        if (!user.getIsAdmin() && !user.getCompany().getCompanyId().equals(sIcloudDemand.getCompany().getCompanyId())) {
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        Content content = sIcloudDemand.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("sIcloudDemand", sIcloudDemand);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /***
     * 云需求管理列表--删除
     *
     * @param model
     * @return
     * @author wanjinyou
     */
    @RequestMapping(value = "/member/cloudcenter_demand_remove.jspx")
    public String delete(Integer id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //删除云需求管理实体类
        CloudCenterDemandService.deleteById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        //跳转至前台展示页
        String nextUrl = "/member/cloudcenter_demand_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private CloudCenterDemandService CloudCenterDemandService;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private CmsUserDao userDao;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
