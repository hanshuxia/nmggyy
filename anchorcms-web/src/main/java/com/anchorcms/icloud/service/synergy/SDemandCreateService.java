package com.anchorcms.icloud.service.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.common.SMemberAddress;
import com.anchorcms.icloud.model.commservice.SBigDataDemandSign;
import com.anchorcms.icloud.model.commservice.SBigDataNews;
import com.anchorcms.icloud.model.commservice.SBigDataPolicy;
import com.anchorcms.icloud.model.commservice.SBigDataSign;
import com.anchorcms.icloud.model.synergy.SDemand;

import java.util.List;

/*
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * @author: Gao Jiangning
 * @date: 2016/12/23 0023
 * 添加需求业务层接口
 */
public interface SDemandCreateService {
    /**
     * @author: gao jiangning
     * @desciption 保存业务内容+cms内容
     */
    public Content save(SDemand sDemand, Content c, ContentExt ext, ContentTxt t,
                        String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths,
                        String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b,
                        String demandObjListJsonString);

    public Content save2(SBigDataSign sBigDataSign, Content c, ContentExt ext, ContentTxt t,
                         String[] attachmentPaths, String[] attachmentNames,
                         String[] attachmentFilenames, String[] picPaths,
                         String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);
    public Content save3(SBigDataDemandSign sBigDataDemandSign, Content c, ContentExt ext, ContentTxt t,
                         String[] attachmentPaths, String[] attachmentNames,
                         String[] attachmentFilenames, String[] picPaths,
                         String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);
    /**
     * @author: suhe
     * @desciption 保存大数据新闻
     */
    public Content saveBigdataNews(SBigDataNews sBigDataNewsBigDataNews, Content c, ContentExt ext, ContentTxt t,
                                   String[] attachmentPaths, String[] attachmentNames,
                                   String[] attachmentFilenames, String[] picPaths,
                                   String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);

    public Content saveBigdataPolicy(SBigDataPolicy sBigDataPolicy, Content c, ContentExt ext, ContentTxt t,
                                     String[] attachmentPaths, String[] attachmentNames,
                                     String[] attachmentFilenames, String[] picPaths,
                                     String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);

    /**
     * @Author 闫浩芮
     * @Email netuser.orz@icloud.com
     * @Date 2017/01/04
     * @Desc 需求详情
     */
    public SDemand byDemandId(Integer demandId);

    /**
     * @Author 闫浩芮
     * 更新需求信息
     * @Date 2017/01/04
     */
    void update(SDemand sDemand, Content c, ContentExt ext, ContentTxt t,
                String[] attachmentPaths, String[] attachmentNames,
                String[] attachmentFilenames, String[] picPaths,
                String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b,
                String demandObjListJsonString);

    /**
     * @Author zhouyq
     * @Date 2016/8/12
     * @Desc 获取联系人信息
     */
    public String getContactJson(Integer userId);

    /**
     * @Author zhouyq
     * @Date 2016/8/13
     * @Desc 获取联系人信息list
     */
    List<SMemberAddress> getContactJsonList(Integer userId);
}
