package com.anchorcms.cms.service.assist;

import com.anchorcms.cms.model.assist.CmsVoteItem;
import com.anchorcms.cms.model.assist.CmsVoteSubTopic;
import com.anchorcms.cms.model.assist.CmsVoteTopic;

import java.util.Collection;
import java.util.List;
import java.util.Set;


public interface CmsVoteSubTopicMng {

	public CmsVoteSubTopic findById(Integer id);
	
	public List<CmsVoteSubTopic> findByVoteTopic(Integer voteTopicId);
	
	public CmsVoteTopic save(CmsVoteTopic bean, Set<CmsVoteSubTopic> subTopics);

	public CmsVoteSubTopic save(CmsVoteSubTopic bean, List<CmsVoteItem> items);

	public CmsVoteSubTopic update(CmsVoteSubTopic bean, Collection<CmsVoteItem> items);
	
	public CmsVoteTopic update(CmsVoteTopic bean, Collection<CmsVoteSubTopic> subTopics);
	
	public Collection<CmsVoteSubTopic> update(Collection<CmsVoteSubTopic> subTopics, CmsVoteTopic topic);

	public CmsVoteSubTopic deleteById(Integer id);

	public CmsVoteSubTopic[] deleteByIds(Integer[] ids);

}