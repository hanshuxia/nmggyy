package com.anchorcms.icloud.dao.supplychain.impl;


import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.supplychain.SSpareReleaseAdminsManageobjDao;
import com.anchorcms.icloud.model.supplychain.SSpareDemandObj;
import org.springframework.stereotype.Repository;

/**
 * Created by DELL on 2016/12/29.
 */
    /**
     * @Author liuyang
     * @Email
     * @Date 2016/12/29
     * @Desc 备品备件管理_管理员Dao层实现类
     */
    @Repository
    public class SSpareReleaseAdminsManageobjDaoImpl extends HibernateBaseDao<SSpareDemandObj, String> implements SSpareReleaseAdminsManageobjDao {

        /**
         * 根据id获取备品备件资源明细信息(适合返回tr列表形式)
         *
         * @return
         */
        public SSpareDemandObj findDetailAndPreviewByIdDemandObj(String demandId) {
            SSpareDemandObj entitiy = get(demandId);
            return entitiy;
        }

        protected Class<SSpareDemandObj> getEntityClass() {
            return SSpareDemandObj.class;
        }
    }


