package com.anchorcms.cms.model.main;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import org.hibernate.annotations.Cache;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc CMS内容扩展表
 */
@Entity
@Table(name = "c_content_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class ContentExt implements Serializable{
    private static final long serialVersionUID = -1992258307360707441L;
    private int contentId;
    private String title;
    private String shortTitle;
    private String author;
    private String origin;
    private String originUrl;
    private String description;
    private Date releaseDate;
    private String mediaPath;
    private String mediaType;
    private String titleColor;
    private Boolean isBold;
    private String titleImg;
    private String contentImg;
    private String typeImg;
    private String link;
    private String tplContent;
    private Boolean needRegenerate;
    private String tplMobileContent;
    private Date toplevelDate;
    private Date pigeonholeDate;

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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "short_title")
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "origin")
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Basic
    @Column(name = "origin_url")
    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
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
    @Column(name = "release_date")
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Basic
    @Column(name = "media_path")
    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    @Basic
    @Column(name = "media_type")
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Basic
    @Column(name = "title_color")
    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    @Basic
    @Column(name = "is_bold")
    public Boolean getIsBold() {
        return isBold;
    }

    public void setIsBold(Boolean isBold) {
        this.isBold = isBold;
    }

    @Basic
    @Column(name = "title_img")
    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    @Basic
    @Column(name = "content_img")
    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    @Basic
    @Column(name = "type_img")
    public String getTypeImg() {
        return typeImg;
    }

    public void setTypeImg(String typeImg) {
        this.typeImg = typeImg;
    }

    @Basic
    @Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Basic
    @Column(name = "tpl_content")
    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
    }

    @Basic
    @Column(name = "need_regenerate")
    public Boolean getNeedRegenerate() {
        return needRegenerate;
    }

    public void setNeedRegenerate(Boolean needRegenerate) {
        this.needRegenerate = needRegenerate;
    }

    @Basic
    @Column(name = "tpl_mobile_content")
    public String getTplMobileContent() {
        return tplMobileContent;
    }

    public void setTplMobileContent(String tplMobileContent) {
        this.tplMobileContent = tplMobileContent;
    }

    @Basic
    @Column(name = "toplevel_date")
    public Date getToplevelDate() {
        return toplevelDate;
    }

    public void setToplevelDate(Date toplevelDate) {
        this.toplevelDate = toplevelDate;
    }

    @Basic
    @Column(name = "pigeonhole_date")
    public Date getPigeonholeDate() {
        return pigeonholeDate;
    }

    public void setPigeonholeDate(Date pigeonholeDate) {
        this.pigeonholeDate = pigeonholeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentExt that = (ContentExt) o;

        if (contentId != that.contentId) return false;
        if (isBold != that.isBold) return false;
        if (needRegenerate != that.needRegenerate) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (shortTitle != null ? !shortTitle.equals(that.shortTitle) : that.shortTitle != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (origin != null ? !origin.equals(that.origin) : that.origin != null) return false;
        if (originUrl != null ? !originUrl.equals(that.originUrl) : that.originUrl != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null) return false;
        if (mediaPath != null ? !mediaPath.equals(that.mediaPath) : that.mediaPath != null) return false;
        if (mediaType != null ? !mediaType.equals(that.mediaType) : that.mediaType != null) return false;
        if (titleColor != null ? !titleColor.equals(that.titleColor) : that.titleColor != null) return false;
        if (titleImg != null ? !titleImg.equals(that.titleImg) : that.titleImg != null) return false;
        if (contentImg != null ? !contentImg.equals(that.contentImg) : that.contentImg != null) return false;
        if (typeImg != null ? !typeImg.equals(that.typeImg) : that.typeImg != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (tplContent != null ? !tplContent.equals(that.tplContent) : that.tplContent != null) return false;
        if (tplMobileContent != null ? !tplMobileContent.equals(that.tplMobileContent) : that.tplMobileContent != null)
            return false;
        if (toplevelDate != null ? !toplevelDate.equals(that.toplevelDate) : that.toplevelDate != null) return false;
        if (pigeonholeDate != null ? !pigeonholeDate.equals(that.pigeonholeDate) : that.pigeonholeDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contentId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (shortTitle != null ? shortTitle.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (originUrl != null ? originUrl.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (mediaPath != null ? mediaPath.hashCode() : 0);
        result = 31 * result + (mediaType != null ? mediaType.hashCode() : 0);
        result = 31 * result + (titleColor != null ? titleColor.hashCode() : 0);
        result = 31 * result + (titleImg != null ? titleImg.hashCode() : 0);
        result = 31 * result + (contentImg != null ? contentImg.hashCode() : 0);
        result = 31 * result + (typeImg != null ? typeImg.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (tplContent != null ? tplContent.hashCode() : 0);
        result = 31 * result + (tplMobileContent != null ? tplMobileContent.hashCode() : 0);
        result = 31 * result + (toplevelDate != null ? toplevelDate.hashCode() : 0);
        result = 31 * result + (pigeonholeDate != null ? pigeonholeDate.hashCode() : 0);
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
        if (getReleaseDate() == null) {
            setReleaseDate(new Timestamp(System.currentTimeMillis()));
        }
        if (getIsBold() == null) {
            setIsBold(false);
        }
        if(getNeedRegenerate()==null){
            setNeedRegenerate(true);
        }
        blankToNull();
    }
    public void blankToNull() {
        if (StringUtils.isBlank(getShortTitle())) {
            setShortTitle(null);
        }
        if (StringUtils.isBlank(getAuthor())) {
            setAuthor(null);
        }
        if (StringUtils.isBlank(getOrigin())) {
            setOrigin(null);
        }
        if (StringUtils.isBlank(getOriginUrl())) {
            setOriginUrl(null);
        }
        if (StringUtils.isBlank(getDescription())) {
            setDescription(null);
        }
        if (StringUtils.isBlank(getTitleColor())) {
            setTitleColor(null);
        }
        if (StringUtils.isBlank(getTitleImg())) {
            setTitleImg(null);
        }
        if (StringUtils.isBlank(getContentImg())) {
            setContentImg(null);
        }
        if (StringUtils.isBlank(getTypeImg())) {
            setTypeImg(null);
        }
        if (StringUtils.isBlank(getLink())) {
            setLink(null);
        }
        if (StringUtils.isBlank(getTplContent())) {
            setTplContent(null);
        }
        if (StringUtils.isBlank(getMediaPath())) {
            setMediaPath(null);
        }
        if (StringUtils.isBlank(getMediaType())) {
            setMediaType(null);
        }
    }
}
