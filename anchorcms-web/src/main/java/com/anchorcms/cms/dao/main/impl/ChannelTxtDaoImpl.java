package com.anchorcms.cms.dao.main.impl;

import com.anchorcms.cms.dao.main.ChannelTxtDao;
import com.anchorcms.cms.model.main.ChannelTxt;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;


@Repository
public class ChannelTxtDaoImpl extends HibernateBaseDao<ChannelTxt, Integer>
		implements ChannelTxtDao {
	public ChannelTxt findById(Integer id) {
		ChannelTxt entity = get(id);
		return entity;
	}

	public ChannelTxt save(ChannelTxt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ChannelTxt> getEntityClass() {
		return ChannelTxt.class;
	}
}