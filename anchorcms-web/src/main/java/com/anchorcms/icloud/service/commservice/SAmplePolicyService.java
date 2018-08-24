package com.anchorcms.icloud.service.commservice;

import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;

import java.util.List;

/**
 * Created by Hansx on 2017/1/13 0013.
 *
 *  惠补政策信息
 */
public interface SAmplePolicyService {

    /**
     * @author hansx
     * @Date 2017/1/13 0013 上午 11:30
     * @return
     *  根据id获惠补政策信息
     */
    public SAmplePolicy findById(int id);

    public List<SAmplePolicy> getSpareDemandResList(Integer count, Integer orderBy, Integer listType);

}
