package com.anchorcms.cms.service.assist.impl;

import com.anchorcms.cms.dao.assist.CmsConsultDao;
import com.anchorcms.cms.model.assist.CmsConsult;
import com.anchorcms.cms.model.assist.CmsConsultExt;
import com.anchorcms.cms.service.assist.CmsConsultExtMng;
import com.anchorcms.cms.service.assist.CmsConsultMng;
import com.anchorcms.cms.service.assist.CmsSensitivityMng;
import com.anchorcms.cms.service.main.ContentCountMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.service.CmsSiteMng;
import com.anchorcms.core.service.CmsUserMng;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class CmsConsultMngImpl implements CmsConsultMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer contentId,
							  Integer greaterThen, Boolean checked, Boolean recommend,
							  boolean desc, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId, contentId, greaterThen, checked,
				recommend, desc, pageNo, pageSize, false);
		return page;
	}

	@Transactional(readOnly = true)
	public Pagination getPageForTag(Integer siteId, Integer contentId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId, contentId, greaterThen, checked,
				recommend, desc, pageNo, pageSize, true);
		return page;
	}
	public Pagination getPageForMember(Integer siteId, Integer contentId,Integer toUserId,Integer fromUserId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize){
		Pagination page = dao.getPageForMember(siteId, contentId,toUserId,fromUserId, greaterThen, checked,
				recommend, desc, pageNo, pageSize, false);
		return page;
	}
	
	@Transactional(readOnly = true)
	public List<CmsConsult> getListForDel(Integer siteId, Integer userId, Integer consultUserId, String ip){
		return dao.getListForDel(siteId,userId,consultUserId,ip);
	}

	@Transactional(readOnly = true)
	public List<CmsConsult> getListForTag(Integer siteId, Integer contentId,
			Integer parentId,Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int count) {
		return dao.getList(siteId, contentId,parentId,greaterThen, checked, recommend,
				desc, count, true);
	}

	@Transactional(readOnly = true)
	public CmsConsult findById(Integer id) {
		CmsConsult entity = dao.findById(id);
		return entity;
	}

	public CmsConsult consult(Integer score,String text, String ip, Integer contentId,
			Integer siteId, Integer userId, boolean checked, boolean recommend,Integer parentId) {
		CmsConsult consult = new CmsConsult();
		consult.setContent(contentMng.findById(contentId));
		consult.setSite(cmsSiteMng.findById(siteId));
		if (userId != null) {
			consult.setConsultUser(cmsUserMng.findById(userId));
		}
		consult.setIsChecked(checked);
		consult.setIsRecommend(recommend);
		consult.setScore(score);
		consult.init();
		if(parentId!=null){
			CmsConsult parent = findById(parentId);
			if (parent.getTopParentId() != null) {
				consult.setParent(parent);
				parent.setReplyCount(parent.getReplyCount() + 1);
				update(parent, parent.getConsultExt());
				CmsConsult topParent = findById(parent.getTopParentId());
				topParent.setReplyCount(topParent.getReplyCount() + 1);
				update(topParent, topParent.getConsultExt());
			} else{
				consult.setParent(parent);
				parent.setReplyCount(parent.getReplyCount() + 1);
				update(parent, parent.getConsultExt());
			}
			consult.setTopParentIds(parentId);
		}
		dao.save(consult);
		text = cmsSensitivityMng.replaceSensitivity(text);
		CmsConsultExtMng.save(ip, text, consult);
//		contentCountMng.consultCount(contentId);
		return consult;
	}

	public CmsConsult update(CmsConsult bean, CmsConsultExt ext) {
		Updater<CmsConsult> updater = new Updater<CmsConsult>(bean);
		bean = dao.updateByUpdater(updater);
		CmsConsultExtMng.update(ext);
		return bean;
	}

	public int deleteByContentId(Integer contentId) {
		CmsConsultExtMng.deleteByContentId(contentId);
		return dao.deleteByContentId(contentId);
	}

	public CmsConsult deleteById(Integer id) {
		CmsConsult bean = dao.deleteById(id);
		return bean;
	}

	public CmsConsult[] deleteByIds(Integer[] ids) {
		CmsConsult[] beans = new CmsConsult[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	public CmsConsult[] checkByIds(Integer[] ids, CmsUser user, boolean checked) {
		CmsConsult[] beans = new CmsConsult[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = checkById(ids[i],user,checked);
		}
		return beans;
	}
	
	
	private CmsConsult checkById(Integer id, CmsUser checkUser, boolean checked){
		CmsConsult bean=findById(id);
		Updater<CmsConsult> updater = new Updater<CmsConsult>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setIsChecked(checked);
		return bean;
	}
	
	public void ups(Integer id) {
		CmsConsult consult = findById(id);
		consult.setUps((short) (consult.getUps() + 1));
	}

	public void downs(Integer id) {
		CmsConsult consult = findById(id);
		consult.setDowns((short) (consult.getDowns() + 1));
	}

	@Resource
	private CmsSensitivityMng cmsSensitivityMng;
	@Resource
	private CmsUserMng cmsUserMng;
	@Resource
	private CmsSiteMng cmsSiteMng;
	@Resource
	private ContentMng contentMng;
	@Resource
	private ContentCountMng contentCountMng;
	@Resource
	private CmsConsultExtMng CmsConsultExtMng;
	@Resource
	private CmsConsultDao dao;

}