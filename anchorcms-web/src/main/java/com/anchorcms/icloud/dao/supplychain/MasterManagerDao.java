package com.anchorcms.icloud.dao.supplychain;



import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairRes;

import java.util.Date;
import java.util.List;

/**
 * Created by jxd on 2016/12/27.
 */
public interface MasterManagerDao {

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:17
     * @return
     * @description获取所有资源表的数据
     */
    public List<SRepairRes> getList();
    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:17
     * @return
     * @description根据id获取对应资源表的数据
     */
    public SRepairRes getSRepairResEntity(String id);
    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:17
     * @return
     * @description保存资源表的信息
     */
    public void insert(SRepairRes sRepairRes);
    /**
     * @author dongxuecheng
     * @Date 2017/1/7 11:05
     * @return
     * @description获取分页以及SRepairRes表中的数据
     */
    public Pagination getPageBySelf(String repairType,Integer channelId, Integer siteId, Integer modelId,
                                    Boolean isAdmin, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                    String companyId, String status, String payType, String statusType);

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:16
     * @return
     * @description通过维修资源表获取询价信息
     */
    public Pagination getPageInquiry(Integer channelId, Integer siteId, Integer modelId,
                                    Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                     String companyId, String status, String payType, String statusType);


    /**
     * @author dongxuecheng
     * @Date 2017/1/7 17:36
     * @return
     * @description备品备件管理
     */
    public Pagination getSpare(Integer channelId, Integer siteId, Integer modelId,
                                    Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                    Integer UserId, String status, String payType, String statusType);

    /**
     * @author dongxuecheng
     * @Date 2017/1/9 9:57
     * @return
     * @description备品备件上传审核
     */
    public Pagination getSpare_check(Integer channelId, Integer siteId, Integer modelId,
                               Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                               Integer UserId, String status, String payType, String statusType, String spareName);
    /**
     * @author dongxuecheng
     * @Date 2017/1/8 16:22
     * @return
     * @description删除
     */
    public SRepairRes delete(SRepairRes sRepairRes);
    /**
     * @author dongxuecheng
     * @Date 2017/1/8 16:22
     * @return
     * @description退回
     */
    public int reback(String id);
    /**
     * @author dongxuecheng
     * @Date 2017/1/8 16:22
     * @return
     * @description发布
     */
    public int relece(String id,CmsUser user);
    /**
     * @author dongxuecheng
     * @Date 2017/1/8 16:22
     * @return
     * @description修改
     */
    //public int update(SRepairRes sRepairRes);

    public SRepairRes updateByUpdater(Updater<SRepairRes> updater);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 18:42
     * @return
     * @description 备品备件通过
     */
    public int pass(String id);

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 18:42
     * @return
     * @description备品备件驳回
     */
    public int goback(String id);




    /**
     * @author 苏和 13739980760
     * @Date 2017/1/14 16:21
     * @return
     * 根据id修改备品备件状态（通过、驳回）
     */
    public void mdyRepairDemandStateById(String sparepartsId, String status);

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/14 16:21
     * @return
     * 根据id批量修改备品备件状态（通过、驳回）
     */
    public void setRepairDemandStateByIds(String sparepartsId, String status,String backReason);

    public SRepairRes updateRepair(SRepairRes sRepairRes);

    /**
     * @author dongxuecheng
     * @Date 2017/2/10 11:07
     * @return
     * @description获取相似资源的自定义标签方法
     */
    public List<SRepairRes> getList(Integer count, Integer orderBy, Integer listType, String repairType);
}
