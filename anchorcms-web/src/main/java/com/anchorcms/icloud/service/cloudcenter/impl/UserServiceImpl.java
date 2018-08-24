package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.UserDao;
import com.anchorcms.icloud.service.cloudcenter.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/179:18
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    public CmsUser getByYserId(String createrId) {
        CmsUser user = userDao.getUserById(createrId);
        return user;
    }

    @Resource
    private UserDao userDao;
}
