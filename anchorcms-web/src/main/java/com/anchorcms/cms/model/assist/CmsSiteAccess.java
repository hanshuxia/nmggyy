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
@Table(name = "c_site_access")
public class CmsSiteAccess implements Serializable{
    private static final long serialVersionUID = -5627704869197296112L;
    public static final String ENGINE_BAIDU=".baidu.";
    public static final String ENGINE_GOOGLE=".google.";
    public static final String ENGINE_YAHOO=".yahoo.";
    public static final String ENGINE_BING=".bing.";
    public static final String ENGINE_SOGOU=".sogou.";
    public static final String ENGINE_SOSO=".soso.";
    public static final String ENGINE_SO=".so.";
    private Integer accessId;
    private String sessionId;
    private java.util.Date accessTime;
    private java.util.Date accessDate;
    private String ip;
    private String area;
    private String accessSource;
    private String externalLink;
    private String engine;
    private String entryPage;
    private String lastStopPage;
    private Integer visitSecond;
    private Integer visitPageCount;
    private String operatingSystem;
    private String browser;
    private java.lang.String keyword;

    @Id
    @Column(name = "access_id", nullable = false)
    public Integer getAccessId() {
        return accessId;
    }

    public void setAccessId(Integer accessId) {
        this.accessId = accessId;
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
    @Column(name = "access_time", nullable = false)
    public java.util.Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(java.util.Date accessTime) {
        this.accessTime = accessTime;
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
    @Column(name = "ip", nullable = false, length = 50)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "area", nullable = true, length = 50)
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Basic
    @Column(name = "access_source", nullable = true, length = 255)
    public String getAccessSource() {
        return accessSource;
    }

    public void setAccessSource(String accessSource) {
        this.accessSource = accessSource;
    }

    @Basic
    @Column(name = "external_link", nullable = true, length = 255)
    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    @Basic
    @Column(name = "engine", nullable = true, length = 50)
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Basic
    @Column(name = "entry_page", nullable = true, length = 255)
    public String getEntryPage() {
        return entryPage;
    }

    public void setEntryPage(String entryPage) {
        this.entryPage = entryPage;
    }

    @Basic
    @Column(name = "last_stop_page", nullable = true, length = 255)
    public String getLastStopPage() {
        return lastStopPage;
    }

    public void setLastStopPage(String lastStopPage) {
        this.lastStopPage = lastStopPage;
    }

    @Basic
    @Column(name = "visit_second", nullable = true)
    public Integer getVisitSecond() {
        return visitSecond;
    }

    public void setVisitSecond(Integer visitSecond) {
        this.visitSecond = visitSecond;
    }

    @Basic
    @Column(name = "visit_page_count", nullable = true)
    public Integer getVisitPageCount() {
        return visitPageCount;
    }

    public void setVisitPageCount(Integer visitPageCount) {
        this.visitPageCount = visitPageCount;
    }

    @Basic
    @Column(name = "operating_system", nullable = true, length = 50)
    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @Basic
    @Column(name = "browser", nullable = true, length = 50)
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    @Basic
    @Column(name = "keyword", nullable = true, length = 255)
    public java.lang.String getKeyword () {
        return keyword;
    }

    public void setKeyword (java.lang.String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsSiteAccess that = (CmsSiteAccess) o;

        if (accessId != null ? !accessId.equals(that.accessId) : that.accessId != null) return false;
        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
        if (accessTime != null ? !accessTime.equals(that.accessTime) : that.accessTime != null) return false;
        if (accessDate != null ? !accessDate.equals(that.accessDate) : that.accessDate != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (accessSource != null ? !accessSource.equals(that.accessSource) : that.accessSource != null) return false;
        if (externalLink != null ? !externalLink.equals(that.externalLink) : that.externalLink != null) return false;
        if (engine != null ? !engine.equals(that.engine) : that.engine != null) return false;
        if (entryPage != null ? !entryPage.equals(that.entryPage) : that.entryPage != null) return false;
        if (lastStopPage != null ? !lastStopPage.equals(that.lastStopPage) : that.lastStopPage != null) return false;
        if (visitSecond != null ? !visitSecond.equals(that.visitSecond) : that.visitSecond != null) return false;
        if (visitPageCount != null ? !visitPageCount.equals(that.visitPageCount) : that.visitPageCount != null)
            return false;
        if (operatingSystem != null ? !operatingSystem.equals(that.operatingSystem) : that.operatingSystem != null)
            return false;
        if (browser != null ? !browser.equals(that.browser) : that.browser != null) return false;
        if (keyword != null ? !keyword.equals(that.keyword) : that.keyword != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accessId != null ? accessId.hashCode() : 0;
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        result = 31 * result + (accessTime != null ? accessTime.hashCode() : 0);
        result = 31 * result + (accessDate != null ? accessDate.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (accessSource != null ? accessSource.hashCode() : 0);
        result = 31 * result + (externalLink != null ? externalLink.hashCode() : 0);
        result = 31 * result + (engine != null ? engine.hashCode() : 0);
        result = 31 * result + (entryPage != null ? entryPage.hashCode() : 0);
        result = 31 * result + (lastStopPage != null ? lastStopPage.hashCode() : 0);
        result = 31 * result + (visitSecond != null ? visitSecond.hashCode() : 0);
        result = 31 * result + (visitPageCount != null ? visitPageCount.hashCode() : 0);
        result = 31 * result + (operatingSystem != null ? operatingSystem.hashCode() : 0);
        result = 31 * result + (browser != null ? browser.hashCode() : 0);
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
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
