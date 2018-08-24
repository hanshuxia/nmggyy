package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import com.anchorcms.icloud.model.synergy.SCompany;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by machao on 2016/12/21.
 */
public interface SupplychainCreateService {
    public List<SSpare> getList();
    // public SRepairRes addResource(SRepairRes srr);

    Content save(SRepairRes srr, Content c, ContentExt ext, ContentTxt t, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, boolean forMember);
    /**
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页大小
     * @return 文章分页对象
     */
    public Pagination getPageForMember(String title, Integer channelId,
                                       Integer siteId, Integer modelId, Integer memberId,
                                       int pageNo, int pageSize);


    public  int insertsSpareDemandEntity(SSpareDemand sSpareDemand);
    public  SSpareDemand updateSpareDemandEntity(SSpareDemand su);

    public Content supplychain_save(SSpareDemand sSpareDemand, Content c, ContentExt ext, ContentTxt t,
                                    Integer channelId, Integer typeId, CmsUser user, boolean b);

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:06
     * @return
     * 备品备件求购管理明细预览
     */
    public List<SSpareDemand> SearchList(String uid);
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:03
     * @return
     * 备品备件求购管理编辑（取数据）
     */
    public SSpareDemand SearchLis(String uid);
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:02
     * @return
     * 备品备件求购管理撤回
     */
    int ManagelistRe(String uid) throws UnsupportedEncodingException;
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:02
     * @return
     * 备品备件求购管理发布
     */
    int ManagelistIss(String uid) throws UnsupportedEncodingException;
//    int ManagelistDell(String uid) throws UnsupportedEncodingException;

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:01
     * @return
     * 备品备件求购管理删除
     */
    public SSpareDemand delete(String demandId);


    public List<SSpareDemand> getListSpare();
    public List<SSpareDemand> searchList(String theme);
    public List<SSpareDemand> manageList();
    public List<SSpareDemand> ManageSearchList(String theme, String type);
    /**
     * @author machao
     * @Date 2016/12/29 15:27
     * 金牌老师傅列表查询
     * @return
     */
    public Pagination getJplsftjPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize,
                                              Date startDate, Date endDate,  String releaseName, String repairType,String flag,String companyName,String workYear,String addrCity);
    /**
     * @author machao
     * @Date 2017/1/7 14:38
     * @return
     * 金牌老师傅推荐、撤销
     */
    boolean recommendManage(String id,String flag);

    /**
     * @author dongxuecheng
     * @Date 2017/1/6 8:52
     * @return
     * @description备品备件求购发布
     */
    public Content save(SSpareDemand sSpareDemand, Content c, ContentExt ext, ContentTxt t,
                        String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths,
                        String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b,
                        String demandObjListJsonString);

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/5 15:24
     * @return
     */
    public Pagination getList(String isUrgency, String requestTheme,
                              Integer pageNo, Integer pageSize) throws UnsupportedEncodingException;

/**
 * @author 苏和 13739980760
 * @Date 2017/1/6 15:03
 * @return
 * 备品备件求购管理
 */
public Pagination getPageForMember(String requestType,Integer channelId, Integer siteId, Integer modelId,
                                   Integer memberId,int pageNo, int pageSize, Date startDate,
                                   Date endDate,String releaseId, String dealType,
                                   String expectPrice, String isUrgency, String requestTheme,String invoiceType,String status);


    Pagination getBPB(/*Integer channelId, Integer siteId, Integer modelId,
                                         Integer memberId,*/ int pageNo, int pageSize/*,Date startDate, Date endDate,  String releaseName, String repairTyp*/,
                      String theme/*, String type*/);


    /**
     * @Author 闫浩芮
     * 更新需求信息
     * @Date 2017/01/04
     */
    void update(SSpareDemand sSpareDemand, Content c, ContentExt ext, ContentTxt t,
                String[] attachmentPaths, String[] attachmentNames,
                String[] attachmentFilenames, String[] picPaths,
                String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b,
                String demandObjListJsonString);

    /**
     * @author machao
     * @Date 2017/1/17 10:26
     * @return
     * 自定义标签-维修资源
     */
    public List<SRepairRes> getRepairResList( Integer orderBy, Integer listType);
    public List<SRepairRes> getRepairResList( Integer count, Integer orderBy, Integer listType);
    /**
     * @author machao
     * @Date 2017/2/9 13:58
     * @return
     * 自定义标签-备品备件
     */
    public List<SSpare> getSpareList(Integer count, Integer orderBy, Integer listType, String spareType);

    /**
     * @author machao
     * @Date 2017/2/19 14:24
     * @return
     * 企业信息下拉框
     */
    public List<SCompany> getCompanyLists(Integer count, String orderBy, Integer listType);
    /**
     * @author machao
     * @Date 2017/1/17 10:55
     * @return
     * 自定义标签-备品备件求购
     */
    public List<SSpareDemand> getSpareDemandResList(Integer count, Integer orderBy, Integer listType,String requestType);
    /**
     * @author gengwenlong
     * @Date 2017/2/18 11:10
     * @return
     * 自定义标签-备品备件求购即将过期
     */
    public List<SSpareDemand> getSpareDemandResLists(Integer count, Integer orderBy, Integer listType);
    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return getRepairDemandList
     * 自定义标签-维修需求展示
     */
    public List<SRepairDemand> getRepairDemandList(Integer count, Integer orderBy, Integer listType,String demandType);
    /**
     * @author gengwenlong
     * @Date 2017/2/17 10:43
     * @return
     * 自定义标签-维修需求即将过期展示
     */
    public List<SRepairDemand> getRepairDemandLists(Integer count, Integer orderBy, Integer listType);
}
