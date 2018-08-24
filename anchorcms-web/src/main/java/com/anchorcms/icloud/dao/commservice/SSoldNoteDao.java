package com.anchorcms.icloud.dao.commservice;


import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.commservice.SSoldNote;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.synergy.SDemand;

import java.util.Date;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 招标预告Dao
 */
public interface SSoldNoteDao {

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/13 14:21
	 * @return
	 * @description保存SSoldNote表功能
	 */
	public SSoldNote insert(SSoldNote sSoldNote);

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/7 11:05
	 * @return
	 * @description获取分页以及SSoldNote表中的数据
	 */
	public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId,
									Boolean isAdmin, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
									Integer UserId, String status, String payType, String statusType);
	/**
	 * @author dongxuecheng
	 * @Date 2017/1/20 11:08
	 * @return
	 * @description惠补政策申请导入销售信息的时候获取销售信息
	 */
	public Pagination getPage(Integer currUserId, int pageNo, int pageSize,String inquiryTheme,String statusType);

	/**
	 * @Author dxc
	 * 根据demandId查找
	 * @Date 2016/12/28 0028 9:08
	 */
	public SSoldNote findById(Integer id);

	/**
	 * @Author dxc
	 * @Date 2016/12/29 0029 8:51
	 */
	public SSoldNote updateByUpdater(Updater<SSoldNote> updater);

	/**
	 * @Author dxc
	 * 删除需求信息
	 * @Date 2016/12/29 0029 11:01
	 */
	public SSoldNote delete(SSoldNote sSoldNote);

}