package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTxtMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.model.supplychain.*;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.common.PubCodeService;
import com.anchorcms.icloud.service.supplychain.*;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.apache.commons.lang.StringUtils;
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
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.anchorcms.common.constants.Constants.TPLDIR_COMMON;
import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;
import static com.anchorcms.common.utils.FrontUtils.frontData;
import static com.anchorcms.common.utils.FrontUtils.getTplPath;

/**
 * Created by machao on 2016/12/21.
 */
@Controller
public class SupplychainCreateController {

    private static final Logger log = LoggerFactory.getLogger(SSpareController.class);

    // 备品备件列表
    public static final String BPBJQG_LIST = "tpl.synergy_bpbjqg_list";



    public static final String TPLDIR_SUPPLYCHAIN = "supplychain";
    public static final String SUPPLYDETAIL = "tpl.supplychainDetail";
    public static final String TPL_BPBJQG = "tpl.news_icloud_gylxt_bpbjqgdetail";
    public static final String TPL_BPBJQGBACK = "tpl.news_icloud_gylxt_bpbjqgdetailback";
    public static final String TPL_REPAIRDEMAND = "tpl.repairDemandDetail";
    public static final String TPL_REPAIRDEMANDBACK = "tpl.repairDemandDetailback";


    public static final String TPL_WXZYGL = "tpl.supplychainWxzygl";
    private static final String TPL_WXXQGL = "tpl.supplychainWxxqgl";

    //    需求方备品备件求购管理检索
    public static final String TPLDIR_SYNERGYS = "supplychain";
    static final String SUPPLYDETAILS = "tpl.supplychainBpbjqgmanageSearch";
    //求购明细
    public static final String SUPPLYDETAILD = "tpl.supplychainBpbjqgmanageDetail";
    //    求购预览
    public static final String SUPPLYDETAILP = "tpl.supplychainBpbjqgmanagePreview";
    //求购编辑
    public static final String SUPPLYDETAILUP = "tpl.supplychainBpbjqgmanageUpdate";

    //金牌老师傅推荐管理
    public static final String JPLSP_RECOMMEND = "tpl.supplychainJplsftj";
    public static final String JPLSFTJ_ADD = "tpl.supplychainJplsftjAdd";

    public static final String TPLDIR_SYNERGY_RELESE = "supplychain";
    public static final String SUPPLYDETAIL_RELESE = "tpl.supplychainDemand_relese";
    public static final String SUPPLYDETAIL_CLOSE = "tpl.supplychainDemand_close";

    public static final String SUPPLYDETAIL_RELESE_WINDOWS = "tpl.supplychainDemand_relese_windows";

    private static final String MESSAGE_PAGEF = "tpl.messagePagef";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";

    @RequiresPermissions("member:supplychainDetail")
    @RequestMapping("/member/supplychainDetail.jspx")
    public String add(HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        frontData(request, model, site);
        List<SSpare> list = supplychainCreateService.getList();
        model.addAttribute("test", list);
        return getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, SUPPLYDETAIL);
    }



    /**
     * @author dongxuecheng
     * @Date 2017/1/6 8:50
     * @return
     * @description跳转到备品备件求购发布页面
     */

    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/demand_relese.jspx")
    public String relese(HttpServletRequest request, HttpServletResponse response,
                         ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
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
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("user", user);
            return getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGY_RELESE, SUPPLYDETAIL_RELESE);

    }


    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/close.jspx")
    public String close(HttpServletRequest request, HttpServletResponse response,
                         ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
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
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_RELESE, SUPPLYDETAIL_CLOSE);

    }



    /**
     * @author dongxuecheng
     * @Date 2017/1/6 8:50
     * @return
     * @description跳转到备品备件求购发布页面
     */
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/demand_relese_windows.jspx")
    public String relese_windows(HttpServletRequest request, HttpServletResponse response,
                         ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
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
        model.addAttribute("channelList", channelList);
        model.addAttribute("channel",channelMng.findById(99));
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(99));
        return getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_RELESE, SUPPLYDETAIL_RELESE_WINDOWS);

    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/6 8:51
     * @return
     * @description备品备件求购发布页面
     */
    @RequestMapping(value = "/member/demand_relese_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                    //询价对象list相关的参数，Json串
                    String demandObjListJsonString,

                    String DemandId,//唯一标识
                    String contentId,//跟cms关联的字段
                    String requestTheme,//求购主题
                    String isUrgency,//订单紧急程度
                    //Double expectPrice,//期望总价
                    String isShow,//是否显示期望报价
                    String dealType,//交易方式
                    String payType,//付款方式
                    String invoiceType,//发票类型
                    String carryType,//配送方式
                    String addrProvince,//地址（省）
                    String addrCity,//地址（地级市）
                    String addrCounty,//地址（市、县级）
                    String addrStreet,//街道信息
                    String mobile,//MOBILE
                    String telephone,//TELEPHONE
                    String fax,//传真
                    String email,//邮箱
                    String reinspection,//是否复验筛选
                    String dpa,//是否DPA
                    String factoryInspection,//是否下厂验收
                    String freightBorne,//运费承担商
                    String releaseId,//发布人id
                    String companyId,//所属企业id
                    String createrId,//创建人id
                    Date createDt,//创建时间
                    Date updateDt,//更新时间
                    Date releaseDt,//发布时间
                    Date deadlineDt,//求购截止日期
                    Date deliverDt,//要求交货日期
                    String state,//状态
                    String optimalState,//优选状态
                    String deFlag,//逻辑判断
                    String content,//内容
                    String contact,//联系人
                    String demandTypeInput,//求购类型汉字
                    String requestType,//求购类型code

                  // String spareObjid,//唯一标识
                  //  String demandId,//求购信息ID
                  //  String spareType,//备件分类
                  //   String spareName,//备件名称
                  //   String spareCode,//备件编码
                  // Double requestNum,//求购数量
                  //  String spareDesc,//备件描述
                  //  String unit,//计量单位
                  //  Double expectPrice,//期望单价
                  //  String createrId,//创建人
                  //  Date createDt,//创建时间
                  //  Date updateDt,//更新时间
                  //  Date deliverDt,//要求交货日期
                  //    String deFlag//逻辑删除
                  String flag,//判断是发布还是修改（flag为1的时候走苏和的修改方法）
                   //cms表相关参数
                   String editor,
                   String nextUrl,
                   Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                   String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SSpareDemand sSpareDemand=new SSpareDemand();
        String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
        sSpareDemand.setDemandId(uid); //唯一标识
//        sSpareDemand.setContentId(contentId); //跟cms关联的字段
        sSpareDemand.setRequestTheme(requestTheme); //求购主题
        sSpareDemand.setIsUrgency(isUrgency); //订单紧急程度
        //sSpareDemand.setExpectPrice(expectPrice); //期望总价
        sSpareDemand.setIsShow(isShow); //是否显示期望报价
        sSpareDemand.setDealType(dealType); //交易方式
        sSpareDemand.setPayType(payType); //付款方式
        sSpareDemand.setInvoiceType(invoiceType);//发票类型
        sSpareDemand.setCarryType(carryType);//配送方式
        sSpareDemand.setAddrProvince(addrProvince);//地址（省）
        sSpareDemand.setAddrCity(addrCity);//地址（地级市）
        sSpareDemand.setAddrCounty(addrCounty);//地址（市、县级）
        sSpareDemand.setAddrStreet(addrStreet);//街道信息
        sSpareDemand.setMobile(mobile);//MOBILE
        sSpareDemand.setTelephone(telephone);//TELEPHONE
        sSpareDemand.setCreaterId(user.getUserId().toString());
        sSpareDemand.setFax(fax);//传真
        sSpareDemand.setEmail(email);//邮箱
        sSpareDemand.setReinspection(reinspection);//是否复验筛选
        sSpareDemand.setDpa(dpa);//是否DPA
        sSpareDemand.setFactoryInspection(factoryInspection);//是否下厂验收
        sSpareDemand.setFreightBorne(freightBorne);//运费承担商
        sSpareDemand.setReleaseId(contact);//发布人id

        sSpareDemand.setScompany(user.getCompany());//所属企业id

        sSpareDemand.setCreateDt(date);//创建时间
        sSpareDemand.setUpdateDt(date); //更新时间
//        sSpareDemand.setReleaseDt(date);//发布时间
        sSpareDemand.setDeadlineDt(deadlineDt);//求购截止日期
        sSpareDemand.setDeliverDt(deliverDt);//要求交货日期
        sSpareDemand.setState(state);//状态
        sSpareDemand.setOptimalState(optimalState);//优选状态
        sSpareDemand.setDeFlag(deFlag);//逻辑判断
        sSpareDemand.setRequestType(requestType);

        Content c = new Content();
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
        ext.setTitle(requestTheme);
        ext.setAuthor(user.getUsername());
        ext.setDescription("备品备件求购发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(editor);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
             c=supplychainCreateService.save(sSpareDemand,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                     ,picPaths,picDescs,channelId, typeId,user,charge,true,demandObjListJsonString);

        //跳转至需求管理列表
       // String nextUrl = "/member/synergy_bpbjqg_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    /**
     * @author dongxuecheng
     * @Date 2017/1/6 8:51
     * @return
     * @description备品备件求购发布编辑页面
     */
    @RequestMapping(value = "/member/demand_relese_change.jspx")
    public String change(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       //询价对象list相关的参数，Json串
                         String demandObjListJsonString,

                       String DemandId,//唯一标识
                       String contentId,//跟cms关联的字段
                       String requestTheme,//求购主题
                       String isUrgency,//订单紧急程度
                       //Double expectPrice,//期望总价
                       String isShow,//是否显示期望报价
                       String dealType,//交易方式
                       String payType,//付款方式
                       String invoiceType,//发票类型
                       String carryType,//配送方式
                       String addrProvince,//地址（省）
                       String addrCity,//地址（地级市）
                       String addrCounty,//地址（市、县级）
                       String addrStreet,//街道信息
                       String mobile,//MOBILE
                       String telephone,//TELEPHONE
                       String fax,//传真
                       String email,//邮箱
                       String reinspection,//是否复验筛选
                       String dpa,//是否DPA
                       String factoryInspection,//是否下厂验收
                       String freightBorne,//运费承担商
                       String releaseId,//发布人id
                       String companyId,//所属企业id
                       String createrId,//创建人id
                       Date createDt,//创建时间
                       Date updateDt,//更新时间
                       Date releaseDt,//发布时间
                       Date deadlineDt,//求购截止日期
                       Date deliverDt,//要求交货日期
                       String state,//状态
                       String optimalState,//优选状态
                       String deFlag,//逻辑判断
                       String content,//内容
                       String contact,//联系人
                       String requestTypeInput,//分类
                         String mc,
                       // String spareObjid,//唯一标识
                       //  String demandId,//求购信息ID
                       //  String spareType,//备件分类
                       //   String spareName,//备件名称
                       //   String spareCode,//备件编码
                       // Double requestNum,//求购数量
                       //  String spareDesc,//备件描述
                       //  String unit,//计量单位
                       //  Double expectPrice,//期望单价
                       //  String createrId,//创建人
                       //  Date createDt,//创建时间
                       //  Date updateDt,//更新时间
                       //  Date deliverDt,//要求交货日期
                       //    String deFlag//逻辑删除
                       String flag,//判断是发布还是修改（flag为1的时候走苏和的修改方法）
                       //cms表相关参数
                       String editor,
                       //String nextUrl,
                       Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());

        SSpareDemand sSpareDemand= this.supplychainCreateService.SearchLis(DemandId);

//        sSpareDemand.setContentId(contentId); //跟cms关联的字段
        sSpareDemand.setRequestTheme(requestTheme); //求购主题
        sSpareDemand.setIsUrgency(isUrgency); //订单紧急程度
        //sSpareDemand.setExpectPrice(expectPrice); //期望总价
        sSpareDemand.setIsShow(isShow); //是否显示期望报价
        sSpareDemand.setDealType(dealType); //交易方式
        sSpareDemand.setPayType(payType); //付款方式
        sSpareDemand.setInvoiceType(invoiceType);//发票类型
        sSpareDemand.setCarryType(carryType);//配送方式
        sSpareDemand.setAddrProvince(addrProvince);//地址（省）
        sSpareDemand.setAddrCity(addrCity);//地址（地级市）
        sSpareDemand.setAddrCounty(addrCounty);//地址（市、县级）
        sSpareDemand.setAddrStreet(addrStreet);//街道信息
        sSpareDemand.setMobile(mobile);//MOBILE
        sSpareDemand.setTelephone(telephone);//TELEPHONE
        sSpareDemand.setFax(fax);//传真
        sSpareDemand.setEmail(email);//邮箱
        sSpareDemand.setReinspection(reinspection);//是否复验筛选
        sSpareDemand.setDpa(dpa);//是否DPA
        sSpareDemand.setFactoryInspection(factoryInspection);//是否下厂验收
        sSpareDemand.setFreightBorne(freightBorne);//运费承担商
        sSpareDemand.setReleaseId(contact);//发布人id
        sSpareDemand.setScompany(user.getCompany());//所属企业id
        sSpareDemand.setCreaterId(user.getUserId().toString());
        //sSpareDemand.setCreaterId(createrId);//创建人id
        //sSpareDemand.setCreateDt(date);//创建时间
        sSpareDemand.setUpdateDt(date); //更新时间
       // sSpareDemand.setReleaseDt(date);//发布时间
        sSpareDemand.setDeadlineDt(deadlineDt);//求购截止日期
        sSpareDemand.setDeliverDt(deliverDt);//要求交货日期
        sSpareDemand.setState(state);//状态
      //  sSpareDemand.setOptimalState(optimalState);//优选状态
      //  sSpareDemand.setDeFlag(deFlag);//逻辑判断
        sSpareDemand.setRequestType(requestTypeInput);

        Content c=sSpareDemand.getContent();
        ContentExt ext = c.getContentExt();
        ext.setTitle(requestTheme);
        ext.setAuthor(user.getUsername());
        ext.setDescription("备品备件求购发布");
        ContentTxt t = c.getContentTxt();

        //若表中无contentTxt的数据，新增一条
        if(editor == null){
            editor = "";
        }
        if(t == null){
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt(editor);
            contentTxtMng.save(contentTxt, c);
        }else{
            t.setTxt(editor);
        }

        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
            supplychainCreateService.update(sSpareDemand,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                    ,picPaths,picDescs,channelId, typeId,user,charge,true,demandObjListJsonString);


        //跳转至需求管理列表
        String nextUrl = "/member/synergy_bpbjqg_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * Created by 潘晓东 on 2016/12/22.
     * 备品备件详细查询
     */
    @Resource
    private PubCodeService pubCodeService;
    @RequestMapping(value = "/spareDemand.jspx")
    public String selectById(String flag,String id, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
        if (id != null) {
            SSpareDemand sSpareDemand = spareDemandService.selectById(id);
            Content content = sSpareDemand.getContent();
            List<SSpareDemandObj> objEntityList = spareDemandObjService.selectSSpareDemandObjEntity(id);
            model.addAttribute("sSpareDemand", sSpareDemand);
            model.addAttribute("objEntityList", objEntityList);
            model.addAttribute("content",content);
            model.addAttribute("channel",channelMng.findById(99));
            String userId = sSpareDemand.getCreaterId();
            CmsUser cmsuser = userDao.findById(Integer.parseInt(userId));
            String userName = cmsuser.getUsername();
            model.addAttribute("userName",userName);
            String spare_type = sSpareDemand.getRequestType();
            PubCode pc = pubCodeService.findUniqueCode("BPBJLX",spare_type);
            if (pc.getParentCodeId() == null) {
                pc = pubCodeService.findById(pc.getId());
            } else {
                pc = pubCodeService.findById(pc.getParentCodeId());
            }
            String pk = pc.getKey();
            model.addAttribute("pck", pk);
//            Date date = sSpareDemand.getReleaseDt();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String s = sdf.format(date);
//            model.addAttribute("date",s);
        }
        if (flag != null && flag.trim().equals("0")) {
            return getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SUPPLYCHAIN, TPL_BPBJQGBACK);
        }
        if (flag != null && flag.trim().equals("1")){
            return getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SUPPLYCHAIN, TPL_BPBJQG);
        }
        return getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, TPL_BPBJQG);
    }

    /**
     * Created by 潘晓东 on 2016/12/22.
     * 维修需求详细查询
     */
    @RequestMapping(value = "/repairDemand.jspx")
    public String selectRepairDemand(String flag, String id, HttpServletRequest request,
                                     HttpServletResponse response, ModelMap model){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
        if (id !=null){
            SRepairDemand sRepairDemand = repairDemandService.selectReapirDemand(id);
            Content content = sRepairDemand.getContent();
            List<SRepairDemandObj> sRepairDemandObjList = repairDemandObjService.selcetByRepairDemandId(id);

            String userId = sRepairDemand.getCreaterId();
            CmsUser cmsuser = userDao.findById(Integer.parseInt(userId));
            String userName = cmsuser.getUsername();
            model.addAttribute("userName",userName);
            model.addAttribute("user", user);
            model.addAttribute("sRepairDemand", sRepairDemand);
            model.addAttribute("sRepairDemandObjList", sRepairDemandObjList);
            model.addAttribute("content",content);
            model.addAttribute("channel",channelMng.findById(99));
        }
        if (flag != null && flag.equals("0")) {
            return getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SUPPLYCHAIN, TPL_REPAIRDEMANDBACK);
        }
        if (flag != null && flag.trim().equals("1")){
            return getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SUPPLYCHAIN, TPL_REPAIRDEMAND);
        }
        return getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, TPL_REPAIRDEMAND);
    }


    /**
     * 众修资源发布请求
     *
     * @author ld
     * @param request
     * @return
     */
    public static final String TPLDIR_SYPPPLYCHAIN_ADD = "tpl.supplychainResourceAdd";

    @RequiresPermissions("member:supplychain_resource_add")
    @RequestMapping("/member/supplychain_resource_add.jspx")
    public String fb(HttpServletRequest request, ModelMap model, HttpServletResponse response) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        // FrontUtils.frontData(request, model, site);
        if (true) {
            frontData(request, model, site);
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
            model.addAttribute("site", site);
            model.addAttribute("channelList", channelList);
            model.addAttribute("sessionId", request.getSession().getId());
            return getTplPath(request, site.getSolutionPath(), TPLDIR_SUPPLYCHAIN, TPLDIR_SYPPPLYCHAIN_ADD);
        } else {
            WebErrors errors = WebErrors.create(request);
            errors.addErrorCode("error.uploadMoreNumber", user.getGroup().getId());
            return FrontUtils.showError(request, response, model, errors);
        }
    }

    /**
     * 众修资源发布保存
     * ld
     *
     * @param srr
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/member/zxzyfb.jspx")
    public String save(SRepairRes srr, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model,
                       String nextUrl, Integer modelId,
                       String[] picPaths, String[] picDescs, Integer channelId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
        //设置主键
        String id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        srr.setRepairId(id);
        Content c = new Content();
        c.setSite(site);
        CmsModel defaultModel = cmsModelMng.getDefModel();
        if (modelId != null) {
            CmsModel m = cmsModelMng.findById(modelId);
            if (m != null) {
                c.setModel(m);
            } else {
                c.setModel(defaultModel);
            }
        } else {
            c.setModel(defaultModel);
        }
        ContentExt ext = new ContentExt();
        ext.setTitle(srr.getRepairName());
        ext.setAuthor(user.getUsername());
        ext.setDescription("供应链众修资源发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(srr.getDescription());

        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        c = supplychainCreateService.save(srr, c, ext, t, picPaths, picDescs, channelId, typeId, user, true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    /*public String fbzy(SRepairRes srr, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        // 解析前台的${res}
        FrontUtils.frontData(request, model, site);
        String id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        srr.setRepairId(id);
        this.supplychainCreateService.addResource(srr);
        return FrontUtils.getTplPath(request, site.getSolutionPath(), TPLDIR_SUPPLYCHAIN, TPLDIR_SYPPPLYCHAIN_ADD);
    }*/

    /**
     * 会员投稿列表
     * @param pageNo
     * 页码
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/gylxt_wxzygl_list.jspx")
    public String list(String queryTitle, Integer modelId,
                       Integer queryChannelId, Integer pageNo, HttpServletRequest request,
                       ModelMap model) {
        return list(queryTitle, modelId, queryChannelId, TPL_WXZYGL,
                pageNo, request, model);
    }
    protected String list(String q, Integer modelId, Integer queryChannelId,
                          String nextUrl, Integer pageNo,
                          HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        Pagination p = supplychainCreateService.getPageForMember(q, queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        if (!StringUtils.isBlank(q)) {
            model.addAttribute("q", q);
        }
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        return getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER , nextUrl);
    }

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:02
     * @return Manage_Detail
     * 需求方备品备件求购管理明细
     */
    @RequiresPermissions("member:supplychainBpbjqgmanage")
    @RequestMapping("/member/supplychainBpbjqgmanageDetail.jspx")
    public String BpqgDetail(HttpServletRequest request, ModelMap model, String uid)throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        frontData(request, model, site);
        SSpareDemand demand= this.supplychainCreateService.SearchLis(uid);
        model.addAttribute("demand", demand);
        return getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGYS, SUPPLYDETAILD);
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:02
     * @return Manage_Preview
     * 需求方备品备件求购管理预览
     */
    @RequiresPermissions("member:supplychainBpbjqgmanage")
    @RequestMapping("/member/supplychainBpbjqgmanagePreview.jspx")
    public String ManageDetail(HttpServletRequest request, ModelMap model, String uid)throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        frontData(request, model, site);
        List<SSpareDemand> list = supplychainCreateService.SearchList(uid);
        model.addAttribute("ulist", list);
        return getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGYS, SUPPLYDETAILP);
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:03
     * @return Manage_Update
     * 需求方备品备件求购管理编辑（取数据）
     */
    @RequiresPermissions("member:supplychainBpbjqgmanage")
    @RequestMapping("/member/supplychainBpbjqgmanageUpdate.jspx")
    public String BpqgUpdate(HttpServletRequest request, ModelMap model, String uid)throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
        SSpareDemand demand= this.supplychainCreateService.SearchLis(uid);
        model.addAttribute("demand", demand);
        model.addAttribute("user", user);
        return getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGYS, SUPPLYDETAILUP);
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:04
     * @return icloud_bpbjqg_managelist
     * 需求方备品备件求购管理发布
     */
    @RequiresPermissions("member:supplychainBpbjqgmanage")
    @RequestMapping("/member/supplychainBpbjqgmanageIss.jspx")
    public String BpqgIss(HttpServletRequest request, ModelMap model,String uid) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        frontData(request, model, site);
        int flag=supplychainCreateService.ManagelistIss(uid);
        String nextUrl = "/member/synergy_bpbjqg_list.jspx?status=1";
        return  FrontUtils.showSuccess(request,model,nextUrl);
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:30
     * @return icloud_bpbjqg_managelist
     * 需求方备品备件求购管理撤回
     */
    @RequiresPermissions("member:supplychainBpbjqgmanage")
    @RequestMapping("/member/supplychainBpbjqgmanageRe.jspx")
    public String BpqgRe(HttpServletRequest request, ModelMap model,String uid) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        frontData(request, model, site);
        int flag=supplychainCreateService.ManagelistRe(uid);
        String nextUrl = "/member/synergy_bpbjqg_list.jspx?status=2";
        return  FrontUtils.showSuccess(request,model,nextUrl);
    }

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/12 13:49
     * @return
     * 需求方备品备件求购管理删除新
     */
    @RequestMapping(value = "/member/synergy_demand_delet.jspx")
    public String delete(String requestType,Date startDate, Date endDate,String releaseId, String dealType,
                         String expectPrice, String isUrgency, String requestTheme,String spareName,String payType,String invoiceType, Integer modelId,String status,
                         Integer queryChannelId, Integer pageNo,
                         HttpServletRequest request, ModelMap model, String demandId) {
        String nextUrl ="/member/synergy_bpbjqg_list.jspx?status=1" ;
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
        supplychainCreateService.delete(demandId);//删除能力表数据
        Pagination p = supplychainCreateService.getPageForMember(requestType,queryChannelId, site.getSiteId(), modelId,
                user.getUserId(), cpn(pageNo), 20,startDate,endDate,releaseId,dealType,expectPrice,isUrgency,requestTheme,invoiceType,status);
        model.addAttribute("pagination", p);
//        model.addAttribute("statusType",a);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }



    /**
     * @author machao
     * @Date 2016/12/29 15:26
     * 金牌老师傅管理列表查询
     * @return
     */
    @RequiresPermissions("member:supplychain_jplsftj_list")
    @RequestMapping("/member/supplychain_jplsftj_list.jspx")
    public String jplsftjList(java.util.Date startDate, java.util.Date endDate, String releaseName, String repairType,Integer modelId,
                              Integer queryChannelId, Integer pageNo,
                              HttpServletRequest request, ModelMap model,String flag, String companyName, String workYear,String addrCity){
        String nextUrl = "";
        if (flag != null) {
            if (flag.equals("tuijian,")) {
                flag = "tuijian";
            }
        }
        if((flag == "") || (flag == null)){
            nextUrl = JPLSP_RECOMMEND;
            if (flag == null) {
                releaseName = "";
            }
        }else if (flag.equals("tuijian")){
            nextUrl = JPLSFTJ_ADD;
        }
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request,model,site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        //没有开启会员功能
        if(!mcfg.isMemberOn()){
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if(user==null){
            return FrontUtils.showLogin(request,model,site);
        }
        Pagination p = supplychainCreateService.getJplsftjPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(), user.getUserId(),cpn(pageNo), 20,startDate,endDate, releaseName, repairType,flag,companyName,workYear,addrCity);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("releaseName", releaseName);
        model.addAttribute("repairType", repairType);
        model.addAttribute("companyName", companyName);
        model.addAttribute("addrCity", addrCity);
        model.addAttribute("workYear", workYear);
        return getTplPath(request,site.getSolutionPath(),TPLDIR_SUPPLYCHAIN,nextUrl);
    }
    /**
     * @author machao
     * @Date 2017/1/7 14:15
     * @return
     * 金牌老师傅推荐撤销、添加
     */
    @RequestMapping(value = "/member/supplychain_jplsftj_check.jspx")
    public String addToRecommend(String id, String flag,Integer channelId, String nextUrl, HttpServletRequest request,
                         HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //业务数据更新处理,添加推荐，推荐撤销
        boolean bool = supplychainCreateService.recommendManage(id.toString(),flag);
        if(bool){
            return FrontUtils.showSuccess(request, model, nextUrl);
        }else{
            model.addAttribute("title", "此人已经推荐为金牌师傅，请返回！");
            return showMessage(request, model, nextUrl);
        }
    }
    /**
     * @author machao
     * @Date 2017/1/7 18:24
     * 返回页面，重复保存，提示库里已存在
     * @return
     */
    public static String showMessage(HttpServletRequest request,
                                     Map<String, Object> model, String nextUrl) {
        CmsSite site = CmsUtils.getSite(request);
        frontData(request, model, site);
        if (!StringUtils.isBlank(nextUrl)) {
            model.put("nextUrl", nextUrl);
        }
        return getTplPath(request, site.getSolutionPath(), TPLDIR_COMMON,
                MESSAGE_PAGEF);
    }


    @Resource
    SSpareService service;



    /**
     * @author 苏和 13739980760
     * @Date 2017/1/6 14:58
     * @return
     * 备品备件求购管理新
     */
    @RequestMapping(value = "/member/synergy_bpbjqg_list.jspx")
    public String list(Date startDate, String requestType,Date endDate,String releaseId, String dealType,
                       String expectPrice, String isUrgency, String requestTheme,String invoiceType, Integer modelId,String status,
                       Integer queryChannelId, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
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
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        //获取分页对象，
        //gjn:前台获取的status参数应该为statusType，在此依然用status传递，不过dao层已修改为用statusType过滤
        Pagination p = supplychainCreateService.getPageForMember(requestType, queryChannelId, site.getSiteId(), modelId,
                user.getUserId(), cpn(pageNo), 20,startDate,endDate,releaseId,dealType,expectPrice,isUrgency,requestTheme,invoiceType,status);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("requestTheme", requestTheme);
        model.addAttribute("requestType", requestType);
        model.addAttribute("status", status);
        return getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BPBJQG_LIST);
    }




    @Resource
    private SpareDemandService spareDemandService;

    @Resource
    private SupplychainCreateService supplychainCreateService;
    @Resource
    private SpareDemandObjService spareDemandObjService;

    @Resource
    private RepairDemandService repairDemandService;
    @Resource
    private RepairDemandObjService repairDemandObjService;


    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private ContentTxtMng contentTxtMng;

    @Resource
    private CmsUserDao userDao;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
