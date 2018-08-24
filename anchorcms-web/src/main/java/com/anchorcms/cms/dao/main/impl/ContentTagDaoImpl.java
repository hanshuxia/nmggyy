package com.anchorcms.cms.dao.main.impl;

import java.util.List;

import com.anchorcms.cms.dao.main.ContentTagDao;
import com.anchorcms.cms.model.main.ContentTag;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;


@Repository
public class ContentTagDaoImpl extends HibernateBaseDao<ContentTag, Integer>
		implements ContentTagDao {
	@SuppressWarnings("unchecked")
	public List<ContentTag> getList(Integer count, boolean cacheable) {
		String hql = "from ContentTag bean order by bean.refCounter desc";
		Query query = getSession().createQuery(hql);
		if (count != null) {
			query.setMaxResults(count);
		}
		query.setCacheable(cacheable);
		return query.list();
	}

	public Pagination getPage(String name, int pageNo, int pageSize,
							  boolean cacheable) {
		Finder f = Finder.create("from ContentTag bean");
		if (!StringUtils.isBlank(name)) {
			f.append(" where bean.tagName like :name");
			f.setParam("name", "%" + name + "%");
		}
		f.append(" order by bean.refCounter desc");
		f.setCacheable(cacheable);
		return find(f, pageNo, pageSize);
	}

	public ContentTag findById(Integer id) {
		ContentTag entity = get(id);
		return entity;
	}

	public ContentTag findByName(String name, boolean cacheable) {
		String hql = "from ContentTag bean where bean.tagName=:name";
		return (ContentTag) getSession().createQuery(hql).setParameter("name",
				name).setCacheable(cacheable).uniqueResult();
	}

	public ContentTag save(ContentTag bean) {
		getSession().save(bean);
		return bean;
	}

	public ContentTag deleteById(Integer id) {
		ContentTag entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public int deleteContentRef(Integer id) {
		Query query = getSession().getNamedQuery("ContentTag.deleteContentRef");
		return query.setParameter(0, id).executeUpdate();
	}

	public int countContentRef(Integer id) {
		Query query = getSession().getNamedQuery("ContentTag.countContentRef");
		return ((Number) (query.setParameter(0, id).list().iterator().next()))
				.intValue();
	}

	@Override
	protected Class<ContentTag> getEntityClass() {
		return ContentTag.class;
	}
}