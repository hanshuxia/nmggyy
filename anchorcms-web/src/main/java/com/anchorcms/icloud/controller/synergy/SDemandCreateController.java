package com.anchorcms.icloud.controller.synergy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.cloudcenter.Result;
import com.anchorcms.icloud.model.common.SMemberAddress;
import com.anchorcms.icloud.model.commservice.SBigDataDemandSign;
import com.anchorcms.icloud.model.commservice.SBigDataNews;
import com.anchorcms.icloud.model.commservice.SBigDataPolicy;
import com.anchorcms.icloud.model.commservice.SBigDataSign;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.service.common.SMemberAddrService;
import com.anchorcms.icloud.service.synergy.SDemandCreateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import java.sql.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;

/*
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * @author: Gao Jiangning
 * @date: 2016/12/23 0023
 * 添加需求controller
 */
@Controller
public class SDemandCreateController {
    private static final Logger log = LoggerFactory.getLogger(SDemandCreateController.class);
    public static final String SDEMAND_ADD = "tpl.synergySdemandAdd";
    public static final String GET_CONTACT_ADDR_ADD = "tpl.getContactAddrAdd";
    public static final String GET_CONTACT_MANAGE_ADD = "tpl.contact_address_mannage_add";

    /**
     * 前往添加需求页面的controller
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("member:synergy_demand_add")
    @RequestMapping("/synergy_demand_add.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      String frontStatus,String cyrh,ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        model.addAttribute("cyrh", cyrh);
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
        model.addAttribute("frontStatus", frontStatus);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("user", user);
        //首页的发布需求，则栏目为首页；展示页的发布，则栏目为协同制造
        if("1".equals(frontStatus)){
            model.addAttribute("channel",channelMng.findById(103));
        }else{
            model.addAttribute("channel",channelMng.findById(1));
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SDEMAND_ADD);
    }

    /**
     * @author: gao jiangning
     * @desciption 添加新需求的业务controller
     */
    @RequestMapping(value = "/member/synergy_demand_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       //询价对象list相关的参数，Json串
                       String demandObjListJsonString,

                       //P.S.参数竖着放是为了方便加注释和debug
                       String classifyType,
                       String inquiryTheme,
                       String isShow,
                       String dealType,
                       String payType,
                       String invoiceType,
                       String freightBorne,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       String releaseType,
                       String contact,
                       String mobile,
                       String telephone,
                       String email,
                       String fax,
                       String inquiryComp,
                       Date deadlineDt,
                       Date deliverDt,
                       String statusType,
                       String frontStatus,
                       String invoiceCompany,
                       String taxRegNo,
                       String registeredAddress,
                       String bankInfo,
                       String cyrh,

                       //cms表相关参数
                       String editor,
                       String postCode,
                       Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SDemand sd = new SDemand();
        sd.setClassifyType(classifyType);
        sd.setInquiryTheme(inquiryTheme);
        sd.setIsShow(isShow);
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
        sd.setInquiryComp(inquiryComp);
        sd.setDeadlineDt(deadlineDt);
        sd.setDeliverDt(deliverDt);
        sd.setPostCode(postCode);
        sd.setInvoiceCompany(invoiceCompany);
        sd.setTaxRegNo(taxRegNo);
        sd.setRegisteredAddress(registeredAddress);
        sd.setBankInfo(bankInfo);
        //默认值set
        sd.setStatus("0");
        sd.setDeFlag("1");
        sd.setCreateDt(new Date(System.currentTimeMillis()));
        sd.setUpdateDt(sd.getCreateDt());
        sd.setCompany(user.getCompany());
        sd.setStatusType(statusType);
        sd.setCreaterId(user.getUserId().toString());//暂时和UserId一样
        sd.setOperatorId(sd.getCreaterId());//暂时和createrId一样
        sd.setRecommendedType("0");

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
        ext.setDescription("需求发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(editor);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        c = sDemandCreateService.save(sd,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true,demandObjListJsonString);
        //跳转至需求管理列表
        String nextUrl;
        if("1".equals(frontStatus)) {
            nextUrl = "/xtzzXqczs/index.jhtml";
        }
        else if("2".equals(frontStatus)){
            nextUrl = "/html/index.html";
        }else{
            nextUrl = "/member/synergy_demand_list.jspx";
        }
        if("1".equals(cyrh)){
            nextUrl = "/cyrhxqc/index-880000-undefined-undefined-undefined-undefined-880000-.jhtml";
        }else{
            nextUrl = "/member/synergy_bigdata_demand_list.jspx";
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    /**
     * @author suhe
     * @Date 2018/4/16 17:37
     * @return
     * 大数据新闻发布controller
     */
    @RequestMapping(value = "/member/bigdata_news_registed_save.jspx")
    public String saveBigdataNews(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                  String newsName, // 新闻名称
                                  String newsDesc, // 新闻内容

                                  //cms表相关参数
                                  String editor,
                                  String postCode,
                                  Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                                  String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SBigDataNews sd = new SBigDataNews();
        sd.setNewsName(newsName);
        sd.setNewsDesc(newsDesc);
        sd.setCreatDate(new Date(System.currentTimeMillis()));
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
        ext.setTitle(newsName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("大数据新闻");
        ContentTxt t = new ContentTxt();
        t.setTxt(editor);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        sDemandCreateService.saveBigdataNews(sd,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true);
        //跳转至需求管理列表
        String nextUrl;
        nextUrl = "/member/bigdata_news_manage.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author suhe
     * @Date 2018/4/16 17:37
     * @return
     * 大数据政策发布controller
     */
    @RequestMapping(value = "/member/bigdata_policy_registed_save.jspx")
    public String saveBigdataPolicy(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                  String policyName, // 新闻名称

                                  //cms表相关参数
                                  String editor,
                                  String postCode,
                                  Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                                  String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SBigDataPolicy sd = new SBigDataPolicy();
        sd.setPolicyName(policyName);
        sd.setCreatDate(new Date(System.currentTimeMillis()));
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
        ext.setTitle(policyName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("大数据政策");
        ContentTxt t = new ContentTxt();
        t.setTxt(editor);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        sDemandCreateService.saveBigdataPolicy(sd,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true);
        //跳转至需求管理列表
        String nextUrl;
        nextUrl = "/member/bigdata_policy_manage.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }



    /**
     * @author liuyang
     * @Date 2018/4/11 15:31
     * @return
     * 大数据能力报名controller
     */

    @RequestMapping(value = "/member/bigdata_registed_save.jspx")
    public String save2(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                        String companyName, // 公司名称
                        String companyAddress, // 公司地址
                        String companySite, // 企业网址
                        String registrationArea, // 工商注册业务区域
                        String scopeOfBusiness, // 工商注册业务范围
                        java.util.Date registrationTime, // 成立时间
                        String registeredCapital, // 注册资金
                        String staffNumber, //职工人数
                        String legalPerson, // 企业法人
                        String duties, // 职务
                        String enterpriseNature, // 企业性质
                        String contact, // 联系人
                        String department, // 部门
                        String contactDuties, // 职务
                        String telphone, // 办公电话
                        String mobilphone, //手机
                        String email, // 邮箱
                        String isInBigdata, // 是否加入大数据组织
                        String organizationalLevel, // 组织级别
                        String organizationalName, // 组织名称
                        String serviceDirection, // 产业融合服务方向
                        String serviceCompanyType, // 服务提供方企业类型
                        String dataSourse, // 数据源
                        String dataCirculationPlatform, // 数据流通平台
                        String hardware, // 硬件
                        String basicSoftware, // 基础软件
                        String applicationSoftware, // 应用软件
                        String infrastructure, // 基础设施
                        String systemIntegration, // 系统集成和软件开发
                        String enterpriseApplication, // 企业应用
                        String enterpriseIntroduction, // 企业简介
                        String excellentCase, // 企业优秀案例
                        java.util.Date creatDate, // 填表日期

                        //cms表相关参数
                        String editor,
                        String postCode,
                        Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SBigDataSign sd = new SBigDataSign();
        sd.setCompanyName(companyName);
        sd.setCompanyAddress(companyAddress);
        sd.setCompanySite(companySite);
        sd.setRegistrationArea(registrationArea);
        sd.setScopeOfBusiness(scopeOfBusiness);
        sd.setRegistrationTime(registrationTime);
        sd.setRegisteredCapital(registeredCapital);
        sd.setStaffNumber(staffNumber);
        sd.setLegalPerson(legalPerson);
        sd.setDuties(duties);
        sd.setEnterpriseNature(enterpriseNature);
        sd.setContact(contact);
        sd.setDepartment(department);
        sd.setContactDuties(contactDuties);
        sd.setTelphone(telphone);
        sd.setEmail(email);
        sd.setMobilphone(mobilphone);
        sd.setIsInBigdata(isInBigdata);
        sd.setOrganizationalLevel(organizationalLevel);
        sd.setOrganizationalName(organizationalName);
        sd.setServiceDirection(serviceDirection);
        sd.setServiceCompanyType(serviceCompanyType);
        sd.setDataSourse(dataSourse);
        sd.setDataCirculationPlatform(dataCirculationPlatform);
        sd.setHardware(hardware);
        //默认值set
        sd.setBasicSoftware(basicSoftware);
        sd.setApplicationSoftware(applicationSoftware);
        sd.setCreatDate(new Date(System.currentTimeMillis()));
        sd.setInfrastructure(infrastructure);
        sd.setSystemIntegration(systemIntegration);
        sd.setEnterpriseApplication(enterpriseApplication);
        sd.setEnterpriseIntroduction(enterpriseIntroduction);//暂时和UserId一样
        sd.setExcellentCase(excellentCase);//暂时和createrId一样
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
        ext.setTitle(companyName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("大数据报名");
        ContentTxt t = new ContentTxt();
        t.setTxt(editor);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        c = sDemandCreateService.save2(sd,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true);
        //跳转至需求管理列表
        String nextUrl;
        nextUrl ="/synergy_ability_add.jspx?frontStatus=1&flag=front&cyrh=1";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author liuyang
     * @Date 2018/4/13 15:07
     * @return
     * 大数据需求报名
     */
    @RequestMapping(value = "/member/bigdata_demand_registed_save.jspx")
    public String save3(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                        String companyName, // 公司名称
                        String companyAddress, // 公司地址
                        String companySite, // 企业网址
                        String registrationArea, // 工商注册业务区域
                        String scopeOfBusiness, // 工商注册业务范围
                        java.util.Date registrationTime, // 成立时间
                        String registeredCapital, // 注册资金
                        String staffNumber, //职工人数
                        String legalPerson, // 企业法人
                        String duties, // 职务
                        String enterpriseNature, // 企业性质
                        String contact, // 联系人
                        String department, // 部门
                        String contactDuties, // 职务
                        String telphone, // 办公电话
                        String mobilphone, //手机
                        String email, // 邮箱
                        String enterpriseInformationization,
                        String enterpriseIntroduction,
                        String companyDemand,
                        String agriculture,
                        String industry,
                        String service,
                        String energy,
                        String multipartyDataFusion, //
                        String largeDataPublicService, //
                        java.util.Date creatDate, // 填表日期

                        //cms表相关参数
                        String editor,
                        String postCode,
                        Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SBigDataDemandSign sd = new SBigDataDemandSign();
        sd.setCompanyName(companyName);
        sd.setCompanyAddress(companyAddress);
        sd.setCompanySite(companySite);
        sd.setRegistrationArea(registrationArea);
        sd.setScopeOfBusiness(scopeOfBusiness);
        sd.setRegistrationTime(registrationTime);
        sd.setRegisteredCapital(registeredCapital);
        sd.setStaffNumber(staffNumber);
        sd.setLegalPerson(legalPerson);
        sd.setDuties(duties);
        sd.setEnterpriseNature(enterpriseNature);
        sd.setContact(contact);
        sd.setDepartment(department);
        sd.setContactDuties(contactDuties);
        sd.setTelphone(telphone);
        sd.setEmail(email);
        sd.setMobilphone(mobilphone);
        sd.setEnterpriseInformationization(enterpriseInformationization);
        sd.setEnterpriseIntroduction(enterpriseIntroduction);
        sd.setAgriculture(agriculture);
        sd.setIndustry(industry);
        sd.setService(service);
        sd.setEnergy(energy);
        sd.setMultipartyDataFusion(multipartyDataFusion);
        sd.setLargeDataPublicService(largeDataPublicService);
        //默认值set
        sd.setCreatDate(new Date(System.currentTimeMillis()));
        sd.setEnterpriseIntroduction(enterpriseIntroduction);//暂时和UserId一样
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
        ext.setTitle(companyName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("大数据需求报名");
        ContentTxt t = new ContentTxt();
        t.setTxt(editor);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        c = sDemandCreateService.save3(sd,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true);
        //跳转至需求管理列表
        String nextUrl;
        nextUrl ="/synergy_demand_add.jspx?frontStatus=1&flag=front&cyrh=1";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }




    /**
     * @Author 闫浩芮
     * @Date 2017/1/4 0004 13:13
     * 编辑，更新需求信息
     */
    @RequestMapping(value = "/member/synergy_demand_edit.jspx")
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       //询价对象list相关的参数，Json串
                       String demandObjListJsonString,

                       //P.S.参数竖着放是为了方便加注释和debug
                       String classifyType,
                       String inquiryTheme,
                       String isShow,
                       String dealType,
                       String payType,
                       String invoiceType,
                       String freightBorne,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       String releaseType,
                       String contact,
                       String mobile,
                       String telephone,
                       String email,
                       String fax,
                       String inquiryComp,
                       Date deadlineDt,
                       Date deliverDt,
                       String statusType,
                       Integer demandId,
                     String postCode,
                     String invoiceCompany,
                     String taxRegNo,
                     String registeredAddress,
                     String bankInfo,
                       //cms表相关参数
                       String editor,
                       Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SDemand sd = sDemandCreateService.byDemandId(demandId);
        Content c = sd.getContent();
        sd.setClassifyType(classifyType);
        sd.setInquiryTheme(inquiryTheme);
        sd.setIsShow(isShow);
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
        sd.setInquiryComp(inquiryComp);
        sd.setDeadlineDt(deadlineDt);
        sd.setDeliverDt(deliverDt);
        sd.setPostCode(postCode);
        sd.setInvoiceCompany(invoiceCompany);
        sd.setTaxRegNo(taxRegNo);
        sd.setRegisteredAddress(registeredAddress);
        sd.setBankInfo(bankInfo);
        //默认值set
        sd.setUpdateDt(new Date(System.currentTimeMillis()));
        sd.setStatusType(statusType);
        sd.setOperatorId(user.getUserId().toString());
        c.setSite(site);
        ContentExt ext = c.getContentExt();
        ext.setTitle(inquiryTheme);
        ext.setAuthor(user.getUsername());
        ext.setDescription("需求发布");
        ContentTxt t = c.getContentTxt();

        //若表中无contentTxt的数据，新增一条
        if(editor == null){
            editor = "";
        }
        if(t == null){
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt(editor);
            t = contentTxt;
            contentTxtMng.save(t, c);
        }else{
            t.setTxt(editor);
        }

        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        sDemandCreateService.update(sd,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true,demandObjListJsonString);
        //跳转至需求管理列表
        String nextUrl = "/member/synergy_demand_list.jspx";
        model.addAttribute("user", user);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }





    /***
     * @author zhouyq
     * @date 2017-08-11
     * @return
     * @desc 增加会员地址信息维护
     */
    @RequestMapping(value = "/member/getContactAddrAdd.jspx")
    public String getContactAddrAdd(HttpServletRequest request, ModelMap model,Integer addrId) {
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
        SMemberAddress SMemberAddress = null;
        // 获得本站栏目列表
        model.addAttribute("SMemberAddress", SMemberAddress);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, GET_CONTACT_ADDR_ADD);
    }

    @RequestMapping(value = "/member/getContactAddrManageAdd.jspx")
    public String getContactAddrManageAdd(HttpServletRequest request, ModelMap model,Integer addrId ) {
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
        SMemberAddress SMemberAddress = sMemberAddrService.findById(addrId);
        // 获得本站栏目列表
        model.addAttribute("SMemberAddress", SMemberAddress);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, GET_CONTACT_MANAGE_ADD);
    }

    /**
     * @author: zhouyq
     * @desciption 获得会员地址列表
     * @Date 2017/8/12
     */
    @RequestMapping(value = "/member/getContactList.jspx", method = RequestMethod.POST)
    public void getOrderListAjax(Integer userId, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        List<SMemberAddress> sMemberAddressesList = sDemandCreateService.getContactJsonList(userId);
        result.setData(sMemberAddressesList);
        JSONObject jObj = (JSONObject) JSON.toJSON(result);
        String jsonString2 = JSON.toJSONString(jObj);
        PrintWriter writer = response.getWriter();
        writer.print(jsonString2);
        writer.flush();
        writer.close();
    }

    /**
     * @Author zhouyq
     * @Date 2017/8/13
     * @Desc 会员联系信息新增保存
     **/
    @RequestMapping(value = "/member/member_contact_save.jspx")
    public String saveContactInfo(HttpServletRequest request, ModelMap model, String nextUrl, Integer modelId,
                                  Integer channelId, String userName, String province, String city, String country,
                                  String address, String mobile, String zip, String tel, String email, String fax,
                                  Integer defAddr, String shipAddressName,Integer addrId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        Integer userId = user.getUserId();
        FrontUtils.frontData(request, model, site);
            SMemberAddress sMemberAddress = new SMemberAddress();
            sMemberAddress.setUserId(userId);
            sMemberAddress.setUserName(userName);
            sMemberAddress.setProvince(province);
            sMemberAddress.setCity(city);
            sMemberAddress.setCountry(country);
            sMemberAddress.setAddress(address);
            sMemberAddress.setMobile(mobile);
            sMemberAddress.setZip(zip);
            sMemberAddress.setEmail(email);
            sMemberAddress.setFax(fax);
            sMemberAddress.setDefAddr(defAddr);
            sMemberAddress.setShipAddressName(shipAddressName);
            // 若设为默认地址
//            if (defAddr == 1) {
//                sMemberAddrService.updateContactNoDef(userId);
//            }

            sMemberAddrService.saveContactInfo(channelId, modelId, user, sMemberAddress, userId, defAddr, addrId);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @RequestMapping(value = "/member/member_contact_edit.jspx")
    public String editContactInfo(HttpServletRequest request, ModelMap model, String nextUrl, Integer modelId,
                                  Integer channelId, String userName, String province, String city, String country,
                                  String address, String mobile, String zip, String tel, String email, String fax,
                                  Integer defAddr, String shipAddressName,Integer addrId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        Integer userId = user.getUserId();
        FrontUtils.frontData(request, model, site);
        SMemberAddress sd = sMemberAddrService.findById(addrId);
        sd.setUserId(userId);
        sd.setUserName(userName);
        sd.setProvince(province);
        sd.setCity(city);
        sd.setCountry(country);
        sd.setAddress(address);
        sd.setMobile(mobile);
        sd.setZip(zip);
        sd.setEmail(email);
        sd.setFax(fax);
        sd.setDefAddr(defAddr);
        sd.setShipAddressName(shipAddressName);
        // 若设为默认地址
//        if (defAddr == 1) {
//            sMemberAddrService.updateContactNoDef(userId);
//        }

        sMemberAddrService.saveContactInfo(channelId, modelId, user, sd, userId, defAddr, addrId);

//        if (request.getRequestURI() == "/member/contact_address_manage.jspx") {
//           nextUrl= "/member/contact_address_manage.jspx";
//        }

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SDemandCreateService sDemandCreateService;
    @Resource
    private SMemberAddrService sMemberAddrService;
    @Resource
    com.anchorcms.icloud.service.unionCity.unionCityService unionCityService;
}
