package com.anchorcms.cms.dao.main.impl;

import com.anchorcms.cms.dao.main.ChannelExtDao;
import com.anchorcms.cms.model.main.ChannelExt;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;


@Repository
public class ChannelExtDaoImpl extends HibernateBaseDao<ChannelExt, Integer>
		implements ChannelExtDao {
	public ChannelExt save(ChannelExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ChannelExt> getEntityClass() {
		return ChannelExt.class;
	}
}