package com.anchorcms.icloud.dao.staticpage;

import com.anchorcms.core.model.CmsSite;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.service.staticpage.StaticpageService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jxd on 2017/2/17.
 */
public interface StaticpageDao {
    public SAmplePolicy findSAmplePolicyByContentId(Integer id);

    public String type(Integer contentId);

    public STenderTrailer findSTenderTrailerByContentId(Integer contentId);

    public STenderNotice findSTenderNoticeByContentId(Integer contentId);

    public SBidNotice findSBidNoticeByContentId(Integer contentId);



}
