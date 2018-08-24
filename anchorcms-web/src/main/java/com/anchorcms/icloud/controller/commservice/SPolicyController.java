package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.service.commservice.SPolicyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_COMMSERVICE;


@Controller
public class SPolicyController {
    public static final String SUPPLYDETAIL_REPAIR = "tpl.spolicy_save";

    /**
     * @Author lisong
     * @Date 2017/1/16 11:17
     *惠补政策添加
     */
    @RequiresPermissions("member:Policy_save")
    @RequestMapping("/member/Policy_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model, String policyName, java.sql.Date releaseDt, String releaseLevel, String tradeType, String flowExplain, Integer modelId, Integer channelId, String nextUrl, String selectedStatus, String detailDesc, String address,
                       String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        SAmplePolicy sAmplePolicy = new SAmplePolicy();
        sAmplePolicy.setPolicyName(policyName);//惠补政策名称
        sAmplePolicy.setReleaseLevel(releaseLevel);//发布层面
        sAmplePolicy.setTradeType(tradeType);//行业类型
        sAmplePolicy.setReleaseDt(releaseDt);//发布时间
        sAmplePolicy.setAddress(address);//地区
        sAmplePolicy.setFlowExplain(flowExplain);//流程说明
        sAmplePolicy.setSelectedStatus(selectedStatus);//发布状态

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
        ext.setTitle(policyName);//设置标题
        ext.setAuthor(user.getUsername());
        ext.setDescription("惠补政策添加");//设置描述
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
        c=sPolicyService.supplychain_save_hbzc(sAmplePolicy,c,ext,t,
                channelId,typeId,user, true,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,picDescs);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author lisong
     * @Date 2017/1/16 10:05
     *转到惠补政策添加界面
     */
    @RequiresPermissions("member:P_save")
    @RequestMapping("/member/P_save.jspx")
    public String sa(HttpServletRequest request,ModelMap model){
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, SUPPLYDETAIL_REPAIR);
    }
    @Resource
    private SPolicyService sPolicyService;

    @Resource
    private CmsModelMng cmsModelMng;

    @Resource
    private ContentTypeMng contentTypeMng;

    @Resource
    private ChannelMng channelMng;
}

