package com.anchorcms.icloud.service.payment;

import java.util.Date;

/**
 * Created by ly on 2017/2/15.
 */

public interface Tx1810Service {
    String checkCode(String institutionID, Date checkDate);
}
