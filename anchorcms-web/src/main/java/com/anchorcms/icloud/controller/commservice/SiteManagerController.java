package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.CmsModel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.commservice.SStiteManager;
import com.anchorcms.icloud.service.commservice.SiteManagerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author zhouyq
 * @Date 2017/01/13
 * @Desc 政府网站维护controller
 */
@Controller
public class SiteManagerController {
    private static final Logger log = LoggerFactory.getLogger(SStiteManager.class);

    public static final String TPLDIR_SYNERGY = "commservice";
    public static final String SITEOFGOVERNMENTLIST = "tpl.siteOfGovernmentList"; // 政府网站列表
    public static final String SITEOFGOVERNMENTLIST_ADD = "tpl.siteOfGovernmentList_add"; // 政府网站新增
    public static final String SITEOFGOVERNMENTLIST_MDY = "tpl.siteOfGovernmentList_mdy"; // 政府网站修改

    @Resource
    private SiteManagerService service;

    @Resource
    private CmsModelMng cmsModelMng;

    @Resource
    private ContentTypeMng contentTypeMng;

    @Resource
    private ChannelMng channelMng;

    /**
     * @param stiteName, pageNo, request, model
     * @return
     * @throws UnsupportedEncodingException
     * @author zhouyq
     * @Date 2017/01/13
     * @Desc 政府网站列表展示
     */
    @RequiresPermissions("member:siteOfGovernmentList")
    @RequestMapping(value = "/member/siteOfGovernmentList.jspx")
    public String list(String stiteName, String addrProvince, String address, Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String status,String flag,String stiteId
                       ) throws UnsupportedEncodingException {

             CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
//        if (addrProvince != null) { // 省（区）
//            addrProvince = java.net.URLDecoder.decode(addrProvince, "utf-8");
//        }
        if (address != null) {
            address = java.net.URLDecoder.decode(address, "utf-8");
            if (address.equals("nmgzzqzf")) {
                address = "内蒙古自治区政府";
            } else if (address.equals("hhhts")) {
                address = "呼和浩特市";
            } else if (address.equals("bts")) {
                address = "包头市";
            } else if (address.equals("eedss")) {
                address = "鄂尔多斯市";
            } else if (address.equals("wlcbs")) {
                address = "乌兰察布市";
            } else if (address.equals("whs")) {
                address = "乌海市";
            } else if (address.equals("hlbes")) {
                address = "呼伦贝尔市";
            } else if (address.equals("tls")) {
                address = "通辽市";
            } else if (address.equals("cfs")) {
                address = "赤峰市";
            } else if (address.equals("bynes")) {
                address = "巴彦淖尔市";
            } else if (address.equals("xlglm")) {
                address = "锡林郭勒盟";
            } else if (address.equals("xam")) {
                address = "兴安盟";
            } else if (address.equals("alsm")) {
                address = "阿拉善盟";
            } else if (address.equals("zfcgw")) {
                address = "政府采购网";
            }
        }
//        if (address != null) { // 地区
//            address = java.net.URLDecoder.decode(address, "utf-8");
//        }
        if (flag != null) {
            if (flag.equals("release")) {
                service.setstatus(stiteId, "3");
            } else if (flag.equals("reback")) {
                service.setstatus(stiteId, "1");
            } else if (flag.equals("delete")) {
                service.delSiteById(stiteId);
            }
        }
        // 获得分页信息
        Pagination pagination = service.getList(stiteName, addrProvince, address,status,cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("status", status);
        model.addAttribute("stiteName", stiteName);
        model.addAttribute("address", address);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, SITEOFGOVERNMENTLIST);
    }

    /**
     * @param stiteName, addrProvince, address, pageNo, request, response, model
     * @return
     * @throws UnsupportedEncodingException
     * @author zhouyq
     * @Date 2017/02/23
     * @Desc 公共服务二级页面政务导航根据地区查询相应网站
     */
    @RequestMapping(value = "/siteOfGovernment.jspx", method = RequestMethod.POST)
    public void getaddress(String stiteName, String addrProvince, String address, Integer pageNo, HttpServletRequest request,
                           HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        response.addHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
        // 解决前台传入的中文乱码参数

        if (stiteName != null) { // 网站名称
            stiteName = java.net.URLDecoder.decode(stiteName, "utf-8");
        }
        if (addrProvince != null) { // 省（区）
            addrProvince = java.net.URLDecoder.decode(addrProvince, "utf-8");
        }
        if (address != null) { // 地区
            address = java.net.URLDecoder.decode(address, "utf-8");
        }
        // 获得分页信息
        String jsonStr = this.service.getPage(addrProvince, stiteName, address, cpn(pageNo), 50);
        try {
            PrintWriter writer = response.getWriter();
            writer.print(jsonStr);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     * @author zhouyq
     * @Date 2017/1/14
     * @description 政府网站新增页面
     */
    @RequestMapping(value = "/member/siteOfGovernmentList_add.jspx")
    public String save(HttpServletRequest request, ModelMap model, HttpServletResponse response, String flag) {

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
        model.addAttribute("site", site);
        model.addAttribute("flag", flag);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(), TPLDIR_SYNERGY, SITEOFGOVERNMENTLIST_ADD);
    }

    /**
     * @return
     * @author zhouyq
     * @Date 2017/1/14
     * @description 政府网站新增、修改保存
     */
    @RequestMapping(value = "/member/siteOfGovernmentList_add_save.jspx")
    public String siteAddAndMdySave(HttpServletRequest request, HttpServletResponse response, ModelMap model, String stiteId,
                       String stiteName, String addrProvince, String stiteAddress, String address, Date createrDt, Date updateDt, String nextUrl,String statusType,
                       String operType, String status) throws UnsupportedEncodingException {

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        String uid = "";
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis()); // 获取当前时间
        SStiteManager sSiteManagerEntity = new SStiteManager();
//        if (stiteName != null) {
//            stiteName = java.net.URLDecoder.decode(stiteName, "utf-8");
//        }
//        if (address != null) { // 地区
//            address = java.net.URLDecoder.decode(address, "utf-8");
//        }
//        if (addrProvince != null) { // 省（区）
//            address = java.net.URLDecoder.decode(addrProvince, "utf-8");
//        }
        sSiteManagerEntity.setStiteName(stiteName);
        sSiteManagerEntity.setStiteAddress(stiteAddress);
//        if (!(("").equals(addrProvince))) {
//            sSiteManagerEntity.setAddrProvince(addrProvince);
//        }
//        if (!(("").equals(address)) && (!(address.contains("区")) && (!(address.contains("省"))) )) {
//            sSiteManagerEntity.setAddress(address);
//        }
//        if (("北京市").equals(address)) {
//            sSiteManagerEntity.setAddress("北京市市辖区");
//        }
//        if (("天津市").equals(address)) {
//            sSiteManagerEntity.setAddress("天津市市辖区");
//        }
        sSiteManagerEntity.setUpdateDt(date);
        sSiteManagerEntity.setStatus(status);
        sSiteManagerEntity.setAddress(address);

        if (address.equals("内蒙古自治区政府")) {
            address = "nmgzzqzf";
        } else if (address.equals("呼和浩特市")) {
            address = "hhhts";
        } else if (address.equals("包头市")) {
            address = "bts";
        } else if (address.equals("鄂尔多斯市")) {
            address = "eedss";
        } else if (address.equals("乌兰察布市")) {
            address = "wlcbs";
        } else if (address.equals("乌海市")) {
            address = "whs";
        } else if (address.equals("呼伦贝尔市")) {
            address = "hlbes";
        } else if (address.equals("通辽市")) {
            address = "tls";
        } else if (address.equals("赤峰市")) {
            address = "cfs";
        } else if (address.equals("巴彦淖尔市")) {
            address = "bynes";
        } else if (address.equals("锡林郭勒盟")) {
            address = "xlglm";
        } else if (address.equals("兴安盟")) {
            address = "xam";
        } else if (address.equals("阿拉善盟")) {
            address = "alsm";
        } else if (address.equals("政府采购网")) {
            address = "zfcgw";
        }
        if (operType.equals("siteAdd")) { // 新增保存
            uid = UUID.randomUUID().toString().replace("-", ""); // 用来生成数据库的主键id
            sSiteManagerEntity.setStiteId(uid);
            sSiteManagerEntity.setCreaterDt(date);
            sSiteManagerEntity.setStatus(status);
            service.siteAddSave(sSiteManagerEntity);
            if (status.equals("3")) {
                nextUrl = "/member/siteOfGovernmentList.jspx?status=3&address=" + address;
            }
            if (status.equals("1")) {
                nextUrl = "/member/siteOfGovernmentList.jspx?status=1&address=" + address;
            }
        }
        if (operType.equals("siteMdy")) { // 修改保存
            sSiteManagerEntity.setStiteId(stiteId);
            service.siteMdySave(sSiteManagerEntity);
            nextUrl = "/member/siteOfGovernmentList.jspx?status=1&address=" + address;
        }

        Content c = new Content();
        c.setSite(site);
        CmsModel defaultModel = cmsModelMng.getDefModel();
//        if (modelId != null) {
//            CmsModel m = cmsModelMng.findById(modelId);
//            if (m != null) {
//                c.setModel(m);
//            } else {
//                c.setModel(defaultModel);
//            }
//        } else {
//            c.setModel(defaultModel);
//        }
//        ContentExt ext = new ContentExt();
//        ext.setTitle("维修资源发布");
//        ext.setAuthor(user.getUsername());
//        ext.setDescription("维修资源发布");
//        ContentTxt t = new ContentTxt();
//        t.setTxt(detailDesc);
//        ContentType type = contentTypeMng.getDef();
//        if (type == null) {
//            throw new RuntimeException("Default ContentType not found.");
//        }
//        Integer typeId = type.getTypeId();
//        if (c.getRecommendLevel() == null) {
//            c.setRecommendLevel((byte) 0);
//        }
//        c = masterManagerService.supplychain_save(sRepairResEntity, c, ext, t, channelId, typeId, user, true, attachmentPaths, attachmentNames, attachmentFilenames, picPaths, picDescs);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author zhouyq
     * @Date 2017/1/14
     * @return
     * @description 政府网站修改界面
     */
    @RequestMapping("/member/siteOfGovernmentList_mdy.jspx")
    public String siteMdy(HttpServletRequest request, HttpServletResponse response,
                                          ModelMap model, String stiteId) {
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
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        SStiteManager siteManagerEntity = service.getSiteManagerEntity(stiteId);
//        Content content = siteManagerEntity.getContent();
        model.addAttribute("siteManagerEntity", siteManagerEntity);
//        model.addAttribute("content", content);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, SITEOFGOVERNMENTLIST_MDY);
    }

    /**
     * @param stiteId, request, response, model
     * @return
     * @author zhouyq
     * @Date 2016/01/14
     * @Desc 根据id删除政府网站记录
     */
    public String delDemandStatById(String stiteId, String nextUrl, String deleteAddress,
                                    HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if (stiteId != null) {
//            stiteId = java.net.URLDecoder.decode(stiteId, "utf-8");
            service.delSiteById(stiteId);
        }
        if (deleteAddress.equals("内蒙古自治区政府")) {
            deleteAddress = "nmgzzqzf";
        } else if (deleteAddress.equals("呼和浩特市")) {
            deleteAddress = "hhhts";
        } else if (deleteAddress.equals("包头市")) {
            deleteAddress = "bts";
        } else if (deleteAddress.equals("鄂尔多斯市")) {
            deleteAddress = "eedss";
        } else if (deleteAddress.equals("乌兰察布市")) {
            deleteAddress = "wlcbs";
        } else if (deleteAddress.equals("乌海市")) {
            deleteAddress = "whs";
        } else if (deleteAddress.equals("呼伦贝尔市")) {
            deleteAddress = "hlbes";
        } else if (deleteAddress.equals("通辽市")) {
            deleteAddress = "tls";
        } else if (deleteAddress.equals("赤峰市")) {
            deleteAddress = "cfs";
        } else if (deleteAddress.equals("巴彦淖尔市")) {
            deleteAddress = "bynes";
        } else if (deleteAddress.equals("锡林郭勒盟")) {
            deleteAddress = "xlglm";
        } else if (deleteAddress.equals("兴安盟")) {
            deleteAddress = "xam";
        } else if (deleteAddress.equals("阿拉善盟")) {
            deleteAddress = "alsm";
        } else if (deleteAddress.equals("政府采购网")) {
            deleteAddress = "zfcgw";
        }
//        deleteAddress = java.net.URLDecoder.decode(deleteAddress, "utf-8");
        nextUrl = "/member/siteOfGovernmentList.jspx?address=" + deleteAddress;
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
}
