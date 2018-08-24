package com.anchorcms.cms.model.main;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc 收费内容配置表
 */
@Entity
@Table(name = "c_content_charge")
public class ContentCharge implements Serializable{
    private static final long serialVersionUID = -870499369012687532L;
    public static final Short MODEL_FREE=0;
    public static final Short MODEL_CHARGE=1;
    public static final Short MODEL_REWARD=2;
    private int contentId;
    private Double chargeAmount = 0.0;
    private Double totalAmount = 0.0;
    private Double yearAmount = 0.0;
    private Double monthAmount = 0.0;
    private Double dayAmount = 0.0;
    private Date lastBuyTime;
    private Short chargeReward = 0;

    @Id
    @Column(name = "content_id")
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    @Basic
    @Column(name = "charge_amount")
    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    @Basic
    @Column(name = "total_amount")
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Basic
    @Column(name = "year_amount")
    public Double getYearAmount() {
        return yearAmount;
    }

    public void setYearAmount(Double yearAmount) {
        this.yearAmount = yearAmount;
    }

    @Basic
    @Column(name = "month_amount")
    public Double getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Double monthAmount) {
        this.monthAmount = monthAmount;
    }

    @Basic
    @Column(name = "day_amount")
    public Double getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(Double dayAmount) {
        this.dayAmount = dayAmount;
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
    @Column(name = "charge_reward")
    public Short getChargeReward() {
        return chargeReward;
    }

    public void setChargeReward(Short chargeReward) {
        this.chargeReward = chargeReward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentCharge that = (ContentCharge) o;

        if (contentId != that.contentId) return false;
        if (Double.compare(that.chargeAmount, chargeAmount) != 0) return false;
        if (Double.compare(that.totalAmount, totalAmount) != 0) return false;
        if (Double.compare(that.yearAmount, yearAmount) != 0) return false;
        if (Double.compare(that.monthAmount, monthAmount) != 0) return false;
        if (Double.compare(that.dayAmount, dayAmount) != 0) return false;
        if (chargeReward != that.chargeReward) return false;
        if (lastBuyTime != null ? !lastBuyTime.equals(that.lastBuyTime) : that.lastBuyTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = contentId;
       // temp = Double.doubleToLongBits(chargeAmount);
       // result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(yearAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(monthAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(dayAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (lastBuyTime != null ? lastBuyTime.hashCode() : 0);
        result = 31 * result + (int) chargeReward;
        return result;
    }

    private Content content;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name="content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
    public void init(){
        if(getChargeAmount()==null){
            setChargeAmount(0d);
        }
        if(getDayAmount()==null){
            setDayAmount(0d);
        }
        if(getMonthAmount()==null){
            setMonthAmount(0d);
        }
        if(getYearAmount()==null){
            setYearAmount(0d);
        }
        if(getTotalAmount()==null){
            setTotalAmount(0d);
        }
    }
}
