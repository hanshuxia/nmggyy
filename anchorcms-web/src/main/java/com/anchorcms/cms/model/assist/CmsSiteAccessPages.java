package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by smt on 2016/11/15.
 */
@Entity
@Table(name = "c_site_access_pages")
public class CmsSiteAccessPages implements Serializable{

    private static final long serialVersionUID = 5896616770908696633L;
    private Integer accessPagesId;
    private String accessPage;
    private String sessionId;
    private java.util.Date accessDate;
    private java.util.Date accessTime;
    private Integer visitSecond;
    private Integer pageIndex;

    @Id
    @Column(name = "access_pages_id", nullable = false)
    public Integer getAccessPagesId() {
        return accessPagesId;
    }

    public void setAccessPagesId(Integer accessPagesId) {
        this.accessPagesId = accessPagesId;
    }

    @Basic
    @Column(name = "access_page", nullable = false, length = 255)
    public String getAccessPage() {
        return accessPage;
    }

    public void setAccessPage(String accessPage) {
        this.accessPage = accessPage;
    }

    @Basic
    @Column(name = "session_id", nullable = false, length = 32)
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "access_date", nullable = false)
    public java.util.Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(java.util.Date accessDate) {
        this.accessDate = accessDate;
    }

    @Basic
    @Column(name = "access_time", nullable = false)
    public java.util.Date getAccessTime () {
        return accessTime;
    }

    public void setAccessTime(java.util.Date accessTime) {
        this.accessTime = accessTime;
    }

    @Basic
    @Column(name = "visit_second", nullable = false)
    public Integer getVisitSecond() {
        return visitSecond;
    }

    public void setVisitSecond(Integer visitSecond) {
        this.visitSecond = visitSecond;
    }

    @Basic
    @Column(name = "page_index", nullable = false)
    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsSiteAccessPages that = (CmsSiteAccessPages) o;

        if (accessPagesId != null ? !accessPagesId.equals(that.accessPagesId) : that.accessPagesId != null)
            return false;
        if (accessPage != null ? !accessPage.equals(that.accessPage) : that.accessPage != null) return false;
        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
        if (accessDate != null ? !accessDate.equals(that.accessDate) : that.accessDate != null) return false;
        if (accessTime != null ? !accessTime.equals(that.accessTime) : that.accessTime != null) return false;
        if (visitSecond != null ? !visitSecond.equals(that.visitSecond) : that.visitSecond != null) return false;
        if (pageIndex != null ? !pageIndex.equals(that.pageIndex) : that.pageIndex != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accessPagesId != null ? accessPagesId.hashCode() : 0;
        result = 31 * result + (accessPage != null ? accessPage.hashCode() : 0);
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        result = 31 * result + (accessDate != null ? accessDate.hashCode() : 0);
        result = 31 * result + (accessTime != null ? accessTime.hashCode() : 0);
        result = 31 * result + (visitSecond != null ? visitSecond.hashCode() : 0);
        result = 31 * result + (pageIndex != null ? pageIndex.hashCode() : 0);
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
