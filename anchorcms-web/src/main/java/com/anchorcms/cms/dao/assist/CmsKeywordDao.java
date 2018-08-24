package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsKeyword;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;


public interface CmsKeywordDao {
	public List<CmsKeyword> getList(Integer siteId, boolean onlyEnabled,
									boolean cacheable);

	public List<CmsKeyword> getListGlobal(boolean onlyEnabled, boolean cacheable);

	public CmsKeyword findById(Integer id);

	public CmsKeyword save(CmsKeyword bean);

	public CmsKeyword updateByUpdater(Updater<CmsKeyword> updater);

	public CmsKeyword deleteById(Integer id);
}