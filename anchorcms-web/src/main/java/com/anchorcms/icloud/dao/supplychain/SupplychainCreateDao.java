package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import com.anchorcms.icloud.model.synergy.SCompany;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by machao on 2016/12/21.
 */
public interface SupplychainCreateDao {
    List<SSpare> getAll();
    public SRepairRes addResource(SRepairRes srr);

    /**
     *
     * @param title
     * @param typeId
     * @param currUserId
     * @param inputUserId
     * @param topLevel
     * @param recommend
     * @param status
     * @param checkStep
     * @param siteId
     * @param modelId
     * @param channelId
     * @param orderBy
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getPage(String title, Integer typeId, Integer currUserId,
                              Integer inputUserId, boolean topLevel, boolean recommend,
                              Content.ContentStatus status, Byte checkStep, Integer siteId, Integer modelId,
                              Integer channelId, int orderBy, int pageNo, int pageSize);

    int insertsSpareDemandEntity(SSpareDemand sSpareDemand);
    SSpareDemand updateSpareDemandEntity(SSpareDemand su);

    List<SSpareDemand> getAllSpare();
    List<SSpareDemand> search(String requestTheme);
    List<SSpareDemand> BpbjqgManagelist();
    List<SSpareDemand> BpbjqgManagelistSearch(String requestTheme, String type);
    /**
     * @author machao
     * @Date 2016/12/29 15:31
     * 金牌老师傅推荐
     * @return
     */
    public Pagination getJplsftjList(Integer channelId, Integer siteId, Integer modelId,
                                     Integer memberId, int pageNo, int pageSize,Date startDate, Date endDate,  String releaseName, String repairType,String flag,String companyName,String workYear,String addrCity);
    /**
     * @author machao
     * @Date 2017/1/7 14:42
     * @return
     * 金牌老师傅推荐、撤销
     */
    boolean recommendManage(String id,String flag);
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:06
     * @return
     * 备品备件求购管理明细预览
     */
    List<SSpareDemand> ManagelistSearch(String uid);
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:04
     * @return
     * 备品备件求购管理编辑（取数据）
     */
    SSpareDemand ManagelistSearc(String uid);
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:02
     * @return
     * 备品备件求购管理撤回
     */
    int ManagelistRe(String uid) throws UnsupportedEncodingException;
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:03
     * @return
     * 备品备件求购管理发布
     */
    int ManagelistIss(String uid) throws UnsupportedEncodingException;
    int ManagelistDell(String uid) throws UnsupportedEncodingException;



    public SSpareDemand findById(String id);
/**
 * @author 苏和 13739980760
 * @Date 2017/1/12 14:57
 * @return
 * 求购删除新
 */
    public SSpareDemand delete(SSpareDemand spareDemand);


    /**
     * @author 苏和 13739980760
     * @Date 2017/1/5 15:24
     * @return
     */
    public Pagination getPage(String isUrgency, String requestTheme,
                              Integer pageNo, Integer pageSize) throws UnsupportedEncodingException;


    /**
     * @author 苏和 13739980760
     * @Date 2017/1/6 15:11
     * @return
     * 备品备件求购管理
     */
    public Pagination getPageBySelf(String requestType, Integer typeId, Integer userId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus contentStatus, Byte checkStep, Integer siteId,
                                    Integer channelId, int pageNo, int pageSize, Date startDate,
                                    Date endDate, String releaseId, String dealType,
                                    String expectPrice, String isUrgency, String requestTheme,String invoiceType,String status);


    /**
     * @author liuyang
     * @Date 2017/1/8 0:04
     * @return
     * 众修资源列表展示
     */
    Pagination getPageBySiteIdZxxqId(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);




    public Pagination getBP(/*Integer channelId, Integer siteId, Integer modelId,
                                     Integer memberId,*/ int pageNo, int pageSize/*,Date startDate, Date endDate,  String releaseName, String repairTyp*/,
                            String theme/*, String type*/);


    /**
     * @author 苏和 13739980760
     * @Date 2017/1/10 16:57
     * @return
     * 备品备件求购编辑
     */

    public  SSpareDemand updateByUpdater(Updater<SSpareDemand> updater);

    /**
     * @author machao
     * @Date 2017/1/17 10:29
     * @return
     * 自定义标签，维修资源展示
     */
    public List<SRepairRes> getRepairResList( Integer orderBy, Integer listType);
    public List<SRepairRes> getRepairResList(Integer count, Integer orderBy, Integer listType);
    /**
     * @author machao
     * @Date 2017/1/17 10:56
     * @return
     * 自定义标签-备品备件求购
     */
    public List<SSpareDemand> getSpareDemandResList(Integer count, Integer orderBy, Integer listType,String requestType);
    /**
     * @author gengwenlong
     * @Date 2017/2/18 11:12
     * @return
     * 自定义标签-备品备件求购即将过期
     */
    public List<SSpareDemand> getSpareDemandResLists(Integer count, Integer orderBy, Integer listType);
    /**
     * @author machao
     * @Date 2017/2/9 14:00
     * @return
     *自定义标签-备品备件
     */
    public List<SSpare> getSpareList(Integer count, Integer orderBy, Integer listType, String spareType);
    /**
     * @author machao
     * @Date 2017/2/19 14:28
     * @return
     * 企业下拉框
     */
    public List<SCompany> getCompanyLists(Integer count, String orderBy, Integer listType);
    /**
     * @author machao
     * @Date 2017/1/17 11:01
     * @return
     * 自定义标签-维修需求展示
     */
    public List<SRepairDemand> getRepairDemandList(Integer count, Integer orderBy, Integer listType,String demandType);
    /**
     * @author gengwenlong
     * @Date 2017/2/17 10:44
     * @return
     * 自定义标签-维修需求即将过期展示
     */
    public List<SRepairDemand> getRepairDemandLists(Integer count, Integer orderBy, Integer listType);
}
