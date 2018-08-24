package com.anchorcms.cms.dao.main;


import com.anchorcms.cms.model.main.ChannelExt;
import com.anchorcms.common.hibernate.Updater;

public interface ChannelExtDao {
	public ChannelExt save(ChannelExt bean);

	public ChannelExt updateByUpdater(Updater<ChannelExt> updater);
}