package com.anchorcms.core.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.anchorcms.cms.service.main.ContentQueryFreshTimeCache;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.dao.CmsConfigDao;
import com.anchorcms.core.service.CmsConfigMng;
import com.anchorcms.core.model.CmsConfig;
import com.anchorcms.core.model.CmsConfigAttr;
import com.anchorcms.core.model.MarkConfig;
import com.anchorcms.core.model.MemberConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CmsConfigMngImpl implements CmsConfigMng {
	@Transactional(readOnly = true)
	public CmsConfig get() {
		CmsConfig entity = dao.findById(1);
		return entity;
	}
	
	@Transactional(readOnly = true)
	public Integer getContentFreshMinute() {
		CmsConfig entity = get();
		return entity.getContentFreshMinute();
	}

	public void updateCountCopyTime(Date d) {
		dao.findById(1).setCountCopyTime(d);
	}

	public void updateCountClearTime(Date d) {
		dao.findById(1).setCountClearTime(d);
	}
	
	public void updateFlowClearTime(Date d) {
		dao.findById(1).setFlowClearTime(d);
	}
	
	public void updateChannelCountClearTime(Date d) {
		dao.findById(1).setChannelCountClearTime(d);
	}


	public CmsConfig update(CmsConfig bean) {
		Updater<CmsConfig> updater = new Updater<CmsConfig>(bean);
		CmsConfig entity = dao.updateByUpdater(updater);
		entity.blankToNull();
		return entity;
	}

	public MarkConfig updateMarkConfig(MarkConfig mark) {
		get().setMarkConfig(mark);
		return mark;
	}

	public void updateMemberConfig(MemberConfig memberConfig) {
		get().getAttr().putAll(memberConfig.getAttr());
	}

	public void updateConfigAttr(CmsConfigAttr configAttr) {
		get().getAttr().putAll(configAttr.getAttr());
		contentQueryFreshTimeCache.setInterval(getContentFreshMinute());
	}
	
	public void updateSsoAttr(Map<String,String> ssoAttr){
		Map<String,String> oldAttr=get().getAttr();
		Iterator<String> keys = oldAttr.keySet().iterator();
	    String key = null;
	    while (keys.hasNext()) {
		    key = (String) keys.next();
		    if (key.startsWith("sso_")){
		      keys.remove();
		     }
	    }
		oldAttr.putAll(ssoAttr);
	}

	@Resource
	private CmsConfigDao dao;
	@Resource
	private ContentQueryFreshTimeCache contentQueryFreshTimeCache;
}