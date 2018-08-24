package com.anchorcms.icloud.service.common;


import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.common.SMemberAddress;

/**
 * @author zhouyq
 * @Date 2017/8/12
 * @return
 * 联系人信息service
 */
public interface SMemberAddrService {

    /**
     * @Author zhouyq
     * @Date 2017/8/12
     * @param
     * @return
     * @Desc 获取联系人信息
     */
    public SMemberAddress getContactInfo(Integer userId);

    /**
     * @Author zhouyq
     * @Date 2017/8/13
     * @param
     * @return
     * @Desc 新增保存联系人信息
     */
    public SMemberAddress saveContactInfo(Integer channelId, Integer modelId, CmsUser user, SMemberAddress sMemberAddress, Integer userId, Integer defAddr, Integer addrId);

    /**
     * @Author zhouyq
     * @Date 2017/8/15
     * @param
     * @return
     * @Desc 更新userId数据为非默认地址
     */
    public int updateContactNoDef(Integer userId, Integer addrId);

    public SMemberAddress findById(Integer addrId);
}
