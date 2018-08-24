package com.anchorcms.icloud.model.cloudcenter;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by ly on 2017/1/10.
 */
@Entity
@Table(name = "s_icloud_quote_manage")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SIcloudQuoteManage implements Serializable{
    private static final long serialVersionUID = -8936880094996967213L;
    private Integer quoteId;
    private String createrId;
    private String offerId;
    private Date createDt;
    private Date updateDt;
    private Date releaseDt;
    private Date deadlineDt;
    private String status;
    private String demandState; //需求方状态位
    private String quoteState;//报价方状态位

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUOTE_ID")

    public Integer getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    @Basic
    @Column(name = "CREATER_ID")
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    @Basic
    @Column(name = "OFFER_ID")
    public String getOfferId() {
        return offerId;
    }
    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    @Basic
    @Column(name = "CREATE_DT")
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    @Basic
    @Column(name = "UPDATE_DT")
    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    @Basic
    @Column(name = "RELEASE_DT")
    public Date getReleaseDt() {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
    }

    @Basic
    @Column(name = "DEADLINE_DT")
    public Date getDeadlineDt() {
        return deadlineDt;
    }

    public void setDeadlineDt(Date deadlineDt) {
        this.deadlineDt = deadlineDt;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "DEMAND_STATE")
    public String getDemandState() {
        return demandState;
    }

    public void setDemandState(String demandState) {
        this.demandState = demandState;
    }

    @Basic
    @Column(name = "QUOTE_STATE")

    public String getQuoteState() {
        return quoteState;
    }

    public void setQuoteState(String quoteState) {
        this.quoteState = quoteState;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SIcloudQuoteManage that = (SIcloudQuoteManage) o;

        if (quoteId != that.quoteId) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (offerId != null ? !offerId.equals(that.offerId) : that.offerId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (demandState != null ? !demandState.equals(that.demandState) : that.demandState != null) return false;
        if (quoteState != null ? !quoteState.equals(that.quoteState) : that.quoteState != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quoteId;
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (offerId != null ? offerId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (demandState != null ? demandState.hashCode() : 0);
        result = 31 * result + (quoteState != null ? quoteState.hashCode() : 0);

        return result;
    }

    private SIcloudDemand demand;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEMAND_ID")
    public SIcloudDemand getDemand() {
        return demand;
    }

    public void setDemand(SIcloudDemand demand) {
        this.demand = demand;
    }

    private SIcloudDemandQuote quote;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEMAND_OBJ_ID")
    public SIcloudDemandQuote getQuote() {
        return quote;
    }

    public void setQuote(SIcloudDemandQuote quote) {
        this.quote = quote;
    }
}
