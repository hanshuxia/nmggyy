package com.anchorcms.icloud.dao.commservice;


import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;
import com.anchorcms.icloud.model.commservice.SAmplePolicyApply;
import com.anchorcms.icloud.model.commservice.SSoldNote;

import java.util.Date;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 招标预告Dao
 */
public interface SAmplePolicyApplyDao {

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/13 14:21
	 * @return
	 * @description保存销售记录
	 */
	public SAmplePolicyApply insert(SAmplePolicyApply sAmplePolicyApply);

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/7 11:05
	 * @return
	 * @description获取分页以及SRepairRes表中的数据
	 */
	public Pagination getPageBySelf(Integer siteId, Boolean isadmin, int pageNo, int pageSize, String inquiryTheme, Integer UserId, String status);
    /**
     * @author gengwenlong
     * @Date 2017/2/24 16:48
     * @return
	 * 惠补政策申请审核获取分页以及SAmplePolicyApply的数据
     */
	public Pagination getPageBySelf1(Integer siteId, Boolean isadmin, int pageNo, int pageSize, String inquiryTheme, Integer UserId, String status);
	/**
	 * @author dongxuecheng
	 * @Date 2017/1/8 12:34
	 * @return
	 * @description众修需求管理
	 */
	public Pagination getZxxqList(String repairName, Integer pageNo, Integer pageSize);



	/**
	 * @Author dongxuecheng
	 * @Date 2016/12/26
	 * @param repairId, state
	 * @return
	 * @Desc 根据id获取需求，改动状态（通过、驳回）
	 */
	public int setStatus(String amplePolicyApplyId, String status,String backReason);


/**
 * @author dongxuecheng
 * @Date 2017/1/14 10:03
 * @return
 * @description删除惠补政策申请
 */
	public void delete(String repairId);

/**
 * @author dongxuecheng
 * @Date 2017/1/14 10:04
 * @return
 * @description批量设置惠补政策申请的状态
 */
	public int setRepairDemandstatus(String repairIds, String status,String backReason);

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/20 11:04
	 * @return
	 * @description删除一条惠补政策申请信息
	 */
	public SAmplePolicyApply delete(SAmplePolicyApply sAmplePolicyApply);

	/**
	 * @Author dxc
	 * 根据demandId查找
	 * @Date 2016/12/28 0028 9:08
	 */
	public SAmplePolicyApply findById(Integer id);

	/**
	 * @Author dxc
	 * @Date 2016/12/29 0029 8:51
	 */
	public SAmplePolicyApply updateByUpdater(Updater<SAmplePolicyApply> updater);


	public SAmplePolicyApply updatemc(SAmplePolicyApply sAmplePolicyApply);



	/**
	 * @author dongxuecheng
	 * @Date 2017/1/20 11:07
	 * @return
	 * @description更新政策申请的销售id功能
	 */
	public int updatesoldNoteIds(String soldNoteIds,Integer id);

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/20 11:05
	 * @return
	 * @description
	 */
	public int relece(Integer id);

	/**
	 * @author zhouyq
	 * @Date 2017/2/16
	 * @return
	 * @description 惠补政策申请管理撤回功能
	 */
	public int reback(Integer id);
}