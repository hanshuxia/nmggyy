package com.anchorcms.cms.service.assist.impl;

import java.sql.Timestamp;
import java.util.Date;

import com.anchorcms.cms.dao.assist.CmsVoteRecordDao;
import com.anchorcms.cms.model.assist.CmsVoteRecord;
import com.anchorcms.cms.model.assist.CmsVoteTopic;
import com.anchorcms.cms.service.assist.CmsVoteRecordMng;
import com.anchorcms.core.model.CmsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CmsVoteRecordMngImpl implements CmsVoteRecordMng {

	public CmsVoteRecord save(CmsVoteTopic topic, CmsUser user, String ip,
							  String cookie) {
		CmsVoteRecord record = new CmsVoteRecord();
		record.setTopic(topic);
		record.setVoteIp(ip);
		record.setVoteCookie(cookie);
		record.setVoteTime(new Timestamp(System.currentTimeMillis()));
		dao.save(record);
		return record;
	}

	public int deleteByTopic(Integer topicId) {
		return dao.deleteByTopic(topicId);
	}

	public Date lastVoteTimeByUserId(Integer userId, Integer topicId) {
		CmsVoteRecord record = dao.findByUserId(userId, topicId);
		return record != null ? record.getVoteTime() : null;
	}

	public Date lastVoteTimeByIp(String ip, Integer topicId) {
		CmsVoteRecord record = dao.findByIp(ip, topicId);
		return record != null ? record.getVoteTime() : null;
	}

	public Date lastVoteTimeByCookie(String cookie, Integer topicId) {
		CmsVoteRecord record = dao.findByCookie(cookie, topicId);
		return record != null ? record.getVoteTime() : null;
	}

	@Resource
	private CmsVoteRecordDao dao;
}