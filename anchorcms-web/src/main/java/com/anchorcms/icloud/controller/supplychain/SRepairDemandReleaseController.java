package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObjPageBean;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.supplychain.RepairDemandObjService;
import com.anchorcms.icloud.service.supplychain.RepairDemandService;
import com.anchorcms.icloud.service.supplychain.SRepairInquiryService;
import com.anchorcms.icloud.service.supplychain.SRepairResService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

import static com.anchorcms.common.constants.Constants.TPLDIR_SUPPLYCHAIN;

/**
 * Created by hansx on 2016/12/29.
 * <p>
 * 维修需求发布
 */
@Controller
public class SRepairDemandReleaseController {
    private static final Logger log = LoggerFactory.getLogger(SRepairDemandReleaseController.class);

    public static final String TPL_REPAIR_DEMAND_RELEASE = "tpl.repair_demand_add";
    public static final String COMPANY_APTITUDE_ADD = "/member/index.jspx";

    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 1:36
     * @return
     * 转至维修需求发布页面
     */
    @RequestMapping(value = "/member/demandrelease.jspx")
    public String demandrelease(HttpServletRequest request,
                                HttpServletResponse response, ModelMap model,String flag) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        if (true) {
            FrontUtils.frontData(request, model, site);
            MemberConfig mcfg = site.getConfig().getMemberConfig();
            // 没有开启会员功能
            if (mcfg==null||!mcfg.isMemberOn()) {
                return FrontUtils.showMessage(request, model, "member.memberClose");
            }
            if (user == null) {
                return FrontUtils.showLogin(request, model, site);
            }
            if (user.getCompany() == null) {
                return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
            }
//            //判断企业资质
//            SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
//            if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
//                String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
//                return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
//            }
            model.addAttribute("user", user);
            if(flag==null)flag="";
            model.addAttribute("flag", flag);//前台后台页面区分
            model.addAttribute("channel",channelMng.findById(99));
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SUPPLYCHAIN, TPL_REPAIR_DEMAND_RELEASE);
        } else {
            WebErrors errors = WebErrors.create(request);
            errors.addErrorCode("error.uploadMoreNumber", user.getGroup().getId());
            return FrontUtils.showError(request, response, model, errors);
        }
    }

    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 1:37
     * @return
     * 维修需求信息保存
     */
    @RequestMapping(value = "/member/demand_save.jspx",method = RequestMethod.POST)
    public String demandsave(SRepairDemand sRepairDemand, String demandObjListJsonString,HttpServletRequest request, ModelMap model,
                             String nextUrl, Integer modelId, Integer channelId,String[] attachmentPaths, String[] attachmentNames,
                             String[] attachmentFilenames, String[] picPaths, String[] picDescs) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);


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
        ext.setTitle(sRepairDemand.getRepairName());
        ext.setAuthor(user.getUsername());
        ext.setDescription("供应链维修需求发布");
        ContentTxt t = new ContentTxt();
        t.setTxt(sRepairDemand.getDescription());

        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        //设置主键
        String uid = String.valueOf(Math.abs(UUID.randomUUID().hashCode() + 1));
        sRepairDemand.setRepairId(uid);
        sRepairDemand.setCompany(user.getCompany());
        //保存到维修需求表
        sRepairDemand.setDescription(null);
        c = repairDemandService.save(sRepairDemand,c,ext,t,channelId, typeId,user,true, attachmentPaths,
                attachmentNames, attachmentFilenames, picPaths, picDescs,demandObjListJsonString);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    private RepairDemandService repairDemandService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
