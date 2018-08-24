package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsAcquisitionTemp;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;

public interface CmsAcquisitionTempDao {
	public List<CmsAcquisitionTemp> getList(Integer siteId);

	public CmsAcquisitionTemp findById(Integer id);

	public CmsAcquisitionTemp save(CmsAcquisitionTemp bean);

	public CmsAcquisitionTemp updateByUpdater(Updater<CmsAcquisitionTemp> updater);

	public CmsAcquisitionTemp deleteById(Integer id);
	
	public Integer getPercent(Integer siteId);
	
	public void clear(Integer siteId, String channelUrl);
}