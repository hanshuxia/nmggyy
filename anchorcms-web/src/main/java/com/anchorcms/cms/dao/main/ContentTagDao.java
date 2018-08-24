package com.anchorcms.cms.dao.main;

import com.anchorcms.cms.model.main.ContentTag;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface ContentTagDao {
	public List<ContentTag> getList(Integer count, boolean cacheable);

	public Pagination getPage(String name, int pageNo, int pageSize,
							  boolean cacheable);

	public ContentTag findById(Integer id);

	public ContentTag findByName(String name, boolean cacheable);

	public ContentTag save(ContentTag bean);

	public ContentTag updateByUpdater(Updater<ContentTag> updater);

	public ContentTag deleteById(Integer id);

	public int deleteContentRef(Integer id);

	public int countContentRef(Integer id);
}