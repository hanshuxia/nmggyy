package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.RepairDemandDao;
import com.anchorcms.icloud.dao.supplychain.SrepairdemandDao;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import org.apache.shiro.util.Assert;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhouyq
 * @Date 2016/12/26
 * @Desc ������Դ����_����ԱDao��ʵ����
 */
@Repository
public class SrepairdemandDaoImpl extends HibernateBaseDao<SRepairRes, String> implements SrepairdemandDao {

    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairName, pageNo, pageSize
     * @return
     * @Desc ��ȡ������Դ�б��ҳ����Ϣ
     */
    public Pagination getPage(String repairName,String status,Integer pageNo, Integer pageSize) {
        Finder f = getFinder(repairName,status);
        return find(f, pageNo, pageSize);
    }

    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairName
     * @return
     * @Desc ��ȡ������Դ�б���Ϣ
     */
    private Finder getFinder(String repairName,String status) {
        if(status!=null &&!status.equals("") ){
            if(status.equals("2")){
                Finder f = Finder.create("from SRepairRes bean where DATEDIFF(bean.deadlineDt,now())>=0 and bean.status = 2");
                if (repairName != null && repairName.trim().length() > 0) {
                    f.append(" and bean.repairName like:repairName");
                    f.setParam("repairName", "%" + repairName + "%");
                }
                f.append(" order by bean.createDt desc");
                return f;
            }
            Finder f = Finder.create("from SRepairRes bean where  bean.status =  '" + status + "'"); // �����ۼ�¼
            if (repairName != null && repairName.trim().length() > 0) {
                f.append(" and bean.repairName like:repairName");
                f.setParam("repairName", "%" + repairName + "%");
            }
            f.append(" order by bean.createDt desc");
            return f;
        }
        if(repairName==null){
            Finder f = Finder.create("from SRepairRes bean where  bean.status = 2 and DATEDIFF(bean.deadlineDt,now())>=0");
            f.append(" order by bean.createDt desc");
            return f;
        }else {
            Finder f = Finder.create("from SRepairRes bean where 1=1 "); // �����ۼ�¼
            if (repairName != null && repairName.trim().length() > 0) {
                f.append(" and bean.repairName like:repairName");
                f.setParam("repairName", "%" + repairName + "%");
            }
            f.append(" and bean.status in (0,2,3)");
            f.append(" order by bean.createDt desc");
            return f;
        }
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/8 12:33
     * @return
     * @description�����������
     */
    public Pagination getZxxqList(String repairName, Integer pageNo, Integer pageSize,String statusType) {
        Finder f = getZxxqFinder(repairName,statusType);
        f.append(" order by bean.releaseDt desc");
        return find(f, pageNo, pageSize);
    }

    private Finder getZxxqFinder(String repairName,String statusType) {
        Finder f = Finder.create("from SRepairDemand bean where 1=1");
        f.append(" and bean.state in ('0','2','3') ");
        if (repairName != null && repairName.trim().length() > 0) {
            f.append(" and bean.repairName like:repairName");
            f.setParam("repairName", "%" + repairName + "%");
        }
        if (statusType != null && !"".equals(statusType)) {
            f.append(" and bean.state =:statusType");
            f.setParam("statusType",statusType);
        }
        return f;
    }
    /**
    *@Author 苏和 【562763562@qq.com】
    *@Date 2017/5/3 11:28
    *@Return
    */
    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize,
                              java.sql.Date startDate, String status, String paramu) {
        Finder f = null;
        if(status.equals("1")) {
             f = Finder.create("select  bean from SSupplychainOrder bean where bean.state=6 ");
            if (startDate != null) {
                f.append(" and bean.orderDt =:startDate");
                f.setParam("startDate", startDate);
            }
        } else if (status.equals("2")) {
            f = Finder.create("select  bean from SPAdminSettle bean where bean.state=0 and bean.flag=1 ");
        } else if (status.equals("3")) {
            f = Finder.create("select  bean from SPAdminSettle bean where bean.state=2 and bean.flag=1 ");
        }
//        f.append(" order by bean.buyDt desc,bean.userId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId
     * @return
     * @Desc ����id��ȡ������Դ��ϸ��Ϣ(�ʺϷ���tr�б���ʽ)
     */
//    public List<SRepairDemand> findDetailAndPreviewById(String repairId) {
//        StringBuffer hql = new StringBuffer();
//        hql.append(" from SRepairDemand sd, SRepairDemandObj sdo where sd.repairId = sdo.repairId ");
//        Query query = getSession().createQuery(hql.toString());
//        List<SRepairDemand> list = query.list();
//        return list;
//    }

    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId
     * @return
     * @Desc ����id����������Դʵ����
     */
    public SRepairRes findDetailAndPreviewByIdDemand(String repairId) {
        SRepairRes entitiy = get(repairId);
        return entitiy;
    }

    /**
     * @param repairId, state
     * @return
     * @Author zhouyq
     * @Date 2016/12/26
     * @Desc ����id�޸�������Դ״̬��ͨ�������أ�
     */
    public void mdyRepairDemandStateById(String repairId, String status) {
        StringBuffer hql = new StringBuffer();
        if (status.equals("0")) { // ����
            hql.append(" UPDATE SRepairRes  SET status = '0'" + " WHERE repairId = '" + repairId + "'");
        } else if (status.equals("3")) { // ͨ��
            hql.append(" UPDATE SRepairRes  SET status = '3'" + " WHERE repairId = '" + repairId + "'");
        }
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // ִ��delete��update��insert into
    }

    /**
     * @param repairIds, status
     * @return
     * @author zhouyq
     * @Date 2017/01/06
     * @Desc ����id�����޸�������Դ״̬��ͨ�������أ�
     */
    public void setRepairDemandStateByIds(String repairIds, String status, String backReason){
        StringBuffer hql = new StringBuffer();
        String[] strArr = repairIds.split(","); // id�ַ�������
        SRepairRes res;
        Content content;
        List<Map<String, Object>> mapList;
        for (int j = 0; j < strArr.length; j ++) { // ����ִ��update
            if ((status.equals("0")) && (strArr[j].trim().length() > 0)) { // ����
                String nopassIds = strArr[j].trim();
                res  = resdao.findDetailAndPreviewByIdDemand(nopassIds);
                res.setBackReason(backReason);
                hql.append(" UPDATE SRepairRes  SET releaseDt = CURDATE(),status = '0' WHERE repairId = '"+nopassIds+"' ");
                Query query = getSession().createQuery(hql.toString());
                query.executeUpdate();
                hql.setLength(0);
            } else if (status.equals("3") ) { // ͨ��
                String passIds = strArr[j].trim();
                res  = resdao.findDetailAndPreviewByIdDemand(passIds);
                content  = res.getContent();
                // 执行监听器
                mapList = preChange(content);
                //已发布时将content状态改为已审核
                content.setStatus(ContentCheck.CHECKED);
                // 更新content表
                Updater<Content> updater = new Updater<Content>(content);
                content = contentDao.updateByUpdater(updater);
                afterChange(content, mapList);

                hql.append(" UPDATE SRepairRes  SET releaseDt = CURDATE(),status = '3' WHERE repairId = '"+passIds+"' ");
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
    @Resource
    private SrepairdemandDao resdao;

    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId
     * @return
     * @Desc ����idɾ��������Դ
     */
    public void delRepairDemandById(String repairId){
        StringBuffer hql = new StringBuffer();
//        hql.append(" delete from SRepairDemand sd, SRepairDemandObj sdo where sd." + repairId + "= sdo." + repairId);
        hql.append("delete from SRepairRes sd where sd.repairId = '"+ repairId +"' ");
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // ����Ӵ˾���򲻻�ִ��ɾ������
    }

    /**
     * @Author Don��ѧ��
     * @Date 2016/12/26
     * @param repairId, state
     * @return
     * @Desc ����id��ȡ���󣬸Ķ�״̬��ͨ�������أ�
     */
    public int setStatus(String repairId, String status,String backReason) {
        StringBuffer hql = new StringBuffer();
        if (status.equals("0")) { // ����
            hql.append(" UPDATE SRepairDemand  SET releaseDt = CURDATE(),state = '0'" + " WHERE repairId = '" + repairId + "'");
        } else if (status.equals("3")) { // ͨ��
            hql.append(" UPDATE SRepairDemand  SET releaseDt = CURDATE(),state = '3'" + " WHERE repairId = '" + repairId + "'");
        }
        Query query = getSession().createQuery(hql.toString());
        return query.executeUpdate(); // ִ��delete��update��insert into
    }

    public void delete(String repairId) {
        StringBuffer hql = new StringBuffer();
//        hql.append(" delete from SRepairDemand sd, SRepairDemandObj sdo where sd." + repairId + "= sdo." + repairId);
        hql.append("delete from SRepairDemand sd where sd.repairId = '"+ repairId +"' ");
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // ����Ӵ˾���򲻻�ִ��ɾ������
    }


    /**
     * @param repairIds, status
     * @return
     * @author zhouyq
     * @Date 2017/01/06
     * @Desc ����id�����޸�������Դ״̬��ͨ�������أ�
     */
    public int setRepairDemandstatus(String repairIds, String status,String backReason){
        StringBuffer hql = new StringBuffer();
        String[] strArr = repairIds.split(","); // id�ַ�������
        SRepairDemand sRepairDemand;
        Content content;
        for (int j = 0; j < strArr.length; j ++) { // ����ִ��update


            if ((status.equals("0")) && (strArr[j].trim().length() > 0)) { // ����
                String nopassIds = strArr[j].trim();
                SRepairDemand sRepairDemandObj = repairDemandDao.selectReapirDemand(nopassIds);
                sRepairDemandObj.setBackReason(backReason);
                hql.append(" UPDATE SRepairDemand  SET releaseDt = CURDATE(),state = '0' WHERE repairId = '"+nopassIds+"' ");
                Query query = getSession().createQuery(hql.toString());
                query.executeUpdate();
                hql.setLength(0);
            } else if (status.equals("3") ) { // ͨ��
                String passIds = strArr[j].trim();

                sRepairDemand = repairDemandDao.selectReapirDemand(passIds);
                content = sRepairDemand.getContent();
                //审核状态
                content.setStatus(ContentCheck.CHECKED);
                // 更新content表
                Updater<Content> updater = new Updater<Content>(content);
                content = contentDao.updateByUpdater(updater);

                hql.append(" UPDATE SRepairDemand  SET releaseDt = CURDATE(),state = '3' WHERE repairId = '"+passIds+"'");
                Query query = getSession().createQuery(hql.toString());
                query.executeUpdate();
                hql.setLength(0);
            }
        }
        return 1;
    }

    public List getSpareStatisticsJson(String region) {
        StringBuffer sql = new StringBuffer();
//        hql.append(" delete from SRepairDemand sd, SRepairDemandObj sdo where sd." + repairId + "= sdo." + repairId);
        sql.append(" SELECT s.*,e.value from \n" +
                "(SELECT e.SPARE_TYPE,SUM(e.INVENT_COUNT) as sum from s_spare e where e.SPARE_TYPE is NOT null and e.SOURCE LIKE '%"+region+"%' GROUP BY e.SPARE_TYPE) s \n" +
                " LEFT JOIN pub_code e on s.SPARE_TYPE=e.KEY");
        SQLQuery sqlQueryl = getSession().createSQLQuery(sql.toString());
        List list = sqlQueryl.list();
        return list;
    }

    protected Class<SRepairRes> getEntityClass() {
        return SRepairRes.class;
    }
        @Resource
        RepairDemandDao repairDemandDao;
        @Resource
        private ContentDao contentDao;
}
