package com.anchorcms.cms.service.main;

import com.anchorcms.cms.model.assist.CmsWebservice;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUserExt;

import java.util.List;
import java.util.Map;


public interface CmsWebserviceMng {
	public Pagination getPage(int pageNo, int pageSize);
	
	public List<CmsWebservice> getList(String type);
	
	public boolean hasWebservice(String type);
	
	public String callWebService(CmsWebservice service, Map<String, String> params);
	
	public void callWebService(String operate, Map<String, String> params);
	
	public void callWebService(String admin, String username, String password, String email, CmsUserExt userExt, String operate);
	
	public CmsWebservice findById(Integer id);

	public CmsWebservice save(CmsWebservice bean, String[] paramName, String[] defaultValue);

	public CmsWebservice update(CmsWebservice bean, String[] paramName, String[] defaultValue);

	public CmsWebservice deleteById(Integer id);
	
	public CmsWebservice[] deleteByIds(Integer[] ids);
}