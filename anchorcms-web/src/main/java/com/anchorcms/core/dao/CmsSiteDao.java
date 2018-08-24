package com.anchorcms.core.dao;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.model.CmsSite;

import java.util.List;


/**
 * 站点DAO接口
 */
public interface CmsSiteDao {
	/**
	 * 获得站点数量
	 * 
	 * @param cacheable
	 * @return
	 */
	public int siteCount(boolean cacheable);

	/**
	 * 获得所有站点
	 * 
	 * @param cacheable
	 * @return
	 */
	public List<CmsSite> getList(boolean cacheable);
	
	public int  getCountByProperty(String property);
	
	public CmsSite findByDomain(String domain);

	public CmsSite findById(Integer id);

	public CmsSite save(CmsSite bean);

	public CmsSite updateByUpdater(Updater<CmsSite> updater);

	public CmsSite deleteById(Integer id);

	
}