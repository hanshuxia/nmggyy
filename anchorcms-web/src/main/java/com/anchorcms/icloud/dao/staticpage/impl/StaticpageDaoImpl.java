package com.anchorcms.icloud.dao.staticpage.impl;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.CmsModel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.main.CmsKeywordMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.staticpage.DistributionThread;
import com.anchorcms.common.constants.Constants;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.model.PageInfo;
import com.anchorcms.common.page.Paginable;
import com.anchorcms.common.page.SimplePage;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.utils.URLHelper;
import com.anchorcms.common.web.mvc.RealPathResolver;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.Ftp;
import com.anchorcms.core.service.FtpMng;
import com.anchorcms.icloud.dao.staticpage.StaticpageDao;

import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.service.staticpage.StaticpageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.anchorcms.common.constants.Constants.UTF8;


/**
 * Created by jxd on 2017/2/17.
 */
@Repository
public class StaticpageDaoImpl extends HibernateBaseDao<Content, Integer> implements StaticpageDao {

    public SAmplePolicy findSAmplePolicyByContentId(Integer id) {
        Query query = getSession().createQuery("SELECT bean from SAmplePolicy bean where bean.content.contentId=:contentId ")
                .setParameter("contentId", id);
        SAmplePolicy sAmplePolicy = (SAmplePolicy) query.uniqueResult();
        return sAmplePolicy;
    }

    public String type(Integer contentId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" 	T.TYPE ");
        sql.append(" FROM ");
        sql.append(" 	( ");
        sql.append(" 		SELECT ");
        sql.append(" 			TN.CONTENT_ID, ");
        sql.append(" 			'STenderNotice' TYPE ");
        sql.append(" 		FROM ");
        sql.append(" 			S_TENDER_NOTICE TN ");
        sql.append(" 		UNION ALL ");
        sql.append(" 			SELECT ");
        sql.append(" 				TT.CONTENT_ID, ");
        sql.append(" 				'STenderTrailer' TYPE ");
        sql.append(" 			FROM ");
        sql.append(" 				S_TENDER_TRAILER TT ");
        sql.append(" 			UNION ALL ");
        sql.append(" 				SELECT ");
        sql.append(" 					BN.CONTENT_ID, ");
        sql.append(" 					'SBidNotice' TYPE ");
        sql.append(" 				FROM ");
        sql.append(" 					S_BID_NOTICE BN ");
        sql.append(" 	) T ");
        sql.append(" WHERE ");
        sql.append(" 	T.CONTENT_ID =" + contentId);
        Query query = getSession().createSQLQuery(sql.toString());
        List list = query.list();
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0).toString();
    }

    public STenderTrailer findSTenderTrailerByContentId(Integer contentId) {
        Query query = getSession().createQuery("SELECT bean from STenderTrailer bean where bean.content.contentId=:contentId ")
                .setParameter("contentId", contentId);
        STenderTrailer sTenderTrailer = (STenderTrailer) query.uniqueResult();
        return sTenderTrailer;
    }

    public STenderNotice findSTenderNoticeByContentId(Integer contentId) {
        Query query = getSession().createQuery("SELECT bean from STenderNotice bean where bean.content.contentId=:contentId ")
                .setParameter("contentId", contentId);
        STenderNotice sTenderNotice = (STenderNotice) query.uniqueResult();
        return sTenderNotice;
    }

    public SBidNotice findSBidNoticeByContentId(Integer contentId) {
        Query query = getSession().createQuery("SELECT bean from SBidNotice bean where bean.content.contentId=:contentId ")
                .setParameter("contentId", contentId);
        SBidNotice sBidNotice = (SBidNotice) query.uniqueResult();
        return sBidNotice;
    }





    @Override
    protected Class<Content> getEntityClass() {
        return null;
    }


}

