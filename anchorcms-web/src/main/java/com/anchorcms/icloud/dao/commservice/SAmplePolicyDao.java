package com.anchorcms.icloud.dao.commservice;


import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;

import java.util.List;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 惠补政策Dao
 */
public interface SAmplePolicyDao {

	/**
	 * @author hansx
	 * @Date 2017/1/13 0013 上午 11:38
	 * @return
	 *  根据id获取惠补政策信息
	 */
	public SAmplePolicy findById(int id);


	public List<SAmplePolicy> getSpareDemandResList(Integer count, Integer orderBy, Integer listType);
}