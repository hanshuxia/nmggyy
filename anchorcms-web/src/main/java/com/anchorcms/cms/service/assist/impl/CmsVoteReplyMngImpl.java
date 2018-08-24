package com.anchorcms.cms.service.assist.impl;

import com.anchorcms.cms.dao.assist.CmsVoteReplyDao;
import com.anchorcms.cms.model.assist.CmsVoteReply;
import com.anchorcms.cms.service.assist.CmsVoteReplyMng;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CmsVoteReplyMngImpl implements CmsVoteReplyMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer  subTopicId, int pageNo, int pageSize){
		return dao.getPage(subTopicId, pageNo, pageSize);
	}
	@Transactional(readOnly = true)
	public CmsVoteReply findById(Integer id) {
		CmsVoteReply entity = dao.findById(id);
		return entity;
	}

	public CmsVoteReply save(CmsVoteReply bean) {
		dao.save(bean);
		return bean;
	}

	public CmsVoteReply update(CmsVoteReply bean) {
		Updater<CmsVoteReply> updater = new Updater<CmsVoteReply>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}


	public CmsVoteReply deleteById(Integer id) {
		CmsVoteReply bean = dao.deleteById(id);
		return bean;
	}

	public CmsVoteReply[] deleteByIds(Integer[] ids) {
		CmsVoteReply[] beans = new CmsVoteReply[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Resource
	private CmsVoteReplyDao dao;
}