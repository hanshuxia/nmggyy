package com.anchorcms.icloud.dao.supplychain;


import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.supplychain.SRepairAbility;

import java.util.Date;
import java.util.List;

/**
 * Created by hansx on 2017/1/2.
 * <p>
 * 维修资源报价对象对象信息表DAO
 */
public interface SRepairAbilityDao {
    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:25
     * @return
     * 根据id获取报价对象信息
     */
    public SRepairAbility findById(String id);

   /**
    * @author hansx
    * @Date 2017/1/5 0005 下午 2:25
    * @return
    * 添加报价对象信息
    */
    public SRepairAbility save(SRepairAbility srr);


    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:26
     * @return
     * 获取报价对象列表
     */
    public List<SRepairAbility> getList();

}
