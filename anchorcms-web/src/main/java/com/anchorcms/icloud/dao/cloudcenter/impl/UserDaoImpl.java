package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.UserDao;
import org.springframework.stereotype.Repository;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/179:42
 */
@Repository
public class UserDaoImpl extends HibernateBaseDao<CmsUser,Integer> implements UserDao{
    public CmsUser getUserById(String createrId) {
        return get(Integer.parseInt(createrId));
    }
    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<CmsUser> getEntityClass() {
        return CmsUser.class;
    }


}
