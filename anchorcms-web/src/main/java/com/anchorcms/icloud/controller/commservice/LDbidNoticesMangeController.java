package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.dao.commservice.LDTenderNoticesManageDao;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.service.commservice.LDTenderNoticesManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * DESCRIPTION:招标公告管理Controller
 * Author: DELL
 * Date:2017/1/13.
 */
@Controller
public class LDbidNoticesMangeController {
    private static final String TPLDIR_COMMONSERVICE = "commservice";
    /**
     * @author ldong
     * @Date 2017/1/13 10:17
     * @return 招标公告管理
     */
    private final static String TPL_BIDS_NOTICES_MANAGE_LD = "tpl.commonservice_tender_manage";

    @RequestMapping(value = "/member/commonservice_tender_manage_list.jspx")
    public String getRepairDemandList(String title, Integer modelId,
                                      Integer queryChannelId, Integer pageNo, HttpServletRequest request,
                                      ModelMap model, String state) {
        if (state == null) {
            state = "1";
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
        //不是管理员不能查看
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        return repairDemandlist(title, modelId, queryChannelId, TPL_BIDS_NOTICES_MANAGE_LD,
                pageNo, request, model, state, site, user);
    }

    protected String repairDemandlist(String title, Integer modelId, Integer queryChannelId,
                                      String nextUrl, Integer pageNo,
                                      HttpServletRequest request, ModelMap model, String state, CmsSite site, CmsUser user) {

        Pagination p = LDTenderNoticesManageService.getTenderNoticesList(title, queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20, state);
        model.addAttribute("pagination", p);
        if (!StringUtils.isBlank(title)) {
            model.addAttribute("title", title);
        }
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("state", state);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMONSERVICE, nextUrl);
    }

    /**
     * @return 删除功能
     * @author ldong
     * @Date 2017/1/13 16:48
     */
    @RequestMapping(value = "/member/commonservice_tender_manage_delete.jspx")
    public String delete(int id, HttpServletRequest request,
                         String state, HttpServletResponse response, ModelMap model) {
        String nextUrl = "commonservice_tender_manage_list.jspx?state=" + state;
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
        //不是管理员不能查看
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        LDTenderNoticesManageService.deleteWithTenderManage(id, state);//删除能力表数据
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @return 公共服务 招标管理 编辑请求
     * @author ldong
     * @Date 2017/1/13 16:48
     */
    public static final String TPL_TENDERTRENDER_EDIT = "tpl.tenderTrail_edit";
    public static final String TPL_TENDERNOTICE_EDIT = "tpl.tenderNotice_edit";
    public static final String TPL_SBIDNOTICE_EDIT = "tpl.bidNotice_edit";

    @RequestMapping(value = "/member/commonservice_tender_manager_goEdit.jspx")
    public String editForTender(int id, HttpServletRequest request,
                                String state, HttpServletResponse response, ModelMap model) {

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
        //不是管理员不能查看
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        String nextUrl = null;
        if ("1".equals(state)) {
            STenderTrailer obj = LDTenderNoticesManageDao.getTenderTrailerById(id);
            model.addAttribute("obj", obj);
            nextUrl = TPL_TENDERTRENDER_EDIT;
        } else if ("2".equals(state)) {
            STenderNotice obj = LDTenderNoticesManageDao.getSTenderNoticeById(id);
            model.addAttribute("obj", obj);
            nextUrl = TPL_TENDERNOTICE_EDIT;
        } else if ("3".equals(state)) {
            SBidNotice obj = LDTenderNoticesManageDao.getSBidNoticeById(id);
            model.addAttribute("obj", obj);
            nextUrl = TPL_SBIDNOTICE_EDIT;
        } else {
            return FrontUtils.showMessage(request, model, "error.ParamError");
        }
        model.addAttribute("state", state);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMONSERVICE, nextUrl);
    }

/*    *//**
     * @return 保存招标预告
     * @author ldong
     * @Date 2017/1/16 10:23
     *//*
    @RequestMapping(value = "/member/tender_trail_edit_save.jspx")
    public String relese(int contentId, STenderTrailer stt, HttpServletRequest request, String nextUrl, Integer channelId, boolean forMember, Short charge,
                         Integer modelId, String textarea1,
                         HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        CmsUser user = CmsUtils.getUser(request);
        int tenderTrailerId = stt.getTenderTrailerId();
        STenderTrailer bean = LDTenderNoticesManageDao.getTenderTrailerById(tenderTrailerId);
        Content c = new Content();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());//获取当前时间
        stt.setReleaseDt(bean.getReleaseDt());
        stt.setCreaterDt(bean.getCreaterDt());
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
        ext.setTitle(stt.getProjectName());
        ext.setAuthor(user.getUsername());
        ext.setDescription("发布招标预告");
        ContentTxt t = new ContentTxt();
        t.setTxt(textarea1);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        c = LDTenderNoticesManageService.updateTenderTrail(contentId, stt, request, c, ext, t, nextUrl, modelId, channelId, textarea1, user, charge, typeId, forMember, response, model);
//        this.sSpareDemandService.save(ssd);
        nextUrl = "commonservice_tender_manage_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }*/

    /**
     * @return 保存招标预告——new
     * @author ldong
     * @Date 2017/1/17 16:46
     */
    @RequestMapping(value = "/member/tender_trail_edit_save_update.jspx")
    public String TenderTrailEditSave(int contentId, STenderTrailer stt, HttpServletRequest request, String nextUrl, Integer channelId, boolean forMember, Short charge,
                                      Integer modelId, String textarea1, HttpServletResponse response, ModelMap model, String[] attachmentPaths,
                                      String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        CmsUser user = CmsUtils.getUser(request);
        STenderTrailer bean = LDTenderNoticesManageDao.getTenderTrailerById(stt.getTenderTrailerId());
        Content c = bean.getContent();
        bean.setProjectName(stt.getProjectName());
        bean.setBidDt(stt.getBidDt());
        bean.setBidParty(stt.getBidParty());
        bean.setAddrProvince(stt.getAddrProvince());
        bean.setAddrCity(stt.getAddrCity());
        bean.setAddrCounty(stt.getAddrCounty());
        bean.setAddrStreet(stt.getAddrStreet());
        bean.setBidType(stt.getBidType());
        //默认值set
        bean.setUpdateDt(new Date(System.currentTimeMillis()));
        // bean.setOperatorId(user.getUserId().toString());
        c.setSite(site);
        ContentExt ext = c.getContentExt();
         ext.setTitle(stt.getProjectName());
        ext.setAuthor(user.getUsername());
        ext.setDescription("招标预告编辑");
        ContentTxt t = c.getContentTxt();
        //若表中无contentTxt的数据，新增一条
        if (textarea1 == null) {
            textarea1 = "";
        }
        if (t == null) {
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt(textarea1);
            t = contentTxt;
            contentTxtMng.save(t, c);
        } else {
            t.setTxt(textarea1);
        }
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        this.LDTenderNoticesManageService.updateTenderTrail_new(bean, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs, channelId, typeId, user, charge, true);
        //跳转至需求管理列表
        nextUrl = "commonservice_tender_manage_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @return 招标公告b编辑
     * @author ldong
     * @Date 2017/1/16 10:39
     */
/*    @RequestMapping(value = "/member/tender_notice_edit_save.jspx")
    public String tenderNoticeRelese(int contentId, STenderNotice stn, HttpServletRequest request, String nextUrl, Integer channelId, boolean forMember, Short charge,
                                     Integer modelId, String textarea1,
                                     HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        CmsUser user = CmsUtils.getUser(request);
        Content c = new Content();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());//获取当前时间
        stn.setCreateDt(currentDate);
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
        ext.setTitle(stn.getProjectName());
        ext.setAuthor(user.getUsername());
        ext.setDescription("招标预告编辑保存");
        ContentTxt t = new ContentTxt();
        t.setTxt(textarea1);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        c = LDTenderNoticesManageService.updateTenderNotice(contentId, stn, request, c, ext, t, nextUrl, modelId, channelId, textarea1, user, charge, typeId, forMember, response, model);
        nextUrl = "commonservice_tender_manage_list.jspx?state=2";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }*/

    /**
     * @return 招标公告编辑保存——new
     * @author ldong
     * @Date 2017/1/17 16:55
     */
    @RequestMapping(value = "/member/tender_notice_edit_save_update.jspx")
    public String TenderNoticeEditSave(int contentId, STenderNotice stt, HttpServletRequest request, String nextUrl, Integer channelId, boolean forMember, Short charge,
                                       Integer modelId, String textarea1, HttpServletResponse response, ModelMap model, String[] attachmentPaths,
                                       String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs)
    {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        CmsUser user = CmsUtils.getUser(request);
        STenderNotice bean = LDTenderNoticesManageDao.getSTenderNoticeById(stt.getTenderNoticeId());
        Content c = bean.getContent();
        bean.setProjectName(stt.getProjectName());
        bean.setTenderCode(stt.getTenderCode());
        bean.setPurchaseOwner(stt.getPurchaseOwner());
        bean.setBidCompany(stt.getBidCompany());
        bean.setContact(stt.getContact());
        bean.setMobile(stt.getMobile());
        bean.setAddrProvince(stt.getAddrProvince());
        bean.setAddrCity(stt.getAddrCity());
        bean.setAddrCounty(stt.getAddrCounty());
        bean.setAddrStreet(stt.getAddrStreet());
        bean.setDeadlineDt(stt.getDeadlineDt());
        //默认值set
        bean.setUpdateDt(new Date(System.currentTimeMillis()));
        // bean.setOperatorId(user.getUserId().toString());
        c.setSite(site);
        ContentExt ext = c.getContentExt();
        ext.setTitle(stt.getProjectName());
        ext.setAuthor(user.getUsername());
        ext.setDescription("招标公告编辑");
        ContentTxt t = c.getContentTxt();
        //若表中无contentTxt的数据，新增一条
        if (textarea1 == null) {
            textarea1 = "";
        }
        if (t == null) {
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt(textarea1);
            t = contentTxt;
            contentTxtMng.save(t, c);
        } else {
            t.setTxt(textarea1);
        }
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        this.LDTenderNoticesManageService.updateTenderNotice_new(bean, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames ,
                picPaths, picDescs, channelId, typeId, user, charge, true);
        //跳转至需求管理列表
        nextUrl = "commonservice_tender_manage_list.jspx?state=2";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @return 中标公告保存，发布
     * @author machao
     * @Date 2017/1/15 11:11
  /*   *//*
    @RequestMapping(value = "/member/bid_notice_edit_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       int contentId,
                       String projectName,
                       String bidsNo,
                       String purchasePer,
                       String bidsCompany,
                       String bidsContact,
                       String bidsTel,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       java.sql.Date deadlineDt,
                       String detailDesc,
                       String bidInfo,
                       String bidName,
                       String bidAccount,
                       java.sql.Date contractBgDt,
                       String contact,
                       String mobile,
                       String telephone,
                       String fax,
                       String email,
                       int bidNoticeId,
                       String nextUrl, Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge) {

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        java.util.Date contractBgDt1 = null;
        SBidNotice sBidNotice = new SBidNotice();
        sBidNotice.setBidNoticeId(bidNoticeId);
        sBidNotice.setProjectName(projectName);
        sBidNotice.setBidInfo(bidInfo);
        sBidNotice.setBidName(bidName);
        sBidNotice.setBidSum(bidAccount);
        sBidNotice.setPactDt(contractBgDt);
        sBidNotice.setTenderCode(bidsNo);
        sBidNotice.setPurchaseOwner(purchasePer);
        sBidNotice.setBidCompany(bidsCompany);
        sBidNotice.setContact(bidsContact);
        sBidNotice.setMobile(bidsTel);
        sBidNotice.setAddrStreet(addrProvince + addrCity + addrCounty + addrStreet);
        sBidNotice.setDeadlineDt(deadlineDt);
        sBidNotice.setBidContact(contact);
        sBidNotice.setBidTel(mobile);
        sBidNotice.setBidEml(fax);
        sBidNotice.setBidFax(email);

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
        ext.setTitle(bidName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("中标公告");
        ContentTxt t = new ContentTxt();
        t.setTxt(detailDesc);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        c = LDTenderNoticesManageService.updateBidNotice(contentId, sBidNotice, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs, channelId, typeId, user, charge, true);
        nextUrl = "commonservice_tender_manage_list.jspx?state=3";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }*/

    /**
     * @return 中标公告编辑保存——new
     * @author ldong
     * @Date 2017/1/17 16:55
     */
    @RequestMapping(value = "/member/bid_notice_edit_save_update.jspx")
    public String BidNoticeEditSave(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                    int contentId,
                                    int bidNoticeId,
                                    String projectName,
                                    String bidsNo,
                                    String purchasePer,
                                    String bidsCompany,
                                    String bidsContact,
                                    String bidsTel,
                                    String addrProvince,
                                    String addrCity,
                                    String addrCounty,
                                    String addrStreet,
                                    java.sql.Date deadlineDt,
                                    String detailDesc,
                                    String bidInfo,
                                    String bidName,
                                    String bidAccount,
                                    java.sql.Date contractBgDt,
                                    String contact,
                                    String mobile,
                                    String telephone,
                                    String fax,
                                    String email,
                                    String bidaddrProvince,
                                    String bidaddrCity,
                                    String bidaddrCounty,
                                    String bidAdd,

                                    String nextUrl, Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                                    String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        CmsUser user = CmsUtils.getUser(request);
        SBidNotice bean = LDTenderNoticesManageDao.getSBidNoticeById(bidNoticeId);
        Content c = bean.getContent();
        bean.setBidNoticeId(bidNoticeId);
        bean.setProjectName(projectName);
        bean.setBidInfo(bidInfo);
        bean.setBidName(bidName);
        bean.setBidSum(bidAccount);
        bean.setPactDt(contractBgDt);
        bean.setTenderCode(bidsNo);
        bean.setPurchaseOwner(purchasePer);
        bean.setBidCompany(bidsCompany);
        bean.setContact(bidsContact);
        bean.setMobile(bidsTel);
        bean.setAddrProvince(addrProvince);
        bean.setAddrCity(addrCity);
        bean.setAddrCounty(addrCounty);
        bean.setAddrStreet(addrStreet);
        bean.setDeadlineDt(deadlineDt);
        bean.setBidaddrProvince(bidaddrProvince);
        bean.setBidaddrCity(bidaddrCity);
        bean.setBidContact(bidaddrCounty);
        bean.setBidAdd(bidAdd);
        bean.setBidContact(contact);
        bean.setBidTel(mobile);
        bean.setBidEml(email);
        bean.setBidFax(fax);
        //默认值set
        // bean.setUpdateDt(new Date(System.currentTimeMillis()));
        // bean.setOperatorId(user.getUserId().toString());
        c.setSite(site);
        ContentExt ext = c.getContentExt();
         ext.setTitle(projectName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("中标公告编辑");
        ContentTxt t = c.getContentTxt();
        //若表中无contentTxt的数据，新增一条
        if (detailDesc == null) {
            detailDesc = "";
        }
        if (t == null) {
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt(detailDesc);
            t = contentTxt;
            contentTxtMng.save(t, c);
        } else {
            t.setTxt(detailDesc);
        }
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        this.LDTenderNoticesManageService.updateBidNotice_new(bean,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true);
        //跳转至需求管理列表
        nextUrl = "commonservice_tender_manage_list.jspx?state=3";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    LDTenderNoticesManageDao LDTenderNoticesManageDao;
    @Resource
    private LDTenderNoticesManageService LDTenderNoticesManageService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    protected ContentMng contentMng;
    @Resource
    private ContentTxtMng contentTxtMng;
}
