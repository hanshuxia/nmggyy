package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.icloud.dao.supplychain.SupplyChainDao;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.service.supplychain.SupplyChainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by DELL on 2017/1/3.
 */
@Service
@Transactional
public class SupplyChainServiceImpl implements SupplyChainService {
    /**
     * @return
     */
    public List<SSpare> getList(){
        return supplychainCreateDao.getAll();
    }

    public List<SRepairRes> getAl(String name, String place, String type) {
        return supplychainCreateDao.getAl(name,place,type);
    }
    public List<SRepairRes> getAl() {
        return supplychainCreateDao.getAl();
    }

    public int delSupply(String delNum){
        return supplychainCreateDao.delSupply(delNum);
    }
    public List<SRepairRes> getAl(String uid){
        return supplychainCreateDao.getAl(uid);
    }
    public int updateSupply(String uid){
        return supplychainCreateDao.updateSupply(uid);
    }

    @Resource
    private SupplyChainDao supplychainCreateDao;
}
