package com.anchorcms.icloud.dao.commservice;


import com.anchorcms.icloud.model.commservice.SBidNotice;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 中标公告Dao
 */
public interface SBidNoticeDao {

	/**
	 * @author hansx
	 * @Date 2017/1/13 0013 上午 11:38
	 * @return
	 *  根据id获取中标公告信息
	 */
	public SBidNotice findById(int id);
}