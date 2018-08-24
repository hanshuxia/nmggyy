package com.anchorcms.icloud.service.supplychain;


import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareStorage;

/**
 * Created by hansx on 2016/12/20.
 *
 *备品备件信息业务管理
 */
public interface SSpareDetailMng {


	/**
	 * 根据id获取备品备件信息
	 * @param id
	 * @return
	 */
	public SSpare findById(String id);

	/**
	 * @Author hansx  2016/6/9
	 * @Desc 保存出入库信息
	 */
	public void save(SSpareStorage sSpareStorage);

	/**
	 * @Author hansx  2016/6/9
	 * @Desc 根据id修改备品备件库存数量
	 */
	public void updateInvnetCount(SSpare sSpare);

}