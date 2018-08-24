package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.CompanyDao;
import com.anchorcms.icloud.dao.synergy.SCompanyManagementDao;
import com.anchorcms.icloud.model.common.SCfcaApply;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.synergy.SCompanyManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/27 0027
 * @Desc 后台公司列表业务实现类
 */
@Service
@Transactional
public class SCompanyManagementServiceImpl implements SCompanyManagementService {
    public Pagination getCompanyListByPage(String companyName, String companyCode,String mobile, Date createDtStart, Date createDtEnd, Integer pageNo,String status) {
        Finder finder = Finder.create("select bean from SCompany bean where 1=1");

        if(status!=null&&!"".equals(status)){
            if(status.equals("1")) {
                finder.append(" and bean.isRecommend=:status");
                finder.setParam("status", status);
            }else{
                finder.append(" and (bean.isRecommend=:status");
                finder.setParam("status", status);
                finder.append(" or bean.isRecommend is null)");
            }
        }

        finder = appendFinder(finder, companyName, companyCode, mobile, createDtStart, createDtEnd);
        if(pageNo==null){
            pageNo=1;
        }
        return dao.findByPage(finder, pageNo, 10);
    }

    public Pagination getCompanyListByPage_check(String companyName, String companyCode, String mobile, Date createDtStart, Date createDtEnd, Integer pageNo, String status) {
        Finder finder = Finder.create("select bean from SCompany bean where 1=1");

        if(status!=null&&!"".equals(status)){
            if(status.equals("2")) {
                finder.append(" and (bean.status=:status");
                finder.setParam("status", status);
                finder.append(" or bean.status is null)");
            }else if(status.equals("0")){
                finder.append(" and bean.status=:status");
                finder.setParam("status", status);
            }else{
                finder.append(" and bean.status=:status");
                finder.setParam("status", status);
            }
        }

        finder = appendFinder(finder, companyName, companyCode, mobile, createDtStart, createDtEnd);
        if(pageNo==null){
            pageNo=1;
        }
        return dao.findByPage(finder, pageNo, 10);
    }

    private Finder appendFinder(Finder f, String companyName, String companyCode, String mobile, Date createDtStart, Date createDtEnd){
        if(companyName!=null && !"".equals(companyName.trim())) {
            f.append(" and bean.companyName like :companyName");
            f.setParam("companyName", "%" + companyName + "%");
        }
        if(companyCode!=null){
            f.append(" and bean.companyCode like :companycode");
            f.setParam("companycode","%"+companyCode+"%");
        }
        f.append(" order by bean.updateDt desc");
        return f;
    }
    /**
     * @Author jsz
     * @Date 2016/1/10
     * @Desc 自定义标签——企业制造能力展示
     */
    public List<SCompany> getList(Integer count, Integer orderBy, Integer listType) {
        return dao.getList(count, orderBy, listType);
    }
    /**
     * @author liuyang
     * @Date 2017/5/10 19:06
     * @return
     */
    public Pagination getCredentialsList(String companyId,String companyName, String status , Integer pageNo, Integer pageSize) {
        Pagination page = dao.getPage(companyId,companyName,status, pageNo, pageSize);
        return page;
    }
    @Resource
    private SCompanyManagementDao dao;

    /**
     * @author liuyang
     * @Date 2017/5/11 9:59
     * @return 资质审核通过驳回
     */
    public void modifyAptitudeStateById(String aptitudeId, String status, java.util.Date updateDt, String backReason){
        SCompanyAptitude sCompanyAptitude = dao.getCfcaEntity(aptitudeId);
        sCompanyAptitude.setBackReason(backReason);
        dao.updateBean(sCompanyAptitude);
        dao.modifyCfcaStateById(aptitudeId, status,updateDt,backReason);
    }
    /**
     * @author liuyang
     * @Date 2017/5/11 18:22
     * @return
     */
    public SCompany byCompanyId(String id) {
        return cDao.getCompanyBy(id);
    }
    @Resource
    private CompanyDao cDao;
}
