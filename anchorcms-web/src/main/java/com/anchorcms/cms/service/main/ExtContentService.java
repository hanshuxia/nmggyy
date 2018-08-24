package com.anchorcms.cms.service.main;

import com.anchorcms.common.page.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by smt on 2016/12/9.
 */
public interface ExtContentService {
    Object findById(Integer id, String modelPath);
    Pagination getPageBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg,
                                      Boolean recommend, String title, Map<String, String[]> attr, int orderBy,
                                      int pageNo, int pageSize, String[] params, String modelPath);
     List getListBySiteIdsForTag(Integer[] siteIds,
                                       Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                       String title, Map<String, String[]> attr,
                                       int orderBy, Integer first, Integer count, String[] params, String modelPath);
    Pagination getPageByIdsForTag(Integer[] id, int oderBy,int pageNo, int count, String[] queryParams, String modelPath);

    List getListByIdsForTag(Integer[] id, int oderBy, String[] queryParams, String modelPath);

    Pagination getPageByTagIdsForTag(Integer[] tagIds, Integer[] siteIds,
                                     Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend,
                                     String title, Map<String, String[]> attr, int orderBy,
                                     int pageNo, int count, String[] queryParams, String modelPath);

    List getListByTagIdsForTag(Integer[] tagIds, Integer[] siteIds,
                               Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend,
                               String title, Map<String, String[]> attr, int orderBy,
                               int first, int count, String[] queryParams, String modelPath);

    Pagination getPageByTopicIdForTag(Integer topicId, Integer[] siteIds,
                                      Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                      String title, Map<String, String[]> attr, int orderBy,
                                      int pageNo, int count, String[] queryParams, String modelPath);
    List getListByTopicIdForTag(Integer topicId, Integer[] siteIds,
                                Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                String title, Map<String, String[]> attr, int orderBy,
                                int first, int count, String[] queryParams, String modelPath);
    Pagination getPageByChannelIdsForTag(Integer[] channelIds,
                                         Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                         String title, Map<String, String[]> attr,
                                         int orderBy, int option, int pageNo, int pageSize, String[] queryParams, String modelPath);
    public List getListByChannelIdsForTag(Integer[] channelIds,
                                          Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                          String title, Map<String, String[]> attr,
                                          int orderBy, int option, Integer first, Integer count, String[] queryParams, String modelPath);
    public Pagination getPageByChannelPathsForTag(String[] paths,
                                                  Integer[] siteIds, Integer[] typeIds, Boolean titleImg,
                                                  Boolean recommend, String title, Map<String, String[]> attr,
                                                  int orderBy, int pageNo,
                                                  int pageSize, String[] queryParams, String modelPath);
    public List getListByChannelPathsForTag(String[] paths,
                                            Integer[] siteIds, Integer[] typeIds, Boolean titleImg,
                                            Boolean recommend, String title, Map<String, String[]> attr,
                                            int orderBy, Integer first,
                                            Integer count, String[] queryParams, String modelPath);
}
