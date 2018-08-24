package com.anchorcms.icloud.dao.commservice;


import com.anchorcms.icloud.model.commservice.STenderNotice;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 招标公告Dao
 */
public interface STenderNoticeDao {

	/**
	 * @author hansx
	 * @Date 2017/1/13 0013 上午 11:38
	 * @return
	 *  根据id获取招标公告信息
	 */
	public STenderNotice findById(int id);
	/**
	 * @author gengwenlong
	 * @Date 2017/1/14 23:31
	 * @return
	 * 发布招标公告
	 */
	public STenderNotice tenderNoticeRelese(STenderNotice stn);
}