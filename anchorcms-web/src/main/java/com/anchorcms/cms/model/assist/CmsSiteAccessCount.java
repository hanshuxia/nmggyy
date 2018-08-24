package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by smt on 2016/11/15.
 */
@Entity
@Table(name = "c_site_access_count")
public class CmsSiteAccessCount implements Serializable{
    private static final long serialVersionUID = 8393385996058311024L;
    private Integer accessCount;
    private Integer pageCount;
    private Integer visitors;
    private Date statisticDate;

    @Id
    @Column(name = "access_count", nullable = false)
    public Integer getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    @Basic
    @Column(name = "page_count", nullable = false)
    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
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
    @Column(name = "statistic_date", nullable = false)
    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsSiteAccessCount that = (CmsSiteAccessCount) o;

        if (accessCount != null ? !accessCount.equals(that.accessCount) : that.accessCount != null) return false;
        if (pageCount != null ? !pageCount.equals(that.pageCount) : that.pageCount != null) return false;
        if (visitors != null ? !visitors.equals(that.visitors) : that.visitors != null) return false;
        if (statisticDate != null ? !statisticDate.equals(that.statisticDate) : that.statisticDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accessCount != null ? accessCount.hashCode() : 0;
        result = 31 * result + (pageCount != null ? pageCount.hashCode() : 0);
        result = 31 * result + (visitors != null ? visitors.hashCode() : 0);
        result = 31 * result + (statisticDate != null ? statisticDate.hashCode() : 0);
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
}
