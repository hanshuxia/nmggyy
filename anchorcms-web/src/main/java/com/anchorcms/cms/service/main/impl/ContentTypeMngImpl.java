package com.anchorcms.cms.service.main.impl;

import java.util.List;

import com.anchorcms.cms.dao.main.ContentTypeDao;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.cms.model.main.ContentType;
import com.anchorcms.common.hibernate.Updater;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class ContentTypeMngImpl implements ContentTypeMng {
	@Transactional(readOnly = true)
	public List<ContentType> getList(boolean containDisabled) {
		return dao.getList(containDisabled);
	}

	@Transactional(readOnly = true)
	public ContentType getDef() {
		return dao.getDef();
	}

	@Transactional(readOnly = true)
	public ContentType findById(Integer id) {
		ContentType entity = dao.findById(id);
		return entity;
	}

	public ContentType save(ContentType bean) {
		dao.save(bean);
		return bean;
	}

	public ContentType update(ContentType bean) {
		Updater<ContentType> updater = new Updater<ContentType>(bean);
		ContentType entity = dao.updateByUpdater(updater);
		return entity;
	}

	public ContentType deleteById(Integer id) {
		ContentType bean = dao.deleteById(id);
		return bean;
	}

	public ContentType[] deleteByIds(Integer[] ids) {
		ContentType[] beans = new ContentType[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Resource
	private ContentTypeDao dao;
}