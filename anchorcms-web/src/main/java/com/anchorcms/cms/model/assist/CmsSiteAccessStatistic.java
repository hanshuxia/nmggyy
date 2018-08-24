package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by smt on 2016/11/15.
 */
@Entity
@Table(name = "c_site_access_statistic")
public class CmsSiteAccessStatistic implements Serializable {

    private static final long serialVersionUID = -8895466143307420740L;
    private Integer accessStatisticId;
    private Date statisticDate;
    private Integer pv;
    private Integer ip;
    private Integer visitors;
    private Integer pagesAver;
    private Integer visitSecondAver;
    private String statisitcType;
    private String statisticColumnValue;

    @Id
    @Column(name = "access_statistic_id", nullable = false)
    public Integer getAccessStatisticId() {
        return accessStatisticId;
    }

    public void setAccessStatisticId(Integer accessStatisticId) {
        this.accessStatisticId = accessStatisticId;
    }

    @Basic
    @Column(name = "statistic_date", nullable = false)
    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    @Basic
    @Column(name = "pv", nullable = false)
    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    @Basic
    @Column(name = "ip", nullable = false)
    public Integer getIp() {
        return ip;
    }

    public void setIp(Integer ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "visitors", nullable = false)
    public Integer getVisitors() {
        return visitors;
    }

    public void setVisitors(Integer visitors) {
        this.visitors = visitors;
    }

    @Basic
    @Column(name = "pages_aver", nullable = false)
    public Integer getPagesAver() {
        return pagesAver;
    }

    public void setPagesAver(Integer pagesAver) {
        this.pagesAver = pagesAver;
    }

    @Basic
    @Column(name = "visit_second_aver", nullable = false)
    public Integer getVisitSecondAver() {
        return visitSecondAver;
    }

    public void setVisitSecondAver(Integer visitSecondAver) {
        this.visitSecondAver = visitSecondAver;
    }

    @Basic
    @Column(name = "statisitc_type", nullable = false, length = 255)
    public String getStatisitcType() {
        return statisitcType;
    }

    public void setStatisitcType(String statisitcType) {
        this.statisitcType = statisitcType;
    }

    @Basic
    @Column(name = "statistic_column_value", nullable = true, length = 255)
    public String getStatisticColumnValue() {
        return statisticColumnValue;
    }

    public void setStatisticColumnValue(String statisticColumnValue) {
        this.statisticColumnValue = statisticColumnValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsSiteAccessStatistic that = (CmsSiteAccessStatistic) o;

        if (accessStatisticId != null ? !accessStatisticId.equals(that.accessStatisticId) : that.accessStatisticId != null)
            return false;
        if (statisticDate != null ? !statisticDate.equals(that.statisticDate) : that.statisticDate != null)
            return false;
        if (pv != null ? !pv.equals(that.pv) : that.pv != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (visitors != null ? !visitors.equals(that.visitors) : that.visitors != null) return false;
        if (pagesAver != null ? !pagesAver.equals(that.pagesAver) : that.pagesAver != null) return false;
        if (visitSecondAver != null ? !visitSecondAver.equals(that.visitSecondAver) : that.visitSecondAver != null)
            return false;
        if (statisitcType != null ? !statisitcType.equals(that.statisitcType) : that.statisitcType != null)
            return false;
        if (statisticColumnValue != null ? !statisticColumnValue.equals(that.statisticColumnValue) : that.statisticColumnValue != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accessStatisticId != null ? accessStatisticId.hashCode() : 0;
        result = 31 * result + (statisticDate != null ? statisticDate.hashCode() : 0);
        result = 31 * result + (pv != null ? pv.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (visitors != null ? visitors.hashCode() : 0);
        result = 31 * result + (pagesAver != null ? pagesAver.hashCode() : 0);
        result = 31 * result + (visitSecondAver != null ? visitSecondAver.hashCode() : 0);
        result = 31 * result + (statisitcType != null ? statisitcType.hashCode() : 0);
        result = 31 * result + (statisticColumnValue != null ? statisticColumnValue.hashCode() : 0);
        return result;
    }

    private CmsSite site;

    @ManyToOne
    @JoinColumn(name = "site_id",insertable = false,updatable = false)
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }

    public static final String STATISTIC_ALL = "all";
    public static final String STATISTIC_SOURCE = "source";
    public static final String STATISTIC_ENGINE = "engine";
    public static final String STATISTIC_LINK = "link";
    public static final String STATISTIC_KEYWORD = "keyword";
    public static final String STATISTIC_AREA = "area";
    public static final String STATISTIC_BROWER = "brower";
    public static final String STATISTIC_SYSTEM = "system";
    public static final int STATISTIC_TARGET_PV = 0;
    public static final int STATISTIC_TARGET_IP = 1;
    public static final int STATISTIC_TARGET_VISITORS= 2;
    public static final int STATISTIC_TARGET_VISITSECOND = 3;
}
