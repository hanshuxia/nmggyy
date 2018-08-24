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
import com.anchorcms.icloud.model.cloudcenter.SIcloudQuoteManage;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterDemandService;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterInquiryService;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterQuoteMangerService;
import com.anchorcms.icloud.service.cloudcenter.UserService;
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

@Controller
public class CloudCenterInquiryController {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterInquiryController.class);
    public static final String DEMANDQUOTE_LIST = "tpl.cloudCenterDemandQuoteList";
    public static final String DEMAND_LIST = "tpl.cloudCenterSIcloudOrderList";
    public static final String DEMANDQUOTE_VIEW = "tpl.cloudCenterDemandQuoteView";
    public static final String ORDER_VIEW = "tpl.cloudCenterOrderView";
    public static final String EDIT_ORDER = "tpl.editOrder";
    public static final String EDIT_ORDER_VIEW = "tpl.editOrderView";

    /**
     * @Author maocheng
     * 我是供方--我的报价管理界面
     * @Date 2017/1/4 18:20
     */

    @RequestMapping(value = "/member/cloudcenter_inquiry_list.jspx")
    public String list(String demandObjId, Date startDate, Date endDate, String quoteState, String companyName,
                       Integer modelId, Integer queryChannelId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        String nextUrl = DEMANDQUOTE_LIST;
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
        Pagination p = CloudCenterInquiryService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20, demandObjId, startDate, endDate, companyName, quoteState);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("demandObjId", demandObjId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("companyName", companyName);
        model.addAttribute("quoteState", quoteState);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Desc 我是需求--需求订单管理
     * @Date 2017/1/12
     */

    @RequestMapping(value = "/member/cloudcenter_offerList.jspx")
    public String demandList(String demandObjId, Date startDate, Date endDate, String demandState, String companyName,
                             Integer modelId, Integer queryChannelId, Integer pageNo, HttpServletRequest request, ModelMap model) {
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
        Pagination p = CloudCenterInquiryService.getDeamdOrder(queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20, demandObjId, startDate, endDate, companyName, demandState);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("demandObjId", demandObjId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("companyName", companyName);
        model.addAttribute("demandState", demandState);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /***
     * 我的报价管理--明细
     *
     * @param model
     * @return
     * @author wanjinyou
     */
    @RequestMapping(value = "/member/cloudcenter_quote_view.jspx")
    public String view(Integer id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        String nextUrl = DEMANDQUOTE_VIEW;
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
        SIcloudQuoteManage quoteManage = quotemanger.findById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("quoteManage", quoteManage);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /***
     * 我的报价管理--合同信息明细
     *
     * @param model
     * @return
     * @author lilimin
     */
    @RequestMapping(value = "/member/cloudcenter_quote_order.jspx")
    public String editOrder(String quoteId, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        String nextUrl = EDIT_ORDER;
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
        SIcloudQuoteManage quoteManage = quotemanger.findById(Integer.parseInt(quoteId));
        //根据userID 获取需求用户的名称用户名
        CmsUser demandUser = userService.getByYserId(quoteManage.getDemand().getCreaterId());
        CmsUser quoteUser = userService.getByYserId(quoteManage.getQuote().getCreaterId());
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("quoteManage", quoteManage);
        model.addAttribute("site", site);
        model.addAttribute("demandUser", demandUser);
        model.addAttribute("quoteUser", quoteUser);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /***
     * 我的报价管理--合同信息明细查看
     *
     * @param model
     * @return
     * @author lilimin
     */
    @RequestMapping(value = "/member/cloudcenter_quote_orderview.jspx")
    public String editOrderView(String quoteId, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        String nextUrl = EDIT_ORDER_VIEW;
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
        SIcloudQuoteManage quoteManage = quotemanger.findById(Integer.parseInt(quoteId));
        //根据userID 获取需求用户的名称用户名
        CmsUser demandUser = userService.getByYserId(quoteManage.getDemand().getCreaterId());
        CmsUser quoteUser = userService.getByYserId(quoteManage.getQuote().getCreaterId());
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("quoteManage", quoteManage);
        model.addAttribute("site", site);
        model.addAttribute("demandUser", demandUser);
        model.addAttribute("quoteUser", quoteUser);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /***
     * 需方订单管理--明细
     *
     * @param model
     * @return
     * @author wanjinyou
     */
    @RequestMapping(value = "/member/cloudcenter_order_view.jspx")
    public String demandView(Integer id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        String nextUrl = ORDER_VIEW;
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
        SIcloudQuoteManage quoteManage = quotemanger.findById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("quoteManage", quoteManage);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }


    /**
     * @Author maocheng
     * @Date 2017/1/6
     * @param demandId 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 订单明细查看
     */
   /* @RequestMapping(value = "/member/cloudcenter_inquiry_view.jspx")
    public String view(Integer demandId,Integer demandObjId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMANDQUOTE_VIEW;
        return demandRedirective(demandId,demandObjId,request,response,model,nextUrl);
    }*/

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
        SIcloudDemandQuote sIcloudDemandQuote = CloudCenterInquiryService.byDemandObjId(demandObjId);
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

    /***
     * 需求订单管理列表--删除
     *
     * @param model
     * @return
     * @author wanjinyou
     */
    @RequestMapping(value = "/member/cloudcenter_order_delete.jspx")
    public String delete(Integer id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl = "cloudcenter_offerList.jspx";
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //删除报价管理实体类
        quotemanger.deleteById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    @Resource
    protected ContentMng contentMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private CloudCenterDemandService CloudCenterDemandService;
    @Resource
    private CloudCenterInquiryService CloudCenterInquiryService;
    @Resource
    private CloudCenterQuoteMangerService quotemanger;
    @Resource
    private UserService userService;
}
