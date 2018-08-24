package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairRes;

import java.util.Date;
import java.util.List;

/**
 * Created by jxd on 2016/12/27.
 */
public interface MasterManagerService {
/**
 * @author dongxuecheng
 * @Date 2017/1/7 10:56
 * @return
 * @description获取所有SRepairRes表的数据
 */
    public List<SRepairRes> getList();

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 10:57
     * @return
     * @description根据repairId查询对应的数据
     */
    public SRepairRes getSRepairResEntity(String id);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 10:59
     * @return
     * @description保存SRepairRes的数据
     */
    public Content supplychain_save(SRepairRes sRepairRes, Content c, ContentExt ext, ContentTxt t,
                                    Integer channelId, Integer typeId, CmsUser user, boolean b,String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 10:55
     * @return
     * @description 获取分页以及SRepairRes表中的数据
     */
    public Pagination getPageForMember(String repairType,Integer channelId, Integer siteId, Integer modelId, String companyId,Boolean isAdmin, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType);
    public Pagination getPageInquiry(Integer channelId, Integer siteId, Integer modelId, String companyId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType);
    /**
     * @author dongxuecheng
     * @Date 2017/1/7 17:30
     * @return
     * @description备品备件管理
     */
    public Pagination getSpare(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType);
    /**
     * @author dongxuecheng
     * @Date 2017/1/9 9:55
     * @return
     * @description备品备件上传审核
     */
    public Pagination getSpare_chexk(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType, String spareName);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 10:59
     * @return
     * @description根据repairId删除SRepairRes表中的数据
     */
    public void delete(String id);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 11:00
     * @return
     * @description根据repairId修改（撤回）SRepairRes表中的数据
     */
    public void reback(String id);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 11:00
     * @return
     * @description根据repairId修改（发布）SRepairRes表中的数据
     */
    public void relece(String id,CmsUser user);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 11:00
     * @return
     * @description根据repairId修改（更新）SRepairRes表中的数据
     */
  //  public void update(SRepairRes sRepairRes);

    void update(SRepairRes sRepairRes, Content c, ContentExt ext, ContentTxt t,
                String[] attachmentPaths, String[] attachmentNames,
                String[] attachmentFilenames, String[] picPaths,
                String[] picDescs, Integer channelId, Integer typeId, CmsUser user,Short charge,boolean b
                );

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 18:42
     * @return
     * @description 备品备件通过
     */
    public void pass(String id);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 18:42
     * @return
     * @description备品备件驳回
     */
    public void goback(String id);





    /**
     * @author 苏和 13739980760
     * @Date 2017/1/14 16:18
     * @return
     * 根据id修改备品备件状态（通过、驳回）
     */
    public void mdyRepairDemandStateById(String sparepartsId, String status,String backReason);

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/14 16:18
     * @return
     * 根据id批量修改备品备件状态（通过、驳回）
     */
    public void setRepairDemandStateByIds(String sparepartsId, String status,String backReason);

    /**
     * @author dongxuecheng
     * @Date 2017/2/10 11:07
     * @return
     * @description获取相似资源的自定义标签方法
     */

    public List<SRepairRes> getList(Integer count, Integer orderBy, Integer listType, String repairType);


}
