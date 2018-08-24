package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.model.synergy.*;
import com.anchorcms.icloud.service.supplychain.SRepairInquiryService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyAdminService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import com.anchorcms.icloud.service.synergy.SynergyCreateAdminService;
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
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Created by ly on 2016/12/26.
 * @desc 企业信息管理类
 */

/**
 * @author dongxuecheng
 * @Date 2017/2/20 13:16
 * @return
 * @description企业信息管理类(管理员用)
 */

@Controller
public class SynergyCompanyAdminController {
    private static final Logger log = LoggerFactory.getLogger(SynergyCompanyAdminController.class);
    public static final String COMPANY_INFO_ADMIN = "tpl.companyInfo_admin";
    public static final String COMPANY_BASEINFO_ADMIN = "tpl.companyBaseInfo_admin";
    public static final String COMPANY_DIPLOMA_LIST_ADMIN = "tpl.companyDiplomaList_admin";
    public static final String COMPANY_DIPLOMA_ADD_ADMIN = "tpl.companyDiplomaAdd_admin";
    public static final String COMPANY_DIPLOMA_EDIT_ADMIN = "tpl.companyDiplomaEdit_admin";
    public static final String COMPANY_DEVICE_LIST_ADMIN = "tpl.companyDeviceList_admin";
    public static final String COMPANY_DEVICE_VIEW = "tpl.companyDeviceView";
    public static final String COMPANY_DEVICE_ADD_ADMIN = "tpl.companyDeviceAdd_admin";
    public static final String COMPANY_DEVICE_EDIT_ADMIN = "tpl.companyDeviceEdit_admin";
    public static final String COMPANY_DETAIL_INFO_ADMIN = "tpl.companyDetailInfo_admin";
    public static final String ABILITY_LIST_ADMIN_ADMIN = "tpl.synergyAbilityList_admin";
    public static final String ABILITY_ADD = "tpl.synergyAbilityAdd_admin";
    public static final String COMPANY_DEVICE_INQUIRYPRICE = "tpl.companyDeviceInquiryPrice";
    public static final String COMPANY_APTITUDE_ADD = "/member/index.jspx";

    /**
     * @Author ly
     * @Date 2016/12/26 15:50
     * @Desc 打开企业信息维护页面
     **/
    @RequiresPermissions("member:company_edit")
    @RequestMapping("/member/company_edit_admin.jspx")
    public String companyInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model,String companyId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        WebErrors errors = WebErrors.create(request);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员or主帐号(1为主账号)不能查看
        if(!user.getIsAdmin() && !"1".equals(user.getIsMain())){
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request,response,model,errors);
        }

        if(companyId!=null){
            model.addAttribute("companyId", companyId);
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_INFO_ADMIN);
    }

    /**
     * @Author ly
     * @Date 2016/12/26 15:50
     * @Desc 打开企业基本信息
     **/
    @RequiresPermissions("member:company_base_info")
    @RequestMapping("/member/company_base_info_admin.jspx")
    public String info(HttpServletRequest request, ModelMap model,String companyId) {
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
        if(companyId!=null&&!companyId.equals("")){
            SCompany company=synergyCompanyService.findSCompanyById(companyId);
            model.addAttribute("company", company);
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("companyId", companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_BASEINFO_ADMIN);
    }

    /**
     * @Author ly
     * @Date 2016/12/26 18:36
     * @Desc 企业基本信息保存
     * @param request
     * @param response
     * @param model
     * @param companyCode  企业号
     * @param companyName  公司名称
     * @param companyType  企业类型
     * @param operateType  经营模式
     * @param mainProduct  主营产品
     * @param deviceCount  设备数量
     * @param productCount 生产数量
     * @param mobile       联系电话
     * @param addrStreet   所在地区
     * @param registerDt   注册时间
     * @param modelId
     * @param channelId
     * @param charge
     **/
    @RequestMapping(value = "/member/company_baseinfo_save_admin.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String companyCode, String companyName, String companyType, String serverType,String industryType,String operateType,
                       String companyScale, String mainProduct, Integer deviceCount, Integer productCount,
                       String mobile,String sites,String email, String addrProvince,String addrCity,String addrCounty,String addrStreet, Date registerDt,  Integer modelId,
                       Integer channelId, Short charge,String postCode) {

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SCompany company = new SCompany();
        company.setCompanyCode(companyCode);//企业号
        company.setCompanyName(companyName);//企业名称
        company.setCompanyType(companyType);//企业类型
        company.setCompanyScale(companyScale);//企业规模
        company.setServerType(serverType);//服务类型
        company.setIndustryType(industryType);//行业分类
        company.setOperateType(operateType);//经营模式
        company.setDeviceCount(deviceCount);//设备数量
        company.setProductCount(productCount);//产品数量
        company.setMainProduct(mainProduct);//主营产品
        company.setRegisterDt(registerDt);//注册时间
        company.setSites(sites);//企业网址
        company.setEmail(email);//企业邮箱
        company.setMobile(mobile);//联系电话
        company.setAddrProvince(addrProvince);//所在地区省
        company.setAddrCity(addrCity);//所在地区市
        company.setAddrCounty(addrCounty);//所在地区县
        company.setAddrStreet(addrStreet);//所在地区
        company.setPostCode(postCode);//邮编
        SCompany new_SCompany=synergyCompanyService.save(company, channelId, modelId, user, charge, true);
        String nextUrl="company_base_info_admin.jspx?companyId="+new_SCompany.getCompanyId();
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2016/12/28 10:51
     * @Desc 企业荣誉列表
     **/
    @RequiresPermissions("member:company_diploma")
    @RequestMapping("/member/company_diploma_list_admin.jspx")
    public String diplomaList(HttpServletRequest request, ModelMap model, Integer pageNo,String companyId) {
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
        //检查用户是否维护了企业基本信息，如果没有则跳转到基本信息页面
        if (companyId== null) {
            return FrontUtils.showBaseMessage(request, model, "请先维护企业基本信息", "company_base_info_admin.jspx");
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        Pagination p = synergyCompanyService.getDiplomaPage(site.getSiteId(), companyId, cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        SCompany company=synergyCompanyService.findSCompanyById(companyId);
        model.addAttribute("company", company);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("companyId", companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DIPLOMA_LIST_ADMIN);
    }

    /**
     * @Author ly
     * @Date 2016/12/28 16:34
     * @Desc 企业能力新增
     **/
    @RequestMapping(value = "/member/company_diploma_add_admin.jspx")
    public String addDiploma(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             String nextUrl, Integer modelId, Integer channelId,String companyId) {
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
        SCompany company=synergyCompanyService.findSCompanyById(companyId);

        model.addAttribute("company", company);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("companyId",companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DIPLOMA_ADD_ADMIN);
    }

    /**
     * @Author ly
     * @Date 2016/12/29 9:50
     * @Desc 企业荣誉保存
     * @param request
     * @param response
     * @param nextUrl     跳转地址
     * @param diplomaType 证书分类
     * @param issuser     发证机构
     * @param operationDt 生效日期
     * @param deadlineDt  截止日期
     * @param diplomaPic  证书照片路径
     **/
    @RequestMapping(value = "/member/company_diploma_save_admin.jspx")
    public String saveDiploma(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                              String nextUrl, Integer modelId, Integer channelId, String diplomaType,
                              String issuser, Date operationDt, Date deadlineDt, String[] diplomaPic,String companyId,String diplomaDiscribe) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SCompanyDiploma diploma = new SCompanyDiploma();
        diploma.setDiplomaType(diplomaType);//证书分类
        diploma.setDiplomaDiscribe(diplomaDiscribe);//证书描述
        diploma.setDiplomaPic(diplomaPic[0]);//证书照片路径
        diploma.setIssuser(issuser);//发证机构
        diploma.setOperationDt(operationDt);//生效日期
        diploma.setDeadlineDt(deadlineDt);//截止日期
        synergyCompanyService.saveDiploma(channelId, modelId, user,companyId, diploma);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2016/12/29 15:17
     * @Desc 企业荣誉修改
     **/
    @RequestMapping(value = "/member/company_diploma_edit_admin.jspx")
    public String editDiploma(Integer id, HttpServletRequest request,
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
        SCompanyDiploma diploma = synergyCompanyService.findDiplomaById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("diploma", diploma);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DIPLOMA_EDIT_ADMIN);
    }

    /**
     * @Author ly
     * @Date 2016/12/30 8:43
     * @Desc 企业荣誉更新
     * @param request
     * @param response
     * @param nextUrl     跳转地址
     * @param diplomaType 证书分类
     * @param issuser     发证机构
     * @param operationDt 生效日期
     * @param deadlineDt  截止日期
     * @param diplomaPic  证书照片路径
     **/
    @RequestMapping(value = "/member/company_diploma_update_admin.jspx")
    public String updateDiploma(HttpServletRequest request, HttpServletResponse response,
                                ModelMap model, String nextUrl, Integer modelId, Integer channelId,
                                Integer diplomaId, String diplomaType, String issuser,
                                Date operationDt, Date deadlineDt, String[] diplomaPic) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //更新企业荣誉
        synergyCompanyService.updateDiploma(channelId, modelId, user, diplomaId, diplomaType,
                issuser, operationDt, deadlineDt, diplomaPic[0]);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2016/12/30 9:26
     * @Desc 企业荣誉删除
     **/
    @RequestMapping(value = "/member/company_diploma_delete_admin.jspx")
    public String deleteDiploma(Integer id, HttpServletRequest request, String nextUrl,
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
        //根据id进行删除
        synergyCompanyService.deleteDiplomaById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2016/12/30 10:00
     * @Desc 打开企业信息新增页面
     **/
    @RequestMapping(value = "/member/company_device_add_admin.jspx")
    public String addDevice(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                            String nextUrl, Integer modelId, Integer channelId,String companyId) {
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
        model.addAttribute("companyId", companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DEVICE_ADD_ADMIN);
    }

    /**
     * @Author ly
     * @Date 2016/12/30 10:32
     * @Desc 保存企业设备信息
     * @param request
     * @param response
     * @param model
     * @param deviceCode  设备编码
     * @param deviceName  设备名称
     * @param deviceType  设备类型
     * @param unit        单位
     * @param expectPrice 价格
     * @param nextUrl
     * @param modelId
     * @param detailDesc  详细描述
     * @param picPaths    图片集路径
     * @param picDescs    图片描述
     * @param channelId   栏目ID
     * @param charge
     **/
    @RequestMapping(value = "/member/company_device_save_admin.jspx")
    public String saveDevice(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             String deviceCode, String deviceName, String deviceType, String unit,
                             Double expectPrice, String nextUrl, Integer modelId, String detailDesc,
                             String[] picPaths, String[] picDescs, Integer channelId, Short charge,String companyId,String contact,
        String mobile) {

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
        ext.setTitle(deviceName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("企业设备");
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
        SCompanyDevice device = new SCompanyDevice();
        device.setDeviceCode(deviceCode);//设备编码
        device.setDeviceType(deviceType);//设备类型
        device.setDeviceName(deviceName);//设备名称
        device.setUnit(unit);//单位
        device.setExpectPrice(expectPrice);//价格
        device.setCreateDt(new Date(System.currentTimeMillis()));//创建时间
        device.setCreaterId(user.getUserId().toString());//创建人
        device.setUpdateDt(new Date(System.currentTimeMillis()));//更新时间
        device.setContact(contact);
        device.setMobile(mobile);
        device.setCompany(synergyCompanyService.findSCompanyById(companyId));
        c = synergyCompanyService.saveDevice(device, c, ext, t, picPaths, picDescs, channelId, typeId, user, charge, true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2016/12/30 16:57
     * @Desc 获取企业设备列表
     **/
    @RequiresPermissions("member:company_device")
    @RequestMapping("/member/company_device_list_admin.jspx")
    public String deviceList(HttpServletRequest request, ModelMap model, Integer pageNo,String companyId) {
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
        //检查用户是否维护了企业基本信息，如果没有则跳转到基本信息页面
        if (companyId== null) {
            return FrontUtils.showBaseMessage(request, model, "请先维护企业基本信息", "company_base_info_admin.jspx");
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取设备分页对象

        Pagination p = synergyCompanyService.getDevicePage(site.getSiteId(), companyId, cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("companyId", companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DEVICE_LIST_ADMIN);
    }

    /**
     * @Author zhouyq
     * @Date 2017/04/11
     * @Desc 获取企业设备明细
     **/
    @RequestMapping("/member/company_device_view.jspx")
    public String getCompanyDeviceView(Integer id, HttpServletRequest request,
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
//        SCompany company = synergyCompanyAdminService.findSCompanyById(id);
//        Set<SCompanyDevice> deviceSet = company.getDevices();
        SCompanyDevice device = synergyCompanyService.findDeviceById(id);
        SCompany company = device.getCompany();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("device", device);
        model.addAttribute("company", company);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DEVICE_VIEW);
    }

    /**
     * @Author ly
     * @Date 2016/12/30 17:03
     * @Desc 修改企业设备
     **/
    @RequestMapping(value = "/member/company_device_edit_admin.jspx")
    public String editDevice(Integer id, HttpServletRequest request,
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
        SCompanyDevice device = synergyCompanyService.findDeviceById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("device", device);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DEVICE_EDIT_ADMIN);
    }

    /**
     * @Author ly
     * @Date 2017/1/3 8:47
     * @Desc 更新企业设备信息
     * @param request
     * @param response
     * @param model
     * @param deviceCode  设备编码
     * @param deviceName  设备名称
     * @param deviceType  设备类型
     * @param unit        单位
     * @param expectPrice 价格
     * @param nextUrl
     * @param modelId
     * @param detailDesc  详细描述
     * @param picPaths    图片集路径
     * @param picDescs    图片描述
     * @param channelId   栏目ID
     * @param charge
     **/
    @RequestMapping(value = "/member/company_device_update_admin.jspx")
    public String updateDevice(Integer id, HttpServletRequest request, HttpServletResponse response,
                               ModelMap model, String deviceCode, String deviceName, String deviceType,
                               String unit, Double expectPrice, String nextUrl, Integer modelId,
                               String detailDesc, String[] picPaths, String[] picDescs,
                               Integer channelId, Short charge,String contact,
                               String mobile) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        SCompanyDevice device = new SCompanyDevice();
        device.setDeviceCode(deviceCode);//设备编码
        device.setDeviceType(deviceType);//设备类型
        device.setDeviceName(deviceName);//设备名称
        device.setUnit(unit);//单位
        device.setExpectPrice(expectPrice);//价格
        device.setContact(contact);
        device.setMobile(mobile);
        device.setUpdateDt(new Date(System.currentTimeMillis()));//更新时间
        synergyCompanyService.updateDevice(id, device, detailDesc, picPaths, picDescs, channelId,
                user, charge, true);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2017/1/3 11:41
     * @Desc 删除企业设备信息
     **/
    @RequestMapping(value = "/member/company_device_delete_admin.jspx")
    public String deleteDevice(Integer id, HttpServletRequest request, String nextUrl,
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
        //根据id删除企业版设备
        synergyCompanyService.deleteDeviceById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Date 2017/1/3 15:51
     * @Desc 企业详细信息页
     **/
    @RequiresPermissions("member:company_detail")
    @RequestMapping("/member/company_detail_info_admin.jspx")
    public String detailInfoList(HttpServletRequest request, ModelMap model,String companyId) {
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
        //检查用户是否维护了企业基本信息，如果没有则跳转到基本信息页面
        if (companyId == null) {
            return FrontUtils.showBaseMessage(request, model, "请先维护企业基本信息", "company_base_info_admin.jspx");
        }
        if(companyId!=null){
          SCompany company=synergyCompanyService.findSCompanyById(companyId);
            model.addAttribute("company", company);
        }

        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("companyId", companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DETAIL_INFO_ADMIN);
    }

    /**
     * @Author ly
     * @Date 2017/1/3 16:36
     * @Desc 企业详细信息保存
     * @param detailDesc 详细描述
     * @param picPaths   图片集路径
     * @param picDescs   图片描述
     **/
    @RequestMapping(value = "/member/company_detail_save_admin.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String detailDesc, String[] picPaths, String[] picDescs,
                       Integer modelId, Integer channelId, Short charge,String companyId) {

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
        ext.setTitle("企业详细信息");
        ext.setAuthor(user.getUsername());
        ext.setDescription("企业详细信息");
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
        c = synergyCompanyService.saveDetail(c, ext, t, picPaths, picDescs, channelId, typeId,
                user, charge, true,companyId);
        String nextUrl="company_detail_info.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author ly
     * @date 2017/1/4 13:44
     * @desc 企业详细信息更新
     * @param detailDesc 详细描述
     * @param picPaths   图片集路径
     * @param picDescs   图片描述
     **/
    @RequestMapping(value = "/member/company_detail_update_admin.jspx")
    public String updateDetail(HttpServletRequest request, HttpServletResponse response,
                               ModelMap model, Integer modelId,
                               String detailDesc, String[] picPaths, String[] picDescs,
                               Integer channelId, Short charge,String companyId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        synergyCompanyService.updateDetail(detailDesc, picPaths, picDescs, channelId, user, charge, true,companyId);
        String nextUrl="company_detail_info.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author ly
     * @date 2017/1/5 15:23
     * @desc 企业能力列表
     **/
    @RequestMapping(value = "/member/ability_list_admin.jspx")
    public String companyAbilitiesList(Date startDate, Date endDate, String releaseId,
                                       String abilityType, String abilityName,
                                       String abilityCode, Integer modelId, Integer pageNo,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model,String companyId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        WebErrors errors = WebErrors.create(request);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        //没有登录
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //检查用户是否维护了企业基本信息，如果没有则跳转到基本信息页面
        if (companyId == null) {
            return FrontUtils.showBaseMessage(request, model, "请先维护企业基本信息", "company_base_info_admin.jspx");
        }
        //非管理员or主帐号(1为主账号)不能查看
        if(!user.getIsAdmin() && !"1".equals(user.getIsMain())){
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request,response,model,errors);
            //return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        //获取分页对象
            Pagination p = synergyCreateService.getPageForCompany(companyId,
                    cpn(pageNo), 20, startDate, endDate, releaseId, abilityType,
                    abilityName, abilityCode);

            model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("releaseId", releaseId);
        model.addAttribute("abilityType", abilityType);
        model.addAttribute("abilityName", abilityName);
        model.addAttribute("abilityCode", abilityCode);
        model.addAttribute("companyId", companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, ABILITY_LIST_ADMIN_ADMIN);
    }


    /**
     * @Author 阁楼麻雀
     * @Date 2016/12/19 13:06
     * @Desc 能力发布请求
     */
    @RequiresPermissions("member:synergy_ability_add")
    @RequestMapping("/member/synergy_ability_add_admin.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      String frontStatus,ModelMap model,String companyId) {
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
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("frontStatus", frontStatus);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(103));
        model.addAttribute("companyId",companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, ABILITY_ADD);
    }


    /**
     * @Author 阁楼麻雀
     * @Date 2016/12/19 13:06
     * @Desc 能力发布保存
     */
    @RequestMapping(value = "/member/synergy_ability_save_admin.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response,ModelMap model,

                       String abilityType,
                       String abilityName,
                       String abilityCode,
                       String unit,
                       Double referPrice,
                       String detailDesc,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       String contact,
                       String mobile,
                       String statusType,

                       String nextUrl,Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge,String companyId){

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SAbility ability = new SAbility();
        ability.setAbilityType(abilityType);
        ability.setAbilityName(abilityName);
        ability.setAbilityCode(abilityCode);
        ability.setUnit(unit);
        ability.setReferPrice(referPrice);
        ability.setAddrProvince(addrProvince);
        ability.setAddrCity(addrCity);
        ability.setAddrCounty(addrCounty);
        ability.setAddrStreet(addrStreet);
        ability.setContact(contact);
        ability.setMobile(mobile);
        ability.setStatusType(statusType);
        //set默认值
        ability.setCompany(synergyCompanyService.findSCompanyById(companyId));
        /*ability.setCreaterId(user.getUserId().toString());
        ability.setOperatorId(ability.getCreaterId());*/
        ability.setCreateUser(user);
        ability.setOperatorId(user.getUserId().toString());
        ability.setCreateDt(new java.sql.Date(System.currentTimeMillis()));
        ability.setUpdateDt(ability.getCreateDt());
        ability.setDeFlag("1");

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
        ext.setTitle(abilityName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("能力发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(detailDesc);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        c = synergyCreateService.save(ability,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true);
        nextUrl = "ability_list_admin.jspx?companyId="+companyId;
            return FrontUtils.showSuccess(request, model, nextUrl);
    }
    SCompanyDevice device;
    /**
     * @return 转至企业设备询价页面
     * @author hansx
     * @Date 2017/6/2 0005 上午 11:56
     */
    @RequestMapping(value = "/member/device_inquiryprice.jspx")
    public String findRepairResById(Integer id, HttpServletRequest request,
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
            SCompanyAptitude company_aptitude = synergyCompanyService2.findAptitudeByCompanyId(user.getCompany().getCompanyId());
            if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
                String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
                return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
            }

            // 获得本站栏目列表
            Set<Channel> rights = user.getGroup().getContriChannels();
            List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
            List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
            model.addAttribute("site", site);
            model.addAttribute("channelList", channelList);
            model.addAttribute("sessionId", request.getSession().getId());
            model.addAttribute("channel",channelMng.findById(98));
            if (id != null&&!id.equals("")) {
                //根据id获取维修资源，
               device = synergyCompanyService.findDeviceById(id);


            SCompany company = device.getCompany();
                if(device!=null)
                    model.addAttribute("device", device);
                model.addAttribute("company", company);

            }
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_MEMBER, COMPANY_DEVICE_INQUIRYPRICE);
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
    @RequestMapping(value = "/member/device_inquirysave.jspx")
    public String saveInquiry(SDeviceInquiry sDeviceInquiry,String truction, HttpServletRequest request, ModelMap model,
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
        ext.setTitle(sDeviceInquiry.getInquiryTheme() + "");
        ext.setAuthor(user.getUsername());
        ext.setDescription("企业设备询价发布");
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
        sDeviceInquiry.setScompanyDevice(device);
        //登录用户的企业信息
        sDeviceInquiry.setCompany(user.getCompany());
        //创建人id
        sDeviceInquiry.setCreaterId(user.getUserId());
        //创建时间
        sDeviceInquiry.setCreateDt(new Date(System.currentTimeMillis()));
        //发布时间
        sDeviceInquiry.setReleaseDt(new Date(System.currentTimeMillis()));
        c = sRepairInquiryService.save(sDeviceInquiry, c, ext, t, channelId, typeId, user, true, attachmentPaths, attachmentNames, attachmentFilenames);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    @Resource
    private ChannelMng channelMng;
    @Resource
    private SynergyCompanyAdminService synergyCompanyService;
    @Resource
    private SynergyCompanyAdminService synergyCompanyAdminService;
    @Resource
    protected ContentMng contentMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SynergyCreateAdminService synergyCreateService;
    @Resource
    private SRepairInquiryService sRepairInquiryService;
    @Resource
    private SynergyCompanyService synergyCompanyService2;
}

