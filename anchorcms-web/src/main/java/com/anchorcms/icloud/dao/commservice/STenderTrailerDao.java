package com.anchorcms.icloud.dao.commservice;


import com.anchorcms.icloud.model.commservice.STenderTrailer;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 招标预告Dao
 */
public interface STenderTrailerDao {

	/**
	 * @author hansx
	 * @Date 2017/1/13 0013 上午 11:38
	 * @return
	 *  根据id获取招标预告信息
	 */
	public STenderTrailer findById(int id);
	/**
	 * @author gengwenlong
	 * @Date 2017/1/14 18:01
	 * @return
	 * 发布招标预告
	 */
	public STenderTrailer relese(STenderTrailer stt);

}