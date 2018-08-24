package com.anchorcms.common.page.staticpage.impl;

import com.anchorcms.common.page.staticpage.StaticPageException;

/**
 * 
 * 模板未找到异常
 * 
 */
public class TemplateNotFoundException extends StaticPageException {
	public TemplateNotFoundException() {
	}

	public TemplateNotFoundException(String msg, Integer generated, String errorTitle) {
		super(msg, generated, errorTitle);
	}
}
