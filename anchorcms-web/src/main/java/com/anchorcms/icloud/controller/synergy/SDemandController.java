package com.anchorcms.icloud.controller.synergy;

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
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPOrderObj;
import com.anchorcms.icloud.model.synergy.*;
import com.anchorcms.icloud.service.common.PubCodeService;
import com.anchorcms.icloud.service.synergy.SDemandQuoteService;
import com.anchorcms.icloud.service.synergy.SDemandService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.constants.Constants.TPLDIR_NLCZS;
import static com.anchorcms.common.page.SimplePage.cpn;

@Controller
public class SDemandController {
    private static final Logger log = LoggerFactory.getLogger(SDemandController.class);
    public static final String DEMAND_LIST = "tpl.synergySdemandList";
    public static final String BIGDATA_DEMAND_LIST = "tpl.synergyBigdatademandList";
    public static final String DEMAND_VIEW = "tpl.synergySdemandView";
    public static final String DEMAND_PREVIEW ="tpl.synergySdemandPreView";
    public static final String DEMAND_EDITOR ="tpl.synergySdemandEditor";
    public static final String DEMAND_PREVIEW_XTZZ ="tpl.synergySdemandPreViewXtzz";
    public static final String COMPANY_PREVIEW_XTZZ ="tpl.synergyCompanyPreViewXtzz";
    public static final String COMPANY_PREVIEW_CYRH ="tpl.synergyCompanyPreViewCyrh";
    public static final String ABILITYQUOTELIST ="tpl.synergyAbilityInquiryList";
    public static final String DEMANDADMIN_VIEW = "tpl.synergyAdminDemandView";
    public static final String DEVICEQUOTELIST ="tpl.synergyDeviceInquiryList";
    public static final String INQUIRY_VIEW = "tpl.sdeviceInquiryView";
    public static final String COMPANY_DETAIL_XTZZ ="tpl.xtzz_synergy_company_detail";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    /**
     * @Author Yhr
     * 查询用户所在公司的需求列表
     * @Date 2016/12/23 0023 15:57
     */

    @RequestMapping(value = "/member/synergy_demand_list.jspx")
    public String list(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                       String selectedStatus, String payType,String statusType, Integer modelId,
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
        //需要用户存在某公司下
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        Pagination p = synergyCreateService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId()
                , user.getUserId(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme,selectedStatus,payType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("selectedStatus", selectedStatus);
        model.addAttribute("statusType", statusType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    @RequestMapping(value = "/member/synergy_bigdata_demand_list.jspx")
    public String list2(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                       String selectedStatus, String payType,String statusType, Integer modelId,
                       Integer queryChannelId, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        String nextUrl = BIGDATA_DEMAND_LIST;
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
        //需要用户存在某公司下
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
//        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
//        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
//            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
//            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
//        }
        Pagination p = synergyCreateService.getPageForMember2(queryChannelId, site.getSiteId(), modelId, user.getUserId()
                , user.getUserId(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme,selectedStatus,payType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("selectedStatus", selectedStatus);
        model.addAttribute("statusType", statusType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    /**
     * @Author jiangshuzhong
     * @Date 2016/12/26
     * @param demandId 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 需求管理查看 【用户公司一条需求的明细】
     */
    @RequestMapping(value = "/member/synergy_demand_view.jspx")
    public String view(Integer demandId, Integer quoteId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_VIEW;
        return demandRedirectiveForView(demandId, quoteId, request, false, response, model, nextUrl);
    }
    /**
     * @Author wanjinyou
     * @Date 2017/1/17
     * 管理员--需求管理列表的明细
     */
    @RequestMapping(value = "/member/synergy_demandAdmin_view.jspx")
    public String adminView(Integer demandId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMANDADMIN_VIEW;
        return demandRedirective(demandId,request,false,response,model,nextUrl);
    }
    /**
     * @Author 闫浩芮
     * @Date 2017/1/3 0003 18:26
     * 需求管理编辑 【用户公司一条需求的编辑页面】
     */
    @RequestMapping(value = "/member/synergy_demand_editor.jspx")
    public String editor(Integer demandId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_EDITOR;
        return demandRedirective(demandId,request,true,response,model,nextUrl);
    }

    /**
     * @Author jiangshuzhong
     * @Date 2016/12/26
     * @param demandId 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 需求管理查看 【用户公司一条需求的四级页面预览】
     */
    @RequestMapping(value = "/member/synergy_demand_preview.jspx")
    public String preview(Integer demandId, HttpServletRequest request,
                          HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_PREVIEW;
        return demandRedirective(demandId,request,false,response,model,nextUrl);
    }

    /**
     * @Author jiangshuzhong
     * @Date 2016/12/26
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 需求管理查看 【前台展示页 4级页面】
     */
    @Resource
    private PubCodeService pubCodeService;
    @RequestMapping(value = "/xtzzNlczs/xtzz_synergy_demand_preview.jspx")
    public String xtzz_preview(Integer id, HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_PREVIEW_XTZZ;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        // 没有开启会员功能的代码被注释，reason:【这玩意游客也可以查看，so注释掉】
        SDemand demand= synergyCreateService.byDemandId(id);
        Content content = demand.getContent();
        String userId = demand.getCreaterId();
         CmsUser cmsuser = userDao.findById(Integer.parseInt(userId));
        String userName = cmsuser.getUsername();

        //获取分类信息
        String spare_type = demand.getClassifyType();
        PubCode pc = pubCodeService.findUniqueCode("NLFL",spare_type);
        if (pc.getParentCodeId() == null) {
            pc = pubCodeService.findById(pc.getId());
        } else {
            pc = pubCodeService.findById(pc.getParentCodeId());
        }
        String pk = pc.getKey();
        model.addAttribute("pck", pk);
        model.addAttribute("pc", pc);
        // 获得本站栏目列表
        model.addAttribute("demand",demand);
        model.addAttribute("userName",userName);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("user", user);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(104));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_NLCZS, nextUrl);
    }

    /**
     * @Author lilimin
     * @Date 2016/12/30
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 企业能力查看 ？？？？
     */
    @RequestMapping(value = "/xtzzNlczs/xtzz_synergy_company_preview.jspx")
    public String xtzz_Company(String id, HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) {
        String nextUrl = COMPANY_PREVIEW_XTZZ;
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        SCompany company = synergyCreateService.byCompanyId(id);
        Set<SAbility> list = company.getAbilities();
        Content content = null;
        if((company.getAbilities()).size()>0){
            Iterator<SAbility> it  = list.iterator();
            while(it.hasNext()){
                content= it.next().getContent();
            }
        }
        // Content content = company.getAbilities();
        // 获得本站栏目列表
        model.addAttribute("company",company);
        model.addAttribute("channel",channelMng.findById(105));
        model.addAttribute("site", site);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(103));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_NLCZS, nextUrl);
    }


    /**
     * @Author 苏和
     * @Date 2018/5/3
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 产业融合企业黄页
     */
    @RequestMapping(value = "/xtzzNlczs/cyrh_company_preview.jspx")
    public String cyrh_Company(String id, HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) {
        String nextUrl = COMPANY_PREVIEW_CYRH;
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        SCompany company = synergyCreateService.byCompanyId(id);
        Set<SAbility> list = company.getAbilities();
        Content content = null;
        if((company.getAbilities()).size()>0){
            Iterator<SAbility> it  = list.iterator();
            while(it.hasNext()){
                content= it.next().getContent();
            }
        }
        // Content content = company.getAbilities();
        // 获得本站栏目列表
        model.addAttribute("company",company);
        model.addAttribute("channel",channelMng.findById(105));
        model.addAttribute("site", site);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(103));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_NLCZS, nextUrl);
    }

/**
 * @author liuyang
 * @Date 2017/7/6 9:06
 * @return  企业审核明细
 */
@RequestMapping(value = "/xtzzNlczs/xtzz_synergy_company_detail.jspx")
public String xtzz_CompanyDetail(String id, HttpServletRequest request,
                           HttpServletResponse response, ModelMap model) {
    String nextUrl = COMPANY_DETAIL_XTZZ;
    CmsSite site = CmsUtils.getSite(request);
    FrontUtils.frontData(request, model, site);
    MemberConfig mcfg = site.getConfig().getMemberConfig();
    // 没有开启会员功能
    if (!mcfg.isMemberOn()) {
        return FrontUtils.showMessage(request, model, "member.memberClose");
    }
    SCompany company = synergyCreateService.byCompanyId(id);
    Set<SAbility> list = company.getAbilities();
    Content content = null;
    if((company.getAbilities()).size()>0){
        Iterator<SAbility> it  = list.iterator();
        while(it.hasNext()){
            content= it.next().getContent();
        }
    }
    // Content content = company.getAbilities();
    // 获得本站栏目列表
    model.addAttribute("company",company);
    model.addAttribute("channel",channelMng.findById(105));
    model.addAttribute("site", site);
    model.addAttribute("sessionId",request.getSession().getId());
    model.addAttribute("channel",channelMng.findById(103));
    return FrontUtils.getTplPath(request, site.getSolutionPath(),
            TPLDIR_NLCZS, nextUrl);
}
    /**
     * @Author 闫浩芮
     * 更新需求的statusType
     * @Date 2016/12/27 0027 9:55
     */
    @RequestMapping(value = "/member/synergy_demand_update.jspx")
    public String update(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                         String selectedStatus, String payType,String a, Integer modelId,
                         Integer queryChannelId, Integer pageNo,
                         HttpServletRequest request, ModelMap model) {
        String nextUrl = "/member/synergy_demand_list.jspx";
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
        //需要 用户公司 与 需求所属公司 相等
        SDemand demand= synergyCreateService.byDemandId(demandId);
        if(user.getCompany().getCompanyId() != demand.getCompanyId()){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        synergyCreateService.update(demandId,a);
        Pagination p = synergyCreateService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId()
                , user.getUserId(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme, selectedStatus,payType,a);
        model.addAttribute("pagination", p);
        model.addAttribute("statusType",a);
        model.addAttribute("selectedStatus",selectedStatus);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author 闫浩芮
     * 删除需求信息
     * @Date 2016/12/29 0029 11:08
     */
    @RequestMapping(value = "/member/synergy_demand_delete.jspx")
    public String delete(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                         String status, String payType,String a, Integer modelId,
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
        synergyCreateService.delete(demandId);//删除需求表数据
        Pagination p = synergyCreateService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId()
                , user.getUserId(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme, status,payType,a);
        model.addAttribute("pagination", p);
        model.addAttribute("statusType",a);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    /**
     * @Author 闫浩芮
     * 查询需求方对能力方进行能力询价的列表
     * @Date 2017/1/9 0009 10:16
     */
    @RequestMapping(value = "/member/synergy_demand_abilityInquiryList.jspx")
    public String abilityQuoteList(Integer canshu, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                   String selectedStatus, String payType,String statusType, Integer modelId,
                                   Integer queryChannelId, Integer pageNo,
                                   HttpServletRequest request, ModelMap model) {
        String nextUrl = ABILITYQUOTELIST;
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
        //需要用户存在某公司下
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        Pagination p = synergyCreateService.getPageForMember(canshu, site.getSiteId(), modelId, user.getUserId(),
                user.getUserId(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme,selectedStatus,payType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("selectedStatus", selectedStatus);
        model.addAttribute("statusType", statusType);
        model.addAttribute("payType", payType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/5 根据demandID获取demand和 content等一些参数放入model，并根据nextUrl跳转的方法
     * isEdit: 若为编辑，需要加一个权限判断
     */
    private String demandRedirective(Integer demandId, HttpServletRequest request, boolean isEdit,
                                     HttpServletResponse response, ModelMap model, String nextUrl){
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
        SDemand demand= synergyCreateService.byDemandId(demandId);
        //若要编辑，则需要用户公司 与 需求所属公司 相等
        if(isEdit && user.getCompany().getCompanyId() != demand.getCompanyId()){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        Content content = demand.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("demand",demand);
        model.addAttribute("demand",demand);
        model.addAttribute("user",user);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @author: zhouyq
     * @desciption 2017/3/16 根据demandID获取demand和 content等一些参数放入model，并根据nextUrl跳转的方法
     */
    private String demandRedirectiveForView(Integer demandId, Integer quoteId, HttpServletRequest request, boolean isEdit,
                                     HttpServletResponse response, ModelMap model, String nextUrl){
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
        SDemand demand= synergyCreateService.byDemandId(demandId);
        //若要编辑，则需要用户公司 与 需求所属公司 相等
        if(isEdit && user.getCompany().getCompanyId() != demand.getCompanyId()){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        Content content = demand.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("demand",demand);
        model.addAttribute("quoteId",quoteId);
        model.addAttribute("user",user);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
/**
 * @author liuyang
 * @Date 2017/6/2 14:23
 * @return
 */
    @RequestMapping(value = "/member/synergy_demand_deviceInquiryList.jspx")
    public String deviceQuoteList(Integer canshu, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                   String selectedStatus, String payType,String statusType, Integer modelId,
                                   Integer queryChannelId, Integer pageNo,
                                   HttpServletRequest request, ModelMap model) {
        String nextUrl = DEVICEQUOTELIST;
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
        //需要用户存在某公司下
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        Pagination p = synergyCreateService.getDevicePage(canshu, site.getSiteId(), modelId, user.getUserId(),
                user.getUserId(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme,selectedStatus,payType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("selectedStatus", selectedStatus);
        model.addAttribute("statusType", statusType);
        model.addAttribute("payType", payType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
/**
 * @author liuyang
 * @Date 2017/6/5 11:47
 * @return 设备询价管理 下单、关闭
 */
@RequestMapping(value = "/member/device_inquiry_deviceOrderORclose.jspx")
public String deviceOrderORclose(Integer inquiryId, Integer isOrder, HttpServletRequest request, ModelMap model){
    CmsSite site = CmsUtils.getSite(request);
    CmsUser user = CmsUtils.getUser(request);
    boolean isOrderBoolean;
    if (user == null) {
        return FrontUtils.showLogin(request, model, site);
    }
    //用户没有关联公司的话不能操作
    if(user.getCompany() == null){
        return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
    }
    //判断企业资质
    SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
    if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
        String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
        return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
    }
    //判断一下Id
    if (inquiryId == null || isOrder == null) {
        return FrontUtils.showMessage(request, model, "error.needParams");
    }
    SDeviceInquiry aInq = SAbilityInquiryService.byDeviceInquiryId(inquiryId);
    //不能操作非本公司的
    if(!user.getCompany().getCompanyId().equals(aInq.getCompanyId())){
        return FrontUtils.showMessage(request, model, "error.noPermissionsView");
    }
    if(isOrder==1){
        isOrderBoolean=true;
    }else if(isOrder==0){
        isOrderBoolean=false;
    }else {
        return FrontUtils.showMessage(request, model, "error.exceptionParams");
    }
    SAbilityInquiryService.deviceOrderOrClose(inquiryId,isOrderBoolean);
    if(isOrder==1){
        SDeviceInquiry inquiry=SAbilityInquiryService.byDeviceInquiryId(inquiryId);
        SCompanyDevice ablity = inquiry.getScompanyDevice();
        SCompany company = ablity.getCompany();
//        List<SDemandQuoteObj> quoteObj = sDemandQuoteService.byDemandQuoteObjId(demandQuoteId);
        SPOrder buy = new SPOrder();
        SPOrderObj o = new SPOrderObj();
//        buy.setOrderId(quote.getDemandQuoteId());
        o.setObjName(ablity.getDeviceName());
        o.setObjNum(inquiry.getDemandCount());
        o.setObjPrice(inquiry.getQuotePrice());
        buy.setIsDevice("1");
        buy.setSupplyName(ablity.getDeviceName());
        buy.setSupplycompany(ablity.getCompany());
        buy.setSupplyMobile(ablity.getCreaterId());
        buy.setSupplyContact(ablity.getContact());
        buy.setSupplyMobile(ablity.getMobile());
        buy.setBuycompany(inquiry.getCompany());
        buy.setBuyMobile(inquiry.getMobile());
        buy.setBuyContact(inquiry.getContact());
        buy.setOperator(inquiry.getOperatorId());
        buy.setOrderDt(new java.sql.Date(System.currentTimeMillis()));
        buy.setSenderPostCode(company.getPostCode());
        buy.setSenderProv(company.getAddrProvince());
        buy.setSenderCity(company.getAddrCity());
        buy.setSenderArea(company.getAddrCounty());
        buy.setSenderAddress(company.getAddrStreet());
        buy.setReceiverPostCode(inquiry.getPostCode());
        buy.setReceiverProv(inquiry.getAddrProvince());
        buy.setReceiverCity(inquiry.getAddrCity());
        buy.setReceiverArea(inquiry.getAddrCounty());
        buy.setReceiverAddress(inquiry.getAddrStreet());
//        buy.setReceiverAddress(inquiry.getFreightBorne());
        buy.setNum(inquiry.getDemandCount());
        List<SPOrderObj> li = new ArrayList<SPOrderObj>();li.add(o);
        buy.setSOrderObjList(li);
        //buy.setPayerID(payerID);
        //buy.setpayerName;
        //订单号
        String guId = null;
        try {
            guId = GUIDGenerator.genGUID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String guidStart = "HTYWHL";
        String guidMIddle = guId.substring(0, 14);
        String guidEnd = guId.substring(guId.length()-6, guId.length());
        guId =  guidStart + guidMIddle + guidEnd;
        buy.setOrderId(guId);

        String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
        o.setOrderObjId(uid);
        buy.setPrice(buy.getSPOrderObjPrice());
        buy.setState("0");
        sDemandQuoteService.saveOrder(buy);
    }

    String nextUrl = "/member/synergy_demand_deviceInquiryList.jspx?canshu=1";
    return FrontUtils.showSuccess(request, model, nextUrl);
}
    /**
     * @author liuyang
     * @Date 2017/6/6 11:12
     * @return 询价明细
     */
    @RequestMapping(value = "/member/s_device_inquiry_view.jspx")
    public String view(Integer inquiryId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = INQUIRY_VIEW;
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
        SDeviceInquiry inquiry = SAbilityInquiryService.byDeviceInquiryId(inquiryId);
        Content content = inquiry.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("inquiry",inquiry);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    @Resource
    protected ContentMng contentMng;
    @Resource
    private SDemandService synergyCreateService;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private CmsUserDao userDao;
    @Resource
    private SDemandQuoteService sDemandQuoteService;
    @Resource
    private com.anchorcms.icloud.service.synergy.SAbilityInquiryService SAbilityInquiryService;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
