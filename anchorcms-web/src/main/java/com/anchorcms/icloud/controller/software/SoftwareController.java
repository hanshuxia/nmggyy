package com.anchorcms.icloud.controller.software;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.ResponseUtils;
import com.anchorcms.core.model.CmsConfig;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.payment.PaySettlementRefund;
import com.anchorcms.icloud.model.software.SSoftware;
import com.anchorcms.icloud.model.software.SSoftwareBuy;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPaymentService;
import com.anchorcms.icloud.service.payment.ReceiveNoticeService;
import com.anchorcms.icloud.service.software.SoftwareService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import payment.api.system.PaymentEnvironment;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.anchorcms.common.constants.Constants.*;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Created by ly on 2017/1/4.
 *
 * @desc 软件应用管理类
 */
@Controller
public class SoftwareController {
    private static final Logger log = LoggerFactory.getLogger(SoftwareController.class);
    public static final String SOFTWARE_LIST = "tpl.softwareList";
    public static final String SOFTWARE_ADD = "tpl.softwareAdd";
    public static final String SOFTWARE_EDIT = "tpl.softwareEdit";
    public static final String SOFTWARE_VIEW = "tpl.softwareView";
    public static final String SOFTWARE_INFO = "tpl.softwareInfo";
    public static final String SOFTWARE_BUYLIST = "tpl.softwareBuyList";
    public static final String SOFTWARE_ORDERLIST = "tpl.softwareOrderList";
    public static final String SOFTWARE_SELLERORDERLIST = "tpl.softwareSellerOrderList";
    public static final String softwareDetail = "tpl.softwareDetail";


    /**
     * @author ly
     * @date 2017/1/4 16:41
     * @desc 打开软件信息新增页面
     **/
    @RequestMapping(value = "/member/software_add.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                      String nextUrl, Integer modelId, Integer channelId) {
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
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SOFTWARE_ADD);
    }


    /**
     * @param request
     * @param response
     * @param model
     * @param companyName         公司名称
     * @param softwareType        软件类型
     * @param payType             付费类型
     * @param isOnline            是否在线应用
     * @param softwareName        软件名称
     * @param softwareSize        软件大小
     * @param softwareHref        链接地址
     * @param detailDesc          文字说明
     * @param picPaths            图片地址
     * @param picDescs            图片描述
     * @param attachmentPaths     附件地址
     * @param attachmentNames     附件名称
     * @param attachmentFilenames 附件文件名
     * @param addrProvince        所在地区-省
     * @param addrCity            所在地区-市
     * @param addrCounty          所在地区-区
     * @param addrStreet          所在地区-街道
     * @param contact             联系人
     * @param mobile              联系电话
     * @param modelId
     * @param channelId
     * @param charge
     * @author ly
     * @date 2017/1/4 17:26
     * @desc 软件信息保存
     **/
    @RequestMapping(value = "/member/software_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String companyName, String softwareType, String payType, String isAgency, String isOnline,
                       String softwareName, String softwareSize, String softwareHref, double price,
                       String detailDesc, String[] picPaths, String[] picDescs,
                       String[] attachmentPaths, String[] attachmentNames,
                       String[] attachmentFilenames, String addrProvince, String addrCity,
                       String addrCounty, String addrStreet, String contact, String mobile,
                       Integer modelId, Integer channelId, Short charge) {

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
        ext.setTitle(softwareName);
        ext.setAuthor(user.getUsername());
        ext.setDescription("软件信息");
        ContentTxt t = new ContentTxt();
        t.setTxt(detailDesc);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if (c.getRecommendLevel() == null) {
            c.setRecommendLevel((byte) 0);
        }
        SSoftware software = new SSoftware();
        software.setCompanyName(companyName); //公司名称
        software.setSoftwareName(softwareName); //软件名称
        software.setSoftwareType(softwareType); //软件类型
        software.setPayType(payType); //付费类型
        software.setAgencyFlag(isAgency); // 是否平台代理
        software.setPrice(price); // 价格
        software.setIsOnline(isOnline); // 是否在线应用
        software.setSoftwareSize(softwareSize); // 软件大小
        software.setSoftwareHref(softwareHref); // 链接地址
        software.setAddrProvince(addrProvince); // 所在地区-省
        software.setAddrCity(addrCity); // 所在地区-市
        software.setAddrCounty(addrCounty); // 所在地区-区
        software.setAddrStreet(addrStreet); // 所在地区-街道
        software.setContact(contact); // 联系人
        software.setMobile(mobile); // 联系电话
        software.setStatus("0"); // 状态
        software.setUser(user); // 创建人
        software.setCreateDt(new Date(System.currentTimeMillis())); // 创建时间
        software.setUpdateDt(new Date(System.currentTimeMillis())); // 更新时间

        c = softwareService.save(software, c, ext, t, attachmentPaths, attachmentNames,
                attachmentFilenames, picPaths, picDescs, channelId, typeId, user, charge, true);
        String nextUrl = "/member/software_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author ly
     * @date 2017/1/4 17:26
     * @desc 软件信息列表
     **/
    @RequiresPermissions("member:software_list")
    @RequestMapping("/member/software_list.jspx")
    public String list(HttpServletRequest request, ModelMap model, Integer pageNo,
                       String softwareType, String softwareName, String status) {
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
        //非管理员不能查看
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = softwareService.getSoftwarePage(site.getSiteId(), user, cpn(pageNo),
                20, softwareType, softwareName, status);
        model.addAttribute("pagination", p);
        model.addAttribute("softwareType", softwareType);
        model.addAttribute("softwareName", softwareName);
        model.addAttribute("status", status);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SOFTWARE_LIST);
    }

    /**
     * @author ly
     * @date 2017/1/4 17:26
     * @desc 软件信息修改
     **/
    @RequestMapping(value = "/member/software_edit.jspx")
    public String edit(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
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
        SSoftware software = softwareService.findById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("software", software);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SOFTWARE_EDIT);
    }

    /**
     * @param request
     * @param response
     * @param model
     * @param id                  软件id
     * @param companyName         公司名称
     * @param softwareType        软件类型
     * @param payType             付费类型
     * @param isOnline            是否在线应用
     * @param softwareName        软件名称
     * @param softwareSize        软件大小
     * @param softwareHref        链接地址
     * @param detailDesc          文字说明
     * @param picPaths            图片地址
     * @param picDescs            图片描述
     * @param attachmentPaths     附件地址
     * @param attachmentNames     附件名称
     * @param attachmentFilenames 附件文件名
     * @param addrProvince        所在地区-省
     * @param addrCity            所在地区-市
     * @param addrCounty          所在地区-区
     * @param addrStreet          所在地区-街道
     * @param contact             联系人
     * @param mobile              联系电话
     * @param nextUrl             跳转链接
     * @param modelId
     * @param channelId
     * @param charge
     * @author ly
     * @date 2017/1/4 17:26
     * @desc 软件信息更新
     **/
    @RequestMapping(value = "/member/software_update.jspx")
    public String update(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, String companyName, String softwareType, String payType,
                         String isOnline, String softwareName, String softwareSize, double price,
                         String softwareHref, String detailDesc, String[] picPaths,
                         String[] picDescs, String[] attachmentPaths, String[] attachmentNames,
                         String[] attachmentFilenames, String addrProvince, String addrCity,
                         String addrCounty, String addrStreet, String contact, String mobile,
                         String nextUrl, Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SSoftware software = new SSoftware();
        software.setCompanyName(companyName);//公司名称
        software.setSoftwareName(softwareName);//软件名称
        software.setSoftwareType(softwareType);//软件类型
        software.setPayType(payType);//付费类型
        software.setPrice(price);//价格
        software.setIsOnline(isOnline);//是否在线应用
        software.setSoftwareSize(softwareSize);//软件大小
        software.setSoftwareHref(softwareHref);//链接地址
        software.setAddrProvince(addrProvince);//所在地区-省
        software.setAddrCity(addrCity);//所在地区-市
        software.setAddrCounty(addrCounty);//所在地区-区
        software.setAddrStreet(addrStreet);//所在地区-街道
        software.setContact(contact);//联系人
        software.setMobile(mobile);//联系电话
        software.setUpdateDt(new Date(System.currentTimeMillis()));
        //更新软件信息
        softwareService.update(id, software, detailDesc, attachmentPaths, attachmentNames,
                attachmentFilenames, picPaths, picDescs, channelId, user, charge, true);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param id       软件id
     * @param request
     * @param response
     * @param model
     * @return
     * @author ly
     * @date 2017/1/4 17:26
     * @desc 软件信息删除
     **/
    @RequestMapping(value = "/member/software_delete.jspx")
    public String delete(Integer id, HttpServletRequest request, String nextUrl,
                         HttpServletResponse response, ModelMap model) {
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
        //删除软件实体类
        softwareService.deleteById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author ly
     * @date 2017/1/5 13:42
     * @desc 软件信息明细
     **/
    @RequestMapping(value = "/member/software_view.jspx")
    public String view(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
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
        //获取软件实体类
        SSoftware software = softwareService.findById(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("software", software);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SOFTWARE_VIEW);
    }

    /**
     * @param id        软件id
     * @param request
     * @param response
     * @param model
     * @param status    状态
     * @param nextUrl   跳转链接
     * @param modelId
     * @param channelId
     * @param charge
     * @author ly
     * @date 2017/1/5 16:17
     * @desc 软件状态管理
     **/
    @RequestMapping(value = "/member/software_manage.jspx")
    public String manage(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, String status, String states,
                         String nextUrl, Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //更新软件状态
        softwareService.update(id, status, channelId, user, charge, true);
        if ("0".equals(states)) {
            nextUrl += "?status=0";
            return FrontUtils.showSuccess(request, model, nextUrl);
        } else if ("2".equals(states)) {
            nextUrl += "?status=2";
            return FrontUtils.showSuccess(request, model, nextUrl);
        } else {
            nextUrl += "?status=1";
            return FrontUtils.showSuccess(request, model, nextUrl);
        }

    }

    /**
     * @author ly
     * @date 2017/1/5 13:42
     * @desc 软件信息前台页面
     **/
    @RequestMapping(value = "/rjyyRjjsym/software_info.jspx")
    public String info(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        //获取软件实体类
        SSoftware software = softwareService.findById(id);
        model.addAttribute("software", software);
        model.addAttribute("site", site);
        model.addAttribute("channel", channelMng.findById(119));
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CONTENT, SOFTWARE_INFO);
    }

    /**
     * @Author 闫浩芮
     * 浏览次数+1
     * @Date 2017/2/24 0024 16:06
     */

    @RequestMapping(value = "/rjyyRjjsym/software_info_browse.jspx")
    public String infoAddBrowse(Integer id, HttpServletRequest request,
                                HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        //获取软件实体类
        SSoftware software = softwareService.addBrowse(id);
        model.addAttribute("software", software);
        model.addAttribute("site", site);
        model.addAttribute("channel", channelMng.findById(119));
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CONTENT, SOFTWARE_INFO);
    }

    /**
     * @return 帮助文档下载
     * @author machao
     * @Date 2017/3/1 17:21
     */
    @RequestMapping(value = "/downloadfile.jspx")
    public void fileDownload(HttpServletResponse response, HttpServletRequest request,String cyrh) throws ServletException, IOException {
        // TODO Auto-generated method stub

        //获得请求文件名
        String filename = "";
        if (cyrh == null) {
             filename = "INDUSTRIAL CLOUD.doc";
        }else {
            filename = "Operation manual .doc";
        }
            filename = new String(filename.getBytes(), "GBK");
            System.out.println(filename);

        //设置文件MIME类型
        response.setContentType(request.getServletContext().getMimeType(filename));
        //设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        //读取目标文件，通过response将目标文件写到客户端
        //获取目标文件的绝对路径
        String fullFileName = request.getServletContext().getRealPath("/r/cms/www/default/resources/" + filename);
        //读取文件
        InputStream in = new FileInputStream(fullFileName);
        OutputStream out = response.getOutputStream();
        //写文件
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
        in.close();
        out.close();
    }

    @RequestMapping(value = "/member/attachment.jspx")
    public void attachment(Integer id, HttpServletRequest request, HttpServletResponse response,
                           ModelMap model) throws IOException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        if (id == null) {
            ResponseUtils.renderText(response, "downlaod error!");
        }
        CmsConfig config = CmsUtils.getSite(request).getConfig();
        String code = config.getDownloadCode();
        SSoftware software = softwareService.findById(id);
        if (software != null) {
            List<ContentAttachment> list = software.getContent().getAttachments();
            if (list.size() > 0) {
                contentCountMng.downloadCount(software.getContent().getContentId());
                softwareService.addBuyRecord(software, user);
                ContentAttachment ca = list.get(0);
                response.sendRedirect(ca.getAttachmentPath());
                return;
            } else {
                log.info("download index is out of range: {}", 0);
            }
        } else {
            log.info("Content not found: {}", id);
        }
        ResponseUtils.renderText(response, "downlaod error!");
    }

    /**
     * @desc 2017/2/19 17:12
     **/
    @RequestMapping("/member/software_buy_list.jspx")
    public String buyList(HttpServletRequest request, ModelMap model, Integer pageNo,
                          Date startDate, Date endDate, String status, String paramu) {
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
        if (StringUtils.isBlank(status)) {
            status = "1";
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = softwareService.getSoftwareBuyPage(site.getSiteId(), user, cpn(pageNo),
                20, startDate, status, paramu);
        model.addAttribute("pagination", p);
        model.addAttribute("startDate", startDate);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("status", status);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SOFTWARE_BUYLIST);
    }

    /**
     * @Author zhouyq
     * @Date 2017/3/24
     * @Desc 软件卖家订单管理
     */
    @RequestMapping("/member/softwareSellerOrder_list.jspx")
    public String getSoftwareSellerOrder(HttpServletRequest request, ModelMap model, Integer pageNo,
                                         Date startDate, Date endDate, String status, String paramu) {
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
        if (StringUtils.isBlank(status)) {
            status = "1";
        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = softwareService.getSoftwareBuyPage(site.getSiteId(), user, cpn(pageNo),
                20, startDate, status, paramu);
        model.addAttribute("pagination", p);
        model.addAttribute("startDate", startDate);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("status", status);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SOFTWARE_SELLERORDERLIST);
    }

    /**
     * @Author ly
     * @Date 2016/2/19 13:06
     * @Desc 软件购买软件订单列表
     */
    @RequestMapping("/member/software_order_list.jspx")
    public String orderList(HttpServletRequest request, HttpServletResponse response,
                            ModelMap model, Integer pageNo, String statusType, String paymentNo) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //获取分页对象
        Pagination p = orderPaymentService.getOrderPage(site.getSiteId(), user, cpn(pageNo),
                20, statusType, paymentNo);
        model.addAttribute("pagination", p);
        model.addAttribute("statusType", statusType);
        model.addAttribute("paymentNo", paymentNo);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SOFTWARE_ORDERLIST);
    }

    /**
     * @param demandIdsStr 批量操作对象的Id字符串
     * @param nextUrl      操作完成后的返回地址
     * @Author yhr
     * 批量下架
     */
    @RequestMapping(value = "/member/synergy_software_rejectMany.jspx")
    public String rejectMany(String demandIdsStr, String nextUrl, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        String statusType = "3";
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        //业务数据更新处理
        softwareService.rejectMany(demandIdsStr);
        model.addAttribute("statusType", statusType);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * 批量发布
     *
     * @param demandIdsStr 批量操作对象的Id字符串
     * @param nextUrl      操作完成后的返回地址
     * @Author 闫浩芮
     * @Date 2017/2/17 0017 18:44
     */
    @RequestMapping(value = "/member/synergy_software_passMany.jspx")
    public String passMany(String demandIdsStr, String nextUrl, HttpServletRequest request,
                           HttpServletResponse response, ModelMap model) {
        String statusType = "2";
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        //业务数据更新处理
        softwareService.passMany(demandIdsStr);
        model.addAttribute("statusType", statusType);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * 批量删除
     *
     * @param demandIdsStr 批量操作对象的Id字符串
     * @param nextUrl      操作完成后的返回地址
     * @Author 闫浩芮
     * @Date 2017/2/20 0020 10:39
     */
    @RequestMapping(value = "/member/synergy_software_deleteMany.jspx")
    public String deleteMany(String demandIdsStr, String nextUrl, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        String statusType = "2";
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        //业务数据更新处理
        softwareService.deleteMany(demandIdsStr);
        model.addAttribute("statusType", statusType);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author ldong
     * @Date 2017/2/20 15:43
     * @return 软件购买下单界面
     */
    public static final String SOFTWARE_ORDER = "tpl.software_order";

    @RequestMapping("/member/software_order.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      Integer id,
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
        SSoftware software = softwareService.findById(id);
        CmsUser cmsUser = software.getContent().getUser();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("cmsUser", user);
        //这里的user是发布资源的公司用户
        model.addAttribute("user", cmsUser);
        model.addAttribute("channel", channelMng.findById(119));
        model.addAttribute("software", software);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SOFTWARE_ORDER);
    }

    /*
      * @author ldong
      * @Date 2017/2/20 21:05
      * @return  确认下单功能
      */
    @RequestMapping(value = "/member/software_orderdo.jspx", method = RequestMethod.POST)
    public String orderdo(HttpServletRequest request, HttpServletResponse response,
                          ModelMap model, int softwareid) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SSoftware software = softwareService.findById(softwareid);
        SSoftwareBuy buy = new SSoftwareBuy();
        buy.setUserId(user.getUserId());
        buy.setUserName(user.getRealname());
        //buy.setBuyDt(new Date(System.currentTimeMillis()));
        buy.setUserName(user.getUserExt().getRealname());
        buy.setSoftwareId(software.getSoftwareId());
        buy.setSoftwareName(software.getSoftwareName());
        buy.setCreaterId(user.getUserId());
        buy.setCreateDt(new Date(System.currentTimeMillis()));
        buy.setAmount(software.getPrice());
        buy.setBuyPrice(software.getPrice());
        buy.setIsOnline(software.getIsOnline());
        //buy.setPayerID(payerID);
        //buy.setpayerName;
        //订单号
        String guId = null;
        try {
            guId = GUIDGenerator.genGUID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        buy.setOrderNo(guId);
        buy.setStatus("1");
        softwareService.saveOrder(buy);
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SOFTWARE_PAY);*/
        //String nextUrl = "/yzyjyzxYzyck/index.jhtml";
        String nextUrl = "/rjyyRjjsym/index.jhtml";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author ldong
     * @Date 2017/2/20 21:05
     * @return 点击付款 进入支付界面
     */

    public static final String SOFTWARE_PAY = "tpl.software_pay";

    @RequestMapping(value = "/member/software_pay.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response,
                      ModelMap model, int orderId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }

        SSoftwareBuy buy = softwareService.findOrderById(orderId);
        SSoftware software = softwareService.findById(buy.getSoftwareId());
        String s = buy.getStatus();
        if (!s.equals("1")) {
            return FrontUtils.showBaseMessage(request,
                    model, "请确认订单是否已经付款", "/member/software_buy_list.jspx?statusType=1");
        }
        String orderNo = buy.getOrderNo();
        //支付流水号
        String payGuid = null;
        try {
            payGuid = GUIDGenerator.genGUID();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String contextpath;
        contextpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
/*
        model.addAttribute("count",count);
        model.addAttribute("managerId",managerId);*/
        model.addAttribute("user", user);
        model.addAttribute("orderId", orderId);
        model.addAttribute("guId", orderNo);
        model.addAttribute("payGuid", payGuid);
        model.addAttribute("site", site);
        model.addAttribute("url", contextpath);
        model.addAttribute("software", software);
        model.addAttribute("buy", buy);
      /*  model.addAttribute("softwareid",softwareid);*/
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SOFTWARE_PAY);
    }

    /**
     * @author ldong
     * @Date 2017/2/20 21:05
     * @return 前往付款处理  直接向中金发送信息
     */
    public static final String SOFTWARE_BUY = "tpl.software_buy";

    @RequestMapping(value = "/member/software_buy.jspx", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String institutionID, String paymentNo, String orderNo, double amount, double fee, String payerID,
                       String payerName, String usage, String remark, String notificationURL, String payees,
                       String bankID,String bankName, Integer accountType, String cardType, Integer managerId, String label, String count, int orderId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        institutionID = "002818";
        SSoftwareBuy software = softwareService.findOrderById(orderId);
        amount = software.getBuyPrice();
        Map attributes = softwareService.pay(orderId, user, institutionID, paymentNo, orderNo, amount, fee, payerID, payerName, usage, remark, notificationURL, payees, bankID,bankName, accountType, cardType);
        model.addAttribute("message", attributes.get("message"));
        model.addAttribute("signature", attributes.get("signature"));
        model.addAttribute("txCode", attributes.get("txCode"));
        model.addAttribute("txName", attributes.get("txName"));
        model.addAttribute("Flag", attributes.get("flag"));
        model.addAttribute("action", PaymentEnvironment.paymentURL);
        model.addAttribute("institutionID", institutionID);//机构号码
        model.addAttribute("managerId", managerId);
        model.addAttribute("orderNo", orderNo);//订单号
        model.addAttribute("paymentNo", paymentNo);//支付流水号
        model.addAttribute("amount", amount);//付款金额
        model.addAttribute("fee", fee);//支付服务手续费
        model.addAttribute("payerID", payerID);//付款者ID
        model.addAttribute("payerName", payerName);
        model.addAttribute("label", label);//资金用途
        model.addAttribute("remark", remark);//订单描述
        model.addAttribute("payees", payees);//收款人
        model.addAttribute("bankID", bankID);//银行ID
        model.addAttribute("bankName", bankName);//银行
        model.addAttribute("accountType", accountType);//账号类型
        model.addAttribute("cardType", cardType);//卡类型
        model.addAttribute("software", software);
        model.addAttribute("price", fee + amount);

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SOFTWARE_BUY);


    }

    /**
     * @author ldong
     * @Date 2017/2/20 22:37
     * @return 接受信息
     */
    public static final String SOFTWARE_PAYRESULT = "tpl.software_payResult";

    @RequestMapping(value = "/software_receive_notice.jspx", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String message, String signature) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        System.out.println(message);
        message = message.replace(" ", "+");
        softwareService.receiveNotice(message, signature);
        //return  FrontUtils.showBaseMessage(request,model,"nihao","rjyyRjjsym/index.jhtml");
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SOFTWARE_PAYRESULT);
    }

    public static final String SETTLEMENT = "tpl.settlement";

    /**
     * @Author ldong
     * @Date 2017/2/15 14:06
     * @Desc 中金支付   结算接口
     * flag 结算或者退款标置位 1 为结算  0为退款
     */
    @RequestMapping(value = "/member/settlement.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model, int id,
                      String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SSoftwareBuy spo = softwareService.findOrderById(id);
        String OrderNo = spo.getOrderNo(); //订单号（数据库取值）
        double amount = spo.getAmount();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        //int AccountType = 20;  //交易类型默认支付平台账户20
        String action = null;
        if (flag.equals("1")) {
            action = "software_settle.jspx";
        } else if (flag.equals("0")) {
            action = "software_save_apply_refund.jspx";
        }
        model.addAttribute("OrderNo", OrderNo);
        model.addAttribute("Amount", amount);
        model.addAttribute("SerialNumber", SerialNumber);
        model.addAttribute("id", id);
        model.addAttribute("actiona", action);
        //model.addAttribute("AccountType",AccountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SETTLEMENT);
    }

    public static final String REFUND = "tpl.refund";

    /**
     * @Author zhouyq
     * @Date 2017/3/24
     * @Desc 中金支付   退款结算接口
     * flag 结算或者退款标置位 1 为结算  0为退款
     */
    @RequestMapping(value = "/refund.jspx")
    public String refund(HttpServletRequest request, HttpServletResponse response, ModelMap model, int id,
                         String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SSoftwareBuy spo = softwareService.findOrderById(id);
        spo.setStatus("31");
        String OrderNo = spo.getOrderNo(); // 订单号（数据库取值）
        double amount = spo.getAmount(); // 分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); // 交易流水号
        //int AccountType = 20; // 交易类型默认支付平台账户20
        model.addAttribute("OrderNo", OrderNo);
        model.addAttribute("Amount", amount);
        model.addAttribute("SerialNumber", SerialNumber);
        model.addAttribute("id", id);
        model.addAttribute("spo", spo);
        //model.addAttribute("AccountType",AccountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, REFUND);
    }

    /**
     * @param orderId, request, response, model
     * @return
     * @author zhouyq
     * @Date 2017/03/24
     * @Desc 根据id修改软件订单状态（拒绝）
     */
    @RequestMapping("/member/orderMdyState.jspx")
    public String modifyDemandStatById(int orderId, String status, String nextUrl,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model, String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        if (backReason != null) {
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
//        orderId = java.net.URLDecoder.decode(orderId, "utf-8");
        softwareService.mdyOrderStateById(orderId, status, backReason);
        return FrontUtils.showSuccess(request, model, nextUrl + "?paramu=a");
    }

    /**
     * @author ldong
     * @Date 2017/2/20 22:37
     * @return 接收信息
     */
    public static final String SOFTWARE_SETTLE = "tpl.software_SETTLE";

    @RequestMapping(value = "/member/software_settle.jspx")
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
                         String City, int id, String flag/*(设置标志位确定是结算还是退款 0 退款  1结算)*/) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        if (("0").equals(flag)) {
            // 以下信息从数据库取值
            SSoftwareBuy softWareBuy = softwareService.findOrderById(id);
            PaySettlementRefund paySettlementRefund = softWareBuy.getPaySettlementRefund();
            SerialNumber = paySettlementRefund.getSerialNumber(); // 流水号
            OrderNo = paySettlementRefund.getOrderNo(); // 订单号
            remark = softWareBuy.getRemark(); // 备注
            AccountType = paySettlementRefund.getAccountType(); // 账号类型
            BankID = paySettlementRefund.getBankId(); // 银行账号
            bankName = paySettlementRefund.getBankName(); // 银行账号
            AccountName = paySettlementRefund.getAcountName(); // 账户名称
            AccountNumber = paySettlementRefund.getAcountNumber(); // 账户号码
            BranchName = paySettlementRefund.getBranchName(); // 网点名称
            Province = paySettlementRefund.getProvince(); // 省市
            City = paySettlementRefund.getCity(); // 城市
        }

        String result = softwareService.settle(SerialNumber, OrderNo, remark,
                123L,
                AccountType,
                BankID,
                bankName,
                AccountName,
                AccountNumber,
                BranchName,
                Province,
                City, id, flag);
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, NEXTURL);*/
//        return FrontUtils.showSuccess(request,
//                model, "/member/index.jspx");
        if (result.equals("success")) {
            return FrontUtils.showSuccess(request,
                    model, "/member/softwareSellerOrder_list.jspx?paramu=a&status=3");
        } else {
            return FrontUtils.showBaseMessage(request,
                    model, result, "/member/softwareSellerOrder_list.jspx?paramu=a&status=3");
        }
    }

    @RequestMapping(value = "/member/software_save_apply_refund.jspx")
    public String applyRefund(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                              String SerialNumber,
                              String OrderNo,
                              String remark,/*暂时无用 可以用作退款原因*/
                              String Amount,
                              int AccountType,
                              String BankID,
                              String bankName,
                              String AccountName,
                              String AccountNumber,
                              String BranchName,
                              String Province,
                              String City, int orderId, String flag/*(设置标志位确定是结算还是退款)*/) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SSoftwareBuy sorder = softwareService.findOrderById(orderId);
        PaySettlementRefund po = sorder.getPaySettlementRefund();
        if (po == null) {
            po = new PaySettlementRefund();
            String u = UUID.randomUUID().toString().replaceAll("-", "");
            po.setId(u);
        }
        po.setAmount(sorder.getAmount());
        po.setSerialNumber(SerialNumber);
        po.setOrderNo(OrderNo);
        po.setAccountType(AccountType);
        po.setAcountName(AccountName);
        po.setAcountNumber(AccountNumber);
        po.setBranchName(BranchName);
        po.setBankId(BankID);
        po.setBankName(bankName);
        po.setProvince(Province);
        po.setCity(City);
        sorder.setPaySettlementRefund(po);
        sorder.setStatus("30");
        softwareService.saveOrder(sorder);

        return FrontUtils.showSuccess(request,
                model, "/member/software_buy_list.jspx?paramu=u&status=2");
    }

    /**
     * @return 改变订单状态
     * @author ldong
     * @Date 2017/3/24 16:20
     */

    @RequestMapping(value = "/member/software_editStatus.jspx"/*, method = RequestMethod.POST*/)
    public String editOrderStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model, int orderId, String setstatus, String tabState, String onlinePay
    ) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, NEXTURL);*/
        softwareService.editOrderStatus(orderId, setstatus, onlinePay);
        String nextUrl = "/member/software_buy_list.jspx?paramu=u&status=" + tabState;
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
    *@Author 潘晓东
    *@Date 2017/3/27 10:18
    *@Return软件订单明细
    */
    @RequestMapping(value = "/member/software_order_detail.jspx")
    public String software_order_detail(HttpServletRequest request, HttpServletResponse response, ModelMap model, int orderId
    ) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        SSoftwareBuy buy = softwareService.findOrderById(orderId);//获取软件订单表信息
        int wid = buy.getSoftwareId();//根据ID获取软件表id
        SSoftware software = softwareService.findById(wid);//获取软件信息
//        return FrontUtils.showSuccess(request, model, softwareDetail);
        model.addAttribute("buy", buy);
        model.addAttribute("software", software);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, softwareDetail);
    }


    @Resource
    public ReceiveNoticeService ReceiveNoticeservice;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private SoftwareService softwareService;
    @Resource
    protected ContentMng contentMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private ContentCountMng contentCountMng;
    @Resource
    private CloudCenterPaymentService orderPaymentService;

}
