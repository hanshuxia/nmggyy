package com.anchorcms.icloud.controller.commservice;

import com.alibaba.fastjson.JSON;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.controller.cloudcenter.CloudCenterCreateContriller;
import com.anchorcms.icloud.model.commservice.STask;
import com.anchorcms.icloud.model.commservice.STaskModel;
import com.anchorcms.icloud.model.synergy.SDemandObj;
import com.anchorcms.icloud.service.commservice.MissionGoseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_COMMSERVICE;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1214:05
 */
@Controller
public class MissionGoseController {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterCreateContriller.class);
    public static final String CLOUNDCEMTER_ADD = "tpl.missionGose_add";
    /**
     * @Author lilimin
     * @Date 2017/1/12 13:06
     * @Desc  云需求发布请求
     */
    @RequiresPermissions("member:missionGose_add")
    @RequestMapping("/member/missionGose_add.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String userName = user.getUsername();
        String userCompany = user.getCompany().getCompanyName();
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
        model.addAttribute("userName",userName);
        model.addAttribute("userCompany",userCompany);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, CLOUNDCEMTER_ADD);
    }

    /**
     * @author: lilimin
     * @desciption 添加任务下达controller
     */
    @RequestMapping(value = "/member/missionGose_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String taskName,
                       String taskExplain,//填报说明
                       String statusType,//状态位
                        Date startDt,
                       Date deadlineDt,
                       String demandObjListJsonString,
                       //cms表相关参数
                       Integer modelId, String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames,Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        STask sd = new STask();
        //默认值set
        sd.setStatus(statusType);
        if(attachmentNames!= null && attachmentNames.length>0){
            sd.setFileName(attachmentNames[0]+"");
            sd.setFilePath(attachmentPaths[0]+"");
        }
        sd.setTaskExplain(taskExplain);
        sd.setTaskName(taskName);
        sd.setStartDt(startDt);
        sd.setDeadlineDt(deadlineDt);
        sd.setDeFlag("1");
        sd.setCreaterDt(new Date(System.currentTimeMillis()));
        sd.setUser(user);
        sd.setCompany(user.getCompany());
        missionGoseService.save(sd,demandObjListJsonString);
        String nextUrl = "/member/manager_releasetask_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private MissionGoseService missionGoseService;
}
