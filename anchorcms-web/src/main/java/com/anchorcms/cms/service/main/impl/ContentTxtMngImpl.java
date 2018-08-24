package com.anchorcms.cms.service.main.impl;

import com.anchorcms.cms.dao.main.ContentTxtDao;
import com.anchorcms.cms.service.main.ContentTxtMng;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.hibernate.Updater;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class ContentTxtMngImpl implements ContentTxtMng {
	public ContentTxt save(ContentTxt txt, Content content) {
		if (isAllBlank(txt)) {
			return null;
		} else {
			txt.setContent(content);
			txt.init();
			dao.save(txt);
			content.setContentTxt(txt);
			return txt;
		}
	}

	public ContentTxt update(ContentTxt txt, Content content) {
		ContentTxt entity = dao.findById(content.getContentId());
		if (entity == null) {
			entity = save(txt, content);
			content.getContentTxtSet().add(entity);
			return entity;
		} else {
			if (isAllBlank(txt)) {
				content.getContentTxtSet().clear();
				return null;
			} else {
				Updater<ContentTxt> updater = new Updater<ContentTxt>(txt);
				entity = dao.updateByUpdater(updater);
				entity.blankToNull();
				return entity;
			}
		}
	}
	/**
	 * @Author 阁楼麻雀
	 * @Date 2016-11-3 16:39
	 * @Desc 判断是否为空
	 */

	public boolean isAllBlank(ContentTxt txt){
		boolean flag = false;
		if(StringUtils.isBlank(txt.getTxt()) && StringUtils.isBlank(txt.getTxt1())
				&& StringUtils.isBlank(txt.getTxt2())
				&& StringUtils.isBlank(txt.getTxt3())){
			flag = true;
		}

		return flag;
	}

	@Resource
	private ContentTxtDao dao;
}