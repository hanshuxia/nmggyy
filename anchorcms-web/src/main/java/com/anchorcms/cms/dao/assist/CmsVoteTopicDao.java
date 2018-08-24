package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsVoteTopic;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface CmsVoteTopicDao {
	public Pagination getPage(Integer siteId, int pageNo, int pageSize);
	
	public List<CmsVoteTopic> getList(Boolean def, Integer siteId, int count);

	public CmsVoteTopic getDefTopic(Integer siteId);

	public CmsVoteTopic findById(Integer id);

	public CmsVoteTopic save(CmsVoteTopic bean);

	public CmsVoteTopic updateByUpdater(Updater<CmsVoteTopic> updater);

	public CmsVoteTopic deleteById(Integer id);
}