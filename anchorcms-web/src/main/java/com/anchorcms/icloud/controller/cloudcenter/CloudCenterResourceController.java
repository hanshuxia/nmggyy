package com.anchorcms.icloud.controller.cloudcenter;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.model.cloudcenter.SIcloudCenter;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterResourceService;
import com.anchorcms.icloud.service.cloudcenter.CloudMangerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_CONTENT;
import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/7 0007
 * @Desc
 */
@Controller
public class CloudCenterResourceController {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterResourceController.class);
    public static final String DEMAND_LIST = "tpl.CloudCenterSIcloudManageList";
    public static final String DEMAND_VIEW = "tpl.CloudCenterSIcloudManageView";
    public static final String DEMAND_EDIT = "tpl.CloudCenterSIcloudManageEdit";
    public static final String DEMAND_INFO = "tpl.CloudCenterSIcloudManageInfo";
    public static final String DEMAND_DETAIL = "tpl.CloudCenterSIcloudManageDetail";

    /**
     * @Author jsz
     * 查询需求列表
     * @Date 2016/1/7
     */
    @RequestMapping(value = "/member/cloudcenter_resource_manager.jspx")
    public String list(Integer UserId, Date startDt, Date endDt, Integer managerId, String resourceType,
                       String state, String addrCity, Integer modelId,
                       Integer queryChannelId, Integer pageNo, HttpServletResponse response,
                       HttpServletRequest request, ModelMap model) {
        String nextUrl = DEMAND_LIST;
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
        Pagination p = cloudCenterResourceService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(), user.getUserId(), cpn(pageNo), 20, startDt, endDt, managerId, resourceType, state, addrCity);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("startDt", startDt);
        model.addAttribute("endDt", endDt);
        model.addAttribute("managerId", managerId);
        model.addAttribute("resourceType", resourceType);
        model.addAttribute("state", state);
        model.addAttribute("addrCity", addrCity);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /***
     * @desc 云资源管理列表--删除
     * @param id 主键id
     * @param model model对象
     * @param response response对象
     * @param request request 对象
     * @author wanjinyou
     */
    @RequestMapping(value = "/member/cloudcenter_resource_manager_delete.jspx")
    public String delete(Integer id, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl = "/member/cloudcenter_resource_manager.jspx";
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //删除云资源管理实体类
        cloudCenterResourceService.deleteById(id);
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
     * @param managerId 主键id
     * @param request   request对象
     * @param response  response对象
     * @param model     model对象
     * @Author jiangshuzhong
     * @Date 2016/1/7
     * @Desc 云资源管理查看 【用户公司一条需求的明细】
     */
    @RequestMapping(value = "/member/cloudcenter_resource_manager_view.jspx")
    public String view(Integer managerId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_VIEW;
        return demandRedirective(managerId, request, response, model, nextUrl);
    }

    /**
     * @param managerId 主键id
     * @param request   request对象
     * @param response  response对象
     * @param model     model对象
     * @Author jiangshuzhong
     * @Date 2016/1/9
     * @Desc 云资源管理编辑 【用户公司一条需求的明细】
     */
    @RequestMapping(value = "/member/cloudcenter_resource_manager_edit.jspx")
    public String edit(Integer managerId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_EDIT;
        CmsUser user = CmsUtils.getUser(request);
        List<SIcloudCenter> list = cloudMangerService.getsIcloudCenter();
        model.addAttribute("user", user);
        model.addAttribute("list", list);
        return demandRedirective(managerId, request, response, model, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Date 2016/1/20
     * @param managerId 主键id
     * @param model model对象
     * @param response response对象
     * @param request request 对象
     * @Desc 云资源管理明细 【用户公司一条需求的明细】
     */
    @RequestMapping(value = "/member/cloudcenter_resource_manager_detail.jspx")
    public String detail(Integer managerId, HttpServletRequest request,
                         HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_DETAIL;
        return demandRedirective(managerId, request, response, model, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Date 2016/1/20
     * @Desc 云资源管理下架 【用户公司一条需求的明细】
     * @param id 主键id
     * @param model model对象
     * @param response response对象
     * @param request request 对象
     * @status 状态位
     */
    @RequestMapping(value = "/member/cloudcenter_resource_manager_update.jspx")
    public String out(Integer id, HttpServletRequest request, HttpServletResponse response,
                      ModelMap model, String status,
                      String nextUrl, Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //更新云资源管理表--下架
        cloudCenterResourceService.updateState(id, status, channelId, user, charge, true);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author: gao jiangning
     * @desciption  根据demandID获取demand和 content等一些参数放入model，并根据nextUrl跳转的方法
     * @Date 2017/1/5
     */
    private String demandRedirective(Integer managerId, HttpServletRequest request,
                                     HttpServletResponse response, ModelMap model, String nextUrl) {
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
        SIcloudManage sIcloudManage = cloudCenterResourceService.byManagerId(managerId);

        Content content = sIcloudManage.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("sIcloudManage", sIcloudManage);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @param managerId 主键id
     * @param request   request对象
     * @param response  response对象
     * @param model     model对象
     * @Author jiangshuzhong
     * @Date 2016/1/9
     * @Desc 云资源管理编辑更新
     */
    @RequestMapping(value = "/member/cloundCenter_manager_update.jspx")
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         Integer managerId,
                         String release_people,
                         String center_name,
                         String resourceName,//云资源名称
                         String resourceType,
                         String specVersion,
                         String parameter,
                         String rentPrice,
                         Double price,
                         String unit,
                         String addrCity,
                         String contact,//联系人
                         String mobile,//联系电话
                         String email,//邮箱
                         //cms表相关参数
                         Integer modelId, String[] attachmentPaths, String[] attachmentNames, String detailDesc,
                         String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge, String nextUrl) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //更新云资源管理
        cloudCenterResourceService.update(request, response, model, managerId, release_people,
                center_name, resourceName, resourceType, specVersion, parameter, rentPrice, price, unit, addrCity, contact, mobile, email,
                //cms表相关参数
                modelId, attachmentPaths, attachmentNames, attachmentFilenames, detailDesc, picPaths, picDescs, channelId, charge, nextUrl);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param request
     * @param response
     * @param model
     * @param managerId
     * @param attachmentPaths
     * @param attachmentNames
     * @param attachmentFilenames
     * @param picPaths
     * @param picDescs
     * @param channelId
     * @param charge
     * @param nextUrl
     * @return
     * @auter lilimin
     * @Desc 更新软件信息
     */
    @RequestMapping(value = "/member/cloudcenter_resource_manager_release.jspx")
    public String updateStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                               Integer managerId,
                               String[] attachmentPaths, String[] attachmentNames,
                               String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge, String nextUrl) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //更新软件信息
        cloudCenterResourceService.updateState(managerId);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param managerId 主键id
     * @param request   request对象
     * @param response  response对象
     * @param model     model对象
     * @Author jiangshuzhong
     * @Date 2016/1/7
     * @Desc 云资源管理查看 【用户公司一条需求的明细】
     */
    @RequestMapping(value = "/yzyjyzxYzyck/cloudcenter_resource_manager_info.jspx")
    public String info(Integer managerId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEMAND_INFO;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SIcloudManage sIcloudManage = cloudCenterResourceService.byManagerId(managerId);
        String userId = sIcloudManage.getCreaterId();
        CmsUser cmsuser = userDao.findById(Integer.parseInt(userId));
        String userName = cmsuser.getUsername();

        Content content = sIcloudManage.getContent();
        model.addAttribute("sIcloudManage", sIcloudManage);
        model.addAttribute("userName", userName);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("channel", channelMng.findById(114));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CONTENT, nextUrl);
    }

    /**
     * @Author jiangshuzhong
     * @Date 2016/1/17
     * @Desc 特色服务跳转
     */
    @RequestMapping("index_tsfw_info.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        return FrontUtils.showMessage(request, model, "该项功能正在开发中...");
    }


    /**
     * @param channelId 栏目ID
     * @param request
     * @param response
     * @param model
     * @return
     * @Author yhr
     * @Desc 批量下架
     */
    @RequestMapping(value = "/member/synergy_resource_rejectMany.jspx")
    public String rejectMany(String demandIdsStr, String nextUrl, Integer channelId, HttpServletRequest request,
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
        cloudCenterResourceService.rejectMany(demandIdsStr);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author 闫浩芮
     * @Desc 批量发布
     * @Date 2017/2/17 0017 18:44
     */
    @RequestMapping(value = "/member/synergy_resource_passMany.jspx")
    public String passMany(String demandIdsStr, String nextUrl, HttpServletRequest request,
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
        cloudCenterResourceService.passMany(demandIdsStr);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author 闫浩芮
     * @Desc 批量删除
     * @Date 2017/2/20 0020 10:39
     */
    @RequestMapping(value = "/member/synergy_resource_deleteMany.jspx")
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
        cloudCenterResourceService.deleteMany(demandIdsStr);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    private CloudCenterResourceService cloudCenterResourceService;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private CmsUserDao userDao;
    @Resource
    private CloudMangerService cloudMangerService;
}
