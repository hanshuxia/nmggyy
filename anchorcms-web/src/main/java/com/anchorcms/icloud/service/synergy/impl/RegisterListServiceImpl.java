package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.RegisterListDao;
import com.anchorcms.icloud.service.synergy.RegisterListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/2/16 0016
 * @Desc
 */
@Service
@Transactional
public class RegisterListServiceImpl implements RegisterListService {
    /**
     *@Author ly
     *@Date 2016/12/30 17:05
     *@Desc 获取企业信息列表
     **/
    public Pagination getRegisterPage(Integer siteId, CmsUser user, int cpn, int pageSize) {
        return registerListDao.getPage(siteId, user, cpn, pageSize);
    }
    @Resource
    private RegisterListDao registerListDao;
}
