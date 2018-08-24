package com.anchorcms.icloud.service.unionCity;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.common.SMemberAddress;
import com.anchorcms.icloud.model.commservice.*;
import com.anchorcms.icloud.model.synergy.SBigdataDemandQuote;


/**
 * @author zhouyq
 * @Date 2017/8/7
 * @return
 * 盟市行订单service
 */
public interface unionCityService {

    /**
     * @author zhouyq
     * @Date 2017/8/7
     * @return
     * 盟市行实体保存
     */
    public SUnionCity creUnionCityEntity(SUnionCity sUnionCity);

    public Pagination getSyneryListPage(Integer siteId, CmsUser user, int pageNo, int pageSize, java.sql.Date startDate, java.sql.Date endDate, String state, String paramu);
    public Pagination getSyneryListPage2(Integer siteId, CmsUser user, int pageNo, int pageSize,String paramu);
    public Pagination getBigdataOnline(Integer siteId, CmsUser user, int pageNo, int pageSize,String paramu);
    public Pagination getBigdataNews(Integer siteId, CmsUser user, int pageNo, int pageSize,String paramu);
    public Pagination getBigdataPolicy(Integer siteId, CmsUser user, int pageNo, int pageSize,String paramu);
    public SBigDataSign findRegistredById(Integer id);
    public SBigdataDemandQuote findRegistredById2(Integer id);
    public SBigDataDemandSign findOnlineById(Integer id);
    public SBigDataNews findNewsById(Integer id);
    public SBigDataPolicy findPolicyById(Integer id);
    public void deleteNewsById(Integer id);
    public void deletePolicyById(Integer id);

    public Pagination getContactAddressList(Integer siteId, CmsUser user, int pageNo, int pageSize,String defAddr, String paramu,Integer userId);
//    public SMemberAddress delete(Integer addrId);
    public SMemberAddress findById(Integer addrId);
    public void deleteContactAddress(Integer addrId);
    public void deleteSunionCity(Integer unionId);

}
