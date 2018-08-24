package com.anchorcms.cms.service.assist.impl;

import java.util.List;

import com.anchorcms.cms.dao.assist.CmsCommentDao;
import com.anchorcms.cms.model.assist.CmsComment;
import com.anchorcms.cms.model.assist.CmsCommentExt;
import com.anchorcms.cms.service.assist.CmsCommentExtMng;
import com.anchorcms.cms.service.assist.CmsCommentMng;
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


@Service
@Transactional
public class CmsCommentMngImpl implements CmsCommentMng {
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
	public List<CmsComment> getListForDel(Integer siteId, Integer userId, Integer commentUserId, String ip){
		return dao.getListForDel(siteId,userId,commentUserId,ip);
	}

	@Transactional(readOnly = true)
	public List<CmsComment> getListForTag(Integer siteId, Integer contentId,
			Integer parentId,Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int count) {
		return dao.getList(siteId, contentId,parentId,greaterThen, checked, recommend,
				desc, count, true);
	}

	@Transactional(readOnly = true)
	public CmsComment findById(Integer id) {
		CmsComment entity = dao.findById(id);
		return entity;
	}

	public CmsComment comment(Integer score,String text, String ip, Integer contentId,
			Integer siteId, Integer userId, boolean checked, boolean recommend,Integer parentId) {
		CmsComment comment = new CmsComment();
		comment.setContent(contentMng.findById(contentId));
		comment.setSite(cmsSiteMng.findById(siteId));
		if (userId != null) {
			comment.setCommentUser(cmsUserMng.findById(userId));
		}
		comment.setIsChecked(checked);
		comment.setIsRecommend(recommend);
		comment.setScore(score);
		comment.init();
		if(parentId!=null){
			CmsComment parent=findById(parentId);
			if (parent.getTopParentId() != null) {
				comment.setParent(parent);
				parent.setReplyCount(parent.getReplyCount()+1);
				update(parent, parent.getCommentExt());
				CmsComment topParent = findById(parent.getTopParentId());
				topParent.setReplyCount(topParent.getReplyCount() + 1);
				update(topParent, topParent.getCommentExt());
			} else{
				comment.setParent(parent);
				parent.setReplyCount(parent.getReplyCount()+1);
				update(parent, parent.getCommentExt());
			}

			comment.setTopParentIds(parentId);
		}
		dao.save(comment);
		text = cmsSensitivityMng.replaceSensitivity(text);
		cmsCommentExtMng.save(ip, text, comment);
		contentCountMng.commentCount(contentId);
		return comment;
	}

	public CmsComment update(CmsComment bean, CmsCommentExt ext) {
		Updater<CmsComment> updater = new Updater<CmsComment>(bean);
		bean = dao.updateByUpdater(updater);
		cmsCommentExtMng.update(ext);
		return bean;
	}

	public int deleteByContentId(Integer contentId) {
		cmsCommentExtMng.deleteByContentId(contentId);
		return dao.deleteByContentId(contentId);
	}

	public CmsComment deleteById(Integer id) {
		CmsComment bean = dao.deleteById(id);
		return bean;
	}

	public CmsComment[] deleteByIds(Integer[] ids) {
		CmsComment[] beans = new CmsComment[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	public CmsComment[] checkByIds(Integer[] ids, CmsUser user, boolean checked) {
		CmsComment[] beans = new CmsComment[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = checkById(ids[i],user,checked);
		}
		return beans;
	}
	
	
	private CmsComment checkById(Integer id, CmsUser checkUser, boolean checked){
		CmsComment bean=findById(id);
		Updater<CmsComment> updater = new Updater<CmsComment>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setIsChecked(checked);
		return bean;
	}
	
	public void ups(Integer id) {
		CmsComment comment = findById(id);
		comment.setUps((short) (comment.getUps() + 1));
	}

	public void downs(Integer id) {
		CmsComment comment = findById(id);
		comment.setDowns((short) (comment.getDowns() + 1));
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
	private CmsCommentExtMng cmsCommentExtMng;
	@Resource
	private CmsCommentDao dao;

}