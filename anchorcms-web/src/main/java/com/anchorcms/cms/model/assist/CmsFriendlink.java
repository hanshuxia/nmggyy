package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-8
 * @Desc 友链
 */
@Entity
@Table(name = "c_friendlink")
public class CmsFriendlink implements Serializable{
    private static final long serialVersionUID = 1242129851867484547L;
    private int friendlinkId;
    private String siteName;
    private String domain;
    private String logo;
    private String email;
    private String description;
    private Integer views;
    private Boolean isEnabled;
    private Integer priority;
    @Id
    @Column(name = "friendlink_id")
    public int getFriendlinkId() {
        return friendlinkId;
    }

    public void setFriendlinkId(int friendlinkId) {
        this.friendlinkId = friendlinkId;
    }

    @Basic
    @Column(name = "site_name")
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Basic
    @Column(name = "domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Basic
    @Column(name = "logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "views")
    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Basic
    @Column(name = "is_enabled")
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Basic
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsFriendlink that = (CmsFriendlink) o;

        if (friendlinkId != that.friendlinkId) return false;
        if (views != that.views) return false;
        if (priority != that.priority) return false;
        if (siteName != null ? !siteName.equals(that.siteName) : that.siteName != null) return false;
        if (domain != null ? !domain.equals(that.domain) : that.domain != null) return false;
        if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (isEnabled != null ? !isEnabled.equals(that.isEnabled) : that.isEnabled != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = friendlinkId;
        result = 31 * result + (siteName != null ? siteName.hashCode() : 0);
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + views;
        result = 31 * result + (isEnabled != null ? isEnabled.hashCode() : 0);
        result = 31 * result + priority;
        return result;
    }

    private CmsFriendlinkCtg category;
    @ManyToOne
    @JoinColumn(name="friendlinkctg_id")
    public CmsFriendlinkCtg getCategory() {
        return category;
    }

    private CmsSite site;
    @ManyToOne
    @JoinColumn(name="site_id")
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }

    public void setCategory(CmsFriendlinkCtg category) {
        this.category = category;
    }
    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
        if (getViews() == null) {
            setViews(0);
        }
        if (getIsEnabled() == null) {
            setIsEnabled(true);
        }
        blankToNull();
    }
    public void blankToNull() {
        if (StringUtils.isBlank(getLogo())) {
            setLogo(null);
        }
    }
}
