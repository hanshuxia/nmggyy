package com.anchorcms.cms.dao.main.impl;

import java.util.List;

import com.anchorcms.cms.dao.main.CmsModelDao;
import com.anchorcms.cms.model.main.CmsModel;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;


@Repository
public class CmsModelDaoImpl extends HibernateBaseDao<CmsModel, Integer>
		implements CmsModelDao {
	@SuppressWarnings("unchecked")
	public List<CmsModel> getList(boolean containDisabled,Boolean hasContent,Integer siteId) {
		Finder f = Finder.create("select bean from CmsModel bean where "
				+ "(bean.isGlobal=true or bean.site.id=:siteId)").setParam("siteId", siteId);
		if (!containDisabled) {
			f.append(" and bean.isDisabled=false");
		}
		if(hasContent!=null){
			if(hasContent){
				f.append(" and bean.hasContent=true");
			}else{
				f.append(" and bean.hasContent=false");
			}
		}
		f.append(" order by bean.priority");
		return find(f);
	}

	@SuppressWarnings("unchecked")
	public CmsModel getDefModel() {
		String hql = "from CmsModel bean where bean.isDef = isDef";
		List<CmsModel> list = getSession().createQuery(hql).setMaxResults(1)
				.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public CmsModel findById(Integer id) {
		CmsModel entity = get(id);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public CmsModel findByPath(String path){
		String hql = "from CmsModel bean where bean.modelPath=:modelPath";
		List<CmsModel> list = getSession().createQuery(hql).setParameter("modelPath", path).setMaxResults(1).list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public CmsModel save(CmsModel bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsModel deleteById(Integer id) {
		CmsModel entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsModel> getEntityClass() {
		return CmsModel.class;
	}
}