package com.anchorcms.cms.service.main;


import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;

/**
 * 内容审核Manager接口
 * 
 * '内容'数据存在，则'内容审核'数据必须存在。
 */
public interface ContentCheckMng {
	public ContentCheck save(ContentCheck check, Content content);

	public ContentCheck update(ContentCheck bean);
}