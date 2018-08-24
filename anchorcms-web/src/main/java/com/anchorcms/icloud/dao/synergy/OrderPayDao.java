package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.icloud.model.payment.*;
import com.anchorcms.icloud.model.synergy.SBigdataDemandQuote;

public interface OrderPayDao {

    public SPOrder saveOrder(SPOrder bean);


 public SSupplychainOrder getSupplychainOrderById(String id);
 public SSupplychainOrder updateSupplychainOrder(SSupplychainOrder bean);

    public SPOrder updateOrder(SPOrder bean);
    public SBigdataDemandQuote updateOrder2(SBigdataDemandQuote bean);

    public SPOrder getOrderById(String id);
    public SPOrderObj getOrderObjById(String id);


    public SPPay getBypaymentNo(String paymentNo);

    public SPPay savePay(SPPay bean);

    SPAdminSettle findAdminSettleById(int id);

    SPAdminSettle updateAdminSettle(SPAdminSettle sadmin);

    SPAdminSettle deleteAdminSettle(SPAdminSettle sadmin);

    SPAdminSettle saveAdminSet(SPAdminSettle ase);
}
