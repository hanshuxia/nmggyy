package com.anchorcms.cms.dao.main;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;

import java.util.List;


public interface ChannelDao {
	public List getTopList(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable);

	public Pagination getTopPage(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

	public List<Channel> getTopListByRigth(Integer userId, Integer siteId, boolean hasContentOnly);
	
	public List<Channel> getChildList(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable);
	
	public List<Channel> getBottomList(Integer siteId, boolean hasContentOnly);

	public Pagination getChildPage(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

	public List<Channel> getChildListByRight(Integer userId, Integer parentId, boolean hasContentOnly);

	public Channel findByPath(String path, Integer siteId, boolean cacheable);

	public Channel findById(Integer id);

	public Channel save(Channel bean);

	public Channel updateByUpdater(Updater<Channel> updater);

	public Channel deleteById(Integer id);
}