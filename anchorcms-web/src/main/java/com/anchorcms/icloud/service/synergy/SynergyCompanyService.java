package com.anchorcms.icloud.service.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.model.synergy.SCompanyDevice;
import com.anchorcms.icloud.model.synergy.SCompanyDiploma;

import java.sql.Date;
import java.util.List;

/**
 * Created by ly on 2016/12/27.
 */
public interface SynergyCompanyService {

    public SCompany save(SCompany company, Integer channelId, Integer modelId, CmsUser user, Short charge, boolean b);

    public SCompanyDiploma add(Integer channelId, Integer modelId, CmsUser user);

    public Pagination getDiplomaPage(Integer siteId, CmsUser user, int pageNo, int pageSize);

    public SCompanyDiploma saveDiploma(Integer channelId, Integer modelId, CmsUser user, SCompanyDiploma diploma);

    public SCompanyDiploma findDiplomaById(Integer id);

    /**
     *@Author hansx
     *@Date 2017/5/11 08:48
     *@Desc 根据企业id查找企业资质
     **/
    public SCompanyAptitude findAptitudeByCompanyId(String id);

    public SCompanyDiploma updateDiploma(Integer channelId, Integer modelId, CmsUser user, Integer diplomaId, String diplomaType, String issuser, Date operationDt, Date deadlineDt, String diplomaPic,String diplomaDiscribe);

    public SCompanyDiploma deleteDiplomaById(Integer id);

    public Content saveDevice(SCompanyDevice device, Content c, ContentExt ext, ContentTxt t, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember);

    public Pagination getDevicePage(Integer siteId, CmsUser user, int cpn, int pageSize);

    public SCompanyDevice findDeviceById(Integer id);

    public SCompanyDevice deleteDeviceById(Integer id);

    public SCompanyDevice updateDevice(Integer id, SCompanyDevice device, String detailDesc, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean forMember);

    public Content saveDetail(Content c, ContentExt ext, ContentTxt t, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember);

    public Content updateDetail(String detailDesc, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean forMember);

    public List<SCompanyDiploma> getDiplomaList(Integer count, Integer orderBy, Integer listType);

    public List<SCompanyDevice> getDeviceList(Integer count, Integer orderBy, Integer listType);

    /**
     * @Author zhouyq
     * @Date 2017/05/11
     * @Desc 保存企业资质信息
     **/
    Content saveAptitude(SCompanyAptitude sCompanyAptitude, Content c, ContentExt ext, ContentTxt t, String[] picPaths, String[] picDescs,  String[] picPaths1, String[] picDescs1,
                         String[] picPaths2, String[] picDescs2, String[] picPaths3, String[] picDescs3, String[] picPaths4, String[] picDescs4,Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);

    /**
     *  根据企业名称查找企业信息列表
     *  @author: hansx
     *  @date 2017/11/30 11:04
     */
    public List<SCompany> findSCompanyByName(String companyName);

}
