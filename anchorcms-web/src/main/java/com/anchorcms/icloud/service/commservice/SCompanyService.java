package com.anchorcms.icloud.service.commservice;

import com.anchorcms.icloud.model.synergy.SCompany;

/**
 * Created by Hansx on 2017/1/13 0013.
 *
 *  企业信息
 */
public interface SCompanyService {

    /**
     * @author hansx
     * @Date 2017/1/13 0013 上午 11:30
     * @return
     *  根据id获企业信息
     */
    public SCompany findById(String id);

   /**
    * @author hansx
    * @Date 2017/2/17 0017 下午 3:52
    * @return
    *  根据企业id修改推荐状态
    */
    public int updateById(String id,String isRecommend);
    /**
     * @Author 闫浩芮
     * @Date 2017/2/18 0018 17:04
     *置为推荐
     */
    public int recommendByIds(String ids);
    /**
     * @Author 闫浩芮
     * @Date 2017/2/18 0018 17:05
     * 置为撤销
     */
    public int revoke(String ids);

    public int revokeAll(String ids,String backReason,String relstatus);

    /**
     * 通过驳回的方法
     */
    public SCompany Update(SCompany sCompany);
}
