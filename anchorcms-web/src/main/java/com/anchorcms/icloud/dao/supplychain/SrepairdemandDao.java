package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairRes;

import java.util.List;

/**
 * @Author zhouyq
 * @Date 2016/12/26
 * @Desc 众修资源管理_管理员Dao层接口
 */
public interface SrepairdemandDao {
    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairName, pageNo, pageSize
     * @return
     * @Desc 获取众修资源分页后信息
     */
    public Pagination getPage(String repairName,String status, Integer pageNo, Integer pageSize);

//    public List findDetailAndPreviewById(String repairId);

    /**
     * @author dongxuecheng
     * @Date 2017/1/8 12:34
     * @return
     * @description众修需求管理
     */
    public Pagination getZxxqList(String repairName, Integer pageNo, Integer pageSize,String statusType);
    /**
    *@Author 苏和 【562763562@qq.com】
    *@Date 2017/5/3 11:27
    *@Return
    */
    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize, java.sql.Date startDate, String status, String paramu);
    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId
     * @return
     * @Desc 根据id获取众修资源明细或预览信息
     */
    public SRepairRes findDetailAndPreviewByIdDemand(String repairId);

    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId, state
     * @return
     * @Desc 根据id修改众修需求状态（通过、驳回）
     */
    public void mdyRepairDemandStateById(String repairId, String status);

    /**
     * @param repairIds, state
     * @return
     * @author zhouyq
     * @Date 2017/01/06
     * @Desc 根据id批量修改众修需求状态（通过、驳回）
     */
    public void setRepairDemandStateByIds(String repairIds, String status, String backReason);

    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId, state
     * @return
     * @Desc 根据id删除众修需求
     */
    public void delRepairDemandById(String repairId);

    /**
     * @Author Don国学成
     * @Date 2016/12/26
     * @param repairId, state
     * @return
     * @Desc 根据id获取需求，改动状态（通过、驳回）
     */
    public int setStatus(String repairId, String status,String backReason);



    public void delete(String repairId);


    public int setRepairDemandstatus(String repairIds, String status,String backReason);

    List getSpareStatisticsJson(String region);
}
