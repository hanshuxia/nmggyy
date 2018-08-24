package com.anchorcms.cms.dao.main.impl;

import com.anchorcms.cms.dao.main.CmsModelItemDao;
import com.anchorcms.cms.model.main.CmsModelItem;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CmsModelItemDaoImpl extends
		HibernateBaseDao<CmsModelItem, Integer> implements CmsModelItemDao {
	@SuppressWarnings("unchecked")
	public List<CmsModelItem> getList(Integer modelId, boolean isChannel,
			boolean hasDisabled) {
		StringBuilder sb = new StringBuilder(
				"from CmsModelItem bean where bean.model.modelId=? and bean.isChannel=?");
		if (!hasDisabled) {
			sb.append(" and bean.isDisplay=true");
		}
		sb.append(" order by bean.priority asc,bean.modelitemId asc");
		return find(sb.toString(), modelId, isChannel);
	}

	public CmsModelItem findById(Integer id) {
		CmsModelItem entity = get(id);
		return entity;
	}

	public CmsModelItem save(CmsModelItem bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsModelItem deleteById(Integer id) {
		CmsModelItem entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsModelItem> getEntityClass() {
		return CmsModelItem.class;
	}
}