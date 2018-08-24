package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.dao.synergy.OrderPayDao;
import com.anchorcms.icloud.model.payment.SPPay;
import com.anchorcms.icloud.model.payment.SPSettle;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.service.supplychain.SSpareDemandService;
import com.anchorcms.icloud.service.supplychain.SrepairdemandObjService;
import com.anchorcms.icloud.service.supplychain.SrepairdemandService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author zhouyq
 * @Date 2016/12/26
 * @Desc 众修需求对象controller
 */
@Controller
public class SrepairdemandController {
    private static final Logger log = LoggerFactory.getLogger(SrepairdemandController.class);

    public static final String TPLDIR_SYNERGY = "supplychain";
    public static final String REPAIRDEMANDLIST = "tpl.repairDemandList"; // 众修列表
    public static final String REPAIRDEMANDDETAILLIST = "tpl.repairDemandDetailList"; // 众修明细列表
    public static final String REPAIRDEMANDPREVIEWLIST = "tpl.repairDemandPreviewList"; // 众修预览列表
    public static final String ICLOUD_GLYJM_ZXXQ_LIST = "tpl.repairIcloud_glyjm_zxxq_list"; // 众修资源管理
    public static final String SUPPLYCHAIN_ORDER_LIST = "tpl.supplychain_order_list";//众修订单管理员列表
    public static final String Supplychain_Refund = "tpl.SupplychainRefund";
    public static final String Supplychain_Refundd = "tpl.SupplychainRefundds";
    public static final String Supplychain_Settlement = "tpl.SupplychainSettlement";//结算

    public static final String TPLDIR_MEMBER = "member";
    public static final String SPARE_STOCK_STATISTICS = "tpl.spare_stock_statistics";


    @Resource
    private SrepairdemandService service;
    @Resource
    private SrepairdemandObjService serviceObj;
    @Resource
    private SSpareDemandService sSpareDemandService;

    /**
     * @param repairName, pageNo, request, model
     * @return
     * @throws UnsupportedEncodingException
     * @author zhouyq
     * @Date 2016/12/27
     * @Desc 众修资源列表展示
     */
    @RequiresPermissions("member:repairDemandList")
    @RequestMapping(value = "/member/repairDemandList.jspx")
    public String list(String repairName, Integer pageNo, HttpServletRequest request, ModelMap model, String status) throws UnsupportedEncodingException {

        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
        // 解决前台传入的中文乱码参数

//        if (repairName != null) {
//            repairName = java.net.URLDecoder.decode(repairName, "utf-8");
//        }
        // 获得分页信息
        Pagination pagination = service.getList(repairName, status, cpn(pageNo), CookieUtils.getPageSize(request));
        List paginationList = pagination.getList(); // 必须返回List格式数据
        model.addAttribute("repairDemandList", paginationList);
//        Integer pageCount = pagination.getTotalCount();
//        Integer pageSize = pagination.getPageSize();
//        Integer pageNumber = pagination.getPageNo();
//        Integer totalPages = (pageCount % pageSize) == 0 ? (pageCount / pageSize) : (pageCount / pageSize + 1);
//        model.addAttribute("pageCount", pageCount);
//        model.addAttribute("pageSize", pageSize);
//        model.addAttribute("pageNumber", pageNumber);
//        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pagination", pagination);
        model.addAttribute("repairName", repairName);
        if (repairName == null && status == null) {
            model.addAttribute("status", "2");
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGY, REPAIRDEMANDLIST);
        }
        model.addAttribute("status", status);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, REPAIRDEMANDLIST);

//        ArrayList totalPagesList = new ArrayList();
//        for (int i = -1; i < totalPages; i++) {
//            totalPagesList.add(i + 1, i + 1);
//        }
//        model.addAttribute("totalPagesList", totalPagesList);

    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/8 12:13
     * @description众修需求管理
     */
    @RequiresPermissions("member:repairDemandList")
    @RequestMapping(value = "/member/icloud_glyjm_zxxq_list.jspx")
    public String icloud_glyjm_zxxq_list(String repairName, Integer pageNo, HttpServletRequest request, ModelMap model, String statusType) throws UnsupportedEncodingException {

        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
        // 解决前台传入的中文乱码参数
        if (repairName != null) {
            repairName = java.net.URLDecoder.decode(repairName, "utf-8");
        }
        // 获得分页信息
        Pagination pagination = service.getZxxqList(repairName, cpn(pageNo), CookieUtils.getPageSize(request), statusType);
        List paginationList = pagination.getList(); // 必须返回List格式数据
        model.addAttribute("repairDemandList", paginationList);

        model.addAttribute("pagination", pagination);
        model.addAttribute("state", statusType);

//        ArrayList totalPagesList = new ArrayList();
//        for (int i = -1; i < totalPages; i++) {
//            totalPagesList.add(i + 1, i + 1);
//        }
//        model.addAttribute("totalPagesList", totalPagesList);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, ICLOUD_GLYJM_ZXXQ_LIST);
    }


    /**
     * @param repairIds, request, response, model
     * @return
     * @author dongxuecheng
     * @Date 2017/01/06
     * @Desc 根据id批量修改众修需求状态（通过、驳回）
     */
    @RequestMapping("/member/repairDemandSetStates.jspx")
    public String setDemandStatByIdss(String repairIds, String status, String nextUrl, String flag, String id, Integer pageNo,
                                      HttpServletRequest request, HttpServletResponse response, ModelMap model, String statusType, String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        String repairName = null;
        if (flag != null) {
            if (flag.equals("pass")) {
                service.setStatus(id, "3", null);
            } else if (flag.equals("noPass")) {
                try {
                    backReason = java.net.URLDecoder.decode(backReason, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                service.setStatus(id, "0", backReason);
            } else if (flag.equals("delete")) {
                service.delete(id);
            } else if (flag.equals("setPass")) {
                service.setall(id, "3", null);
            } else if (flag.equals("setNopass")) {
                try {
                    backReason = java.net.URLDecoder.decode(backReason, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                service.setall(id, "0", backReason);
            } else if (flag.equals("repairName")) {
                repairName = id;
            }
        }
        Pagination pagination = service.getZxxqList(repairName, cpn(pageNo), CookieUtils.getPageSize(request), statusType);
        List paginationList = pagination.getList(); // 必须返回List格式数据
        model.addAttribute("repairDemandList", paginationList);
        model.addAttribute("pagination", pagination);
        model.addAttribute("state", statusType);
        model.addAttribute("repairName", repairName);
        nextUrl = "/member/icloud_glyjm_zxxq_list.jspx?statusType=" + statusType;
        if (("noPass".equals(flag)) || ("setNopass".equals(flag)) || ("setPass".equals(flag)) || ("pass".equals(flag))) {
            return FrontUtils.showSuccess(request, model, nextUrl);
        } else {
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGY, ICLOUD_GLYJM_ZXXQ_LIST);
        }
    }

    /**
     * @Author 苏和 【562763562@qq.com】
     * @Date 2017/5/3 11:23
     * @Return 众修订单管理员列表
     */
    @RequestMapping("/member/supplychain_order_list.jspx")
    public String getSyneryList(HttpServletRequest request, ModelMap model, Integer pageNo,
                                java.sql.Date startDate, java.sql.Date endDate, String state, String paramu) {
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
        if (StringUtils.isBlank(state)) {
            state = "1";
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = service.getSyneryListPage(site.getSiteId(), user, cpn(pageNo),
                20, startDate, state, paramu);
        model.addAttribute("pagination", p);
        model.addAttribute("startDate", startDate);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("state", state);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, SUPPLYCHAIN_ORDER_LIST);
    }

    /**
     * @author zhouyq
     * @Date 2017/6/14
     * @return
     * @desc 根据地区获取备品备件的分类统计信息
     */
    /*,produces = "application/json;charset=utf-8"*/
    @RequestMapping(value = "/member/spare_stock_statisticsAjax.jspx")
    public void getSpareStatisticsAjax(String region, HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        if(region.equals("") || region == null || region == "undefined"){
            region = "呼和浩特市";
        }else{
            try {
                region= URLDecoder.decode(URLDecoder.decode(region, "UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.print(service.getSpareStatisticsJson(region));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/member/spare_stock_statistics.jspx")
    public String getSpareStatistics(ModelMap model, HttpServletRequest request, HttpServletResponse response){
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SPARE_STOCK_STATISTICS);
    }


    /**
     * @Author 苏和 【562763562@qq.com】
     * @Date 2017/5/3 17:25
     * @Return 供应链协同卖家结算
     */
    @RequestMapping(value = "/member/SupplychainSettlement.jspx")
    public String Settlement(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id,
                             String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SPPay spPay = orderPayDao.getSupplychainOrderById(id).getSpPay();
        String OrderNo = spPay.getOrderNo(); //订单号（数据库取值）
        double amount = spPay.getAmount();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        //int AccountType = 20;  //交易类型默认支付平台账户20
        model.addAttribute("orderNo", OrderNo);
        model.addAttribute("amount", amount);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("id", id);
//        model.addAttribute("AccountType",AccountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, Supplychain_Settlement);
    }

    /**
     * @Author 苏和 【562763562@qq.com】
     * @Date 2017/5/3 17:39
     * @Return 管理员退款给买家
     */
    @RequestMapping(value = "/member/SupplychainRefund.jspx")
    public String Refund(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id,
                         String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SPPay spPay = orderPayDao.getSupplychainOrderById(id).getSpPay();
        SPSettle se = spPay.getSpSettle();
        String OrderNo = spPay.getOrderNo(); //订单号（数据库取值）
        double amount = spPay.getAmount();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        //int AccountType = 20;  //交易类型默认支付平台账户20
        model.addAttribute("orderNo", OrderNo);
        model.addAttribute("amount", amount);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("id", id);
        model.addAttribute("se", se);
//        model.addAttribute("AccountType",AccountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, Supplychain_Refund);
    }

    /**
     * @Author 苏和 【562763562@qq.com】
     * @Date 2017/5/3 17:39
     * @Return 卖家确认退款页面
     */
    @RequestMapping(value = "/member/SupplychainRefundd.jspx")
    public String Refundd(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id,
                          String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SPPay spPay = orderPayDao.getSupplychainOrderById(id).getSpPay();
        SPSettle se = spPay.getSpSettle();
        String OrderNo = spPay.getOrderNo(); // 订单号（数据库取值）
        double amount = spPay.getAmount(); // 分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); // 交易流水号
        //int AccountType = 20;  // 交易类型默认支付平台账户20
        model.addAttribute("orderNo", OrderNo);
        model.addAttribute("amount", amount);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("id", id);
        model.addAttribute("se", se);
//        model.addAttribute("AccountType",AccountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, Supplychain_Refundd);
    }

    /**
     * @param repairId, request, response, model
     * @return
     * @author zhouyq
     * @Date 2016/12/27
     * @Desc 根据id获取众修资源明细信息
     */
    @RequiresPermissions("member:repairDemandDetailList")
    @RequestMapping("/member/repairDemandDetailList.jspx")
    public String findDetailById(String repairId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if (repairId != null) {
//            List repairDemandDetailList = service.findDetailAndPreviewById(repairId);
//            SRepairDemand srepairDemandEntity = repairDemandDetailList.get(0)[1];
//            model.addAttribute("repairDemandDetailList", repairDemandDetailList);
//            model.addAttribute("SRepairDemand", srepairDemandEntity);
//            model.addAttribute("SRepairDemandObj", SRepairDemandObj);
            repairId = java.net.URLDecoder.decode(repairId, "utf-8");
            SRepairRes oRepairDemandEntity = service.findDetailAndPreviewByIdDemand(repairId);
            SRepairDemandObj oRepairDemandObjEntity = serviceObj.findDetailAndPreviewByIdDemandObj(repairId);
            model.addAttribute("oRepairDemandEntity", oRepairDemandEntity);
            model.addAttribute("oRepairDemandObjEntity", oRepairDemandObjEntity);
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, REPAIRDEMANDDETAILLIST);
    }

    /**
     * @param repairId, request, response, model
     * @return
     * @author zhouyq
     * @Date 2016/12/27
     * @Desc 根据id获取众修资源预览信息
     */
    @RequiresPermissions("member:repairDemandPreviewlList")
    @RequestMapping("/member/repairDemandPreviewlList.jspx")
    public String findPreviewById(String repairId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if (repairId != null) {
//            List repairDemandPreviewlList = service.findDetailAndPreviewByIdDemand(repairId);
//            model.addAttribute("repairDemandPreviewlList", repairDemandPreviewlList);
            repairId = java.net.URLDecoder.decode(repairId, "utf-8");
            SRepairRes oRepairDemandEntity = service.findDetailAndPreviewByIdDemand(repairId);
            SRepairDemandObj oRepairDemandObjEntity = serviceObj.findDetailAndPreviewByIdDemandObj(repairId);
            //List<SRepairRes> lRepairResEntityList = sSpareDemandService.getAllSearch();
            model.addAttribute("oRepairDemandEntity", oRepairDemandEntity);
            model.addAttribute("oRepairDemandObjEntity", oRepairDemandObjEntity);
            //model.addAttribute("lRepairResEntityList", lRepairResEntityList);
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, REPAIRDEMANDPREVIEWLIST);
    }

    /**
     * @param repairId, request, response, model
     * @return
     * @author zhouyq
     * @Date 2016/12/27
     * @Desc 根据id修改众修资源状态（通过、驳回）
     */
    @RequestMapping("/member/repairDemandMdyState.jspx")
    public String modifyDemandStatById(String repairId, String status, String nextUrl,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model, String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if (backReason != null) {
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
        if (repairId != null) {
            repairId = java.net.URLDecoder.decode(repairId, "utf-8");
            service.mdyRepairDemandStateById(repairId, status, backReason);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param repairIds, request, response, model
     * @return
     * @author zhouyq
     * @Date 2017/01/06
     * @Desc 根据id批量修改众修资源状态（通过、驳回）
     */
    @RequestMapping("/member/repairDemandSetState.jspx")
    public String setDemandStatByIds(String repairIds, String status, String nextUrl,
                                     HttpServletRequest request, HttpServletResponse response, ModelMap model, String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if (backReason != null) {
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
        if (repairIds != null) {
            service.setRepairDemandStateByIds(repairIds, status, backReason);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param repairId, request, response, model
     * @return
     * @author zhouyq
     * @Date 2016/12/27
     * @Desc 根据id删除众修需求
     */
    @RequestMapping("/member/repairDemandDel.jspx")
    public String delDemandStatById(String repairId, String nextUrl,
                                    HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if (repairId != null) {
            repairId = java.net.URLDecoder.decode(repairId, "utf-8");
            service.delRepairDemandById(repairId);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    private ChannelMng channelMng;

    @Resource
    private OrderPayDao orderPayDao;
}
