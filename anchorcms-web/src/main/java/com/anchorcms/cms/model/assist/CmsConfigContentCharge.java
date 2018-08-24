package com.anchorcms.cms.model.assist;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by 56995 on 2016/12/5.
 */
@Entity
@Table(name = "c_config_content_charge")
public class CmsConfigContentCharge {
    private Integer configContentId;
    private String weixinAppid;
    private String weixinSecret;
    private String weixinAccount;
    private String weixinPassword;
    private String alipayPartnerId;
    private String alipayAccount;
    private String alipayKey;
    private String alipayAppid;
    private String alipayPublicKey;
    private String alipayPrivateKey;
    private Double chargeRatio;
    private Double minDrawAmount;
    private Double commissionTotal;
    private Double commissionYear;
    private Double commissionMonth;
    private Double commissionDay;
    private Date lastBuyTime;
    private String payTransferPassword;
    private String transferApiPassword;
    private Double rewardMin;
    private Double rewardMax;

    @Id
    @Column(name = "config_content_id")
    public Integer getConfigContentId() {
        return configContentId;
    }

    public void setConfigContentId(Integer configContentId) {
        this.configContentId = configContentId;
    }

    @Basic
    @Column(name = "weixin_appid")
    public String getWeixinAppid() {
        return weixinAppid;
    }

    public void setWeixinAppid(String weixinAppid) {
        this.weixinAppid = weixinAppid;
    }

    @Basic
    @Column(name = "weixin_secret")
    public String getWeixinSecret() {
        return weixinSecret;
    }

    public void setWeixinSecret(String weixinSecret) {
        this.weixinSecret = weixinSecret;
    }

    @Basic
    @Column(name = "weixin_account")
    public String getWeixinAccount() {
        return weixinAccount;
    }

    public void setWeixinAccount(String weixinAccount) {
        this.weixinAccount = weixinAccount;
    }

    @Basic
    @Column(name = "weixin_password")
    public String getWeixinPassword() {
        return weixinPassword;
    }

    public void setWeixinPassword(String weixinPassword) {
        this.weixinPassword = weixinPassword;
    }

    @Basic
    @Column(name = "alipay_partner_id")
    public String getAlipayPartnerId() {
        return alipayPartnerId;
    }

    public void setAlipayPartnerId(String alipayPartnerId) {
        this.alipayPartnerId = alipayPartnerId;
    }

    @Basic
    @Column(name = "alipay_account")
    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    @Basic
    @Column(name = "alipay_key")
    public String getAlipayKey() {
        return alipayKey;
    }

    public void setAlipayKey(String alipayKey) {
        this.alipayKey = alipayKey;
    }

    @Basic
    @Column(name = "alipay_appid")
    public String getAlipayAppid() {
        return alipayAppid;
    }

    public void setAlipayAppid(String alipayAppid) {
        this.alipayAppid = alipayAppid;
    }

    @Basic
    @Column(name = "alipay_public_key")
    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    @Basic
    @Column(name = "alipay_private_key")
    public String getAlipayPrivateKey() {
        return alipayPrivateKey;
    }

    public void setAlipayPrivateKey(String alipayPrivateKey) {
        this.alipayPrivateKey = alipayPrivateKey;
    }

    @Basic
    @Column(name = "charge_ratio")
    public Double getChargeRatio() {
        return chargeRatio;
    }

    public void setChargeRatio(Double chargeRatio) {
        this.chargeRatio = chargeRatio;
    }

    @Basic
    @Column(name = "min_draw_amount")
    public Double getMinDrawAmount() {
        return minDrawAmount;
    }

    public void setMinDrawAmount(Double minDrawAmount) {
        this.minDrawAmount = minDrawAmount;
    }

    @Basic
    @Column(name = "commission_total")
    public Double getCommissionTotal() {
        return commissionTotal;
    }

    public void setCommissionTotal(Double commissionTotal) {
        this.commissionTotal = commissionTotal;
    }

    @Basic
    @Column(name = "commission_year")
    public Double getCommissionYear() {
        return commissionYear;
    }

    public void setCommissionYear(Double commissionYear) {
        this.commissionYear = commissionYear;
    }

    @Basic
    @Column(name = "commission_month")
    public Double getCommissionMonth() {
        return commissionMonth;
    }

    public void setCommissionMonth(Double commissionMonth) {
        this.commissionMonth = commissionMonth;
    }

    @Basic
    @Column(name = "commission_day")
    public Double getCommissionDay() {
        return commissionDay;
    }

    public void setCommissionDay(Double commissionDay) {
        this.commissionDay = commissionDay;
    }

    @Basic
    @Column(name = "last_buy_time")
    public Date getLastBuyTime() {
        return lastBuyTime;
    }

    public void setLastBuyTime(Date lastBuyTime) {
        this.lastBuyTime = lastBuyTime;
    }

    @Basic
    @Column(name = "pay_transfer_password")
    public String getPayTransferPassword() {
        return payTransferPassword;
    }

    public void setPayTransferPassword(String payTransferPassword) {
        this.payTransferPassword = payTransferPassword;
    }

    @Basic
    @Column(name = "transfer_api_password")
    public String getTransferApiPassword() {
        return transferApiPassword;
    }

    public void setTransferApiPassword(String transferApiPassword) {
        this.transferApiPassword = transferApiPassword;
    }

    @Basic
    @Column(name = "reward_min")
    public Double getRewardMin() {
        return rewardMin;
    }

    public void setRewardMin(Double rewardMin) {
        this.rewardMin = rewardMin;
    }

    @Basic
    @Column(name = "reward_max")
    public Double getRewardMax() {
        return rewardMax;
    }

    public void setRewardMax(Double rewardMax) {
        this.rewardMax = rewardMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsConfigContentCharge that = (CmsConfigContentCharge) o;

        if (configContentId != null ? !configContentId.equals(that.configContentId) : that.configContentId != null)
            return false;
        if (weixinAppid != null ? !weixinAppid.equals(that.weixinAppid) : that.weixinAppid != null) return false;
        if (weixinSecret != null ? !weixinSecret.equals(that.weixinSecret) : that.weixinSecret != null) return false;
        if (weixinAccount != null ? !weixinAccount.equals(that.weixinAccount) : that.weixinAccount != null)
            return false;
        if (weixinPassword != null ? !weixinPassword.equals(that.weixinPassword) : that.weixinPassword != null)
            return false;
        if (alipayPartnerId != null ? !alipayPartnerId.equals(that.alipayPartnerId) : that.alipayPartnerId != null)
            return false;
        if (alipayAccount != null ? !alipayAccount.equals(that.alipayAccount) : that.alipayAccount != null)
            return false;
        if (alipayKey != null ? !alipayKey.equals(that.alipayKey) : that.alipayKey != null) return false;
        if (alipayAppid != null ? !alipayAppid.equals(that.alipayAppid) : that.alipayAppid != null) return false;
        if (alipayPublicKey != null ? !alipayPublicKey.equals(that.alipayPublicKey) : that.alipayPublicKey != null)
            return false;
        if (alipayPrivateKey != null ? !alipayPrivateKey.equals(that.alipayPrivateKey) : that.alipayPrivateKey != null)
            return false;
        if (chargeRatio != null ? !chargeRatio.equals(that.chargeRatio) : that.chargeRatio != null) return false;
        if (minDrawAmount != null ? !minDrawAmount.equals(that.minDrawAmount) : that.minDrawAmount != null)
            return false;
        if (commissionTotal != null ? !commissionTotal.equals(that.commissionTotal) : that.commissionTotal != null)
            return false;
        if (commissionYear != null ? !commissionYear.equals(that.commissionYear) : that.commissionYear != null)
            return false;
        if (commissionMonth != null ? !commissionMonth.equals(that.commissionMonth) : that.commissionMonth != null)
            return false;
        if (commissionDay != null ? !commissionDay.equals(that.commissionDay) : that.commissionDay != null)
            return false;
        if (lastBuyTime != null ? !lastBuyTime.equals(that.lastBuyTime) : that.lastBuyTime != null) return false;
        if (payTransferPassword != null ? !payTransferPassword.equals(that.payTransferPassword) : that.payTransferPassword != null)
            return false;
        if (transferApiPassword != null ? !transferApiPassword.equals(that.transferApiPassword) : that.transferApiPassword != null)
            return false;
        if (rewardMin != null ? !rewardMin.equals(that.rewardMin) : that.rewardMin != null) return false;
        if (rewardMax != null ? !rewardMax.equals(that.rewardMax) : that.rewardMax != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = configContentId != null ? configContentId.hashCode() : 0;
        result = 31 * result + (weixinAppid != null ? weixinAppid.hashCode() : 0);
        result = 31 * result + (weixinSecret != null ? weixinSecret.hashCode() : 0);
        result = 31 * result + (weixinAccount != null ? weixinAccount.hashCode() : 0);
        result = 31 * result + (weixinPassword != null ? weixinPassword.hashCode() : 0);
        result = 31 * result + (alipayPartnerId != null ? alipayPartnerId.hashCode() : 0);
        result = 31 * result + (alipayAccount != null ? alipayAccount.hashCode() : 0);
        result = 31 * result + (alipayKey != null ? alipayKey.hashCode() : 0);
        result = 31 * result + (alipayAppid != null ? alipayAppid.hashCode() : 0);
        result = 31 * result + (alipayPublicKey != null ? alipayPublicKey.hashCode() : 0);
        result = 31 * result + (alipayPrivateKey != null ? alipayPrivateKey.hashCode() : 0);
        result = 31 * result + (chargeRatio != null ? chargeRatio.hashCode() : 0);
        result = 31 * result + (minDrawAmount != null ? minDrawAmount.hashCode() : 0);
        result = 31 * result + (commissionTotal != null ? commissionTotal.hashCode() : 0);
        result = 31 * result + (commissionYear != null ? commissionYear.hashCode() : 0);
        result = 31 * result + (commissionMonth != null ? commissionMonth.hashCode() : 0);
        result = 31 * result + (commissionDay != null ? commissionDay.hashCode() : 0);
        result = 31 * result + (lastBuyTime != null ? lastBuyTime.hashCode() : 0);
        result = 31 * result + (payTransferPassword != null ? payTransferPassword.hashCode() : 0);
        result = 31 * result + (transferApiPassword != null ? transferApiPassword.hashCode() : 0);
        result = 31 * result + (rewardMin != null ? rewardMin.hashCode() : 0);
        result = 31 * result + (rewardMax != null ? rewardMax.hashCode() : 0);
        return result;
    }
}
