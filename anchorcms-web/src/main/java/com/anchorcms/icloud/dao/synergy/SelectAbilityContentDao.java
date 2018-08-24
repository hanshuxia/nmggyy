package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.synergy.SAbility;

import java.util.List;
import java.util.Map;

/**
 * Created by 11239 on 2016/12/21.
 */
public interface SelectAbilityContentDao {
    public SAbility findById(Integer id);
    /**协同制造--能力池展示*/
    public Pagination getPageBySiteIdsForTag(Integer[] siteIds,
                                             Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                             String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize,
                                             String type, String name, String abilityType, String abilityName, int adilityordeyBy);
    List getListBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count,
                                String abilityType );
    public Pagination getPageByIdsForTag(Integer[] ids, int orderBy, int pageNo, int count,String abilityType);

    public List getListByIdsForTag(Integer[] ids, int orderBy,String abilityType);

    public Pagination getPageByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend,
                                            String title, Map<String, String[]> attr, int orderBy,
                                            int pageNo, int count,String abilityType  );

    public List getListByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend,
                                      String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count,String abilityType  );

    public Pagination getPageByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                             Map<String, String[]> attr, int orderBy, int pageNo, int count, String abilityType );

    public List getListByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                       Map<String, String[]> attr, int orderBy, Integer first, Integer count, String abilityType );

    Pagination getPageByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                         Map<String, String[]> attr, int orderBy, int option, int pageNo, int pageSize,String abilityType );

    List getListByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy,
                                   int option, Integer first, Integer count,String abilityType  );

    Pagination getPageByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                           String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String abilityType );

    List getListByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                     Map<String, String[]> attr, int orderBy, Integer first, Integer count, String abilityType );

    /**
     * 协同制造--能力池展示
     * @author wanjinyou
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
    Pagination getPageBySiteIdAbilityById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /***
     * 获取需求信息表中的内容并显示在前段
     * @param ids
     * @param oderBy
     * @param pageNo
     * @param count
     * @param queryParams
     * @return
     */
    Pagination getPageByIdsForTagById(Integer[] ids, int oderBy, int pageNo, int count, String[] queryParams);

    /***
     * 获取全部需求信息表中的内容并显示在前段
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
    Pagination getPageBySiteIdsForTagDe(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /***
     * 企业能力展示
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
    Pagination etPageBySiteIdComporyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

}
