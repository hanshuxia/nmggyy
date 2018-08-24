package com.anchorcms.cms.dao.main.impl;

import java.util.List;

import com.anchorcms.cms.dao.main.CmsWebserviceDao;
import com.anchorcms.cms.model.assist.CmsWebservice;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import org.springframework.stereotype.Repository;

@Repository
public class CmsWebserviceDaoImpl extends HibernateBaseDao<CmsWebservice, Integer> implements CmsWebserviceDao {
	public Pagination getPage(int pageNo, int pageSize) {
		String hql="from CmsWebservice bean";
		Finder f=Finder.create(hql);
		return find(f, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<CmsWebservice> getList(String type){
		String hql="from CmsWebservice bean where bean.serviceType=:type";
		Finder f =Finder.create(hql).setParam("type", type);
		f.setCacheable(true);
		return find(f);
	}

	public CmsWebservice findById(Integer id) {
		CmsWebservice entity = get(id);
		return entity;
	}

	public CmsWebservice save(CmsWebservice bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsWebservice deleteById(Integer id) {
		CmsWebservice entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsWebservice> getEntityClass() {
		return CmsWebservice.class;
	}
}