package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.supplychain.SRepairResListDao;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import org.springframework.stereotype.Repository;

/**
 * Created by 刘洋 on 2017/1/4.
 */
    /**
     * Created by DELL on 2016/12/26.
     */
    /**
     * @author liuyang
     * @Date 2017/1/4 15:29
     * 维修资源展示dao层实现类
     */
    @Repository
    public class SRepariResListServiceDaoImpl extends HibernateBaseDao<SRepairDemand, Integer> implements SRepairResListDao {

        public Pagination getPage(String repairType, String addrProvince,
                                  String addrCity, String releaseId, String releaseDt,
                                  Integer pageNo, Integer pageSize) {
            Finder f = getFinder(repairType, addrProvince, addrCity, releaseId, releaseDt);
            f.setCacheable(true);
            return find(f, pageNo, pageSize);
        }

        private Finder getFinder(String repairType, String addrProvince, String addrCity, String releaseId, String releaseDt){
            Finder f = Finder.create("from SRepairDemand bean where 1=1");
            if (repairType != null && repairType.trim().length()> 0) {
                f.append(" and bean.repairType=:repairType");
                f.setParam("repairType", repairType);
            } else if (addrProvince != null && addrProvince.trim().length() > 0) {
                f.append(" and bean.addrProvince=:addrProvince");
                f.setParam("addrProvince", addrProvince);
            } else if (addrCity != null && addrCity.trim().length() > 0) {
                f.append(" and bean.addrCity=:addrCity");
                f.setParam("addrCity", addrCity);
            } else if (releaseId != null && releaseId.trim().length() > 0) {
                f.append(" and bean.releaseId=:releaseId");
                f.setParam("releaseId", releaseId);
            } else if (releaseDt != null && releaseDt.trim().length() > 0) {
                f.append(" and bean.releaseDt=:releaseDt");
                f.setParam("releaseDt", releaseDt);
            }
            return f;
        }

        protected Class<SRepairDemand> getEntityClass() {
            return SRepairDemand.class;
        }
    }




