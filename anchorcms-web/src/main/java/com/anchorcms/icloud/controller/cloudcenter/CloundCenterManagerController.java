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
import com.anchorcms.icloud.model.cloudcenter.SIcloudCenter;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterService;
import com.anchorcms.icloud.service.cloudcenter.CloudMangerService;
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
 * @data 2017/1/5 11:10
 */
@Controller
public class CloundCenterManagerController {

    private static final Logger log = LoggerFactory.getLogger(CloudCenterCreateContriller.class);
    public static final String CLOUNDMANAGER_ADD = "tpl.cloundManager_add";

    /**
     * @Author lilimin
     * @Date 2016/12/19 13:06
     * @Desc 云资源新增
     */
    @RequiresPermissions("member:cloudcenter_manager_add.jspx")
    @RequestMapping("/member/cloudcenter_manager_add.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      ModelMap model,Integer centerId) {
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
//        List<SIcloudCenter> list = cloudMangerService.getsIcloudCenter(centerId);
        List<SIcloudCenter> list = cloudMangerService.getsIcloudCenter();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("user", user);
        model.addAttribute("centerId",centerId );
        model.addAttribute("list", list);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, CLOUNDMANAGER_ADD);
    }
/**
 * @author liuyang
 * @Date 2017/12/4 10:18
 * @return
 */
//public static final String TPL_NAME = "content_list";

//    public void execute(Environment env, Map params, TemplateModel[] loopVars,
//                        TemplateDirectiveBody body) throws TemplateException, IOException {
//        CmsSite site = FrontUtils.getSite(env);
//        Integer count = DirectiveUtils.getInt("count", params);
//        String orderBy =DirectiveUtils.getString("orderBy", params);
//        Integer listType = DirectiveUtils.getInt("type", params);
//
//        List<SIcloudCenter> list = cloudMangerService.getsIcloudCenter(count, orderBy, listType);
//        Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
//                params);
//        paramWrap.put(OUT_LIST, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(list));
//        Map<String, TemplateModel> origMap = DirectiveUtils
//                .addParamsToVariable(env, paramWrap);
//        DirectiveUtils.InvokeType type = DirectiveUtils.getInvokeType(params);
//        String listStyle = DirectiveUtils.getString(PARAM_STYLE_LIST, params);
//        if (DirectiveUtils.InvokeType.sysDefined == type) {
//            if (StringUtils.isBlank(listStyle)) {
//                throw new ParamsRequiredException(PARAM_STYLE_LIST);
//            }
//            env.include(TPL_STYLE_LIST + listStyle + TPL_SUFFIX, UTF8, true);
//        } else if (DirectiveUtils.InvokeType.userDefined == type) {
//            if (StringUtils.isBlank(listStyle)) {
//                throw new ParamsRequiredException(PARAM_STYLE_LIST);
//            }
//            FrontUtils.includeTpl(TPL_STYLE_LIST, site, env);
//        } else if (DirectiveUtils.InvokeType.custom == type) {
//            FrontUtils.includeTpl(TPL_NAME, site, params, env);
//        } else if (DirectiveUtils.InvokeType.body == type) {
//            body.render(env.getOut());
//        } else {
//            throw new RuntimeException("invoke type not handled: " + type);
//        }
//        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
//    }
    /**
     * @author: lilimin
     * @desciption 添加云资源controller
     */
    @RequestMapping(value = "/member/cloundCenter_manager_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String release_people,
                       String center_name,
                       String resourceName,
                       String resourceType,
                       String specVersion,
                       String parameter,
                       Double price,
                       String rentPrice,
                       String unit,
                       String addrCity,
                       String detailDesc,
                       String contact,//联系人
                       String mobile,//联系电话
                       String email,
                       Integer centerId,//云计算中心
                       //cms表相关参数
                       Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Short charge, String nextUrl) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SIcloudManage sd = new SIcloudManage();
        sd.setRelease_people(release_people);
        sd.setCompany(user.getCompany());
        sd.setResourceName(resourceName);
        sd.setResourceType(resourceType);
        sd.setSpecVersion(specVersion);
        sd.setParameter(parameter);
        sd.setRentPrice(rentPrice);
        sd.setPrice(price);
        sd.setUnit(unit);
        sd.setContact(contact);
        sd.setMobile(mobile);
        sd.setEmail(email);
        sd.setAddrCity(addrCity);
        sd.setReleaseDt(new Date(System.currentTimeMillis()));
        sd.setState("0");
        //默认值set
        sd.setDeFlag("1");
        SIcloudCenter bean=cloudCenterService.findById(centerId);
        sd.setsIcloudCenter(bean);
        sd.setCenter_name(bean.getCenterName());
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
        ext.setTitle(resourceName);//云资源名称
        ext.setAuthor(user.getUsername());
        ext.setDescription("云资源管理");
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
        c = cloudMangerService.save(sd, c, ext, t, attachmentPaths, attachmentNames, attachmentFilenames
                , picPaths, picDescs, channelId, typeId, user, charge, true);
        //跳转至需求管理列表
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    private CloudCenterService cloudCenterService;
    @Resource
    protected ContentMng contentMng;
    @Resource
    private CloudMangerService cloudMangerService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
}
