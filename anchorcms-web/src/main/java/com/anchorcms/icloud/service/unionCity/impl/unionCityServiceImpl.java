package com.anchorcms.icloud.service.unionCity.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.common.SMemberAddrDao;
import com.anchorcms.icloud.dao.unionCity.unionCityDao;
import com.anchorcms.icloud.model.common.SMemberAddress;
import com.anchorcms.icloud.model.commservice.*;
import com.anchorcms.icloud.model.synergy.SBigdataDemandQuote;
import com.anchorcms.icloud.service.unionCity.unionCityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zhouyq
 * @Date 2017/8/7
 * @return
 * 盟市行service实现类
 */
@Service
@Transactional
public class unionCityServiceImpl implements unionCityService {
    public SUnionCity creUnionCityEntity(SUnionCity sUnionCity) {
        return unionCityDao.creUnionCityEntity(sUnionCity);
    }

    public Pagination getSyneryListPage(Integer siteId, CmsUser user, int pageNo, int pageSize, java.sql.Date startDate, java.sql.Date endDate, String state, String paramu) {
        return unionCityDao.getPage(siteId, user, pageNo, pageSize, startDate, endDate, state, paramu);
    }
    public Pagination getSyneryListPage2(Integer siteId, CmsUser user, int pageNo, int pageSize,String paramu) {
        return unionCityDao.getPage2(siteId, user, pageNo, pageSize,  paramu);
    }
    public Pagination getBigdataOnline(Integer siteId, CmsUser user, int pageNo, int pageSize,String paramu) {
        return unionCityDao.getBigdataOnline(siteId, user, pageNo, pageSize,  paramu);
    }
    public Pagination getBigdataNews(Integer siteId, CmsUser user, int pageNo, int pageSize,String paramu) {
        return unionCityDao.getBigdataNews(siteId, user, pageNo, pageSize,  paramu);
    }
    public Pagination getBigdataPolicy(Integer siteId, CmsUser user, int pageNo, int pageSize,String paramu) {
        return unionCityDao.getBigdataPolicy(siteId, user, pageNo, pageSize,  paramu);
    }


    public SBigDataSign findRegistredById(Integer id) {
        return unionCityDao.findRegistredById(id);
    }
    public SBigdataDemandQuote findRegistredById2(Integer id) {
        return unionCityDao.findRegistredById2(id);
    }

    public SBigDataDemandSign findOnlineById(Integer id) {
        return unionCityDao.findOnlineById(id);
    }



    public SBigDataNews findNewsById(Integer id) {
        return unionCityDao.findNewsById(id);
    }
    public SBigDataPolicy findPolicyById(Integer id) {
        return unionCityDao.findPolicyById(id);
    }


    public void deleteNewsById(Integer id){
        unionCityDao.deleteNewsById(id);
    }
    public void deletePolicyById(Integer id){
        unionCityDao.deletePolicyById(id);
    }


    public Pagination getContactAddressList(Integer siteId, CmsUser user, int pageNo, int pageSize, String defAddr, String paramu,Integer userId) {
        return unionCityDao.getContactAddressList(siteId, user, pageNo, pageSize,defAddr, paramu, userId);
    }
//    public SMemberAddress delete(Integer addrId) {
//        SMemberAddress bean = findById(addrId);//获取软件实体类
//        unionCityDao.deleteById(addrId);
//        return bean;
//    }
    public SMemberAddress findById(Integer addrId) {
        return sMemberAddrDao.findById(addrId);
    }

    public void deleteContactAddress(Integer addrId){
        unionCityDao.deleteContactAddress(addrId);
    }
    public void deleteSunionCity(Integer unionId){
        unionCityDao.deleteSunionCity(unionId);
    }
    @Resource
    private unionCityDao unionCityDao;
    @Resource
    private SMemberAddrDao sMemberAddrDao;
}
