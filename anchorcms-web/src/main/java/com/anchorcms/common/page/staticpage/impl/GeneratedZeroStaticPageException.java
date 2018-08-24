package com.anchorcms.common.page.staticpage.impl;

import com.anchorcms.common.page.staticpage.StaticPageException;

/**
 * 
 * 未生成静态页异常
 * 
 */
public class GeneratedZeroStaticPageException extends StaticPageException {
	public GeneratedZeroStaticPageException() {
	}

	public GeneratedZeroStaticPageException(String msg) {
		super(msg, 0);
	}
}
