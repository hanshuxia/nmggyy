package com.anchorcms.icloud.controller.payment;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPaymentService;
import com.anchorcms.icloud.service.payment.ReceiveNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.anchorcms.common.constants.Constants.TPLDIR_PAYMENT;

/**
 * Created by ly on 2017/2/15.
 */
@Controller
public class ReceiveNoticePageController {
    private static final Logger log = LoggerFactory.getLogger(ReceiveNoticePageController.class);
    public static final String PAYSUCCESS = "tpl.paySuccess";

    /**
     * @Author ly
     * @Date 2017/2/15 15:14
     * @Desc 中金支付接收信息
     */
    @RequestMapping(value = "/cloud_receive_notice.jspx", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             String message, String signature){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        System.out.println(message);
        message = message.replace(" ","+");
        Map resultMessage = service.receiveNotice(message, signature);
       String  PaymentNo = (String) resultMessage.get("PaymentNo");
        if("".equals(PaymentNo)){
            return null;
        }
        //做云资源状态改变的功能
        paymentService.getUpdateByPaymentNo(PaymentNo);

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, PAYSUCCESS);



    }


    @Resource
    private ReceiveNoticeService service;
    @Resource
    private CloudCenterPaymentService paymentService;
}
