package com.anchorcms.cms.service.assist.impl;

import com.anchorcms.cms.dao.assist.CmsConsultExtDao;
import com.anchorcms.cms.model.assist.CmsConsult;
import com.anchorcms.cms.model.assist.CmsConsultExt;
import com.anchorcms.cms.service.assist.CmsConsultExtMng;
import com.anchorcms.common.hibernate.Updater;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CmsConsultExtMngImpl implements CmsConsultExtMng {
	public CmsConsultExt save(String ip, String text, CmsConsult consult) {
		CmsConsultExt ext = new CmsConsultExt();
		ext.setText(text);
		ext.setIp(ip);
		ext.setConsult(consult);
		consult.setConsultExt(ext);
		dao.save(ext);
		return ext;
	}

	public CmsConsultExt update(CmsConsultExt bean) {
		Updater<CmsConsultExt> updater = new Updater<CmsConsultExt>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public int deleteByContentId(Integer contentId) {
		return dao.deleteByContentId(contentId);
	}

	@Resource
	private CmsConsultExtDao dao;

}