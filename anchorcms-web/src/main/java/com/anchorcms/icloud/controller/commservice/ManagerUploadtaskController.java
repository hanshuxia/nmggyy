package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.commservice.STask;
import com.anchorcms.icloud.service.commservice.ManagerReleaseTaskService;
import com.anchorcms.icloud.service.commservice.ManagerUploadTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.constants.Constants.TPLDIR_COMMSERVICE;
import static com.anchorcms.common.page.SimplePage.cpn;

@Controller
public class ManagerUploadtaskController {
    private static final Logger log = LoggerFactory.getLogger(ManagerUploadtaskController.class);
    public static final String UPLOADTASK_LIST = "tpl.managerUploadtask";
    public static final String TASKFILE_LIST="tpl.taskfile";
    public static final String TASK_VIEW ="tpl.managerTaskView";

    /**
     * @Author Yhr
     * 查询任务列表
     * @Date 22017/01/12
     */
    @RequiresPermissions("member:uploadtask_list.jspx")
    @RequestMapping(value = "/member/manager_uploadtask_list.jspx")
    public String list(Date startDt, Date deadlineDt,String status,String taskName,Integer modelId,
                       Integer pageNo,HttpServletRequest request, ModelMap model) {
        String nextUrl = UPLOADTASK_LIST;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request,model,"member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        Pagination p = managerUploadTaskService.getPageForMember(cpn(pageNo), 20, startDt,deadlineDt,taskName,status,user);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        int total = p.getTotalCount();
        model.addAttribute("total",total);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("status",status);
        model.addAttribute("taskName",taskName);
        model.addAttribute("startDt",startDt);
        model.addAttribute("deadlineDt",deadlineDt);
        return FrontUtils.getTplPath(request,site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @Author lilimin
     * 管理员查询上报任务列表
     * @Date 22017/01/12
     */
    @RequiresPermissions("member:taskFile_list.jspx")
    @RequestMapping(value = "/member/taskFile_list.jspx")
    public String FileModellist(Date startDt, Date deadlineDt,String status,String taskName,Integer modelId,
                       Integer pageNo,HttpServletRequest request, ModelMap model) {
        String nextUrl = TASKFILE_LIST;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        Pagination p = managerUploadTaskService.getPageForTaskFill(cpn(pageNo), 20, startDt,deadlineDt,taskName,status,user);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("status",status);
        model.addAttribute("taskName",taskName);
        model.addAttribute("startDt",startDt);
        model.addAttribute("deadlineDt",deadlineDt);
        return FrontUtils.getTplPath(request,site.getSolutionPath(),
                TPLDIR_COMMSERVICE, nextUrl);
    }
 /*   *//**
     * @Author Yhr
     * 更新任务状态值
     * 草稿/发布
     * @Date 2017/01/13
     *//*
    @RequestMapping(value = "/member/manager_uploadtaskupdate.jspx")
    public String list(Date startDt, Date deadlineDt,String status,String taskName,Integer taskId,Integer modelId,
                       Integer pageNo,HttpServletRequest request, ModelMap model) {
        String nextUrl = UPLOADTASK_LIST;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request,model,"member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //不是管理员不能查看
        if(!user.getIsAdmin()){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        managerUploadTaskService.update(taskId);
        Pagination p = managerUploadTaskService.getPageForMember(cpn(pageNo),20,startDt,deadlineDt,taskName,status);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("status",status);
        return FrontUtils.getTplPath(request,site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }*/
    /**
     * @Author 闫浩芮
     * 明细查看
     * @Date 2017/1/16 0016 10:51
     */
    @RequestMapping(value = "/member/task_view.jspx")
    public String view(Integer taskId,Integer userId, HttpServletRequest request,
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
        model.addAttribute("userId",userId);
        return FrontUtils.getTplPath(request,site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    @Resource
    protected ContentMng contentMng;
    @Resource
    private ManagerReleaseTaskService managerReleaseTaskService;
    @Resource
    private ManagerUploadTaskService managerUploadTaskService;

}
