package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareStorage;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.supplychain.SSpareDetailMng;
import com.anchorcms.icloud.service.supplychain.SSpareService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
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
import java.net.URLDecoder;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.anchorcms.common.page.SimplePage.cpn;

/**
 *  @Author zhouyq
 * @Email
 * @Date 2016/12/20
 * @Desc 备品备件controller
 */
@Controller
public class SSpareController {
    private static final Logger log = LoggerFactory.getLogger(SSpareController.class);

    public static final String TPLDIR_SYNERGY = "supplychain";
    public static final String TPLDIR_SUPPLYCHAINS = "supplychain";
    public static final String TPL_SPAREDETAILS = "tpl.spareManageDetail";
    public static final String SUPPLYDETAIL = "tpl.supplychainList";
    public static final String SPARELISTS ="tpl.spareManagement";
    public static final String SPAREEDITOR ="tpl.spareUpdate";
    public static final String TPL_SPARESTORAGE ="tpl.spareStorage";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    public static final String TPL_SSPARE_INQUIRYPRICE = "tpl.sspare_inquiryprice";


    /**
     * @author zhouyq
     * @Date 2016/12/27
     * @param spareType, spareName, spareDesc, companyType, addrCity, pageNo, request, model
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @Desc 备品备件列表展示
     */
    @RequiresPermissions("member:supplychainList")
    @RequestMapping("/member/supplychainList.jspx")
    public String list(String spareType, String spareName,
                       String spareDesc, String companyType, String addrCity,String addrProvince,
                       Integer pageNo, HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
        // 解决前台传入的中文乱码参数
        if (spareType != null) {
            spareType = java.net.URLDecoder.decode(spareType, "utf-8");
        }
        if (spareName != null) {
            spareName = java.net.URLDecoder.decode(spareName, "utf-8");
        }
        if (spareDesc != null) {
            spareDesc = java.net.URLDecoder.decode(spareDesc, "utf-8");
        }
        if (companyType != null) {
            companyType = java.net.URLDecoder.decode(companyType, "utf-8");
        }
        if (addrCity != null) {
            addrCity = java.net.URLDecoder.decode(addrCity, "utf-8");
        }
        if (addrProvince != null) {
            addrProvince = java.net.URLDecoder.decode(addrProvince, "utf-8");
        }
        // 获得分页信息
        Pagination pagination = service.getList(spareType, spareName,
                spareDesc, companyType, addrCity, addrProvince, cpn(pageNo),
                CookieUtils.getPageSize(request));
        List paginationList = pagination.getList(); // 必须返回List格式数据
        model.addAttribute("spareList", paginationList);
        model.addAttribute("pagination", pagination);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SYNERGY, SUPPLYDETAIL);
    }

    /**
     * @author liupeng
     * @Date 2017/1/7 16:30
     * @return
     * 备品备件上传发布
     */
    public static final String TPLDIR_SUPPLYCHAIN = "supplychain";
    public static final String TPL_SSpareAdd = "tpl.SSpareAdd";
    @RequestMapping("/member/SSpareAdd.jspx")
    public String fb(HttpServletRequest request, ModelMap model, HttpServletResponse response,String flag) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        // FrontUtils.frontData(request, model, site);
        if (true) {
            FrontUtils.frontData(request, model, site);
            MemberConfig mcfg = site.getConfig().getMemberConfig();
            // 没有开启会员功能
            if (!mcfg.isMemberOn()) {
                return FrontUtils.showMessage(request, model, "member.memberClose");
            }
            if (user == null) {
                return FrontUtils.showLogin(request, model, site);
            }
            if (user.getCompany() == null) {
                return FrontUtils.showMessage(request, model, "请完善您的企业信息");
            }

            // 获得本站栏目列表
            Set<Channel> rights = user.getGroup().getContriChannels();
            List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
            List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
            model.addAttribute("site", site);
            model.addAttribute("flag",flag);
            model.addAttribute("companyId",user.getCompany().getCompanyId());
            model.addAttribute("channelList", channelList);
            model.addAttribute("sessionId", request.getSession().getId());
            model.addAttribute("user", user);
            return FrontUtils.getTplPath(request, site.getSolutionPath(), TPLDIR_SUPPLYCHAIN, TPL_SSpareAdd);
        } else {
            WebErrors errors = WebErrors.create(request);
            errors.addErrorCode("error.uploadMoreNumber", user.getGroup().getId());
            return FrontUtils.showError(request, response, model, errors);
        }
    }

    /**
     * Created by 刘鹏 2016/12/27
     * 备品备件信息上传保存
     */
    @RequestMapping(value = "/member/SSpare.jspx")
    public String saveSSpare(HttpServletRequest request, HttpServletResponse response,
                             ModelMap model,String spareType,String spareName,String spareDesc,String detailDesc,String flag,String fax,String email,
                             Double inventCount, String addrProvince,String addrCity,String addrCounty,String userType,String source,String telephone,
                              String status,String addrStreet,String contact,String mobile, Integer modelId,Integer channelId,String nextUrl,String companyIds,
                             String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,Double referPrice) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SSpare sspare = new SSpare();



        String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
        sspare.setSparepartsId(uid);
        sspare.setSpareName(spareName);
        sspare.setSpareType(spareType);
        sspare.setInventCount(inventCount);
        sspare.setEmail(email);
        sspare.setFax(fax);
        sspare.setTelephone(telephone);

        SCompany company = new SCompany();

        company.setCompanyId(companyIds);
        sspare.setCompany(company);


        sspare.setAddrProvince(addrProvince);
        sspare.setAddrCity(addrCity);
        sspare.setSource(addrCity);
        sspare.setAddrCounty(addrCounty);
        sspare.setAddrStreet(addrStreet);
        sspare.setContact(contact);
        sspare.setMobile(mobile);
        sspare.setStatus(status);
        sspare.setUserType(flag);
        sspare.setCreateDt(date);
        sspare.setPublicType("0");
        sspare.setReferPrice(referPrice);//设置价格
        sspare.setCreaterId(user.getUserId().toString());
        if (flag.equals("1")){
            sspare.setReleaseDt(date);

        }


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
        ext.setTitle(spareName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("备品备件信息上传");
        ContentTxt t = new ContentTxt();
        t.setTxt(spareDesc);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }


        c=service.saveSSpare(sspare,c,ext,t,channelId,typeId,user,true,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,picDescs);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @author李洪波
     * @Desc备品备件列表展示
     * @param pageNo
     * @param request
     * @param model
     * @return
     * @throws UnsupportedEncodingException
     */

    public static final String CHECK_PAGE="tpl.supplychaininSspare_manager_check";
    public static final String ADMIN_SPARE_MANAGE="tpl.admin_spare_manage";
    @RequiresPermissions("member:spareManagement")
    @RequestMapping("/member/spareManagement.jspx")
    public String showSpareList(Date startDate, Date endDate, String publicType, String status, String spareType, String source,
                                String companyId, Integer pageNo, Integer pageSize, HttpServletRequest request,
                                ModelMap model, HttpServletResponse response, String flag) throws UnsupportedEncodingException{
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        if (user.getCompany() == null) {
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        companyId=user.getCompany().getCompanyId();
        FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        //没有登录
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //获取分页对象，
        Pagination pp= service.findByTimeCateSourCompany(startDate,endDate,publicType,status,spareType, source,companyId, cpn(pageNo), 5,user.getUserId(), flag);
        model.addAttribute("pagination", pp);
        //将查询条件放入model中
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("publicType", publicType);
        model.addAttribute("status", status);
        model.addAttribute("spareType", spareType);
        model.addAttribute("source", source);
        model.addAttribute("flag",flag);
        if(flag!=null){
            if(flag.equals("3") ){
                return FrontUtils.getTplPath(request, site.getSolutionPath(),
                        TPLDIR_SYNERGY,CHECK_PAGE);
            }
            else if(flag.equals("1") ){
                return FrontUtils.getTplPath(request, site.getSolutionPath(),
                        TPLDIR_SYNERGY,ADMIN_SPARE_MANAGE);
            }
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SYNERGY, SPARELISTS);
    }

    /**
     * @author李洪波
     * @Desc根据备品ID查看备品明细
     * @param id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("member:spareManageDetail")
    @RequestMapping("/member/spareManageDetail.jspx")
    public String findById(String id, HttpServletRequest request,
                           HttpServletResponse response, ModelMap model, String flag) {
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
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        SSpare spare = sSpareMng.findById(id);
        model.addAttribute("spare", spare);
        Content content = spare.getContent();
        model.addAttribute("flag",flag);
        model.addAttribute("site", site);
        model.addAttribute("content", content);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAINS, TPL_SPAREDETAILS);
    }

    /**
     * @return
     * @author李洪波
     * @Desc更改备品备件的发布状态
     */
    @RequestMapping(value = "/member/spareManageUpdateStatus.jspx")
    public String setSpareStatusById(String ids, HttpServletRequest request,
                                     String nextUrl, HttpServletResponse response, ModelMap model, String status, String flag) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if(flag.equals("1")){
            nextUrl="/member/spareManagement.jspx?status=1&flag=1";
        }else{
            nextUrl="/member/spareManagement.jspx?status=1";
        }
        service.updateSpareStatus(ids,status,date,flag);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author李洪波
     * @param ids
     * @param publicType
     * @param nextUrl
     * @param request
     * @param model
     * @return
     * @Desc根据id批量修改备品备件公开状态
     */
    @RequestMapping("/member/spareManageUpdate.jspx")
    public String updateById(String ids, String publicType,String nextUrl, HttpServletResponse response, HttpServletRequest request,
                             ModelMap model, String flag){
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
            String[] p = publicType.split(",");
            service.update(ids, p[0]);
            if (flag.equals("1")) {
                nextUrl = "/member/spareManagement.jspx?status=3&flag=1";
            } else {
                nextUrl = "/member/spareManagement.jspx?status=3";
            }
            return FrontUtils.showSuccess(request, model, nextUrl);

    }

    /**
     * @author李洪波
     * @Desc备品备件删除
     * @param id
     * @param request
     * @param response
     * @param model
     * @param flag
     * @return
     */
    @RequestMapping("/member/spareManageDelete.jspx")
    public String deleteById(String id, HttpServletRequest request,
                             HttpServletResponse response, String nextUrl, ModelMap model, String flag){
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
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        service.delete(id,flag);
        if (flag.equals("1")) {
            nextUrl = "/member/spareManagement.jspx?status=1&flag=1";
        } else {
            nextUrl = "/member/spareManagement.jspx?status=1";
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author李洪波
     * @Desc备品备件修改
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/member/spareUpdate.jspx")
    public String SSpareUpdate(HttpServletRequest request,HttpServletResponse response,
                               ModelMap model,String id, String flag){
        String nextUrl= SPAREEDITOR;
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

        SSpare spare = sSpareMng.findById(id);
        if(flag.equals("1")){}else{
            if(user.getCompany().getCompanyId() !=spare.getCompany().getCompanyId()){
                return FrontUtils.showMessage(request, model, "error.noPermissionsView");
            }
        }

        Content content = spare.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,true);
        model.addAttribute("spare",spare);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("flag",flag);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAINS, nextUrl);
    }

    /**
     * @Author李洪波
     * @Desc备品备件编辑
     * @return
     */
    @RequestMapping("/member/spareEdit.jspx")
    public String spareEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                            //cms表相关参数
                            String editor,
                            Integer modelId,String[] attachmentPaths, String[] attachmentNames,
                            String[] attachmentFilenames, String[] picPaths, String[] picDescs,Integer channelId,Short charge,
                            //备品备件相关属性
                            String spareType,String spareName,String spareDesc,String detailDesc,String flag,String fax,String email,
                            Double inventCount, String addrProvince,String addrCity,String addrCounty,String telephone,
                            String status,String addrStreet,String contact,String mobile,String id,String nextUrl,Double referPrice
                            ){

        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SSpare s=sSpareMng.findById(id);
        Content c=s.getContent();
        s.setSpareName(spareName);
        s.setSpareDesc(spareDesc);
        s.setSpareType(spareType);
        s.setFax(fax);
        s.setEmail(email);
        s.setInventCount(inventCount);
        s.setAddrProvince(addrProvince);
        s.setAddrCounty(addrCounty);
        s.setAddrCity(addrCity);
        s.setAddrStreet(addrStreet);
        s.setSource(addrCity);
        s.setTelephone(telephone);
        s.setContact(contact);
        s.setMobile(mobile);
        s.setUpdateDt(new Date(System.currentTimeMillis()));
        s.setStatus(status);
        s.setReferPrice(referPrice);//保存价格

        c.setSite(site);
        ContentExt ext = c.getContentExt();
        ext.setAuthor(user.getUsername());
        ext.setDescription("备品备件编辑");
        ContentTxt t = c.getContentTxt();

        //若表中无contentTxt的数据，新增一条
        if(editor == null){
            editor = "";
        }
        if(t == null){
            ContentTxt contentTxt = new ContentTxt();
            contentTxt.setContent(c);
            contentTxt.setTxt(editor);
            t = contentTxt;
            contentTxtMng.save(t, c);
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
        service.edit(s,c,ext,t,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,picDescs,channelId,typeId,user,charge,true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * Created by hansx 2016/6/9
     * 备品备件出入库信息保存
     */
    @RequestMapping(value = "/member/SSpare_storage_save.jspx")
    public String saveSSpareStorage(HttpServletRequest request,
                             ModelMap model,String type,String id,Double count,String remark) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //添加库存信息表
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SSpareStorage sSpareStorage = new SSpareStorage();
        sSpareStorage.setSpareId(id);
        sSpareStorage.setType(type);
        sSpareStorage.setCount(count);
        sSpareStorage.setRemark(URLDecoder.decode(remark,"UTF-8"));
        sSpareStorage.setCreateId(user.getUserId().toString());
        sSpareStorage.setCreateDt(date);
        sSpareMng.save(sSpareStorage);
        //备品信息库存数量修改。
        SSpare sSpare =  sSpareMng.findById(id);
        Double inventCount = sSpare.getInventCount();
        if(count!=null) {
            if (type.equals("1")) {
                inventCount += count;
            } else {
                inventCount = inventCount - count;
            }
        }
        sSpare.setInventCount(inventCount);
        sSpareMng.updateInvnetCount(sSpare);

       String  nextUrl="/member/spareManagement.jspx?flag=1";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * Created by hansx 2016/6/9
     * 备品备件出入库信息保存
     */
    @RequestMapping(value = "/member/SSpare_storage_get.jspx")
    public String getSSpareStorage(HttpServletRequest request,
                                    ModelMap model,String id,int pageNo) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        // 获得分页信息
        Pagination p = service.getSSpareStorageList(id, cpn(pageNo),20);
        model.addAttribute("pagination", p);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAINS, TPL_SPARESTORAGE);
    }
    @RequestMapping(value = "/member/SSpare_storage_save2.jspx")
    public String saveSSpareStorage2(HttpServletRequest request,
                                    ModelMap model,String type,String id,Double count,String remark) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //添加库存信息表
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SSpareStorage sSpareStorage = new SSpareStorage();
        sSpareStorage.setSpareId(id);
        sSpareStorage.setType(type);
        sSpareStorage.setCount(count);
        sSpareStorage.setRemark(URLDecoder.decode(remark,"UTF-8"));
        sSpareStorage.setCreateId(user.getUserId().toString());
        sSpareStorage.setCreateDt(date);
        sSpareMng.save(sSpareStorage);
        //备品信息库存数量修改。
        SSpare sSpare =  sSpareMng.findById(id);
        Double inventCount = sSpare.getInventCount();
        if(count!=null) {
            if (type.equals("1")) {
                inventCount += count;
            } else {
                inventCount = inventCount - count;
            }
        }
        sSpare.setInventCount(inventCount);
        sSpareMng.updateInvnetCount(sSpare);

        String  nextUrl="/member/spareManagement.jspx?flag=0";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author liuyang
     * @Date 2017/12/15 15:18
     * @return
     * 备品备件询价页面
     */
    @RequestMapping(value = "/member/sspareinquiryprice.jspx")
    public String findRepairResById(String id, HttpServletRequest request,
                                    HttpServletResponse response, ModelMap model) {
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
            model.addAttribute("site", site);
            model.addAttribute("channel",channelMng.findById(108));
            model.addAttribute("channelList", channelList);
            model.addAttribute("sessionId", request.getSession().getId());
            model.addAttribute("channel",channelMng.findById(99));
            model.addAttribute("user", user);
            if (id != null&&!id.equals("")) {
                //根据id获取维修资源，
                SSpare sspare=sSpareMng.findById(id);
                if(sspare!=null)
                    model.addAttribute("sspare", sspare);

            }
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_SUPPLYCHAIN, TPL_SSPARE_INQUIRYPRICE);
        } else {

            WebErrors errors = WebErrors.create(request);
            errors.addErrorCode("error.uploadMoreNumber", user.getGroup().getId());
            return FrontUtils.showError(request, response, model, errors);
        }
    }

    @Resource
    private SSpareService service;

    @Resource
    private CmsModelMng cmsModelMng;

    @Resource
    private ContentTypeMng contentTypeMng;

    @Resource
    private ChannelMng channelMng;

    @Resource
    SSpareDetailMng sSpareMng;

    @Resource
    protected ContentMng contentMng;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;

}
