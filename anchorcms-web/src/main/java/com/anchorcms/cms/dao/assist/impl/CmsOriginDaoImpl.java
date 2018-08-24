package com.anchorcms.cms.dao.assist.impl;

import com.anchorcms.cms.dao.assist.CmsOriginDao;
import com.anchorcms.cms.model.assist.CmsOrigin;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public class CmsOriginDaoImpl extends HibernateBaseDao<CmsOrigin, Integer> implements CmsOriginDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Finder f= Finder.create("from CmsOrigin origin");
		Pagination page = find(f, pageNo, pageSize);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	public List<CmsOrigin> getList(String name){
		Finder f=Finder.create("from CmsOrigin origin ");
		if(StringUtils.isNotBlank(name)){
			f.append(" where origin.originName like :name").setParam("name", "%"+name+"%");
		}
		return find(f);
	}

	public CmsOrigin findById(Integer id) {
		CmsOrigin entity = get(id);
		return entity;
	}

	public CmsOrigin save(CmsOrigin bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsOrigin deleteById(Integer id) {
		CmsOrigin entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsOrigin> getEntityClass() {
		return CmsOrigin.class;
	}
}