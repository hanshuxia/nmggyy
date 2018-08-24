package com.anchorcms.icloud.controller.cloudcenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.cloudcenter.Result;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuoteCopy;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterDemandService;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterQuoteMangerService;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterQuoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by lilimin
 *
 * @Date 2016/12/30 0030
 * @Desc 对云需求进行报价的controller
 */
@Controller
@ResponseBody
public class CloudDemandQuoteController {
    public static final String DEMAND_QUOTE = "tpl.ClounddemandQuote";


    /**
     * @author: gao jiangning
     * @desciption 后台页面 云需求-get优选列表 or get下单列表 ajax
     * @Date 2017/1/7
     */
    @RequestMapping(value = "/member/cloudCenter_Cdemand_order.jspx", method = RequestMethod.POST)
    public void getOrderListAjax(Integer demandId, String isSelected, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        Result result = new Result();
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
        List<SIcloudDemandQuote> icDemandQuote;
        if ("1".equals(isSelected)) {
            icDemandQuote = cloudCenterQuoteService.getICDemandQuoteSelectedByDemandId(demandId);
        } else {
            icDemandQuote = cloudCenterQuoteService.getICDemandQuoteByDemandId(demandId);
        }
        SIcloudDemand demand = cloudCenterDemandService.byDemandId(demandId);
        //把查询结果的对象转换为没有关联关系的对象
        List<SIcloudDemandQuoteCopy> quoteCopyList = getquoteCopyList(icDemandQuote, demand);
        result.setData(quoteCopyList);
        JSONObject jObj = (JSONObject) JSON.toJSON(result);
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        jObj.fluentPut("count", icDemandQuote.size())
                .fluentPut("demandId", nf.format(demandId))
                .fluentPut("amount", nf.format(demand.getCount()));
        String jsonString2 = JSON.toJSONString(jObj);
        PrintWriter writer = response.getWriter();
        writer.print(jsonString2);
        writer.flush();
        writer.close();
    }

    /**
     * @param icDemandQuote
     * @param demand
     * @return
     * @auther lilimin
     * @desciption 设置关联关系处理
     */
    private List<SIcloudDemandQuoteCopy> getquoteCopyList(List<SIcloudDemandQuote> icDemandQuote, SIcloudDemand demand) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        List<SIcloudDemandQuoteCopy> list = new ArrayList<SIcloudDemandQuoteCopy>();
        for (int i = 0; i < icDemandQuote.size(); i++) {
            SIcloudDemandQuoteCopy quoteCopy = new SIcloudDemandQuoteCopy();
            quoteCopy.setDemandObjId(nf.format(icDemandQuote.get(i).getDemandObjId()));
            quoteCopy.setCompanyId(icDemandQuote.get(i).getCompany().getCompanyId());
            quoteCopy.setDemandObjId(nf.format(icDemandQuote.get(i).getDemandObjId()));
            quoteCopy.setDemandName(demand.getDemandName());
            quoteCopy.setCompanyName(icDemandQuote.get(i).getCompany().getCompanyName());
            quoteCopy.setCreateDt(icDemandQuote.get(i).getCreateDt());
            quoteCopy.setCreaterId(icDemandQuote.get(i).getCreaterId());
            quoteCopy.setDemandId(nf.format(icDemandQuote.get(i).getIcloudDemand().getDemandId()));
            quoteCopy.setPrice(nf.format(icDemandQuote.get(i).getPrice()));
            quoteCopy.setExpertPrice(nf.format(demand.getExpect_price()));
            quoteCopy.setCount(nf.format(demand.getCount()));
            quoteCopy.setClassCode(demand.getClassify_code());
            quoteCopy.setUnit(demand.getUnit());
            quoteCopy.setSelectedStatus(icDemandQuote.get(i).getSelectedStatus());
            list.add(quoteCopy);
        }
        return list;
    }

    /**
     * @return
     * @auther lilimin
     * @desciption 云需求展示--我要报价
     */
    @RequestMapping(value = "/member/cloudCenter_Cdemand_quote.jspx")
    public String toQuotePage(Integer demandId, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();

        SIcloudDemand targetDemand = cloudCenterDemandService.byDemandId(demandId);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if (!user.getIsAdmin()) {
            return FrontUtils.showBaseMessage(request, model, "只能由管理员报价,请与管理员联系！", "/yzyjyzxYxq/index.jhtml");
        }
        //用户没有关联公司的话不能报价 ps应该修改一下提示信息，新增一个Message字段
        if (user.getCompany() == null) {
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        //判断一下demandId能不能用，[在数据库中是否存在
        if (demandId == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //用户 不能报自己发的需求的价
        if (user.getCompany().getCompanyId().equals(targetDemand.getCompany().getCompanyId())) {
            return FrontUtils.showMessage(request, model, "无法对自己发布的需求报价！");
        }
        //公司不能对同一云需求进行二次报价
        if (cloudCenterQuoteService.hasQuoted(demandId, user.getCompany().getCompanyId())) {
            return FrontUtils.showMessage(request, model, "error.hasquoted");
        }
        model.addAttribute("site", site);
        model.addAttribute("channel", channelMng.findById(115));
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("demand", targetDemand);
        model.addAttribute("user", user);

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, DEMAND_QUOTE);
    }

    /**
     * @param request
     * @param response
     * @param model
     * @param demandId demand_obj_id ←这个其实是云需求报价的主键，和云需求obj无任何关系
     * @auther lilimin
     * @Descript 下单
     */
    @RequestMapping(value = "/member/cloudCenter_quotoManger_order.jspx")
    public void saveQuoteManger(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                Integer demandId,
                                String orderJson) {
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        if (user == null) {
            return;
        }
        //用户没有关联公司的话不能继续
        if (user.getCompany() == null) {
            return;
        }
        //判断一下demandId是否为空
        if (demandId == null) {
            return;
        }
        SIcloudDemand demand = cloudCenterDemandService.byDemandId(demandId);
        //判断一下demand是否为用户公司的 不能对非本公司需求下单
        if (!user.getCompany().getCompanyId().equals(demand.getCompany().getCompanyId())) {
            return;
        }
        int operatedCount = quotemanger.saveByJson(demand, orderJson, user);
        int insertedCount = quotemanger.countByDemandId(demandId);
        cloudCenterDemandService.changeDemandStatus(demandId, "5");//demand改状态位5为已下单

        //检测下单是否成功
        Result result = new Result();
        if (insertedCount == operatedCount) {
            result.setMsg("下单成功！成功下单数:" + insertedCount);
        } else {
            result.setMsg("下单异常！请联系工作人员！成功下单数:" + insertedCount);
        }
        try {
            PrintWriter writer = response.getWriter();
            String jsonString2 = JSON.toJSONString(result);
            writer.print(jsonString2);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param request
     * @param model
     * @auther lilimin
     * @Descript 云需求--我要报价--保存
     */
    @RequestMapping(value = "/member/cloudCenter_demandquote_save.jspx")
    public String save(HttpServletRequest request, ModelMap model,
                       Integer demandId,
                       String offerExplan,
                       Double price,
                       String contact,
                       String telephone,
                       String mobile,
                       String email,
                       String fax,
                       Date deadlineDt
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        SIcloudDemandQuote sDemandQuote = new SIcloudDemandQuote();
        SIcloudDemand demand = cloudCenterDemandService.byDemandId(demandId);
        //接收页面传来的值
        sDemandQuote.setIcloudDemand(demand);
        sDemandQuote.setOfferExplan(offerExplan);
        sDemandQuote.setContact(contact);
        sDemandQuote.setTelephone(telephone);
        sDemandQuote.setMobile(mobile);
        sDemandQuote.setEmail(email);
        sDemandQuote.setFax(fax);
        sDemandQuote.setPrice(price);
        sDemandQuote.setDeadlineDt(deadlineDt);
        //默认值set
        sDemandQuote.setCompany(user.getCompany());
        sDemandQuote.setOperatorId(user.getUserId().toString());
        sDemandQuote.setUpdateDt(new Date(System.currentTimeMillis()));
        sDemandQuote.setCreaterId(user.getUserId().toString());
        sDemandQuote.setReleaseDt(sDemandQuote.getUpdateDt());
        sDemandQuote.setCreateDt(sDemandQuote.getUpdateDt());
        sDemandQuote.setDeFlag("1");
        sDemandQuote.setSelectedStatus("0");//提交报价后为已报价
        //前往service层保存业务
        cloudCenterQuoteService.save(sDemandQuote);
        //保存完了去需求池展示页
        String nextUrl = "/yzyjyzxYxq/index.jhtml";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Desc 报价管理表--点击确认更新状态位
     * @Date 2017/1/12
     */
    @RequestMapping(value = "/member/cloudCenter_quotoManger_updateState.jspx")
    public String manageAdmin(Integer id, HttpServletRequest request, HttpServletResponse response,
                              ModelMap model, String demandState, String quoteState,
                              Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        String nextUrl = "/member/cloudcenter_offerList.jspx";
        //更新状态ss
        quotemanger.updateState(id, demandState, quoteState, channelId, user, charge, true);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Desc 报价管理表--撤单
     * @Date 2017/1/12
     */
    @RequestMapping(value = "/member/cloudCenter_quotoManger_remove.jspx")
    public String remove(Integer id, Integer demandId, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, String demandState, String quoteState,
                         Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        String nextUrl = "/member/cloudcenter_offerList.jspx";
        //更新状态
        quotemanger.remove(id, demandId, channelId, user, charge, true);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/2/13 后台页面-云需求管理 对多条报价进行 优选/取消优选 的 ajax
     * toggleType: 1为进行优选 0为取消优选
     */
    @RequestMapping(value = "/member/cloudCenter_icDemandQuote_toggleSelectAjax.jspx", method = RequestMethod.POST)
    public void toggleSelect(Integer demandId, String quoteIds, String toggleType, HttpServletRequest request,
                             HttpServletResponse response) {
        boolean isSelect;
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        CmsUser user = CmsUtils.getUser(request);
        if (user == null) {
            return;
        }
        //用户没有关联公司的话不能继续
        if (user.getCompany() == null) {
            return;
        }
        //判断一下demandId是否为空
        if (demandId == null) {
            return;
        }
        //判断一下demand是否为用户公司的 不能优选非本公司的需求的 报价
        if (!user.getCompany().getCompanyId().equals(cloudCenterDemandService.byDemandId(demandId).getCompany().getCompanyId())) {
            return;
        }
        //判断优选 or 取消优选， 参数不存在则直接返回
        if ("1".equals(toggleType)) {
            isSelect = true;
        } else if ("0".equals(toggleType)) {
            isSelect = false;
        } else {
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            //报价状态位更改 + 需求状态位更改，把更改结果作为判断条件，值返回页面
            if (isSelect && cloudCenterDemandService.changeDemandStatus(demandId, "4") > 0) {
                writer.print("{\"rows\":\"" + cloudCenterQuoteService.selectQuotesChange(quoteIds, "1") + "\"}");
            } else {
                writer.print("{\"rows\":\"" + cloudCenterQuoteService.selectQuotesChange(quoteIds, "0") + "\"}");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Resource
    private CloudCenterQuoteService cloudCenterQuoteService;
    @Resource
    private CloudCenterDemandService cloudCenterDemandService;
    @Resource
    private CloudCenterQuoteMangerService quotemanger;
    @Resource
    private ChannelMng channelMng;
}
