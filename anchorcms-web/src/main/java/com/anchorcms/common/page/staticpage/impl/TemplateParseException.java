package com.anchorcms.common.page.staticpage.impl;

import com.anchorcms.common.page.staticpage.StaticPageException;

/**
 * 
 * 模板解析异常
 * 
 */
public class TemplateParseException extends StaticPageException {
	public TemplateParseException() {
	}

	public TemplateParseException(String msg, Integer generated, String errorTitle) {
		super(msg, generated, errorTitle);
	}
}
