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
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterDemandService;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterMyInquiryService;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterQuoteService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;


/**
 * @Author 万金友
 * @Date 2017/2/20
 * @Description 云资源交易中心-管理员-我的方案报价
 */
@Controller
public class CloudCenterMyInquiryController {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterMyInquiryController.class);
    public static final String CLOUD_MYINQUIRY_LIST = "tpl.CloudMyInquiryList";
    public static final String CLOUD_MYINQUIRY_VIEW = "tpl.CloudMyInquiryBiddView";
    public static final String INQUIRY_DEMAND_VIEW = "tpl.CloudCenterInquiryDemandView";
    public static final String COMPANY_APTITUDE_ADD = "/member/index.jspx";

    /**
     * @Author 万金友
     * @Date 2017/2/20
     * @Description 管理员-我的方案报价--跳转列表界面
     */
    @RequestMapping(value = "/member/cloudcenter_myInquiry_list.jspx")
    public String list(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                       String selectedStatus, String payType, String statusType, Integer modelId,
                       Integer queryChannelId, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        String nextUrl = CLOUD_MYINQUIRY_LIST;
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
        Pagination p = myInquiryService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(), selectedStatus,
                user.getUserId(), cpn(pageNo), 20, releaseDt, deadlineDt, demandId, statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("statusType", statusType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Date 2017/2/20
     * @Desc 管理员-我的方案报价--需求详情 【用户公司一条需求的明细】
     */
    @RequestMapping(value = "/member/cloudcenter_myInquiry_view.jspx")
    public String demandView(Integer demandId, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        String nextUrl = INQUIRY_DEMAND_VIEW;
        return demandRedirective(demandId, request, response, model, nextUrl);
    }

    /**
     * @author wanjinyou
     * @Date 2017/2/20
     * @desciption 云资源交易中心--管理员-我的方案报价-报价详情列表 ajax
     */
    @RequestMapping(value = "/member/cloud_demandQuoteDetail_getQuoteAjax.jspx", method = RequestMethod.POST)
    public void getQuoteAjax(Integer quoteId, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
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
        if (user.getCompany() == null) {
            return;
        }
        //判断一下quoteId
        if (quoteId == null) {
            return;
        }
        //判断一下quote是否为用户公司的 不能查看非本公司的报价
        if (!user.getCompany().getCompanyId().equals(cloudCenterQuoteService.byDemandQuoteId(quoteId).getCompany().getCompanyId())) {
            return;
        }

        try {
            PrintWriter writer = response.getWriter();
            writer.print(myInquiryService.getQuoteDetailJson(quoteId));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author wanjinyou
     * @Date 2017/2/20
     * @Desc 管理员--我的方案报价--中标详情
     */
    @RequestMapping(value = "/member/cloudcenter_myInquiry_biddView.jspx")
    public String view(Integer quoteId, HttpServletRequest request,
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
        //用户没有关联公司的话不能操作
        if (user.getCompany() == null) {
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        if (quoteId == null) {
            return FrontUtils.showMessage(request, model, "error.needParams");
        }
        SIcloudDemandQuote quote = cloudCenterQuoteService.byDemandQuoteId(quoteId);
        //SDemandQuote quote= sDemandQuoteService.byDemandQuoteId(quoteId);
        //报价状态为已中标才能查看
        if (!"2".equals(quote.getSelectedStatus())) {
            return FrontUtils.showMessage(request, model, "error.exceptionParams");
        }
        model.addAttribute("quote", quote);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, CLOUD_MYINQUIRY_VIEW);
    }

    /**
     * @Author wanjinyou
     * @Date 2017/2/21
     * @Description 管理员--我的方案报价--删除
     */
    @RequestMapping(value = "/member/cloudCenter_myInquiry_delete.jspx")
    public String delete(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, Integer modelId, Integer channelId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl = "/member/cloudcenter_myInquiry_list.jspx";
        FrontUtils.frontData(request, model, site);
        //删除报价表
        myInquiryService.delete(id);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/5 根据demandID获取demand和 content等一些参数放入model，并根据nextUrl跳转的方法
     */
    private String demandRedirective(Integer demandId, HttpServletRequest request,
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
        //若不是管理员，需要用户公司 与 需求所属公司 相等
        if (!user.getIsAdmin() && !user.getCompany().getCompanyId().equals(sIcloudDemand.getCompany().getCompanyId())) {
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        Content content = sIcloudDemand.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("sIcloudDemand", sIcloudDemand);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private CloudCenterMyInquiryService myInquiryService;
    @Resource
    private CloudCenterQuoteService cloudCenterQuoteService;
    @Resource
    private CloudCenterDemandService CloudCenterDemandService;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
