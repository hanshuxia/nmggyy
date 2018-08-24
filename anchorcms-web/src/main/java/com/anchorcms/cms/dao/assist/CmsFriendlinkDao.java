package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsFriendlink;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;


public interface CmsFriendlinkDao {
	public List<CmsFriendlink> getList(Integer siteId, Integer ctgId,
									   Boolean enabled);

	public int countByCtgId(Integer ctgId);

	public CmsFriendlink findById(Integer id);

	public CmsFriendlink save(CmsFriendlink bean);

	public CmsFriendlink updateByUpdater(Updater<CmsFriendlink> updater);

	public CmsFriendlink deleteById(Integer id);
}