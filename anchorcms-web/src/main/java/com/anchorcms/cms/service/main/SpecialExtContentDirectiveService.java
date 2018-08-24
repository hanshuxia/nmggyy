package com.anchorcms.cms.service.main;

import com.anchorcms.common.page.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by smt on 2016/12/15.
 */
public interface SpecialExtContentDirectiveService {
    String getModelPath();

    /***
     * 返回需求信息内容
     * @return
     */
    String getModelDemandPath();
    Pagination getPageBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg,
                                      Boolean recommend, String title, Map<String, String[]> attr, int orderBy,
                                      int pageNo, int pageSize, String[] params);
    List<Object> getListByIdsForTag(Integer[] id, int oderBy, String[] queryParams);
    Pagination getPageByIdsForTag(Integer[] ids, int oderBy,int pageNo,int count,String[] queryParams);
    Pagination getPageByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend,
                                     String title, Map<String, String[]> attr, int orderBy,
                                     int pageNo, int count, String[] queryParams);
    List<Object> getListByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend,
                                       String title, Map<String, String[]> attr, int orderBy,
                                       int first, int count, String[] queryParams);
    Pagination getPageByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                      Map<String, String[]> attr, int orderBy, int pageNo, int count, String[] queryParams);
    List getListByTopicIdForTag(Integer topicId, Integer[] siteIds,
                                Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                String title, Map<String, String[]> attr, int orderBy,
                                int first, int count, String[] queryParams);
    Pagination getPageByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                         Map<String, String[]> attr, int orderBy, int option, int pageNo, int pageSize, String[] queryParams);

    List getListByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr,
                                   int orderBy, int option, Integer first, Integer count, String[] queryParams);

    Pagination getPageByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                           Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] queryParams);

    public List getListByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                            Map<String, String[]> attr, int orderBy, Integer first, Integer count, String[] queryParams);

    List getListBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr,
                                int orderBy, Integer first, Integer count, String[] params);

    /**
     *协同制造--能力池展示
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
     *  根据不同的params path 或得需求信息
     * @param ids
     * @param oderBy
     * @param pageNo
     * @param count
     * @param queryParams
     * @return
     */
    Pagination getPageByDemandForTag(Integer[] ids, int oderBy, int pageNo, int count, String[] queryParams);

    /***
     * 查询初始化查询信息
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
    Pagination getPageBySiteIdsForTagById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

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
    Pagination getPageBySiteIdComporyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /**
     * 云资源交易中心--云计算政策
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
    Pagination getPageBySiteIdPolicyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /**
     * 云资源交易中心--扶持政策
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
    Pagination getPageBySiteIdFostPolicyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);


    /***
     * 云资源交易中心--云资源查看
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
     * 云资源交易中心--云需求
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

    /***
     * @author ly
     * @date 2017/1/5 16:36
     * @desc 软件应用——软件检索页面查询
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
    Pagination getPageBySiteIdSoftwareById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/5 16:45
     * 备品备件求购列表展示查询
     * @return
     */
    Pagination getPageBySiteIdBpBjById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
    /**
     * @author liuyang
     * @Date 2017/1/7 20:22
     * 众修需求展示查询
     * @return
    */
    Pagination getPageBySiteIdZxxqId(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
    /**
     * @author machao
     * @Date 2017/1/7 21:25
     * @return
     * 众修资源列表展示
     */
    Pagination getPageBySiteIdZxzyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /**
     * @author gengwenlong
     * @Date 2017/1/5 16:56
     * @return 金牌老师傅推荐页面查询
     */
    Pagination getAllSearch(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
    /**
     * @author zhouyq
     * @Date 2017/01/07
     * 备品备件列表展示查询
     * @return
     */
    Pagination getPageBySiteIdBpBjListById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
/**
 * @author gengwenlong
 * @Date 2017/1/13 10:19
 * @return
 * 惠补政策展示页面
 */
    Pagination getSearchHuibu(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
    /**
     * @author suhe
     * @Date 2017/4/20 13:40
     * @return
     * 产业融合新闻页面
     */
    Pagination getSearchBigdataNews(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
    /**
     * @author suhe
     * @Date 2017/4/20 13:40
     * @return
     * 产业融合政策页面
     */
    Pagination getSearchBigdataPolicy(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
    /**
     * @author gengwenlong
     * @Date 2017/1/13 15:13
     * @return
     * 项目招投标展示页面
     */
    Pagination getSearchTender(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /**
     * @author liuyang
     * @Date 2017/12/11 10:46
     * @return
     * 政务导航列表展示
     */
    Pagination getSearchZhengWu(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
}
