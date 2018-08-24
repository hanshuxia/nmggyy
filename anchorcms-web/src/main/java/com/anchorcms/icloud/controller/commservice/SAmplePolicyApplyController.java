package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTxtMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.commservice.SAmplePolicyApply;
import com.anchorcms.icloud.model.commservice.SSoldNote;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.commservice.SAmplePolicyApplyService;
import com.anchorcms.icloud.service.commservice.SAmplePolicyService;
import com.anchorcms.icloud.service.commservice.SSoldNoteService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Created by 董学成 on 2017/1/13.
 */
@Controller
public class SAmplePolicyApplyController {

    /**
     * @author dongxuecheng
     * @Date 2017/1/13 11:16
     * @return
     * @description跳转政策申请界面
     *
     */
    public static final String TPLDIR_SYNERGY_SOLD = "commservice";
    public static final String SUPPLYDETAIL_SOLD_RELEASE = "tpl.supplychainSAmplePolicyApply";
    public static final String COMPANY_APTITUDE_ADD = "/member/index.jspx";
    public static final String COMPANY_APTITUDE_ADD2 = "/member/company_edit.jspx";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/SAmplePolicyApply.jspx")
    public String SAmplePolicyApply(HttpServletRequest request, HttpServletResponse response,
                         ModelMap model,Integer id) {
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
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        SAmplePolicy sAmplePolicy=sAmplePolicyService.findById(id);
        model.addAttribute("sAmplePolicy", sAmplePolicy);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(101));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_SOLD, SUPPLYDETAIL_SOLD_RELEASE);
    }
    /**
     * @author dongxuecheng
     * @Date 2017/1/14 23:16
     * @return
     * @description查询销售记录
     */
    @RequestMapping(value = "/member/Open_import.jspx", method = RequestMethod.POST)
    public void Open_import(String inquiryTheme,String status,String statusType,Integer pageNo, HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();

        if (inquiryTheme != null) { // 地区
            try {
                inquiryTheme = java.net.URLDecoder.decode(inquiryTheme, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return;
        }
        if (user == null) {
            return;
        }
        //用户没有关联公司的话不能继续
        if(user.getCompany() == null){
            return;
        }
        if(pageNo == null){
            pageNo = 1;
        }
        try {
            String jsonStr=this.sSoldNoteService.getPage(user.getUserId(),cpn(pageNo),20,inquiryTheme,"2");
            PrintWriter writer = response.getWriter();
            writer.print(jsonStr);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:51
     * @return
     * @description 政策申请提交功能
     */
    public static final String SUPPLYDETAIL_OPEN_IMPORT = "tpl.supplychainOpen_import";
    @RequestMapping(value = "/member/SAmplePolicyApply_submit.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       ///     int amplePolicyApplyId,//唯一标示
                       Integer amplePolicyId,//申请政策id
                       String soldNoteId,//销售记录id
                      // String companyId,//公司id
                       String companyName,//公司名称
                       String contact,//联系人
                       String mobile,//联系电话
                       String telephone,//固定电话
                       String fax,//传真
                       String email,//email
                       String comInfoPath,//企业基本信息附件
                       String applyDatumPaht,//申请材料附件
                      // String createrId,//创建人id
                       String status,//状态
                       Integer modelId,Integer channelId,
                       String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs){
                        CmsSite site = CmsUtils.getSite(request);
                        CmsUser user = CmsUtils.getUser(request);
                        FrontUtils.frontData(request, model, site);

                        Date date = new Date(System.currentTimeMillis());//获取当前时间
                        Content c = new Content();
                        SAmplePolicyApply sAmplePolicyApply=new SAmplePolicyApply();
                        sAmplePolicyApply.setsAmplePolicy(sAmplePolicyService.findById(amplePolicyId));
                        sAmplePolicyApply.setSoldNoteId(soldNoteId);
                        sAmplePolicyApply.setCompanyId(user.getCompany().getCompanyId());
                        sAmplePolicyApply.setCompanyName(companyName);
                        sAmplePolicyApply.setContact(contact);
                        sAmplePolicyApply.setMobile(mobile);
                        sAmplePolicyApply.setFax(fax);
                        sAmplePolicyApply.setEmail(email);
                        sAmplePolicyApply.setComInfoPath(comInfoPath);
                        sAmplePolicyApply.setApplyDatumPaht(applyDatumPaht);
                        sAmplePolicyApply.setCreaterId(user.getUserId().toString());
                        sAmplePolicyApply.setCreateDt(date);
                        sAmplePolicyApply.setUpdateDt(date);
                        sAmplePolicyApply.setStatus(status);

                            c.setSite(site);
                            CmsModel defaultModel=cmsModelMng.getDefModel();
                            if(modelId!=null){
                                CmsModel m=cmsModelMng.findById(modelId);
                                if(m!=null){
                                    c.setModel(m);
                                }else{
                                    c.setModel(defaultModel);
                                }
                            }else{
                                c.setModel(defaultModel);
                            }
                            ContentExt ext = new ContentExt();
                            ext.setTitle(companyName);
                            ext.setAuthor(user.getUsername());
                            ext.setDescription("惠补政策申请");
                            ContentTxt t = new ContentTxt();
                            t.setTxt("惠补政策申请");
                            ContentType type = contentTypeMng.getDef();
                            if (type == null) {
                                throw new RuntimeException("Default ContentType not found.");
                            }
                            Integer typeId = type.getTypeId();
                            if(c.getRecommendLevel()==null){
                                c.setRecommendLevel((byte) 0);
                            }
                       c = sAmplePolicyApplyService.supplychain_save(sAmplePolicyApply, c, ext, t, channelId, typeId, user, true, attachmentPaths, attachmentNames, attachmentFilenames, picPaths, picDescs);
                         String nextUrl="/";
                        return FrontUtils.showSuccess(request, model, nextUrl);

    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/13 11:16
     * @return
     * @description 明细页面
     *
     */
    public static final String SUPPLYDETAIL_SAMPLEPOLICYAPPLY_DETAIL = "tpl.supplychainSAmplePolicyApply_detail";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/SAmplePolicyApply_detail.jspx")
    public String SAmplePolicyApply_detail(HttpServletRequest request, HttpServletResponse response,
                                    ModelMap model,Integer amplePolicyId,Integer amplePolicyApplyId) {
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
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        if(amplePolicyId!=null) {
            SAmplePolicy sAmplePolicy = sAmplePolicyService.findById(amplePolicyId);
            model.addAttribute("sAmplePolicy", sAmplePolicy);
        }else{
            SAmplePolicy sAmplePolicy=new SAmplePolicy();
            model.addAttribute("sAmplePolicy", sAmplePolicy);
        }
        SAmplePolicyApply sAmplePolicyApply=sAmplePolicyApplyService.findbyId(amplePolicyApplyId);
        String[] soldNoteIds={};
        if(sAmplePolicyApply.getSoldNoteId()!=null) {
            soldNoteIds = sAmplePolicyApply.getSoldNoteId().split(",");
        }
        List<SSoldNote> sSoldNotes = new ArrayList<SSoldNote>();
        if(soldNoteIds!=null) {
            for(int i=0;i<soldNoteIds.length;i++){
                String isoldNoteId=soldNoteIds[i];
                if(!isoldNoteId.equals("")) {
                    int realid = Integer.parseInt(isoldNoteId);
                    sSoldNotes.add(sSoldNoteService.findById(realid));
                }
            }

        }
        model.addAttribute("sAmplePolicyApply", sAmplePolicyApply);
        model.addAttribute("sSoldNotes", sSoldNotes);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_SOLD, SUPPLYDETAIL_SAMPLEPOLICYAPPLY_DETAIL);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/13 11:16
     * @return
     * @description 编辑页面
     */
    public static final String SUPPLYDETAIL_SAMPLEPOLICYAPPLY_EDIT = "tpl.supplychainSAmplePolicyApply_edit";

    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/SAmplePolicyApply_edit.jspx")
    public String SAmplePolicyApply_edit(HttpServletRequest request, HttpServletResponse response,
                                           ModelMap model, Integer amplePolicyId, Integer amplePolicyApplyId) {
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
        if (amplePolicyId != null) {
            SAmplePolicy sAmplePolicy = sAmplePolicyService.findById(amplePolicyId);
            model.addAttribute("sAmplePolicy", sAmplePolicy);
        } else {
            SAmplePolicy sAmplePolicy = new SAmplePolicy();
            model.addAttribute("sAmplePolicy", sAmplePolicy);
        }
        SAmplePolicyApply sAmplePolicyApply = sAmplePolicyApplyService.findbyId(amplePolicyApplyId);
        String[] soldNoteIds = {};
        if (sAmplePolicyApply.getSoldNoteId() != null) {
            soldNoteIds = sAmplePolicyApply.getSoldNoteId().split(",");
        }
        List<SSoldNote> sSoldNotes = new ArrayList<SSoldNote>();
        if (soldNoteIds != null) {
            for (int i = 0; i < soldNoteIds.length; i++) {
                String isoldNoteId = soldNoteIds[i];
                if (!isoldNoteId.equals("")) {
                    int realid = Integer.parseInt(isoldNoteId);
                    sSoldNotes.add(sSoldNoteService.findById(realid));
                }
            }

        }
        model.addAttribute("sAmplePolicyApply", sAmplePolicyApply);
        model.addAttribute("sSoldNotes", sSoldNotes);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_SOLD, SUPPLYDETAIL_SAMPLEPOLICYAPPLY_EDIT);
    }


    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:51
     * @return
     * @description 政策申请提交编辑功能
     */
    @RequestMapping(value = "/member/SAmplePolicyApply_change.jspx")
    public String change(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       Integer amplePolicyApplyId,//唯一标示
                       Integer amplePolicyId,
                       String soldNoteId,
                       String companyId,
                       String companyName,
                       String contact,
                       String mobile,
                         String fax,
                         String email,
                         String telephone,
                       String comInfoPath,
                       String applyDatumPaht,
                       String createrId,
                       Date createDt,
                       Date updateDt,
                       String status,
                       String flag,//flag为submit的时候提交，为openSold的时候，打开销售记录页面
                       Integer modelId,Integer channelId,String nextUrl,
                       String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        Date date = new Date(System.currentTimeMillis());//获取当前时间

        SAmplePolicyApply sAmplePolicyApply=sAmplePolicyApplyService.findbyId(amplePolicyApplyId);
        Content c = sAmplePolicyApply.getContent();

        sAmplePolicyApply.setsAmplePolicy(sAmplePolicyService.findById(amplePolicyId));
        sAmplePolicyApply.setSoldNoteId(soldNoteId);
        sAmplePolicyApply.setCompanyId(user.getCompany().getCompanyId());
        sAmplePolicyApply.setCompanyName(companyName);
        sAmplePolicyApply.setContact(contact);
        sAmplePolicyApply.setFax(fax);
        sAmplePolicyApply.setEmail(email);
        sAmplePolicyApply.setTelephone(telephone);
        sAmplePolicyApply.setComInfoPath(comInfoPath);
        sAmplePolicyApply.setApplyDatumPaht(applyDatumPaht);
        sAmplePolicyApply.setStatus(status);
        sAmplePolicyApply.setCreaterId(user.getUserId().toString());
        sAmplePolicyApply.setCreateDt(date);
        sAmplePolicyApply.setUpdateDt(date);
       // sAmplePolicyApply.setStatus(status);


        c.setSite(site);
        ContentExt ext = c.getContentExt();
        ext.setTitle(companyName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("惠补政策申请");
        ContentTxt t = c.getContentTxt();

        //若表中无contentTxt的数据，新增一条
//        if(editor == null){
//            editor = "";
//        }
        if(t == null){
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt("");
            contentTxtMng.save(contentTxt, c);
        }else{
            t.setTxt("惠补政策申请");
        }

        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        sAmplePolicyApplyService.update(sAmplePolicyApply,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,null,true,null);
        //跳转至需求管理列表
         nextUrl = "/member/SAmplePolicy_manager.jspx?statusType="+status+"&status="+status;
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:51
     * @return
     * @description //惠补政策管理
     */
    public static final String SUPPLYDETAIL_SAMPLEPOLICY_MANAGER = "tpl.supplychainSAmplePolicy_manager";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/SAmplePolicy_manager.jspx")
    public String SAmplePolicy_manager(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,Date StartDt,Date EndDt,
                                 String status, String payType, String statusType, Integer modelId, Integer queryChannelId, Integer pageNo,String flag,
                                 HttpServletRequest request, ModelMap model,Integer id) {
        // String nextUrl = DEMAND_LIST;
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
        if(flag!=null&&flag.equals("delete")){//删除功能

            sAmplePolicyApplyService.delete(id);
        } else if (flag!=null && flag.equals("relece")) { // 发布功能
            sAmplePolicyApplyService.relece(id);
        } else  if (flag!=null && flag.equals("reback")) { // 撤回功能
            sAmplePolicyApplyService.reback(id);
        }

        //将检索条件返回到页面当中
        if (!StringUtils.isBlank(inquiryTheme)) {
            model.addAttribute("title", inquiryTheme);
        }
        if (releaseDt != null) {
            model.addAttribute("releaseDt", releaseDt);
        }
        if (deadlineDt != null) {
            model.addAttribute("deadlineDt", deadlineDt);
        }
        Pagination p = sAmplePolicyApplyService.getPageForMember( site.getSiteId(),  user.getIsAdmin(),cpn(pageNo), 20, inquiryTheme, user.getUserId(), status);
        model.addAttribute("pagination", p);

        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("StartDt", StartDt);
        model.addAttribute("EndDt", EndDt);
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("status", status);
        model.addAttribute("statusType", statusType);
        model.addAttribute("payType", payType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_SOLD, SUPPLYDETAIL_SAMPLEPOLICY_MANAGER);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/8 12:13
     * @return
     * @description惠补政策申请审核
     */
    public static final String SUPPLYDETAIL_SAMPLEPOLICY_CHECK = "tpl.supplychainSAmplePolicy_check";
    @RequiresPermissions("member:repairDemandList")
    @RequestMapping(value ="/member/SAmplePolicy_check.jspx")
    public String SAmplePolicy_check(String flag,String id,String repairName, Integer pageNo,String nextUrl, HttpServletRequest request, ModelMap model,String status,String inquiryTheme,String backReason) throws UnsupportedEncodingException {

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
        // 解决前台传入的中文乱码参数
        if (repairName != null) {
            repairName = java.net.URLDecoder.decode(repairName, "utf-8");
        }
        if (backReason != null) {
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
        if (flag != null) {
            if (flag.equals("pass")) {
                sAmplePolicyApplyService.setStatus(id, "3",backReason);
            } else if (flag.equals("noPass")) {
                sAmplePolicyApplyService.setStatus(id, status,backReason);
            } else if (flag.equals("delete")) {
                sAmplePolicyApplyService.delete(id);
            } else if (flag.equals("setPass")) {
                sAmplePolicyApplyService.setall(id, "3",backReason);
            } else if (flag.equals("setNopass")) {
                sAmplePolicyApplyService.setall(id, status,backReason);
            } else if (flag.equals("repairName")) {
                repairName = id;
            }
        }
        // 获得分页信息
        Pagination pagination = sAmplePolicyApplyService.getPageForMember1(site.getSiteId(),  user.getIsAdmin(),cpn(pageNo), 20, inquiryTheme,user.getUserId(), status);
       // model.addAttribute("pagination", p);
       // Pagination pagination = sAmplePolicyApplyService.getZxxqList(repairName, cpn(pageNo), CookieUtils.getPageSize(request));
        List paginationList = pagination.getList(); // 必须返回List格式数据
        model.addAttribute("repairDemandList", paginationList);
        model.addAttribute("status", status);
        model.addAttribute("pagination", pagination);
        model.addAttribute("inquiryTheme", inquiryTheme);

//        ArrayList totalPagesList = new ArrayList();
//        for (int i = -1; i < totalPages; i++) {
//            totalPagesList.add(i + 1, i + 1);
//        }
//        model.addAttribute("totalPagesList", totalPagesList);
        if (flag==null||flag.equals("")) {
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGY_SOLD, SUPPLYDETAIL_SAMPLEPOLICY_CHECK);
        }else {
            return FrontUtils.showSuccess(request, model, nextUrl);
        }
    }


    /**
     * @param repairIds, request, response, model
     * @return
     * @author dongxuecheng
     * @Date 2017/01/06
     * @Desc 根据id批量修改众修需求状态（通过、驳回）
     */
    @RequestMapping("/member/SAmplePolicySetStates.jspx")
    public String SAmplePolicySetStates(String repairIds, String status, String nextUrl,String flag,String id,Integer pageNo,
                                        HttpServletRequest request, HttpServletResponse response, ModelMap model,String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        String repairName=null;
        if (flag != null) {
            if (flag.equals("pass")) {
                sAmplePolicyApplyService.setStatus(id, "3",backReason);
            } else if (flag.equals("noPass")) {
                sAmplePolicyApplyService.setStatus(id, "0",backReason);
            } else if (flag.equals("delete")) {
                sAmplePolicyApplyService.delete(id);
            } else if (flag.equals("setPass")) {
                sAmplePolicyApplyService.setall(id, "3",backReason);
            } else if (flag.equals("setNopass")) {
                sAmplePolicyApplyService.setall(id, "0",backReason);
            } else if (flag.equals("repairName")) {
                repairName = id;
                Pagination pagination = sAmplePolicyApplyService.getZxxqList(repairName, cpn(pageNo), CookieUtils.getPageSize(request));
                List paginationList = pagination.getList(); // 必须返回List格式数据
                model.addAttribute("repairDemandList", paginationList);
                model.addAttribute("pagination", pagination);
                if (repairName != null) {
                    model.addAttribute("repairName", repairName);
                }
            }
        }

//        return FrontUtils.getTplPath(request, site.getSolutionPath(),
//                TPLDIR_SYNERGY_SOLD, SUPPLYDETAIL_SAMPLEPOLICY_CHECK);
        if (flag.equals("repairName")) {
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGY_SOLD, SUPPLYDETAIL_SAMPLEPOLICY_CHECK);
        } else {
            return FrontUtils.showSuccess(request, model, nextUrl);
        }

    }

    @Resource
    private SAmplePolicyApplyService sAmplePolicyApplyService;
    @Resource
    private SAmplePolicyService sAmplePolicyService;
    @Resource
    private SSoldNoteService sSoldNoteService;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;


}
