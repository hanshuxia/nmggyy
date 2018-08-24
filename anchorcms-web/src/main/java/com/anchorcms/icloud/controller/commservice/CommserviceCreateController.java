package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.service.commservice.CommserviceCreateService;
import com.anchorcms.icloud.service.commservice.STenderNoticeService;
import com.anchorcms.icloud.service.commservice.STenderTrailerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_COMMSERVICE;


/**
 * @author machao
 * @Date 2017/1/14 23:46
 * @return
 */
@Controller
public class CommserviceCreateController {
    /**
     * @author gengwenlong
     * @Date 2017/1/14 17:28
     * @return
     * 招标预告发布页面
     */
    public static final String TPL_TenderTrailer_release = "tpl.TenderTrailer_release";
    @RequestMapping(value = "/member/sTenderTrailer.jspx")
    public String findTenderTrailerById( HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request,model,site);
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, TPL_TenderTrailer_release);
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/14 17:37
     * @return
     * 招标预告发布功能
     */
    @RequestMapping(value = "/member/sTenderTrailerRelese.jspx",method = RequestMethod.POST)
    public String relese(STenderTrailer stt, HttpServletRequest request, String nextUrl, Integer channelId, boolean forMember, Short charge,
                         Integer modelId, String textarea1, HttpServletResponse response, ModelMap model, String[] attachmentPaths,
                         String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request,model,site);
        CmsUser user = CmsUtils.getUser(request);
        Content c = new Content();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());//获取当前时间
        stt.setCreaterDt(currentDate);
        stt.setReleaseDt(currentDate);
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
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        c = sTenderTrailerService.relese(stt,request,c,ext,t,nextUrl,modelId,channelId,textarea1,user,charge,typeId,forMember,response,model,attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs);
//        this.sSpareDemandService.save(ssd);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/14 23:05
     * @return
     * 发布招标公告页面
     */
    public static final String TPL_tenderNotice_release = "tpl.tenderNotice_release";
    @RequestMapping(value = "/member/tenderNotice.jspx")
    public String tenderNotice( HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request,model,site);
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, TPL_tenderNotice_release);
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/14 23:05
     * @return
     * 发布招标公告功能
     */
    @RequestMapping(value = "/member/tenderNoticeRelese.jspx",method = RequestMethod.POST)
    public String tenderNoticeRelese(STenderNotice stn, HttpServletRequest request, String nextUrl, Integer channelId, boolean forMember, Short charge,
                                     Integer modelId, String textarea1, HttpServletResponse response, ModelMap model, String[] attachmentPaths,
                                     String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request,model,site);
        CmsUser user = CmsUtils.getUser(request);
        Content c = new Content();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());//获取当前时间
        stn.setCreateDt(currentDate);
        stn.setReleaseDt(currentDate);
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
        ext.setTitle(stn.getProjectName());
        ext.setAuthor(user.getUsername());
        ext.setDescription("发布招标公告");
        ContentTxt t = new ContentTxt();
        t.setTxt(textarea1);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        c = sTenderNoticeService.tenderNoticeRelese(stn,request,c,ext,t,nextUrl,modelId,channelId,textarea1,user,charge,typeId,forMember,response,
                model, attachmentPaths, attachmentNames, attachmentFilenames , picPaths, picDescs);
//        this.sSpareDemandService.save(ssd);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    @Resource
    private STenderTrailerService sTenderTrailerService;
    @Resource
    private STenderNoticeService sTenderNoticeService;


    public static final String SBIDNOTICE_ADD = "tpl.sbidNoticeAdd";
    /**
     * @author machao
     * @Date 2017/1/14 23:47
     * @return
     * 发布中标公告
     */
    @RequestMapping("/member/SbidNoticeAdd.jspx")
    public String addSbid(HttpServletRequest request, HttpServletResponse resopnce, ModelMap model){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request,model,site);
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, SBIDNOTICE_ADD);
    }
    /**
     * @author machao
     * @Date 2017/1/15 11:11
     * @return
     * 中标公告保存，发布
     */
    @RequestMapping(value = "/member/SbidNoticeSave.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response,ModelMap model,
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
                       Date deadlineDt,
                       String detailDesc,
                       String bidInfo,
                       String bidName,
                       String bidAccount,
                       Date contractBgDt,
                       String contact,
                       String mobile,
                       String telephone,
                       String fax,
                       String email,
                       String bidaddrProvince,    //中标方地址
                       String bidaddrCity,
                       String bidaddrCounty,
                       String bidAdd,

                       String nextUrl,Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge){

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());//获取当前时间
        SBidNotice sBidNotice = new SBidNotice();
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
        sBidNotice.setAddrProvince(addrProvince);
        sBidNotice.setAddrCity(addrCity);
        sBidNotice.setAddrCounty(addrCounty);
        sBidNotice.setAddrStreet(addrStreet);
        sBidNotice.setBidaddrProvince(bidaddrProvince);
        sBidNotice.setBidaddrCity(bidaddrCity);
        sBidNotice.setBidaddrCounty(bidaddrCounty);
        sBidNotice.setBidAdd(bidAdd);
        sBidNotice.setDeadlineDt(deadlineDt);
        sBidNotice.setBidContact(contact);
        sBidNotice.setBidTel(mobile);
        sBidNotice.setBidEml(email);
        sBidNotice.setBidFax(fax);
        sBidNotice.setReleaseDt(currentDate);

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
        ext.setTitle(projectName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("中标公告");
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
        c = commserviceCreateService.save(sBidNotice,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private CommserviceCreateService commserviceCreateService;

}
