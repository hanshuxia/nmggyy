package com.anchorcms.icloud.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.security.encoder.PwdEncoder;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.ResponseUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.model.UnifiedUser;
import com.anchorcms.core.service.CmsUserMng;
import com.anchorcms.core.service.UnifiedUserMng;
import com.anchorcms.icloud.model.synergy.SCompanyDiploma;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;

/**
 * Created by zth on 2017/2/16.
 */
@Controller
public class SubUserController {
    public static final String Pwd_EDIT = "tpl.pwdEdit";

    @RequestMapping(value = "/common/update.jspx")
    public  void updateUser(int id, boolean isDisabled, HttpServletResponse response){
        CmsUser cmsUser = cmsUserMng.findById(id);
        cmsUser.setIsDisabled(isDisabled);
        org.json.JSONObject json = new org.json.JSONObject();
        try{
            cmsUserMng.updateUser(cmsUser);
            json.put("status", 1);
        }catch (Exception e){
            json.put("status",0);

        }
        ResponseUtils.renderJson(response, json.toString());
    }
    @RequestMapping(value = "/common/pwd_edit.jspx")
    public String editPwd(Integer id, HttpServletRequest request,
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
        UnifiedUser unifiedUser= unifiedUserMng.findById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("unifiedUser", unifiedUser);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, Pwd_EDIT);
    }
    @RequestMapping(value = "/common/update_pwd.jspx")
    public String updatePwd(int id, String pwd, HttpServletRequest request, HttpServletResponse response, ModelMap model, String nextUrl){
        UnifiedUser unUser = unifiedUserMng.findById(id);
        unUser.setPassword(pwdEncoder.encodePassword(pwd));
         unifiedUserMng.updateUser(unUser);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    private PwdEncoder pwdEncoder;

    @Resource
    private CmsUserMng cmsUserMng;

    @Resource
    private UnifiedUserMng unifiedUserMng;
    @Resource
    private ChannelMng channelMng;

}
