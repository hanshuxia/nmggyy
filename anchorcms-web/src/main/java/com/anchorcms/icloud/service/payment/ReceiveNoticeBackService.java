package com.anchorcms.icloud.service.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ly on 2017/2/17.
 */
public interface ReceiveNoticeBackService {
    public void receive(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
