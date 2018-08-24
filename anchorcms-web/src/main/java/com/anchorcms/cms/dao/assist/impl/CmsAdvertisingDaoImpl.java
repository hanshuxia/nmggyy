package com.anchorcms.cms.dao.assist.impl;

import java.util.List;

import com.anchorcms.cms.dao.assist.CmsAdvertisingDao;
import com.anchorcms.cms.model.assist.CmsAdvertising;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import org.springframework.stereotype.Repository;


@Repository
public class CmsAdvertisingDaoImpl extends
		HibernateBaseDao<CmsAdvertising, Integer> implements CmsAdvertisingDao {
	public Pagination getPage(Integer siteId, Integer adspaceId,
							  Boolean enabled, int pageNo, int pageSize) {
		Finder f = Finder.create("from CmsAdvertising bean where 1=1");
		if (siteId != null && adspaceId == null) {
			f.append(" and bean.site.siteId=:siteId");
			f.setParam("siteId", siteId);
		} else if (adspaceId != null) {
			f.append(" and bean.adspace.adspaceId=:adspaceId");
			f.setParam("adspaceId", adspaceId);
		}
		if (enabled != null) {
			f.append(" and bean.isEnabled=:enabled");
			f.setParam("enabled", enabled);
		}
		f.append(" order by bean.advertisingId desc");
		return find(f, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<CmsAdvertising> getList(Integer adspaceId, Boolean enabled) {
		Finder f = Finder.create("from CmsAdvertising bean where 1=1");
		if (adspaceId != null) {
			f.append(" and bean.adspace.adspaceId=:adspaceId");
			f.setParam("adspaceId", adspaceId);
		}
		if (enabled != null) {
			f.append(" and bean.isEnabled=:enabled");
			f.setParam("enabled", enabled);
		}
		return find(f);
	}

	public CmsAdvertising findById(Integer id) {
		CmsAdvertising entity = get(id);
		return entity;
	}

	public CmsAdvertising save(CmsAdvertising bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsAdvertising deleteById(Integer id) {
		CmsAdvertising entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsAdvertising> getEntityClass() {
		return CmsAdvertising.class;
	}
}