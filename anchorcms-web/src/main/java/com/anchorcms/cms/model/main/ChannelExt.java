package com.anchorcms.cms.model.main;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-2
 * @Desc CMS栏目内容表
 */
@Entity
@Table(name = "c_channel_ext")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class ChannelExt implements Serializable{
    private static final long serialVersionUID = -8540037366242221095L;
    private int channelId;
    private String channelName;
    private Byte finalStep;
    private Byte afterCheck;
    private Boolean isStaticChannel;
    private Boolean isStaticContent;
    private Boolean isAccessByDir;
    private Boolean isListChild;
    private Integer pageSize;
    private String channelRule;
    private String contentRule;
    private String link;
    private String tplChannel;
    private String tplContent;
    private String titleImg;
    private String contentImg;
    private Boolean hasTitleImg;
    private Boolean hasContentImg;
    private Integer titleImgWidth;
    private Integer titleImgHeight;
    private Integer contentImgWidth;
    private Integer contentImgHeight;
    private Integer commentControl;
    private Boolean allowUpdown;
    private Boolean isBlank;
    private String title;
    private String keywords;
    private String description;
    private Boolean allowShare;
    private Boolean allowScore;
    private String tplMobileChannel;

    @Id
    @GeneratedValue
    @Column(name = "channel_id")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Basic
    @Column(name = "channel_name")
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Basic
    @Column(name = "final_step")
    public Byte getFinalStep() {
        return finalStep;
    }

    public void setFinalStep(Byte finalStep) {
        this.finalStep = finalStep;
    }

    @Basic
    @Column(name = "after_check")
    public Byte getAfterCheck() {
        return afterCheck;
    }

    public void setAfterCheck(Byte afterCheck) {
        this.afterCheck = afterCheck;
    }

    @Basic
    @Column(name = "is_static_channel")
    public Boolean getIsStaticChannel() {
        return isStaticChannel;
    }

    public void setIsStaticChannel(Boolean isStaticChannel) {
        this.isStaticChannel = isStaticChannel;
    }

    @Basic
    @Column(name = "is_static_content")
    public Boolean getIsStaticContent() {
        return isStaticContent;
    }

    public void setIsStaticContent(Boolean isStaticContent) {
        this.isStaticContent = isStaticContent;
    }

    @Basic
    @Column(name = "is_access_by_dir")
    public Boolean getIsAccessByDir() {
        return isAccessByDir;
    }

    public void setIsAccessByDir(Boolean isAccessByDir) {
        this.isAccessByDir = isAccessByDir;
    }

    @Basic
    @Column(name = "is_list_child")
    public Boolean getIsListChild() {
        return isListChild;
    }

    public void setIsListChild(Boolean isListChild) {
        this.isListChild = isListChild;
    }

    @Basic
    @Column(name = "page_size")
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Basic
    @Column(name = "channel_rule")
    public String getChannelRule() {
        return channelRule;
    }

    public void setChannelRule(String channelRule) {
        this.channelRule = channelRule;
    }

    @Basic
    @Column(name = "content_rule")
    public String getContentRule() {
        return contentRule;
    }

    public void setContentRule(String contentRule) {
        this.contentRule = contentRule;
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
    @Column(name = "tpl_channel")
    public String getTplChannel() {
        return tplChannel;
    }

    public void setTplChannel(String tplChannel) {
        this.tplChannel = tplChannel;
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
    @Column(name = "has_title_img")
    public Boolean getHasTitleImg() {
        return hasTitleImg;
    }

    public void setHasTitleImg(Boolean hasTitleImg) {
        this.hasTitleImg = hasTitleImg;
    }

    @Basic
    @Column(name = "has_content_img")
    public Boolean getHasContentImg() {
        return hasContentImg;
    }

    public void setHasContentImg(Boolean hasContentImg) {
        this.hasContentImg = hasContentImg;
    }

    @Basic
    @Column(name = "title_img_width")
    public Integer getTitleImgWidth() {
        return titleImgWidth;
    }

    public void setTitleImgWidth(Integer titleImgWidth) {
        this.titleImgWidth = titleImgWidth;
    }

    @Basic
    @Column(name = "title_img_height")
    public Integer getTitleImgHeight() {
        return titleImgHeight;
    }

    public void setTitleImgHeight(Integer titleImgHeight) {
        this.titleImgHeight = titleImgHeight;
    }

    @Basic
    @Column(name = "content_img_width")
    public Integer getContentImgWidth() {
        return contentImgWidth;
    }

    public void setContentImgWidth(Integer contentImgWidth) {
        this.contentImgWidth = contentImgWidth;
    }

    @Basic
    @Column(name = "content_img_height")
    public Integer getContentImgHeight() {
        return contentImgHeight;
    }

    public void setContentImgHeight(Integer contentImgHeight) {
        this.contentImgHeight = contentImgHeight;
    }

    @Basic
    @Column(name = "comment_control")
    public Integer getCommentControl() {
        return commentControl;
    }

    public void setCommentControl(Integer commentControl) {
        this.commentControl = commentControl;
    }

    @Basic
    @Column(name = "allow_updown")
    public Boolean getAllowUpdown() {
        return allowUpdown;
    }

    public void setAllowUpdown(Boolean allowUpdown) {
        this.allowUpdown = allowUpdown;
    }

    @Basic
    @Column(name = "is_blank")
    public Boolean getIsBlank() {
        return isBlank;
    }

    public void setIsBlank(Boolean isBlank) {
        this.isBlank = isBlank;
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
    @Column(name = "keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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
    @Column(name = "allow_share")
    public Boolean getAllowShare() {
        return allowShare;
    }

    public void setAllowShare(Boolean allowShare) {
        this.allowShare = allowShare;
    }

    @Basic
    @Column(name = "allow_score")
    public Boolean getAllowScore() {
        return allowScore;
    }

    public void setAllowScore(Boolean allowScore) {
        this.allowScore = allowScore;
    }

    @Basic
    @Column(name = "tpl_mobile_channel")
    public String getTplMobileChannel() {
        return tplMobileChannel;
    }

    public void setTplMobileChannel(String tplMobileChannel) {
        this.tplMobileChannel = tplMobileChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChannelExt that = (ChannelExt) o;

        if (channelId != that.channelId) return false;
        if (pageSize != that.pageSize) return false;
        if (hasTitleImg != that.hasTitleImg) return false;
        if (hasContentImg != that.hasContentImg) return false;
        if (titleImgWidth != that.titleImgWidth) return false;
        if (titleImgHeight != that.titleImgHeight) return false;
        if (contentImgWidth != that.contentImgWidth) return false;
        if (contentImgHeight != that.contentImgHeight) return false;
        if (commentControl != that.commentControl) return false;
        if (allowUpdown != that.allowUpdown) return false;
        if (isBlank != that.isBlank) return false;
        if (allowShare != that.allowShare) return false;
        if (allowScore != that.allowScore) return false;
        if (channelName != null ? !channelName.equals(that.channelName) : that.channelName != null) return false;
        if (finalStep != null ? !finalStep.equals(that.finalStep) : that.finalStep != null) return false;
        if (afterCheck != null ? !afterCheck.equals(that.afterCheck) : that.afterCheck != null) return false;
        if (isStaticChannel != null ? !isStaticChannel.equals(that.isStaticChannel) : that.isStaticChannel != null)
            return false;
        if (isStaticContent != null ? !isStaticContent.equals(that.isStaticContent) : that.isStaticContent != null)
            return false;
        if (isAccessByDir != null ? !isAccessByDir.equals(that.isAccessByDir) : that.isAccessByDir != null)
            return false;
        if (isListChild != null ? !isListChild.equals(that.isListChild) : that.isListChild != null) return false;
        if (channelRule != null ? !channelRule.equals(that.channelRule) : that.channelRule != null) return false;
        if (contentRule != null ? !contentRule.equals(that.contentRule) : that.contentRule != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (tplChannel != null ? !tplChannel.equals(that.tplChannel) : that.tplChannel != null) return false;
        if (tplContent != null ? !tplContent.equals(that.tplContent) : that.tplContent != null) return false;
        if (titleImg != null ? !titleImg.equals(that.titleImg) : that.titleImg != null) return false;
        if (contentImg != null ? !contentImg.equals(that.contentImg) : that.contentImg != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (keywords != null ? !keywords.equals(that.keywords) : that.keywords != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (tplMobileChannel != null ? !tplMobileChannel.equals(that.tplMobileChannel) : that.tplMobileChannel != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = channelId;
        result = 31 * result + (channelName != null ? channelName.hashCode() : 0);
        result = 31 * result + (finalStep != null ? finalStep.hashCode() : 0);
        result = 31 * result + (afterCheck != null ? afterCheck.hashCode() : 0);
        result = 31 * result + (isStaticChannel != null ? isStaticChannel.hashCode() : 0);
        result = 31 * result + (isStaticContent != null ? isStaticContent.hashCode() : 0);
        result = 31 * result + (isAccessByDir != null ? isAccessByDir.hashCode() : 0);
        result = 31 * result + (isListChild != null ? isListChild.hashCode() : 0);
        result = 31 * result + pageSize;
        result = 31 * result + (channelRule != null ? channelRule.hashCode() : 0);
        result = 31 * result + (contentRule != null ? contentRule.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (tplChannel != null ? tplChannel.hashCode() : 0);
        result = 31 * result + (tplContent != null ? tplContent.hashCode() : 0);
        result = 31 * result + (titleImg != null ? titleImg.hashCode() : 0);
        result = 31 * result + (contentImg != null ? contentImg.hashCode() : 0);
        result = 31 * result + titleImgWidth;
        result = 31 * result + titleImgHeight;
        result = 31 * result + contentImgWidth;
        result = 31 * result + contentImgHeight;
        result = 31 * result + commentControl;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (keywords != null ? keywords.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tplMobileChannel != null ? tplMobileChannel.hashCode() : 0);
        return result;
    }

    private Channel channel;
    public static String PROP_FINAL_STEP = "finalStep";
    public static String PROP_AFTER_CHECK = "afterCheck";
    /**
     * 游客评论
     */
    public static final int COMMENT_OPEN = 0;
    /**
     * 登录评论
     */
    public static final int COMMENT_LOGIN = 1;
    /**
     * 关闭评论
     */
    public static final int COMMENT_OFF = 2;
    /**
     * 登陆评论多次
     */
    public static final int COMMENT_LOGIN_MANY = 3;

    @OneToOne
    @MapsId
    @JoinColumn(name="channel_id")
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void init() {
        if (getHasTitleImg() == null) {
            setHasTitleImg(false);
        }
        if (getHasContentImg() == null) {
            setHasContentImg(false);
        }
        if (getTitleImgWidth() == null) {
            setTitleImgWidth(139);
        }
        if (getTitleImgHeight() == null) {
            setTitleImgHeight(139);
        }
        if (getContentImgWidth() == null) {
            setContentImgWidth(310);
        }
        if (getContentImgHeight() == null) {
            setContentImgHeight(310);
        }
        if (getIsBlank() == null) {
            setIsBlank(false);
        }
        if (getCommentControl() == null) {
            setCommentControl(0);
        }
        if (getAllowUpdown() == null) {
            setAllowUpdown(true);
        }
        if (getIsStaticChannel() == null) {
            setIsStaticChannel(false);
        }
        if (getIsStaticContent() == null) {
            setIsStaticContent(false);
        }
        if (getIsAccessByDir() == null) {
            setIsAccessByDir(true);
        }
        if (getIsListChild() == null) {
            setIsListChild(false);
        }
        if (getPageSize() == null) {
            setPageSize(20);
        }
        if(getAllowShare()==null){
            setAllowShare(false);
        }
        if(getAllowScore()==null){
            setAllowScore(false);
        }
        blankToNull();
    }
    public void blankToNull() {
        if (StringUtils.isBlank(getLink())) {
            setLink(null);
        }
        if (StringUtils.isBlank(getTplChannel())) {
            setTplChannel(null);
        }
        if (StringUtils.isBlank(getTplContent())) {
            setTplContent(null);
        }
        if (StringUtils.isBlank(getTitle())) {
            setTitle(null);
        }
        if (StringUtils.isBlank(getKeywords())) {
            setKeywords(null);
        }
        if (StringUtils.isBlank(getDescription())) {
            setDescription(null);
        }
        if (StringUtils.isBlank(getTitleImg())) {
            setTitleImg(null);
        }
        if (StringUtils.isBlank(getContentImg())) {
            setContentImg(null);
        }
        if (StringUtils.isBlank(getChannelRule())) {
            setChannelRule(null);
        }
        if (StringUtils.isBlank(getContentRule())) {
            setContentRule(null);
        }
    }
}
