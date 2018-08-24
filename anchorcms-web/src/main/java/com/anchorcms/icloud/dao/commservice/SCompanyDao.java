package com.anchorcms.icloud.dao.commservice;


import com.anchorcms.icloud.model.synergy.SCompany;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 企业信息Dao
 */
public interface SCompanyDao {

	/**
	 * @author hansx
	 * @Date 2017/1/13 0013 上午 11:38
	 * @return
	 *  根据id获取企业信息
	 */
	public SCompany findById(String id);

	/**
	 * @author hansx
	 * @Date 2017/1/7 0007 下午 12:11
	 * @return
	 * 根据id更新状态
	 */
	public int updateStatusById(String id, String statusType);
	/**
	 * @Author 闫浩芮
	 * 置为推荐
	 * @Date 2017/2/18 0018 16:48
	 */
	public int recommendMany(String ids);
	/**
	 * @Author 闫浩芮
	 * 置为撤销
	 * @Date 2017/2/18 0018 16:48
	 */
	public int revokeMany(String ids);

	/**
	 * 批量修改
	 * @param ids
	 * @param backReason
	 * @param relstatus
     * @return
     */
	public int revokeAll(String ids,String backReason,String relstatus);

	/**
	 * 更新公司数据
	 * @param bean
	 * @return
	 */
	public SCompany update(SCompany bean);

}