package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPOrderObj;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.payment.SSupplychainOrderObj;
import com.anchorcms.icloud.model.supplychain.SRepairInquiry;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.supplychain.SRepairInquiryService;
import com.anchorcms.icloud.service.supplychain.SRepairResService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.anchorcms.common.constants.Constants.TPLDIR_SUPPLYCHAIN;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Created by hansx on 2016/12/27.
 * <p>
 * 维修资源询价
 */
@Controller
public class SRepairResInquiryPriceController {
    private static final Logger log = LoggerFactory.getLogger(SRepairResInquiryPriceController.class);

    public static final String TPL_REPAIRRES_INQUIRYPRICE = "tpl.repairres_inquiryprice";
    public static final String COMPANY_APTITUDE_ADD = "/member/index.jspx";
    public static final String COMPANY_APTITUDE_ADD2 = "/member/company_edit.jspx";
    SRepairRes sRepairRes;

    /**
     * @return 转至询价页面
     * @author hansx
     * @Date 2017/1/5 0005 上午 11:56
     */
    @RequestMapping(value = "/member/inquiryprice.jspx")
    public String findRepairResById(String id, HttpServletRequest request,
                                    HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        if (true) {
            FrontUtils.frontData(request, model, site);
            MemberConfig mcfg = site.getConfig().getMemberConfig();
            // 没有开启会员功能
            if (mcfg==null||!mcfg.isMemberOn()) {
                return FrontUtils.showMessage(request, model, "member.memberClose");
            }
            if (user == null) {
                return FrontUtils.showLogin(request, model, site);
            }
            if (user.getCompany() == null) {
                return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
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
            model.addAttribute("site", site);
            model.addAttribute("channel",channelMng.findById(108));
            model.addAttribute("channelList", channelList);
            model.addAttribute("sessionId", request.getSession().getId());
            model.addAttribute("channel",channelMng.findById(99));
            model.addAttribute("user", user);
            if (id != null&&!id.equals("")) {
                //根据id获取维修资源，
                sRepairRes = sRepairResService.findById(id);
                if(sRepairRes!=null)
                model.addAttribute("repairres", sRepairRes);

            }
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SUPPLYCHAIN, TPL_REPAIRRES_INQUIRYPRICE);
        } else {

            WebErrors errors = WebErrors.create(request);
            errors.addErrorCode("error.uploadMoreNumber", user.getGroup().getId());
            return FrontUtils.showError(request, response, model, errors);
        }
    }

    /**
     * @return 询价信息保存
     * @author hansx
     * @Date 2017/1/5 0005 下午 1:35
     */
    @RequestMapping(value = "/member/inquirysave.jspx")
    public String saveInquiry(SRepairInquiry sRepairInquiry,String truction, HttpServletRequest request, ModelMap model,
                              String nextUrl, Integer modelId, Integer channelId, String[] attachmentPaths, String[] attachmentNames,
                              String[] attachmentFilenames) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        Content c = new Content();
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
        ext.setTitle(sRepairInquiry.getInquiryTheme() + "");
        ext.setAuthor(user.getUsername());
        ext.setDescription("供应链维修资源询价发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(truction);

        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        //设置主键
        String uid = UUID.randomUUID().toString().replaceAll("-", "");
        sRepairInquiry.setInquiryId(uid);
        //设置询价资源的信息
        sRepairInquiry.setsRepairRes(sRepairRes);
        //登录用户的企业信息
        sRepairInquiry.setCompany(user.getCompany());
        //创建人id
        sRepairInquiry.setCreaterId(user.getUserId().toString());
        //创建时间
        sRepairInquiry.setCreateDt(new Date(System.currentTimeMillis()));
        //发布时间
        sRepairInquiry.setReleaseDt(new Date(System.currentTimeMillis()));
        c = sRepairInquiryService.save(sRepairInquiry, c, ext, t, channelId, typeId, user, true, attachmentPaths, attachmentNames, attachmentFilenames);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    /**
     * @author hansx
     * @Date 2017/1/19 12:15
     * @return
     * @description 供应链-维修需求方-询价管理
     */
    public static final String SUPPLYDETAIL_INQUIRY_PRICE_MANAGER = "tpl.supplychaininquiry_price_manager";

    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/inquiry_price_manager.jspx")
    public String inquiry_price_manager(String statusType, String inquiryTheme, Date startDate, Date endDate, String companyName,
                                        Integer modelId, Integer queryChannelId, Integer pageNo, HttpServletRequest request,
                                        ModelMap model, String id, String flag, String status,
                                        String state,String demandObjId,String evaluteValue,String flagevalute) {
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
        if (user.getCompany() == null) {
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD2);
        }

        if (flag != null && (flag.equals("close") || flag.equals("order"))) {//询价管理-状态更新（下单/关闭）
            sRepairInquiryService.updateStatusById(id, status);
            //生成订单
            if(flag.equals("order")){
            SRepairInquiry inquiry=sRepairInquiryService.findById(id);
            SRepairRes res=  inquiry.getsRepairRes();

//        List<SDemandQuoteObj> quoteObj = sDemandQuoteService.byDemandQuoteObjId(demandQuoteId);
            SSupplychainOrder buy = new SSupplychainOrder();
            SSupplychainOrderObj o = new SSupplychainOrderObj();
//        buy.setOrderId(quote.getDemandQuoteId());
            o.setObjName(res.getRepairName());
            o.setObjPrice(inquiry.getOffer());
            o.setObjNum(inquiry.getDemandCount());
            buy.setRepairName(res.getRepairName());
            buy.setSupplyContact(res.getContact());
            buy.setSupplycompany(res.getScompany());
            buy.setSupplyMobile(res.getMobile());
            buy.setBuycompany(inquiry.getCompany());
            buy.setBuyMobile(inquiry.getMobile());
            buy.setBuyContact(inquiry.getContact());
            buy.setOperator(inquiry.getOperatorId());
            buy.setPrice(inquiry.getOffer());
            buy.setOrderDt(new Date(System.currentTimeMillis()));
//    buy.setNum(inquiry.getDemandCount());
            //buy.setPayerID(payerID);
            //buy.setpayerName;
            //订单号
            List<SSupplychainOrderObj> li = new ArrayList<SSupplychainOrderObj>();li.add(o);
            buy.setSOrderObjList(li);
            String guId = null;
            try {
                guId = GUIDGenerator.genGUID();
            } catch (Exception e) {
                e.printStackTrace();
            }
            buy.setOrderId(guId);
            String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
            o.setOrderObjId(uid);
            o.setOrderId(guId);
            buy.setState("0");
            sRepairInquiryService.saveOrder(buy);
                return FrontUtils.showSuccess(request, model, "/member/inquiry_price_manager.jspx?statusType=5");
        }
        }
        String companyId = user.getCompany().getCompanyId();
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //条件查询参数
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("companyName", companyName);
        model.addAttribute("statusType", statusType);

        if(flagevalute!=null&&flagevalute.equals("talk")){
            sRepairInquiryService.setEvaluteValue(demandObjId,evaluteValue);
            return FrontUtils.showSuccess(request, model, "/member/inquiry_price_manager.jspx?statusType=5");
        }else{
            //条件查询列表
            Pagination p = sRepairInquiryService.getInquiryListForMember(statusType, inquiryTheme, startDate, endDate,
                    companyName, companyId, queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20,false);
            model.addAttribute("pagination", p);
        }

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, SUPPLYDETAIL_INQUIRY_PRICE_MANAGER);
    }
/**
 * @author liuyang
 * @Date 2017/5/4 18:10
 * @return 维修资源订单生成
 */
@RequestMapping(value = "/member/gylxt_wxzy.jspx")
public String orderdo(HttpServletRequest request, HttpServletResponse response,
                      ModelMap model,String id,String inquiryInfoId) {
    CmsSite site = CmsUtils.getSite(request);
    CmsUser user = CmsUtils.getUser(request);
    FrontUtils.frontData(request, model, site);
    if (user == null) {
        return FrontUtils.showLogin(request, model, site);
    }
//        SAbility ablity = synergyManageService.byAbilityId(abilityId);
    SRepairInquiry inquiry=sRepairInquiryService.findById(id);
    SRepairRes res=  inquiry.getsRepairRes();

//        List<SDemandQuoteObj> quoteObj = sDemandQuoteService.byDemandQuoteObjId(demandQuoteId);
    SSupplychainOrder buy = new SSupplychainOrder();
    SSupplychainOrderObj o = new SSupplychainOrderObj();
//        buy.setOrderId(quote.getDemandQuoteId());
    o.setObjName(res.getRepairName());
    o.setObjPrice(inquiry.getOffer());
    o.setObjNum(inquiry.getDemandCount());
    buy.setRepairName(res.getRepairName());
    buy.setSupplyContact(res.getContact());
    buy.setSupplycompany(res.getScompany());
    buy.setSupplyMobile(res.getMobile());
    buy.setBuycompany(inquiry.getCompany());
    buy.setBuyMobile(inquiry.getMobile());
    buy.setBuyContact(inquiry.getContact());
    buy.setOperator(inquiry.getOperatorId());
    buy.setPrice(inquiry.getOffer());
    buy.setOrderDt(new Date(System.currentTimeMillis()));
//    buy.setNum(inquiry.getDemandCount());
    //buy.setPayerID(payerID);
    //buy.setpayerName;
    //订单号
    List<SSupplychainOrderObj> li = new ArrayList<SSupplychainOrderObj>();li.add(o);
    buy.setSOrderObjList(li);
    String guId = null;
    try {
        guId = GUIDGenerator.genGUID();
    } catch (Exception e) {
        e.printStackTrace();
    }
        buy.setOrderId(guId);
    String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
    o.setOrderObjId(uid);
        o.setOrderId(guId);
        buy.setState("0");
    sRepairInquiryService.saveOrder(buy);
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SOFTWARE_PAY);*/
    //String nextUrl = "/yzyjyzxYzyck/index.jhtml";
    String nextUrl = "/member/inquiry_price_manager.jspx";
    return FrontUtils.showSuccess(request, model, nextUrl);
}
    @Resource
    private SRepairResService sRepairResService;
    @Resource
    private SRepairInquiryService sRepairInquiryService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
