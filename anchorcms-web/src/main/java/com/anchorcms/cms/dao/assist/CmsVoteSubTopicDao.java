package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsVoteSubTopic;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;


public interface CmsVoteSubTopicDao {
	public List<CmsVoteSubTopic> findByVoteTopic(Integer voteTopicId);
	
	public CmsVoteSubTopic findById(Integer id);

	public CmsVoteSubTopic save(CmsVoteSubTopic bean);

	public CmsVoteSubTopic updateByUpdater(Updater<CmsVoteSubTopic> updater);

	public CmsVoteSubTopic deleteById(Integer id);
}