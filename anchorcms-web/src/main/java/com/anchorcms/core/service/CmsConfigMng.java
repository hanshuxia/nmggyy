package com.anchorcms.core.service;

import com.anchorcms.core.model.CmsConfig;
import com.anchorcms.core.model.CmsConfigAttr;
import com.anchorcms.core.model.MarkConfig;
import com.anchorcms.core.model.MemberConfig;

import java.util.Date;
import java.util.Map;


public interface CmsConfigMng {
	public CmsConfig get();
	
	public Integer getContentFreshMinute();

	public void updateCountCopyTime(Date d);

	public void updateCountClearTime(Date d);
	
	public void updateFlowClearTime(Date d);
	
	public void updateChannelCountClearTime(Date d);

	public CmsConfig update(CmsConfig bean);

	public MarkConfig updateMarkConfig(MarkConfig mark);

	public void updateMemberConfig(MemberConfig memberConfig);
	
	public void updateConfigAttr(CmsConfigAttr configAttr);
	
	public void updateSsoAttr(Map<String, String> ssoAttr);
}