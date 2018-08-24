package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SSpare;

import java.sql.Date;

/**
 * @Author zhouyq
 * @Date 2016/12/20
 * @Desc 备品备件服务接口
 */
public interface SSpareService {
    /**
     * @Author zhouyq
     * @Date 2016/12/20
     * @param spareType, spareName, spareDesc, companyType, addrCity, pageNo, pageSize
     * @return
     * @Desc 获取备品备件分页后信息
     */
    public Pagination getList(String spareType, String spareName, String spareDesc, String companyType, String addrCity, String addrProvince,
                              Integer pageNo, Integer pageSize);
    public Pagination getSSpareStorageList(String spareId, Integer pageNo, Integer pageSize);

    /*
    * 刘鹏
    * 备品备件信息上传
    *
    * */
    public Content saveSSpare(SSpare sse, Content c, ContentExt ext, ContentTxt t,
                             Integer channelId, Integer typeId, CmsUser user, boolean b, String[] attachmentPaths,
                              String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs);

    /**
     * @Author李洪波
     * @Date 2016/12/20
     * @Desc
     * 根据创建时间，备品分类，来源和所属企业来分类并分页
     */
    public Pagination findByTimeCateSourCompany(Date startDate, Date endDate, String publicType, String status, String spareType, String source, String companyId, Integer pageNo, Integer pageSize, Integer userId, String flag);

    /**
     * @Author李洪波
     * @param id
     * @param publicType
     * @Desc修改备品备件公开状态
     */
    public void update(String id, String publicType);


    /**
     * @Author李洪波
     * @param id
     * @param status
     * @Desc修改备品备件发布状态
     */
    public void updateSpareStatus(String id, String status, Date date,String flag);

    /**
     * @author李洪波
     * @Desc根据ID删除备品备件
     * @param id
     * @param flag
     */
    public void delete(String id, String flag);

    /**
     * @Author李洪波
     * @Desc备品备件编辑
     * @param spare
     * @param c
     * @param ext
     * @param t
     * @param attachmentPaths
     * @param attachmentNames
     * @param attachmentFilenames
     * @param picPaths
     * @param picDescs
     * @param channelId
     * @param typeId
     * @param user
     * @param charge
     * @param b
     */
    public void edit(SSpare spare, Content c, ContentExt ext, ContentTxt t,
                     String[] attachmentPaths, String[] attachmentNames,
                     String[] attachmentFilenames, String[] picPaths,
                     String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);


}
