package com.anchorcms.icloud.service.software.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.software.SoftwareBuyDao;
import com.anchorcms.icloud.dao.software.SoftwareDao;
import com.anchorcms.icloud.model.payment.PaySettlementRefund;
import com.anchorcms.icloud.model.payment.SendMessageVO;
import com.anchorcms.icloud.model.software.SSoftware;
import com.anchorcms.icloud.model.software.SSoftwareBuy;
import com.anchorcms.icloud.service.payment.SettlementAndRefundService;
import com.anchorcms.icloud.service.payment.Tx1311Service;
import com.anchorcms.icloud.service.software.SoftwareService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * Created by ly on 2017/1/4.
 */
@Service
@Transactional
public class SoftwareServiceImpl implements SoftwareService {

    /**
     * @author ly
     * @date 2017/1/5 19:16
     * @desc 保存软件信息
     **/
    public Content save(SSoftware bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
        if (charge == null) {
            charge = ContentCharge.MODEL_FREE;
        }
        saveContent(c, ext, t, channelId, typeId, null, true, user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel = channelMng.findById(channelId);
        c.addToChannels(channel);
        // 保存附件
        if (attachmentPaths != null && attachmentPaths.length > 0) {
            for (int i = 0, len = attachmentPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(attachmentPaths[i])) {
                    c.addToAttachmemts(attachmentPaths[i],
                            attachmentNames[i], attachmentFilenames[i]);
                }
            }
        }
        // 保存图片集
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    c.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }
        bean.setContent(c);
        dao.save(bean);
        afterSave(c);
        return c;

    }

    public Pagination getSoftwarePage(Integer siteId, CmsUser user, int pageNo, int pageSize, String softwareType, String softwareName, String status) {
        return dao.getPage(siteId, user, pageNo, pageSize, softwareType, softwareName, status);
    }

    public SSoftware findById(Integer id) {
        SSoftware bean = dao.findById(id);
        return bean;
    }

    /**
     * 浏览次数+1
     *
     * @param id
     * @return
     */
    public SSoftware addBrowse(Integer id) {
        SSoftware bean = dao.findById(id);
        Integer browse = bean.getBrowse() + 1;
        bean.setBrowse(browse);
        dao.update(bean);
        return bean;
    }


    public SSoftware deleteById(Integer id) {
        SSoftware bean = findById(id);//获取软件实体类
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        dao.deleteById(id);
        afterDelete(contentBean);
        return bean;
    }

    /**
     * @author ly
     * @date 2017/1/5 19:21
     * @desc 更新软件信息
     **/
    public SSoftware update(Integer id, SSoftware bean, String detailDesc,
                            String[] attachmentPaths, String[] attachmentNames,
                            String[] attachmentFilenames, String[] picPaths,
                            String[] picDescs, Integer channelId, CmsUser user,
                            Short charge, boolean forMember) {
        SSoftware software = findById(id);
        software.setCompanyName(bean.getCompanyName());
        software.setSoftwareType(bean.getSoftwareType());
        software.setSoftwareName(bean.getSoftwareName());
        software.setIsOnline(bean.getIsOnline());
        software.setPayType(bean.getPayType());
        software.setPrice(bean.getPrice()); //价格
        software.setSoftwareSize(bean.getSoftwareSize());
        software.setSoftwareHref(bean.getSoftwareHref());
        software.setAddrProvince(bean.getAddrProvince());
        software.setAddrCity(bean.getAddrCity());
        software.setAddrCounty(bean.getAddrCounty());
        software.setAddrStreet(bean.getAddrStreet());
        software.setContact(bean.getContact());
        software.setMobile(bean.getMobile());
        software.setUpdateDt(bean.getUpdateDt());
        Content c = software.getContent();
        ContentExt ext = c.getContentExt();
        //富文本
        ContentTxt txt = c.getContentTxt();
        txt.setTxt(detailDesc);
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(c);
        //更新content
        c = updateContent(c, ext, txt, attachmentPaths, attachmentNames, attachmentFilenames,
                picPaths, picDescs, channelId, charge, user, forMember);
        dao.update(software);
        // 执行监听器
        afterChange(c, mapList);
        return null;
    }

    /**
     * @author ly
     * @date 2017/1/5 19:21
     * @desc 更新软件审核状态
     **/
    public SSoftware update(Integer id, String status, Integer channelId, CmsUser user, Short charge, boolean b) {
        SSoftware software = findById(id);
        Content content = software.getContent();
        if (status != null) {
            // 执行监听器
            List<Map<String, Object>> mapList = preChange(content);
            software.setStatus(status);
            if (Integer.parseInt(status) == 1) {//已发布时将content状态改为已审核
                content.setStatus(ContentCheck.CHECKED);
            } else {//其他未审核状态
                content.setStatus(ContentCheck.CONTRIBUTE);
            }
            // 更新content表
            Updater<Content> updater = new Updater<Content>(content);
            content = contentDao.updateByUpdater(updater);
            //更新软件表
            dao.update(software);
            // 执行监听器
            afterChange(content, mapList);
        }

        return software;
    }

    public void addBuyRecord(SSoftware software, CmsUser user) {
        SSoftwareBuy buy = new SSoftwareBuy();
        buy.setUserId(user.getUserId());
        buy.setUserName(user.getUserExt().getRealname());
        buy.setSoftwareId(software.getSoftwareId());
        buy.setSoftwareName(software.getSoftwareName());
        buy.setBuyDt(new Date(System.currentTimeMillis()));
        buy.setCreaterId(user.getUserId());
        buy.setCreateDt(new Date(System.currentTimeMillis()));
        buyDao.save(buy);
        return;
    }

    public Pagination getSoftwareBuyPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, String status, String paramu) {
        return buyDao.getPage(siteId, user, pageNo, pageSize, startDate, status, paramu);
    }

    public Content updateContent(Content bean, ContentExt ext, ContentTxt txt,
                                 String[] attachmentPaths, String[] attachmentNames,
                                 String[] attachmentFilenames, String[] picPaths,
                                 String[] picDescs, Integer channelId, Short charge,
                                 CmsUser user, boolean forMember) {

        // 更新主表
        Updater<Content> updater = new Updater<Content>(bean);
        bean = contentDao.updateByUpdater(updater);

        // 更新扩展表
        contentExtMng.update(ext);
        // 更新文本表
        contentTxtMng.update(txt, bean);
        bean.getAttachments().clear();
        if (attachmentPaths != null && attachmentPaths.length > 0) {
            for (int i = 0, len = attachmentPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(attachmentPaths[i])) {
                    bean.addToAttachmemts(attachmentPaths[i],
                            attachmentNames[i], attachmentFilenames[i]);
                }
            }
        }
        // 更新图片集
        bean.getPictures().clear();
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    bean.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }

        return bean;
    }

    private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
                                Integer channelId, Integer typeId, Boolean draft,
                                Boolean contribute, CmsUser user, boolean forMember) {
        Channel channel = channelMng.findById(channelId);
        bean.setChannel(channel);
        bean.setType(contentTypeMng.findById(typeId));
        bean.setUser(user);
        Byte userStep;
        if (forMember) {
            // 会员的审核级别按0处理
            userStep = 0;
        } else {
            CmsSite site = bean.getSite();
            userStep = user.getCheckStep(site.getSiteId());
        }
        // 流程处理
        if (contribute != null && contribute) {
            bean.setStatus(ContentCheck.CONTRIBUTE);
        } else if (draft != null && draft) {
            // 草稿
            bean.setStatus(DRAFT);
        } else {
            if (userStep >= bean.getChannel().getFinalStepExtends()) {
                bean.setStatus(ContentCheck.CHECKED);
            } else {
                bean.setStatus(ContentCheck.CHECKING);
            }
        }
        // 是否有标题图
        bean.setHasTitleImg(!StringUtils.isBlank(ext.getTitleImg()));
        bean.init();
        // 执行监听器
        preSave(bean);
        contentDao.save(bean);
        contentExtMng.save(ext, bean);
        contentTxtMng.save(txt, bean);
        ContentCheck check = new ContentCheck();
        check.setCheckStep(userStep);
        contentCheckMng.save(check, bean);
        contentCountMng.save(new ContentCount(), bean);
        return bean;
    }

    public Content deleteContentById(Integer id) {
        Content bean = contentDao.findById(id);
        // 执行监听器
        preDelete(bean);
        // 移除tag
        contentTagMng.removeTags(bean.getTags());
        bean.getTags().clear();
        // 删除评论
        cmsCommentMng.deleteByContentId(id);
        //删除附件记录
        fileMng.deleteByContentId(id);
        bean.clear();
        bean = contentDao.deleteById(id);
        return bean;
    }

    private void preSave(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.preSave(content);
            }
        }
    }

    private void afterSave(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterSave(content);
            }
        }
    }

    private List<Map<String, Object>> preChange(Content content) {
        if (listenerList != null) {
            int len = listenerList.size();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(
                    len);
            for (ContentListener listener : listenerList) {
                list.add(listener.preChange(content));
            }
            return list;
        } else {
            return null;
        }
    }

    private void afterChange(Content content, List<Map<String, Object>> mapList) {
        if (listenerList != null) {
            Assert.notNull(mapList);
            Assert.isTrue(mapList.size() == listenerList.size());
            int len = listenerList.size();
            ContentListener listener;
            for (int i = 0; i < len; i++) {
                listener = listenerList.get(i);
                listener.afterChange(content, mapList.get(i));
            }
        }
    }

    private void preDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.preDelete(content);
            }
        }
    }

    private void afterDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterDelete(content);
            }
        }
    }

    /**
     * @author jsz
     * @date 2017/1/10
     * @desc 自定义标签——软件列表获取
     **/
    public List<SSoftware> getList(Integer count, Integer orderBy, Integer listType, Integer listId) {
        return dao.getList(count, orderBy, listType, listId);
    }

    /**
     * @Author 闫浩芮
     * 管理员置为通过需求
     * @Date 2017/2/17 0017 18:39
     */
    public void passMany(String demandIdsStr) {
        if (',' == (demandIdsStr.charAt(demandIdsStr.length() - 1))) {
            demandIdsStr = demandIdsStr.substring(0, demandIdsStr.length() - 1);
        }
        dao.passMany(demandIdsStr);
    }

    /**
     * @Author 闫浩芮
     * 管理员置为驳回需求
     * @Date 2017/2/17 0017 18:39
     */
    public void rejectMany(String demandIdsStr) {
        if (',' == (demandIdsStr.charAt(demandIdsStr.length() - 1))) {
            demandIdsStr = demandIdsStr.substring(0, demandIdsStr.length() - 1);
        }
        dao.rejectMany(demandIdsStr);
    }

    /**
     * @Author 闫浩芮
     * 管理员批量删除
     * @Date 2017/2/17 0017 18:39
     */
    public void deleteMany(String ids) {
        if (',' == (ids.charAt(ids.length() - 1))) {
            ids = ids.substring(0, ids.length() - 1);
            String[] idarr = ids.split(",");
            for (int i = 0; i < idarr.length; i++) {
                deleteById(Integer.parseInt(idarr[i]));
            }
        }
    }


    public Map pay(int orderId, CmsUser user, String institutionID, String paymentNo, String orderNo, Double amount, Double fee,
                   String payerID, String payerName, String usage, String remark,
                   String notificationURL, String payees, String bankID, String bankName, Integer accountType,
                   String cardType) {

        SSoftwareBuy buy = this.findOrderById(orderId);
        buy.setUserId(user.getUserId());
        //buy.setUserName(user.getUserExt().getRealname());
        buy.setUserName(payerName);
        buy.setBuyDt(new Date(System.currentTimeMillis()));
        buy.setInstitutionId(institutionID);
        buy.setPaymentNo(paymentNo);
        buy.setOrderNo(orderNo);
        buy.setAmount(amount);
        buy.setFee(fee);
        buy.setPayerId(user.getUserId());
        //buy.setpayerName;
        buy.setUeage(usage);
        buy.setRemark(remark);
        buy.setPayees(payees);
        buy.setBankId(bankID);
        buy.setAccountType(accountType);
        buy.setCardType(cardType);
        buyDao.save(buy);
        Long Allamount = new Double(amount * 100).longValue();
        long fees = new Double(fee * 100).longValue();
        return Tx1322service.checkCode(institutionID, paymentNo, orderNo, Allamount, fees, payerID, payerName,
                usage, remark, notificationURL, payees, bankID, accountType, cardType);
    }

    /**
     * @return 接受付款信息
     * @author ldong
     * @Date 2017/2/20 22:26
     */

    public void receiveNotice(String message, String signature) {
        message = message.replace(" ", "+");
        Map resultMessage = ReceiveNoticeService.receiveNotice(message, signature);
        String status = String.valueOf(resultMessage.get("Status"));
        String paymentNo = (String) resultMessage.get("PaymentNo");
        SSoftwareBuy bean = buyDao.findByPaymentNo(paymentNo);
        int id = bean.getSoftwareId();
        if ("20".equals(status)) {
//支付成功
            bean.setStatus("2");
        } else if ("10".equals(status)) {
//支付失败
            bean.setStatus(status);
        }
        buyDao.update(bean);
        SSoftware software = dao.findById(id);
        software.setDownload(software.getDownload() + 1);
        dao.update(software);
        return;
    }

    /**
     * @return 软件退款 结算模块
     * @author ldong
     * @Date 2017/3/22 10:07
     */

    public String settle(String serialNumber,
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
                         String city, int id, String flag) throws Exception {
        SSoftwareBuy software = buyDao.findbean(id);
        amount = (long) (software.getAmount() * 100);
        orderNo = software.getOrderNo();
        serialNumber = GUIDGenerator.genGUID(); //生成新的交易流水号
        SendMessageVO vo ;
        if (flag.equals("1")) {
            vo = SettleService.tx1341(serialNumber, orderNo, remark, amount,
                    accountType,
                    bankID,
                    accountName,
                    accountNumber,
                    branchName,
                    province,
                    city);
        } else if (flag.equals("0")) {
            vo = SettleService.tx1343(serialNumber, orderNo, remark, amount,
                    accountType,
                    bankID,
                    accountName,
                    accountNumber,
                    branchName,
                    province,
                    city);
        } else {
            return "参数错误";
        }

        Map<String, String> fla = SendMessageServiceImpl.sendMessage(vo.getMessage(), vo.getSignature(), vo.getTxCode(), vo.getTxName(), vo.getFlag());
        PaySettlementRefund po = software.getPaySettlementRefund();
        if (po == null) {
            po = new PaySettlementRefund();
            String u = UUID.randomUUID().toString().replaceAll("-", "");
            po.setId(u);
        }
        po.setAccountType(accountType);
        po.setAcountName(accountName);
        po.setAcountNumber(accountNumber);
        po.setBranchName(branchName);
        po.setBankId(bankID);
        po.setBankName(bankName);
        po.setProvince(province);
        po.setCity(city);
        if (fla.get("message").equals("success")) {
            po.setAmount(software.getAmount());
            po.setSerialNumber(serialNumber);
            po.setOrderNo(orderNo);
            po.setFlag(flag);
            po.setResultFlag("1");
            software.setPaySettlementRefund(po);
            if (flag.equals("1")) {
                software.setStatus("40");
            } else if (flag.equals("0")) {
                software.setStatus("31");
            }
            buyDao.update(software);
            return "success";
        } else {
            po.setAmount(software.getAmount());
            po.setSerialNumber(serialNumber);
            po.setOrderNo(orderNo);
            po.setFlag(flag);
            po.setResultFlag("2");
            software.setPaySettlementRefund(po);
            buyDao.update(software);
            //退款失败
            return fla.get("message");
        }

    }

    /**
     * @param
     * @return
     * @Author zhouyq
     * @Date 2017/03/24
     * @Desc 根据id修改退款订单状态（拒绝）
     */
    public void mdyOrderStateById(int orderId, String status, String backReason) {
        SSoftwareBuy softObj = buyDao.findbean(orderId);
        softObj.setBackReason(backReason);
        if (status != null) {
            buyDao.mdyOrderStateById(orderId, status);
        }
    }

    public SSoftwareBuy findSOrderByPNo(String paymentNo) {
        return buyDao.findByPaymentNo(paymentNo);
    }

    /**
     * @return 通过主键得到订单对象
     * @author ldong
     * @Date 2017/3/24 16:30
     */
    public SSoftwareBuy findOrderById(int orderId) {
        return buyDao.findbean(orderId);
    }

    public void saveOrder(SSoftwareBuy buy) {
        buyDao.save(buy);
    }

    /**
     * @return 编辑订单表的状态
     * @author ldong
     * @Date 2017/3/24 16:29
     */
    public void editOrderStatus(int orderId, String setstatus, String onlinePay) {
        SSoftwareBuy sbuy = buyDao.findbean(orderId);
        sbuy.setStatus(setstatus);
        if (onlinePay!=null){
            sbuy.setOnlinePay(onlinePay);
        }
        buyDao.update(sbuy);
    }

    @Resource
    com.anchorcms.icloud.service.payment.impl.SendMessageServiceImpl SendMessageServiceImpl;
    @Resource
    private SoftwareService softwareService;
    @Resource
    private com.anchorcms.icloud.service.payment.ReceiveNoticeService ReceiveNoticeService;
    @Resource
    private Tx1311Service Tx1322service;
    @Resource
    private SoftwareDao dao;
    @Resource
    private SoftwareBuyDao buyDao;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentDao contentDao;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private ContentExtMng contentExtMng;
    @Resource
    private ContentCountMng contentCountMng;
    @Resource
    private ContentCheckMng contentCheckMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private CmsFileMng fileMng;
    @Resource
    private SettlementAndRefundService SettleService;
}
