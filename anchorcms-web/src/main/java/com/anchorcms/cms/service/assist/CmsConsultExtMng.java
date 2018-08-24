package com.anchorcms.cms.service.assist;


import com.anchorcms.cms.model.assist.CmsConsult;
import com.anchorcms.cms.model.assist.CmsConsultExt;

public interface CmsConsultExtMng {
	public CmsConsultExt save(String ip, String text, CmsConsult consult);

	public CmsConsultExt update(CmsConsultExt bean);

	public int deleteByContentId(Integer contentId);
}