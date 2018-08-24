package com.anchorcms.icloud.service.software;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.software.SSoftware;
import com.anchorcms.icloud.model.software.SSoftwareBuy;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ly on 2017/1/4.
 */
public interface SoftwareService {
    public Content save(SSoftware software, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember);

    public Pagination getSoftwarePage(Integer siteId, CmsUser user, int pageNo, int pageSize, String softwareType, String softwareName, String status);

    public SSoftware findById(Integer id);

    public SSoftware addBrowse(Integer id);

    public SSoftware deleteById(Integer id);

    public SSoftware update(Integer id, SSoftware software, String detailDesc, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean forMember);

    public SSoftware update(Integer id, String status, Integer channelId, CmsUser user, Short charge, boolean b);

    public void addBuyRecord(SSoftware software, CmsUser user);

    public Pagination getSoftwareBuyPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate,String status,String paramu);
    /**
     *@Author jsz
     *@Date 2017/1/10
     *@desc  软件标签查询
     */
    public List<SSoftware> getList(Integer count, Integer orderBy, Integer listType,Integer listId);

    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/18 0018 11:07
     */
    public void passMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/18 0018 11:07
     */
    public void rejectMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/20 0020 10:37
     */
    public void deleteMany(String demandIdsStr);

    Map pay(int softwareid ,CmsUser user,String institutionID, String paymentNo, String orderNo, Double amount, Double fee, String payerID, String payerName, String usage, String remark, String notificationURL, String payees, String bankID,String bankName, Integer accountType, String cardType);

    void receiveNotice(String message, String signature);

    String settle(String serialNumber,
                String orderNo,
                String remark,
                long amount,
                int accountType,
                String bankID,
                String bankName,
                String accountName,
                String accountNumber,
                String branchName,
                String province,
                String city, int id, String flag) throws Exception;


    SSoftwareBuy findOrderById(int orderId);

    void saveOrder(SSoftwareBuy buy);

    void editOrderStatus(int orderId, String setstatus, String onlinePay);

    //
    /**
     * @Author zhouyq
     * @Date 2017/03/24
     * @param orderId
     * @return
     * @Desc 根据id修改退款订单状态（拒绝）
     */
    public void mdyOrderStateById(int orderId, String status, String backReason);

    SSoftwareBuy findSOrderByPNo(String paymentNo);
}
