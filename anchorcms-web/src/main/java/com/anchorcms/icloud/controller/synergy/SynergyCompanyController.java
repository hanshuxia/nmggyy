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
import com.anchorcms.icloud.service.synergy.*;
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Created by ly on 2016/12/26.
 * @desc 企业信息管理类
 */

@Controller
public class SynergyCompanyController {
    private static final Logger log = LoggerFactory.getLogger(SynergyCompanyController.class);
    public static final String COMPANY_INFO = "tpl.companyInfo";
    public static final String COMPANY_BASEINFO = "tpl.companyBaseInfo";
    public static final String COMPANY_DIPLOMA_LIST = "tpl.companyDiplomaList";
    public static final String COMPANY_DIPLOMA_ADD = "tpl.companyDiplomaAdd";
    public static final String COMPANY_DIPLOMA_EDIT = "tpl.companyDiplomaEdit";
    public static final String COMPANY_DEVICE_LIST = "tpl.companyDeviceList";
    public static final String COMPANY_DEVICE_ADD = "tpl.companyDeviceAdd";
    public static final String COMPANY_DEVICE_EDIT = "tpl.companyDeviceEdit";
    public static final String COMPANY_DETAIL_INFO = "tpl.companyDetailInfo";
    public static final String ABILITY_LIST = "tpl.synergyAbilityList";
    public static final String COMPANY_PREVIEW = "tpl.companyView";
    public static final String COMPANY_APTITUDE_INFO = "tpl.companyAptitudeInfo";
    public static final String COMPANY_APTITUDE_ADD = "tpl.companyAptitudeAdd";
    public static final String COMPANY_APTITUDE_EDIT = "tpl.companyAptitudeEdit";


    /**
     * @Author zth
     * @Date 2017/2/20 16:44
     * @Desc 打开企业信息查看页面
     **/
    @RequestMapping(value = "/member/company_view.jspx")
    public String view_Company(String id, HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) {
        String nextUrl = COMPANY_PREVIEW;
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        SCompany company = createService.byCompanyId(id);
        Set<SAbility> list = company.getAbilities();
        Content content = null;
        if((company.getAbilities()).size()>0){
            Iterator<SAbility> it  = list.iterator();
            while(it.hasNext()){
                content= it.next().getContent();
            }
        }
        // Content content = company.getAbilities();
        // 获得本站栏目列表
        model.addAttribute("company",company);
        model.addAttribute("channel",channelMng.findById(105));
        model.addAttribute("site", site);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(103));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }


    /**
     * @Author ly
     * @Date 2016/12/26 15:50
     * @Desc 打开企业信息维护页面
     **/
    @RequiresPermissions("member:company_edit")
    @RequestMapping("/member/company_edit.jspx")
    public String companyInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_INFO);
    }

    /**
     * @Author ly
     * @Date 2016/12/26 15:50
     * @Desc 打开企业基本信息
     **/
    @RequiresPermissions("member:company_base_info")
    @RequestMapping("/member/company_base_info.jspx")
    public String info(HttpServletRequest request, ModelMap model) {
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
        Set rights = user.getGroup().getContriChannels();
        List topList = channelMng.getTopList(site.getSiteId(), true);
        List channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_BASEINFO);
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
    @RequestMapping(value = "/member/company_baseinfo_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String companyCode, String companyName, String companyType, String serverType,String industryType,String operateType,
                       String companyScale, String mainProduct, Integer deviceCount, Integer productCount,
                       String mobile,String sites,String email, String addrProvince,String addrCity,String addrCounty,String addrStreet, Date registerDt,  Integer modelId,
                       Integer channelId, Short charge,String postCode) {

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        String nextUrl = "company_base_info.jspx";
        /**
         *  验证企业名称是否已存在系统中，如果存在提示不能重复。
         *  @author: hansx
         *  @date 2017/11/30 11:03
         */
        List<SCompany>  slist = synergyCompanyService.findSCompanyByName(companyName);
        Boolean flag = false;
        for(SCompany sCompany:slist) {
            if ((sCompany != null && user.getCompany() == null) || (sCompany != null && user.getCompany() != null && !user.getCompany().getCompanyId().equals(sCompany.getCompanyId()))) {
                flag = true;
                break;
            }
        }
        if(flag) {
            return FrontUtils.showBaseMessage(request, model, "您好，您的企业信息已被注册，详情请咨询客服电话4008576688处理!", nextUrl);
        }
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
        company.setIsRecommend("0");
        company.setPostCode(postCode);
        synergyCompanyService.save(company, channelId, modelId, user, charge, true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2016/12/28 10:51
     * @Desc 企业荣誉列表
     **/
    @RequiresPermissions("member:company_diploma")
    @RequestMapping("/member/company_diploma_list.jspx")
    public String diplomaList(HttpServletRequest request, ModelMap model, Integer pageNo) {
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
        if (user.getCompany() == null) {
            return FrontUtils.showBaseMessage(request, model, "请先维护企业基本信息", "company_base_info.jspx");
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        Pagination p = synergyCompanyService.getDiplomaPage(site.getSiteId(), user, cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DIPLOMA_LIST);
    }

    /**
     * @Author ly
     * @Date 2016/12/28 16:34
     * @Desc 企业能力新增
     **/
    @RequestMapping(value = "/member/company_diploma_add.jspx")
    public String addDiploma(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             String nextUrl, Integer modelId, Integer channelId) {
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DIPLOMA_ADD);
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
    @RequestMapping(value = "/member/company_diploma_save.jspx")
    public String saveDiploma(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                              String nextUrl, Integer modelId, Integer channelId, String diplomaType,
                              String issuser, Date operationDt, Date deadlineDt, String[] diplomaPic) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SCompanyDiploma diploma = new SCompanyDiploma();
        diploma.setDiplomaType(diplomaType);//证书分类
        diploma.setDiplomaPic(diplomaPic[0]);//证书照片路径
        diploma.setIssuser(issuser);//发证机构
        diploma.setOperationDt(operationDt);//生效日期
        diploma.setDeadlineDt(deadlineDt);//截止日期
        synergyCompanyService.saveDiploma(channelId, modelId, user, diploma);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2016/12/29 15:17
     * @Desc 企业荣誉修改
     **/
    @RequestMapping(value = "/member/company_diploma_edit.jspx")
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
                TPLDIR_MEMBER, COMPANY_DIPLOMA_EDIT);
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
    @RequestMapping(value = "/member/company_diploma_update.jspx")
    public String updateDiploma(HttpServletRequest request, HttpServletResponse response,
                                ModelMap model, String nextUrl, Integer modelId, Integer channelId,
                                Integer diplomaId, String diplomaType, String issuser,
                                Date operationDt, Date deadlineDt, String[] diplomaPic,String diplomaDiscribe) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //更新企业荣誉
        synergyCompanyService.updateDiploma(channelId, modelId, user, diplomaId, diplomaType,
                issuser, operationDt, deadlineDt, diplomaPic[0],diplomaDiscribe);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2016/12/30 9:26
     * @Desc 企业荣誉删除
     **/
    @RequestMapping(value = "/member/company_diploma_delete.jspx")
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
    @RequestMapping(value = "/member/company_device_add.jspx")
    public String addDevice(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                            String nextUrl, Integer modelId, Integer channelId) {
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DEVICE_ADD);
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
    @RequestMapping(value = "/member/company_device_save.jspx")
    public String saveDevice(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             String deviceCode, String deviceName, String deviceType, String unit,
                             Double expectPrice, String nextUrl, Integer modelId, String detailDesc,
                             String[] picPaths, String[] picDescs, Integer channelId, Short charge,String contact,
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
//        device.setCompanyId(user.getCompany().getCompanyId());
        device.setContact(contact);
        device.setMobile(mobile);
        device.setCompany(synergyCompanyAdminService.findSCompanyById(user.getCompany().getCompanyId()));
        c = synergyCompanyService.saveDevice(device, c, ext, t, picPaths, picDescs, channelId, typeId, user, charge, true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2016/12/30 16:57
     * @Desc 获取企业设备列表
     **/
    @RequiresPermissions("member:company_device")
    @RequestMapping("/member/company_device_list.jspx")
    public String deviceList(HttpServletRequest request, ModelMap model, Integer pageNo) {
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
        if (user.getCompany() == null) {
            return FrontUtils.showBaseMessage(request, model, "请先维护企业基本信息", "company_base_info.jspx");
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取设备分页对象
        Pagination p = synergyCompanyService.getDevicePage(site.getSiteId(), user, cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DEVICE_LIST);
    }

    /**
     * @Author ly
     * @Date 2016/12/30 17:03
     * @Desc 修改企业设备
     **/
    @RequestMapping(value = "/member/company_device_edit.jspx")
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
                TPLDIR_MEMBER, COMPANY_DEVICE_EDIT);
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
    @RequestMapping(value = "/member/company_device_update.jspx")
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
    @RequestMapping(value = "/member/company_device_delete.jspx")
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
    @RequestMapping("/member/company_detail_info.jspx")
    public String detailInfoList(HttpServletRequest request, ModelMap model) {
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
        if (user.getCompany() == null) {
            return FrontUtils.showBaseMessage(request, model, "请先维护企业基本信息", "company_base_info.jspx");
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_DETAIL_INFO);
    }

    /**
     * @Author ly
     * @Date 2017/1/3 16:36
     * @Desc 企业详细信息保存
     * @param detailDesc 详细描述
     * @param picPaths   图片集路径
     * @param picDescs   图片描述
     **/
    @RequestMapping(value = "/member/company_detail_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String detailDesc, String[] picPaths, String[] picDescs,
                       Integer modelId, Integer channelId, Short charge) {

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
                user, charge, true);
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
    @RequestMapping(value = "/member/company_detail_update.jspx")
    public String updateDetail(HttpServletRequest request, HttpServletResponse response,
                               ModelMap model, Integer modelId,
                               String detailDesc, String[] picPaths, String[] picDescs,
                               Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        synergyCompanyService.updateDetail(detailDesc, picPaths, picDescs, channelId, user, charge, true);
        String nextUrl="company_detail_info.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author ly
     * @date 2017/1/5 15:23
     * @desc 企业能力列表
     **/
    @RequestMapping(value = "/member/ability_list.jspx")
    public String companyAbilitiesList(Date startDate, Date endDate, String releaseId,
                                       String abilityType, String abilityName,
                                       String abilityCode, Integer modelId, Integer pageNo,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
        if (user.getCompany() == null) {
            return FrontUtils.showBaseMessage(request, model, "请先维护企业基本信息", "company_base_info.jspx");
        }
        //非管理员or主帐号(1为主账号)不能查看
        if(!user.getIsAdmin() && !"1".equals(user.getIsMain())){
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request,response,model,errors);
            //return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        //获取分页对象
        Pagination p = synergyCreateService.getPageForCompany(user.getCompany().getCompanyId(),
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, ABILITY_LIST);
    }

    /**
     * @Author zhouyq
     * @Date 2017/5/10
     * @Desc 企业资质信息页
     **/
    @RequiresPermissions("member:company_aptitude_info")
    @RequestMapping("/member/company_aptitude_info.jspx")
    public String aptitudeInfoList(HttpServletRequest request, ModelMap model) {
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
        if (user.getCompany() == null) {
            return FrontUtils.showBaseMessage(request, model, "请先维护企业基本信息", "company_base_info.jspx");
        }
        String nextUrl = "";
        if (user.getCompany().getAptitude().size() == 0) {
            nextUrl = COMPANY_APTITUDE_ADD;
        } else {
            nextUrl = COMPANY_APTITUDE_EDIT;
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @Author zhouyq
     * @Date 2017/5/10
     * @Desc 打开企业资质新增页面
     **/
    @RequestMapping(value = "/member/company_aptitude_add.jspx")
    public String addAptitude(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                            String nextUrl, Integer modelId, Integer channelId) {
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_APTITUDE_ADD);
    }

    /**
     * @Author zhouyq
     * @Date 2017/5/12
     * @Desc 打开企业资质修改页面
     **/
    @RequestMapping(value = "/member/company_aptitude_edit.jspx")
    public String editAptitude(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                              String nextUrl, Integer modelId, Integer channelId) {
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
        String companyId = user.getCompany().getCompanyId();
        SCompany company = sCompanyManagementService.byCompanyId(companyId);
        model.addAttribute("company", company);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_APTITUDE_EDIT);
    }

    /**
     * @Author zhouyq
     * @Date 2017/05/10
     * @Desc 保存企业资质信息
     * @param request
     * @param response
     * @param model
     **/
    @RequestMapping(value = "/member/company_aptitude_save.jspx")
    public String saveAptitude(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                               String isThreeToOne, String businessRegNo, String businessRegNo1, String orgInstitutionCode, String taxRegNo,
                               String legRepresentName, String legRepresentIdNO, String legRepresentPhoneNO, String applyContact,
                               String applyContactPhoneNO, String nextUrl, Integer modelId,
                               String[] picPaths, String[] picDescs, String[] picPaths1, String[] picDescs1,
                               String[] picPaths2, String[] picDescs2, String[] picPaths3, String[] picDescs3,
                               String[] picPaths4, String[] picDescs4, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String companyId = user.getCompany().getCompanyId();
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
        ext.setTitle("企业资质");
        ext.setAuthor(user.getUsername());
        ext.setDescription("企业资质");
        ContentTxt t = new ContentTxt();
        t.setTxt("企业资质");
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        if (("1").equals(isThreeToOne)) { // 三证合一
            businessRegNo = businessRegNo1;
            orgInstitutionCode = null;
            taxRegNo = null;
            picPaths = null;
            picPaths1 = null;
        } else if (("0").equals(isThreeToOne)) { // 非三证合一
            businessRegNo = businessRegNo;
        }
        SCompanyAptitude sCompanyAptitude = synergyCompanyService.findAptitudeByCompanyId(companyId);
        if (sCompanyAptitude == null) {
            sCompanyAptitude = new SCompanyAptitude();
        }
        if (picPaths != null) {
            sCompanyAptitude.setParam(picPaths[0]);
        }
        if (picPaths1 != null) {
            sCompanyAptitude.setParam1(picPaths1[0]);
        }
        sCompanyAptitude.setStatus("1"); // 待审批状态
        sCompanyAptitude.setIsThreeToOne(isThreeToOne); // 是否三证合一
        sCompanyAptitude.setBusinessRegNo(businessRegNo); // 工商注册号
        sCompanyAptitude.setOrgInstitutionCode(orgInstitutionCode); // 组织机构代码
        sCompanyAptitude.setTaxRegNo(taxRegNo); // 纳税人识别号
        sCompanyAptitude.setLegRepresentName(legRepresentName); // 法人代表姓名
        sCompanyAptitude.setLegRepresentIdNO(legRepresentIdNO); // 法人身份证号
        sCompanyAptitude.setLegRepresentPhoneNO(legRepresentPhoneNO); // 法人手机号
        sCompanyAptitude.setApplyContact(applyContact); // 申请联系人
        sCompanyAptitude.setApplyContactPhoneNO(applyContactPhoneNO); // 联系人手机号
        sCompanyAptitude.setCreateDt(new Date(System.currentTimeMillis())); // 创建时间
        sCompanyAptitude.setCreaterId(user.getUserId().toString()); // 创建人
        sCompanyAptitude.setUpdateDt(new Date(System.currentTimeMillis())); // 更新时间
        sCompanyAptitude.setParam2(picPaths2[0]);
        sCompanyAptitude.setParam3(picPaths3[0]);
        sCompanyAptitude.setParam4(picPaths4[0]);
        sCompanyAptitude.setCompany(user.getCompany());
        c = synergyCompanyService.saveAptitude(sCompanyAptitude, c, ext, t, picPaths, picDescs, picPaths1, picDescs1,
                picPaths2, picDescs2, picPaths3, picDescs3,picPaths4, picDescs4, channelId, typeId, user, charge, true);
        nextUrl = "/member/company_aptitude_edit.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    private ChannelMng channelMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;
    @Resource
    private SynergyCompanyAdminService synergyCompanyAdminService;
    @Resource
    protected ContentMng contentMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SynergyCreateService synergyCreateService;
    @Resource
    private SDemandService createService;
    @Resource
    private SCompanyManagementService sCompanyManagementService;

}

