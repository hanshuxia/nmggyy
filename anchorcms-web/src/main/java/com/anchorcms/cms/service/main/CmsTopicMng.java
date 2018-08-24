package com.anchorcms.cms.service.main;

import com.anchorcms.cms.model.main.CmsTopic;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface CmsTopicMng {

	public List<CmsTopic> getListForTag(Integer channelId, boolean recommend,
										Integer count);

	public Pagination getPageForTag(Integer channelId, boolean recommend,
									int pageNo, int pageSize);

	public Pagination getPage(int pageNo, int pageSize);

	public List<CmsTopic> getListByChannel(Integer channelId);

	public CmsTopic findById(Integer id);

	public CmsTopic save(CmsTopic bean, Integer channelId, Integer[] channelIds);

	public CmsTopic update(CmsTopic bean, Integer channelId, Integer[] channelIds);

	public CmsTopic deleteById(Integer id);

	public CmsTopic[] deleteByIds(Integer[] ids);

	public CmsTopic[] updatePriority(Integer[] ids, Integer[] priority);
}