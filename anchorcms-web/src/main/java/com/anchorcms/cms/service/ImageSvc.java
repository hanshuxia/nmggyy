package com.anchorcms.cms.service;


import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.Ftp;

/**
 * @author Tom
 */
public interface ImageSvc {
	/**
	 * 抓取远程图片返回本地地址
	 * @param imgUrl 远程图片URL
	 * @return
	 */
	public String crawlImg(String imgUrl, CmsSite site);
	
	public String crawlImg(String imgUrl, String ctx, Ftp ftp, String uploadPath);
}
