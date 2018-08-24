package com.anchorcms.icloud.dao.unionCity;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.*;
import com.anchorcms.icloud.model.synergy.SBigdataDemandQuote;

import java.util.Map;

/**
 * @author zhouyq
 * @Date 2017/8/7
 * @return
 * 盟市行dao
 */
public interface unionCityDao {

    /**
     * @author zhouyq
     * @Date 2017/8/7
     * @return
     * 盟市行保存
     */
    public SUnionCity creUnionCityEntity(SUnionCity sUnionCity);

    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize, java.sql.Date startDate, java.sql.Date endDate, String state, String paramu);
    public Pagination getPage2(Integer siteId, CmsUser user, int pageNo, int pageSize, String paramu);
    public Pagination getBigdataOnline(Integer siteId, CmsUser user, int pageNo, int pageSize, String paramu);
    public Pagination getBigdataNews(Integer siteId, CmsUser user, int pageNo, int pageSize, String paramu);
    public Pagination getBigdataPolicy(Integer siteId, CmsUser user, int pageNo, int pageSize, String paramu);
    public SBigDataSign findRegistredById(Integer id);
    public SBigdataDemandQuote findRegistredById2(Integer id);
    public SBigDataDemandSign findOnlineById(Integer id);

    Pagination getSearchBigdataNews(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                    Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    Pagination getSearchBigdataPolicy(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                    Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);


    public SBigDataNews findNewsById(Integer id);
    public void deleteNewsById(Integer id);

    public SBigDataPolicy findPolicyById(Integer id);
    public void deletePolicyById(Integer id);

    public Pagination getContactAddressList(Integer siteId, CmsUser user, int pageNo, int pageSize, String defAddr, String paramu, Integer userId);
//    public SMemberAddress findById(Integer addrId);
//    public SMemberAddress deleteById(Integer addrId);
    public void deleteContactAddress(Integer addrId);
    public void deleteSunionCity(Integer unionId);



}
