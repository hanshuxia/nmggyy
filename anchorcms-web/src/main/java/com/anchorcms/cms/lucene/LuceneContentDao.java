package com.anchorcms.cms.lucene;

import java.io.IOException;
import java.util.Date;

import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.model.cloudcenter.SIcloudPolicy;
import com.anchorcms.icloud.model.software.SSoftware;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SDemand;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

public interface LuceneContentDao {

	public Integer index(IndexWriter writer, Integer siteId, Integer channelId,
						 Date startDate, Date endDate, Integer startId, Integer max)
			throws CorruptIndexException, IOException;


	public SAbility findAbilityByContentId(Integer id);

	public SDemand findDemandByContentId(Integer id);

	public SIcloudPolicy findPolicyByContentId(Integer id);

	public SIcloudManage findResourceByContentId(Integer id);

	public SIcloudDemand findIcloudDemandByContentId(Integer id);

	public SSoftware findSoftwareDemandByContentId(Integer id);

	public SCompany findCompanyByContentId(Integer id);
	/**
	 * @author machao
	 * @Date 2017/1/19 23:07
	 * @return
	 * 备品备件
	 */
	public SSpare findSSpareByContentId(Integer id);
	/**
	 * @author machao
	 * @Date 2017/1/19 23:07
	 * @return
	 * 备品备件求购
	 */
	public SSpareDemand findSSpareDemandByContentId(Integer id);
	/**
	 * @author machao
	 * @Date 2017/1/19 23:07
	 * @return
	 * 维修资源
	 */
	public SRepairRes findSRepairResByContentId(Integer id);
	/**
	 * @author machao
	 * @Date 2017/1/19 23:07
	 * @return
	 * 维修需求
	 */
	public SRepairDemand findSRepairDemandByContentId(Integer id);

	Integer getSAmplePolicyIdByContentId(Integer id);

	Integer getSTenderTrailerIdByContentId(Integer id);
}
