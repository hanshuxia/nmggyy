package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/7 0007
 * @Desc
 */
public interface CloudCenterResourceService {
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date startDt, Date endDt, Integer managerId, String resourceType, String state, String addrCity);
    /**
     * @Author jsz
     * 删除y云资源信息
     * @Date 2016/1/7
     */
    public SIcloudManage delete(Integer managerId);
    /**
     * @Author jsz
     * 获得云资源信息
     * @Date 2016/1/7
     */
    public SIcloudManage byManagerId(Integer managerId);
    /**
     * @Author jsz
     * 更新云资源信息
     * @Date 2016/1/9
     */
    public SIcloudManage update(
            HttpServletRequest request, HttpServletResponse response, ModelMap model,
            Integer managerId,
            String release_people,
            String center_name,
            String resourceName,//云资源名称
            String resourceType,
            String specVersion,
            String parameter,
            String rentPrice,
            Double price,
            String unit,
            String addrCity,
            String contact,//联系人
            String mobile,//联系电话
            String email,//邮箱
            //cms表相关参数
            Integer modelId, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames,String detailDesc, String[] picPaths, String[] picDescs, Integer channelId, Short charge, String nextUrl);
    /**
     *@Author jsz
     *@Date 2017/1/10
     *@desc  云计算政策标签查询
     */
    public List<SIcloudManage> getList(Integer count, Integer orderBy, Integer listType,Integer areaType,Integer state);

    /**
     * 修改数据状态（发布资源功能）
     * @param managerId
     */
    void updateState(Integer managerId);

    /**
     * @Author wanjinyou
     * @Date 2016/1/20
     * @Desc 云资源管理下架 【用户公司一条需求的明细】
     */
    public SIcloudManage updateState(Integer id, String status, Integer channelId, CmsUser user, Short charge, boolean b);

    /**
     * @Author wanjinyou
     * @Date 2016/1/20
     * @Desc 云资源管理删除 【用户公司一条需求的明细】
     */
    public SIcloudManage deleteById(Integer id);

    /**
     * 置为通过
     * @Author 闫浩芮
     * @param demandIdsStr 需要置为通过的Id字符串
     * @Date 2017/2/18 0018 11:07
     */
    public void passMany(String demandIdsStr);
    /**
     * 置为驳回
     * @Author 闫浩芮
     * @param demandIdsStr 需要置为驳回的Id字符串
     * @Date 2017/2/18 0018 11:07
     */
    public void rejectMany(String demandIdsStr);
    /**
     * 批量删除
     * @Author 闫浩芮
     * @param demandIdsStr 需要批量删除的Id字符串
     * @Date 2017/2/20 0020 10:37
     */
    public void deleteMany(String demandIdsStr);

}
