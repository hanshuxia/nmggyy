package com.anchorcms.cms.dao.assist.impl;

import java.util.List;

import com.anchorcms.cms.dao.assist.CmsFriendlinkDao;
import com.anchorcms.cms.model.assist.CmsFriendlink;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;


@Repository
public class CmsFriendlinkDaoImpl extends
		HibernateBaseDao<CmsFriendlink, Integer> implements CmsFriendlinkDao {
	@SuppressWarnings("unchecked")
	public List<CmsFriendlink> getList(Integer siteId, Integer ctgId,
			Boolean enabled) {
		Finder f = Finder.create("from CmsFriendlink bean where 1=1");
		if (enabled != null) {
			f.append(" and bean.enabled=:enabled");
			f.setParam("enabled", enabled);
		}
		if (siteId != null) {
			f.append(" and bean.site.siteId=:siteId");
			f.setParam("siteId", siteId);
		}
		if (ctgId != null) {
			f.append(" and bean.category.friendlinkctgId=:ctgId");
			f.setParam("ctgId", ctgId);
		}
		f.append(" order by bean.priority asc");
		f.setCacheable(true);
		return find(f);
	}

	public int countByCtgId(Integer ctgId) {
		String hql = "select count(*) from CmsFriendlink bean where bean.category.friendlinkctgId=:ctgId";
		return ((Number) getSession().createQuery(hql).setParameter("ctgId",
				ctgId).iterate().next()).intValue();
	}

	public CmsFriendlink findById(Integer id) {
		CmsFriendlink entity = get(id);
		return entity;
	}

	public CmsFriendlink save(CmsFriendlink bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsFriendlink deleteById(Integer id) {
		CmsFriendlink entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsFriendlink> getEntityClass() {
		return CmsFriendlink.class;
	}
}