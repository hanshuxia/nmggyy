package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.utils.StrUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.controller.logistics.DeliverGoods;
import com.anchorcms.icloud.dao.synergy.OrderPayDao;
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.model.logistics.SLogistics;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPSettle;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.common.PubCodeService;
import com.anchorcms.icloud.service.logistics.LogisticsService;
import com.anchorcms.icloud.service.synergy.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.*;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/19
 * @Desc 协调制造
 */
@Controller
public class SynergyCreateController {
    private static final Logger log = LoggerFactory.getLogger(SynergyCreateController.class);
    public static final String ABILITY_LIST = "tpl.synergyAbilityList";
    public static final String ABILITY_ADD = "tpl.synergyAbilityAdd";
    public static final String ABILITY_EDIT = "tpl.synergyAbilityEdit";
    public static final String ABILITY_VIEW = "tpl.synergyAbilityView";
    public static final String ABILITY_PREVIEW = "tpl.synergyAbilityPreView";
    public static final String XTZZ_PREVIEW = "tpl.xtzzAbilityPreView";
    public static final String ABILITY_MANAGE_VIEW = "tpl.synergyAbilityManageView";
    public static final String COMPANY_ABILITY_VIEW="tpl.companyAbilityView";
    public static final String MYINQUIRYORDER_LIST = "tpl.abilitySellerOrderList";
    public static final String MYDEVICEORDER_LIST = "tpl.deviceSellerOrderList";
    public static final String GET_FRONTDELIVER = "tpl.getFrontDeliver";
    public static final String GET_FRONTUNIONCITY = "tpl.getUnionCityOnline";
    public static final String GET_BIGDATAREGISTRATION = "tpl.getBigdataOnlineRegistration";
    public static final String GET_BIGDATADEMAND = "tpl.getBigdataDemandRegistration";
    public static final String GET_BIGDATANEWS = "tpl.getBigdataNewsRegistration";
    public static final String GET_BIGDATAPOLICY = "tpl.getBigdataPolicyRegistration";
    public static final String BIGDATA_PAGE = "tpl.bigdataPage";
    public static final String GET_NEWSPAGE = "tpl.newsPage";
    public static final String BIGDATA_REMINDER = "tpl.bigdataReminder";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    /**
     * @Author 阁楼麻雀
     * @Date 2016/12/19 13:06
     * @Desc 能力发布请求
     */
    @RequiresPermissions("member:synergy_ability_add")
    @RequestMapping("/synergy_ability_add.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      String frontStatus, String cyrh,ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        model.addAttribute("cyrh", cyrh);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("frontStatus", frontStatus);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("user", user);
        //首页的发布能力，则栏目为首页；展示页的发布，则栏目为协同制造
        if("1".equals(frontStatus)){
            model.addAttribute("channel",channelMng.findById(103));
        }else{
            model.addAttribute("channel",channelMng.findById(1));
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, ABILITY_ADD);
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016/12/19 13:06
     * @Desc 能力发布保存
     */
    @RequestMapping(value = "/member/synergy_ability_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response,ModelMap model,

                       String abilityType,
                       String abilityName,
                       String abilityCode,
                       String unit,
                       Double referPrice,
                       String detailDesc,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       String contact,
                       String mobile,
                       String statusType,
                       String frontStatus,
                       String postCode,
                       String cyrh,

                       String nextUrl,Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge){

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        Content c = new Content();
        SAbility ability = new SAbility();
        ability.setAbilityType(abilityType);
        ability.setAbilityName(abilityName);
        ability.setAbilityCode(abilityCode);
        ability.setUnit(unit);
        ability.setReferPrice(referPrice);
        ability.setAddrProvince(addrProvince);
        ability.setAddrCity(addrCity);
        ability.setAddrCounty(addrCounty);
        ability.setAddrStreet(addrStreet);
        ability.setContact(contact);
        ability.setMobile(mobile);
        ability.setStatusType(statusType);
        //set默认值
        ability.setCompany(user.getCompany());
        /*ability.setCreaterId(user.getUserId().toString());
        ability.setOperatorId(ability.getCreaterId());*/
        ability.setCreateUser(user);
        ability.setOperatorId(user.getUserId().toString());
        ability.setCreateDt(new java.sql.Date(System.currentTimeMillis()));
        ability.setUpdateDt(ability.getCreateDt());
        ability.setDeFlag("1");
        ability.setPostCode(postCode);
        c.setSite(site);
        CmsModel defaultModel=cmsModelMng.getDefModel();
        if(modelId!=null){
            CmsModel m=cmsModelMng.findById(modelId);
            if(m!=null){
                c.setModel(m);
            }else{
                c.setModel(defaultModel);
            }
        }else{
            c.setModel(defaultModel);
        }
        ContentExt ext = new ContentExt();
        ext.setTitle(abilityName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("能力发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(detailDesc);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        c = synergyCreateService.save(ability,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true);
        if("1".equals(frontStatus)){
            nextUrl = "/xtzzNlczs/index.jhtml";
        }
        else if("2".equals(frontStatus)){
            nextUrl = "/html/index.html";
        }else {
            nextUrl = "/member/synergy_ability_list.jspx";
        }
        if("1".equals(cyrh)){
            nextUrl = "/cyrhnlc/index-880000-undefined-undefined-undefined-undefined-880000-.jhtml";
        }else {
            nextUrl = "/member/synergy_ability_list.jspx";
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /***
     * @author ly
     * @date 2016-12-21
     * @param startDate 查询开始时间
     * @param endDate 查询结束时间
     * @param releaseId 发布人
     * @param abilityType 能力分类
     * @param abilityName 能力名称
     * @param abilityCode 能力代码
     * @param modelId 模型id
     * @param queryChannelId 频道id
     * @param pageNo 当前页数
     * @param request request对象
     * @param model model对象
     * @desc 能力发布列表
     * @return
     */
    @RequestMapping(value = "/member/synergy_ability_list.jspx")
    public String list(Date startDate, Date endDate, String releaseId, String abilityType,
                       String abilityName,String cyrh, String abilityCode, String status, Integer modelId,
                       Integer queryChannelId, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        //没有登录
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //判断企业资质
//        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
//        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
//            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
//            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
//        }
        //获取分页对象，
        //gjn:前台获取的status参数应该为statusType，在此依然用status传递，不过dao层已修改为用statusType过滤
        Pagination p = synergyCreateService.getPageForMember(queryChannelId, site.getSiteId(), modelId,
                user.getUserId(), cpn(pageNo), 20,startDate,endDate,releaseId,abilityType,abilityName,abilityCode,status);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("releaseId", releaseId);
        model.addAttribute("abilityType", abilityType);
        model.addAttribute("abilityName", abilityName);
        model.addAttribute("abilityCode", abilityCode);
        model.addAttribute("status", status);
        model.addAttribute("cyrh", cyrh);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, ABILITY_LIST);
    }

    /***
     * @author zhouyq
     * @date 2017-05-02
     * @return
     * @desc 能力方订单列表
     */
    @RequestMapping(value = "/member/abilitySellerOrder_list.jspx")
    public String list(HttpServletRequest request, ModelMap model, Integer pageNo,
                      Date startDate, Date endDate, String state, String orderId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = synergyCreateService.getAbilitySellerOrder(site.getSiteId(), user, cpn(pageNo),
                20, startDate, state, orderId);
        model.addAttribute("pagination", p);
        model.addAttribute("startDate", startDate);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("state", state);
        model.addAttribute("orderId", orderId);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, MYINQUIRYORDER_LIST);
    }

    /***
     * @author zhouyq
     * @date 2017-06-02
     * @return
     * @desc 企业设备订单列表
     */
    @RequestMapping(value = "/member/deviceSellerOrder_list.jspx")
    public String deviceOrderlist(HttpServletRequest request, ModelMap model, Integer pageNo,
                       Date startDate, Date endDate, String state, String orderId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = synergyCreateService.getDeviceSellerOrder(site.getSiteId(), user, cpn(pageNo),
                20, startDate, state, orderId);
        model.addAttribute("pagination", p);
        model.addAttribute("startDate", startDate);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("state", state);
        model.addAttribute("orderId", orderId);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, MYDEVICEORDER_LIST);
    }

    /**
     * @param orderId, request, response, model
     * @return
     * @author zhouyq
     * @Date 2017/05/02
     * @Desc 根据id修改订单状态
     */
    @RequestMapping("/member/abilityOrderMdyState.jspx")
    public String modifyDemandStateById(String orderId, String state, String nextUrl,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model, String backReason) throws UnsupportedEncodingException, ParseException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if (backReason != null) {
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
        if (!("1".equals(state))) {
            // 获取创建订单必要参数
            String creParam = getCreLogisticsParam(orderId);
            // 发货
            DeliverGoods deliverGoods = new DeliverGoods();
            String deliverResult = null;
            try {
                // 订单创建结果
                deliverResult = deliverGoods.createLogisticsOrder(creParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (deliverResult.equals("true")) { // 创建订单成功
                // 生成物流订单
                creLogisticsEntity(creParam);
                synergyCreateService.mdyOrderStateById(orderId, state, backReason);
            } else {
                return FrontUtils.showBaseMessage(request, model, "创建物流订单失败", "/member/abilitySellerOrder_list.jspx");
            }
        } else {
            synergyCreateService.mdyOrderStateById(orderId, state, backReason);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author zhouyq
     * @Date 2017/6/30
     * @return 创建物流订单
     */
    private void creLogisticsEntity(String creLogisticsParam) throws ParseException {
        // 参数数组
        String[] creLogisticsParaArray = creLogisticsParam.split(",");
        SLogistics sLogistics = new SLogistics();
        sLogistics.setTxlogisticId(creLogisticsParaArray[0]);
        sLogistics.setEccompanyId("HTYW");
        sLogistics.setLogisticproviderId("ZT");
        sLogistics.setOrderType(1);
        sLogistics.setServiceType("11");
        sLogistics.setCreateOrderTime(creLogisticsParaArray[15]);
        sLogistics.setPayType(2);
        sLogistics.setLogisticsOrderState("00");
        sLogistics.setIsBackground("1");
        logisticsService.creLogisticsEntity(sLogistics);
    }

    /**
     * @author zhouyq
     * @Date 2017/6/29
     * @return 获取创建物流订单参数
     */
    private String getCreLogisticsParam(String orderId) {
        String creLogisticsParam = null;
        SPOrder order = ALDPayService.findOrderById(orderId); // 订单
        // 卖家信息
        String txlogisticId = order.getOrderId();
        String supplyContact = order.getSupplyContact();
        String senderPostCode = order.getSenderPostCode();
        String supplyMobile = order.getSupplyMobile();
        String senderProv = order.getSenderProv();
        String senderCity = order.getSenderCity();
        String senderArea = order.getSenderArea();
        String senderAddress = order.getSenderAddress();
        // 买家信息
        String buyContact = order.getBuyContact();
        String receiverPostCode = order.getReceiverPostCode();
        String buyMobile = order.getBuyMobile();
        String receiverProv = order.getReceiverProv();
        String receiverCity = order.getReceiverCity();
        String receiverArea = order.getReceiverArea();
        String receiverAddress = order.getReceiverAddress();
        // 订单信息
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
        String createorderTime = df.format(new Date()); // new Date()为获取当前系统时间，也可使用当前时间戳
        creLogisticsParam = txlogisticId + "," + supplyContact + "," + senderPostCode + "," + supplyMobile
                + "," + senderProv + "," + senderCity + "," + senderArea + "," + senderAddress
                + "," + buyContact + "," + receiverPostCode + "," + buyMobile + "," + receiverProv
                + "," + receiverCity + "," + receiverArea + "," + receiverAddress + "," + createorderTime;
        return creLogisticsParam;
    }

    /***
     * @author zhouyq
     * @date 2017-07-11
     * @return
     * @desc 前台在线物流页面
     */
    @RequestMapping(value = "/getFrontDeliver.jspx")
    public String getFrontDeliver(HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
//        if (!mcfg.isMemberOn()) {
//            return FrontUtils.showMessage(request, model, "member.memberClose");
//        }
//        if (user == null) {
//            return FrontUtils.showLogin(request, model, site);
//        }
        // 获得本站栏目列表
//        Set<Channel> rights = user.getGroup().getContriChannels();
//        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
//        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
//        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CHANNEL, GET_FRONTDELIVER);
    }

    /***
     * @author zhouyq
     * @date 2017-08-05
     * @return
     * @desc 前台盟市行报名页面
     */
    @RequestMapping(value = "/unionCity_Online.jspx")
    public String getUnionCityOnline(HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CHANNEL, GET_FRONTUNIONCITY);
    }

    /**
     * @author suhe
     * @Date 2018/4/19 14:52
     * @return
     * 产业融合二级页面
     */
    @RequestMapping(value = "/bigdata_page.jspx")
    public String bigdataPage(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, Integer  orderBy, Integer  pageSize, String[] params,
                              HttpServletRequest request, HttpServletResponse response,
                              String frontStatus, ModelMap model, Integer pageNo, String paramu
                              ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        model.addAttribute("user", user);
        FrontUtils.frontData(request, model, site);
        //获取新闻分页对象
        Pagination pNews = unionCityService.getBigdataNews(site.getSiteId(), user, cpn(pageNo),
                20, paramu);
        Pagination policy = unionCityService.getBigdataPolicy(site.getSiteId(), user, cpn(pageNo),
                20, paramu);
        //仅查询产业融合分类下的能力和需求
        params = new String[]{"880000","undefined","undefined","undefined","undefined","880000","undefined"};
        orderBy =0;
        pageNo =1;
        pageSize=12;
        typeIds= new Integer[]{1};
        //查询能力列表
        Pagination pAbility = specialExtContentDirectiveService.getPageBySiteIdAbilityById(siteIds,typeIds,titleImg,recommend,title, attr,orderBy, pageNo, pageSize,params);
        //查询需求列表
        pageSize=10;
        Pagination pDemand = specialExtContentDirectiveService.getPageBySiteIdsForTagById(siteIds,typeIds, titleImg,recommend,title,attr,orderBy, pageNo,pageSize, params);
        // 获得本站栏目列表
//        Set<Channel> rights = user.getGroup().getContriChannels();
//        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
//        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("pNews", pNews);
        model.addAttribute("policy", policy);
        model.addAttribute("pDemand", pDemand);
        model.addAttribute("pAbility", pAbility);
//        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_PAGE);
    }



    /**
     * @author suhe
     * @Date 2018/4/20 11:52
     * @return
     * 产业融合新闻列表
     */

    @RequestMapping(value = "/member/bigdata_news_page.jspx")
    public String getBigdataNewsPage(HttpServletRequest request, ModelMap model, Integer pageNo,
                                     String paramu) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        //获取分页对象
        Pagination p = unionCityService.getBigdataNews(site.getSiteId(), user, cpn(pageNo),
                20, paramu);
        model.addAttribute("site", site);
        model.addAttribute("page", p);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, GET_NEWSPAGE);
    }

    /**
     * @author suhe
     * @Date 2018/4/16 16:09
     * @return
     * 大数据新闻发布
     */
    @RequiresPermissions("member:bigdata_news_registration.jspx")
    @RequestMapping(value = "/member/bigdata_news_registration.jspx")
    public String getBigdataNewsRegistration(HttpServletRequest request, HttpServletResponse response,
                                               String frontStatus,ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("frontStatus", frontStatus);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("user", user);
        //首页的发布需求，则栏目为首页；展示页的发布，则栏目为协同制造
        if("1".equals(frontStatus)){
            model.addAttribute("channel",channelMng.findById(103));
        }else{
            model.addAttribute("channel",channelMng.findById(1));
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, GET_BIGDATANEWS);
    }

    /**
     * @author suhe
     * @Date 2018/4/16 16:09
     * @return
     * 大数据政策发布
     */
    @RequiresPermissions("member:bigdata_policy_registration.jspx")
    @RequestMapping(value = "/member/bigdata_policy_registration.jspx")
    public String getBigdataPolicyRegistration(HttpServletRequest request, HttpServletResponse response,
                                             String frontStatus,ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("frontStatus", frontStatus);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("user", user);
        //首页的发布需求，则栏目为首页；展示页的发布，则栏目为协同制造
        if("1".equals(frontStatus)){
            model.addAttribute("channel",channelMng.findById(103));
        }else{
            model.addAttribute("channel",channelMng.findById(1));
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, GET_BIGDATAPOLICY);
    }


    /**
     * @author liuyang
     * @Date 2018/4/10 10:20
     * @return
     * 大数据服务调查
     */
    @RequiresPermissions("member:bigdata_Online_registration.jspx")
    @RequestMapping(value = "/bigdata_Online_registration.jspx")
    public String getBigdataOnlineRegistration(HttpServletRequest request, HttpServletResponse response,
                      String frontStatus,ModelMap model,String cyrh) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        model.addAttribute("cyrh", cyrh);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //获取企业信息
        String companyId = user.getCompany().getCompanyId();
        SCompany company = sCompanyManagementService.byCompanyId(companyId);
        String companyName = company.getCompanyName();
        java.sql.Date registerDt = company.getRegisterDt();
        String companyType = company.getCompanyType();
        String companyScale =company.getCompanyScale();
        String sites = company.getSites();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("company", company);
        model.addAttribute("companyName", companyName);
        model.addAttribute("registerDt", registerDt);
        model.addAttribute("companyType", companyType);
        model.addAttribute("companyScale", companyScale);
        model.addAttribute("sites", sites);
        model.addAttribute("frontStatus", frontStatus);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("user", user);
        //首页的发布需求，则栏目为首页；展示页的发布，则栏目为协同制造
        if("1".equals(frontStatus)){
            model.addAttribute("channel",channelMng.findById(103));
        }else{
            model.addAttribute("channel",channelMng.findById(1));
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, GET_BIGDATAREGISTRATION);
    }

    /**
     * @author liuyang
     * @Date 2018/4/13 11:19
     * @return
     *
     * 大数据需求填报调查
     */
    @RequiresPermissions("member:bigdata_demand_registration.jspx")
    @RequestMapping(value = "/bigdata_demand_registration.jspx")
    public String getBigdataDemandRegistration(HttpServletRequest request, HttpServletResponse response,
                                               String frontStatus,ModelMap model,String cyrh) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        model.addAttribute("cyrh", cyrh);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //获取企业信息
        String companyId = user.getCompany().getCompanyId();
        SCompany company = sCompanyManagementService.byCompanyId(companyId);
        String companyName = company.getCompanyName();
        java.sql.Date registerDt = company.getRegisterDt();
        String companyType = company.getCompanyType();
        String companyScale =company.getCompanyScale();
        String sites = company.getSites();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("company", company);
        model.addAttribute("companyName", companyName);
        model.addAttribute("registerDt", registerDt);
        model.addAttribute("companyType", companyType);
        model.addAttribute("companyScale", companyScale);
        model.addAttribute("sites", sites);
        model.addAttribute("frontStatus", frontStatus);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("user", user);
        //首页的发布需求，则栏目为首页；展示页的发布，则栏目为协同制造
        if("1".equals(frontStatus)){
            model.addAttribute("channel",channelMng.findById(103));
        }else{
            model.addAttribute("channel",channelMng.findById(1));
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, GET_BIGDATADEMAND);
    }

    /**
     * @author suhe
     * @Date 2018/4/19 11:19
     * @return
     *
     * 大数据调查发布后跳转页面
     */
    @RequestMapping(value = "/member/bigdata_reminder.jspx")
    public String bigdataReminder(HttpServletRequest request, HttpServletResponse response,
                                               String frontStatus,String remind,ModelMap model

    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList=channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("site", site);
        model.addAttribute("remind", remind);
        model.addAttribute("frontStatus", frontStatus);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("user", user);
        //首页的发布需求，则栏目为首页；展示页的发布，则栏目为协同制造
        if("1".equals(frontStatus)){
            model.addAttribute("channel",channelMng.findById(103));
        }else{
            model.addAttribute("channel",channelMng.findById(1));
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_REMINDER);
    }

    /**
     * @author zhouyq
     * @Date 2017/5/3
     * @return 接收信息(卖家)
     */
    public static final String ABILITYORDERSETTLE = "tpl.abilityOrderSettle";

    @RequestMapping(value = "/member/abilityOrderSettle.jspx")
    public String settle(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         String SerialNumber,
                         String OrderNo,
                         String remark,
                         String Amount,
                         int AccountType,
                         String BankID,
                         String bankName,
                         String AccountName,
                         String AccountNumber,
                         String BranchName,
                         String Province,
                         String City, String orderId, String flag, String isDevice/*(设置标志位确定是结算还是退款 0 退款  1结算)*/) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if (("0").equals(flag)) { // 退款
            // 以下信息从数据库取值
            SPOrder orderPayObj = orderPayDao.getOrderById(OrderNo);
            SPSettle spSettle = orderPayObj.getSpPay().getSpSettle();
            SerialNumber = spSettle.getSerialNumber(); // 流水号
            OrderNo = spSettle.getOrderNo(); // 订单号
//            remark = spSettle.getRemark(); // 备注
            AccountType = spSettle.getAccountType(); // 账号类型
            BankID = spSettle.getBankId(); // 银行账号
            bankName = spSettle.getBankName(); // 银行账号
            AccountName = spSettle.getAcountName(); // 账户名称
            AccountNumber = spSettle.getAcountNumber(); // 账户号码
            BranchName = spSettle.getBranchName(); // 网点名称
            Province = spSettle.getProvince(); // 省市
            City = spSettle.getCity(); // 城市
        }

        String result = synergyCreateService.settle(SerialNumber, OrderNo, remark,
                123L,
                AccountType,
                BankID,
                bankName,
                AccountName,
                AccountNumber,
                BranchName,
                Province,
                City, orderId, flag, isDevice);
        if (result.equals("success")) { // 成功
            if ("1".equals(isDevice)) {
                return FrontUtils.showSuccess(request, model, "/member/deviceSellerOrder_list.jspx?state=10");
            } else {
                return FrontUtils.showSuccess(request, model, "/member/abilitySellerOrder_list.jspx?state=10");
            }
        } else { // 失败
            if ("1".equals(isDevice)) {
                return FrontUtils.showBaseMessage(request, model, result, "/member/deviceSellerOrder_list.jspx?state=4");
            } else {
                return FrontUtils.showBaseMessage(request, model, result, "/member/abilitySellerOrder_list.jspx?state=4");
            }
        }
    }

    /**
     * @author zhouyq
     * @Date 2017/5/7
     * @return 接收信息(管理员)
     */
    public static final String ABILITYADMINORDERSETTLE = "tpl.abilityAdminOrderSettle";

    @RequestMapping(value = "/member/abilityAdminOrderSettle.jspx")
    public String adminSettle(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         String SerialNumber,
                         String OrderNo,
                         String remark,
                         String Amount,
                         int AccountType,
                         String BankID,
                         String bankName,
                         String AccountName,
                         String AccountNumber,
                         String BranchName,
                         String Province,
                         String City, String orderId, String flag/*(设置标志位确定是结算还是退款 0 退款  1结算)*/) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if (("0").equals(flag)) { // 退款
            // 以下信息从数据库取值
            SPOrder orderPayObj = orderPayDao.getOrderById(OrderNo);
            SPSettle spSettle = orderPayObj.getSpPay().getSpSettle();
            SerialNumber = spSettle.getSerialNumber(); // 流水号
            OrderNo = spSettle.getOrderNo(); // 订单号
//            remark = spSettle.getRemark(); // 备注
            AccountType = spSettle.getAccountType(); // 账号类型
            BankID = spSettle.getBankId(); // 银行账号
            bankName = spSettle.getBankName(); // 银行账号
            AccountName = spSettle.getAcountName(); // 账户名称
            AccountNumber = spSettle.getAcountNumber(); // 账户号码
            BranchName = spSettle.getBranchName(); // 网点名称
            Province = spSettle.getProvince(); // 省市
            City = spSettle.getCity(); // 城市
        }

        String result = synergyCreateService.settle(SerialNumber, OrderNo, remark,
                123L,
                AccountType,
                BankID,
                bankName,
                AccountName,
                AccountNumber,
                BranchName,
                Province,
                City, orderId, flag, null);
        if (result.equals("success")) { // 成功
                return FrontUtils.showSuccess(request, model, "/member/synergy_seller_list.jspx");
        } else { // 失败
                return FrontUtils.showBaseMessage(request, model, result, "/member/synergy_seller_list.jspx");
        }
    }

    /**
     * @Author 李利民
     * @Date 2016/12/20 20:16
     * @Desc 能力发布编辑
     * @param id 主键ID
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @return
     */
    @RequestMapping(value = "/member/synergy_ability_edit.jspx")
    public String edit(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = ABILITY_EDIT;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SAbility ability = synergyCreateService.byContentId(id);
        Content content = ability.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        id = content.getContentId();
        model.addAttribute("id",id);//有些多余
        model.addAttribute("ability",ability);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Date 2016/12/21
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 能力发布查看
     */
    @RequestMapping(value = "/member/synergy_ability_view.jspx")
    public String view(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = ABILITY_VIEW;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SAbility ability = synergyCreateService.byContentId(id);
        Content content = ability.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("ability",ability);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    /**
     * @Author wanjinyou
     * @Date 2016/12/21
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 能力发布预览
     */
    @RequestMapping(value = "/member/synergy_ability_preview.jspx")
    public String preview(Integer id, HttpServletRequest request,
                          HttpServletResponse response, ModelMap model) {
        String nextUrl = ABILITY_PREVIEW;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SAbility ability = synergyCreateService.byContentId(id);
        Content content = ability.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("ability",ability);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Date 2016/1/16
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 管理员-能力管理-明细
     */
    @RequestMapping(value = "/member/synergy_ability_manage_view.jspx")
    public String manageView(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = ABILITY_MANAGE_VIEW;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SAbility ability = synergyCreateService.byContentId(id);
        Content content = ability.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("ability",ability);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }



    /**
     * @Author wanjinyou
     * @Date 2016/12/22
     * @param id abilityId
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 能力发布三级页面点击跳转四级页面预览
     */
    @Resource
    private PubCodeService pubCodeService;
    @RequestMapping(value = "/xtzzNlczs/xtzz_ability_preview.jspx")
    public String nlcPreview(Integer id, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        String nextUrl = XTZZ_PREVIEW;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        SAbility ability = synergyCreateService.byAbilityId(id);
        //  Content content = ability.getContent();
        //获取分类信息
        String spare_type = ability.getAbilityType();
        PubCode pc = pubCodeService.findUniqueCode("NLFL",spare_type);
        if (pc.getParentCodeId() == null) {
            pc = pubCodeService.findById(pc.getId());
        } else {
            pc = pubCodeService.findById(pc.getParentCodeId());
        }
        String pk = pc.getKey();
        model.addAttribute("pck", pk);
        model.addAttribute("pc", pc);


        model.addAttribute("ability",ability);
        //    model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("user", user);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(103));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_NLCZS, nextUrl);
    }

    private WebErrors validateEdit(Integer id, CmsSite site, CmsUser user,
                                   HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldOpt(errors, site, user, new Integer[] { id })) {
            return errors;
        }
        return errors;
    }
    private boolean vldOpt(WebErrors errors, CmsSite site, CmsUser user,
                           Integer[] ids) {
        for (Integer id : ids) {
            if (errors.ifNull(id, "id")) {
                return true;
            }
            Content c = contentMng.findById(id);
            // 数据不存在
            if (errors.ifNotExist(c, Content.class, id)) {
                return true;
            }
            // 非本用户数据
            if (!c.getUser().getUserId().equals(user.getUserId())) {
                errors.noPermission(Content.class, id);
                return true;
            }
            // 非本站点数据
            if (!c.getSite().getSiteId().equals(site.getSiteId())) {
                errors.notInSite(Content.class, id);
                return true;
            }
            // 文章级别大于0，不允许修改
            if (c.getCheckStep() > 0) {
                errors.addErrorCode("member.contentChecked");
                return true;
            }
        }
        return false;
    }

    /***
     * @author ly
     * @param ids 删除的文章id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @desc 能力池删除
     * @return
     */
    @RequestMapping(value = "/member/synergy_ability_delete.jspx")
    public String delete(Integer[] ids, HttpServletRequest request,
                         HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl="synergy_ability_list.jspx";
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        synergyCreateService.deleteByIds(ids);//删除能力表数据
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    private WebErrors validateDelete(Integer[] ids, CmsSite site, CmsUser user,
                                     HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldOpt(errors, site, user, ids)) {
            return errors;
        }
        return errors;
    }

    /***
     * @Author 李利民
     * @param id 文章ID
     * @param abilityType 能力分类
     * @param abilityName 能力名称
     * @param abilityCode 能力代码
     * @param txt 内容
     * @param unit 计量单位
     * @param channelId 栏目ID
     * @param referPrice 参考价格
     * @param picPaths 图片路径
     * @param picDescs 图片描述
     * @param addrStreet 地址
     * @param contact 联系人
     * @param mobile 联系电话
     * @param attachmentPaths 附件路径
     * @param attachmentNames 附件名称
     * @param attachmentFilenames 附件文件名称
     * @param charge
     * @param chargeAmount
     * @param nextUrl 跳转路径
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/synergy_ability_update.jspx")
    //title author  description tagStr mediaPath mediaType  attachmentPaths attachmentNames attachmentFilenames标题不存在 txt picPaths picDescs存在
    public String update(Integer id,
                         String abilityType,
                         String abilityName,
                         String abilityCode,
                         String txt,
                         String unit,
                         Integer channelId,
                         Double referPrice,
                         String[] picPaths, String[] picDescs,
                         String addrProvince,
                         String addrCity,
                         String addrCounty,
                         String addrStreet,
                         String contact,
                         String mobile,
                         String postCode,
                         String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames,
                         Short charge, Double chargeAmount, String nextUrl, HttpServletRequest request,
                         HttpServletResponse response, ModelMap model,
                         String statusType


    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SAbility ability = synergyCreateService.byAbilityId(id);
        Content c = ability.getContent();
        c.setSite(site);
        ContentExt ext = c.getContentExt();
        ext.setTitle(abilityName);
        ContentTxt t = c.getContentTxt();
        t.setTxt(txt);
        //业务数据处理abilityType
        ability.setAbilityType(abilityType);
        ability.setAbilityName(abilityName);
        ability.setAbilityCode(abilityCode);
        ability.setUnit(unit);
        ability.setReferPrice(referPrice);
        ability.setAddrProvince(addrProvince);
        ability.setAddrCity(addrCity);
        ability.setAddrCounty(addrCounty);
        ability.setAddrStreet(addrStreet);
        ability.setContact(contact);
        ability.setMobile(mobile);
        ability.setStatusType(statusType);
        ability.setOperatorId(user.getUserId().toString());
        ability.setUpdateDt(new java.sql.Date(System.currentTimeMillis()));
        if(statusType == "2"){
            ability.setReleaseUser(user);
        }
        ability.setPostCode(postCode);
        String[] tagArr = StrUtils.splitAndTrim("", ",", null);
        //业务数据更新处理
         synergyCreateService.updateAdility(ability,c,ext, t,tagArr,
                attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs, channelId,
                charge,chargeAmount,user, true);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /***
     * @description 能力管理列表-草稿-发布
     * @author wanjinyou
     * @param id 主键id
     * @param request request对象
     * @param response response 对象
     * @param model model注释
     * @param statusType 状态位
     * @param nextUrl 跳转地址
     * @param modelId modelId
     * @param channelId 栏目id
     * @param charge
     * @return
     */
    @RequestMapping(value = "/member/synergy_ability_updateState.jspx")
    public String updateState(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, String statusType,
                         String nextUrl, Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //更新能力状态
        synergyCreateService.updateState(id, statusType, channelId, user, charge, true);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    private WebErrors validateUpdate(Integer id, Integer channelId,
                                     CmsSite site, CmsUser user, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldOpt(errors, site, user, new Integer[] { id })) {
            return errors;
        }
        if (vldChannel(errors, site, user, channelId)) {
            return errors;
        }
        return errors;
    }
    private boolean vldChannel(WebErrors errors, CmsSite site, CmsUser user,
                               Integer channelId) {
        Channel channel = channelMng.findById(channelId);
        if (errors.ifNotExist(channel, Channel.class, channelId)) {
            return true;
        }
        if (!channel.getSite().getSiteId().equals(site.getSiteId())) {
            errors.notInSite(Channel.class, channelId);
            return true;
        }
        if (!channel.getContriGroups().contains(user.getGroup())) {
            errors.noPermission(Channel.class, channelId);
            return true;
        }
        return false;
    }

    /**
     * @Author wanjinyou
     * @Date 2017/2/7
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 企业制造能力管理-明细--再点明细
     */
    @RequestMapping(value = "/member/company_ability_view.jspx")
    public String companyView(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = COMPANY_ABILITY_VIEW;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SAbility ability = synergyCreateService.byContentId(id);
        Content content = ability.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("ability",ability);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private SynergyCreateService synergyCreateService;
    @Resource
    private SDemandService sDemandService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private OrderPayDao orderPayDao;
    @Resource
    ALDPayService ALDPayService;
    @Resource
    LogisticsService logisticsService;
    @Resource
    private SynergyCompanyService synergyCompanyService;
    @Resource
    private SCompanyManagementService sCompanyManagementService;

    @Resource
    private SpecialExtContentDirectiveService specialExtContentDirectiveService;


    @Resource
    com.anchorcms.icloud.service.unionCity.unionCityService unionCityService;
}
