package com.anchorcms.icloud.service.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.SSoldNote;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.synergy.SDemand;

import java.util.Date;

/**
 * Created by Hansx on 2017/1/13 0013.
 *
 *  招标预告信息
 */
public interface SSoldNoteService {

/**
 * @author dongxuecheng
 * @Date 2017/1/13 13:51
 * @return
 * @description销售记录发布保存功能
 */
public Content supplychain_save(SSoldNote sSoldNote, Content c, ContentExt ext, ContentTxt t,
                                Integer channelId, Integer typeId, CmsUser user, boolean b, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 10:55
     * @return
     * @description 销售记录管理获取相应数据
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Boolean isAdmin, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType);

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:08
     * @return
     * @description惠补政策申请导入销售信息的时候获取销售信息
     */
    public String getPage(Integer currUserId, int pageNo, int pageSize,String inquiryTheme,String statusType);

    /**
     * @author dongxuecheng
     * @Date 2017/1/14 10:24
     * @return
     * @description更新状态（驳回和发布）
     */
    public void  update(Integer demandId);

    /**
     * @author dongxuecheng
     * @Date 2017/1/14 10:57
     * @return
     * @description删除销售记录
     */
    public SSoldNote  delete(Integer demandId);

/**
 * @author dongxuecheng
 * @Date 2017/1/14 12:03
 * @return
 * @description根据id查找相应的记录
 */
    public SSoldNote  findById(Integer demandId);

    /**
     * @Author dxc
     * 更新销售记录
     * @Date 2017/01/04
     */
    void update(SSoldNote sSoldNote, Content c, ContentExt ext, ContentTxt t,
                String[] attachmentPaths, String[] attachmentNames,
                String[] attachmentFilenames, String[] picPaths,
                String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b,
                String demandObjListJsonString);
}
