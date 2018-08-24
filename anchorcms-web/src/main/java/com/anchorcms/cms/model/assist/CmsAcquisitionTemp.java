package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-9
 * @Desc 采集临时表
 */
@Entity
@Table(name = "c_acquisition_temp")
public class CmsAcquisitionTemp implements Serializable{
    private static final long serialVersionUID = 5843194292672240629L;
    private int tempId;
    private Integer siteId;
    private String channelUrl;
    private String contentUrl;
    private String title;
    private int finishPercent;
    private String description;
    private int seq;

    @Id
    @Column(name = "temp_id")
    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    @Basic
    @Column(name = "site_id")
    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @Basic
    @Column(name = "channel_url")
    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    @Basic
    @Column(name = "content_url")
    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
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
    @Column(name = "finish_percent")
    public int getFinishPercent() {
        return finishPercent;
    }

    public void setFinishPercent(int finishPercent) {
        this.finishPercent = finishPercent;
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
    @Column(name = "seq")
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsAcquisitionTemp that = (CmsAcquisitionTemp) o;

        if (tempId != that.tempId) return false;
        if (finishPercent != that.finishPercent) return false;
        if (seq != that.seq) return false;
        if (siteId != null ? !siteId.equals(that.siteId) : that.siteId != null) return false;
        if (channelUrl != null ? !channelUrl.equals(that.channelUrl) : that.channelUrl != null) return false;
        if (contentUrl != null ? !contentUrl.equals(that.contentUrl) : that.contentUrl != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tempId;
        result = 31 * result + (siteId != null ? siteId.hashCode() : 0);
        result = 31 * result + (channelUrl != null ? channelUrl.hashCode() : 0);
        result = 31 * result + (contentUrl != null ? contentUrl.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + finishPercent;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + seq;
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
