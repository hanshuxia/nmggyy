package com.anchorcms.core.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/6.
 */
@Entity
@Table(name = "c_account_pay")
public class CmsAccountPay implements Serializable {
    private Long accountPayId;
    private String drawNum;
    private String payAccount;
    private String drawAccount;
    private Timestamp payTime;
    private String weixinNum;
    private String alipayNum;

    @Id
    @Column(name = "account_pay_id", nullable = false)
    public Long getAccountPayId() {
        return accountPayId;
    }

    public void setAccountPayId(Long accountPayId) {
        this.accountPayId = accountPayId;
    }

    @Basic
    @Column(name = "draw_num", nullable = false, length = 50)
    public String getDrawNum() {
        return drawNum;
    }

    public void setDrawNum(String drawNum) {
        this.drawNum = drawNum;
    }


    @Basic
    @Column(name = "pay_account", nullable = false, length = 100)
    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    @Basic
    @Column(name = "draw_account", nullable = false, length = 100)
    public String getDrawAccount() {
        return drawAccount;
    }

    public void setDrawAccount(String drawAccount) {
        this.drawAccount = drawAccount;
    }

    @Basic
    @Column(name = "pay_time", nullable = false)
    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    @Basic
    @Column(name = "weixin_num", nullable = true, length = 100)
    public String getWeixinNum() {
        return weixinNum;
    }

    public void setWeixinNum(String weixinNum) {
        this.weixinNum = weixinNum;
    }

    @Basic
    @Column(name = "alipay_num", nullable = true, length = 100)
    public String getAlipayNum() {
        return alipayNum;
    }

    public void setAlipayNum(String alipayNum) {
        this.alipayNum = alipayNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsAccountPay that = (CmsAccountPay) o;

        if (accountPayId != null ? !accountPayId.equals(that.accountPayId) : that.accountPayId != null) return false;
        if (drawNum != null ? !drawNum.equals(that.drawNum) : that.drawNum != null) return false;
        if (payAccount != null ? !payAccount.equals(that.payAccount) : that.payAccount != null) return false;
        if (drawAccount != null ? !drawAccount.equals(that.drawAccount) : that.drawAccount != null) return false;
        if (payTime != null ? !payTime.equals(that.payTime) : that.payTime != null) return false;
        if (weixinNum != null ? !weixinNum.equals(that.weixinNum) : that.weixinNum != null) return false;
        if (alipayNum != null ? !alipayNum.equals(that.alipayNum) : that.alipayNum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountPayId != null ? accountPayId.hashCode() : 0;
        result = 31 * result + (drawNum != null ? drawNum.hashCode() : 0);
        result = 31 * result + (payAccount != null ? payAccount.hashCode() : 0);
        result = 31 * result + (drawAccount != null ? drawAccount.hashCode() : 0);
        result = 31 * result + (payTime != null ? payTime.hashCode() : 0);
        result = 31 * result + (weixinNum != null ? weixinNum.hashCode() : 0);
        result = 31 * result + (alipayNum != null ? alipayNum.hashCode() : 0);
        return result;
    }

    private CmsUser cuser;
    @ManyToOne
    @JoinColumn(name = "draw_user_id")
    public CmsUser getDrawUser() {
        return cuser;
    }

    public void setDrawUser(CmsUser cuser) {
        this.cuser = cuser;
    }

    @ManyToOne
    @JoinColumn(name = "pay_user_id")
    public CmsUser getPayUser() {
        return cuser;
    }

    public void setPayUser(CmsUser cuser) {
        this.cuser = cuser;
    }
}