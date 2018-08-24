package com.anchorcms.icloud.controller.payment;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.service.payment.ReceiveNoticeBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ly on 2017/2/15.
 */
@Controller
public class ReceiveNoticeBackController {
    private static final Logger log = LoggerFactory.getLogger(ReceiveNoticeBackController.class);
    public static final String PAYSUCCESS = "tpl.paySuccess";

    /**
     * @Author ly
     * @Date 2017/2/15 15:14
     * @Desc 中金支付接收信息
     */
    @RequestMapping(value = "/receive_notice.jspx", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                     String message, String signature) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        try {
            service.receive(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Resource
    ReceiveNoticeBackService service;
}
