package com.anchorcms.cms.dao.main;


import com.anchorcms.cms.model.main.ChannelTxt;
import com.anchorcms.common.hibernate.Updater;

public interface ChannelTxtDao {
	public ChannelTxt findById(Integer id);

	public ChannelTxt save(ChannelTxt bean);

	public ChannelTxt updateByUpdater(Updater<ChannelTxt> updater);
}