package com.anchorcms.cms.service.assist;

import com.anchorcms.cms.model.assist.CmsConfigContentCharge;

import java.util.Map;


public interface CmsConfigContentChargeMng {
	
	public CmsConfigContentCharge findById(Integer id) ;
	
	public CmsConfigContentCharge getDefault();

	public CmsConfigContentCharge update(CmsConfigContentCharge bean
			, String payTransferPassword, Map<String, String> keys);
	
	public CmsConfigContentCharge afterUserPay(Double payAmout);

}