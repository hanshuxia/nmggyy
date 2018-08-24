package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.supplychain.SRepairAbility;
import java.util.List;

/**
 * Created by hansx on 2016/12/27.
 *
 * 维修资源报价对象信息表业务接口
 */
public interface SRepairAbilityService {

    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:05
     * @return
     * 根据id查询
     */
    public SRepairAbility findById(String id);
    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:05
     * @return
     * 保存
     */
    public void save(SRepairAbility srr);

   /**
    * @author hansx
    * @Date 2017/1/4 0004 下午 4:05
    * @return
    * 获取列表
    */
    public List<SRepairAbility> getList();

    }
