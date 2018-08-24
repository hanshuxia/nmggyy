package com.anchorcms.icloud.service.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.SStiteManager;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.synergy.SAbility;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ${耿文龙} on 2017/1/13.
 */
public interface CommserviceCreateService {

    /**
     * @author machao
     * @Date 2017/1/15 12:30
     * @return
     * 中标公告发布
     */
    public Content save(SBidNotice ability, Content c, ContentExt ext, ContentTxt t,
                        String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths,
                        String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);
    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-招标公告
     */
    public List<STenderNotice> getStenderNoticeList(Integer count, Integer orderBy, Integer listType);

    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-招标预告
     */
    public List<STenderTrailer> getStenderTrailerList(Integer count, Integer orderBy, Integer listType);

    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-中标公告
     */
    public List<SBidNotice> getSBidNoticeList(Integer count, Integer orderBy, Integer listType);
    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-政务导航
     */
    public List<SStiteManager> getSstiteManagerList(Integer count, Integer orderBy, Integer listType);



}
