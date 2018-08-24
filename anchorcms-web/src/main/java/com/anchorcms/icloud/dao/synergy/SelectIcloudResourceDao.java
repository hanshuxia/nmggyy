package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.page.Pagination;

import java.util.Map;

/**
 * Created by 11239 on 2016/12/21.
 */
public interface SelectIcloudResourceDao {

    /***
     * @author wanjinyou
     * @description 云资源交易中心--云计算政策页面查询
     * @param siteIds
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    Pagination getPageBySiteIdPolicyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /***
     * @author wanjinyou
     * @description 云资源交易中心--扶持政策
     * @param siteIds
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    Pagination getPageBySiteIdFostPolicyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);


    /***
     * @author wanjinyou
     * @description 云资源交易中心--云资源查看
     * @param siteIds
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    Pagination getPageBySiteIdCheckById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /***
     * @author wanjinyou
     * @description 云资源交易中心--云需求
     * @param siteIds
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    Pagination getPageBySiteIdDemandById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

}
