package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;

import java.util.Date;
import java.util.List;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/27 0027
 * @Desc 后台公司列表 dao接口
 */
public interface SCompanyManagementDao {

    /**
     * 分页查询，带条件
     * @param finder
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pagination findByPage(Finder finder, int pageNo, int pageSize);
    /**
     * @Author jsz
     * @Date 2016/1/10
     * @Desc 自定义标签——企业制造能力展示
     */
    public List<SCompany> getList(Integer count, Integer orderBy, Integer listType);
    /**
     * @author liuyang
     * @Date 2017/5/10 19:08
     * @return
     */
    public Pagination getPage(String companyId, String companyName,String status, Integer pageNo, Integer pageSize);

    public SCompanyAptitude getCfcaEntity(String id);

    List<SCompanyAptitude> getList(CmsUser user);

    Pagination getPage(CmsUser user, int cpn, int i);

    SCompanyAptitude findById(String id);

    SCompanyAptitude  updateBean(SCompanyAptitude bean );

    public void modifyCfcaStateById(String aptitudeId, String status, Date updateDt, String backReason);
}
