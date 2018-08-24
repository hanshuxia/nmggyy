package com.anchorcms.icloud.controller.cloudcenter;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterDemandService;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3 0003
 * @Desc
 */
@Controller
public class CloudCenterOrderController {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterOrderController.class);
    public static final String DEMAND_LIST = "tpl.cloudCenterSIcloudOrderList";
    public static final String DEMAND_ORDER_VIEW = "tpl.cloudCenterSIcloudOrderView";

    /**
     * @Author Yhr
     * 查询需求列表
     * @Date 2016/12/23 0023 15:57
     */

    @RequestMapping(value = "/member/cloudcenter_demand_order.jspx")
    public String list(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandObjId, String demandName,
                       String status, String payType, Integer modelId,
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
        Pagination p = cloudCenterOrderService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(), user.getUserId(), cpn(pageNo), 20, releaseDt, deadlineDt, demandObjId, demandName, status, payType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("releaseDt", releaseDt);
        model.addAttribute("deadlineDt", deadlineDt);
        model.addAttribute("demandObjId", demandObjId);
        model.addAttribute("demandName", demandName);
        model.addAttribute("status", status);
        model.addAttribute("payType", payType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @param demandId 主键id
     * @param request  request对象
     * @param response response对象
     * @param model    model对象
     * @Author jiangshuzhong
     * @Date 2016/1/5
     * @Desc yun需求管理查看 【用户公司一条需求的明细】
     */
    @RequestMapping(value = "/member/cloudcenter_demand_order_view.jspx")
    public String view(Integer demandId, Integer demandObjId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_ORDER_VIEW;
        return demandRedirective(demandId, demandObjId, request, response, model, nextUrl);
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/5 根据demandID获取demand和 content等一些参数放入model，并根据nextUrl跳转的方法
     */
    private String demandRedirective(Integer demandId, Integer demandObjId, HttpServletRequest request,
                                     HttpServletResponse response, ModelMap model, String nextUrl) {
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
        SIcloudDemand sIcloudDemand = CloudCenterDemandService.byDemandId(demandId);
        SIcloudDemandQuote sIcloudDemandQuote = cloudCenterOrderService.byDemandObjId(demandObjId);
        //需要用户公司 与 需求所属公司 相等
        if (user.getCompany() != sIcloudDemand.getCompany()) {
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        Content content = sIcloudDemand.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("sIcloudDemand", sIcloudDemand);
        model.addAttribute("sIcloudDemandQuote", sIcloudDemandQuote);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentMng contentMng;
    @Resource
    private CloudCenterOrderService cloudCenterOrderService;
    @Resource
    private CloudCenterDemandService CloudCenterDemandService;
}
