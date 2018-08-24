package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.supplychain.SSpare;

import java.sql.Date;
import java.util.Map;

/**
 * @Author zhouyq
 * @Email
 * @Date 2016/12/20
 * @Desc 备品备件Dao层接口
 */
public interface SSpareDao {
    /**
     * @Author zhouyq
     * @Date 2016/12/20
     * @param spareType, spareName, spareDesc, companyType, addrCity, pageNo, pageSize
     * @return
     * @Desc 获取备品备件分页后信息
     */
    public Pagination getPage(String spareType, String spareName, String spareDesc, String companyType, String addrCity, String addrProvince,
                              Integer pageNo, Integer pageSize);

    public Pagination getSSpareStorageList(String spareId, Integer pageNo, Integer pageSize);
    /**
    * 刘鹏
    * 备品备件信息上传
    * */
    public SSpare save(SSpare sse);
    /**
     ** @Author zhouyq
     * @Date 2017/01/07
     * @param
     * @return
     * @Desc 获取备品备件分页后信息（调用公共方法）
     */
    Pagination getPageBySiteIdBpbjById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /**
     * @Author李洪波
     * @param startDate 开始发布时间
     * @param endDate 终止发布时间
     * @param publicType 公开状态
     * @param status 发布状态
     * @param spareType 备品类型
     * @param source 来源
     * @param companyId 公司号
     * @param pageNo 页码
     * @param pageSize 页面大小
     * @param userId 用户id
     * @return 分页内容
     * @Desc 根据创建时间，备品分类，来源和所属企业来分类并分页
     */
    public Pagination findByTimeCateSourCompany(Date startDate, Date endDate, String publicType, String status, String spareType, String source, String companyId, Integer pageNo, Integer pageSize, Integer userId,  String flag);
    //根据备品ID查询实体类信息
    public SSpare findByID(String id);

    /**
     * @Author李洪波
     * 备品备件公开状态修改
     */
    public void updateSSpare(String id, String publicType);
    /**
     * @Author李洪波
     * 备品备件发布状态修改
     */
    public void updateSpareStatus(String id, String status,Date date, String flag);

    /**
     * @author李洪波
     * @Desc根据ID删除备品备件
     * @param bean
     */
    public SSpare delete(SSpare bean);

    /**
     * @author李洪波
     * @Desc备品备件编辑
     * @param updater
     * @return
     */
    public SSpare updateByUpdater(Updater<SSpare> updater);

}
