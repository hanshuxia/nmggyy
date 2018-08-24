package com.anchorcms.icloud.controller.synergy;

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
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.synergy.EnquiryService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
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
 * @Author wanjinyou
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/26
 * @Desc 我要询价
 */
@Controller
public class EnquiryController {
    private static final Logger log = LoggerFactory.getLogger(EnquiryController.class);
    public static final String SYNERGYENQUIRY = "tpl.synergyEnquiry";
    public static final String BACKENQUIRY = "tpl.synergyBackEnquiry";
    public static final String ABILITYQUOTELIST ="tpl.synergyAbilityInquiryList";
    public static final String COMPANY_APTITUDE_ADD = "/member/index.jspx";
    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/26
     * @Desc 询价界面请求
     */
    @RequestMapping("/member/synergy_enquiry.jspx")
    public String add(Integer id,  HttpServletRequest request, HttpServletResponse response,
                      ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        String nextUrl;
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if(user.getCompany() == null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        if(id == null){
            return FrontUtils.showMessage(request, model, "error.needParams");
        }

        SAbility ability = enquiryService.byAbilityId(id);
        //只能对发布中的能力询价
        if(!"3".equals(ability.getStatusType())){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        //用户不能为自己的能力询价
        if(user.getCompany().getCompanyId().equals(ability.getCompanyId())){
            //return FrontUtils.showMessage(request, model, "error.noPermissionsView");
            nextUrl="/xtzzNlczs/xtzz_ability_preview.jspx?id="+id;
            return FrontUtils.showBaseMessage(request,model,"error.noPermissionsView", nextUrl);
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("ability", ability);
        model.addAttribute("site", site);
        model.addAttribute("channel",channelMng.findById(103));
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SYNERGYENQUIRY);
    }

    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/28
     * @Desc 询价界面保存
     * @param request  request对象
     * @param response response对象
     * @param model    model对象
     * @param demandCount 需求数量
     * @param expectPrice 期望单价
     * @param deadlineDt 询价截止日期
     * @param deliverDt 要求交付日期
     * @param instruction 补充说明
     * @param dealType 交易方式
     * @param payType 付款方式
     * @param invoiceType 发票类型
     * @param freightBorne 运费承担商
     * @param addrProvince 收货地址省
     * @param addrCity 收货地址市
     * @param addrCounty 收货地址区/县
     * @param addrStreet 收货地址街道
     * @param contact 联系人
     * @param mobile 联系电话(手机)
     * @param fax    传真
     * @param email  邮箱
     * @return
     */
    @RequestMapping(value = "/member/enquiry_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       //P.S.参数竖着放是为了方便加注释和debug
                       Double demandCount,
                       Double expectPrice,
                       Date deadlineDt,
                       Date deliverDt,
                       String inquiryTheme,

                       String dealType,
                       String payType,
                       String invoiceType,
                       String freightBorne,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       String contact,
                       String mobile,
                       String fax,
                       String email,
                       Integer abilityId,
                       String postCode,
                       String invoiceCompany,
                       String taxRegNo,
                       String registeredAddress,
                       String bankInfo,
                       //String companyId,

                       //cms表相关参数
                       //String editor,
                       String instruction,String nextUrl,
                       Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if(user.getCompany() == null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }

        Content c = new Content();
        SAbilityInquiry enquiry = new SAbilityInquiry();
        enquiry.setDemandCount(demandCount);
        enquiry.setExpectPrice(expectPrice);
        enquiry.setDeadlineDt(deadlineDt);
        enquiry.setDeliverDt(deliverDt);
        enquiry.setInquiryTheme(inquiryTheme);
        enquiry.setDealType(dealType);
        enquiry.setPayType(payType);
        enquiry.setInvoiceType(invoiceType);
        enquiry.setFreightBorne(freightBorne);
        enquiry.setAddrProvince(addrProvince);
        enquiry.setAddrCity(addrCity);
        enquiry.setAddrCounty(addrCounty);
        enquiry.setAddrStreet(addrStreet);
        enquiry.setContact(contact);
        enquiry.setMobile(mobile);
        enquiry.setFax(fax);
        enquiry.setEmail(email);
        enquiry.setPostCode(postCode);
        enquiry.setInvoiceCompany(invoiceCompany);
        enquiry.setTaxRegNo(taxRegNo);
        enquiry.setRegisteredAddress(registeredAddress);
        enquiry.setBankInfo(bankInfo);

        //默认值set
        enquiry.setCompany(user.getCompany());
       // enquiry.setStatus("1");
        enquiry.setStatusType("1");
        enquiry.setOperatorId(user.getUserId().toString());
        enquiry.setCreateDt(new Date(System.currentTimeMillis()));
        enquiry.setReleaseDt(enquiry.getCreateDt());
        enquiry.setUpdateDt(enquiry.getCreateDt());
        enquiry.setDeFlag("1");

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
        ext.setTitle(inquiryTheme);
        ext.setAuthor(user.getUsername());
        ext.setDescription("询价页面");
        ContentTxt t = new ContentTxt();
        if(instruction == null){
            instruction="";
        }
        t.setTxt(instruction);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        c = enquiryService.save(abilityId,enquiry,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true);
        //跳转到预览页面
     //   String nextUrl="/xtzzNlczs/xtzz_ability_preview.jspx?id=${abilityId}";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/1/16
     * @Desc 后台询价界面请求
     */
    @RequestMapping("/member/synergy_back_enquiry.jspx")
    public String addBack(Integer id,  HttpServletRequest request, HttpServletResponse response,
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
        if(user.getCompany() == null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        if(id == null){
            return FrontUtils.showMessage(request, model, "error.needParams");
        }

        SAbility ability = enquiryService.byAbilityId(id);
        //用户不能为自己的能力询价，解开注释即可使用
//        if(user.getCompany().getCompanyId().equals(ability.getCompanyId())){
//            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
//        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("ability", ability);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BACKENQUIRY);
    }

    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/1/16
     * @Desc 后台询价界面保存
     * @param request  request对象
     * @param response response对象
     * @param model    model对象
     * @param demandCount 需求数量
     * @param expectPrice 期望单价
     * @param deadlineDt 询价截止日期
     * @param deliverDt 要求交付日期
     * @param instruction 补充说明
     * @param dealType 交易方式
     * @param payType 付款方式
     * @param invoiceType 发票类型
     * @param freightBorne 运费承担商
     * @param addrProvince 收货地址省
     * @param addrCity 收货地址市
     * @param addrCounty 收货地址区/县
     * @param addrStreet 收货地址街道
     * @param contact 联系人
     * @param mobile 联系电话(手机)
     * @param fax    传真
     * @param email  邮箱
     * @return
     */
    @RequestMapping(value = "/member/enquiry_back_save.jspx")
    public String saveBack(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       //P.S.参数竖着放是为了方便加注释和debug
                       Double demandCount,
                       Double expectPrice,
                       Date deadlineDt,
                       Date deliverDt,
                       String inquiryTheme,

                       String dealType,
                       String payType,
                       String invoiceType,
                       String freightBorne,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       String contact,
                       String mobile,
                       String fax,
                       String email,
                       Integer abilityId,
                       //String companyId,

                       //cms表相关参数
                       //String editor,
                       String instruction,

                       Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if(user.getCompany() == null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }

        Content c = new Content();
        SAbilityInquiry enquiry = new SAbilityInquiry();
        enquiry.setDemandCount(demandCount);
        enquiry.setExpectPrice(expectPrice);
        enquiry.setDeadlineDt(deadlineDt);
        enquiry.setDeliverDt(deliverDt);
        enquiry.setInquiryTheme(inquiryTheme);
        enquiry.setDealType(dealType);
        enquiry.setPayType(payType);
        enquiry.setInvoiceType(invoiceType);
        enquiry.setFreightBorne(freightBorne);
        enquiry.setAddrProvince(addrProvince);
        enquiry.setAddrCity(addrCity);
        enquiry.setAddrCounty(addrCounty);
        enquiry.setAddrStreet(addrStreet);
        enquiry.setContact(contact);
        enquiry.setMobile(mobile);
        enquiry.setFax(fax);
        enquiry.setEmail(email);

        //默认值set
        enquiry.setCompany(user.getCompany());
        // enquiry.setStatus("1");
        enquiry.setStatusType("1");
        enquiry.setOperatorId(user.getUserId().toString());
        enquiry.setCreateDt(new Date(System.currentTimeMillis()));
        enquiry.setReleaseDt(enquiry.getCreateDt());
        enquiry.setUpdateDt(enquiry.getCreateDt());
        enquiry.setDeFlag("1");

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
        ext.setTitle(inquiryTheme);
        ext.setAuthor(user.getUsername());
        ext.setDescription("询价页面");
        ContentTxt t = new ContentTxt();
        if(instruction == null){
            instruction="";
        }
        t.setTxt(instruction);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        c = enquiryService.save(abilityId,enquiry,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true);
        //跳转到能力询价管理列表
        String nextUrl="/member/synergy_demand_abilityInquiryList.jspx?canshu=1";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private EnquiryService enquiryService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
