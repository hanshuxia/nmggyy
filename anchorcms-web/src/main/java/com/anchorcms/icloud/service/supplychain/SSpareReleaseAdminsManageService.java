package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;

import java.util.Date;


/**
 * Created by DELL on 2016/12/29.
 */

    /**
     * @Author liuyang
     * @Email
     * @Date 2016/12/29
     * @Desc 备品备件求购审核管理员服务层接口
     * */
    public interface SSpareReleaseAdminsManageService {
        public Pagination getList(String statusType,String requestTheme,Date createDt, Date deadlineDt, Integer pageNo, Integer pageSize);

        /**
         * 根据id获取备品备件管理明细
         *
         * @param demandId
         * @return
         */
//    public List findDetailAndPreviewById(String demandId);
        public SSpareDemand findDetailAndPreviewByIdDemand(String demandId);

        /**
         * 根据id修改备品备件审核管理状态（通过、驳回）
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


