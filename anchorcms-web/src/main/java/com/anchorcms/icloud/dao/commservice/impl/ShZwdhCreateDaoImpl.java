package com.anchorcms.icloud.dao.commservice.impl;


import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.ShZwdhCreateDao;
import com.anchorcms.icloud.model.commservice.SStiteManager;

import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
@Repository
public class ShZwdhCreateDaoImpl extends HibernateBaseDao implements ShZwdhCreateDao {
//    public Pagination getPageBySelf(String status) {
//        Finder f = Finder.create("select  bean from SSpareDemand bean");
//        return find(f);
//    }
    public List ZwdhManagelist(String status) {

        StringBuffer sql =  new StringBuffer();
        sql.append(" SELECT R.STITE_NAME,R.STITE_address FROM s_stite_manager R WHERE 1=1 ");
        if (status != null && !"".equals(status)) {//全部
            if ("1".equals(status)) {
                sql.append(" AND  R.ADDRESS = '内蒙古自治区政府'  ");
            } else if ("2".equals(status)) {
                sql.append(" AND  R.ADDRESS = '呼和浩特市'  ");
            } else if ("3".equals(status)) {
                sql.append(" AND  R.ADDRESS = '包头市'  ");
            } else if ("4".equals(status)) {
                sql.append(" AND  R.ADDRESS = '鄂尔多斯市'  ");
            }else if ("5".equals(status)) {
                sql.append(" AND  R.ADDRESS = '乌兰察布市' ");
            }else if ("6".equals(status)) {
                sql.append(" AND  R.ADDRESS = '乌海市' ");
            }else if ("7".equals(status)) {
                sql.append(" AND  R.ADDRESS = '呼伦贝尔市'  ");
            }else if ("8".equals(status)) {
                sql.append(" AND  R.ADDRESS = '通辽市'  ");
            }else if ("9".equals(status)) {
                sql.append(" AND  R.ADDRESS = '赤峰市'  ");
            }else if ("10".equals(status)) {
                sql.append(" AND  R.ADDRESS = '巴彦淖尔市'  ");
            }else if ("11".equals(status)) {
                sql.append(" AND  R.ADDRESS = '锡林郭勒盟' ");
            }else if ("12".equals(status)) {
                sql.append(" AND  R.ADDRESS = '阿拉善盟'  ");
            }else if ("13".equals(status)) {
                sql.append(" AND  R.ADDRESS = '兴安盟' ");
            }else if ("14".equals(status)) {
                sql.append(" AND  R.ADDRESS = '政府采购网' ");
            }else {
                sql.append(" AND R.STATUS = '"+status+"' ");
            }
        }
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        List list = query.list();
        return list;
    }


    protected Class getEntityClass() {
        return null;
    }
}
