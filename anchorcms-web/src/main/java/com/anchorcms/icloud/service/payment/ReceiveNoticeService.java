package com.anchorcms.icloud.service.payment;

import java.util.Map;

/**
 * Created by ly on 2017/2/17.
 */
public interface ReceiveNoticeService {
    public Map receiveNotice(String message, String signature);
}
