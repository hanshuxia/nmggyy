package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTxtMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.supplychain.MasterManagerService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import java.util.UUID;

import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Created by jxd on 2016/12/26.
 */
@Controller
public class MasterManagerController {

  /**
   * @author dongxuecheng
   * @Date 2017/1/4 11:51
   * @return
   * @description 维修资源发布界面
   */
    public static final String TPLDIR_SYNERGY_REPAIRE = "supplychain";
    public static final String SUPPLYDETAIL_REPAIRE = "tpl.supplychainRepair_relese";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/Repair_relese.jspx")
    public String Repair(HttpServletRequest request, HttpServletResponse response,
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
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_REPAIRE, SUPPLYDETAIL_REPAIRE);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:51
     * @return
     * @description 维修资源发布界面
     */
    public static final String SUPPLYDETAIL_REPAIRE_WINDOWS = "tpl.supplychainRepair_relese_windows";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/Repair_relese_windows.jspx")
    public String Repair_windows(HttpServletRequest request, HttpServletResponse response,
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
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(99));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_REPAIRE, SUPPLYDETAIL_REPAIRE_WINDOWS);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:51
     * @return
     * @description //维修资源发布保存界面
     */
    @RequestMapping(value = "/member/Repair_relese_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,String repairName,String requestType,
                       String addrProvince,String addrCity,String addrCounty,String addrStreet, String goodAt,String repairPrice,
                        String workYear, String mobile,String telephone,String detailDesc,String repairMachine,String contact,String email,String fax,
                       Integer modelId,Integer channelId,String nextUrl,String status,Date deadlineDt,
                       String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs){

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

       java.sql.Date date = new java.sql.Date(System.currentTimeMillis());//获取当前时间

        SRepairRes sRepairResEntity=new SRepairRes();
        String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
        sRepairResEntity.setRepairId(uid);
        sRepairResEntity.setRepairName(repairName);
        sRepairResEntity.setRepairPrice(repairPrice);
        sRepairResEntity.setAddrProvince(addrProvince);
        sRepairResEntity.setAddrCity(addrCity);
        sRepairResEntity.setAddrCounty(addrCounty);
        sRepairResEntity.setAddrStreet(addrStreet);
        sRepairResEntity.setGoodAt(goodAt);
        sRepairResEntity.setWorkYear(workYear);
        sRepairResEntity.setMobile(mobile);
        sRepairResEntity.setTelephone(telephone);
//        sRepairResEntity.setDescription(detailDesc);
        sRepairResEntity.setReleaseId(user.getUserId().toString());
        sRepairResEntity.setReleaseName(user.getRealname());
//        sRepairResEntity.setReleaseDt(date);
        sRepairResEntity.setRepairMachine(repairMachine);
        sRepairResEntity.setCreaterId(user.getUserId().toString());
        sRepairResEntity.setCreateDt(date);
        sRepairResEntity.setDeadlineDt(deadlineDt);//截止时间暂时不知道如何待定
        sRepairResEntity.setStatus(status);
        sRepairResEntity.setScompany(user.getCompany());
        sRepairResEntity.setFax(fax);
        sRepairResEntity.setEmail(email);
        sRepairResEntity.setContact(contact);
        sRepairResEntity.setRepairType(requestType);

        Content c = new Content();
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
        ext.setTitle(repairName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("维修资源发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(detailDesc);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        c=masterManagerService.supplychain_save(sRepairResEntity,c,ext,t,channelId,typeId,user,true,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,picDescs);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:51
     * @return
     * @description //维修资源管理界面
     */
    public static final String TPLDIR_SYNERGY_REPAIR_MANAGER = "supplychain";
    public static final String SUPPLYDETAIL_REPAIR_MANAGER = "tpl.supplychainRepair_manager";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/Repair_manager.jspx")
    public String Repair_manager(String companyId, java.util.Date releaseDt, java.util.Date deadlineDt, Integer demandId, String inquiryTheme,String repairType,
                       String status, String payType, String statusType, Integer modelId, Integer queryChannelId, Integer pageNo,String flag,
                       HttpServletRequest request, ModelMap model,String id,String talk) {
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
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        String nextUrl="/member/Repair_manager.jspx";
        if(flag!=null&&flag.equals("delete")){//删除功能
            nextUrl="/member/Repair_manager.jspx?statusType=1&status="+status;
            masterManagerService.delete(id);
            return FrontUtils.showSuccess(request, model, nextUrl);
        }else if(flag!=null&&flag.equals("reback")){//撤回功能
            nextUrl="/member/Repair_manager.jspx?statusType=1&status"+status;
            masterManagerService.reback(id);
            return FrontUtils.showSuccess(request, model, nextUrl);
        }else if(flag!=null&&flag.equals("relece")){//发布功能
            nextUrl="/member/Repair_manager.jspx?statusType=2&status="+status;
            masterManagerService.relece(id,user);
            return FrontUtils.showSuccess(request, model, nextUrl);
        }else
            companyId = user.getCompany().getCompanyId();
        Pagination p = masterManagerService.getPageForMember(repairType,queryChannelId, site.getSiteId(), modelId, companyId, user.getIsAdmin(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme, status,payType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("releaseDt", releaseDt);
        model.addAttribute("deadlineDt", deadlineDt);
        model.addAttribute("demandId", demandId);
        model.addAttribute("repairType", repairType);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("status", status);
        model.addAttribute("statusType", statusType);
        model.addAttribute("payType", payType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_REPAIR_MANAGER, SUPPLYDETAIL_REPAIR_MANAGER);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:51
     * @return
     * @description //维修资源详情界面
     */
    public static final String TPLDIR_SYNERGY_REPAIR_MANAGER_DETAILED = "supplychain";
    public static final String SUPPLYDETAIL_REPAIR_MANAGER_DETAILED = "tpl.supplyRepair_manager_detailed";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/Repair_manager_detailed.jspx")
    public String Repair_manager_detailed(HttpServletRequest request, HttpServletResponse response,
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
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        SRepairRes sRepairResEntity=masterManagerService.getSRepairResEntity(id);
        Content content=sRepairResEntity.getContent();
        model.addAttribute("sRepairResEntity", sRepairResEntity);
        model.addAttribute("content", content);
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_REPAIR_MANAGER_DETAILED, SUPPLYDETAIL_REPAIR_MANAGER_DETAILED);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/4 13:44
     * @return
     * @description 资源管理修改界面
     */

    @RequestMapping(value = "/member/Repair_relese_change.jspx")
    public String Repair_relese_change(HttpServletRequest request,String status, HttpServletResponse response, ModelMap model,String repairName,String requestType,
                                       String addrProvince,String addrCity,String addrCounty,String addrStreet, String goodAt,
                                       String workYear, String mobile, String detailDesc,Date deadlineDt,String telephone,
                                       String nextUrl,String repairId,String releaseName,String repairPrice,Integer channelId,
                                       String[] attachmentPaths,String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs){

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());//获取当前时间

        SRepairRes sRepairResEntity = masterManagerService.getSRepairResEntity(repairId);
        sRepairResEntity.setRepairId(repairId);
        sRepairResEntity.setRepairName(repairName);
        sRepairResEntity.setReleaseName(user.getRealname());
        sRepairResEntity.setAddrProvince(addrProvince);
        sRepairResEntity.setAddrCity(addrCity);
        sRepairResEntity.setAddrCounty(addrCounty);
        sRepairResEntity.setAddrStreet(addrStreet);
        sRepairResEntity.setGoodAt(goodAt);
        sRepairResEntity.setWorkYear(workYear);
        sRepairResEntity.setMobile(mobile);
      //  sRepairResEntity.setDescription(detailDesc);
        sRepairResEntity.setDeadlineDt(deadlineDt);
        sRepairResEntity.setRepairType(requestType);
        sRepairResEntity.setRepairPrice(repairPrice);
        sRepairResEntity.setTelephone(telephone);

        Content c = sRepairResEntity.getContent();
        c.setSite(site);
        ContentExt ext = c.getContentExt();
        ext.setTitle(repairName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("维修资源发布");
        ContentTxt t = c.getContentTxt();

        //若表中无contentTxt的数据，新增一条
        if(detailDesc == null){
            detailDesc = "";
        }
        if(t == null){
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt(detailDesc);
            t = contentTxt;
            contentTxtMng.save(t, c);
        }else{
            t.setTxt(detailDesc);
        }

        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        //masterManagerService.update(sRepairResEntity);
        masterManagerService.update(sRepairResEntity,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,null,true);
        nextUrl="/member/Repair_manager.jspx?statusType=1&status="+status;
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:54
     * @return
     * @description     //维修资源明细界面
     */
    public static final String TPLDIR_SYNERGY_REPAIR_MANAGER_VIEW = "supplychain";
    public static final String SUPPLYDETAIL_REPAIR_MANAGER_VIEW = "tpl.supplyRepair_manager_view";
    @RequestMapping("/member/Repair_manager_view.jspx")
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
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);

        SRepairRes sRepairResEntity=masterManagerService.getSRepairResEntity(id);
        model.addAttribute("sRepairResEntity", sRepairResEntity);
        Content content=sRepairResEntity.getContent();
        model.addAttribute("content", content);

        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_REPAIR_MANAGER_VIEW, SUPPLYDETAIL_REPAIR_MANAGER_VIEW);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:54
     * @return
     * @description//维修资源预览界面
     */
    public static final String TPLDIR_SYNERGY_REPAIR_MANAGER_PREVIEW = "supplychain";
    public static final String SUPPLYDETAIL_REPAIR_MANAGER_PREVIEW = "tpl.supplyRepair_manager_preview";
    @RequestMapping("/Repair_manager_preview.jspx")
    public String Repair_manager_preview(HttpServletRequest request, HttpServletResponse response,
                                          ModelMap model,String id) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
//        if (!mcfg.isMemberOn()) {
//            return FrontUtils.showMessage(request, model, "member.memberClose");
//        }
//        if (user == null) {
//            return FrontUtils.showLogin(request, model, site);
//        }
        SRepairRes sRepairRes=masterManagerService.getSRepairResEntity(id);
        Content content = sRepairRes.getContent();
        // 获得本站栏目列表
//        Set<Channel> rights = user.getGroup().getContriChannels();
//        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
//        List<Channel> channelList = Channel.getListForSelect(topList, rights,
//                true);
        String userId = sRepairRes.getCreaterId();
        CmsUser cmsuser = userDao.findById(Integer.parseInt(userId));

        String userName = cmsuser.getUsername();
        model.addAttribute("userName",userName);



        model.addAttribute("sRepairRes", sRepairRes);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("user", user);
//        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(99));
        model.addAttribute("repairId",id);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_REPAIR_MANAGER_PREVIEW, SUPPLYDETAIL_REPAIR_MANAGER_PREVIEW);
    }


    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:51
     * @return
     * @description //预览维修资源界面
     */
    public static final String TPLDIR_SYNERGY_REPAIR_REPAIRE = "supplychain";
    public static final String SUPPLYDETAIL_REPAIR_REPAIRE = "tpl.supplychainpreview_Repair_relese";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/preview_Repair_relese.jspx")
    public String preview_Repair_relese(HttpServletRequest request, HttpServletResponse response,
                                        ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);//为前台模板创建公共数据
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_REPAIR_REPAIRE, SUPPLYDETAIL_REPAIR_REPAIRE);
    }


  /*
  * 已重新修改
  * //**
     * @author dongxuecheng
     * @Date 2017/1/7 12:15
     * @return
     * @description 维修资源询价
     *//*
    public static final String TPLDIR_SYNERGY_INQUIRY_PRICE_MANAGER = "supplychain";
    public static final String SUPPLYDETAIL_INQUIRY_PRICE_MANAGER = "tpl.supplychaininquiry_price_manager";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/inquiry_price_manager.jspx")
    public String inquiry_price_manager(Integer UserId, java.util.Date releaseDt, java.util.Date deadlineDt, Integer demandId, String inquiryTheme,
                                        String status, String payType, String statusType, Integer modelId, Integer queryChannelId, Integer pageNo,String flag,
                                        HttpServletRequest request, ModelMap model,String id) {
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

        Pagination p = masterManagerService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(), user.getUserId(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme, status,payType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("releaseDt", releaseDt);
        model.addAttribute("deadlineDt", deadlineDt);
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("status", status);
        model.addAttribute("statusType", statusType);
        model.addAttribute("payType", payType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_INQUIRY_PRICE_MANAGER, SUPPLYDETAIL_INQUIRY_PRICE_MANAGER);
    }*/


    /**
     * @author dongxuecheng
     * @Date 2017/1/7 17:25
     * @return Sspare_manager.html
     * @description 备品备件管理
     *
     */

    public static final String TPLDIR_SYNERGY_SSPARE_MANAGER = "supplychain";
    public static final String SUPPLYDETAIL_SSPARE_MANAGER = "tpl.supplychaininSspare_manager";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/Sspare_manager.jspx")
    public String Sspare_manager(Integer UserId, java.util.Date releaseDt, java.util.Date deadlineDt, Integer demandId, String inquiryTheme,
                                        String status, String payType, String statusType, Integer modelId, Integer queryChannelId, Integer pageNo,String flag,
                                        HttpServletRequest request, ModelMap model,String id) {
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

        if(flag!=null&&flag.equals("pass")){//通过
            masterManagerService.pass(id);
        }else if(flag!=null&&flag.equals("goback")){//驳回
            masterManagerService.goback(id);
        }

        Pagination p = masterManagerService.getSpare(queryChannelId, site.getSiteId(), modelId, user.getUserId(), user.getUserId(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme, status,payType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("releaseDt", releaseDt);
        model.addAttribute("deadlineDt", deadlineDt);
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("status", status);
        model.addAttribute("statusType", statusType);
        model.addAttribute("payType", payType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_SSPARE_MANAGER, SUPPLYDETAIL_SSPARE_MANAGER);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/9 8:56
     * @return  Sspare_manager_check。html
     * @description备品备件上传审核
     */
    public static final String SUPPLYDETAIL_SSPARE_MANAGER_CHECK = "tpl.supplychaininSspare_manager_check";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/Sspare_manager_check.jspx")
    public String Sspare_manager_check(Integer UserId, java.util.Date releaseDt, java.util.Date deadlineDt, Integer demandId, String inquiryTheme,
                                 String status, String payType, String statusType, Integer modelId, Integer queryChannelId, Integer pageNo,String flag,
                                 HttpServletRequest request, ModelMap model,String id,String spareName) {
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
        if(status == null){
            status = "2";
        }

        if(flag!=null&&flag.equals("pass")){//通过
            masterManagerService.pass(id);
        }else if(flag!=null&&flag.equals("goback")){//驳回
            masterManagerService.goback(id);
        }

        Pagination p = masterManagerService.getSpare_chexk(queryChannelId, site.getSiteId(), modelId, user.getUserId(), user.getUserId(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme, status,payType,statusType,spareName);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("releaseDt", releaseDt);
        model.addAttribute("deadlineDt", deadlineDt);
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("status", status);
        model.addAttribute("statusType", statusType);
        model.addAttribute("payType", payType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_SSPARE_MANAGER, SUPPLYDETAIL_SSPARE_MANAGER_CHECK);
    }



    /**
     * @author 苏和 13739980760
     * @Date 2017/1/14 16:14
     * @return
     * 根据id修改备品备件状态状态（通过、驳回）
     */
    @RequestMapping("/member/sSpareMdyState.jspx")
    public String modifyDemandStatById(String sparepartsId, String status, String nextUrl,String backReason,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if (backReason != null) {
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
        if (sparepartsId != null) {
            sparepartsId = java.net.URLDecoder.decode(sparepartsId, "utf-8");
            masterManagerService.mdyRepairDemandStateById(sparepartsId, status,backReason);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/14 16:15
     * @return
     * 根据id批量修改备品备件状态（通过、驳回）
     */


    @RequestMapping("/member/sSpareSetState.jspx")
    public String setDemandStatByIds(String sparepartsId, String status, String nextUrl,String backReason,
                                     HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if (backReason != null) {
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
        if (sparepartsId != null) {

            masterManagerService.setRepairDemandStateByIds(sparepartsId, status,backReason);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    private MasterManagerService masterManagerService;

    @Resource
    private CmsModelMng cmsModelMng;

    @Resource
    private ContentTypeMng contentTypeMng;

    @Resource
    private ChannelMng channelMng;

    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private CmsUserDao userDao;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
