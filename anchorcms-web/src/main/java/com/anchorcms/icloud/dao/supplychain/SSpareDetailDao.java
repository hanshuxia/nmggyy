package com.anchorcms.icloud.dao.supplychain;


import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareStorage;

/**
 * Created by hansx on 2016/12/20.
 * 备品备件Dao
 */
public interface SSpareDetailDao {

	/**
	 * 根据id获取备品备件信息
	 * @param id
	 * @return
	 */
	public SSpare findById(String id);


	/**
	 * @author hansx 2016/6/9
	 * @return 库存记录保存
	 */
	public void save(SSpareStorage sSpareStorage);

	/**
	 * @author hansx 2016/6/9
	 * @return 备品备件库存数量修改
	 */
	public void updateInvnetCount(SSpare sSpare);

}