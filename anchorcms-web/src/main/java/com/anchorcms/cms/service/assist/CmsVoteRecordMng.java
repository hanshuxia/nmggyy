package com.anchorcms.cms.service.assist;

import com.anchorcms.cms.model.assist.CmsVoteRecord;
import com.anchorcms.cms.model.assist.CmsVoteTopic;
import com.anchorcms.core.model.CmsUser;

import java.util.Date;


public interface CmsVoteRecordMng {
	public CmsVoteRecord save(CmsVoteTopic topic, CmsUser user, String ip,
							  String cookie);

	public int deleteByTopic(Integer topicId);

	public Date lastVoteTimeByUserId(Integer userId, Integer topicId);

	public Date lastVoteTimeByIp(String ip, Integer topicId);

	public Date lastVoteTimeByCookie(String cookie, Integer topicId);
}