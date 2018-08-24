package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;

import java.util.Date;


/**
 * Created by DELL on 2016/12/29.
 */

    /**
     * @Author liuyang
     * @Email
     * @Date 2016/12/26
     * @Desc 备品备件审核管理_管理员Dao层接口
     */
    public interface SSpareReleaseAdminsManageDao {
        public Pagination getPage(String statusType,String requestTheme, Date createDt, Date deadlineDt, Integer pageNo, Integer pageSize);

        /**
         * 根据id获取备品备件明细信息
         *
         * @param demandId
         * @return
         */
//    public List findDetailAndPreviewById(String demandId);
        public SSpareDemand findDetailAndPreviewByIdDemand(String demandId);

        /**
         * 根据id修改备品备件审核状态（通过、驳回）
         *
         * @param demandId, state
         * @return
         */
        public void setRepairDemandStateById(String demandId, String state,Date releaseDt,String flag,String backReason);
        public void modifyRepairDemandStateById(String demandId, String state,Date releaseDt,String backReason);

        /**
         * 根据id删除备品备件信息
         *
         * @param demandId
         * @return
         */
        public void delRepairDemandById(String demandId);
    }


