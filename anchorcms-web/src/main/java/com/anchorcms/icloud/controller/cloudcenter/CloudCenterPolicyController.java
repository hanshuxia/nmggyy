package com.anchorcms.icloud.controller.cloudcenter;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.model.cloudcenter.SIcloudPolicy;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPolicyService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_CONTENT;
import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

@Controller
public class CloudCenterPolicyController {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterPolicyController.class);
    public static final String POLICY_LIST = "tpl.cloudCenterPolicyList";
    public static final String POLICY_VIEW = "tpl.cloudCenterPolicyView";
    public static final String POLICY_PREVIEW = "tpl.cloudCenterPolicyPreview";
    public static final String POLICY_EDIT = "tpl.cloudCenterPolicyEdit";
    public static final String CLOUNDPOLICE_ADD = "tpl.cloundCenerPolice_add";
    public static final String POLICY_INFO = "tpl.cloudCenterPolicyInfo";

    /**
     * @Author lilimin
     * @Date 2016/12/19 13:06
     * @Desc 扶持政策新增
     */
    @RequiresPermissions("member:cloudcenter_policy_add")
    @RequestMapping("/member/cloudcenter_policy_add.jspx")
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
                TPLDIR_MEMBER, CLOUNDPOLICE_ADD);
    }

    /**
     * @author: lilimin
     * @desciption 扶持政策保存controller
     */
    @RequestMapping(value = "/member/cloundCenter_policy_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String policyName,
                       String postCode,//发文单位
                       Date releaseDt,
                       Date endApplyDt,//申请结束时间
                       String policyLevel,
                       String contact,
                       String mobile,
                       String telephone,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       String isSupport,
                       String state,
                       //cms表相关参数
                       String policyNumber,
                       String editor,
                       Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());//获取当前时间
        SIcloudPolicy sd = new SIcloudPolicy();
        sd.setPolicyName(policyName);
        sd.setPostCode(postCode);
        sd.setReleaseDt(releaseDt);
        sd.setPolicyLevel(policyLevel);
        sd.setAddrProvince(addrProvince);
        sd.setAddrCity(addrCity);
        sd.setAddrCounty(addrCounty);
        sd.setAddrStreet(addrStreet);
        sd.setContact(contact);
        sd.setMobile(mobile);
        sd.setUser(user);
        sd.setPhone(telephone);
        if (endApplyDt == null) { // 非扶植政策
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dstr="2021-12-31";
            try {
                java.util.Date dateDate = sdf.parse(dstr);
                endApplyDt = new java.sql.Date(dateDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        sd.setEndApplyDt(endApplyDt);
        sd.setIsSupport(isSupport);
        sd.setPolicyNumber(policyNumber);
        sd.setState(state);
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
        ext.setTitle(policyName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("云计算政策申请");
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
        c = CloudCenterPolicyService.save(sd, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs, channelId, typeId, user, charge, true);
        //跳转至需求管理列表
        String nextUrl = "/member/cloudcenter_policy_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author maocheng
     * 云计算政策管理-列表界面
     * @Date 2017/1/4 0004 18:46
     */

    @RequestMapping(value = "/member/cloudcenter_policy_list.jspx")
    public String list(String policyName, Date startDate, Date endDate, String policyLevel, Integer modelId, Integer queryChannelId, Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model, String state, Integer policyId) {
        String nextUrl = POLICY_LIST;
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
        Pagination p = CloudCenterPolicyService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20, state, policyName, startDate, endDate, policyLevel);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("state", state);
        model.addAttribute("policyName", policyName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("policyLevel", policyLevel);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @param policyId 主键id
     * @param request  request对象
     * @param response response对象
     * @param model    model对象
     * @Author maocheng
     * @Date 2017/1/5
     * @Desc 政策明细查看
     */
    @RequestMapping(value = "/member/cloudcenter_policy_view.jspx")
    public String view(Integer policyId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = POLICY_VIEW;
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
        WebErrors errors = validateEdit(policyId, site, user, request);
        if (errors.hasErrors()) {
            return FrontUtils.showError(request, response, model, errors);
        }
        SIcloudPolicy policy = CloudCenterPolicyService.byPolicyId(policyId);
        Content content = policy.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("policy", policy);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    private WebErrors validateEdit(Integer policyId, CmsSite site, CmsUser user,
                                   HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldOpt(errors, site, user, new Integer[]{policyId})) {
            return errors;
        }
        return errors;
    }

    private boolean vldOpt(WebErrors errors, CmsSite site, CmsUser user,
                           Integer[] policyIds) {
        for (Integer policyId : policyIds) {
            if (errors.ifNull(policyId, "id")) {
                return true;
            }
            SIcloudPolicy policy = CloudCenterPolicyService.byPolicyId(policyId);
            Content c = contentMng.findById(policy.getContent().getContentId());
            // 数据不存在
            if (errors.ifNotExist(c, Content.class, policyId)) {
                return true;
            }
            // 非本用户数据
            if (!c.getUser().getUserId().equals(user.getUserId())) {
                errors.noPermission(Content.class, policyId);
                return true;
            }
            // 非本站点数据
            if (!c.getSite().getSiteId().equals(site.getSiteId())) {
                errors.notInSite(Content.class, policyId);
                return true;
            }
            // 文章级别大于0，不允许修改
            if (c.getCheckStep() > 0) {
                errors.addErrorCode("member.contentChecked");
                return true;
            }
        }
        return false;
    }

    /**
     * @param policyId 主键id
     * @param request  request对象
     * @param response response对象
     * @param model    model对象
     * @Author maocheng
     * @Date 2016/1/7
     * @Desc 政策预览
     */
    @RequestMapping(value = "/member/cloudcenter_policy_preview.jspx")
    public String preview(Integer policyId, HttpServletRequest request,
                          HttpServletResponse response, ModelMap model) {
        String nextUrl = POLICY_PREVIEW;
        return policyRedirective(policyId, request, response, model, nextUrl);
    }

    /**
     * @author: gao jiangning
     * @desciption  根据demandID获取demand和 content等一些参数放入model，并根据nextUrl跳转的方法
     * @Date 2017/1/5
     */
    private String policyRedirective(Integer policyId, HttpServletRequest request,
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
        SIcloudPolicy sIcloudPolicy = CloudCenterPolicyService.byPolicyId(policyId);
        Content content = sIcloudPolicy.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("sIcloudPolicy", sIcloudPolicy);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @param policyId  政策id
     * @param request
     * @param response
     * @param model
     * @author maocheng
     * @date 2017/1/7 16:21
     * @desc 云计算政策编辑页面
     **/
    @RequestMapping(value = "/member/cloudcenter_policy_edit.jspx")
    public String edit(Integer policyId, HttpServletRequest request, String nextUrl,
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
        WebErrors errors = validateEdit(policyId, site, user, request);
        if (errors.hasErrors()) {
            return FrontUtils.showError(request, response, model, errors);
        }
        SIcloudPolicy policy = CloudCenterPolicyService.byPolicyId(policyId);
        Content content = policy.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("policy", policy);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, POLICY_EDIT);
    }

    /**
     * @param policyId  政策id
     * @param request
     * @param response
     * @param model
     * @param modelId
     * @param channelId
     * @author maocheng
     * @date 2017/1/7 16:21
     * @desc 云计算政策编辑
     **/
    @RequestMapping(value = "/member/cloundcenter_policy_update.jspx")
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         String policyName,
                         Date releaseDt,
                         Date endApplyDt, //申请结束时间
                         String policyLevel,
                         String policyNumber,
                         String contact,
                         String mobile,
                         String addrProvince,
                         String addrCity,
                         String addrCounty,
                         String addrStreet,
                         String isSupport,
                         String postCode,
                         //cms表相关参数
                         String editor,
                         Integer policyId,//
                         Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                         String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());//获取当前时间
        SIcloudPolicy sp = CloudCenterPolicyService.byPolicyId(policyId);
        sp.setPolicyName(policyName);
        sp.setPolicyLevel(policyLevel);
        sp.setPolicyNumber(policyNumber);
        sp.setAddrProvince(addrProvince);
        sp.setAddrCity(addrCity);
        sp.setAddrCounty(addrCounty);
        sp.setAddrStreet(addrStreet);
        sp.setContact(contact);
        sp.setPostCode(postCode);
        sp.setMobile(mobile);
        sp.setUser(user);
        sp.setIsSupport(isSupport);
        sp.setReleaseDt(releaseDt);
        if (endApplyDt == null) { // 非扶植政策
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dstr="2021-12-31";
            try {
                java.util.Date dateDate = sdf.parse(dstr);
                endApplyDt = new java.sql.Date(dateDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        sp.setEndApplyDt(endApplyDt);
        sp.setState("0");
        //默认值set
        sp.setDeFlag("1");
        sp.setCreateDt(new Date(System.currentTimeMillis()));
        CloudCenterPolicyService.update(policyId, sp, editor, attachmentPaths, attachmentNames,
                attachmentFilenames, picPaths, picDescs, channelId, user, charge, true);

        String nextUrl = "/member/cloudcenter_policy_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param policyId  政策id
     * @param request
     * @param response
     * @param model
     * @param modelId
     * @param channelId
     * @author maocheng
     * @date 2017/1/7 16:21
     * @desc 云计算政策管理
     **/
    @RequestMapping(value = "/member/cloudcenter_policy_manage.jspx")
    public String manage(Integer policyId, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, String state,
                         String nextUrl, Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //更新申请状态
        CloudCenterPolicyService.updateState(policyId, state, channelId, user, charge, true);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    /**
     * @param policyId  政策id
     * @param request
     * @param response
     * @param model
     * @param modelId
     * @param channelId
     * @author maocheng
     * @date 2017/1/7 16:21
     * @desc 删除政策
     **/
    @RequestMapping(value = "/member/cloudcenter_policy_delete.jspx")
    public String delete(Integer policyId, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, Integer modelId, Integer channelId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl = "cloudcenter_policy_list.jspx";
        FrontUtils.frontData(request, model, site);
        //更新申请状态
        CloudCenterPolicyService.delete(policyId);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param policyId 主键id
     * @param request  request对象
     * @param response response对象
     * @param model    model对象
     * @Author jsz
     * @Date 2016/1/13
     * @Desc 前台政策预览
     */
    @RequestMapping(value = "/yzyjyzxYjszc/cloudcenter_policy_info.jspx")
    public String info(Integer policyId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = POLICY_INFO;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        SIcloudPolicy sIcloudPolicy = CloudCenterPolicyService.byPolicyId(policyId);
        Content content = sIcloudPolicy.getContent();
        String userId = sIcloudPolicy.getCreaterId();
        CmsUser cmsuser = userDao.findById(Integer.parseInt(userId));
        String userName = cmsuser.getUsername();

        model.addAttribute("sIcloudPolicy", sIcloudPolicy);
        model.addAttribute("userName", userName);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("channel", channelMng.findById(112));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CONTENT, nextUrl);
    }

    /**
     * 批量撤回
     *
     * @param channelId 栏目ID
     * @param request
     * @param response
     * @param model
     * @return
     * @Author yhr
     */
    @RequestMapping(value = "/member/cloudcenter_policy_rejectMany.jspx")
    public String rejectMany(String demandIdsStr, String nextUrl, Integer channelId, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        String statusType = "3";
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
        CloudCenterPolicyService.rejectMany(demandIdsStr);
        model.addAttribute("statusType", statusType);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author 闫浩芮
     * 批量发布
     * @Date 2017/2/17 0017 18:44
     */
    @RequestMapping(value = "/member/cloudcenter_policy_passMany.jspx")
    public String passMany(String demandIdsStr, String nextUrl, HttpServletRequest request,
                           HttpServletResponse response, ModelMap model) {
        String statusType = "2";
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
        CloudCenterPolicyService.passMany(demandIdsStr);
        model.addAttribute("statusType", statusType);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/20 0020 10:39
     */
    @RequestMapping(value = "/member/cloudcenter_policy_deleteMany.jspx")
    public String deleteMany(String demandIdsStr, String nextUrl, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        String statusType = "2";
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
        CloudCenterPolicyService.deleteMany(demandIdsStr);
        model.addAttribute("statusType", statusType);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    private CloudCenterPolicyService CloudCenterPolicyService;
    @Resource
    protected ContentMng contentMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private CmsUserDao userDao;
}
