package com.anchorcms.icloud.controller.cloudcenter;

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
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.service.cloudcenter.CloudService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/310:35
 */
@Controller
public class CloudCenterCreateContriller {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterCreateContriller.class);
    public static final String CLOUND_LIST = "tpl.cloudCenterList";
    public static final String CLOUND_DETAIL = "tpl.cloudCenterDetail";

    /**
     * @Author 阁楼麻雀
     * @Date 2016/12/19 13:06
     * @Desc 发布云需求
     */
    @RequiresPermissions("member:cloundcenter_clound_add")
    @RequestMapping("/member/cloundcenter_clound_add.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      ModelMap model) {
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
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, CLOUND_LIST);
    }

    /**
     * @Author wanjinyou
     * @Date 2017/1/10 9:02
     * @Desc 前台三级页面发布云需求请求
     */
    @RequiresPermissions("member:cloundcenter_clound_add")
    @RequestMapping("/member/cloundcenter_add_demand.jspx")
    public String addDemand(HttpServletRequest request, HttpServletResponse response, String frontStatus,
                            ModelMap model) {
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
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("frontStatus", frontStatus);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("channel", channelMng.findById(100));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, CLOUND_DETAIL);
    }

    /**
     * @author: lilimin
     * @desciption 添加新需求的业务controller
     */
    @RequestMapping(value = "/member/cloundCenter_demand_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       //P.S.参数竖着放是为了方便加注释和debug
                       String demand_name,//需求名称
                       String server_type,//服务器类型
                       Integer count,//数量
                       String dealType,//交易方式
                       String payType,//付款方式
                       String invoiceType,//发票类型
                       String freightBorne,//运费承担商
                       String addrProvince,//addrProvince
                       String addrCity,//地级市
                       String addrCounty,//区/县
                       String addrStreet,//街道
                       String releaseType,//发布方式
                       String contact,//联系信息
                       String mobile,//联系电话
                       String telephone,//移动电话
                       String email,//邮箱
                       String fax,//传真
                       String states,//状态
                       String inquiryComp,
                       String createrId,
                       Date deadlineDt,//询价截止日期
                       Date deliverDt,//要求交货日期
                       String statusType,//状态尅性
                       String classify_code,//需求编码
                       String unit,//计量单位
                       double expect_price,//期望单价
                       //cms表相关参数
                       String detailDesc,
                       String invoiceCompany,
                       String taxRegNo,
                       String registeredAddress,
                       String bankInfo,
                       // String nextUrl,
                       Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SIcloudDemand sd = new SIcloudDemand();
        sd.setDemandName(demand_name);
        sd.setServerType(server_type);
        sd.setCount(count);
        sd.setDealType(dealType);
        sd.setPayType(payType);
        sd.setInvoiceType(invoiceType);
        sd.setFreightBorne(freightBorne);
        sd.setAddrProvince(addrProvince);
        sd.setAddrCity(addrCity);
        sd.setAddrCounty(addrCounty);
        sd.setAddrStreet(addrStreet);
        sd.setReleaseType(releaseType);
        sd.setContact(contact);
        sd.setMobile(mobile);
        sd.setTelephone(telephone);
        sd.setEmail(email);
        sd.setFax(fax);
        sd.setUnit(unit);
        sd.setClassify_code(classify_code);
        sd.setExpect_price(expect_price);
        sd.setInquiryComp(inquiryComp);
        sd.setDeadlineDt(deadlineDt);
        sd.setDeliverDt(deliverDt);
        sd.setCompany(user.getCompany());
        sd.setReleaseDt(new Date(System.currentTimeMillis()));
        //默认值set
        sd.setStatus(statusType);
        sd.setDeFlag("1");
        sd.setCreateDt(new Date(System.currentTimeMillis()));
        sd.setUpdateDt(sd.getReleaseDt());
        sd.setCreaterId(user.getUserId() + "");
        sd.setInvoiceCompany(invoiceCompany);
        sd.setTaxRegNo(taxRegNo);
        sd.setRegisteredAddress(registeredAddress);
        sd.setBankInfo(bankInfo);
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
        ext.setTitle(demand_name);
        ext.setAuthor(user.getUsername());
        ext.setDescription("云需求发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(detailDesc);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        c = cloudService.save(sd, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs, channelId, typeId, user, charge, true);
        //跳转至需求管理列表
        String nextUrl = "/member/cloudcenter_demand_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author: lilimin
     * @desciption 前台发布云需求的保存
     */
    @RequestMapping(value = "/member/cloundCenter_save.jspx")
    public String saveDemand(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             //P.S.参数竖着放是为了方便加注释和debug
                             String demand_name,//需求名称
                             String server_type,//服务器类型
                             Integer count,//数量
                             String dealType,//交易方式
                             String payType,//付款方式
                             String invoiceType,//发票类型
                             String freightBorne,//运费承担商
                             String addrProvince,//addrProvince
                             String addrCity,//地级市
                             String addrCounty,//区/县
                             String addrStreet,//街道
                             String releaseType,//发布方式
                             String contact,//联系信息
                             String mobile,//联系电话
                             String telephone,//移动电话
                             String email,//邮箱
                             String fax,//传真
                             String inquiryComp,
                             String createrId,
                             Date deadlineDt,//询价截止日期
                             Date deliverDt,//要求交货日期
                             String statusType,//状态尅性
                             String classify_code,//需求编码
                             String unit,//计量单位
                             double expect_price,//期望单价
                             String frontStatus,

                             //cms表相关参数
                             String detailDesc,
                             String nextUrl,
                             Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                             String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SIcloudDemand sd = new SIcloudDemand();
        sd.setDemandName(demand_name);
        sd.setServerType(server_type);
        sd.setCount(count);
        sd.setDealType(dealType);
        sd.setPayType(payType);
        sd.setInvoiceType(invoiceType);
        sd.setFreightBorne(freightBorne);
        sd.setAddrProvince(addrProvince);
        sd.setAddrCity(addrCity);
        sd.setAddrCounty(addrCounty);
        sd.setAddrStreet(addrStreet);
        sd.setReleaseType(releaseType);
        sd.setContact(contact);
        sd.setMobile(mobile);
        sd.setTelephone(telephone);
        sd.setEmail(email);
        sd.setFax(fax);
        sd.setUnit(unit);
        sd.setClassify_code(classify_code);
        sd.setExpect_price(expect_price);
        sd.setInquiryComp(inquiryComp);
        sd.setDeadlineDt(deadlineDt);
        sd.setDeliverDt(deliverDt);
        sd.setCompany(user.getCompany());
        sd.setReleaseDt(new Date(System.currentTimeMillis()));
        //默认值set
        sd.setStatus(statusType);
        sd.setDeFlag("1");
        sd.setCreateDt(new Date(System.currentTimeMillis()));
        sd.setUpdateDt(sd.getReleaseDt());
        sd.setCreaterId(user.getUserId() + "");

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
        ext.setTitle(demand_name);
        ext.setAuthor(user.getUsername());
        ext.setDescription("云需求发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(detailDesc);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        c = cloudService.save(sd, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs, channelId, typeId, user, charge, true);
        //跳转至前台展示页
        if ("1".equals(frontStatus)) {
            nextUrl = "/yzyjyzx/index.jhtml";
        } else {
            nextUrl = "/yzyjyzxYxq/index.jhtml";
        }

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private CloudService cloudService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
}