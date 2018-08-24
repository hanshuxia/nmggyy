package com.anchorcms.cms.controller.admin.assist;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.anchorcms.cms.model.assist.CmsVoteItem;
import com.anchorcms.cms.model.assist.CmsVoteSubTopic;
import com.anchorcms.cms.model.assist.CmsVoteTopic;
import com.anchorcms.cms.service.assist.CmsVoteItemMng;
import com.anchorcms.cms.service.assist.CmsVoteSubTopicMng;
import com.anchorcms.cms.service.assist.CmsVoteTopicMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.service.CmsLogMng;
import com.anchorcms.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.common.utils.CmsUtils;

import static com.anchorcms.common.page.SimplePage.cpn;


@Controller
public class CmsVoteTopicController {
	private static final Logger log = LoggerFactory
			.getLogger(CmsVoteTopicController.class);

	@RequiresPermissions("vote_topic:v_list")
	@RequestMapping("/vote_topic/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Pagination pagination = manager.getPage(site.getSiteId(), cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNo", pagination.getPageNo());
		return "vote_topic/list";
	}

	@RequiresPermissions("vote_topic:v_add")
	@RequestMapping("/vote_topic/v_add.do")
	public String add(ModelMap model) {
		return "vote_topic/add";
	}

	@RequiresPermissions("vote_topic:v_edit")
	@RequestMapping("/vote_topic/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("cmsVoteTopic", manager.findById(id));
		model.addAttribute("pageNo", pageNo);
		return "vote_topic/edit";
	}

	@RequiresPermissions("vote_topic:o_save")
	@RequestMapping("/vote_topic/o_save.do")
	public String save(CmsVoteTopic bean, String[] subtitle, Integer[] subPriority,
					   String[] itemTitle, Integer[] itemVoteCount, Integer[] itemPriority,
					   String[]picture, HttpServletRequest request, ModelMap model) {
		WebErrors errors;
		errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		if(bean.getVoteDay()==null){
			bean.setVoteDay(0);
		}
		List<Integer>subTypeIds=getSubTypeIdsParam(request);
		Set<CmsVoteSubTopic>subTopics=getSubTopics(null, subtitle,subPriority, subTypeIds);
		bean = manager.save(bean, subTopics);
		List<List<CmsVoteItem>>voteItems=getSubtopicItems(itemTitle, itemVoteCount, itemPriority,picture);
		List<CmsVoteSubTopic>subTopicSet=subTopicMng.findByVoteTopic(bean.getVotetopicId());
		for(int i=0;i<voteItems.size();i++){
			if(voteItems.get(i).size()<=0){
				voteItems.remove(i);
			}
		}
		if(voteItems.size()>0){
			for(int i=0;i<subTopicSet.size();i++){
				voteItemMng.save(voteItems.get(i), subTopicSet.get(i));
			}
		}
		log.info("save CmsVoteTopic id={}", bean.getVotetopicId());
		cmsLogMng.operating(request, "cmsVoteTopic.log.save", "id="
				+ bean.getVotetopicId() + ";title=" + bean.getTitle());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("vote_topic:o_update")
	@RequestMapping("/vote_topic/o_update.do")
	public String update(CmsVoteTopic bean,String[] subtitle,Integer[] subPriority
			,Integer[] subTopicId,String[] itemTitle, Integer[] itemVoteCount
			,Integer[] itemPriority,String[]picture,
			Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getVotetopicId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		List<Integer>subTypeIds=getSubTypeIdsParam(request);
		Set<CmsVoteSubTopic>subTopics=getSubTopics(subTopicId, subtitle,subPriority, subTypeIds);
		bean = manager.update(bean);
		subTopicMng.update(subTopics,bean);
		List<List<CmsVoteItem>>voteItems=getSubtopicItems(itemTitle, itemVoteCount, itemPriority,picture);
		List<CmsVoteSubTopic>subTopicSet=subTopicMng.findByVoteTopic(bean.getVotetopicId());
		for(int i=0;i<voteItems.size();i++){
			if(voteItems.get(i).size()<=0){
				voteItems.remove(i);
			}
		}
		for(int i=0;i<subTopicSet.size();i++){
			CmsVoteSubTopic voteSubTopic= subTopicSet.get(i);
			if(voteSubTopic.getSubtopicType()!=3&&voteItems.size()>=subTopicSet.size()){
				voteItemMng.update(voteItems.get(i),voteSubTopic);
			}
		}
		log.info("update CmsVoteTopic id={}.", bean.getVotetopicId());
		cmsLogMng.operating(request, "cmsVoteTopic.log.update", "id="
				+ bean.getVotetopicId() + ";title=" + bean.getTitle());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("vote_topic:o_delete")
	@RequestMapping("/vote_topic/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsVoteTopic[] beans = manager.deleteByIds(ids);
		for (CmsVoteTopic bean : beans) {
			log.info("delete CmsVoteTopic id={}", bean.getVotetopicId());
			cmsLogMng.operating(request, "cmsVoteTopic.log.delete", "id="
					+ bean.getVotetopicId() + ";title=" + bean.getTitle());
		}
		return list(pageNo, request, model);
	}

	
	private List<Integer> getSubTypeIdsParam(HttpServletRequest request){
		return getParamsByStartName(request, "typeId");
	}
	
	private List<Integer> getParamsByStartName(HttpServletRequest request,String startName){
		//参数名从小到大排序
		Enumeration<?> paramNames=request.getParameterNames();
		List<Integer>params=new ArrayList<Integer>();
		List<Integer>paramEndNames=new ArrayList<Integer>();
		String paramName;
		while(paramNames.hasMoreElements()){
			paramName=(String) paramNames.nextElement();
			if(paramName.startsWith(startName)){
				String paramEndName=paramName.substring(startName.length());
				paramEndNames.add(Integer.parseInt(paramEndName));
			}
		}
		Collections.sort(paramEndNames);
		for(Integer paramEndName:paramEndNames){
			params.add(Integer.parseInt(request.getParameter(startName+paramEndName)));
		}
		return params;
	}

	private List<List<CmsVoteItem>> getSubtopicItems( String[] itemTitle,
			Integer[] itemVoteCount, Integer[] itemPriority,String[]picture) {
		List<List<CmsVoteItem>> subTopicItems= new ArrayList<List<CmsVoteItem>>();
		CmsVoteItem item;
		List<Integer>splitCharIndexList=new ArrayList<Integer>();
		if(itemTitle!=null){
			for (int i = 0, len = itemTitle.length; i < len; i++) {
				if(itemTitle[i].equals(",")){
					splitCharIndexList.add(i);
				}
			}
			for(int i=0;i<splitCharIndexList.size()-1;i++){
				List<CmsVoteItem>items=new ArrayList<CmsVoteItem>();
				//非连续分隔符
				if(splitCharIndexList.get(i+1)-splitCharIndexList.get(i)!=1){
					for(int index=splitCharIndexList.get(i);index<itemTitle.length;index++){
						if(index>splitCharIndexList.get(i)&&index<splitCharIndexList.get(i+1)){
							if (!StringUtils.isBlank(itemTitle[index])) {
								item = new CmsVoteItem();
								item.setTitle(itemTitle[index]);
								item.setVoteCount(itemVoteCount[index-i-1]);
								item.setPriority(itemPriority[index-i-1]);
								item.setPicture(picture[index-i-1]);
								items.add(item);
							}
						}
					}
				}
				subTopicItems.add(items);
			}
		}
		return subTopicItems;
	}
	
	private Set<CmsVoteSubTopic> getSubTopics(Integer[] subTopicIds,String[] titles,Integer[]subPrioritys,List<Integer>typeIds) {
		SortedSet<CmsVoteSubTopic> subTopics = new TreeSet<CmsVoteSubTopic>();
		CmsVoteSubTopic subTopic;
		if(titles!=null){
			for (int i = 0, len = titles.length; i < len; i++) {
				if (!StringUtils.isBlank(titles[i])) {
					subTopic = new CmsVoteSubTopic();
					if (subTopicIds != null && subTopicIds[i] != null) {
						subTopic.setSubtopicId(subTopicIds[i]);
					}
					subTopic.setTitle(titles[i]);
					subTopic.setSubtopicType(typeIds.get(i));
					subTopic.setPriority(subPrioritys[i]);
					subTopics.add(subTopic);
				}
			}
		}
		return subTopics;
	}

	private WebErrors validateSave(CmsVoteTopic bean, HttpServletRequest request) {
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
		CmsVoteTopic entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsVoteTopic.class, id)) {
			return true;
		}
		if (!entity.getSite().getSiteId().equals(siteId)) {
			errors.notInSite(CmsVoteTopic.class, id);
			return true;
		}
		return false;
	}

	@Resource
	private CmsLogMng cmsLogMng;
	@Resource
	private CmsVoteTopicMng manager;
	@Resource
	private CmsVoteSubTopicMng subTopicMng;
	@Resource
	private CmsVoteItemMng voteItemMng;
}