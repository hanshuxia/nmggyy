package com.anchorcms.icloud.controller.payment;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.service.payment.Tx1810Service;
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

import static com.anchorcms.common.constants.Constants.TPLDIR_PAYMENT;

/**
 * Created by ly on 2017/2/15.
 */
@Controller
public class Tx1810Controller {
    private static final Logger log = LoggerFactory.getLogger(Tx1810Controller.class);
    public static final String DOWNLOAD_STATEMENT = "tpl.paymentDownloadStatement";

    /**
     * @Author ly
     * @Date 2017/2/15 14:06
     * @Desc 中金支付
     */
    @RequestMapping(value = "/download_statement.jspx", method = RequestMethod.GET)
    public String add(HttpServletRequest request, HttpServletResponse response,
                      ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        model.addAttribute("site", site);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, DOWNLOAD_STATEMENT);
    }

    /**
     * @Author ly
     * @Date 2017/2/15 15:14
     * @Desc 中金支付
     */
    @RequestMapping(value = "/download_statement.jspx", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String institutionID, Date checkDate){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        String code = service.checkCode(institutionID, checkDate);
        if ("2000".equals(code)) {
            String message = "操作成功";
            String nextUrl = "/download_statement.jspx";
            return FrontUtils.showBaseMessage(request, model, message, nextUrl);
        }
        return null;
    }

    @Resource
    private Tx1810Service service;
}
