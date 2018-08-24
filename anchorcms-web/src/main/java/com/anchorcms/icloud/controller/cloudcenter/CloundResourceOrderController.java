package com.anchorcms.icloud.controller.cloudcenter;

import cfca.seal.util.StringUtil;
import cfca.seal.ws.SealClient;
import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.upload.FileRepository;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FileUtil;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.utils.UploadUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.service.CmsUserMng;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.model.common.SCfcaApply;
import com.anchorcms.icloud.model.common.SOrderPayment;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPaymentService;
import com.anchorcms.icloud.service.cloudcenter.CloudMangerService;
import com.anchorcms.icloud.service.cloudcenter.UserService;
import com.anchorcms.icloud.service.common.CfcaService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.constants.Constants.TPLDIR_PAYMENT;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @anther ly
 * @descript
 * @data 2017/1/511:10
 */
@Controller
public class CloundResourceOrderController {

    private static final Logger log = LoggerFactory.getLogger(CloudCenterCreateContriller.class);
    public static final String ORDER_LIST = "tpl.cloud_resource_order_list";
    public static final String ORDER_LIST_VIEW = "tpl.cloud_resource_view";
    public static final String ORDER_LIST_PAY = "tpl.cloud_resource_pay";
    public static final String ORDER_MANAGE_LIST = "tpl.cloud_resource_order_manage_list";
    public static final String ORDER_CONTRACT_VIEW = "tpl.editOrderView";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    /**
     * @Author ly
     * @Date 2016/2/19 13:06
     * @Desc 云资源订单列表
     */
    @RequestMapping("/member/cloud_resource_order_list.jspx")
    public String orderList(HttpServletRequest request, HttpServletResponse response,
                            ModelMap model, Integer pageNo, String statusType, String orderNo) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        //获取分页对象
        Pagination p = service.getOrderPage(site.getSiteId(), user, cpn(pageNo),
                20, statusType, orderNo);
        model.addAttribute("pagination", p);
        model.addAttribute("statusType", statusType);
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, ORDER_LIST);

    }

    /**
     * @Author llm
     * @Date 2016/2/19 13:06
     * @Desc 订单详细信息查看
     */
    @RequestMapping("/member/managerPay_view.jspx")
    public String payView(HttpServletRequest request, HttpServletResponse response,
                          ModelMap model, String paymentNo) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SOrderPayment payment = service.getByOrderNo(paymentNo);
        SIcloudManage manager = mangerService.getMangerById(payment.getManage().getManagerId());

        model.addAttribute("payment", payment);
        model.addAttribute("manager", manager);
        model.addAttribute("user", user);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, ORDER_LIST_VIEW);
    }

    /**
     * @Author llm
     * @Date 2016/2/19 13:06
     * @Desc 订单付款
     */
    @RequestMapping("/member/managerPay_creat.jspx")
    public String payCreat(HttpServletRequest request, HttpServletResponse response,
                           ModelMap model, String paymentNo) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SOrderPayment payment = service.getByOrderNo(paymentNo);
        SIcloudManage manager = mangerService.getMangerById(payment.getManage().getManagerId());

        String contextpath;
        contextpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        String payGuid = GUIDGenerator.genGUID();
        model.addAttribute("url", contextpath);
        model.addAttribute("payGuid", payGuid);
        model.addAttribute("payment", payment);
        model.addAttribute("manager", manager);
        model.addAttribute("user", user);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, ORDER_LIST_PAY);
    }

    /**
     * @Author llm
     * @Date 2016/2/21 9:22
     * @Desc 订单撤销
     */
    @RequestMapping("/member/managerPay_reback.jspx")
    public String reBackOrder(HttpServletRequest request, HttpServletResponse response,
                              ModelMap model, String orderNo, Integer managerId) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        String nextUrl = "/member/cloud_resource_order_list.jspx";
        SOrderPayment payment = service.getByOrderNo(orderNo);
        service.deleteByOrderId(payment);
        SIcloudManage manager = mangerService.getMangerById(payment.getManage().getManagerId());
        manager.setState("1");
        mangerService.updateManger(manager);
        String payGuid = GUIDGenerator.genGUID();
        model.addAttribute("payGuid", payGuid);
        model.addAttribute("payment", payment);
        model.addAttribute("manager", manager);
        model.addAttribute("user", user);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    /**
     * @Author ly
     * @Date 2016/2/19 13:06
     * @Desc 云资源订单管理列表
     */
    @RequestMapping("/member/resource_order_manage_list.jspx")
    public String orderManageList(HttpServletRequest request, HttpServletResponse response,
                                  ModelMap model, Integer pageNo, String statusType, String orderNo) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //获取分页对象
        Pagination p = service.getOrderManagePage(site.getSiteId(), user, cpn(pageNo),
                20, statusType, orderNo);
        model.addAttribute("pagination", p);
        model.addAttribute("statusType", statusType);
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, ORDER_MANAGE_LIST);

    }

    /**
     * @Author hansx
     * @Date 2017/4/15 13:06
     * @Desc 云资源订单修改状态
     */
    @RequestMapping("/member/update_order_status.jspx")
    public String updateOrderStatus(HttpServletRequest request,
                                    ModelMap model, String status, String orderNo) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        String nextUrl;
        SCfcaApply sCfcaApply;
        SOrderPayment orderPayment = service.getByOrderNo(orderNo);
        if (status != null) {
            if ("6".equals(status)) {//选择签章合同时
                sCfcaApply = cfcaService.getSignFlag(user.getCompany().getCompanyId());//判定是否开通签章功能
                if (sCfcaApply == null) {//未开通签章功能
                    nextUrl = "/member/cloud_resource_order_list.jspx?statusType=1";
                    String message = "您的企业尚未开通电子签章功能，开通后才能使用签章！";
//                    return FrontUtils.showMessage(request, model, message);
                    return FrontUtils.showBaseMessage(request, model, message, nextUrl);
                }
            }
            orderPayment.setState(Integer.parseInt(status));
            orderPayment.setUpdateDT(new Timestamp(System.currentTimeMillis()));
            if ("3".equals(status)) {
                orderPayment.setIsOnline("1");
            }
            service.update(orderPayment);
        }
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());

        nextUrl = "/member/cloud_resource_order_list.jspx?statusType=1";
        if ("1".equals(status)) {
            nextUrl = "/member/resource_order_manage_list.jspx?statusType="+status;
        } else if ("4".equals(status)) {
            nextUrl = "/member/cloud_resource_order_list.jspx?statusType="+status;
        } else if ("2".equals(status) || "6".equals(status)) {
            nextUrl = "/member/cloud_resource_order_list.jspx?statusType="+status;
        }else if ("3".equals(status)) {
            nextUrl = "/member/cloud_resource_order_list.jspx?statusType="+status;
        }
        return FrontUtils.showSuccess(request, model, nextUrl);

    }

    /**
     * @Author hansx
     * @Date 2017/4/15 13:06
     * @desc 需求方签章/服务方签章
     **/
    @RequestMapping("/member/seal_order_protocal.jspx")
    public String sealOrderProtocal(HttpServletRequest request,
                                    ModelMap model, String status, String orderNo, String htmls) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        String nextUrl = null;
        SOrderPayment orderPayment = service.getByOrderNo(orderNo);
        SCfcaApply sCfcaApply = cfcaService.getSignFlag(user.getCompany().getCompanyId());//判定是否开通签章功能
            if (sCfcaApply == null) {//未开通签章功能
                String message = "您的企业尚未开通电子签章功能，开通后才能使用签章！";
                if (status.equals("2")) {//乙方（服务方）签章
                    nextUrl = "/member/resource_order_manage_list.jspx?statusType=7";
                }else if (status.equals("7")){//甲方签章
                    nextUrl = "/member/cloud_resource_order_list.jspx?statusType=6";
                }
                return FrontUtils.showBaseMessage(request, model, message, nextUrl);
            }
        Content content = null;
        if (status.equals("2")) {//乙方（服务方）签章
            content = orderPayment.getContent();
            htmls = (content.getAttachmentPaths())[0];
        }
        String protocalPath = null;
        try {
            /**
             * 签章功能
             */
            protocalPath = seal(user, site, request, htmls, status, sCfcaApply);
        } catch (Exception e) {
            e.printStackTrace();
            nextUrl = "/member/resource_order_manage_list.jspx?statusType=6";
            return FrontUtils.showBaseMessage(request, model, "签章失败", nextUrl);
        }
        if (protocalPath == null) {
            nextUrl = "/member/resource_order_manage_list.jspx?statusType=6";
            return FrontUtils.showBaseMessage(request, model, "签章失败", nextUrl);
        }
        if (status.equals("7")) {//甲方签章，第一次签章时新建内容附件表存放甲方的签章合同
            content = new Content();
            content.setSite(site);
            CmsModel defaultModel = cmsModelMng.getDefModel();
            CmsModel m = cmsModelMng.findById(100);
            if (m != null) {
                content.setModel(m);
            } else {
                content.setModel(defaultModel);
            }
            content.addToAttachmemts(protocalPath,
                    "合同文件.pdf", "sealProtocal.pdf");

            ContentExt ext = new ContentExt();
            ext.setTitle(orderPayment.getOrderNo());
            ext.setAuthor(user.getUsername());
            ext.setDescription("电子签章合同");
            contentMng.save(content, ext, new ContentTxt(), 114, contentTypeMng.getDef().getTypeId(), false, user, true);
            nextUrl = "/member/cloud_resource_order_list.jspx?statusType=7";

        } else if (status.equals("2")) {//乙方签章，更新content表中签章合同,双方都已签章
            content.getAttachments().get(0).setAttachmentPath(protocalPath);
            contentMng.update(content);
            orderPayment.setContent(content);
            nextUrl = "/member/resource_order_manage_list.jspx?statusType=2";
        }
        orderPayment.setContent(content);
        orderPayment.setState(Integer.parseInt(status));
        orderPayment.setUpdateDT(new Timestamp(System.currentTimeMillis()));
        service.update(orderPayment);

        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);

    }

    /***
     * @param model
     * @param orderNo 订单号
     * @return
     * @desc 订单管理--合同信息明细查看
     * @author wanjinyou
     */
    @RequestMapping(value = "/member/orderManage.jspx")
    public String editOrderView(String orderNo, ModelMap model, HttpServletRequest request) {
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
        SOrderPayment orderPayment = service.getByOrderNo(orderNo);
        //根据userID 获取需求用户的名称用户名
        CmsUser demandUser = userService.getByYserId(orderPayment.getManage().getCreaterId()); //供方(实质为管理员)
        CmsUser quoteUser = userService.getByYserId(orderPayment.getPayUser().getUserId().toString()); //买方
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("demandUser", demandUser); //供方
        model.addAttribute("quoteUser", quoteUser); //买方
        model.addAttribute("orderPayment", orderPayment);
        if (orderPayment.getAmount() != null && !orderPayment.getAmount().equals("")) {
            model.addAttribute("amountLarge", Tool.change(orderPayment.getAmount()));
        }
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, ORDER_CONTRACT_VIEW);
    }

    /**
     * @Author hansx
     * @Date 2017/4/15 13:06
     * @desc 签章接口
     * 需求方/服务方公用此方法签章
     **/
    public String seal(CmsUser user, CmsSite site, HttpServletRequest request, String htmls, String status, SCfcaApply sCfcaApply) throws Exception {

        /** HTTP方式 */
        String host = "http://222.74.21.26:26002/Seal";//签章服务地址
        SealClient client = new SealClient(host);
        String type = "2";//签章类型（不能为空），1=空白标签签章,2=坐标签章,3=关键字签章
        String pdfURL = "";//Pdf的URL地址，如果pdf为空时使用
        String sealCode = sCfcaApply.getSignNo();//印章编码
        String sealPassword =sCfcaApply.getSignPwd();//印章密码
        String sealPerson = user.getUsername();//签章人
        String sealLocation = user.getCompany().getAddrProvince()+user.getCompany().getAddrCity();//签章地点
        String sealResson = "";//签章理由
        String businessCode = "";//业务码：可以是验证码、查询码
        String blankFieldName = "";//空白标签字段名，为空时代表全部
        String page = "1";// 支持（1）单页，（1,2,3,4）多页,（1-3）区间，（0）所有页
        String lX = "140";//左侧的x坐标
        String lY = "700";//左侧的y坐标
        String keyword = "";//关键字，按关键字签章时不能为空
        String locationStyle = "L";// 位置风格：上:U；下:D；左:L；右:R；中:C；默认：C
        String offsetX = "0";//横轴偏移，默认为0
        String offsetY = "0";//纵轴偏移，默认为0
        byte[] pdf = null;
        if (status.equals("7")) {//乙方
            sealResson = "需求方签章";
            byte[] bs = htmls.getBytes("utf-8");//文件流默认编码GBK，需要转换成utf-8
            String fileType = "html";// html word
            pdf = client.transformToPdf(bs, fileType);
            lX = "380";//左侧的x坐标
            lY = "700";
        } else if (status.equals("2")) {//甲方
            sealResson = "服务方签章";
            pdf = FileUtil.getBytesFromFile(new File(fileRepository.getRealPath(htmls)));
        }
        String sealStrategyXML = "<Request>" + "<Type>" + type + "</Type>" + "<PdfURL>" + pdfURL + "</PdfURL>"
                + "<SealCode>" + sealCode + "</SealCode><SealPassword>" + sealPassword + "</SealPassword><Page>"
                + page + "</Page><SealPerson>" + sealPerson + "</SealPerson><SealLocation>" + sealLocation
                + "</SealLocation>" + "<SealResson>" + sealResson + "</SealResson><BusinessCode>" + businessCode
                + "</BusinessCode><BlankFieldName>" + blankFieldName + "</BlankFieldName><LX>" + lX + "</LX><LY>"
                + lY + "</LY><Keyword>" + keyword + "</Keyword>" + "<LocationStyle>" + locationStyle
                + "</LocationStyle><OffsetX>" + offsetX + "</OffsetX><OffsetY>" + offsetY + "</OffsetY>"
                + "</Request>";

        byte[] resultData = client.sealAutoPdf(pdf, sealStrategyXML);
        if (StringUtil.checkResultDataValid(resultData)) {//签章成功
            String origName = "pdf";
            String filename = UploadUtils.generateFilename(site.getUploadPath(), origName);
            String filenames = filename.substring(0,filename.lastIndexOf("/"));
            File dest = new File(fileRepository.getRealPath(filenames));
            if (!dest.isDirectory())
            { //目录不存在
                dest.mkdir(); //创建目录
            }
            FileUtil.wirteDataToFile(fileRepository.getRealPath(filename), resultData);//签章后内容写入文件
            String ctx = request.getContextPath();
            String savePath = ctx + filename;//存放路径
            //上传签章合同
            cmsUserMng.updateUploadSize(user.getUserId(), Integer.parseInt(String.valueOf(resultData.length / 1024)));
            fileMng.saveFileByPath(savePath, origName, false);
            System.out.println("SealAutoPdfTest ok and save data to \"" + savePath + "\'");
            return savePath;
        } else {
            System.out.println("SealAutoPdfTest wrong:" + new String(resultData));
            return null;
        }
    }
//    public CloundResourceOrderController() throws IOException {
//        InputStream in = FileRepository.class.getClassLoader().getResourceAsStream(UPLOAD_FILENAME);
//        Properties prop = new Properties();
//        prop.load(in);
//        uploadPath = prop.getProperty("upload.path");
//    }
//    private void store(File file, File dest) throws IOException {
//        try {
//            UploadUtils.checkDirAndCreate(dest.getParentFile());
//            FileUtils.copyFile(file, dest);
//        } catch (IOException e) {
//            log.error("Transfer file error when upload file", e);
//            throw e;
//        }
//    }
//    //修改上传路径 02/24
//    private static final String UPLOAD_FILENAME = "upload.properties";
//    private static String uploadPath;
//    public String getRealPath(String name) {
//        return uploadPath + name;
//    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private CloudCenterPaymentService service;
    @Resource
    private CloudMangerService mangerService;
    @Resource
    private UserService userService;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentDao contentDao;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private ContentCountMng contentCountMng;
    @Resource
    private CfcaService cfcaService;

    @Resource
    private CmsUserMng cmsUserMng;
    @Resource
    private CmsFileMng fileMng;
    @Resource
    private FileRepository fileRepository;
    @Resource
    private SynergyCompanyService synergyCompanyService;

    /**
     * @author hansx
     * @Date 2017/3/24 0024 下午 5:30
     * @return 小写金额转大写的工具类
     */
    static class Tool {
        private static final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";
        private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
        private static final double MAX_VALUE = 9999999999999.99D;

        public static String change(double v) {
            if (v < 0 || v > MAX_VALUE) {
                return "参数非法!";
            }
            long l = Math.round(v * 100);
            if (l == 0) {
                return "零元整";
            }
            String strValue = l + "";
            // i用来控制数
            int i = 0;
            // j用来控制单位
            int j = UNIT.length() - strValue.length();
            String rs = "";
            boolean isZero = false;
            for (; i < strValue.length(); i++, j++) {
                char ch = strValue.charAt(i);
                if (ch == '0') {
                    isZero = true;
                    if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
                        rs = rs + UNIT.charAt(j);
                        isZero = false;
                    }
                } else {
                    if (isZero) {
                        rs = rs + "零";
                        isZero = false;
                    }
                    rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
                }
            }
            if (!rs.endsWith("分")) {
                rs = rs + "整";
            }
            rs = rs.replaceAll("亿万", "亿");
            return rs;
        }
    }
}
