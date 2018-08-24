package com.anchorcms.icloud.service.synergy.impl;

/**
 * Created by ly on 2016/12/20.
 */
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.commservice.CommserviceCreateDao;
import com.anchorcms.icloud.dao.software.SoftwareDao;
import com.anchorcms.icloud.dao.supplychain.SRepairResDao;
import com.anchorcms.icloud.dao.supplychain.SSpareDao;
import com.anchorcms.icloud.dao.supplychain.SSpareDemandDao;
import com.anchorcms.icloud.dao.supplychain.SupplychainCreateDao;
import com.anchorcms.icloud.dao.synergy.SelectAbilityContentDao;
import com.anchorcms.icloud.dao.synergy.SelectIcloudResourceDao;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.service.synergy.SelectAbilityContentMng;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

    @Service
    @Transactional
    public class SelectAbilityContentMngImpl implements SelectAbilityContentMng {
        String abilityType =null;
        String abilityName=null;
        String addrStreet = null;
        String contact = null;
        int adilityordeyBy ;
        public Object findById(Integer id) {
            return dao1.findById(id);
        }

        public String getModelPath() {
            return SAbility.MODEL_PATH;
        }

        /***
         * 返回需求信息内容
         *
         * @return
         */
        public String getModelDemandPath() {
            return SDemand.MODEL_PATH;
        }

        //用abilityType和abilityName字段进行查询：
        public Pagination getPageBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            getquery(params);
            return dao1.getPageBySiteIdsForTag(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,abilityType,abilityName,addrStreet,contact,adilityordeyBy);
        }

        public List getListByIdsForTag(Integer[] id, int oderBy, String[] queryParams) {
            getquery(queryParams);
            return dao1.getListByIdsForTag(id,oderBy,abilityType);
        }

        public Pagination getPageByIdsForTag(Integer[] ids, int oderBy, int pageNo,int count,  String[] queryParams) {
            getquery(queryParams);
            return dao1.getPageByIdsForTag(ids,oderBy,pageNo,count, abilityType);
        }

        public Pagination getPageByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int count, String[] queryParams) {
            getquery(queryParams);
            return dao1.getPageByTagIdsForTag(tagIds, siteIds, channelIds, typeIds,
                    excludeId, titleImg, recommend,title,attr,orderBy, pageNo,
                    count,abilityType);
        }

        public List  getListByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int first, int count, String[] queryParams) {
            getquery(queryParams);
            return dao1.getListByTagIdsForTag(tagIds, siteIds, channelIds, typeIds,
                    excludeId, titleImg, recommend, title,attr,orderBy, first,
                    count,abilityType);
        }

        public Pagination getPageByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int count, String[] queryParams) {
            getquery(queryParams);
            return dao1.getPageByTopicIdForTag(topicId,siteIds,channelIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,count,abilityType);
        }

        public List getListByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int first, int count, String[] queryParams) {
            getquery(queryParams);
            return dao1.getListByTopicIdForTag(topicId,siteIds,channelIds,typeIds,titleImg,recommend,title,attr,orderBy,first,count,abilityType);
        }

        public Pagination getPageByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int option, int pageNo, int pageSize, String[] queryParams) {
            getquery(queryParams);
            return dao1.getPageByChannelIdsForTag(channelIds,typeIds,titleImg,recommend,title,attr,orderBy,option,pageNo,pageSize,abilityType);
        }

        public List getListByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int option, Integer first, Integer count, String[] queryParams) {
            getquery(queryParams);
            return dao1.getListByChannelIdsForTag(channelIds,typeIds,titleImg,recommend,title,attr,orderBy,option,first,count,abilityType);
        }

        public Pagination getPageByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] queryParams) {
            getquery(queryParams);
            return dao1.getPageByChannelPathsForTag(paths,siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,abilityType);
        }

        public List getListByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count, String[] queryParams) {
            getquery(queryParams);
            return dao1.getListByChannelPathsForTag(paths,siteIds,typeIds,titleImg,recommend,title,attr,orderBy,first,count,abilityType);
        }

        public List getListBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count, String[] params) {
            getquery(params);
            return dao1.getListBySiteIdsForTag(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,first,count,abilityType);
        }

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
        public Pagination getPageBySiteIdAbilityById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return dao1.getPageBySiteIdAbilityById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }

        /***
         * 根据不同的params path 或得需求信息
         *@auther 李利民
         * @param ids
         * @param oderBy
         * @param pageNo
         * @param count
         * @param queryParams
         * @return
         */
        public Pagination getPageByDemandForTag(Integer[] ids, int oderBy, int pageNo, int count, String[] queryParams) {
            getqueryById(queryParams);
            return dao1.getPageByIdsForTagById(ids,oderBy,pageNo,count, queryParams);
        }

        /***
         * 查询初始化查询信息
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
         * @return
         */
        public Pagination getPageBySiteIdsForTagById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return dao1.getPageBySiteIdsForTagDe(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);

        }

        /***
         * 企业能力展示
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
         * @return
         */
        public Pagination getPageBySiteIdComporyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return dao1.etPageBySiteIdComporyById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }

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
        public Pagination getPageBySiteIdPolicyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return icloudDao.getPageBySiteIdPolicyById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }

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
        public Pagination getPageBySiteIdFostPolicyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return icloudDao.getPageBySiteIdFostPolicyById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }

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
        public Pagination getPageBySiteIdCheckById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return icloudDao.getPageBySiteIdCheckById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }

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
        public Pagination getPageBySiteIdDemandById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return icloudDao.getPageBySiteIdDemandById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }

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
        public Pagination getPageBySiteIdSoftwareById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return softwareDao.getSitePage(siteIds, typeIds, titleImg, recommend, title, attr, orderBy, pageNo, pageSize, params);
        }

        /**
         * @author 苏和 13739980760
         * @Date 2017/1/5 17:40
         * @return
         * 备品备件求购检索
         */

        public Pagination getPageBySiteIdBpBjById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return daoBpbj.getPageBySiteIdBpbjqgById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }
        /**
         * @author machao
         * @Date 2017/1/7 21:26
         * @return
         * 众修资源列表展示
         */
        public Pagination getPageBySiteIdZxzyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return sRepairResDao.getPageBySiteIdZxzyById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }

        /**
         * @author zhouyq
         * @Date 2017/01/07
         * @return
         * 备品备件展示查询
         */
        public Pagination getPageBySiteIdBpBjListById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return spareDao.getPageBySiteIdBpbjById(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }
        /**
         * @author gengwenlong
         * @Date 2017/1/13 15:16
         * @return
         * 惠补政策展示页面
         */
        public Pagination getSearchHuibu(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return commserviceCreateDao.getSearchHuibu(siteIds,typeIds,titleImg,recommend,title,attr, orderBy, pageNo, pageSize,params);
        }

        public Pagination getSearchBigdataNews(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return unionCityDao.getSearchBigdataNews(siteIds,typeIds,titleImg,recommend,title,attr, orderBy, pageNo, pageSize,params);
        }

        public Pagination getSearchBigdataPolicy(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return unionCityDao.getSearchBigdataPolicy(siteIds,typeIds,titleImg,recommend,title,attr, orderBy, pageNo, pageSize,params);
        }


        /**
         * @author liuyang
         * @Date 2017/12/11 10:51
         * @return
         * 政务导航展示页面
         */
        public Pagination getSearchZhengWu(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return commserviceCreateDao.getSearchZhengWu(siteIds,typeIds,titleImg,recommend,title,attr, orderBy, pageNo, pageSize,params);
        }


        /**
         * @author gengwenlong
         * @Date 2017/1/13 15:15
         * @return
         * 项目招投标展示页面
         */
        public Pagination getSearchTender(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return commserviceCreateDao.getSearchTender(siteIds,typeIds,titleImg,recommend,title,attr, orderBy, pageNo, pageSize,params);
        }

        /**
         * @author gengwenlong
         * @Date 2017/1/5 16:58
         * @return金牌老师傅推荐页面查询
         */
        public Pagination getAllSearch(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr,
                                       int orderBy, int pageNo, int pageSize, String[] params) {
            return daoBpbj.getAllSearch(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }

        public Pagination getPageBySiteIdZxxqId(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
            return supplychainCreateDao.getPageBySiteIdZxxqId(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
        }

        /***
         * 用来获取传来参数中的数据
         * @param queryParams
         */
        private void getqueryById(String[] queryParams) {

        }

        public void getquery(String[] params)
        {
            abilityType = null;
            abilityName=null;
            addrStreet = null;
            contact = null;
            adilityordeyBy = 1;
            if(params.length > 0){
                if(params[0]!=null && (params[0].trim()).length()>0 && !("undefined".equals(params[0]))){
                    try{
                        abilityType=params[0].trim();
                    }catch (NumberFormatException nfe){
                        abilityType = null;
                    }
                }
                if(params[1]!=null && (params[1].trim()).length()>0 && !("undefined".equals(params[1]))){
                    try{
                        abilityName=params[1].trim();
                    }catch (NumberFormatException nfe){
                        abilityName = null;
                    }
                }
                if(params[2]!=null && (params[2].trim()).length()>0 && !("undefined".equals(params[2]))){
                    try{
                        addrStreet=params[2].trim();
                    }catch (NumberFormatException nfe){
                        addrStreet = null;
                    }
                }
                if(params[3]!=null && (params[3].trim()).length()>0 && !("undefined".equals(params[3]))){
                    try{
                        contact=params[3].trim();
                    }catch (NumberFormatException nfe){
                        contact = null;
                    }
                }
                if(params[4]!=null && (params[4].trim()).length()>0 && !("undefined".equals(params[4]))){
                    try{
                        String orderBy = params[4].trim();
                        adilityordeyBy = Integer.parseInt(orderBy);
                    }catch (NumberFormatException nfe){
                        adilityordeyBy = 1;
                    }
                }
            }
        }
        @Resource
        private SelectAbilityContentDao dao1;
        @Resource
        private SSpareDemandDao daoBpbj;
        @Resource
        private SRepairResDao sRepairResDao;
        @Resource
        private SupplychainCreateDao supplychainCreateDao;
        @Resource
        private SSpareDao spareDao;
        @Resource
        private SelectIcloudResourceDao icloudDao;
        @Resource
        private SoftwareDao softwareDao;
        @Resource
        private CommserviceCreateDao commserviceCreateDao;
        @Resource
        private com.anchorcms.icloud.dao.unionCity.unionCityDao unionCityDao;
    }
