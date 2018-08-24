package com.anchorcms.icloud.service.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyDevice;
import com.anchorcms.icloud.model.synergy.SCompanyDiploma;

import java.sql.Date;
import java.util.List;

/**
 * @author dongxuecheng
 * @Date 2017/2/20 13:18
 * @return
 * @description
 */
public interface SynergyCompanyAdminService {

    public SCompany save(SCompany company, Integer channelId, Integer modelId, CmsUser user, Short charge, boolean b);

    public SCompanyDiploma add(Integer channelId, Integer modelId, CmsUser user);

    public Pagination getDiplomaPage(Integer siteId, String companyId, int pageNo, int pageSize);

    public SCompanyDiploma saveDiploma(Integer channelId, Integer modelId, CmsUser user,String companyId,SCompanyDiploma diploma);

    public SCompanyDiploma findDiplomaById(Integer id);

    public SCompany findSCompanyById(String id);

    public SCompanyDiploma updateDiploma(Integer channelId, Integer modelId, CmsUser user, Integer diplomaId, String diplomaType, String issuser, Date operationDt, Date deadlineDt, String diplomaPic);

    public SCompanyDiploma deleteDiplomaById(Integer id);

    public Content saveDevice(SCompanyDevice device, Content c, ContentExt ext, ContentTxt t, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember);

    public Pagination getDevicePage(Integer siteId, String companyId, int cpn, int pageSize);

    public SCompanyDevice findDeviceById(Integer id);

    public SCompanyDevice deleteDeviceById(Integer id);

    public SCompanyDevice updateDevice(Integer id, SCompanyDevice device, String detailDesc, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean forMember);

    public Content saveDetail(Content c, ContentExt ext, ContentTxt t, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember,String company);

    public Content updateDetail(String detailDesc, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean forMember,String companyId);

    public List<SCompanyDiploma> getDiplomaList(Integer count, Integer orderBy, Integer listType);

    public List<SCompanyDevice> getDeviceList(Integer count, Integer orderBy, Integer listType);

    public SCompany findSCompanyBytypeAndname(SCompany bean);
}
