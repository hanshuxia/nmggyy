package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsSiteAccessCount;
import com.anchorcms.common.hibernate.Updater;

import java.util.Date;
import java.util.List;

/**
 * @author Tom
 */
public interface CmsSiteAccessCountDao {

	public List<Object[]> statisticVisitorCountByDate(Integer siteId, Date begin, Date end);
	
	public List<Object[]> statisticVisitorCountByYear(Integer siteId, Integer year);

	public CmsSiteAccessCount save(CmsSiteAccessCount count);

	public CmsSiteAccessCount updateByUpdater(Updater<CmsSiteAccessCount> updater);

}
