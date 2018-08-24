package com.anchorcms.icloud.controller.cloudcenter;

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
import com.anchorcms.icloud.model.cloudcenter.SIcloudCenter;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterService;
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
 * @anther 李利民
 * @descript
 * @data 2017/1/49:57
 */
@Controller
public class CloudComputingCenter {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterCreateContriller.class);
    public static final String CLOUNDCEMTER_ADD = "tpl.cloundComputerCener_add";
    public static final String MANAGE_LIST = "tpl.cloudCenter_manage";
    public static final String CENTER_EDIT = "tpl.cloudCenter_edit";
    public static final String CENTER_VIEW = "tpl.cloudCenter_view";

    /**
     * @Author lilimin
     * @Date 2016/12/19 13:06
     * @Desc 云需求发布请求
     */
    @RequiresPermissions("member:cloudcenter_center_add")
    @RequestMapping("/member/cloudcenter_center_add.jspx")
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, CLOUNDCEMTER_ADD);
    }

    /**
     * @author: lilimin
     * @desciption 添加新计算机中心的业务controller
     */
    @RequestMapping(value = "/member/cloundCenter_center_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String centerName,
                       String contact,
                       String mobile,
                       String email,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       String selectAddress,
                       String acreage,
                       //cms表相关参数
                       String editor,
                       //String nextUrl,
                       Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SIcloudCenter sd = new SIcloudCenter();
        sd.setCenterName(centerName);
        sd.setAddrProvince(addrProvince);
        sd.setAddrCity(addrCity);
        sd.setAddrCounty(addrCounty);
        sd.setAddrStreet(addrStreet);
        sd.setContact(contact);
        sd.setMobile(mobile);
        sd.setEmail(email);
        sd.setSelectAddress(selectAddress);
        sd.setAcreage(acreage);
        //默认值set
        sd.setDeFlag("1");
        sd.setCreateDt(new Date(System.currentTimeMillis()));
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
        ext.setTitle(centerName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("云计算中心");
        ContentTxt t = new ContentTxt();
        t.setTxt(editor);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        c = cloudCenterService.save(sd, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs, channelId, typeId, user, charge, true);
        //跳转至云计算中心管理列表
        String nextUrl = "/member/cloudcenter_manage_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /***
     * @author wanjinyou
     * @param centerId 主键id
     * @param centerName 云计算中心名称
     * @param addrCity 市
     * @param startDate 添加时间的开始时间
     * @param endDate   添加时间的结束时间
     * @param modelId  modelId
     * @param queryChannelId 栏目id
     * @param pageNo 页码
     * @param request request对象
     * @param response response对象
     * @param model model
     * @desc  云计算中心列表请求
     * @return
     */
    @RequestMapping(value = "/member/cloudcenter_manage_list.jspx")
    public String list(Integer centerId, String centerName, String addrCity, java.util.Date startDate, java.util.Date endDate, Integer modelId, Integer queryChannelId,
                       Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String nextUrl = MANAGE_LIST;
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
        //非管理员 无权查看
        if (!user.getIsAdmin()) {
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request, response, model, errors);
        }
        Pagination p = cloudCenterService.getPageForCenter(queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20, centerId, centerName, addrCity, startDate, endDate);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("centerId", centerId);
        model.addAttribute("centerName", centerName);
        model.addAttribute("addrCity", addrCity);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /***
     * @author wanjinyou
     * @param id 主键id
     * @param model model对象
     * @param response response对象
     * @param request request 对象
     * @desc 云计算中心列表--编辑
     * @return
     */
    @RequestMapping(value = "/member/cloudcenter_manage_edit.jspx")
    public String edit(Integer id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        String nextUrl = CENTER_EDIT;
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
        SIcloudCenter icloudCenter = cloudCenterService.findById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("icloudCenter", icloudCenter);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /***
     *  @author wanjinyou
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @param centerName 云计算中心名称
     * @param contact  联系人
     * @param mobile 联系电话
     * @param email 邮箱
     * @param addrProvince 省
     * @param addrCity 市
     * @param addrCounty 区
     * @param addrStreet 街道
     * @param detailDesc 详细描述
     * @param channelId 栏目id
     * @param charge charge
     * @desc 云计算中心列表--编辑--更新
     * @return
     */
    @RequestMapping(value = "/member/cloudcenter_center_update.jspx")
    public String update(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model,   String selectAddress, String acreage,
                         String centerName, String contact, String mobile, String email, String addrProvince, String addrCity, String addrCounty, String addrStreet, String detailDesc,
                         //cms表相关参数
                         Integer channelId, Short charge, String[] picPaths, String[] picDescs) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SIcloudCenter sd = new SIcloudCenter();
        sd.setCenterName(centerName);//云计算中心名称
        sd.setContact(contact);//联系人
        sd.setMobile(mobile);//联系电话
        sd.setEmail(email);//邮箱
        sd.setAddrProvince(addrProvince);
        sd.setAddrCity(addrCity);
        sd.setAddrCounty(addrCounty);
        sd.setAddrStreet(addrStreet); //地区
        sd.setSelectAddress(selectAddress);//项目选址
        sd.setAcreage(acreage);//面积
        //默认值set
        sd.setCreateDt(new Date(System.currentTimeMillis()));
        sd.setCreaterId(user.getUserId() + "");

        //前往service层保存业务
        cloudCenterService.update(id, sd, detailDesc, channelId, user, charge, true,picPaths,picDescs);
        //跳转至云计算中心管理列表
        String nextUrl = "/member/cloudcenter_manage_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /***
     * @desc 云计算中心列表--明细
     * @param id 主键id
     * @param model model对象
     * @param response response对象
     * @param request request 对象
     * @author wanjinyou
     */
    @RequestMapping(value = "/member/cloudcenter_manage_view.jspx")
    public String view(Integer id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        String nextUrl = CENTER_VIEW;
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
        SIcloudCenter icloudCenter = cloudCenterService.findById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("icloudCenter", icloudCenter);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /***
     * @desc 云计算中心列表--删除
     * @param id 主键id
     * @param model model对象
     * @param response response对象
     * @param request request 对象
     * @author wanjinyou
     */
    @RequestMapping(value = "/member/cloudcenter_manage_delete.jspx")
    public String delete(Integer id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl = "cloudcenter_manage_list.jspx";
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //删除云计算中心实体类
        cloudCenterService.deleteById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/20 0020 10:39
     */
    @RequestMapping(value = "/member/cloudcenter_manage_deleteMany.jspx")
    public String deleteMany(String demandIdsStr, String nextUrl, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        //业务数据更新处理
        cloudCenterService.deleteMany(demandIdsStr);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private CloudCenterService cloudCenterService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
}
