package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.service.commservice.SAmplePolicyService;
import com.anchorcms.icloud.service.commservice.SBidNoticeService;
import com.anchorcms.icloud.service.commservice.STenderNoticeService;
import com.anchorcms.icloud.service.commservice.STenderTrailerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.anchorcms.common.constants.Constants.TPLDIR_COMMSERVICE;

/**
 * Created by hansx on 2017/1/13 0013.
 * <p>
 * 招标预告/招标公告/中标公告 详情页
 */

@Controller
public class STenderShowDetailController {
    private static final Logger log = LoggerFactory.getLogger(STenderShowDetailController.class);

    public static final String TPL_STENDERTRAILER_DETAIL = "tpl.sTenderTrailerDetail";
    public static final String TPL_STENDERNOTICE_DETAIL = "tpl.sTenderNoticeDetail";
    public static final String TPL_SBIDNOTICE_DETAIL = "tpl.sBidNoticeDetail";
    public static final String TPL_SAMPLEPOLICY_DETAIL = "tpl.sAmplePolicyDetail";

    /**
     * @return 根据id获取招标预告信息
     * @author hansx
     * @Date 2017/1/13 0013 上午 11:19
     */
    @RequestMapping(value = "/commservice/sTenderTrailerDetail.jspx")
    public String findTenderTrailerById(int id, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        model.addAttribute("channel",channelMng.findById(101));
        if (id != 0) {
            STenderTrailer sTenderTrailer = sTenderTrailerService.findById(id);
            model.addAttribute("obj", sTenderTrailer);
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, TPL_STENDERTRAILER_DETAIL);
    }

    /**
     * @return 根据id获取招标公告信息
     * @author hansx
     * @Date 2017/1/13 0013 上午 11:19
     */
    @RequestMapping(value = "/commservice/sTenderNoticeDetail.jspx")
    public String findTenderNoticeById(int id, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        model.addAttribute("channel",channelMng.findById(101));
        if (id != 0) {
            STenderNotice sTenderNotice = sTenderNoticeService.findById(id);
            model.addAttribute("obj", sTenderNotice);
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, TPL_STENDERNOTICE_DETAIL);
    }

    /**
     * @return 根据id获取中标公告信息
     * @author hansx
     * @Date 2017/1/13 0013 上午 11:19
     */
    @RequestMapping(value = "/commservice/sBidNoticeDetail.jspx")
    public String findBidNoticeById(int id, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        model.addAttribute("channel",channelMng.findById(101));
        if (id != 0) {
            SBidNotice sBidNotice = sBidNoticeService.findById(id);
            model.addAttribute("obj", sBidNotice);
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, TPL_SBIDNOTICE_DETAIL);
    }

    /**
     * @return 根据id获取惠补政策明细
     * @author hansx
     * @Date 2017/1/13 0013 上午 11:19
     */
    @RequestMapping(value = "/commservice/sAmplePolicyDetail.jspx")
    public String findSAmplePolicyById(int id, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        model.addAttribute("channel",channelMng.findById(101));
        if (id != 0) {
            SAmplePolicy sAmplePolicy = sAmplePolicyService.findById(id);
            model.addAttribute("obj", sAmplePolicy);
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, TPL_SAMPLEPOLICY_DETAIL);
    }

    @Resource
    private STenderTrailerService sTenderTrailerService;
    @Resource
    private STenderNoticeService sTenderNoticeService;
    @Resource
    private SBidNoticeService sBidNoticeService;
    @Resource
    private SAmplePolicyService sAmplePolicyService;
    @Resource
    private ChannelMng channelMng;

}
