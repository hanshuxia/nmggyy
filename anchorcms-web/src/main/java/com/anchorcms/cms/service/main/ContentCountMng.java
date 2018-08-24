package com.anchorcms.cms.service.main;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCount;
import net.sf.ehcache.Ehcache;


/**
 * 内容计数Manager接口
 * 
 * '内容'数据存在则'内容计数'数据必须存在。
 */
public interface ContentCountMng {

	public int contentUp(Integer id);

	public int contentDown(Integer id);

	public void downloadCount(Integer contentId);

	public void commentCount(Integer contentId);

	public int freshCacheToDB(Ehcache cache);

	public ContentCount findById(Integer id);

	public ContentCount save(ContentCount count, Content content);

	public ContentCount update(ContentCount bean);
}