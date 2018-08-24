package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.supplychain.SSpareReleaseAdminsManageDao;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;

import org.apache.shiro.util.Assert;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2016/12/29.
 */
    /**
     * @Author liuyang
     * @Email
     * @Date 2016/12/26
     * @Desc 备品备件审核管理_管理员Dao层实现类
     */
    @Repository
    public class SSpareReleaseAdmnsManageDaoImpl extends HibernateBaseDao<SSpareDemand, String> implements SSpareReleaseAdminsManageDao {
        public Pagination getPage(String statusType,String requestTheme, Date createDt,Date deadlineDt, Integer pageNo, Integer pageSize) {
            Finder f = getFinder(statusType,requestTheme,createDt,deadlineDt);
            return find(f, pageNo, pageSize);
        }
        @Resource
        private SSpareReleaseAdminsManageDao dao;
        @Resource
        private ContentDao contentDao;
        private Finder getFinder(String statusType,String requestTheme,Date createDt,Date deadlineDt) {
            Finder f = Finder.create("from SSpareDemand bean where  bean.state in ('2','3','0') ");//显示状态为23的数据
            if (statusType != null && !"".equals(statusType)) {
                f.append(" and bean.state =:statusType");
                f.setParam("statusType",statusType);
            }
           if (requestTheme != null && requestTheme.trim().length() > 0) {
//               f.append(" and bean.requestTheme=:requestTheme");
//               f.setParam("requestTheme", requestTheme);
               f.append(" and bean.requestTheme like:requestTheme");
               f.setParam("requestTheme", "%" + requestTheme + "%");
           }
            if (createDt != null && !"".equals(createDt)) {
                f.append(" and bean.createDt >=:createDt");
                f.setParam("createDt", createDt );
            }
            if (deadlineDt != null && !"".equals(deadlineDt)) {
                f.append(" and bean.deadlineDt >=:deadlineDt");
                f.setParam("deadlineDt", deadlineDt );
            }
            f.append(" order by bean.createDt desc");
            return f;
        }

        /**
         * 根据id获取备品备件明细信息(适合返回tr列表形式)
         *
         * @return
         */

        public SSpareDemand findDetailAndPreviewByIdDemand(String demandId) {
            SSpareDemand entitiy = get(demandId);
            return entitiy;
        }
        /**
         * 根据id修改备品备件审核状态（通过、驳回）
         * 暂时写demo
         * @param demandId, state
         * @return
         */
        public void setRepairDemandStateById(String demandId, String state,Date releaseDt,String flag,String backReason){
            StringBuffer hql = new StringBuffer();
            String[] strArr = demandId.split(","); // id字符串数组
            SSpareDemand de;
            Content content;
            List<Map<String, Object>> mapList;
            for (int j = 0; j < strArr.length; j ++) { // 批量执行update
                if ((state.equals("0")) && (strArr[j].trim().length() > 0)) { // 驳回
                    String id = strArr[j].trim();
                    de = dao.findDetailAndPreviewByIdDemand(id);
                    de.setBackReason(backReason);
                    hql.append(" UPDATE  SSpareDemand   SET releaseDt = CURDATE(),state = '0' WHERE demandId = '"+id+"' ");
                    Query query = getSession().createQuery(hql.toString());
                    query.executeUpdate();
                    hql.setLength(0);
                } else if (state.equals("3") ) { // 通过
                    String dd = strArr[j].trim();
                    // 更新content表
                    SSpareDemand sSpareDemand = dao.findDetailAndPreviewByIdDemand(dd);
                    content = sSpareDemand.getContent();
                    // 执行监听器
                    mapList = preChange(content);
                    content.setStatus(ContentCheck.CHECKED);
                    Updater<Content> updater = new Updater<Content>(content);
                    content = contentDao.updateByUpdater(updater);
                    afterChange(content, mapList);

                    hql.append(" UPDATE SSpareDemand SET releaseDt = CURDATE(), state = '3' WHERE demandId = '"+dd+"' ");
                    Query query = getSession().createQuery(hql.toString());
                    query.executeUpdate();
                    hql.setLength(0);
                }

            }
        }
        private List<Map<String, Object>> preChange(Content content) {
            if (listenerList != null) {
                int len = listenerList.size();
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(
                        len);
                for (ContentListener listener : listenerList) {
                    list.add(listener.preChange(content));
                }
                return list;
            } else {
                return null;
            }
        }

        private void afterChange(Content content, List<Map<String, Object>> mapList) {
            if (listenerList != null) {
                Assert.notNull(mapList);
                Assert.isTrue(mapList.size() == listenerList.size());
                int len = listenerList.size();
                ContentListener listener;
                for (int i = 0; i < len; i++) {
                    listener = listenerList.get(i);
                    listener.afterChange(content, mapList.get(i));
                }
            }
        }
        @Resource
        private List<ContentListener> listenerList;
        public void modifyRepairDemandStateById(String demandId, String state,Date releaseDt,String backReason) {
            StringBuffer hql = new StringBuffer();
            if (state.equals("0")) {
                hql.append(" UPDATE SSpareDemand  SET releaseDt = CURDATE(), state = '0'" + " WHERE demandId = '"+demandId+"'");
            } else if (state.equals("3")) {
                hql.append(" UPDATE SSpareDemand  SET releaseDt = CURDATE(),state = '3'" + " WHERE demandId = '"+demandId+"'");
            }
            Query query = getSession().createQuery(hql.toString());
            query.executeUpdate(); // 执行delete，update和insert into
        }


        /**
         * 根据id删除备品备件信息
         *
         * @param demandId
         * @return
         */
        public void delRepairDemandById(String demandId){
            StringBuffer hql = new StringBuffer();
//        hql.append(" delete from SRepairDemandEntity sd, SRepairDemandObjEntity sdo where sd." + repairId + "= sdo." + repairId);
            hql.append(" delete SSpareDemand as sd where sd.demandId = " + demandId);
            Query query = getSession().createQuery(hql.toString());
            query.executeUpdate(); // 必须加此句否则不会执行删除操作
        }

        protected Class<SSpareDemand> getEntityClass() {
            return SSpareDemand.class;
        }
    }

