package com.anchorcms.icloud.service.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.SAmplePolicyApply;

/**
 * Created by Hansx on 2017/1/13 0013.
 *
 *  招标预告信息
 */
public interface SAmplePolicyApplyService {

/**
 * @author dongxuecheng
 * @Date 2017/1/13 13:51
 * @return
 * @description销售记录发布
 */
public Content supplychain_save(SAmplePolicyApply sAmplePolicyApply, Content c, ContentExt ext, ContentTxt t,
                                Integer channelId, Integer typeId, CmsUser user, boolean b, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 10:55
     * @return
     * @description 获取分页以及惠补政策表中的数据
     */
    public Pagination getPageForMember(Integer siteId, Boolean isadmin, int pageNo, int pageSize, String inquiryTheme, Integer UserId, String status);

    /**
     * @author gengwenlong
     * @Date 2017/2/24 16:50
     * @return
     * 惠补政策申请审核获取分页以及SAmplePolicyApply的数据
     */
    public Pagination getPageForMember1(Integer siteId, Boolean isadmin, int pageNo, int pageSize, String inquiryTheme, Integer UserId, String status);

    /**
     * @author dongxuecheng
     * @Date 2017/1/8 12:29
     * @return
     * @description获取惠补政策申请审核界面的分页
     */
    public Pagination getZxxqList(String repairName, Integer pageNo, Integer pageSize);

    /**
     * @Author dongxuecheng
     * @Date 2016/12/26
     * @param repairId, state
     * @return
     * @Desc 根据id获取需求，改动状态（通过、驳回）
     */
    public void setStatus(String repairId, String status,String backreason);

    /**
     * @Author dongxuecheng
     * @Date 2016/12/26
     * @param repairId, state
     * @return
     * @Desc 根据id删除
     */
    public void delete(String repairId);

    /**
     * @author dongxuecheng
     * @Date 2017/1/14 9:59
     * @return
     * @description根据选中的所有id设置表中的状惠补政策申请状态
     */
    public void setall(String repairId, String status,String backreason);

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 10:59
     * @return
     * @description更具id删除对应的数据
     */
    public SAmplePolicyApply delete(Integer id);


    /**
     * @author dongxuecheng
     * @Date 2017/1/20 10:59
     * @return
     * @description通过id朝着对应的数据
     */
    public SAmplePolicyApply findbyId(Integer sAmplePolicyApplyId);

    /**
     * @Author dxc
     * 更新销售记录
     * @Date 2017/01/04
     */
    void update(SAmplePolicyApply sAmplePolicyApply, Content c, ContentExt ext, ContentTxt t,
                String[] attachmentPaths, String[] attachmentNames,
                String[] attachmentFilenames, String[] picPaths,
                String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b,
                String demandObjListJsonString);

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:01
     * @return
     * @description 更新销售信息
     */
    public void updatesoldNoteIds(String soldNoteIds,Integer id);

/**
 * @author dongxuecheng
 * @Date 2017/1/20 11:01
 * @return
 * @description发布功能
 */
    public void relece(Integer id);

    /**
     * @author zhouyq
     * @Date 2017/2/16
     * @return
     * @description 撤回功能
     */
    public void reback(Integer id);
}
