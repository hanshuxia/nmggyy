package com.anchorcms.cms.service.assist;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsAccountPay;
import com.anchorcms.core.model.CmsUser;
import org.springframework.ui.ModelMap;


public interface CmsAccountPayMng {
	
	public String weixinTransferPay(String serverUrl, Integer drawId,
									CmsUser drawUser, CmsUser payUser, Double payAmount, String orderNum,
									HttpServletRequest request, HttpServletResponse response,
									ModelMap model);
	
	public Pagination getPage(String drawNum, Integer payUserId, Integer drawUserId,
							  Date payTimeBegin, Date payTimeEnd, int pageNo, int pageSize);

	public CmsAccountPay findById(Long id);

	public CmsAccountPay save(CmsAccountPay bean);

	public CmsAccountPay update(CmsAccountPay bean);

	public CmsAccountPay deleteById(Long id);
	
	public CmsAccountPay[] deleteByIds(Long[] ids);
}