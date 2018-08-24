package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.supplychain.SSpareDemandService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;


@Controller
public class QuotePageController {

    public static final String TPLDIR_SUPPLYCHAIN = "supplychain";
    public static final String TPLDIR_SUPPLYCHAIN_quotePage = "tpl.supplychainquotePage";
    public static final String COMPANY_APTITUDE_ADD = "/member/index.jspx";

    /**
     * @author gengwenlong
     * @Date 2017/1/4 19:02
     * @return跳转到报价页面
     */
     @RequiresPermissions("synergy:v_add")
      @RequestMapping("/member/showUser.jspx")
      public String toIndex( HttpServletRequest request,ModelMap model,String repairId) throws UnsupportedEncodingException {
          CmsSite site = CmsUtils.getSite(request);
          CmsUser user = CmsUtils.getUser(request);
          FrontUtils.frontData(request,model,site);
          MemberConfig mcfg = site.getConfig().getMemberConfig();
         repairId = java.net.URLDecoder.decode(repairId, "UTF-8");         //维修资源搜索
         List<SRepairDemand> list = sSpareDemandService.getQuoteSearch(repairId);
         // 没有开启会员功能
         if (!mcfg.isMemberOn()) {
             return FrontUtils.showMessage(request, model, "member.memberClose");
         }
         if (user == null) {
             return FrontUtils.showLogin(request, model, site);
         }
         //用户没有关联公司的话不能报价？
         if(user.getCompany() == null){
             return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
         }
         //判断企业资质
         SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
         if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
             String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
             return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
         }
         //判断一下demandId
         if (repairId == null) {
             return FrontUtils.showMessage(request, model, "error.needParams");
         }
         //用户 不能报自己发的需求的价
        if(user.getCompany().getCompanyId().equals(list.get(0).getCompany().getCompanyId())){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
         //公司不能对同一需求进行二次报价
         if(sSpareDemandService.hasQuoted(repairId,user.getCompany().getCompanyId())){
             return FrontUtils.showMessage(request, model, "error.hasquoted");
         }
         model.addAttribute("testtt", list);
         model.addAttribute("channel",channelMng.findById(99));
         model.addAttribute("user", user);
         return FrontUtils.getTplPath(request, site.getSolutionPath(),
                  TPLDIR_SUPPLYCHAIN, TPLDIR_SUPPLYCHAIN_quotePage);
      }

    /**
     * @author gengwenlong
     * @Date 2017/1/9 19:23
     * @return
     * 报价
     */
    @RequiresPermissions("synergy:v_add")
    @RequestMapping(value = "/member/showBaojia.jspx",method = RequestMethod.POST)
    public String quotes(HttpServletRequest request, ModelMap model, String contentId,
                       String demandId,
                       String offerExplan,
                       String contact,
                       String mobile,
                       String email,
                       String fax,
                       Date deadlineDt,
                       String demandQuoteObjsJsonStr){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //用户没有关联公司的话不能报价？
        if(user.getCompany() == null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        //判断一下demandId
        if (demandId == null) {
            return FrontUtils.showMessage(request, model, "error.needParams");
        }
        //用户 不能报自己发的需求的价
       /* if(user.getCompany().getCompanyId().equals(targetDemand.getCompanyId())){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }*/
        SRepairQuote sRepairQuote = new SRepairQuote();
        //接收页面传来的值
        String s = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
        sRepairQuote.setDemandObjId(s);
        sRepairQuote.setDemandId(demandId);
        sRepairQuote.setContentId(contentId);
        sRepairQuote.setOfferExplan(offerExplan);
        sRepairQuote.setContact(contact);
        sRepairQuote.setMobile(mobile);
        sRepairQuote.setEmail(email);
        sRepairQuote.setFax(fax);
        sRepairQuote.setDeadlineDt(deadlineDt);
        sRepairQuote.setCompany(user.getCompany());
        sRepairQuote.setOperatorId(user.getUserId().toString());
        sRepairQuote.setUpdateDt(new Date(System.currentTimeMillis()));
        sRepairQuote.setCreaterId(user.getUserId().toString());
        sRepairQuote.setReleaseDt(sRepairQuote.getUpdateDt());
        sRepairQuote.setCreateDt(sRepairQuote.getUpdateDt());
        sRepairQuote.setSelectStatus("0");
        sRepairQuote.setDeFlag("1");
        //前往service层保存业务
        sSpareDemandService.save(sRepairQuote,demandQuoteObjsJsonStr);
        //保存完了去众修需求展示页
        String nextUrl = "/gylxtZxxq/index.jhtml";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author gengwenlong
     * @Date 2017/1/4 19:04
     * @return金牌老师傅能力详细页面
     */
    public static final String TPLDIR_SUPPLYCHAIN_jplsf_nlxq = "tpl.jplsf_nlxq";
    @RequiresPermissions("member:supplychainDetail")
    @RequestMapping("/ability.jspx")
    public String getSearch(HttpServletRequest request, ModelMap model,String repairId) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        List<SRepairRes> list = sSpareDemandService.getSearch(repairId);
        model.addAttribute("testtt", list);
        model.addAttribute("channel",channelMng.findById(99));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, TPLDIR_SUPPLYCHAIN_jplsf_nlxq);
    }
    @Resource
    private SSpareDemandService sSpareDemandService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;
  }
