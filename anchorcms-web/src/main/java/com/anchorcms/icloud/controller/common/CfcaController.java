package com.anchorcms.icloud.controller.common;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.common.SCfcaApply;
import com.anchorcms.icloud.model.common.SCfcaPay;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.common.CfcaService;
import com.anchorcms.icloud.service.synergy.SDemandCreateService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.anchorcms.common.constants.Constants.TPLDIR_COMMON;
import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @author machao
 * @Date 2017/3/30 14:39
 * @return 电子签章
 */
@Controller
public class CfcaController {
    public static final String CFCA_SEAL = "tpl.cfca_seal";//签章申请页面旧版
    public static final String CFCA_SEAL_APPLY = "tpl.cfca_seal_apply";//签章申请页面
    public static final String CFCA_USER_APPLY_LIST = "tpl.CFCAuserApplyList";
    public static final String CFCA_SEAL_MANAGE = "tpl.cfca_seal_manage";//用户签章申请管理
    public static final String CFCA_SEAL_INTRODUCE = "tpl.cfca_seal_introduce";//签章介绍页面
    public static final String CFCAAPPLYMANAGELIST = "tpl.cloud_resource_cfcaApplyList"; // 电子签章管理列表
    public static final String CFCAAPPLYMANAGEVIEW = "tpl.cloud_resource_cfcaApplyView";//签章申请详情页
    public static final String  cfca_seal_url="/member/cfca_seal.jspx";
    /**
     * @author zhouyq
     * @Date 2017/03/30
     * @param
     * @return
     * @throws
     * @Desc 电子签章列表展示
     */
    @RequestMapping(value ="/member/cloud_resource_cfcaApplyList.jspx")
    public String list(String companyName, Integer pageNo, HttpServletRequest request, ModelMap model, String status) {

        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
        // 获得分页信息
        Pagination pagination = cfcaService.getList(companyName, status, cpn(pageNo), CookieUtils.getPageSize(request));
        List paginationList = pagination.getList(); // 必须返回List格式数据
        model.addAttribute("cfcaList", paginationList);
        model.addAttribute("pagination", pagination);
        model.addAttribute("companyName", companyName);
        model.addAttribute("status",status);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, CFCAAPPLYMANAGELIST);
    }

    /**
     * @author zhouyq
     * @Date 2017/3/31
     * @return
     * @description  电子签章申请明细界面
     */

    @RequestMapping("/member/cloud_resource_cfcaApplyView.jspx")
    public String Repair_manager_view(HttpServletRequest request, HttpServletResponse response,
                                      ModelMap model,String id) {
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
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);

        SCfcaApply sCfcaEntity = cfcaService.getCfcaEntity(id);
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(sCfcaEntity.getUser().getCompany().getCompanyId());
        model.addAttribute("company",sCfcaEntity.getUser().getCompany());
        model.addAttribute("company_aptitude", company_aptitude);
        model.addAttribute("sCfcaApply", sCfcaEntity);
        model.addAttribute("sCfcaPay", sCfcaEntity.getSCfcaPay());
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, CFCAAPPLYMANAGEVIEW);
    }
    /**
     * @author ld
     * @Date 2017/3/31
     * @return
     * @description  电子签章申请编辑
     */
    public static final String CFCAAPPLYMANAGEEDIT = "tpl.cloud_resource_cfcaApplyEdit";
    @RequestMapping("/member/cloud_resource_cfcaApplyEdit.jspx")
    public String Repair_manager_Edit(HttpServletRequest request, HttpServletResponse response,
                                      ModelMap model,String id) {
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
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);

        SCfcaApply sCfcaEntity = cfcaService.getCfcaEntity(id);
        model.addAttribute("sCfcaEntity", sCfcaEntity);
        model.addAttribute("site", site);
        model.addAttribute("id", id);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, CFCAAPPLYMANAGEEDIT);
    }

    /**
     * @return 用户获取自己的申请数据
     * @author ldong
     * @Date 2017/3/30 16:02
     */
    @RequestMapping("member/cfca_user_apply_list.jspx")
    public String cfcalist(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer pageNo) {
        String nextUrl = "";
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        //WebErrors errors = WebErrors.create(request);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
     /*   //非管理员 无权查看
        if (!user.getIsAdmin()) {
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request, response, model, errors);
        }*/
        //用户没有关联公司的话不能继续
        if (user.getCompany() == null) {
            return null; //返回null回执行什么页面。
        }

        Pagination p = cfcaService.getApplyPage(user, cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMON, CFCA_USER_APPLY_LIST);
    }

    /**
     * @return 更改申请数据的状态
     * @author ldong
     * @Date 2017/3/30 16:03
     */
    @RequestMapping("member/cfca_edit_state.jspx")
    public String editState(HttpServletRequest request, ModelMap model, String state, Integer id,String nextUrl) {
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
        if (user.getCompany() == null) {
            return null; //返回null回执行什么页面。
        }
        try {
            cfcaService.editState(state, id);
            return FrontUtils.showSuccess(request, model,nextUrl );
        } catch (RuntimeException e) {
            e.printStackTrace();
            return FrontUtils.showBaseMessage(request, model, "操作错误", nextUrl);
        }

    }

    /**
     * @author ldong
     * @Date 2017/3/30 16:03
     * @return 提交支付信息支付功能
     */
    @RequestMapping("member/cfca_save_payResult.jspx")
    public String editState(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                            String remark,      //备注
                            String orderNo,     //单号
                            String accountNo,   //付款账户
                            String attachmentPaths[],     //附件
                            String attachmentNames[],//附件名称
                            Integer id) {

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
        if (user.getCompany() == null) {
            return null; //返回null回执行什么页面。
        }
        try {
            SCfcaPay sd = new SCfcaPay();
            sd.setPolicyId(Math.abs(UUID.randomUUID().hashCode()));
            sd.setPolicyName(accountNo);
            sd.setPolicyNumber(orderNo);
            sd.setPolicyLevel(remark);

            Content c = new Content();
            c.setSite(site);
            CmsModel defaultModel = cmsModelMng.getDefModel();

            c.setModel(defaultModel);

            ContentExt ext = new ContentExt();
            ext.setTitle("");
            ext.setAuthor(user.getUsername());
            ext.setDescription("支付信息");
            ContentTxt t = new ContentTxt();
            t.setTxt("");
            ContentType type = contentTypeMng.getDef();
            if (type == null) {
                throw new RuntimeException("Default ContentType not found.");
            }
            Integer typeId = type.getTypeId();
            if (c.getRecommendLevel() == null) {
                c.setRecommendLevel((byte) 0);
            }
            //前往service层保存业务
            c = cfcaService.save(sd, c, ext, t, attachmentPaths, attachmentNames,"101", typeId, user, null, true, id);
//
            this.editState( request,model,"3",id,cfca_seal_url);
            return FrontUtils.showSuccess(request, model, cfca_seal_url);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return FrontUtils.showBaseMessage(request, model, "操作错误", cfca_seal_url);
        }}

    /**
     * 签章开通成功
     */
    @RequestMapping("/member/cfca_admin_pass.jspx")
    public String setPass(Integer applyId1, String signNo, String signPwd,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        //WebErrors errors = WebErrors.create(request);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }

        if (!user.getIsAdmin()) {
            return null;
        }
        cfcaService.setPass(  applyId1,   signNo,   signPwd);
        return FrontUtils.showSuccess(request, model,"/member/cloud_resource_cfcaApplyList.jspx" );
    }

    /**
     * @author machao
     * @Date 2017/3/31 15:22
     * @return
     * 签章申请
     */
    @RequestMapping("/member/cfca_seal_apply.jspx")
    public String cfca_seal_apply(HttpServletRequest request, HttpServletResponse response,
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
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，通过后才能申请签章功能！";
            return FrontUtils.showBaseMessage(request, model, message, cfca_seal_url);
        }

        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
            model.addAttribute("company", user.getCompany());
            model.addAttribute("company_aptitude", company_aptitude);
            model.addAttribute("site", site);
            model.addAttribute("channelList", channelList);
            model.addAttribute("sessionId", request.getSession().getId());
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_COMMON, CFCA_SEAL_APPLY);

    }

    /**
     * @author hansx
     * @Date 2017/5/10 15:22
     * @return
     * 签章入口
     */
    @RequestMapping("/member/cfca_seal.jspx")
    public String cfca_seal(HttpServletRequest request,
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

        List<SCfcaApply> sCfcaApply = cfcaService.getApplyList(user);
        if(sCfcaApply!=null &&sCfcaApply.size()>0){
        model.addAttribute("c", sCfcaApply.get(0));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMON, CFCA_SEAL_MANAGE);

        } else{
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_COMMON, CFCA_SEAL_INTRODUCE);
        }
    }

    /**
     * @author machao
     * @Date 2017/3/31 14:47
     * @return
     * 签章保存
     */
    @RequestMapping(value = "/member/cfca_seal_save.jspx")
    public String save(HttpServletRequest request, ModelMap model,SCfcaApply sCfcaApply){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());//获取当前时间

        List<SCfcaApply> list = cfcaService.getApplyList(user);
        if(list!=null &&list.size()>0) {//更新
            SCfcaApply  cfcaApply1 =list.get(0);
            cfcaApply1.setCompanyName(user.getCompany().getCompanyName());
            cfcaApply1.setCreditCode(sCfcaApply.getCreditCode());//申请年限
            cfcaApply1.setBank(sCfcaApply.getBank());//金额
            cfcaApply1.setSealCon(user.getCompany().getCompanyName() + "合同专用章");
            cfcaApply1.setApplyDt(date);
            cfcaApply1.setState(sCfcaApply.getState());
            cfcaService.cfcaSealSave(cfcaApply1);
        }else{//添加
            sCfcaApply.setApplyDt(date); //创建时间
            sCfcaApply.setCompanyName(user.getCompany().getCompanyName());
            sCfcaApply.setUser(user);
            sCfcaApply.setSealCon(user.getCompany().getCompanyName() + "合同专用章");
            cfcaService.cfcaSealSave(sCfcaApply);
        }
        return FrontUtils.showSuccess(request, model, cfca_seal_url);
    }
//    public String save(HttpServletRequest request, HttpServl
// etResponse response, ModelMap model,
//                       ///     int soldNoteId,//唯一标示
//                       String company,//公司名称
//                       String companyId,//公司名称
//                       String legalName,//法人姓名
//                       String legalId,//法人身份证号
//                       String sealContent,//印章 内容
//                       String companyInfo,//备注
//                       String contact,//联系人
//                       String mobile,//联系电话
//                       String telephone,//固定电话
//                       String addrProvince,//地址（省）
//                       String addrCity,//地址（地级市）
//                       String addrCounty,//地址（市、县级）
//                       String addrStreet,//街道信息
//                       String email,
//                       String status,
//
//                       String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs){
//        String nextUrl = "/member/cfca_user_apply_list.jspx";
//        CmsSite site = CmsUtils.getSite(request);
//        CmsUser user = CmsUtils.getUser(request);
//        FrontUtils.frontData(request, model, site);
//        SCfcaApply sCfcaApply = cfcaService.getSignFlag(user.getCompany().getCompanyId());//判定是否开通签章功能
//        if(sCfcaApply!=null)
//        {
//            com.anchorcms.core.web.WebErrors errors = com.anchorcms.core.web.WebErrors.create(request);
//           return FrontUtils.showBaseMessage(request, model,"公司已申请电子签章，请查看申请列表", nextUrl);
//        }
//        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());//获取当前时间
//
//        sCfcaApply=new SCfcaApply();
//        sCfcaApply.setCompanyName(company); //公司名称
//        sCfcaApply.setCompanyId(companyId);
//        sCfcaApply.setLegalName(legalName);//法人姓名
//        sCfcaApply.setLegalId(legalId);//法人身份证
//        sCfcaApply.setSealCon(sealContent);//印章内容
//        sCfcaApply.setContact(contact); //联系人
//        sCfcaApply.setMobile(mobile); //联系电话
//        sCfcaApply.setApplyDt(date); //创建时间
//        sCfcaApply.setAddrProvince(addrProvince); //省
//        sCfcaApply.setAddrCity(addrCity); //市
//        sCfcaApply.setAddrCounty(addrCounty); //区
//        sCfcaApply.setAddrStreet(addrStreet); //街道
//        sCfcaApply.setEmail(email); //邮箱
//        sCfcaApply.setState(status);
//        sCfcaApply.setUser(user);
//
//
//        Content c = new Content();
//        c.setSite(site);
//        CmsModel defaultModel=cmsModelMng.getDefModel();
//        ContentExt ext = new ContentExt();
//        ext.setTitle("电子签章申请发布");
//        ext.setAuthor(user.getUsername());
//        ext.setDescription("电子签章申请发布");
//        ContentTxt t = new ContentTxt();
//        t.setTxt(companyInfo);
//        ContentType type = contentTypeMng.getDef();
//        if (type == null) {
//            throw new RuntimeException("Default ContentType not found.");
//        }
//        Integer typeId = type.getTypeId();
//        if(c.getRecommendLevel()==null){
//            c.setRecommendLevel((byte) 0);
//        }
//        c=cfcaService.cfcaSealSave(sCfcaApply,c,ext,t,null,typeId,user,true,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,picDescs);
//        return FrontUtils.showSuccess(request, model, nextUrl);
//    }
    /**
     * @author machao
     * @Date 2017/3/31 14:47
     * @return
     * 签章保存
     */
    @RequestMapping(value = "/member/cfca_seal_edit.jspx")
    public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String applyId,//唯一标示
                       String companyName,//公司名称
                       String companyId,//公司名称
                       String legalName,//法人姓名
                       String legalId,//法人身份证号
                       String sealContent,//印章 内容
                       String companyInfo,//备注
                       String contact,//联系人
                       String mobile,//联系电话
                       String telephone,//固定电话
                       String addrProvince,//地址（省）
                       String addrCity,//地址（地级市）
                       String addrCounty,//地址（市、县级）
                       String addrStreet,//街道信息
                       String email,
                       String status,
                       String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs){

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());//获取当前时间

        SCfcaApply sCfcaApply=this.cfcaService.getCfcaEntity(applyId);
        sCfcaApply.setCompanyName(companyName); //公司名称
        sCfcaApply.setCompanyId(companyId);
        sCfcaApply.setLegalName(legalName);//法人姓名
        sCfcaApply.setLegalId(legalId);//法人身份证
        sCfcaApply.setSealCon(sealContent);//印章内容
        sCfcaApply.setContact(contact); //联系人
        sCfcaApply.setMobile(mobile); //联系电话
        //sCfcaApply.setApplyDt(date); //创建时间
        sCfcaApply.setAddrProvince(addrProvince); //省
        sCfcaApply.setAddrCity(addrCity); //市
        sCfcaApply.setAddrCounty(addrCounty); //区
        sCfcaApply.setAddrStreet(addrStreet); //街道
        sCfcaApply.setEmail(email); //邮箱
        sCfcaApply.setState(status);
        sCfcaApply.setUser(user);

        Content c = new Content();
        c.setSite(site);
        CmsModel defaultModel=cmsModelMng.getDefModel();
        ContentExt ext = new ContentExt();
        ext.setTitle("电子签章申请发布");
        ext.setAuthor(user.getUsername());
        ext.setDescription("电子签章申请发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(companyInfo);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        c=cfcaService.cfcaSealEdit(sCfcaApply,c,ext,t,null,typeId,user,true,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,picDescs);

        return FrontUtils.showSuccess(request, model, cfca_seal_url);
    }

    /**
     * 电子签章审核
     */
    @RequestMapping("/member/CfcaModifyState.jspx")
    public String modifyCfcaStateById(String applyId, String state, java.util.Date releaseDt, String nextUrl,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model, String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if(backReason != null){
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
        if (applyId != null) {
            applyId = java.net.URLDecoder.decode(applyId, "utf-8");
            cfcaService.modifyCfcaStateById(applyId, state,releaseDt,backReason);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected CfcaService cfcaService;
    @Resource
    protected ContentMng contentMng;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;
    @Resource
    private SDemandCreateService sDemandCreateService;
}
