package com.anchorcms.cms.dao.main;

import com.anchorcms.cms.model.main.CmsTopic;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface CmsTopicDao {
	public List<CmsTopic> getList(Integer channelId, boolean recommend,
								  Integer count, boolean cacheable);

	public Pagination getPage(Integer channelId, boolean recommend, int pageNo,
							  int pageSize, boolean cacheable);

	public List<CmsTopic> getGlobalTopicList();

	public List<CmsTopic> getListByChannelId(Integer channelId);

	public List<CmsTopic> getListByChannelIds(Integer[] channelIds);

	public CmsTopic findById(Integer id);

	public CmsTopic save(CmsTopic bean);

	public CmsTopic updateByUpdater(Updater<CmsTopic> updater);

	public CmsTopic deleteById(Integer id);

	public int deleteContentRef(Integer id);

	public int countByChannelId(Integer channelId);
}