package com.anchorcms.cms.service.main.impl;

import com.anchorcms.cms.dao.main.ChannelTxtDao;
import com.anchorcms.cms.service.main.ChannelTxtMng;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.ChannelTxt;
import com.anchorcms.common.hibernate.Updater;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * 栏目文本Manager实现
 */
@Service
@Transactional
public class ChannelTxtMngImpl implements ChannelTxtMng {
	/**
	 * @see ChannelTxtMng#save(ChannelTxt, Channel)
	 */
	public ChannelTxt save(ChannelTxt txt, Channel channel) {
		if (isAllBlank(txt)) {
			return null;
		} else {
			txt.setChannel(channel);
			txt.init();
			dao.save(txt);
			return txt;
		}
	}

	/**
	 * @see ChannelTxtMng#update(ChannelTxt, Channel)
	 */
	public ChannelTxt update(ChannelTxt txt, Channel channel) {
		ChannelTxt entity = dao.findById(channel.getChannelId());
		if (entity == null) {
			entity = save(txt, channel);
			channel.getChannelTxtSet().add(entity);
			return entity;
		} else {
			if (isAllBlank(txt)) {
				channel.getChannelTxtSet().clear();
				return null;
			} else {
				Updater<ChannelTxt> updater = new Updater<ChannelTxt>(txt);
				entity = dao.updateByUpdater(updater);
				entity.blankToNull();
				return entity;
			}
		}
	}
	/**
	 * @Author 阁楼麻雀
	 * @Date 2016-11-5 14:28
	 * @Desc 判断是否为空
	 */
	public boolean isAllBlank(ChannelTxt txt){
		boolean flag = false;
		if(StringUtils.isBlank(txt.getTxt()) && StringUtils.isBlank(txt.getTxt1())
				&& StringUtils.isBlank(txt.getTxt2())
				&& StringUtils.isBlank(txt.getTxt3())){
			flag = true;
		}
		return flag;
	}
	@Resource
	private ChannelTxtDao dao;
}