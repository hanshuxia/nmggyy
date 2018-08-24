package com.anchorcms.icloud.dao.software;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.software.SSoftware;

import java.util.List;
import java.util.Map;

/**
 * Created by ly on 2017/1/4.
 */
public interface SoftwareDao {

    public SSoftware save(SSoftware bean);

    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize, String softwareType, String softwareName, String status);

    public SSoftware findById(Integer id);

    public SSoftware deleteById(Integer id);

    public SSoftware update(SSoftware software);

    public Pagination getSitePage(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
    /**
     *@Author jsz
     *@Date 2017/1/10
     *@desc 软件标签查询
     */
    public List<SSoftware> getList(Integer count, Integer orderBy, Integer listType,Integer listId);
    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/17 0017 17:25
     */
    public int passMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/17 0017 18:40
     */
    public int rejectMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/20 0020 10:35
     */
    public int deleteMany(String demandIdsStr);
}
