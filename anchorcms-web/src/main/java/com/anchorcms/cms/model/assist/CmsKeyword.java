package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-7
 * @Desc 关键词
 */
@Entity
@Table(name = "c_keyword")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsKeyword implements Serializable{
    private static final long serialVersionUID = -7021544213507351322L;
    private int keywordId;
    private String keywordName;
    private String url;
    private Boolean isDisabled;

    @Id
    @Column(name = "keyword_id")
    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }

    @Basic
    @Column(name = "keyword_name")
    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
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
    @Column(name = "is_disabled")
    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsKeyword cmsKeyword = (CmsKeyword) o;

        if (keywordId != cmsKeyword.keywordId) return false;
        if (isDisabled != cmsKeyword.isDisabled) return false;
        if (keywordName != null ? !keywordName.equals(cmsKeyword.keywordName) : cmsKeyword.keywordName != null)
            return false;
        if (url != null ? !url.equals(cmsKeyword.url) : cmsKeyword.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keywordId;
        result = 31 * result + (keywordName != null ? keywordName.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    public void init() {
        if (getIsDisabled() == null) {
            setIsDisabled(false);
        }
    }

    private CmsSite site;

    @ManyToOne
    @JoinColumn(name = "site_id",insertable = true,updatable = true)
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
}
