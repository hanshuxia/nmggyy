package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SAbility;

import java.util.Date;
import java.util.List;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/20
 * @Desc
 */
public interface SynergyCreateDao {

    /**
     * @Author 阁楼麻雀
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/20
     * @Desc 发布能力保存
     */
    public SAbility save(SAbility bean);

    /**
     * @Author 李利民
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/21
     * @Desc 通过ID获取能力信息
     */
    public SAbility byAbilityId(Integer id);

    List<SAbility> byAbilityIds(String ids);

    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 通过ID获取能力信息
     */
    public SAbility bySAbilityId(Integer id);

    /***
     * @author ly
     * @date 2016/12/21
     * @param typeId 内容类型ID
     * @param userId 用户ID
     * @param topLevel 是否固顶
     * @param recommend 是否推荐
     * @param status 状态
     * @param checkStep 审核步骤
     * @param siteId 站点ID
     * @param channelId 栏目ID
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param startDate 查询开始时间
     * @param endDate 查询结束时间
     * @param releaseId 发布人
     * @param abilityType 能力分类
     * @param abilityName 能力名称
     * @param abilityCode 能力代码
     * @desc 获得自己发布的能力列表
     * @return
     */
    public Pagination getPageBySelfCompany(Integer typeId, Integer userId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus contentStatus, Byte checkStep, Integer siteId,
                                    Integer channelId, int pageNo, int pageSize, Date startDate,
                                    Date endDate, String releaseId, String abilityType,
                                    String abilityName, String abilityCode, String status);

    /**
     * @author zhouyq
     * @date 2017-05-02
     * @desc 获得能力订单分页
     * @return 文章分页对象
     */
    public Pagination getAbilitySellerOrder(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, String state, String orderId);

    /**
     * @author zhouyq
     * @date 2017-06-02
     * @desc 获得企业设备订单分页
     * @return 文章分页对象
     */
    public Pagination getDeviceSellerOrder(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, String state, String orderId);

    /**
     * @Author zhouyq
     * @Date 2017/05/02
     * @param
     * @return
     * @Desc 根据id修改订单状态
     */
    public void mdyOrderStateById(String orderId, String status);

    /**
     * @author ly
     * @param ability
     * @date 2016/12/21
     * @desc 发布能力删除
     */
    public SAbility delete(SAbility ability);

    /**
     * @Author 李利民
     * @param ability
     * @param c
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/22
     * @Desc 发布能力修改
     */
    void updateAdility(SAbility ability, Content c);

    /**
     * @author: gao jiangning
     * @desciption 2016/12/29 查询某公司下的能力列表 分页+条件
     */
    Pagination getPageForCompany(String companyId, int pageNo, int pageSize, Date startDate,
                                 Date endDate, String releaseId, String abilityType,
                                 String abilityName, String abilityCode);

    public List<SAbility> getList(Integer count, Integer orderBy, Integer listType,String abilityType);

    public SAbility update(SAbility ability);
}
