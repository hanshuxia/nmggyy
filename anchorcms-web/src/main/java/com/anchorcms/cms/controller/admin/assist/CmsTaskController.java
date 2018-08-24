package com.anchorcms.cms.controller.admin.assist;

import static com.anchorcms.common.page.SimplePage.cpn;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.anchorcms.cms.model.assist.CmsAcquisition;
import com.anchorcms.cms.model.assist.CmsTask;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.service.assist.CmsAcquisitionMng;
import com.anchorcms.cms.service.assist.CmsTaskMng;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.common.web.RequestUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.Ftp;
import com.anchorcms.core.service.FtpMng;
import com.anchorcms.core.web.WebErrors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CmsTaskController {
	private static final Logger log = LoggerFactory.getLogger(CmsTaskController.class);

	@RequiresPermissions("task:v_list")
	@RequestMapping("/task/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(CmsUtils.getSiteId(request),cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "task/list";
	}

	@RequiresPermissions("task:v_add")
	@RequestMapping("/task/v_add.do")
	public String add(HttpServletRequest request,ModelMap model) {
		List<Ftp>ftpList=ftpMng.getList();
		List<CmsAcquisition>acquList=acquisitionMng.getList(CmsUtils.getSiteId(request));
		// 栏目列表
		CmsSite site = CmsUtils.getSite(request);
		List<Channel> topList = channelMng.getTopList(site.getSiteId(), false);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				null, false);
		model.addAttribute("ftpList", ftpList);
		model.addAttribute("acquList", acquList);
		model.addAttribute("channelList", channelList);
		return "task/add";
	}

	@RequiresPermissions("task:v_edit")
	@RequestMapping("/task/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsTask task=manager.findById(id);
		List<Ftp>ftpList=ftpMng.getList();
		List<CmsAcquisition>acquList=acquisitionMng.getList(CmsUtils.getSiteId(request));
		// 栏目列表
		CmsSite site = CmsUtils.getSite(request);
		List<Channel> topList = channelMng.getTopList(site.getSiteId(), false);
		List<Channel> channelList = Channel.getListForSelect(topList, null,null, false);
		Map<String,String>params=task.getAttr();
		//获取分发文件夹
		List<String>folders=getFolderParams(params);
		model.addAttribute("ftpList", ftpList);
		model.addAttribute("acquList", acquList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("task", task);
		model.addAttribute("folders", folders);
		model.addAttribute("pageNo",pageNo);
		return "task/edit";
	}

	@RequiresPermissions("task:o_save")
	@RequestMapping("/task/o_save.do")
	public String save(CmsTask bean, HttpServletRequest request, ModelMap model) throws ParseException, SchedulerException, ClassNotFoundException {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean.init(CmsUtils.getSite(request),CmsUtils.getUser(request));
		Map<String,String>attrs= RequestUtils.getRequestMap(request, "attr_");
		UUID uuid=UUID.randomUUID();
		bean.setTaskCode(uuid.toString());
		//静态化任务添加站点id参数
		String siteId= CmsUtils.getSite(request).getSiteId().toString();
		if(bean.getTaskType()==(CmsTask.TASK_STATIC_INDEX)){
			attrs.put(CmsTask.TASK_PARAM_SITE_ID,siteId);
			bean.setAttr(attrs);
		}else if(bean.getTaskType()==(CmsTask.TASK_STATIC_CHANNEL)||bean.getTaskType()==(CmsTask.TASK_STATIC_CONTENT)){
			attrs.put(CmsTask.TASK_PARAM_SITE_ID, siteId);
		}
		bean.setAttr(attrs);
		bean = manager.save(bean);
		//启用则启动任务
		if(bean.getIsEnable()==0){
			startTask(bean,uuid.toString());
		}
		log.info("save CmsTask id={}", bean.getTaskId());
		return "redirect:v_list.do";
	}
	

	@RequiresPermissions("task:o_update")
	@RequestMapping("/task/o_update.do")
	public String update(CmsTask bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) throws SchedulerException, ParseException, ClassNotFoundException {
		WebErrors errors = validateUpdate(bean.getTaskId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		if(bean.getExecycle()==(CmsTask.EXECYCLE_DEFINE)){
			bean.setCronExpression(null);
		}else if(bean.getExecycle()==(CmsTask.EXECYCLE_CRON)){
			bean.setIntervalUnit(null);
		}
		Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
		//静态化任务添加站点id参数
		String siteId= CmsUtils.getSite(request).getSiteId().toString();
		if(bean.getTaskType()==(CmsTask.TASK_STATIC_INDEX)){
			attr.put(CmsTask.TASK_PARAM_SITE_ID,siteId);
		}else if(bean.getTaskType()==(CmsTask.TASK_STATIC_CHANNEL)||bean.getTaskType()==(CmsTask.TASK_STATIC_CONTENT)){
			attr.put(CmsTask.TASK_PARAM_SITE_ID, siteId);
		}
		//结束之前的任务，开始新任务调度
		String oldTaskCode=manager.findById(bean.getTaskId()).getTaskCode();
		endTask(oldTaskCode);
		UUID uuid=UUID.randomUUID();
		bean.setTaskCode(uuid.toString());
		bean = manager.update(bean,attr);
		if(bean.getIsEnable()==0){
			startTask(bean,uuid.toString());
		}
		log.info("update CmsTask id={}.", bean.getTaskId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("task:o_delete")
	@RequestMapping("/task/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) throws SchedulerException {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsTask[] beans = manager.deleteByIds(ids);
		for (CmsTask bean : beans) {
			//删除结束任务
			endTask(bean.getTaskCode());
			log.info("delete CmsTask id={}", bean.getTaskId());
		}
		return list(pageNo, request, model);
	}
	
	/**
	 * 开始任务调度
	 * @param task 任务
	 * @param taskCode 任务名称
	 * @throws ParseException
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	private void startTask(CmsTask task,String taskCode) throws ParseException, SchedulerException, ClassNotFoundException{
		String cronExpress=manager.getCronExpressionFromDB(task.getTaskId());
		if(cronExpress.indexOf("null")==-1){
			JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
			jobDetailFactoryBean.setName(taskCode);
			jobDetailFactoryBean.setGroup(Scheduler.DEFAULT_GROUP);
			jobDetailFactoryBean.setJobClass(getClassByTask(task.getJobClass()));
			//任务需要参数attr属性 
			jobDetailFactoryBean.setJobDataMap(getJobDataMap(task.getAttr()));
			jobDetailFactoryBean.afterPropertiesSet();
			CronTriggerFactoryBean cronTriggerFactoryBean=new CronTriggerFactoryBean();
			cronTriggerFactoryBean.setBeanName(taskCode);
			cronTriggerFactoryBean.setCronExpression(cronExpress);
			cronTriggerFactoryBean.setGroup(Scheduler.DEFAULT_GROUP);
			cronTriggerFactoryBean.setName("cron_" + taskCode);
			cronTriggerFactoryBean.afterPropertiesSet();
			scheduler.scheduleJob(jobDetailFactoryBean.getObject(), cronTriggerFactoryBean.getObject()); 
		}
	}
	
	/**
	 * 结束任务调度
	 * @param taskName 
	 */
	private void endTask(String taskName) throws SchedulerException{
		//scheduler.deleteJob(taskName, Scheduler.DEFAULT_GROUP);
		scheduler.deleteJob(JobKey.jobKey(taskName, Scheduler.DEFAULT_GROUP));
	}
	
	private JobDataMap getJobDataMap(Map<String,String> params){
		JobDataMap jdm=new JobDataMap();
		Set<String>keySet=params.keySet();
		Iterator<String>it=keySet.iterator();
		while(it.hasNext()){
			String key=it.next();
			jdm.put(key, params.get(key));
		}
		return jdm;
	}
	
	private Class<?> getClassByTask(String taskClassName) throws ClassNotFoundException{
		return Class.forName(taskClassName);
	}
	
	private List<String>getFolderParams(Map<String,String>params){
		List<String>folders=new ArrayList<String>();
		Set<String>keySet=params.keySet();
		Iterator<String>it=keySet.iterator();
		while(it.hasNext()){
			String key=it.next();
			if(key.startsWith(CmsTask.TASK_PARAM_FOLDER_PREFIX)){
				folders.add(params.get(key));
			}
		}
		return folders;
	}

	private WebErrors validateSave(CmsTask bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
		return errors;
	}
	
	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getSiteId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getSiteId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, site.getSiteId(), errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsTask entity = manager.findById(id);
		if(errors.ifNotExist(entity, CmsTask.class, id)) {
			return true;
		}
		if (!entity.getSite().getSiteId().equals(siteId)) {
			errors.notInSite(CmsTask.class, id);
			return true;
		}
		return false;
	}
	@Resource
	private Scheduler scheduler;
	@Resource
	private CmsTaskMng manager;
	@Resource
	private FtpMng ftpMng;
	@Resource
	private CmsAcquisitionMng acquisitionMng;
	@Resource
	private ChannelMng channelMng;
}