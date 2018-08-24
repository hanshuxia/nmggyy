package com.anchorcms.cms.service.main.impl;

import com.anchorcms.cms.dao.main.CmsModelItemDao;
import com.anchorcms.cms.model.main.CmsModelItem;
import com.anchorcms.cms.service.main.CmsModelItemMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.common.hibernate.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CmsModelItemMngImpl implements CmsModelItemMng {
	@Transactional(readOnly = true)
	public List<CmsModelItem> getList(Integer modelId, boolean isChannel,
			boolean hasDisabled) {
		return dao.getList(modelId, isChannel, hasDisabled);
	}

	@Transactional(readOnly = true)
	public CmsModelItem findById(Integer id) {
		CmsModelItem entity = dao.findById(id);
		return entity;
	}

	public CmsModelItem save(CmsModelItem bean, Integer modelId) {
		bean.setModel(cmsModelMng.findById(modelId));
		return save(bean);
	}

	public CmsModelItem save(CmsModelItem bean) {
		bean.init();
		dao.save(bean);
		return bean;
	}

	public void saveList(List<CmsModelItem> list) {
		for (CmsModelItem item : list) {
			save(item);
		}
	}

	public void updatePriority(Integer[] wids, Integer[] priority,
			String[] label, Boolean[] single, Boolean[] display) {
		CmsModelItem item;
		for (int i = 0, len = wids.length; i < len; i++) {
			item = findById(wids[i]);
			item.setLabel(label[i]);
			item.setPriority(priority[i]);
			item.setSingle(single[i]);
			item.setDisplay(display[i]);
		}
	}

	public CmsModelItem update(CmsModelItem bean) {
		Updater<CmsModelItem> updater = new Updater<CmsModelItem>(bean);
		CmsModelItem entity = dao.updateByUpdater(updater);
		entity.emptyToNull();
		return entity;
	}

	public CmsModelItem deleteById(Integer id) {
		CmsModelItem bean = dao.deleteById(id);
		return bean;
	}

	public CmsModelItem[] deleteByIds(Integer[] ids) {
		CmsModelItem[] beans = new CmsModelItem[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsModelMng cmsModelMng;
	private CmsModelItemDao dao;

	@Autowired
	public void setCmsModelMng(CmsModelMng cmsModelMng) {
		this.cmsModelMng = cmsModelMng;
	}

	@Autowired
	public void setDao(CmsModelItemDao dao) {
		this.dao = dao;
	}

}