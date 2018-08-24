package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTxtMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.commservice.SSoldNote;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.commservice.SSoldNoteService;
import com.anchorcms.icloud.service.supplychain.MasterManagerService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Created by 董学成 on 2017/1/13.
 */
@Controller
public class SSoldNoteController {

    /**
     * @author dongxuecheng
     * @Date 2017/1/13 11:16
     * @return
     * @description销售记录发布界面
     *
     */
    public static final String TPLDIR_SYNERGY_SOLD = "commservice";
    public static final String SUPPLYDETAIL_SOLD_RELEASE = "tpl.supplychainSold_relese";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/Sold_relese.jspx")
    public String Sold_relese(HttpServletRequest request, HttpServletResponse response,
                         ModelMap model) {
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
        model.addAttribute("company", user.getCompany());
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("channel", channelMng.findById(101));
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_SOLD, SUPPLYDETAIL_SOLD_RELEASE);
    }


    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:51
     * @return
     * @description //销售记录保存发布功能
     */
    @RequestMapping(value = "/member/Sold_relese_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                   ///     int soldNoteId,//唯一标示
                        String company,//公司名称
                        String wareType,//销售产品种类
                        String amount,//订单销售额
                        String charger,//订单负责人
                        String transportInfo,//发货运输信息
                        String companyInfo,//客户公司基础信息
                        String contact,//联系人
                        String mobile,//联系电话
                        String telephone,//固定电话
                        String remark,//备注
                        String hardCopyPath,//合同发票复印件
                        Date dealDt,//订单成交日期
                        String status,//状态
                        String addrProvince,//地址（省）
                        String addrCity,//地址（地级市）
                        String addrCounty,//地址（市、县级）
                        String addrStreet,//街道信息
                       String fax,
                       String email,
                       Integer modelId,Integer channelId,String nextUrl,
                       String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs){

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());//获取当前时间

        SSoldNote soldNote=new SSoldNote();
        soldNote.setCompanyName(company); //公司名称
        soldNote.setWareType(wareType); //销售产品种类
        soldNote.setAmount(amount); //订单销售额
        soldNote.setCharger(charger); //订单负责人
        soldNote.setTransportInfo(transportInfo); //发货运输信息
       // soldNote.setCompanyInfo(companyInfo); //客户公司基础信息
        soldNote.setContact(contact); //联系人
        soldNote.setMobile(mobile); //联系电话
        soldNote.setTelephone(telephone);//固定电话
        soldNote.setRemark(remark); //备注
       // soldNote.setHardCopyPath(attachmentNames.toString()); //合同发票复印件
        soldNote.setDealDt(dealDt); //订单成交日期
        soldNote.setCreaterId(user.getUserId()); //创建人
        soldNote.setCreaterDt(date); //创建时间
        soldNote.setUpdateDt(date); //更新时间
        soldNote.setStatus(status); //逻辑判断
        soldNote.setAddrProvince(addrProvince); //省
        soldNote.setAddrCity(addrCity); //市
        soldNote.setAddrCounty(addrCounty); //区
        soldNote.setAddrStreet(addrStreet); //街道
        soldNote.setFax(fax); //传真
        soldNote.setEmail(email); //邮箱

        soldNote.setCompany(user.getCompany());

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
        ext.setTitle(wareType);
        ext.setAuthor(user.getUsername());
        ext.setDescription("销售记录发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(companyInfo);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        c=sSoldNoteService.supplychain_save(soldNote,c,ext,t,channelId,typeId,user,true,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,picDescs);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/4 11:51
     * @return
     * @description //销售记录管理界面（删除，撤回，发布功能）
     */
    public static final String SUPPLYDETAIL_SOLDNOTE_MANAGER = "tpl.supplychainSoldNote_manager";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/SoldNote_manager.jspx")
    public String SoldNote_manager(Integer UserId, java.util.Date releaseDt, java.util.Date deadlineDt, Integer demandId, String inquiryTheme,Date StartTime,Date EndTime,
                                 String status, String payType, String statusType, Integer modelId, Integer queryChannelId, Integer pageNo,String flag,
                                 HttpServletRequest request, ModelMap model,Integer id) {
        String nextUrl="/member/SoldNote_manager.jspx";
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
        if(flag!=null&&flag.equals("delete")){//删除功能
            sSoldNoteService.delete(id);
            nextUrl="/member/SoldNote_manager.jspx?statusType=1";
            return FrontUtils.showSuccess(request, model, nextUrl);
        }else if(flag!=null&&flag.equals("reback")){//撤回功能
            sSoldNoteService.update(id);
            nextUrl="/member/SoldNote_manager.jspx?statusType=2";
            return FrontUtils.showSuccess(request, model, nextUrl);
        }else if(flag!=null&&flag.equals("relece")){//发布功能
            sSoldNoteService.update(id);
            nextUrl="/member/SoldNote_manager.jspx?statusType=1";
            return FrontUtils.showSuccess(request, model, nextUrl);
        }
        Pagination p = sSoldNoteService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(), user.getIsAdmin(),cpn(pageNo), 20, StartTime,EndTime, demandId, inquiryTheme, status,payType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("releaseDt", releaseDt);
        model.addAttribute("deadlineDt", deadlineDt);
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("status", status);
        model.addAttribute("StartTime", StartTime);
        model.addAttribute("EndTime", EndTime);
        model.addAttribute("statusType", statusType);
        model.addAttribute("payType", payType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_SOLD, SUPPLYDETAIL_SOLDNOTE_MANAGER);
    }

/**
 * @author dongxuecheng
 * @Date 2017/1/14 11:10
 * @return
 * @description销售记录编辑和明细
 */

public static final String SUPPLYDETAIL_SOLD_DETAIL = "tpl.supplychainSold_detail";
    @RequiresPermissions("synergy:v_add")
    @RequestMapping("/member/Sold_detail.jspx")
    public String Sold_detail(HttpServletRequest request, HttpServletResponse response,
                              ModelMap model,Integer id,String webfalg) {
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

        SSoldNote sSoldNote=sSoldNoteService.findById(id);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("sSoldNote",sSoldNote);
        model.addAttribute("webfalg",webfalg);
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY_SOLD, SUPPLYDETAIL_SOLD_DETAIL);
    }

    /**
     * @Author 闫浩芮
     * @Date 2017/1/4 0004 13:13
     * 编辑，更新需求信息
     */
    @RequestMapping(value = "/member/SoldNote_edit.jspx")
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         //询价对象list相关的参数，Json串
                         String demandObjListJsonString,

                         //P.S.参数竖着放是为了方便加注释和debug
                         String company,//公司名称
                         String wareType,//销售产品种类
                         String amount,//订单销售额
                         String charger,//订单负责人
                         String transportInfo,//发货运输信息
                         String companyInfo,//客户公司基础信息
                         String contact,//联系人
                         String mobile,//联系电话
                         String telephone,//固定电话
                         String remark,//备注
                         String hardCopyPath,//合同发票复印件
                         Date dealDt,//订单成交日期
                         String status,//状态
                         String addrProvince,//地址（省）
                         String addrCity,//地址（地级市）
                         String addrCounty,//地址（市、县级）
                         String addrStreet,//街道信息
                         String fax,
                         String email,
                         Integer id,
                         //cms表相关参数
                         String editor,
                         Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                         String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge
    ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        SSoldNote soldNote = sSoldNoteService.findById(id);
        Content c = soldNote.getContent();
        if(soldNote==null){soldNote=new SSoldNote();}
        soldNote.setCompanyName(company); //公司名称
        soldNote.setWareType(wareType); //销售产品种类
        soldNote.setAmount(amount); //订单销售额
        soldNote.setCharger(charger); //订单负责人
        soldNote.setTransportInfo(transportInfo); //发货运输信息
      //  soldNote.setCompanyInfo(companyInfo); //客户公司基础信息
        soldNote.setContact(contact); //联系人
        soldNote.setTelephone(telephone);//固定电话
        soldNote.setMobile(mobile); //联系电话
        soldNote.setRemark(remark); //备注
        if(attachmentNames!=null){soldNote.setHardCopyPath(attachmentNames.toString()); }//合同发票复印件
        soldNote.setDealDt(dealDt); //订单成交日期
        soldNote.setCreaterId(user.getUserId()); //创建人
        soldNote.setAddrProvince(addrProvince); //省
        soldNote.setAddrCity(addrCity); //市
        soldNote.setAddrCounty(addrCounty); //区
        soldNote.setAddrStreet(addrStreet); //街道
        soldNote.setFax(fax); //传真
        soldNote.setEmail(email); //邮箱
        //默认值set
        soldNote.setUpdateDt(new Date(System.currentTimeMillis()));

        c.setSite(site);
        ContentExt ext = c.getContentExt();
        ext.setTitle("销售记录");
        ext.setAuthor(user.getUsername());
        ext.setDescription("销售记录发布");
        ContentTxt t = c.getContentTxt();

        //若表中无contentTxt的数据，新增一条
        if(companyInfo == null){
            companyInfo = "";
        }
        if(t == null){
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt(companyInfo);
            contentTxtMng.save(contentTxt, c);
        }else{
            t.setTxt(companyInfo);
        }

        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        //前往service层保存业务
        sSoldNoteService.update(soldNote,c,ext,t,attachmentPaths,attachmentNames, attachmentFilenames
                ,picPaths,picDescs,channelId, typeId,user,charge,true,demandObjListJsonString);
        //跳转至需求管理列表
        String nextUrl = "/member/SoldNote_manager.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    @Resource
    private MasterManagerService masterManagerService;
    @Resource
    private SSoldNoteService sSoldNoteService;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;

}
