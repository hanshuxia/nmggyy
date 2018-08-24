package com.anchorcms.icloud.controller.commservice;

import com.alibaba.fastjson.JSON;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.commservice.STask;
import com.anchorcms.icloud.model.commservice.STaskModel;
import com.anchorcms.icloud.model.commservice.STask_attr;
import com.anchorcms.icloud.service.commservice.ManagerReleaseTaskService;
import com.anchorcms.icloud.service.commservice.MissionGoseService;
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
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

@Controller
public class ManagerReleasetaskController {
    private static final Logger log = LoggerFactory.getLogger(ManagerReleasetaskController.class);
    public static final String TELEASETASK_LIST = "tpl.managertaskList";
    public static final String  TASK_EDIT = "tpl.managertaskEdit";
    public static final String  TASK_VIEW = "tpl.managertaskView";

    /**
     * 判断下发是否被上报
     * @Auther lilimin
     * @Date 2017/2/22
     */
    @RequiresPermissions("/member/tastModel_checkUpdate.jspx")
    @RequestMapping("/member/tastModel_checkUpdate.jspx")
    public void checkUpdate(Integer taskId,HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        FrontUtils.frontData(request, model, site);
        PrintWriter writer = null;
        writer = response.getWriter();
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        List list = managerReleaseTaskService.getFileTaskByTaskId(taskId);
        if(list == null || "".equals(list) || list.size()==0){

                writer.print(false);
        }else{
            writer.print(true);
        }
        writer.flush();
        writer.close();

    }


    /**
     * @Author Yhr
     * 查询用户所在公司的需求列表
     * @Date 22017/01/12
     */
    @RequiresPermissions("member:releasetask_list.jspx")
    @RequestMapping(value = "/member/manager_releasetask_list.jspx")
    public String list(Date startDt, Date deadlineDt,String status,String taskName,Integer modelId,
                       Integer pageNo,HttpServletRequest request, ModelMap model) {
        String nextUrl = TELEASETASK_LIST;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();

        Pagination p = managerReleaseTaskService.getPageForMember(cpn(pageNo), 20, startDt,deadlineDt,taskName,status,user);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("status",status);
        model.addAttribute("taskName",taskName);
        model.addAttribute("startDt",startDt);
        model.addAttribute("deadlineDt",deadlineDt);
        return FrontUtils.getTplPath(request,site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    /**
     * 草稿/发布
     * @Author 闫浩芮
     * 更新任务状态值
     * @Date 2017/01/13
     */
    @RequestMapping(value = "/member/manager_releasetaskupdate.jspx")
    public String list(Date startDt, Date deadlineDt,String status,String taskName,Integer taskId,Integer modelId,
                       Integer pageNo,HttpServletRequest request, ModelMap model) {
        String nextUrl = TELEASETASK_LIST;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        managerReleaseTaskService.update(taskId);
        Pagination p = managerReleaseTaskService.getPageForMember(cpn(pageNo),20,startDt,deadlineDt,taskName,status, user);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("status",status);
        return FrontUtils.getTplPath(request,site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    /**
     * 任务编辑
     * @Author 闫浩芮
     * @param taskId  对象ID
     * @Date 2017/1/3 0003 18:26
     */
    @RequestMapping(value = "/member/manager_task_editor.jspx")
    public String editor(Integer taskId, HttpServletRequest request,
                         HttpServletResponse response, ModelMap model) {
        String nextUrl = TASK_EDIT;
        return taskRedirective(taskId,request,true,response,model,nextUrl);
    }
    /**
     * @author: gao jiangning
     * @desciption 2017/1/5 根据demandID获取demand和 content等一些参数放入model，并根据nextUrl跳转的方法
     * isEdit: 若为编辑，需要加一个权限判断
     */
    private String taskRedirective(Integer taskId, HttpServletRequest request, boolean isEdit,
                                     HttpServletResponse response, ModelMap model, String nextUrl){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        STask task= managerReleaseTaskService.byTaskId(taskId);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("task",task);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }


    /**
     * @author: 闫浩芮
     * @desciption 保存编辑后的任务
     */
    @RequestMapping(value = "/member/task_editorsave.jspx")
    public String editorTask(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             String taskName,
                             String taskExplain,//填报说明
                             String statusType,//状态位
                             java.sql.Date startDt,
                             java.sql.Date deadlineDt,
                             String demandObjListJsonString,
                             //cms表相关参数
                             Integer taskId, String[] attachmentPaths, String[] attachmentNames,
                             String[] attachmentFilenames,Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        STask sd =missionGoseService.getByTaskId(taskId);
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
        sd.setCreaterDt(new java.sql.Date(System.currentTimeMillis()));
        sd.setUser(user);
        sd.setCompany(user.getCompany());
        managerReleaseTaskService.updateSave(sd,demandObjListJsonString);
        String nextUrl = "/member/manager_releasetask_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    @RequestMapping("/member/tastModel_onload.jspx")
    public void modelJson(HttpServletRequest request, HttpServletResponse response,
                          Integer taskId,//任务ID
                          ModelMap model) {
        CmsUser user = CmsUtils.getUser(request);
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        //或得模型中的数据
        List<STaskModel> stakeList = missionGoseService.getModelById(taskId);
        String Listmodels = JSON.toJSONString(stakeList);
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
     * 或得列的数据
     * @param request
     * @param response
     * @param taskId
     * @param model
     */
    @RequestMapping("/member/tastFillModel_onload.jspx")
    public void fillModelJson(HttpServletRequest request, HttpServletResponse response,
                          Integer taskId,//任务ID
                              Integer userId,
                          ModelMap model) {
        CmsUser user = CmsUtils.getUser(request);
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        //或得模型中的数据
        List<STask_attr> list = missionGoseService.getModelByIdUserId(taskId,userId);
        String Listmodels = JSON.toJSONString(list);
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
     * 根据taskId删除任务信息
     * taskId 操作对象的ID
     * @Author 闫浩芮
     * @Date 2017/1/16 0016 9:17
     */
    @RequestMapping(value = "/member/manager_task_delete.jspx")
    public String delete(Integer taskId,String status,Date startDt,Date deadlineDt,String taskName,Integer modelId,
                         Integer queryChannelId, Integer pageNo,HttpServletRequest request, ModelMap model){
        String nextUrl = "/member/manager_releasetask_list.jspx";
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        managerReleaseTaskService.delete(taskId);
        Pagination p = managerReleaseTaskService.getPageForMember(cpn(pageNo),20,startDt,deadlineDt,taskName,status, user);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("pagination", p);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author maocheng
     * 明细查看
     * @Date 2017/1/16 0016 10:51
     */
    @RequestMapping(value = "/member/manager_task_view.jspx")
    public String view(Integer taskId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = TASK_VIEW;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        STask task= managerReleaseTaskService.byTaskId(taskId);
        model.addAttribute("task",task);
        return FrontUtils.getTplPath(request,site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    @Resource
    protected ContentMng contentMng;
    @Resource
    private ManagerReleaseTaskService managerReleaseTaskService;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private MissionGoseService missionGoseService;
}
