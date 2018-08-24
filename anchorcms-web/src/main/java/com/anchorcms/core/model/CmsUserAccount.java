package com.anchorcms.core.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by smt on 2016/11/18.
 */
@Entity
@Table(name = "c_user_account")
public class CmsUserAccount {
    public static final short DRAW_WEIXIN=0;

    public static final short DRAW_ALIPY=1;
    private Integer userId;
    private String accountWeixin;
    private String accountWeixinOpenId;
    private String accountAlipy;
    private Short drawAccount;
    private Double contentTotalAmount;
    private Double contentNoPayAmount;
    private Double contentYearAmount;
    private Double contentMonthAmount;
    private Double contentDayAmount;
    private Integer contentBuyCount;
    private Integer drawCount;
    private Date lastDrawTime;
    private Date lastBuyTime;

    @Id
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "account_weixin", nullable = true, length = 255)
    public String getAccountWeixin() {
        return accountWeixin;
    }

    public void setAccountWeixin(String accountWeixin) {
        this.accountWeixin = accountWeixin;
    }

    @Basic
    @Column(name = "account_weixin_openId", nullable = true, length = 255)
    public String getAccountWeixinOpenId() {
        return accountWeixinOpenId;
    }

    public void setAccountWeixinOpenId(String accountWeixinOpenId) {
        this.accountWeixinOpenId = accountWeixinOpenId;
    }

    @Basic
    @Column(name = "account_alipy", nullable = true, length = 255)
    public String getAccountAlipy() {
        return accountAlipy;
    }

    public void setAccountAlipy(String accountAlipy) {
        this.accountAlipy = accountAlipy;
    }

    @Basic
    @Column(name = "draw_account", nullable = false)
    public Short getDrawAccount() {
        return drawAccount;
    }

    public void setDrawAccount(Short drawAccount) {
        this.drawAccount = drawAccount;
    }

    @Basic
    @Column(name = "content_total_amount", nullable = true, precision = 4)
    public Double getContentTotalAmount() {
        return contentTotalAmount;
    }

    public void setContentTotalAmount(Double contentTotalAmount) {
        this.contentTotalAmount = contentTotalAmount;
    }

    @Basic
    @Column(name = "content_no_pay_amount", nullable = true, precision = 4)
    public Double getContentNoPayAmount() {
        return contentNoPayAmount;
    }

    public void setContentNoPayAmount(Double contentNoPayAmount) {
        this.contentNoPayAmount = contentNoPayAmount;
    }

    @Basic
    @Column(name = "content_year_amount", nullable = false, precision = 4)
    public Double getContentYearAmount() {
        return contentYearAmount;
    }

    public void setContentYearAmount(Double contentYearAmount) {
        this.contentYearAmount = contentYearAmount;
    }

    @Basic
    @Column(name = "content_month_amount", nullable = false, precision = 4)
    public Double getContentMonthAmount() {
        return contentMonthAmount;
    }

    public void setContentMonthAmount(Double contentMonthAmount) {
        this.contentMonthAmount = contentMonthAmount;
    }

    @Basic
    @Column(name = "content_day_amount", nullable = false, precision = 4)
    public Double getContentDayAmount() {
        return contentDayAmount;
    }

    public void setContentDayAmount(Double contentDayAmount) {
        this.contentDayAmount = contentDayAmount;
    }

    @Basic
    @Column(name = "content_buy_count", nullable = true)
    public Integer getContentBuyCount() {
        return contentBuyCount;
    }

    public void setContentBuyCount(Integer contentBuyCount) {
        this.contentBuyCount = contentBuyCount;
    }

    @Basic
    @Column(name = "draw_count", nullable = true)
    public Integer getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(Integer drawCount) {
        this.drawCount = drawCount;
    }

    @Basic
    @Column(name = "last_draw_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastDrawTime() {
        return lastDrawTime;
    }

    public void setLastDrawTime(Date lastDrawTime) {
        this.lastDrawTime = lastDrawTime;
    }

    @Basic
    @Column(name = "last_buy_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastBuyTime() {
        return lastBuyTime;
    }

    public void setLastBuyTime(Date lastBuyTime) {
        this.lastBuyTime = lastBuyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsUserAccount that = (CmsUserAccount) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (accountWeixin != null ? !accountWeixin.equals(that.accountWeixin) : that.accountWeixin != null)
            return false;
        if (accountWeixinOpenId != null ? !accountWeixinOpenId.equals(that.accountWeixinOpenId) : that.accountWeixinOpenId != null)
            return false;
        if (accountAlipy != null ? !accountAlipy.equals(that.accountAlipy) : that.accountAlipy != null) return false;
        if (drawAccount != null ? !drawAccount.equals(that.drawAccount) : that.drawAccount != null) return false;
        if (contentTotalAmount != null ? !contentTotalAmount.equals(that.contentTotalAmount) : that.contentTotalAmount != null)
            return false;
        if (contentNoPayAmount != null ? !contentNoPayAmount.equals(that.contentNoPayAmount) : that.contentNoPayAmount != null)
            return false;
        if (contentYearAmount != null ? !contentYearAmount.equals(that.contentYearAmount) : that.contentYearAmount != null)
            return false;
        if (contentMonthAmount != null ? !contentMonthAmount.equals(that.contentMonthAmount) : that.contentMonthAmount != null)
            return false;
        if (contentDayAmount != null ? !contentDayAmount.equals(that.contentDayAmount) : that.contentDayAmount != null)
            return false;
        if (contentBuyCount != null ? !contentBuyCount.equals(that.contentBuyCount) : that.contentBuyCount != null)
            return false;
        if (drawCount != null ? !drawCount.equals(that.drawCount) : that.drawCount != null) return false;
        if (lastDrawTime != null ? !lastDrawTime.equals(that.lastDrawTime) : that.lastDrawTime != null) return false;
        if (lastBuyTime != null ? !lastBuyTime.equals(that.lastBuyTime) : that.lastBuyTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (accountWeixin != null ? accountWeixin.hashCode() : 0);
        result = 31 * result + (accountWeixinOpenId != null ? accountWeixinOpenId.hashCode() : 0);
        result = 31 * result + (accountAlipy != null ? accountAlipy.hashCode() : 0);
        result = 31 * result + (drawAccount != null ? drawAccount.hashCode() : 0);
        result = 31 * result + (contentTotalAmount != null ? contentTotalAmount.hashCode() : 0);
        result = 31 * result + (contentNoPayAmount != null ? contentNoPayAmount.hashCode() : 0);
        result = 31 * result + (contentYearAmount != null ? contentYearAmount.hashCode() : 0);
        result = 31 * result + (contentMonthAmount != null ? contentMonthAmount.hashCode() : 0);
        result = 31 * result + (contentDayAmount != null ? contentDayAmount.hashCode() : 0);
        result = 31 * result + (contentBuyCount != null ? contentBuyCount.hashCode() : 0);
        result = 31 * result + (drawCount != null ? drawCount.hashCode() : 0);
        result = 31 * result + (lastDrawTime != null ? lastDrawTime.hashCode() : 0);
        result = 31 * result + (lastBuyTime != null ? lastBuyTime.hashCode() : 0);
        return result;
    }

    private CmsUser user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
}
