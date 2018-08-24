package com.anchorcms.cms.service.main.impl;

import com.anchorcms.cms.service.main.ExtContentService;
import com.anchorcms.cms.service.main.SpecialExtContentDirectiveService;
import com.anchorcms.cms.service.main.SpecialExtContentService;
import com.anchorcms.common.page.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by smt on 2016/12/9.
 */
@Service
public class ExtContentServiceImpl implements ExtContentService {

    public Object findById(Integer id,String modelPath) {
        for(SpecialExtContentService tmpService : specialExtContentServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.findById(id);
            }
        }
        return null;
    }

    /***
     *
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
     * @param modelPath
     * @return
     */
    public Pagination getPageBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params, String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            /**协同制造--能力池展示*/
            if("ability".equals(modelPath)){
                return tmpService.getPageBySiteIdAbilityById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**
             * @auther 李利民
             * 协同制造--需求池展示
             */
            if(tmpService.getModelDemandPath().equals(modelPath)){
                return tmpService.getPageBySiteIdsForTagById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**协同制造--企业制造能力展示*/
            if("company".equals(modelPath)){
                return tmpService.getPageBySiteIdComporyById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**云资源交易中心--云计算政策页面查询*/
            if("policy".equals(modelPath)){
                return tmpService.getPageBySiteIdPolicyById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**云资源交易中心--扶持政策页面查询*/
            if("fosteringPolicy".equals(modelPath)){
                return tmpService.getPageBySiteIdFostPolicyById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**云资源交易中心--云需求页面查询*/
            if("icloudDemand".equals(modelPath)){
                return tmpService.getPageBySiteIdDemandById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**云资源交易中心--云资源查看页面查询*/
            if("check".equals(modelPath)){
                return tmpService.getPageBySiteIdCheckById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**软件应用--软件检索页面查询*/
            if("software".equals(modelPath)){
                return tmpService.getPageBySiteIdSoftwareById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**
             * @author 苏和 13739980760
             * @Date 2017/1/5 16:51
             * @return
             * 备品备件求购展示查询
             */
            if("spareDemand".equals(modelPath)){
                return tmpService.getPageBySiteIdBpBjById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**
             * @author liuyang
             * @Date 2017/1/7 20:24
             * 众修需求展示
             * @return
             */
            if("repairDemand".equals(modelPath)){
                return tmpService.getPageBySiteIdZxxqId(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }

            /**
             * @author machao
             * @Date 2017/1/7 21:24
             * @return
             * 众修资源列表展示
             */
            if("repairRes".equals(modelPath)){
                return tmpService.getPageBySiteIdZxzyById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**
             * @author zhouyq
             * @Date 2017/01/07
             * @return
             * 备品备件展示查询
             */
            if("spare".equals(modelPath)){
                return tmpService.getPageBySiteIdBpBjListById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**
             * @author gengwenlong
             * @Date 2017/1/8 12:07
             * 金牌老师傅推荐展示页面查询
             * @return
             */
            if("repairResTuijian".equals(modelPath)){
                return tmpService.getAllSearch(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**
             * @author gengwenlong
             * @Date 2017/1/13 11:02
             * @return
             * 惠补政策展示页面
             */
            if("benefitPolicy".equals(modelPath)){
                return tmpService.getSearchHuibu(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**
             * @author suhe
             * @Date 2017/4/20 13:32
             * @return
             * 产业融合新闻
             */
            if("bigdataNews".equals(modelPath)){
                return tmpService.getSearchBigdataNews(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**
             * @author suhe
             * @Date 2017/4/20 13:32
             * @return
             * 产业融合政策
             */
            if("bigdataPolicy".equals(modelPath)){
                return tmpService.getSearchBigdataPolicy(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**
             * @author liuyang
             * @Date 2017/12/11 11:24
             * @return
             * 政务导航
             */
            if("ggfwzwdh".equals(modelPath)){
                return tmpService.getSearchZhengWu(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
            /**
             * @author gengwenlong
             * @Date 2017/1/13 15:11
             * @return
             * 项目招投标展示页面
             */
            if("tender".equals(modelPath)){
                return tmpService.getSearchTender(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
            }
        }
        return null;
    }

    public List getListBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count,String[] params, String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getListBySiteIdsForTag(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,first,count,params);
            }
        }
        return null;
    }
    public Pagination getPageByIdsForTag(Integer[] ids,  int oderBy, int pageNo,int count, String[] queryParams,String modelPath)
    {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getPageByIdsForTag(ids,oderBy,pageNo,count,queryParams);
            }
            /**
             * @auther 李利民
             * @descipt 根据modelPath 根据条件判断查询条件
             *
             */
            if(modelPath.equals(tmpService.getModelDemandPath())){
                return tmpService.getPageByDemandForTag(ids,oderBy,pageNo,count,queryParams);
            }
        }
        return null;
    }

    public List  getListByIdsForTag(Integer[] ids, int oderBy,String[] queryParams,String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getListByIdsForTag(ids,oderBy,queryParams);
            }
        }
        return null;
    }

    public Pagination getPageByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend,
                                            String title, Map<String, String[]> attr, int orderBy,
                                            int pageNo,int count, String[] queryParams, String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getPageByTagIdsForTag(tagIds, siteIds,
                        channelIds, typeIds, excludeId, titleImg, recommend,
                        title,attr,orderBy, pageNo, count,queryParams);
            }
        }
        return null;
    }

    public List getListByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend,
                                      String title, Map<String, String[]> attr, int orderBy,
                                      int first, int count, String[] queryParams, String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getListByTagIdsForTag(tagIds, siteIds,
                        channelIds, typeIds, excludeId, titleImg, recommend,
                        title,attr,orderBy, first, count,queryParams);
            }
        }
        return null;
    }

    public Pagination getPageByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                             String title, Map<String, String[]> attr, int orderBy, int pageNo, int count, String[] queryParams, String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getPageByTopicIdForTag(topicId, siteIds,
                        channelIds, typeIds, titleImg, recommend,
                        title,attr,orderBy, pageNo, count,queryParams);
            }
        }
        return null;
    }

    public List getListByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                       String title, Map<String, String[]> attr, int orderBy, int first, int count, String[] queryParams, String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getListByTopicIdForTag(topicId, siteIds,
                        channelIds, typeIds, titleImg, recommend,
                        title,attr,orderBy, first, count,queryParams);
            }
        }
        return null;
    }

    public Pagination getPageByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                                Map<String, String[]> attr, int orderBy, int option, int pageNo, int pageSize, String[] queryParams, String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getPageByChannelIdsForTag(
                        channelIds, typeIds, titleImg, recommend,
                        title,attr,orderBy,option,pageNo,pageSize,queryParams);
            }
        }
        return null;
    }

    public List getListByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr,
                                          int orderBy, int option, Integer first, Integer count, String[] queryParams, String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getListByChannelIdsForTag(
                        channelIds, typeIds, titleImg, recommend,
                        title,attr,orderBy,option,first,count,queryParams);
            }
        }
        return null;
    }

    public Pagination getPageByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                                  Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] queryParams, String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getPageByChannelPathsForTag(paths, siteIds, typeIds,
                        titleImg, recommend, title,attr, orderBy, pageNo, pageSize,queryParams);
            }
        }
        return null;
    }

    public List getListByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                            String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count, String[] queryParams, String modelPath) {
        for(SpecialExtContentDirectiveService tmpService : specialExtContentDirectiveServices){
            if(modelPath.equals(tmpService.getModelPath())){
                return tmpService.getListByChannelPathsForTag(paths, siteIds, typeIds,
                        titleImg, recommend, title,attr, orderBy, first, count,queryParams);
            }
        }
        return null;
    }


    @Resource
    List<SpecialExtContentService> specialExtContentServices;
    @Resource
    List<SpecialExtContentDirectiveService> specialExtContentDirectiveServices;
}
