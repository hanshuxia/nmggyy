package com.anchorcms.cms.service.main;


import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.ChannelExt;

public interface ChannelExtMng {
	public ChannelExt save(ChannelExt ext, Channel channel);

	public ChannelExt update(ChannelExt ext);
}