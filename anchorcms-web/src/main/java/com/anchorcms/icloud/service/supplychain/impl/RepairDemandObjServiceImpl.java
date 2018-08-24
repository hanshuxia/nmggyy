package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.RepairDemandObjDao;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObjPageBean;
import com.anchorcms.icloud.service.supplychain.RepairDemandObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:58
*@Return维修需求对象
*/
@Service
@Transactional
public class RepairDemandObjServiceImpl implements RepairDemandObjService {
    /**
    *@Author 潘晓东
    *@Date 2017/1/23 13:59
    *@Return根据维修需求ID查询维修需求对象
    */
    public List<SRepairDemandObj> selcetByRepairDemandId(String id) {
        List<SRepairDemandObj> sRepairDemandObjList = repairDemandObjDao.selectRepairDemand(id);
        return sRepairDemandObjList;
    }

    /**
     * Created by  hansx
     * 保存维修需求信息
     */
    public Content save(SRepairDemand p, SRepairDemandObjPageBean bean,CmsUser user) {

        if (bean!=null&&bean.getRepairNameStr()!=null&&
                !"".equals(bean.getRepairNameStr().trim())){
            String[] repNameAarr = bean.getRepairNameStr().split(",");
            if(repNameAarr.length>0){
                for (int r=0;r<repNameAarr.length;r++){
                    if(!"".equals(repNameAarr[r].trim())){
                        String uid = UUID.randomUUID().toString().replaceAll("-", "");//用来生成数据库的主键
                        SRepairDemandObj po = new SRepairDemandObj();
                        po.setRepairObjid(uid);
                        po.setRepairId(p.getRepairId());
                        po.setAddrProvince(p.getAddrProvince());
                        po.setAddrCity(p.getAddrCity());
                        po.setAddrStreet(p.getAddrStreet());
                        po.setAddrCounty(p.getAddrCounty());
                        po.setCreateDt(new Date());
                        po.setDeadlineDt(p.getDeadlineDt());
                        po.setCreaterId(user.getUserId().toString());
                        po.setStartDt(p.getStartDt());
                        po.setRepairName(repNameAarr[r].trim());
                        if(bean.getExpectPriceStr()!=null&&
                                !"".equals(bean.getExpectPriceStr().trim())){
                            if(bean.getExpectPriceStr().split(",").length>r &&bean.getExpectPriceStr().split(",")[r]!=null&&
                                   !"".equals(bean.getExpectPriceStr().split(",")[r].trim()) ){
                                po.setExpectPrice(Double.parseDouble(bean.getExpectPriceStr().split(",")[r].trim()));
                            }

                        }
                        if(bean.getMachineTypeStr()!=null&&
                                !"".equals(bean.getMachineTypeStr().trim())){
                            if(bean.getMachineTypeStr().split(",").length>r &&bean.getMachineTypeStr().split(",")[r]!=null&&
                                    !"".equals(bean.getMachineTypeStr().split(",")[r]) ){
                                po.setMachineType(bean.getMachineTypeStr().split(",")[r].trim());
                            }

                        }
                        if(bean.getRepairNumStr()!=null&&
                                !"".equals(bean.getRepairNumStr().trim())){
                            if(bean.getRepairNumStr().split(",").length>r &&bean.getRepairNumStr().split(",")[r]!=null&&
                                    !"".equals(bean.getRepairNumStr().split(",")[r].trim()) ){
                                po.setRepairNum(Double.parseDouble(bean.getRepairNumStr().split(",")[r].trim()));
                            }

                        }

                        if(bean.getRepairRequestStr()!=null&&
                                !"".equals(bean.getRepairRequestStr().trim())){

                            if(bean.getRepairRequestStr().split(",").length>r &&bean.getRepairRequestStr().split(",")[r]!=null&&
                                    !"".equals(bean.getRepairRequestStr().split(",")[r].trim()) ){
                                po.setRepairRequest(bean.getRepairRequestStr().split(",")[r].trim());
                            }

                        }
                        repairDemandObjDao.save(po);

                    }


                }
            }

        }

        return null;
    }

    @Autowired
    private RepairDemandObjDao repairDemandObjDao;

}
