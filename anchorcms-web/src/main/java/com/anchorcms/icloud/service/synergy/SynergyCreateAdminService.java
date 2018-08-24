package com.anchorcms.icloud.service.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SAbility;

import java.util.Date;
import java.util.List;

/**
 * @author dongxuecheng
 * @Date 2017/2/20 13:19
 * @return
 * @description
 */
public interface SynergyCreateAdminService {

    /**
     * @Author 阁楼麻雀
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/19
     * @Desc 发布能力保存
     */
    public Content save(SAbility ability, Content c, ContentExt ext, ContentTxt t,
                        String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths,
                        String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);
    /**
     * @Author 李利民
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/21
     * @Desc 发布能力编辑
     */
    public SAbility byContentId(Integer id);

    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 发布能力三级页面跳转
     */
    public SAbility byAbilityId(Integer id);


    /**
     * @author ly
     * @date 2016-12-21
     * @param ids 文章id
     * @desc 删除能力
     * @return
     */
    public SAbility[] deleteByIds(Integer[] ids);

    /**
     * @author ly
     * @date 2016-12-21
     * @param channelId 栏目ID
     * @param siteId 站点ID
     * @param modelId modelId
     * @param memberId 会员ID
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param startDate 查询开始时间
     * @param endDate 查询结束时间
     * @param releaseId 发布人
     * @param abilityType 能力分类
     * @param abilityName 能力名称
     * @param abilityCode 能力代码
     * @desc 获得文章分页
     * @return 文章分页对象
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId,
                                       Integer memberId, int pageNo, int pageSize, Date startDate,
                                       Date endDate, String releaseId, String abilityType,
                                       String abilityName, String abilityCode, String status);
    /**
     * @Author 李利民
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/22
     * @Desc 发布能力修改
     */
    void updateAdility(SAbility sAbility, Content content, ContentExt ext, ContentTxt t, String[] tagArr, Object o3, Object o2, Object o1, String[] attachmentPaths, String[] attachmentNames, Integer o, Short charge, Double chargeAmount, CmsUser user, boolean b);

    /**
     * @author: gao jiangning
     * @desciption 2016/12/29 查询某公司下的能力列表 分页+条件
     */
    Pagination getPageForCompany(String companyId, int pageNo, int pageSize, Date startDate,
                                 Date endDate, String releaseId, String abilityType,
                                 String abilityName, String abilityCode);

    public List<SAbility> getList(Integer count, Integer orderBy, Integer listType, String abilityType);

    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2017/2/3
     * @Desc 能力管理-草稿-发布
     */
    void updateState(Integer id, String statusType, Integer channelId, CmsUser user, Short charge, boolean b);
}
