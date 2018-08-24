package com.anchorcms.core.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/5.
 */
@Entity
@Table(name = "c_account_draw")
public class CmsAccountDraw implements Serializable {

    public static final byte CHECKING = 0;
    public static final byte CHECKED_SUCC = 1;
    public static final byte CHECKED_FAIL = 2;
    public static final byte DRAW_SUCC = 3;
    private Integer accountDrawId;
    private String applyAccount;
    private Double applyAmount;
    private Byte applyStatus;
    private Timestamp applyTime;

    @Id
    @Column(name = "account_draw_id", nullable = false)
    public Integer getAccountDrawId() {
        return accountDrawId;
    }

    public void setAccountDrawId(Integer accountDrawId) {
        this.accountDrawId = accountDrawId;
    }


    @Basic
    @Column(name = "apply_account", nullable = true, length = 100)
    public String getApplyAccount() {
        return applyAccount;
    }

    public void setApplyAccount(String applyAccount) {
        this.applyAccount = applyAccount;
    }

    @Basic
    @Column(name = "apply_amount", nullable = false, precision = 0)
    public Double getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(Double applyAmount) {
        this.applyAmount = applyAmount;
    }

    @Basic
    @Column(name = "apply_status", nullable = false)
    public Byte getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Byte applyStatus) {
        this.applyStatus = applyStatus;
    }

    @Basic
    @Column(name = "apply_time", nullable = false)
    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsAccountDraw that = (CmsAccountDraw) o;

        if (accountDrawId != null ? !accountDrawId.equals(that.accountDrawId) : that.accountDrawId != null)
            return false;
        if (applyAccount != null ? !applyAccount.equals(that.applyAccount) : that.applyAccount != null) return false;
        if (applyAmount != null ? !applyAmount.equals(that.applyAmount) : that.applyAmount != null) return false;
        if (applyStatus != null ? !applyStatus.equals(that.applyStatus) : that.applyStatus != null) return false;
        if (applyTime != null ? !applyTime.equals(that.applyTime) : that.applyTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountDrawId != null ? accountDrawId.hashCode() : 0;
        result = 31 * result + (applyAccount != null ? applyAccount.hashCode() : 0);
        result = 31 * result + (applyAmount != null ? applyAmount.hashCode() : 0);
        result = 31 * result + (applyStatus != null ? applyStatus.hashCode() : 0);
        result = 31 * result + (applyTime != null ? applyTime.hashCode() : 0);
        return result;
    }
    private CmsUser cuser;
    @ManyToOne
    @JoinColumn(name = "draw_user_id")
    public CmsUser getCmsUser() {
        return cuser;
    }

    public void setCmsUser(CmsUser cuser) {
        this.cuser = cuser;
    }

    private CmsAccountPay accpay;
    @ManyToOne
    @JoinColumn(name = "account_pay_id")
    public CmsAccountPay getAccountPay() {
        return accpay;
    }

    public void setAccountPay(CmsAccountPay accpay) {
        this.accpay = accpay;
    }
}
