package com.anchorcms.icloud.service.common.impl;

import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.common.SMemberAddrDao;
import com.anchorcms.icloud.model.common.SMemberAddress;
import com.anchorcms.icloud.service.common.SMemberAddrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zhouyq
 * @Date 2017/8/12
 * @return
 * 联系人信息service实现
 */
@Service
@Transactional
public class SMemberAddrServiceImpl implements SMemberAddrService {

    /**
     * @Author zhouyq
     * @Date 2017/8/12
     * @param
     * @return
     * @Desc 获取联系人信息
     */
    public SMemberAddress getContactInfo(Integer userId) {
        return sMemberAddrDao.getContactInfo(userId);
    }

    /**
     * @Author zhouyq
     * @Date 2017/8/13
     * @param
     * @return
     * @Desc 新增保存联系人信息
     */
    public SMemberAddress saveContactInfo(Integer channelId, Integer modelId, CmsUser user, SMemberAddress sMemberAddress, Integer userId, Integer defAddr, Integer addrId){
        sMemberAddrDao.saveContactInfo(sMemberAddress, userId, defAddr, addrId);
        return sMemberAddress;
    }

    /**
     * @Author zhouyq
     * @Date 2017/8/15
     * @param
     * @return
     * @Desc 更新userId数据为非默认地址
     */
    public int updateContactNoDef(Integer userId, Integer addrId){
        return sMemberAddrDao.updateContactNoDef(userId, addrId);
    }
    public SMemberAddress findById(Integer addrId) {
        return sMemberAddrDao.findById(addrId);
    }
    @Resource
    private SMemberAddrDao sMemberAddrDao;
}
