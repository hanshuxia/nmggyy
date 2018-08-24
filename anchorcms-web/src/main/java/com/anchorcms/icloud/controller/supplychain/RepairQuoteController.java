package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.dao.synergy.OrderPayDao;
import com.anchorcms.icloud.model.payment.SPSettle;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.supplychain.SRepairAbility;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.supplychain.*;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
*@Author 潘晓东
*@Date 2017/1/5 17:55
*@Return抢单报价
*/
@Controller
public class RepairQuoteController {
    private static final Logger log = LoggerFactory.getLogger(SSpareController.class);
    public static final String TPLDIR_SUPPLYCHAIN = "supplychain";
    public static final String TPL_AllQuote = "tpl.allQuote";
    public static final String TPL_QuoteDetail = "tpl.quoteDetail";
    public static final String TPL_QuoteDetailBACK = "tpl.quoteDetailback";
    public static final String SUPPLYCHAINORDER_LIST = "tpl.supplychainSellerOrderList";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    /**
    *@Author 潘晓东
    *@Date 2017/1/5 17:56
    *@Return抢单报价显示所有
    */
    @RequiresPermissions("member:repairQuote")
    @RequestMapping(value = "/member/repairQuote.jspx")
   // @RequestMapping("/repairQuote.jspx")
    public String selectAllInsquiry(String repairName,String inquiryTheme, Date myStartDate, Date myEndDate, String companyName,
                                    Integer modelId, Integer queryChannelId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request,model,site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        //没有开启会员功能
        if(!mcfg.isMemberOn()){
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if(user==null){
            return FrontUtils.showLogin(request,model,site);
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        //条件查询列表
        Pagination p = repairQuoteService.getQuoteListForMember(user,repairName,inquiryTheme, myStartDate, myEndDate, companyName,cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }

        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("myStartDate", myStartDate);
        model.addAttribute("myEndDate", myEndDate);
        model.addAttribute("companyName", companyName);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("repairName", repairName);
        FrontUtils.frontData(request, model, site);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, TPL_AllQuote);
    }

    /***
     * @author zhouyq
     * @date 2017-05-03
     * @return
     * @desc 供应链维修方订单列表
     */
    @RequestMapping(value = "/member/supplychainSellerOrder_list.jspx")
    public String list(HttpServletRequest request, ModelMap model, Integer pageNo,
                       Date startDate, Date endDate, String state, String orderId) {
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
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = repairQuoteService.getSupplychainSellerOrder(site.getSiteId(), user, cpn(pageNo),
                20, startDate, state, orderId);
        model.addAttribute("pagination", p);
        model.addAttribute("startDate", startDate);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("state", state);
        model.addAttribute("orderId", orderId);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SUPPLYCHAINORDER_LIST);
    }

    /**
     * @param orderId, request, response, model
     * @return
     * @author zhouyq
     * @Date 2017/05/03
     * @Desc 根据id修改供应链维修方订单状态
     */
    @RequestMapping("/member/supplychainOrderMdyState.jspx")
    public String modifyDemandStateById(String orderId, String state, String nextUrl,
                                        HttpServletRequest request, HttpServletResponse response, ModelMap model, String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if (backReason != null) {
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
//        orderId = java.net.URLDecoder.decode(orderId, "utf-8");
        repairQuoteService.mdyOrderStateById(orderId, state, backReason);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author zhouyq
     * @Date 2017/5/3
     * @return 接收信息(卖家)
     */
    public static final String SUPPLYCHAINORDERSETTLE = "tpl.supplychainOrderSettle";

    @RequestMapping(value = "/member/supplychainOrderSettle.jspx")
    public String settle(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         String SerialNumber,
                         String OrderNo,
                         String remark,
                         String Amount,
                         int AccountType,
                         String BankID,
                         String bankName,
                         String AccountName,
                         String AccountNumber,
                         String BranchName,
                         String Province,
                         String City, String orderId, String flag/*(设置标志位确定是结算还是退款 0 退款  1结算)*/) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if (("0").equals(flag)) { // 退款
            // 以下信息从数据库取值
            SSupplychainOrder sSupplychainOrder = orderPayDao.getSupplychainOrderById(OrderNo);
            SPSettle spSettle = sSupplychainOrder.getSpPay().getSpSettle();
            SerialNumber = spSettle.getSerialNumber(); // 流水号
            OrderNo = spSettle.getOrderNo(); // 订单号
//            remark = spSettle.getRemark(); // 备注
            AccountType = spSettle.getAccountType(); // 账号类型
            BankID = spSettle.getBankId(); // 银行账号
            bankName = spSettle.getBankName(); // 银行账号
            AccountName = spSettle.getAcountName(); // 账户名称
            AccountNumber = spSettle.getAcountNumber(); // 账户号码
            BranchName = spSettle.getBranchName(); // 网点名称
            Province = spSettle.getProvince(); // 省市
            City = spSettle.getCity(); // 城市
        }

        String result = repairQuoteService.settle(SerialNumber, OrderNo, remark,
                123L,
                AccountType,
                BankID,
                bankName,
                AccountName,
                AccountNumber,
                BranchName,
                Province,
                City, orderId, flag);
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, NEXTURL);*/
//        return FrontUtils.showSuccess(request,
//                model, "/member/index.jspx");
        if (result.equals("success")) { // 成功
            return FrontUtils.showSuccess(request,
                    model, "/member/supplychainSellerOrder_list.jspx?state=10");
        } else { // 失败
            return FrontUtils.showBaseMessage(request,
                    model, result, "/member/supplychainSellerOrder_list.jspx?state=4");
        }
    }

    /**
     * @author zhouyq
     * @Date 2017/5/7
     * @return 接收信息(管理员)
     */
    public static final String SUPPLYCHAINADMINORDERSETTLE = "tpl.supplychainAdminOrderSettle";

    @RequestMapping(value = "/member/supplychainAdminOrderSettle.jspx")
    public String adminSettle(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         String SerialNumber,
                         String OrderNo,
                         String remark,
                         String Amount,
                         int AccountType,
                         String BankID,
                         String bankName,
                         String AccountName,
                         String AccountNumber,
                         String BranchName,
                         String Province,
                         String City, String orderId, String flag/*(设置标志位确定是结算还是退款 0 退款  1结算)*/) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if (("0").equals(flag)) { // 退款
            // 以下信息从数据库取值
            SSupplychainOrder sSupplychainOrder = orderPayDao.getSupplychainOrderById(OrderNo);
            SPSettle spSettle = sSupplychainOrder.getSpPay().getSpSettle();
            SerialNumber = spSettle.getSerialNumber(); // 流水号
            OrderNo = spSettle.getOrderNo(); // 订单号
//            remark = spSettle.getRemark(); // 备注
            AccountType = spSettle.getAccountType(); // 账号类型
            BankID = spSettle.getBankId(); // 银行账号
            bankName = spSettle.getBankName(); // 银行账号
            AccountName = spSettle.getAcountName(); // 账户名称
            AccountNumber = spSettle.getAcountNumber(); // 账户号码
            BranchName = spSettle.getBranchName(); // 网点名称
            Province = spSettle.getProvince(); // 省市
            City = spSettle.getCity(); // 城市
        }

        String result = repairQuoteService.settle(SerialNumber, OrderNo, remark,
                123L,
                AccountType,
                BankID,
                bankName,
                AccountName,
                AccountNumber,
                BranchName,
                Province,
                City, orderId, flag);
        if (result.equals("success")) { // 成功
            return FrontUtils.showSuccess(request,
                    model, "/member/supplychain_order_list.jspx?status=3");
        } else { // 失败
            return FrontUtils.showBaseMessage(request,
                    model, result, "/member/supplychain_order_list.jspx?status=3");
        }
    }

    /**
    *@Author 潘晓东
    *@Date 2017/1/5 17:56
    *@Return抢单报价详情
    */
    @RequestMapping("/quoteDetail.jspx")
    public String selectQuoteDetail(String id,String state,HttpServletRequest request, ModelMap model,String flag){

        if (id !=null){
            //查询维修资源报价表
            SRepairQuote sRepairQuote = repairQuoteService.selectByQuoteID(id);
            List<SRepairAbility> lis = repairAbilityService.selectByQuoteId(id);
            String demandId =sRepairQuote.getDemandId();
            //获取需求信息
            List<SRepairDemand> sRepairDemandList = repairDemandService.selcetByRepairDemandId(demandId);
            //计算报价总价
            Double offerSum=0.00;
            List<SRepairDemandObj> objList = sRepairDemandList.get(0).getsRepairDemandObj();
            for (int i=0;i<lis.size();i++){
                for(int j=0;j<objList.size();j++) {
                    String l = lis.get(i).getDemandId();
                    String r = objList.get(j).getRepairObjid();
                    if (l.equals(r)) {
                        offerSum = offerSum + (lis.get(i).getOffer())*(objList.get(j).getRepairNum());
                    }
                }
            }
            model.addAttribute("offerSum",offerSum);
            model.addAttribute("sRepairQuote",sRepairQuote);
            model.addAttribute("sRepairDemandList",sRepairDemandList);
        }
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request,model,site);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SUPPLYCHAIN, TPL_QuoteDetailBACK);
    }
    @Resource
    private RepairQuoteService repairQuoteService;
    @Resource
    private RepairAbilityService repairAbilityService;
    @Resource
    RepairDemandObjService repairDemandObjService;
    @Resource
    RepairDemandService repairDemandService;
    @Resource
    private SRepairInquiryService sRepairInquiryService;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private OrderPayDao orderPayDao;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
