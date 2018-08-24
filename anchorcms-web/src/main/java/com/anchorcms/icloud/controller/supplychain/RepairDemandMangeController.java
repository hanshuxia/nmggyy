package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPOrderObj;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.payment.SSupplychainOrderObj;
import com.anchorcms.icloud.model.supplychain.SRepairAbility;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;
import com.anchorcms.icloud.model.synergy.*;
import com.anchorcms.icloud.service.supplychain.RepairDemandMangeService;
import com.anchorcms.icloud.service.supplychain.SLDOrderService;
import com.anchorcms.icloud.service.supplychain.SpareDemandObjService;
import com.anchorcms.icloud.service.supplychain.SpareDemandService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import payment.api.system.PaymentEnvironment;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;

import static com.anchorcms.common.constants.Constants.TPLDIR_PAYMENT;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * DESCRIPTION:
 * Author: ld
 * Date:2017/1/3.
 */
@Controller
public class RepairDemandMangeController {
    public static final String TPLDIR_SUPPLYCHAIN = "supplychain";
    //private static final String TPL_WXXQGL = "tpl.supplychainWxxqgl";
    //private static final String TPL_WXXQGL1 = "tpl.supplychainWxxqgl1";
    private static final String TPL_WXXQGL2 = "tpl.supplychainWxxqgl2";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";

    /**
     * @return 维修需求管理列表
     * @author ldong
     * @Date 2017/1/4 17:20
     */
    @RequestMapping(value = "/member/gylxt_wxxqgl_list.jspx")
    public String getRepairDemandList(String repairType,String queryTitle, Integer modelId,
                                      Integer queryChannelId, Integer pageNo, HttpServletRequest request,String evaluateUrl,
                                      ModelMap model, String state,String demandObjId,String evaluteValue,String flag) {
        return repairDemandlist(repairType, queryTitle, modelId, queryChannelId, TPL_WXXQGL2,evaluateUrl,
                pageNo, request, model, state,demandObjId,evaluteValue,flag);
    }

    /**
     * ld
     */
    protected String repairDemandlist(String repairType,String title, Integer modelId, Integer queryChannelId,
                                      String nextUrl,String evaluateUrl, Integer pageNo,HttpServletRequest request, ModelMap model,
                                      String state,String demandObjId,String evaluteValue,String flag) {
        if (state == null) {
            state = "00";
        }
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
        if (!StringUtils.isBlank(title)) {
            model.addAttribute("queryTitle", title);
        }
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("state", state);
        if(flag!=null&&flag.equals("talk")){
            repairDemandMangeService.setEvaluteValue(demandObjId,evaluteValue);
            return FrontUtils.showSuccess(request, model, evaluateUrl);
        }else{
            Pagination p = repairDemandMangeService.getRepairDemandPage(repairType,title, queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20, state);
            model.addAttribute("pagination", p);
            model.addAttribute("repairType", repairType);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SUPPLYCHAIN, nextUrl);
        }
    }

    /**
     * @return 维修需求列表删除功能
     * @author ldong
     * @Date 2017/1/6 11:09
     */
    @RequestMapping(value = "/member/repairDemand_delete.jspx")
    public String deleteRepairDemandById(String[] ids, HttpServletRequest request,
                                         String nextUrl, HttpServletResponse response, ModelMap model) {
        ids = ids[0].split(",");
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
        repairDemandMangeService.deleteRepairDemandByIds(ids);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * 维修需求编辑请求
     *
     * @param
     */
    private static final String REPAIRDEMAND_EDIT = "tpl.supplyChain_repairDemand_edit";
    private static final String REPAIRDEMAND_EDIT1 = "tpl.supplyChain_repairDemand_edit1";

    @RequestMapping(value = "/member/repairDemand_edit.jspx")
    public String editRepairDemandFromManagerList(Integer demandId, HttpServletRequest request,
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
        WebErrors errors = validateEdit(demandId, site, user, request);
        //判断是不是管理员
        //if (!user.getIsAdmin()) {
        //    return FrontUtils.showError(request, response, model, errors);
        //}
//        if (errors.hasErrors()) {
//            return FrontUtils.showError(request, response, model, errors);
//        }
        SRepairDemand demand = this.repairDemandMangeService.getRepairDemandById(demandId);
        Content content = demand.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        List<SRepairDemandObj> rdo = repairDemandMangeService.selcetByRepairDemandId(String.valueOf(demandId));
        model.addAttribute("OBJlist", rdo);
        model.addAttribute("demand", demand);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, REPAIRDEMAND_EDIT1);
    }






    /**
     * @return 众修需求管理编辑保存
     * @author ldong
     * @Date 2017/1/7 10:36
     */
    @RequestMapping(value = "/member/repair_demand_manage_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       //询价对象list相关的参数，Json串
                       String demandObjListJsonString, SRepairDemand sRepairDemand,
                       //cms表相关参数
                       String editor,int contentId,
                       //String nextUrl,
                       Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SRepairDemand sd = this.repairDemandMangeService.selectReapirDemand(sRepairDemand.getRepairId());
        sd.setRepairName(sRepairDemand.getRepairName());
        sd.setReleaseType(sRepairDemand.getReleaseType());
        sd.setStartDt(sRepairDemand.getStartDt());
        sd.setDeadlineDt(sRepairDemand.getDeadlineDt());
        sd.setAddrCity(sRepairDemand.getAddrCity());
        sd.setAddrProvince(sRepairDemand.getAddrProvince());
        sd.setAddrCounty(sRepairDemand.getAddrCounty());
        sd.setAddrStreet(sRepairDemand.getAddrStreet());
        sd.setIsUrgency(sRepairDemand.getIsUrgency());
        sd.setIsShow(sRepairDemand.getIsShow());
        sd.setReleaseType(sRepairDemand.getReleaseType());
        sd.setContact(sRepairDemand.getContact());
        sd.setTelephone(sRepairDemand.getTelephone());
        sd.setMobile(sRepairDemand.getMobile());
        sd.setFax(sRepairDemand.getFax());
        sd.setState(sRepairDemand.getState());
        sd.setEmail(sRepairDemand.getEmail());
        sd.setRepairType(sRepairDemand.getRepairType());
        Content c = sd.getContent();
        sd.setContent(c);
        sd.setContentId(String.valueOf(contentId));
        c.setSite(site);
        ContentExt ext = c.getContentExt();
        ext.setTitle(sd.getRepairName());
        ext.setAuthor(user.getUsername());
        ext.setDescription("众修需求编辑");
        ContentTxt t = c.getContentTxt();

        //若表中无contentTxt的数据，新增一条
        if(editor == null){
            editor = "";
        }
        if(t == null){
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt(editor);
            t = contentTxt;
            contentTxtMng.save(t, c);
        }else{
            t.setTxt(editor);
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
        this.repairDemandMangeService.update(sd, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs, channelId, typeId, user, charge, true, demandObjListJsonString);
        //跳转至需求管理列表
        String nextUrl = "gylxt_wxxqgl_list.jspx?state=1";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
   /* *//**
     * @Author ld
     * @Date 2017/1/3 0003 18:26
     * 需求管理编辑请求 new
     *//*
    private static final String REPAIRDEMAND_EDIT_NEW = "tpl.supplyChain_repairDemand_edit_new";

    @RequestMapping(value = "/member/new_demand_editor.jspx")
    public String editor(Integer ids, HttpServletRequest request,
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
        WebErrors errors = validateEdit(ids, site, user, request);
        if (!user.getIsAdmin()) {
            return FrontUtils.showError(request, response, model, errors);
        }
//        if (errors.hasErrors()) {
//            return FrontUtils.showError(request, response, model, errors);
//        }
        SRepairDemand demand = this.repairDemandMangeService.getRepairDemandById(ids);
        Content content = demand.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        List<SRepairDemandObj> rdo = repairDemandMangeService.selcetByRepairDemandId(String.valueOf(ids));
        model.addAttribute("OBJlist", rdo);
        model.addAttribute("demand", demand);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, REPAIRDEMAND_EDIT_NEW);
    }*/



    /**
     * @author ldong
     * @Date 2017/1/4 16:46
     * @return  预览界面
     */
    private static final String REPAIRDEMAND_LIST_DETAIL_NEW = "tpl.supplyChain_repairDemand_list_detail_new";

    @RequestMapping(value = "/member/repairDemand_list_detail.jspx")
    public String view(Integer ids, HttpServletRequest request,
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
        WebErrors errors = validateEdit(ids, site, user, request);
        //if (!user.getIsAdmin()) {
        //    return FrontUtils.showError(request, response, model, errors);
        //}
//        if (errors.hasErrors()) {
//            return FrontUtils.showError(request, response, model, errors);
//        }
        SRepairDemand demand = this.repairDemandMangeService.getRepairDemandById(ids);
        Content content = demand.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        List<SRepairDemandObj> rdo = demand.getsRepairDemandObj();
        model.addAttribute("OBJlist", rdo);
        model.addAttribute("demand", demand);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, REPAIRDEMAND_LIST_DETAIL_NEW);
    }


    /**
     * @author
     * @Date 2017/1/4 16:46
     * @return 明细界面修改版
     */
    private static final String REPAIRDEMAND_LIST_DETAIL = "tpl.supplyChain_repairDemand_list_detail";
    private static final String REPAIRDEMAND_LIST_DETAIL_PREVIEW = "tpl.supplyRepair_manager_preview";

    @RequestMapping(value = "/member/repairDemand_list_preview.jspx")
    public String preview(Integer ids, HttpServletRequest request,
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
        WebErrors errors = validateEdit(ids, site, user, request);
        if (!user.getIsAdmin()) {
            return FrontUtils.showError(request, response, model, errors);
        }
//        if (errors.hasErrors()) {
//            return FrontUtils.showError(request, response, model, errors);
//        }
        SRepairDemand demand = this.repairDemandMangeService.getRepairDemandById(ids);
        Content content = demand.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        List<SRepairDemandObj> rdo = repairDemandMangeService.selcetByRepairDemandId(String.valueOf(ids));
        model.addAttribute("OBJlist", rdo);
        model.addAttribute("sRepairRes", demand);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, REPAIRDEMAND_LIST_DETAIL_PREVIEW);
    }
    /**
     * @return
     * @author ldong
     * @Date 2017/1/4 18:38
     * 更改众修需求的状态
     */
    @RequestMapping(value = "/member/repairDemand_setState.jspx")
    public String setStateRepairDemandById(String ids, HttpServletRequest request,
                                           String nextUrl, HttpServletResponse response, ModelMap model, String callBack) {
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
        repairDemandMangeService.setStateByRepairDemandByIds(user,ids, callBack);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * 维修需求管理优选请求
     */
/*    private final static String TPL_WXXQDOCHOOSE = "supplychain_wxxqyx";
    @RequestMapping(value = "/member/repairDemand_choose.jspx")
    public String repairDemand_doChoose(String ids, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        List<SRepairQuote> list = repairDemandMangeService.getDoChoose(ids);
        model.addAttribute("quote", list);
        model.addAttribute("demandId", ids);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, TPL_WXXQDOCHOOSE);
    }*/

    /**
     * @author ldong
     * @Date 2017/1/9 11:03
     * @return
     * 众修需求管理优选界面优选功能。
     */
 /*   @RequestMapping(value = "/member/repairDemand_Changechoose.jspx")
    public String repairDemand_ChangeChoose(String demandId, String nextUrl, String id, String callState, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        // id=quoteId[0];
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        repairDemandMangeService.editChooseState(id, callState, demandId);
        nextUrl = "repairDemand_choose.jspx?ids="+demandId;
        return FrontUtils.showSuccess(request, model, nextUrl);
    }*/


    /**
     * @author ldong
     * @Date 2017/1/9 11:04
     * @return
     * 下单请求 打开下单界面
     */
    private final static String TPL_WXXQDOORDER = "supplychain_wxxqxd";

  /*  @RequestMapping(value = "/member/repairDemand_goOrder.jspx")
    public String repairDemand_goOrder(String demandId, String demandQuoteID, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        List list = repairDemandMangeService.getGoOrder(demandId, demandQuoteID);
        SRepairQuote sq = repairDemandMangeService.getQuoteEntity(demandQuoteID);
        model.addAttribute("quote", list);
        model.addAttribute("demandId", demandId);
        model.addAttribute("quoteEntity", sq);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, TPL_WXXQDOORDER);
    }*/

    /**
     * @return
     * @author ldong
     * @Date 2017/1/4 19:27
     * 下单操作
     */
   /* @RequestMapping(value = "/member/repairDemand_doOrder.jspx")
    public String repairDemand_doOrder(String demandId, String demandQuoteId, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        String nextUrl = "gylxt_wxxqgl_list.jspx?state=4";
        this.repairDemandMangeService.doOrder(demandId, demandQuoteId);
        return FrontUtils.showSuccess(request, model, nextUrl);

    }*/


    /**
     * @author ldong
     * @Date 2017/1/4 17:00
     * @return 维修需求管理预览详情
     */
    public static final String SUPPLYCHAIN_REQUIRE_DEMAND_DETAIL = "tpl.supplychain_require_demand_detail";

    @RequiresPermissions("member:supplychain_demand_detail")
    @RequestMapping("/member/supplychain_require_demand_detail.jspx")
    public String reauireDemandEdit(String ids, HttpServletRequest request, ModelMap model, HttpServletResponse response) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (true) {
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
            model.addAttribute("site", site);
            model.addAttribute("channelList", channelList);
            model.addAttribute("sessionId", request.getSession().getId());
            SRepairDemand sRepairDemand = this.repairDemandMangeService.selectReapirDemand(ids);
            List<SRepairDemandObj> sRepairDemandObjList = sRepairDemand.getsRepairDemandObj();
            Content content = sRepairDemand.getContent();
            model.addAttribute("sRepairDemand", sRepairDemand);
            model.addAttribute("sRepairDemandObjList", sRepairDemandObjList);
            model.addAttribute("rdolist", sRepairDemandObjList);
            model.addAttribute("content",content);
            return FrontUtils.getTplPath(request, site.getSolutionPath(), TPLDIR_SUPPLYCHAIN, SUPPLYCHAIN_REQUIRE_DEMAND_DETAIL);
        } else {
            WebErrors errors = WebErrors.create(request);
            errors.addErrorCode("error.uploadMoreNumber", user.getGroup().getId());
            return FrontUtils.showError(request, response, model, errors);
        }
    }


    private WebErrors validateEdit(Integer id, CmsSite site, CmsUser user,
                                   HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldOpt(errors, site, user, new Integer[]{id})) {
            return errors;
        }
        return errors;
    }

    private boolean vldOpt(WebErrors errors, CmsSite site, CmsUser user,
                           Integer[] ids) {
        for (Integer id : ids) {
            if (errors.ifNull(id, "id")) {
                return true;
            }
            Content c = contentMng.findById(id);
            // 数据不存在
            if (errors.ifNotExist(c, Content.class, id)) {
                return true;
            }
            // 非本用户数据
            if (!c.getUser().getUserId().equals(user.getUserId())) {
                errors.noPermission(Content.class, id);
                return true;
            }
            // 非本站点数据
            if (!c.getSite().getSiteId().equals(site.getSiteId())) {
                errors.notInSite(Content.class, id);
                return true;
            }
            // 文章级别大于0，不允许修改
            if (c.getCheckStep() > 0) {
                errors.addErrorCode("member.contentChecked");
                return true;
            }
        }
        return false;
    }
    /**
     * @author ldong
     * @Date 2017/1/10 10:54
     * @return
     * 后台页面 对报价进行优选的 报价列表
     * ajax 获取优选界面
     */

    @RequestMapping(value = "/member/supplychain_demandQuoteList_getListAjax.jspx", method = RequestMethod.POST)
    public void getListAjax(String demandId, Integer pageNo, HttpServletRequest request, HttpServletResponse response){
        // response.setHeader("contentType", "application/json; charset=UTF-8");
        response.addHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        CmsSite site = CmsUtils.getSite(request);

        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
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
        //判断一下demandId
        if (demandId == null) {
            return;
        }
        if(pageNo == null){
            pageNo = 1;
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.print(this.repairDemandMangeService.getQuoteListJson(Integer.valueOf(demandId),pageNo,8));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author ldong
     * @Date 2017/1/10 14:22
     * @return
     * 优选界面 取消优选 优选 操作 ajax
     * oggleType: 1为进行优选 0为取消优选
     */
    @RequestMapping(value = "/member/supplychain_demandQuoteList_toggleSelectAjax.jspx", method = RequestMethod.POST)
    public void toggleSelect(Integer demandId, String quoteIds, String toggleType, HttpServletRequest request,
                             HttpServletResponse response){
        boolean isSelect;
        response.setHeader("contentType", "text/json; charset=utf-8");
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
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
        //判断一下demandId是否为空
        if (demandId == null) {
            return;
        }
        //判断一下demand是否为用户公司的 不能优选非本公司的需求的 报价
        if (!user.getCompany().getCompanyId().equals(this.repairDemandMangeService.getRepairDemandById(demandId).getCompanyId())) {
            return;
        }
        //判断优选 or 取消优选， 参数不存在则直接返回
        if("1".equals(toggleType)){
            isSelect= true;
        }else if("0".equals(toggleType)){
            isSelect = false;
        }else {
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            if(isSelect){
                writer.print("{\"rows\":\""+this.repairDemandMangeService.selectQuotes(quoteIds)+"\"}");
            }else{
                writer.print("{\"rows\":\""+this.repairDemandMangeService.cancelSelectedQuotes(quoteIds)+"\"}");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @author ldong
     * @Date 2017/1/10 15:55
     * @return
     * 对已优选报价进行下单的 获取下单列表 ajax
     */
    @RequestMapping(value = "/member/supplychain_demandQuoteOrder_getListAjax.jspx", method = RequestMethod.POST)
    public void getOrderListAjax(String quoteId, HttpServletRequest request, HttpServletResponse response){
        // response.setHeader("contentType", "text/json; charset=utf-8");
        response.addHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
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
        //判断一下quoteId
        if (quoteId == null) {
            return;
        }
        //判断一下demand是否为用户公司的 不能对 非本公司的需求的报价 进行下单
        if (!user.getCompany().getCompanyId().equals(this.repairDemandMangeService.getQuoteEntity(quoteId).getDemand().getCompanyId())) {
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.print(this.repairDemandMangeService.getOrderListJson(quoteId));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @author: ldong
     * @desciption 2017/1/10
     * 进行下单的业务逻辑处理【1.update报价表(状态位+分配量) 2.update需求表状态为已下单】
     */
    @RequestMapping(value = "/member/supplychain_demandQuoteOrder_submitOrderAjax.jspx")
    public void excuteOrder(String orderJson, String quoteId,Integer demandId,String demandQuoteID, HttpServletRequest request,
                            HttpServletResponse response, ModelMap model){
        response.setHeader("contentType", "text/json; charset=utf-8");
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
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
        //判断一下quoteId
        if (quoteId == null) {
            return;
        }
        //判断一下demand是否为用户公司的 不能对 非本公司的需求的报价 进行下单
        if (!user.getCompany().getCompanyId().equals(this.repairDemandMangeService.getQuoteEntity(quoteId).getDemand().getCompanyId())) {
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            if(this.repairDemandMangeService.excuteOrder(orderJson,quoteId)){
                writer.print("{\"rows\":\"1\"}");
            }
            writer.flush();
            writer.close();
            SRepairDemand demand=repairDemandMangeService.getRepairDemandById(demandId);
            SRepairQuote quote = repairDemandMangeService.getQuoteEntity(quoteId);

            SRepairDemandObj demandObj;
//        SDemandObj demandObj;
//        SDemandQuoteObj quoteObj= sDemandQuoteService.getDemandQuoteListJson(demandQuoteId);
//       List<SDemandQuoteObj> quoteObj = sDemandQuoteService.byDemandQuoteObjId(demandQuoteId);
            List<SRepairAbility> quoteObj = quote.getsRepairAbility();
            List<SSupplychainOrderObj> li = new ArrayList<SSupplychainOrderObj>();
            for(SRepairAbility bean : quoteObj){
                SSupplychainOrderObj o = new SSupplychainOrderObj();
                o.setObjPrice(bean.getOffer());
//            o.setObjPrice(bean.getOffer());
                String demandObjId = bean.getDemandId();
                demandObj= repairDemandMangeService.byDemandObjId(demandObjId);
                o.setObjName(demandObj.getRepairName());
                o.setObjNum(demandObj.getRepairNum());
                String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
                o.setOrderObjId(uid);
//            o.setOrderObjId(String.valueOf(bean.getDemandQuoteObjid()));
                this.repairDemandMangeService.saveSPOrderObj(o);
                li.add(o);
            }
            SSupplychainOrder buy = new SSupplychainOrder();
            buy.setSOrderObjList(li);
//        buy.setOrderId(quote.getDemandQuoteId());
            buy.setRepairName(demand.getRepairName());
            buy.setBuyContact(demand.getContact());
            buy.setBuycompany(demand.getCompany());
            buy.setSupplycompany(quote.getCompany());
            buy.setBuyMobile(demand.getMobile());
            buy.setSupplyContact(quote.getContact());
            buy.setSupplyMobile(quote.getMobile());
            buy.setOperator(quote.getOperatorId());
            buy.setPrice(buy.getSPOrderObjPrice());
            buy.setNum(buy.getSPOrderObjNum());
            buy.setOrderDt(new Date(System.currentTimeMillis()));
            //buy.setpayerName;
            //订单号
            String guId = null;
            try {
                guId = GUIDGenerator.genGUID();
            } catch (Exception e) {
                e.printStackTrace();
            }
            buy.setOrderId(guId);
            buy.setState("0");
//        this.getSession().save(buy);
            repairDemandMangeService.saveOrder(buy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @author liuyang
     * @Date 2017/5/2 14:12
     * @return 维修需求订单生成
     */
    @RequestMapping(value = "/member/gylxt_orderdo.jspx")
    public String orderdo(HttpServletRequest request, HttpServletResponse response,
                          ModelMap model,Integer demandId,String demandQuoteID) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SRepairDemand demand=repairDemandMangeService.getRepairDemandById(demandId);
        SRepairQuote quote = repairDemandMangeService.getQuoteEntity(demandQuoteID);

        SRepairDemandObj demandObj;
//        SDemandObj demandObj;
//        SDemandQuoteObj quoteObj= sDemandQuoteService.getDemandQuoteListJson(demandQuoteId);
//       List<SDemandQuoteObj> quoteObj = sDemandQuoteService.byDemandQuoteObjId(demandQuoteId);
        List<SRepairAbility> quoteObj = quote.getsRepairAbility();
        List<SSupplychainOrderObj> li = new ArrayList<SSupplychainOrderObj>();
        for(SRepairAbility bean : quoteObj){
            SSupplychainOrderObj o = new SSupplychainOrderObj();
            o.setObjPrice(bean.getOffer());
//            o.setObjPrice(bean.getOffer());
            String demandObjId = bean.getDemandId();
            demandObj= repairDemandMangeService.byDemandObjId(demandObjId);
            o.setObjName(demandObj.getRepairName());
            o.setObjNum(demandObj.getRepairNum());
            String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
            o.setOrderObjId(uid);
//            o.setOrderObjId(String.valueOf(bean.getDemandQuoteObjid()));
            this.repairDemandMangeService.saveSPOrderObj(o);
            li.add(o);
        }
        SSupplychainOrder buy = new SSupplychainOrder();
        buy.setSOrderObjList(li);
//        buy.setOrderId(quote.getDemandQuoteId());
        buy.setRepairName(demand.getRepairName());
        buy.setBuyContact(demand.getContact());
        buy.setBuycompany(demand.getCompany());
        buy.setSupplycompany(quote.getCompany());
        buy.setBuyMobile(demand.getMobile());
        buy.setSupplyContact(quote.getContact());
        buy.setSupplyMobile(quote.getMobile());
        buy.setOperator(quote.getOperatorId());
        buy.setPrice(buy.getSPOrderObjPrice());
        buy.setNum(buy.getSPOrderObjNum());
        buy.setOrderDt(new Date(System.currentTimeMillis()));
        //buy.setpayerName;
        //订单号
        String guId = null;
        try {
            guId = GUIDGenerator.genGUID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        buy.setOrderId(guId);
        buy.setState("0");
//        this.getSession().save(buy);
        repairDemandMangeService.saveOrder(buy);
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SOFTWARE_PAY);*/
        //String nextUrl = "/yzyjyzxYzyck/index.jhtml";
        String nextUrl = "/member/gylxt_wxxqgl_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author liuyang
     * @Date 2017/5/6 9:21
     * @return 订单明细
     */
    String SUPPLYCHAIN_BUY = "tpl.supplychain_order_detail";

    @RequestMapping(value = "/member/supplychain_order_detail.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String institutionID, String paymentNo, String orderNo, String payerID,
                       String payerName, String usage, String remark, String notificationURL, String payees,
                       String bankID, String bankName, Integer accountType, String cardType, Integer managerId, String label, String count, String orderId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        institutionID = "002818";
        SSupplychainOrder order = spOrderService.findOrderById(orderId);
//        amount = order.getPrice();
//        Map attributes = spOrderService.pay(orderId, user, institutionID, paymentNo, orderNo, fee, payerID, payerName, usage, remark, notificationURL, payees, bankID, bankName, accountType, cardType);
//        model.addAttribute("message", attributes.get("message"));
//        model.addAttribute("signature", attributes.get("signature"));
//        model.addAttribute("txCode", attributes.get("txCode"));
//        model.addAttribute("txName", attributes.get("txName"));
//        model.addAttribute("Flag", attributes.get("flag"));
        model.addAttribute("action", PaymentEnvironment.paymentURL);
        model.addAttribute("institutionID", institutionID);//机构号码
        model.addAttribute("managerId", managerId);
        model.addAttribute("orderNo", orderNo);//订单号
        model.addAttribute("paymentNo", paymentNo);//支付流水号
//        model.addAttribute("amount", amount);//付款金额
//        model.addAttribute("fee", fee);//支付服务手续费
        model.addAttribute("payerID", payerID);//付款者ID
        model.addAttribute("payerName", payerName);
        model.addAttribute("label", label);//资金用途
        model.addAttribute("remark", remark);//订单描述
        model.addAttribute("payees", payees);//收款人
        model.addAttribute("bankID", bankID);//银行ID
        model.addAttribute("bankName", bankName);//银行
        model.addAttribute("accountType", accountType);//账号类型
        model.addAttribute("cardType", cardType);//卡类型
        model.addAttribute("order", order);
        List<SSupplychainOrderObj> list = order.getSOrderObjList();
        model.addAttribute("orderList", list);
        model.addAttribute("price", order.getPrice());

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, SUPPLYCHAIN_BUY);


    }
    @Resource
    private SpareDemandService spareDemandService;
    @Resource
    private RepairDemandMangeService repairDemandMangeService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SpareDemandObjService spareDemandObjService;

    @Resource
    protected ContentMng contentMng;
    @Resource
    private SLDOrderService spOrderService;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
