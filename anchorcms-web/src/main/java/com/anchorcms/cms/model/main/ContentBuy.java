package com.anchorcms.cms.model.main;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-8
 * @Desc 收费文章购买记录表
 */
@Entity
@Table(name = "c_content_buy")
public class ContentBuy implements Serializable{
    private static final long serialVersionUID = -6701804839844198737L;
    private long contentBuyId;
    private String orderNumber;
    private int contentId;
    private Integer buyUserId;
    private int authorUserId;
    private double chargeAmount;
    private double authorAmount;
    private double platAmount;
    private Timestamp buyTime;
    private byte hasPaidAuthor;
    private String orderNumWeixin;
    private String orderNumAlipay;
    private short chargeReward;

    @Id
    @Column(name = "content_buy_id")
    public long getContentBuyId() {
        return contentBuyId;
    }

    public void setContentBuyId(long contentBuyId) {
        this.contentBuyId = contentBuyId;
    }

    @Basic
    @Column(name = "order_number")
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Basic
    @Column(name = "content_id")
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    @Basic
    @Column(name = "buy_user_id")
    public Integer getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(Integer buyUserId) {
        this.buyUserId = buyUserId;
    }

    @Basic
    @Column(name = "author_user_id")
    public int getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(int authorUserId) {
        this.authorUserId = authorUserId;
    }

    @Basic
    @Column(name = "charge_amount")
    public double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    @Basic
    @Column(name = "author_amount")
    public double getAuthorAmount() {
        return authorAmount;
    }

    public void setAuthorAmount(double authorAmount) {
        this.authorAmount = authorAmount;
    }

    @Basic
    @Column(name = "plat_amount")
    public double getPlatAmount() {
        return platAmount;
    }

    public void setPlatAmount(double platAmount) {
        this.platAmount = platAmount;
    }

    @Basic
    @Column(name = "buy_time")
    public Timestamp getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Timestamp buyTime) {
        this.buyTime = buyTime;
    }

    @Basic
    @Column(name = "has_paid_author")
    public byte getHasPaidAuthor() {
        return hasPaidAuthor;
    }

    public void setHasPaidAuthor(byte hasPaidAuthor) {
        this.hasPaidAuthor = hasPaidAuthor;
    }

    @Basic
    @Column(name = "order_num_weixin")
    public String getOrderNumWeixin() {
        return orderNumWeixin;
    }

    public void setOrderNumWeixin(String orderNumWeixin) {
        this.orderNumWeixin = orderNumWeixin;
    }

    @Basic
    @Column(name = "order_num_alipay")
    public String getOrderNumAlipay() {
        return orderNumAlipay;
    }

    public void setOrderNumAlipay(String orderNumAlipay) {
        this.orderNumAlipay = orderNumAlipay;
    }

    @Basic
    @Column(name = "charge_reward")
    public short getChargeReward() {
        return chargeReward;
    }

    public void setChargeReward(short chargeReward) {
        this.chargeReward = chargeReward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentBuy that = (ContentBuy) o;

        if (contentBuyId != that.contentBuyId) return false;
        if (contentId != that.contentId) return false;
        if (authorUserId != that.authorUserId) return false;
        if (Double.compare(that.chargeAmount, chargeAmount) != 0) return false;
        if (Double.compare(that.authorAmount, authorAmount) != 0) return false;
        if (Double.compare(that.platAmount, platAmount) != 0) return false;
        if (hasPaidAuthor != that.hasPaidAuthor) return false;
        if (chargeReward != that.chargeReward) return false;
        if (orderNumber != null ? !orderNumber.equals(that.orderNumber) : that.orderNumber != null) return false;
        if (buyUserId != null ? !buyUserId.equals(that.buyUserId) : that.buyUserId != null) return false;
        if (buyTime != null ? !buyTime.equals(that.buyTime) : that.buyTime != null) return false;
        if (orderNumWeixin != null ? !orderNumWeixin.equals(that.orderNumWeixin) : that.orderNumWeixin != null)
            return false;
        if (orderNumAlipay != null ? !orderNumAlipay.equals(that.orderNumAlipay) : that.orderNumAlipay != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (contentBuyId ^ (contentBuyId >>> 32));
        result = 31 * result + (orderNumber != null ? orderNumber.hashCode() : 0);
        result = 31 * result + contentId;
        result = 31 * result + (buyUserId != null ? buyUserId.hashCode() : 0);
        result = 31 * result + authorUserId;
        temp = Double.doubleToLongBits(chargeAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(authorAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(platAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (buyTime != null ? buyTime.hashCode() : 0);
        result = 31 * result + (int) hasPaidAuthor;
        result = 31 * result + (orderNumWeixin != null ? orderNumWeixin.hashCode() : 0);
        result = 31 * result + (orderNumAlipay != null ? orderNumAlipay.hashCode() : 0);
        result = 31 * result + (int) chargeReward;
        return result;
    }
    @Transient
    public boolean getUserHasPaid(){
        if(StringUtils.isNotBlank(getOrderNumWeixin())
                ||StringUtils.isNotBlank(getOrderNumAlipay())){
            return true;
        }else{
            return false;
        }
    }
}
