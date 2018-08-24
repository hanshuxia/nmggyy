package com.anchorcms.core.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-7
 * @Desc CMS日志表
 */
@Entity
@Table(name = "c_log")
public class CmsLog implements Serializable{
    private static final long serialVersionUID = -5178919087310855782L;
    private int logId;
    private int category;
    private Date logTime;
    private String ip;
    private String url;
    private String title;
    private String content;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    @Basic
    @Column(name = "category")
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Basic
    @Column(name = "log_time")
    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsLog cmsLog = (CmsLog) o;

        if (logId != cmsLog.logId) return false;
        if (category != cmsLog.category) return false;
        if (logTime != null ? !logTime.equals(cmsLog.logTime) : cmsLog.logTime != null) return false;
        if (ip != null ? !ip.equals(cmsLog.ip) : cmsLog.ip != null) return false;
        if (url != null ? !url.equals(cmsLog.url) : cmsLog.url != null) return false;
        if (title != null ? !title.equals(cmsLog.title) : cmsLog.title != null) return false;
        if (content != null ? !content.equals(cmsLog.content) : cmsLog.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = logId;
        result = 31 * result + category;
        result = 31 * result + (logTime != null ? logTime.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    private CmsSite site;

    private CmsUser user;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 2;
    public static final int OPERATING = 3;

    public static final String LOGIN_SUCCESS_TITLE = "login success";
    public static final String LOGIN_FAILURE_TITLE = "login failure";
    @ManyToOne
    @JoinColumn(name = "user_id")
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
    @ManyToOne
    @JoinColumn(name = "site_id")
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
}
