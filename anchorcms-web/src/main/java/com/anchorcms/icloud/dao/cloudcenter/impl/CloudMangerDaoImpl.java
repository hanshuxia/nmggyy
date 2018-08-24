package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudMangerDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudCenter;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/516:16
 */
@Repository
public class CloudMangerDaoImpl extends HibernateBaseDao<SIcloudManage, Integer> implements CloudMangerDao {

public SIcloudManage save(SIcloudManage bean) {
        getSession().save(bean);
        return bean;
        }

        public SIcloudManage getMangerById(Integer mangerId) {
               return get(mangerId);
        }

        public SIcloudManage update(SIcloudManage bean) {
                getSession().update(bean);
                return bean;
        }

        public void deleteBymanager(SIcloudManage manager) {
                getSession().delete(manager);
        }
        public List<SIcloudCenter> getsIcloudCenter(){
                Finder f = Finder.create(" select  bean from SIcloudCenter bean");
                f.setCacheable(true);
                return find(f);
        }
        /**
 * 获得Dao对于的实体类
 *
 * @return
 */
@Override
protected Class<SIcloudManage> getEntityClass() {
        return SIcloudManage.class;
}
        }
