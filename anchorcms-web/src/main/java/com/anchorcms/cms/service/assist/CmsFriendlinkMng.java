package com.anchorcms.cms.service.assist;

import com.anchorcms.cms.model.assist.CmsFriendlink;

import java.util.List;


public interface CmsFriendlinkMng {
	public List<CmsFriendlink> getList(Integer siteId, Integer ctgId,
									   Boolean enabled);

	public int countByCtgId(Integer ctgId);

	public CmsFriendlink findById(Integer id);

	public int updateViews(Integer id);

	public CmsFriendlink save(CmsFriendlink bean, Integer ctgId);

	public CmsFriendlink update(CmsFriendlink bean, Integer ctgId);

	public void updatePriority(Integer[] ids, Integer[] priorities);

	public CmsFriendlink deleteById(Integer id);

	public CmsFriendlink[] deleteByIds(Integer[] ids);
}