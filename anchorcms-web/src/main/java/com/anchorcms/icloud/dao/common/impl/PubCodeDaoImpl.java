package com.anchorcms.icloud.dao.common.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.common.PubCodeDao;
import com.anchorcms.icloud.model.common.PubCode;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Copyright @ 2017 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2017/1/13 0013
 * @Desc 公共字典表Dao实现类
 */
@Repository
public class PubCodeDaoImpl extends HibernateBaseDao<PubCode,Integer> implements PubCodeDao {

    /**
     * 获得父节点下子节点List的方法
     *
     * @param parentId
     * @return
     */
    public List<PubCode> getCodeListByParent(Integer parentId) {
        Finder finder = Finder.create("select bean from PubCode bean where bean.parentCodeId=:parentId");
        finder.setParam("parentId",parentId);
        finder.setCacheable(true);
        return find(finder);
    }

    /**
     * 根据typeCode获取所有第一父节点List的方法
     *
     * @param typeCode
     * @return
     */
    public List<PubCode> getGrandCodeList(String typeCode) {
        Finder finder = Finder.create("select bean from PubCode bean where bean.typeCode=:typeCode and bean.parentCodeId is null");
        finder.setParam("typeCode",typeCode);
        finder.setCacheable(true);
        return find(finder);
    }

    /**
     * 用typeCode 和key 获取唯一pubcode对象
     *
     * @param typeCode
     * @param parentKey 应为typeKey...
     */
    public PubCode findUniqueCode(String typeCode, String parentKey) {
        StringBuffer hql= new StringBuffer("from PubCode pub where pub.typeCode='");
        hql.append(typeCode).append("'").append(" and pub.key='").append(parentKey).append("'");
        return (PubCode) findUnique(hql.toString());
    }

    public PubCode findById(Integer id) {
        return get(id);
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */

    /**
     * @author dongxuecheng
     * @Date 2017/2/10 15:57
     * @return
     * @description根据条件查出对应的筛选条件
     */
    public List<PubCode> getMenu(Integer count,Integer level,String orderBy,String menuType){
        Finder f = Finder.create(" select  bean from PubCode bean where bean.typeCode=:typeCode and bean.level=:level ");
        f.setParam("typeCode",menuType);
        f.setParam("level",level);
        if(orderBy!=null){
           f.append(" order by bean.sortNo ASC ");
        }
        f.setCacheable(true);
        if(count!=null){
            f.setMaxResults(count);
        }
        return find(f);
    }

    @Override
    protected Class<PubCode> getEntityClass() {
        return PubCode.class;
    }
}
