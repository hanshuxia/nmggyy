package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.icloud.dao.supplychain.SRepairAbilityDao;
import com.anchorcms.icloud.model.supplychain.SRepairAbility;
import com.anchorcms.icloud.service.supplychain.SRepairAbilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hansx on 2016/12/27.
 * 维修资源报价对象表业务实现类
 */
@Service
@Transactional
public class SRepairAbilityServiceImpl implements SRepairAbilityService {

   /**
    * @author hansx
    * @Date 2017/1/4 0004 下午 4:06
    * @return
    * 根据id获取数据
    */
    public SRepairAbility findById(String id) {
        return SRepairAbilityDao.findById(id);
    }

    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:06
     * @return
     * 保存询价信息
     */
    public void save(SRepairAbility srr) {
        SRepairAbilityDao.save(srr);
    }

    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:06
     * @return
     * 获取询价列表
     */
    public List<SRepairAbility> getList() {
        return SRepairAbilityDao.getList();
    }

    @Resource
    private SRepairAbilityDao SRepairAbilityDao;


}
