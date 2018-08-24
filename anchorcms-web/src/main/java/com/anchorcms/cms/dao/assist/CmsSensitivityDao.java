package com.anchorcms.cms.dao.assist;

import com.anchorcms.cms.model.assist.CmsSensitivity;
import com.anchorcms.common.hibernate.Updater;

import java.util.List;


public interface CmsSensitivityDao {
	public List<CmsSensitivity> getList(boolean cacheable);

	public CmsSensitivity findById(Integer id);

	public CmsSensitivity save(CmsSensitivity bean);

	public CmsSensitivity updateByUpdater(Updater<CmsSensitivity> updater);

	public CmsSensitivity deleteById(Integer id);
}