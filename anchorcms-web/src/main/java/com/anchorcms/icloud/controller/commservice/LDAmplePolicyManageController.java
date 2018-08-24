package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.cms.model.main.ContentType;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.service.commservice.LDAmplePolicyManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * DESCRIPTION:惠补政策管理
 * Author: DELL
 * Date:2017/1/13.
 */
@Controller
public class LDAmplePolicyManageController {
    private static final String TPLDIR_COMMONSERVICE = "commservice";
    /**
     * @author ldong
     * @Date 2017/1/13 10:17
     * @return 惠补政策管理列表
     */
    private final static String TPL_AMPLE_POLICY_MAMAGE_LIST = "tpl.commonservice_ample_policy_manage_list";
    @RequestMapping(value = "/member/commonservice_ample_policy_manage_list.jspx")
    public String getRepairDemandList(Integer itemId,String flag,String tradeType,Date startDT,Date endDT, String title, Integer modelId,
                                      Integer queryChannelId, Integer pageNo, HttpServletRequest request,
                                      ModelMap model, String state) {
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
        if (state == null) {
            state = "1";
        }
        String nextUrl = TPL_AMPLE_POLICY_MAMAGE_LIST;
        if("test".equals(flag)){
        //保留功能按钮
        }
        else if("edit".equals(flag)){
            SAmplePolicy sAmplePolicyEntity = this.LDAmplePolicyManageService.getAmplePolicyById(itemId);
            model.addAttribute("sAmplePolicyEntity", sAmplePolicyEntity);
            model.addAttribute("itemId", itemId);
            nextUrl = TPL_AMPLE_POLICY_MAMAGE_EDIT;
        }
        else if("release".equals(flag)){
            this.LDAmplePolicyManageService.updateAmplePolicyById(itemId,"2");
              nextUrl =   TPL_AMPLE_POLICY_MAMAGE_LIST;
            return FrontUtils.showSuccess(request,model,"/member/commonservice_ample_policy_manage_list.jspx");
        }
        else if("recall".equals(flag)){
            this.LDAmplePolicyManageService.updateAmplePolicyById(itemId,"1");
              nextUrl =   TPL_AMPLE_POLICY_MAMAGE_LIST;
            return FrontUtils.showSuccess(request,model,"/member/commonservice_ample_policy_manage_list.jspx");
        }
        else if("detail".equals(flag)){
            SAmplePolicy sAmplePolicyEntity = this.LDAmplePolicyManageService.getAmplePolicyById(itemId);
            model.addAttribute("sAmplePolicyEntity", sAmplePolicyEntity);
              nextUrl = TPL_AMPLE_POLICY_MAMAGE_DETAIL;
        }

        else if("delete".equals(flag)){
            this.LDAmplePolicyManageService.deleteAmplePolicyById(itemId);
              nextUrl =   TPL_AMPLE_POLICY_MAMAGE_LIST;
            return FrontUtils.showSuccess(request,model,"/member/commonservice_ample_policy_manage_list.jspx");
        }
        return goAmplePolicyManagelist(tradeType ,startDT,endDT,title, modelId, queryChannelId,nextUrl ,
                pageNo, request, model, state, site, user);
    }

    protected String goAmplePolicyManagelist(String tradeType, Date startDT, Date endDT, String title, Integer modelId, Integer queryChannelId,
                                             String nextUrl, Integer pageNo,
                                             HttpServletRequest request, ModelMap model, String state, CmsSite site, CmsUser user) {

        Pagination p = LDAmplePolicyManageService.getAmplePolicyList( tradeType, startDT, endDT,title, queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20, state);
        model.addAttribute("pagination", p);
        //将检索条件返回到页面当中
        if (!StringUtils.isBlank(title)) {
            model.addAttribute("title", title);
        }
        if (!StringUtils.isBlank(tradeType)) {
            model.addAttribute("tradeType", tradeType);
        }
        if (startDT!=null) {
            model.addAttribute("startDT", startDT);
        }
        if (endDT!=null) {
            model.addAttribute("endDT", endDT);
        }
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        if (pageNo != null) {
            model.addAttribute("pageNo", pageNo);
        }
        model.addAttribute("state", state);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMONSERVICE, nextUrl);
    }

    /**
     * @return
     * @author zhouyq
     * @Date 2017/1/16
     * @description 惠补政策修改、明细界面
     */
    private final static String TPL_AMPLE_POLICY_MAMAGE_EDIT = "tpl.commonservice_ample_policy_manage_edit";
    private final static String TPL_AMPLE_POLICY_MAMAGE_DETAIL = "tpl.commonservice_ample_policy_manage_detail";

    /**
     * @Author zhouyq
     * @Date 2017/1/16
     * 惠补政策编辑保存
     */
    @RequestMapping(value = "/member/commonservice_ample_policy_manage_editSave.jspx")
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         //P.S.参数竖着放是为了方便加注释和debug
                         String policyName,
                         String releaseLevel,
                         String tradeType,
                         String flowExplain,
                         Integer createrId,
                         String address,
                         java.sql.Date releaseDt,
                         java.sql.Date createrDt,
                         java.sql.Date updateDt,
                         Integer itemId,
                         //cms表相关参数
                         String detailDesc,
                         Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                         String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SAmplePolicy sp = LDAmplePolicyManageService.getAmplePolicyById(itemId);
        Content c = sp.getContent();
        sp.setAmplePolicyId(itemId);
        sp.setPolicyName(policyName);
        sp.setReleaseLevel(releaseLevel);
        sp.setTradeType(tradeType);
        sp.setFlowExplain(flowExplain);
        sp.setAddress(address);
        sp.setReleaseDt(releaseDt);
        //默认值set
        sp.setUpdateDt(new java.sql.Date(System.currentTimeMillis()));
        c.setSite(site);
        ContentExt ext = c.getContentExt();
        ext.setTitle(policyName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("惠补政策修改");
        ContentTxt t = c.getContentTxt();

        //若表中无contentTxt的数据，新增一条
        if (detailDesc == null) {
            detailDesc = "";
        }
        if (t == null) {
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt(detailDesc);
            // 防止没有创建t
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
        LDAmplePolicyManageService.update(sp, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs, channelId, typeId, user, charge, true);
        //跳转至需求管理列表
        String nextUrl = "/member/commonservice_ample_policy_manage_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    LDAmplePolicyManageService  LDAmplePolicyManageService;
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
