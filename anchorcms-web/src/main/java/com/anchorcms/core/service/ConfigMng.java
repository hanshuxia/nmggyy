package com.anchorcms.core.service;

import com.anchorcms.common.email.EmailSender;
import com.anchorcms.common.email.MessageTemplate;
import com.anchorcms.core.model.Config;

import java.util.Map;

public interface ConfigMng {
	public Map<String, String> getMap();

	public Config.ConfigLogin getConfigLogin();

	public EmailSender getEmailSender();

	public MessageTemplate getForgotPasswordMessageTemplate();
	
	public MessageTemplate getRegisterMessageTemplate();

	public String getValue(String id);

	public void updateOrSave(Map<String, String> map);

	public Config updateOrSave(String key, String value);

	public Config deleteById(String id);

	public Config[] deleteByIds(String[] ids);
}