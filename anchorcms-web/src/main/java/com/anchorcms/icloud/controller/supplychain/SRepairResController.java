package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.service.supplychain.SRepairResService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.anchorcms.common.constants.Constants.TPLDIR_SUPPLYCHAIN;
import static com.anchorcms.icloud.controller.supplychain.SupplychainCreateController.showMessage;
/**
 * Created by 刘鹏 on 2016/12/21.
 */

/**
 * 维修资源详情
 */
@Controller
public class SRepairResController {
    private static final Logger log = LoggerFactory.getLogger(SRepairResController.class);

    public static final String TPL_SRepairResDetail = "tpl.SRepairResDetail";

    @RequestMapping(value = "/SRepairResDetail.jspx")
    public String find(String id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        SRepairRes res = service.findById(id);
        model.addAttribute("resss", res);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, TPL_SRepairResDetail);
    }

    /**
     * Created by 刘鹏 2016/12/27.
     * 跳转金牌老师傅个人维护页面
     */
    public static final String TPL_SRepairResUpdate = "tpl.SRepairResUpdate";
    @RequestMapping(value = "/member/SRepairResUpdate.jspx")
    public String updateSRepair(String id, HttpServletRequest request,
                                      HttpServletResponse response, ModelMap model){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
//        SRepairRes res = service.findById(user.getUserId().toString());
        List<SRepairRes> res = service.findJplsfById(user.getUserId().toString());
        if(res.size()==1){
            model.addAttribute("resss", res);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SUPPLYCHAIN, TPL_SRepairResUpdate);
        }else{
            model.addAttribute("title", "你还不是金牌老师傅！");
            return showMessage(request, model, null);
        }

    }



    /**
     * Created by 刘鹏 2016/12/27.
     * 金牌老师傅个人维护
     */
    @RequestMapping(value = "/updateSRepair.jspx",method = RequestMethod.POST)
    public String updateSRepairs(SRepairRes res, String nextUrl,HttpServletRequest request,
                                HttpServletResponse response, ModelMap model,String repairPrice,String addrProvince, String
                                             addrCity,String addrCounty, String addrStreet,String
                                             goodAt,String workYear,String mobile,String
                                             description,String releaseName){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        SRepairRes sRepairRes=new SRepairRes();
        sRepairRes.setReleaseName(releaseName);
        sRepairRes.setDescription(description);
        sRepairRes.setAddrProvince(addrProvince);
        sRepairRes.setAddrCity(addrCity);
        sRepairRes.setAddrCounty(addrCounty);
        sRepairRes.setAddrStreet(addrStreet);
        sRepairRes.setGoodAt(goodAt);
        sRepairRes.setRepairPrice(repairPrice);
        sRepairRes.setWorkYear(workYear);
        sRepairRes.setMobile(mobile);

        service.updateSRepairRes(res);
        return FrontUtils.showSuccess(request, model,nextUrl);

    }
    @Resource
    private SRepairResService service;
}
