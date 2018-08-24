package com.anchorcms.cms.model.main;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc CMS内容计数表
 */
@Entity
@Table(name = "c_content_count")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class ContentCount implements Serializable{
    private static final long serialVersionUID = -7649522000147440059L;
    private int contentId;
    private Integer views;
    private Integer viewsMonth;
    private Integer viewsWeek;
    private Integer viewsDay;
    private Integer comments;
    private Integer commentsMonth;
    private Short commentsWeek;
    private Short commentsDay;
    private Integer downloads;
    private Integer downloadsMonth;
    private Short downloadsWeek;
    private Short downloadsDay;
    private Integer ups;
    private Integer upsMonth;
    private Short upsWeek;
    private Short upsDay;
    private Integer downs;

    @Id
    @GeneratedValue
    @Column(name = "content_id")
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
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
    @Column(name = "views_month")
    public Integer getViewsMonth() {
        return viewsMonth;
    }

    public void setViewsMonth(Integer viewsMonth) {
        this.viewsMonth = viewsMonth;
    }

    @Basic
    @Column(name = "views_week")
    public Integer getViewsWeek() {
        return viewsWeek;
    }

    public void setViewsWeek(Integer viewsWeek) {
        this.viewsWeek = viewsWeek;
    }

    @Basic
    @Column(name = "views_day")
    public Integer getViewsDay() {
        return viewsDay;
    }

    public void setViewsDay(Integer viewsDay) {
        this.viewsDay = viewsDay;
    }

    @Basic
    @Column(name = "comments")
    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    @Basic
    @Column(name = "comments_month")
    public Integer getCommentsMonth() {
        return commentsMonth;
    }

    public void setCommentsMonth(Integer commentsMonth) {
        this.commentsMonth = commentsMonth;
    }

    @Basic
    @Column(name = "comments_week")
    public Short getCommentsWeek() {
        return commentsWeek;
    }

    public void setCommentsWeek(Short commentsWeek) {
        this.commentsWeek = commentsWeek;
    }

    @Basic
    @Column(name = "comments_day")
    public Short getCommentsDay() {
        return commentsDay;
    }

    public void setCommentsDay(Short commentsDay) {
        this.commentsDay = commentsDay;
    }

    @Basic
    @Column(name = "downloads")
    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Basic
    @Column(name = "downloads_month")
    public Integer getDownloadsMonth() {
        return downloadsMonth;
    }

    public void setDownloadsMonth(Integer downloadsMonth) {
        this.downloadsMonth = downloadsMonth;
    }

    @Basic
    @Column(name = "downloads_week")
    public Short getDownloadsWeek() {
        return downloadsWeek;
    }

    public void setDownloadsWeek(Short downloadsWeek) {
        this.downloadsWeek = downloadsWeek;
    }

    @Basic
    @Column(name = "downloads_day")
    public Short getDownloadsDay() {
        return downloadsDay;
    }

    public void setDownloadsDay(Short downloadsDay) {
        this.downloadsDay = downloadsDay;
    }

    @Basic
    @Column(name = "ups")
    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }

    @Basic
    @Column(name = "ups_month")
    public Integer getUpsMonth() {
        return upsMonth;
    }

    public void setUpsMonth(Integer upsMonth) {
        this.upsMonth = upsMonth;
    }

    @Basic
    @Column(name = "ups_week")
    public Short getUpsWeek() {
        return upsWeek;
    }

    public void setUpsWeek(Short upsWeek) {
        this.upsWeek = upsWeek;
    }

    @Basic
    @Column(name = "ups_day")
    public Short getUpsDay() {
        return upsDay;
    }

    public void setUpsDay(Short upsDay) {
        this.upsDay = upsDay;
    }

    @Basic
    @Column(name = "downs")
    public Integer getDowns() {
        return downs;
    }

    public void setDowns(Integer downs) {
        this.downs = downs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentCount that = (ContentCount) o;

        if (contentId != that.contentId) return false;
        if (views != that.views) return false;
        if (viewsMonth != that.viewsMonth) return false;
        if (viewsWeek != that.viewsWeek) return false;
        if (viewsDay != that.viewsDay) return false;
        if (comments != that.comments) return false;
        if (commentsMonth != that.commentsMonth) return false;
        if (commentsWeek != that.commentsWeek) return false;
        if (commentsDay != that.commentsDay) return false;
        if (downloads != that.downloads) return false;
        if (downloadsMonth != that.downloadsMonth) return false;
        if (downloadsWeek != that.downloadsWeek) return false;
        if (downloadsDay != that.downloadsDay) return false;
        if (ups != that.ups) return false;
        if (upsMonth != that.upsMonth) return false;
        if (upsWeek != that.upsWeek) return false;
        if (upsDay != that.upsDay) return false;
        if (downs != that.downs) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contentId;
        result = 31 * result + views;
        result = 31 * result + viewsMonth;
        result = 31 * result + viewsWeek;
        result = 31 * result + viewsDay;
        result = 31 * result + comments;
        result = 31 * result + commentsMonth;
        result = 31 * result + (int) commentsWeek;
        result = 31 * result + (int) commentsDay;
        result = 31 * result + downloads;
        result = 31 * result + downloadsMonth;
        result = 31 * result + (int) downloadsWeek;
        result = 31 * result + (int) downloadsDay;
        result = 31 * result + ups;
        result = 31 * result + upsMonth;
        result = 31 * result + (int) upsWeek;
        result = 31 * result + (int) upsDay;
        result = 31 * result + downs;
        return result;
    }

    private Content content;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name="content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
    public void init() {
        short zero = 0;
        if (getDowns() == null) {
            setDowns(0);
        }
        if (getViews() == null) {
            setViews(0);
        }
        if (getViewsMonth() == null) {
            setViewsMonth(0);
        }
        if (getViewsWeek() == null) {
            setViewsWeek(0);
        }
        if (getViewsDay() == null) {
            setViewsDay(0);
        }
        if (getComments() == null) {
            setComments(0);
        }
        if (getCommentsMonth() == null) {
            setCommentsMonth(0);
        }
        if (getCommentsWeek() == null) {
            setCommentsWeek(zero);
        }
        if (getCommentsDay() == null) {
            setCommentsDay(zero);
        }
        if (getDownloads() == null) {
            setDownloads(0);
        }
        if (getDownloadsMonth() == null) {
            setDownloadsMonth(0);
        }
        if (getDownloadsWeek() == null) {
            setDownloadsWeek(zero);
        }
        if (getDownloadsDay() == null) {
            setDownloadsDay(zero);
        }
        if (getUps() == null) {
            setUps(0);
        }
        if (getUpsMonth() == null) {
            setUpsMonth(0);
        }
        if (getUpsWeek() == null) {
            setUpsWeek(zero);
        }
        if (getUpsDay() == null) {
            setUpsDay(zero);
        }
    }
}
