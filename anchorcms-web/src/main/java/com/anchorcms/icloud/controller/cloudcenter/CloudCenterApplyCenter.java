package com.anchorcms.icloud.controller.cloudcenter;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;
import com.anchorcms.icloud.model.cloudcenter.SIcloudPolicy;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterApplyService;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPolicyService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
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
import java.sql.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/711:06
 */
@Controller
public class CloudCenterApplyCenter {

    private static final Logger log = LoggerFactory.getLogger(CloudCenterCreateContriller.class);
    public static final String CLOUNDCEMTER_ADD = "tpl.cloudCenerApply_add";
    public static final String SOFTWARE_LIST = "tpl.cloudCenterApply_list";
    public static final String APPLY_EDIT = "tpl.cloudCenterApply_edit";
    public static final String COMPANY_APTITUDE_ADD = "/member/index.jspx";
    public static final String COMPANY_APTITUDE_ADD2 = "/member/company_edit.jspx";
    /**
     * @Author lilimin
     * @Date 2016/12/19 13:06
     * @Desc 政策申请
     */
    @RequiresPermissions("member:cloudCenter_apply_add")
    @RequestMapping("/member/cloudCenter_apply_add.jspx")
    public String add(Integer id, HttpServletRequest request, HttpServletResponse response,
                      ModelMap model) {
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
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        SIcloudPolicy policy = cloudCenterPolicyService.byPolicyId(id);
        String name = policy.getPolicyName();
        model.addAttribute("policy", policy);
        model.addAttribute("name", name);
        model.addAttribute("site", site);
        //政策Id
        model.addAttribute("id", id);
        model.addAttribute("channel", channelMng.findById(100));
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, CLOUNDCEMTER_ADD);
    }

    /**
     * @author: lilimin
     * @desciption 扶持政策保存controller
     */
    @RequestMapping(value = "/member/cloundCenter_apply_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String policyName,
                       Date releaseDt,
                       String policyLevel,
                       String contact,
                       String mobile,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       String isSupport,
                       String applyReason,
                       //cms表相关参数
                       String editor,
                       Integer id,//扶持政策的Id\
                       Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SIcloudPolicy policy = cloudCenterPolicyService.byPolicyId(id);
        SIcloudApply sd = new SIcloudApply();
        sd.setPolicyName(policyName);
        sd.setPolicyLevel(policyLevel);
        sd.setAddrProvince(addrProvince);
        sd.setAddrCity(addrCity);
        sd.setAddrCounty(addrCounty);
        sd.setAddrStreet(addrStreet);
        sd.setContact(contact);
        sd.setApplyReason(applyReason);
        sd.setMobile(mobile);
        sd.setUser(user);
        sd.setPolicy(policy);
        sd.setState("0");
        //默认值set
        sd.setDeFlag("1");
        sd.setCreateDt(new Date(System.currentTimeMillis()));

        c.setSite(site);
        CmsModel defaultModel = cmsModelMng.getDefModel();
        if (modelId != null) {
            CmsModel m = cmsModelMng.findById(modelId);
            if (m != null) {
                c.setModel(m);
            } else {
                c.setModel(defaultModel);
            }
        } else {
            c.setModel(defaultModel);
        }
        ContentExt ext = new ContentExt();
        ext.setTitle(policyName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("云计算政策申请");
        ContentTxt t = new ContentTxt();
        t.setTxt(editor);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        c = cloudCenterApplyService.save(sd, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs, channelId, typeId, user, charge, true);
        //跳转至需求管理列表
        String nextUrl = "/yzyjyzxYjszc/index.jhtml";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author: lilimin
     * @desciption 扶持政策列表显示
     */
    @RequestMapping("/member/cloudcenter_policyApply_list.jspx")
    public String list(HttpServletRequest request, ModelMap model, Integer pageNo) {
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
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD2);
        }
        Pagination p = cloudCenterApplyService.getSoftwarePage(site.getSiteId(), user, cpn(pageNo),
                20);
        model.addAttribute("pagination", p);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SOFTWARE_LIST);
    }

    /**
     * @param id
     * @param request
     * @param response
     * @param model
     * @return
     * @auther lilimin
     * 政策申请修改
     */
    @RequestMapping(value = "/member/cloudCenter_apply_edit.jspx")
    public String edit(Integer id, HttpServletRequest request, String nextUrl,
                       HttpServletResponse response, ModelMap model) {
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
        SIcloudApply apply = cloudCenterApplyService.byApplyId(id);
        SIcloudPolicy policy = apply.getPolicy();
        Content content = apply.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("policy", policy);
        model.addAttribute("applyId", id);//有些多余
        model.addAttribute("apply", apply);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, APPLY_EDIT);
    }

    /**
     * @author: lilimin
     * @desciption 扶持政策保存controller
     */
    @RequestMapping(value = "/member/cloundCenter_apply_update.jspx")
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         String policyName,
                         Date releaseDt,
                         String policyLevel,
                         String contact,
                         String mobile,
                         String addrProvince,
                         String addrCity,
                         String addrCounty,
                         String addrStreet,
                         String isSupport,
                         String applyReason,
                         //cms表相关参数
                         String editor,
                         Integer applyId,//
                         Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                         String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SIcloudApply sd = cloudCenterApplyService.byApplyId(applyId);
        sd.setPolicyName(policyName);
        sd.setPolicyLevel(policyLevel);
        sd.setAddrProvince(addrProvince);
        sd.setAddrCity(addrCity);
        sd.setAddrCounty(addrCounty);
        sd.setAddrStreet(addrStreet);
        sd.setContact(contact);
        sd.setApplyReason(applyReason);
        sd.setMobile(mobile);
        sd.setUser(user);
        sd.setState("0");
        //默认值set
        sd.setDeFlag("1");
        sd.setCreateDt(new Date(System.currentTimeMillis()));
        cloudCenterApplyService.update(applyId, sd, editor, attachmentPaths, attachmentNames,
                attachmentFilenames, picPaths, picDescs, channelId, user, charge, true);

        String nextUrl = "/member/cloudcenter_policyApply_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    public static final String POLICY_FEEDBACK_LIST = "tpl.policyFeedbackList";
    public static final String APPLY_VIEW = "tpl.cloudCenterApplyView";
    public static final String POLICY_FEEDBACK_VIEW = "tpl.policyFeedbackView";


    /**
     * @param request
     * @param model
     * @param pageNo
     * @param startDate   查询创建时间——开始时间
     * @param endDate     查询创建时间——结束时间
     * @param applierName 申请人
     * @param policyLevel 政策层别
     * @param policyName  政策名称
     * @param policyCode  政策代码
     * @param state       审批进度
     * @param status
     * @author ly
     * @date 2017/1/7 11:39
     * @desc 政策申请反馈列表
     **/
    @RequestMapping("/member/policy_feedback_list.jspx")
    public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer pageNo, Date startDate,
                       Date endDate, String applierName, String policyLevel, String policyName,
                       String policyCode, String state, String status) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        WebErrors errors = WebErrors.create(request);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员 无权查看
        if (!user.getIsAdmin()) {
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request, response, model, errors);
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = cloudCenterApplyService.getFeedbackPage(site.getSiteId(), user, cpn(pageNo),
                20, startDate, endDate, applierName, policyLevel, policyName, policyCode, state);
        model.addAttribute("pagination", p);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("applierName", applierName);
        model.addAttribute("policyLevel", policyLevel);
        model.addAttribute("policyName", policyName);
        model.addAttribute("policyCode", policyCode);
        model.addAttribute("status", status);
        model.addAttribute("state", state);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, POLICY_FEEDBACK_LIST);
    }

    /**
     * @param state  审批进度
     * @param status
     * @author wanjinyou
     * @date 2017/1/11 16:00
     * @desc 政策申请跟踪列表
     **/
    @RequestMapping("/member/policy_applyFollow_list.jspx")
    public String followList(HttpServletRequest request, ModelMap model, Integer pageNo, Date startDate,
                             Date endDate, String applierName, String policyLevel, String policyName,
                             String policyCode, String state, String status, String userName) {
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
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = cloudCenterApplyService.getFollowbackPage(site.getSiteId(), user, cpn(pageNo),
                20, startDate, endDate, applierName, policyLevel, policyName, policyCode, state, userName);
        model.addAttribute("pagination", p);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("applierName", applierName);
        model.addAttribute("policyLevel", policyLevel);
        model.addAttribute("policyName", policyName);
        model.addAttribute("policyCode", policyCode);
        model.addAttribute("status", status);
        model.addAttribute("state", state);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SOFTWARE_LIST);
    }

    /**
     * @param id        政策申请id
     * @param request
     * @param response
     * @param model
     * @param state     审批状态
     * @param nextUrl   跳转链接
     * @param modelId
     * @param channelId
     * @param charge
     * @author ly
     * @date 2017/1/7 16:20
     * @desc 政策申请状态管理
     **/
    @RequestMapping(value = "/member/apply_manage.jspx")
    public String manage(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, String state,
                         String nextUrl, Integer modelId, Integer channelId, Short charge,String backReason) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //业务数据更新处理
        try {
            if(backReason!=null) {
                backReason = java.net.URLDecoder.decode(backReason, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //更新申请状态
        cloudCenterApplyService.updateState(id, state, channelId, user, charge, true,backReason);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * 管理员置为驳回需求
     * @Author 闫浩芮
     * @param demandIdsStr 需要批量发布的Id字符串
     * @param  nextUrl 操作完成后返回的地址
     */
    @RequestMapping(value = "/member/synergy_demand_rejectManyPolicy.jspx")
    public String rejectMany(String demandIdsStr, String nextUrl,HttpServletRequest request,
                             HttpServletResponse response, ModelMap model,String backReason) {
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
        //业务数据更新处理
        try {
            backReason=java.net.URLDecoder.decode(backReason, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //业务数据更新处理
        cloudCenterApplyService.rejectMany(demandIdsStr,backReason);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * 批量报送
     * @Author 闫浩芮
     * @param demandIdsStr 需要批量发布的Id字符串
     * @param  nextUrl 操作完成后返回的地址
     * @param  state 操作对象的状态值，后台根据它进行更改
     * @Date 2017/2/17 0017 18:44
     */
    @RequestMapping(value = "/member/synergy_demand_passManyPolicy.jspx")
    public String passMany(String demandIdsStr, String state, String nextUrl, HttpServletRequest request,
                           HttpServletResponse response, ModelMap model) {
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
        //业务数据更新处理
        cloudCenterApplyService.passMany(demandIdsStr, state);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * 批量删除
     * @Author 闫浩芮
     * @param demandIdsStr 需要批量发布的Id字符串
     * @param  nextUrl 操作完成后返回的地址
     * @Date 2017/2/20 0020 10:39
     */
    @RequestMapping(value = "/member/synergy_apply_deleteMany.jspx")
    public String deleteMany(String demandIdsStr, String nextUrl, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
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
        //业务数据更新处理
        cloudCenterApplyService.deleteMany(demandIdsStr);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param id        政策申请id
     * @param request
     * @param response
     * @param model
     * @param modelId
     * @param channelId
     * @author ly
     * @date 2017/1/7 16:21
     * @desc 删除政策申请
     **/
    @RequestMapping(value = "/member/apply_delete.jspx")
    public String delete(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, Integer modelId, Integer channelId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl = "cloudcenter_policyApply_list.jspx";
        FrontUtils.frontData(request, model, site);
        //更新申请状态
        cloudCenterApplyService.delete(id);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param id        政策申请id
     * @param request
     * @param response
     * @param model
     * @param modelId
     * @param channelId
     * @author ly
     * @date 2017/1/7 16:21
     * @desc 删除政策申请
     **/
    @RequestMapping(value = "/member/applyAdmin_delete.jspx")
    public String deleteApply(Integer id, HttpServletRequest request, HttpServletResponse response,
                              ModelMap model, Integer modelId, Integer channelId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl = "/member/policy_feedback_list.jspx?state=0";
        FrontUtils.frontData(request, model, site);
        //更新申请状态
        cloudCenterApplyService.delete(id);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param id 政策申请id
     * @author ly
     * @date 2017/1/7 16:32
     * @desc 政策申请信息明细页
     **/
    @RequestMapping(value = "/member/cloudCenter_apply_view.jspx")
    public String view(Integer id, HttpServletRequest request, String nextUrl,
                       HttpServletResponse response, ModelMap model) {
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
        SIcloudApply apply = cloudCenterApplyService.byApplyId(id);
        SIcloudPolicy policy = apply.getPolicy();
        Content content = apply.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("policy", policy);
        model.addAttribute("applyId", id);//有些多余
        model.addAttribute("apply", apply);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, APPLY_VIEW);
    }

    /**
     * @param id 政策申请id
     * @author wanjinyou
     * @date 2017/2/16
     * @desc 云计算政策申请反馈--明细
     **/
    @RequestMapping(value = "/member/policy_feeBack_view.jspx")
    public String feeBackView(Integer id, HttpServletRequest request, String nextUrl,
                              HttpServletResponse response, ModelMap model) {
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
        SIcloudApply apply = cloudCenterApplyService.byApplyId(id);
        SIcloudPolicy policy = apply.getPolicy();
        Content content = apply.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("policy", policy);
        model.addAttribute("applyId", id);//有些多余
        model.addAttribute("apply", apply);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, POLICY_FEEDBACK_VIEW);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    protected CloudCenterApplyService cloudCenterApplyService;
    @Resource
    private CloudCenterPolicyService cloudCenterPolicyService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
