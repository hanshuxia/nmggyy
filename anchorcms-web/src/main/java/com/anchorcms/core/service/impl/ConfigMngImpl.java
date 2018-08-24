package com.anchorcms.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.anchorcms.common.email.EmailSender;
import com.anchorcms.common.email.MessageTemplate;
import com.anchorcms.core.dao.ConfigDao;
import com.anchorcms.core.model.Config;
import com.anchorcms.core.model.Config.ConfigLogin;
import com.anchorcms.core.model.Config.ConfigEmailSender;
import com.anchorcms.core.model.Config.ConfigMessageTemplate;
import com.anchorcms.core.service.ConfigMng;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class ConfigMngImpl implements ConfigMng {
	@Transactional(readOnly = true)
	public Map<String, String> getMap() {
		List<Config> list = dao.getList();
		Map<String, String> map = new HashMap<String, String>(list.size());
		for (Config config : list) {
			map.put(config.getCfgKey(), config.getCfgValue());
		}
		return map;
	}

	@Transactional(readOnly = true)
	public String getValue(String id) {
		Config entity = dao.findById(id);
		if (entity != null) {
			return entity.getCfgValue();
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	public ConfigLogin getConfigLogin() {
		return ConfigLogin.create(getMap());
	}

	@Transactional(readOnly = true)
	public EmailSender getEmailSender() {
		return ConfigEmailSender.create(getMap());
	}

	@Transactional(readOnly = true)
	public MessageTemplate getForgotPasswordMessageTemplate() {
		return ConfigMessageTemplate.createForgotPasswordMessageTemplate(getMap());
	}
	
	@Transactional(readOnly = true)
	public MessageTemplate getRegisterMessageTemplate() {
		return ConfigMessageTemplate.createRegisterMessageTemplate(getMap());
	}

	public void updateOrSave(Map<String, String> map) {
		if (map != null && map.size() > 0) {
			for (Entry<String, String> entry : map.entrySet()) {
				updateOrSave(entry.getKey(), entry.getValue());
			}
		}
	}

	public Config updateOrSave(String key, String value) {
		Config config = dao.findById(key);
		if (config != null) {
			config.setCfgValue(value);
		} else {
			config = new Config(key);
			config.setCfgValue(value);
			dao.save(config);
		}
		return config;
	}

	public Config deleteById(String id) {
		Config bean = dao.deleteById(id);
		return bean;
	}

	public Config[] deleteByIds(String[] ids) {
		Config[] beans = new Config[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Resource
	private ConfigDao dao;

	public ConfigDao getDao() {
		return dao;
	}

	public void setDao(ConfigDao dao) {
		this.dao = dao;
	}
}