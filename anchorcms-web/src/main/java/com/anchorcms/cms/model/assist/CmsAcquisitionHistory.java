package com.anchorcms.cms.model.assist;

import com.anchorcms.cms.model.main.Content;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-9
 * @Desc 采集历史表
 */
@Entity
@Table(name = "c_acquisition_history")
public class CmsAcquisitionHistory implements Serializable{
    private static final long serialVersionUID = 8018496991479205245L;
    private int historyId;
    private String channelUrl;
    private String contentUrl;
    private String title;
    private String description;
    private Integer acquisitionId;
    private Integer contentId;

    @Id
    @Column(name = "history_id")
    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "acquisition_id")
    public Integer getAcquisitionId() {
        return acquisitionId;
    }

    public void setAcquisitionId(Integer acquisitionId) {
        this.acquisitionId = acquisitionId;
    }

    @Basic
    @Column(name = "content_id")
    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsAcquisitionHistory that = (CmsAcquisitionHistory) o;

        if (historyId != that.historyId) return false;
        if (channelUrl != null ? !channelUrl.equals(that.channelUrl) : that.channelUrl != null) return false;
        if (contentUrl != null ? !contentUrl.equals(that.contentUrl) : that.contentUrl != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (acquisitionId != null ? !acquisitionId.equals(that.acquisitionId) : that.acquisitionId != null)
            return false;
        if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = historyId;
        result = 31 * result + (channelUrl != null ? channelUrl.hashCode() : 0);
        result = 31 * result + (contentUrl != null ? contentUrl.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (acquisitionId != null ? acquisitionId.hashCode() : 0);
        result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        return result;
    }

    private Content content;

    private CmsAcquisition acquisition;

    @ManyToOne
    @JoinColumn(name = "acquisition_id",insertable = false,updatable = false)
    public CmsAcquisition getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(CmsAcquisition acquisition) {
        this.acquisition = acquisition;
    }

    @ManyToOne
    @JoinColumn(name = "content_id",insertable = false,updatable = false)
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
