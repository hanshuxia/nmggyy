package com.anchorcms.common.page.staticpage.impl;

import com.anchorcms.common.page.staticpage.StaticPageException;

/**
 * 
 * 内容未审核异常
 *
 */
public class ContentNotCheckedException extends StaticPageException {
	public ContentNotCheckedException() {
	}

	public ContentNotCheckedException(String msg, Integer generated,
			String errorTitle) {
		super(msg, generated, errorTitle);
	}
}
