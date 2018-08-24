package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.service.supplychain.SupplyChainService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by DELL on 2017/1/3.
 */
@Controller
public class SupplyChainController {

    public static final String TPLDIR_SYNERGY = "supplychain";
    public static final String TPLDIR_SYNERGYA = "channel";
    public static final String SUPPLYDETAI = "tpl.supplychainDetail";
    public static final String SUPPLYDETAILA = "tpl.supplychainDetailA";
    public static final String SUPPLYDETAILAA = "tpl.supplychainDetailAA";
    public static final String SUPPLYDETAILAAA = "tpl.supplychainDetailAAA";
    public static final String SUPPLYDETAILAAAA = "tpl.supplychainDetailAAAA";

    /**
     *众修资源展示
     * @author lisong
     * @param request
     * @param model
     * @return
     */
    @RequiresPermissions("member:supplychainRepairRes")
    @RequestMapping("/member/supplychainRepairRes.jspx")
    public String add(HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        List<SSpare> list = supplychainCreateService.getList();
        model.addAttribute("test", list);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, SUPPLYDETAI);
    }
    /**
     * @Author lisong
     * @Date 2017/2/7 10:27
     *
     */
    @RequiresPermissions("member:supplychainD")
    @RequestMapping("/member/supplychainD.jspx")
    public String get(HttpServletRequest request, ModelMap model,String name,String place,String type) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if(name==null&&place==null&&type==null){
            List<SRepairRes> list = supplychainCreateService.getAl();
            model.addAttribute("testtt", list);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGYA, SUPPLYDETAILA);

        }else{
            if(name!=null) {
                name=java.net.URLDecoder.decode(name, "UTF-8");//设置编码
            }
            if(place!=null){
                place=java.net.URLDecoder.decode(place, "UTF-8");//设置编码
            }
            if(type!=null){
                type=java.net.URLDecoder.decode(type, "UTF-8");//设置编码
            }
            List<SRepairRes> list = supplychainCreateService.getAl(name,place,type);
            model.addAttribute("testtt", list);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGYA, SUPPLYDETAILA);
        }
    }
    /**
     * @Author lisong
     * @Date 2017/2/7 10:28
     *
     */
    @RequiresPermissions("member:supplychainDA")
    @RequestMapping("/member/supplychainDA.jspx")
    public String newPage(HttpServletRequest request, ModelMap model,String name,String place,String type) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if(name==null&&place==null&&type==null){
            List<SRepairRes> list = supplychainCreateService.getAl();
            model.addAttribute("testt", list);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGYA, SUPPLYDETAILAA);
        }else{
            if(name!=null){
                name=java.net.URLDecoder.decode(name, "UTF-8");
            }
            List<SRepairRes> list = supplychainCreateService.getAl(name,place,type);
            model.addAttribute("testt", list);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGYA, SUPPLYDETAILAA);
        }
    }
    /**
     * @Author lisong
     * @Date 2017/2/7 10:28
     *
     */
    @RequiresPermissions("member:supplychainDAA")
    @RequestMapping("/member/supplychainDAA.jspx")
    public String delMess(HttpServletRequest request, ModelMap model,String delNum){
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        int flag = supplychainCreateService.delSupply(delNum);
        if(flag==0){
            model.addAttribute("msg","删除成功");
        }
        String nextUrl = "supplychainDA.jspx";
        return  FrontUtils.showSuccess(request,model,nextUrl);
    }
    //    @RequestMapping(value = "/member/list/mm.jspx", method = RequestMethod.GET)
//    @ResponseBody
//    public JSON test() {
//        List<SSpare> list = supplychainCreateService.getList();
//        return JSONSerializer.toJSON(list);
//    }
    /**
     * @Author lisong
     * @Date 2017/2/7 10:28
     *
     */
    @RequiresPermissions("member:supplychainDAAA")
    @RequestMapping("/member/supplychainDAAA.jspx")
    public String detail(HttpServletRequest request, ModelMap model,String uid){
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        List<SRepairRes> list = supplychainCreateService.getAl(uid);
        model.addAttribute("testttt", list);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGYA, SUPPLYDETAILAAA);
    }
    /**
     * @Author lisong
     * @Date 2017/2/7 10:28
     *
     */
    @RequiresPermissions("member:supplychainDAAAA")
    @RequestMapping("/member/supplychainDAAAA.jspx")
    public String yulan(HttpServletRequest request, ModelMap model,String uid){
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        List<SRepairRes> list = supplychainCreateService.getAl(uid);
        model.addAttribute("testttt", list);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGYA, SUPPLYDETAILAAAA);
    }
    /**
     * @Author lisong
     * @Date 2017/2/7 10:28
     *
     */
    @RequiresPermissions("member:supplychainDAB")
    @RequestMapping("/member/supplychainDAB.jspx")
    public String upda(HttpServletRequest request, ModelMap model,String uid){
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        int flag = supplychainCreateService.updateSupply(uid);
        if(flag==0){
            model.addAttribute("msg","撤回成功");
        }
        String nextUrl = "supplychainDA.jspx";
        return  FrontUtils.showSuccess(request,model,nextUrl);
    }
    @Resource
    private SupplyChainService supplychainCreateService;
}

