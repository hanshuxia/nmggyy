package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsConsultMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.OrderPayDao;
import com.anchorcms.icloud.dao.synergy.SynergyCreateDao;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPSettle;
import com.anchorcms.icloud.model.payment.SendMessageVO;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.service.payment.SettlementAndRefundService;
import com.anchorcms.icloud.service.synergy.ALDPayService;
import com.anchorcms.icloud.service.synergy.SynergyCreateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import java.util.*;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/19
 * @Desc
 */
@Service
@Transactional
public class SynergyCreateServiceImpl implements SynergyCreateService{
    public Content save(SAbility bean, Content c, ContentExt ext, ContentTxt t,
                        String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths,
                        String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
        if(charge==null){
            charge= ContentCharge.MODEL_FREE;
        }
        saveContent(c, ext, t, channelId, typeId, null,true,user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel=channelMng.findById(channelId);
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
//        java.sql.Date createDate = new java.sql.Date(System.currentTimeMillis());
        bean.setContent(c);
        //下面这些set挪到controller里了 by GJN
//        bean.setOperatorId(user.getUserId()+"");
//        bean.setReleaseId(user.getUserId()+"");发布的时候set
//        bean.setCreateDt(createDate);
//        bean.setReleaseDt(createDate);发布的时候set
//        bean.setUpdateDt(createDate);
//        bean.setStatus("1");
//        bean.setDeFlag("0");1为有效
//        bean.setCompanyId(user.getCompany().getCompanyId());
        dao.save(bean);
        afterSave(c);
        return c;

    }
    private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
                                Integer channelId,Integer typeId, Boolean draft,
                                Boolean contribute,CmsUser user, boolean forMember){
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
        if(contribute!=null&&contribute){
            bean.setStatus(ContentCheck.CONTRIBUTE);
        }else if (draft != null && draft) {
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

    /**
     * @Author 李利民
     * @param id 文章id
     * @Date 2016/12/21
     * @Desc 发布能力编辑
     */
    public SAbility byContentId(Integer id) {
        return dao.byAbilityId(id);
    }

    /**
     * @Author wanjinyou
     * @param id abilityId
     * @Date 2016/12/21
     * @Desc 发布能力三级页面跳转
     */
    public SAbility byAbilityId(Integer id) {
        return dao.bySAbilityId(id);
    }
    /**
     * @Author ly
     * @Date 2016/12/21
     * @Desc 发布能力删除
     */
    public SAbility deleteById(Integer id) {
        SAbility bean = byContentId(id);//获取能力实体类
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        bean = dao.delete(bean);// 执行删除
        afterDelete(contentBean);
        return bean;
    }

    /**
     * @Author ly
     * @Date 2016/12/21
     * @Desc 发布能力删除
     */
    public SAbility[] deleteByIds(Integer[] ids) {
        SAbility[] beans = new SAbility[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
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
        // 删除留言
        cmsConsultMng.deleteByContentId(id);
        //删除附件记录
        fileMng.deleteByContentId(id);
        bean.clear();
        bean = contentDao.deleteById(id);
        // 执行监听器

        return bean;
    }

    public Content[] deleteContentByIds(Integer[] ids) {
        Content[] beans = new Content[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteContentById(ids[i]);
        }
        return beans;
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
     * @author ly
     * @date 2016/12/21
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
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer memberId,
                                       int pageNo, int pageSize, Date startDate, Date endDate,
                                       String releaseId, String abilityType, String abilityName,
                                       String abilityCode, String status) {
        return dao.getPageBySelfCompany(null,memberId, false, false, Content.ContentStatus.all, null,
                siteId, channelId, pageNo, pageSize, startDate, endDate, releaseId,
                abilityType, abilityName, abilityCode, status);
    }

    /**
     * @author zhouyq
     * @date 2017-05-02
     * @desc 获得能力订单分页
     * @return 文章分页对象
     */
    public Pagination getAbilitySellerOrder(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, String state, String orderId){
        return dao.getAbilitySellerOrder(siteId, user, pageNo, pageSize, startDate, state, orderId);
    }

    /**
     * @author zhouyq
     * @date 2017-06-02
     * @desc 获得企业设备订单分页
     * @return 文章分页对象
     */
    public Pagination getDeviceSellerOrder(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, String state, String orderId){
        return dao.getDeviceSellerOrder(siteId, user, pageNo, pageSize, startDate, state, orderId);
    }

    /**
     * @param
     * @return
     * @Author zhouyq
     * @Date 2017/05/02
     * @Desc 根据id修改订单状态
     */
    public void mdyOrderStateById(String orderId, String state, String backReason) {
        SPOrder orderPayObj = orderPayDao.getOrderById(orderId);
        orderPayObj.setBackReason(backReason); // 驳回原因
        if (state != null) { // 修改状态
            dao.mdyOrderStateById(orderId, state);
        }
    }

    /**
     * @return
     * @author zhouyq
     * @Date 2017/5/3
     * @desc 软件退款 结算模块
     */
    @Resource
    ALDPayService ALDPayService;
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
                         String city, String orderId, String flag, String isDevice) throws Exception {
        SPOrder orderPayObj = orderPayDao.getOrderById(orderNo);
        SPSettle spSettle = orderPayObj.getSpPay().getSpSettle();
        serialNumber = GUIDGenerator.genGUID(); // 生成新的交易流水号
        SendMessageVO vo ;
        if (flag.equals("1")) { // 结算
            if (!(("1").equals(isDevice))) {
                // 处理手续费
                Map<String,Double> m = ALDPayService.calc(orderPayObj.getPrice(),accountType);
                double fee = m.get("fee");
                amount = (long)(m.get("price") * 100);
                ALDPayService.adminSaveSettle(orderId,fee,m.get("price"),"0");
            }

            vo = SettleService.tx1341(serialNumber, orderNo, remark, amount,
                    accountType,
                    bankID,
                    accountName,
                    accountNumber,
                    branchName,
                    province,
                    city);
        } else if (flag.equals("0")) { // 退款
            amount = (long) (orderPayObj.getPrice() * 100);
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
        if (spSettle == null) {
            spSettle = new SPSettle();
            String u = UUID.randomUUID().toString().replaceAll("-", "");
            spSettle.setId(u);
        }
        spSettle.setAccountType(accountType);
        spSettle.setAcountName(accountName);
        spSettle.setAcountNumber(accountNumber);
        spSettle.setBranchName(branchName);
        spSettle.setBankId(bankID);
        spSettle.setBankName(bankName);
        spSettle.setProvince(province);
        spSettle.setCity(city);
        if (fla.get("message").equals("success")) {
            spSettle.setAmount(spSettle.getAmount());
            spSettle.setSerialNumber(serialNumber);
            spSettle.setOrderNo(orderNo);
            spSettle.setFlag(flag);
            spSettle.setResultFlag("1");
            orderPayObj.getSpPay().setSpSettle(spSettle);
            if (flag.equals("1")) {
                orderPayObj.setState("40");
            } else if (flag.equals("0")) {
                orderPayObj.setState("31");
            }
            orderPayDao.updateOrder(orderPayObj);
            return "success";
        } else {
            spSettle.setAmount(orderPayObj.getSpPay().getAmount());
            spSettle.setSerialNumber(serialNumber);
            spSettle.setOrderNo(orderNo);
            spSettle.setFlag(flag);
            spSettle.setResultFlag("2");
            orderPayObj.getSpPay().setSpSettle(spSettle);
            orderPayDao.updateOrder(orderPayObj);
            // 退款失败
            return fla.get("message");
        }
    }


    /**
     *  @param sAbility
     * @param content
     * @param ext
     * @param t
     * @param tagArr
     * @param o3
     * @param o2
     * @param o1
     * @param attachmentPaths
     * @param attachmentNames
     * @param o
     * @param charge
     * @param chargeAmount
     * @param user
     * @param b   @Author 李利民
     * @Date 2016/12/22
     * @Desc 发布能力修改
     */
    public void updateAdility(SAbility sAbility, Content content, ContentExt ext, ContentTxt t, String[] tagArr, Object o3, Object o2, Object o1, String[] attachmentPaths, String[] attachmentNames, Integer o, Short charge, Double chargeAmount, CmsUser user, boolean b) {
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(content);
        //更新content
        content = updateContent(content, ext, t, attachmentPaths, attachmentNames, o, charge, user, b);
        //更新设备表
        dao.updateAdility(sAbility,content);
        // 执行监听器
        afterChange(content, mapList);
    }
    /**
     *@Author llm
     *@Date 2017/1/3 17:40
     *@Desc 更新能力content
     **/
    public Content updateContent(Content bean, ContentExt ext, ContentTxt txt, String[] picPaths,
                                 String[] picDescs, Integer channelId, Short charge, CmsUser user,
                                 boolean forMember) {

        // 更新主表
        Updater<Content> updater = new Updater<Content>(bean);
        bean = contentDao.updateByUpdater(updater);

        // 更新扩展表
        contentExtMng.update(ext);
        // 更新文本表
        contentTxtMng.update(txt, bean);
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
    /**
     * @author: gao jiangning
     * @desciption 2016/12/29 查询某公司下的能力列表 分页+条件
     */
    public Pagination getPageForCompany(String companyId, int pageNo, int pageSize, Date startDate, Date endDate, String releaseId, String abilityType, String abilityName, String abilityCode) {
        return dao.getPageForCompany(companyId, pageNo,pageSize,startDate,endDate,releaseId,abilityType,abilityName,abilityCode);
    }


    /**
     * @param id
     * @param statusType
     * @param channelId
     * @param user
     * @param charge
     * @param b
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2017/2/3
     * @Desc 能力管理-草稿-发布
     */
    public void updateState(Integer id, String statusType, Integer channelId, CmsUser user, Short charge, boolean b) {
        SAbility ability = byAbilityId(id);
        if ("2".equals(statusType)){
            ability.setStatusType("2");
            dao.update(ability);
        }else{
            ability.setStatusType("1");
            dao.update(ability);
        }

    }

    /**
     * @author ly
     * @date 2017/1/9 13:47
     * @desc 自定义标签——能力列表获取
     **/
    public List<SAbility> getList(Integer count, Integer orderBy, Integer listType,String abilityType) {
        return dao.getList(count, orderBy, listType,abilityType);
    }

    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private ContentExtMng contentExtMng;
    @Resource
    private ContentCountMng contentCountMng;
    @Resource
    private ContentCheckMng contentCheckMng;
    @Resource
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private CmsFileMng fileMng;
    @Resource
    private ContentDao contentDao;
    @Resource
    private SynergyCreateDao dao;
    @Resource
    private CmsConsultMng cmsConsultMng;
    @Resource
    private OrderPayDao orderPayDao;
    @Resource
    private SettlementAndRefundService SettleService;
    @Resource
    com.anchorcms.icloud.service.payment.impl.SendMessageServiceImpl SendMessageServiceImpl;
}
