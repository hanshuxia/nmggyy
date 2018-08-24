package com.anchorcms.icloud.controller.commservice;

import com.alibaba.fastjson.JSON;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.ResponseUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.controller.cloudcenter.CloudCenterCreateContriller;
import com.anchorcms.icloud.model.cloudcenter.Result;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import com.anchorcms.icloud.model.cloudcenter.SIcloudQuoteManage;
import com.anchorcms.icloud.model.commservice.STask;
import com.anchorcms.icloud.model.commservice.STaskFill;
import com.anchorcms.icloud.model.commservice.STaskModel;
import com.anchorcms.icloud.model.commservice.STaskModelCopy;
import com.anchorcms.icloud.service.commservice.MissionGoseService;
import com.anchorcms.icloud.service.commservice.ReportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_COMMSERVICE;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/139:08
 */
@Controller
public class ReportController {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterCreateContriller.class);
    public static final String REPORT_ADD = "tpl.report_add";

    /**
     * @Author lilimin
     * @Date 2017/1/13 13:06
     * @Desc  任务上报
     */
    @RequiresPermissions("member:report_add")
    @RequestMapping("/member/report_add.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      Integer taskId,//任务ID
                      ModelMap model) {
        //这里先给任务一个Id
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        List<STaskModel> list = missionGoseService.getModelById(taskId);
        int length = list.size();
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
        STask stake =  missionGoseService.getByTaskId(taskId);
        String userName = stake.getUser().getUsername();
        String userCompany = stake.getCompany().getCompanyName();
        model.addAttribute("userName",userName);
        model.addAttribute("userCompany",userCompany);
        model.addAttribute("length",length);
        model.addAttribute("site", site);
        model.addAttribute("stake",stake);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, REPORT_ADD);
    }

    @RequestMapping("/member/report_model.jspx")
    public void modelJson(HttpServletRequest request, HttpServletResponse response,
                      Integer models,//任务ID
                      ModelMap model) {
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        //或得模型中的数据
        List<STaskModel> list = missionGoseService.getModelById(models);
        List<STaskModelCopy> listCopy = copyModel(list);
        String Listmodels = JSON.toJSONString(listCopy);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(Listmodels);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对象替换
     * @param list
     * @return
     */
    private List<STaskModelCopy> copyModel(List<STaskModel> list) {
        List<STaskModelCopy> lists = new ArrayList<STaskModelCopy>();
        for(int i=0;i<list.size();i++){
            STaskModelCopy modelCopy = new STaskModelCopy();
            modelCopy.setModelLength(list.get(i).getModelLength());
            modelCopy.setTaskId(list.get(i).getTaskId());
            modelCopy.setModelName(list.get(i).getModelName());
            modelCopy.setModelType(list.get(i).getModelType());
            modelCopy.setRemark(list.get(i).getRemark());
            modelCopy.setSortNum(list.get(i).getSortNum());
            modelCopy.setTaskModelId(list.get(i).getTaskModelId());
            lists.add(modelCopy);
        }
        return lists;
    }

    /**
     * @auther lilimin
     * @Descript 保存
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/member/report_model_save.jspx")
    public String saveQuoteManger(HttpServletRequest request,HttpServletResponse response, ModelMap model,
                                Integer models,//taskId
                                String demandObjListJsonString){
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        STaskFill sd = new STaskFill();
        //sd.setTaskId(models);
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        sd.setCreaterDt(new Date(System.currentTimeMillis()));
        sd.setUser(user);
        sd.setStatus("0");
        sd.setDeFlag("1");
        reportService.save(models,sd,demandObjListJsonString,user.getUserId());
        String nextUrl = "/member/manager_uploadtask_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    @Resource
    protected ContentMng contentMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    ReportService reportService;
    @Resource
    private MissionGoseService missionGoseService;
}
