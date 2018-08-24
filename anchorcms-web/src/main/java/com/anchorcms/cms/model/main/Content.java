package com.anchorcms.cms.model.main;

import com.anchorcms.cms.model.assist.CmsComment;
import com.anchorcms.cms.web.CmsThreadVariable;
import com.anchorcms.common.utils.DateUtils;
import com.anchorcms.common.utils.StaticPageUtils;
import com.anchorcms.core.model.CmsGroup;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.metamodel.binding.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.anchorcms.cms.model.main.ContentTxt.PAGE_START;
import static com.anchorcms.common.constants.Constants.SPT;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc CMS内容表
 */
@Entity
@Table(name = "c_content")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class Content implements Serializable{
    private static final long serialVersionUID = 6895381296604577589L;
    private Integer contentId;
    private Date sortDate;
    private Byte topLevel;
    private Boolean hasTitleImg;
    private Boolean isRecommend;
    private Byte status;
    private Integer viewsDay;
    private Short commentsDay;
    private Short downloadsDay;
    private Short upsDay;
    private Integer score;
    private Byte recommendLevel;






    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    @Basic
    @Column(name = "sort_date")
    public Date getSortDate() {
        return sortDate;
    }

    public void setSortDate(Date sortDate) {
        this.sortDate = sortDate;
    }

    @Basic
    @Column(name = "top_level")
    public Byte getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(Byte topLevel) {
        this.topLevel = topLevel;
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
    @Column(name = "is_recommend")
    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
    @Column(name = "comments_day")
    public Short getCommentsDay() {
        return commentsDay;
    }

    public void setCommentsDay(Short commentsDay) {
        this.commentsDay = commentsDay;
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
    @Column(name = "ups_day")
    public Short getUpsDay() {
        return upsDay;
    }

    public void setUpsDay(Short upsDay) {
        this.upsDay = upsDay;
    }

    @Basic
    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "recommend_level")
    public Byte getRecommendLevel() {
        return recommendLevel;
    }

    public void setRecommendLevel(Byte recommendLevel) {
        this.recommendLevel = recommendLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Content content = (Content) o;

        if (contentId != content.contentId) return false;
        if (topLevel != content.topLevel) return false;
        if (hasTitleImg != content.hasTitleImg) return false;
        if (isRecommend != content.isRecommend) return false;
        if (status != content.status) return false;
        if (viewsDay != content.viewsDay) return false;
        if (commentsDay != content.commentsDay) return false;
        if (downloadsDay != content.downloadsDay) return false;
        if (upsDay != content.upsDay) return false;
        if (score != content.score) return false;
        if (recommendLevel != content.recommendLevel) return false;
        if (sortDate != null ? !sortDate.equals(content.sortDate) : content.sortDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contentId;
        result = 31 * result + (sortDate != null ? sortDate.hashCode() : 0);
        result = 31 * result + (int) topLevel;
        result = 31 * result + (int) status;
        result = 31 * result + viewsDay;
        result = 31 * result + (int) commentsDay;
        result = 31 * result + (int) downloadsDay;
        result = 31 * result + (int) upsDay;
        result = 31 * result + score;
        result = 31 * result + (int) recommendLevel;
        return result;
    }


    private Set<Channel> channels;

    private Set<CmsTopic> topics;

    private Set<ContentCheck> contentCheckSet;

    private Set<CmsGroup> viewGroups;

    private List<ContentTag> tags;

    private List<ContentAttachment> attachments;

    private List<ContentPicture> pictures;

    private Channel channel;

    private ContentType type;

    private CmsUser user;

    private CmsSite site;

    private Map<String,String> attr;

    private Set<CmsUser> collectUsers;

    private ContentExt contentExt;

    private Set<CmsFile> files;

    private Set<ContentTxt> contentTxtSet;

    private ContentCount contentCount;

    private CmsModel model;

    private Set<ContentCharge> contentChargeSet;

    private Set<CmsComment> comments;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="content_id",insertable = true,updatable = true)
    public Set<CmsComment> getComments() {
        return comments;
    }

    public void setComments(Set<CmsComment> comments) {
        this.comments = comments;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "content",orphanRemoval = true)
    public Set<ContentCharge> getContentChargeSet() {
        return contentChargeSet;
    }

    public void setContentChargeSet(Set<ContentCharge> contentChargeSet) {
        this.contentChargeSet = contentChargeSet;
    }
    @ManyToOne
    @JoinColumn(name="model_id",insertable = true,updatable = true)
    public CmsModel getModel() {
        return model;
    }

    public void setModel(CmsModel model) {
        this.model = model;
    }
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="content_id")
    public ContentCount getContentCount() {
        return contentCount;
    }

    public void setContentCount(ContentCount contentCount) {
        this.contentCount = contentCount;
    }
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "content",orphanRemoval = true,fetch = FetchType.EAGER)
    public Set<ContentTxt> getContentTxtSet() {
        return contentTxtSet;
    }

    public void setContentTxtSet(Set<ContentTxt> contentTxtSet) {
        this.contentTxtSet = contentTxtSet;
    }



    private Set<ContentRecord> contentRecord;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "content",orphanRemoval = true)
    public Set<ContentRecord> getContentRecord() {
        return contentRecord;
    }
    public void setContentRecord(Set<ContentRecord> contentRecord) {
        this.contentRecord = contentRecord;
    }


    @OneToMany
    @JoinColumn(name="content_id")
    public Set<CmsFile> getFiles() {
        return files;
    }



    public void setFiles(Set<CmsFile> files) {
        this.files = files;
    }
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="content_id")
    public ContentExt getContentExt() {
        return contentExt;
    }

    public void setContentExt(ContentExt contentExt) {
        this.contentExt = contentExt;
    }
    @ManyToMany
    @JoinTable(name = "c_user_collection",
            joinColumns = {@JoinColumn(name = "content_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    public Set<CmsUser> getCollectUsers() {
        return collectUsers;
    }

    public void setCollectUsers(Set<CmsUser> collectUsers) {
        this.collectUsers = collectUsers;
    }

    @ElementCollection
    @CollectionTable(name = "c_content_attr",joinColumns={ @JoinColumn(nullable=false, name="content_id")})
    @MapKeyColumn(name="attr_name")//指定map的key生成的列
    @Column(name ="attr_value")
    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
    @ManyToOne
    @JoinColumn(name="site_id",insertable = true,updatable = true)
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
    @ManyToOne
    @JoinColumn(name="user_id",insertable = true,updatable = true)
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
    @ManyToOne
    @JoinColumn(name="type_id",insertable = true,updatable = true)
    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }
    @ManyToOne
    @JoinColumn(name="channel_id",insertable = true,updatable = true)
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
 //   @OneToMany
   // @JoinColumn(name="content_id",insertable = false,updatable = false)


    @ElementCollection
    @CollectionTable(name="c_content_picture", joinColumns=@JoinColumn(name="content_id"))
    @OrderColumn(name = "priority")
    public List<ContentPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<ContentPicture> pictures) {
        this.pictures = pictures;
    }


    @ElementCollection
    @CollectionTable(name="c_content_attachment", joinColumns=@JoinColumn(name="content_id"))
    @OrderColumn(name = "priority")
    public List<ContentAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ContentAttachment> attachments) {
        this.attachments = attachments;
    }
    @ManyToMany
    @JoinTable(name = "c_contenttag",
            joinColumns = {@JoinColumn(name = "content_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @OrderColumn(name = "priority")
    public List<ContentTag> getTags() {
        return tags;
    }

    public void setTags(List<ContentTag> tags) {
        this.tags = tags;
    }
    @ManyToMany
    @JoinTable(name = "c_content_group_view",
            joinColumns = {@JoinColumn(name = "content_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    public Set<CmsGroup> getViewGroups() {
        return viewGroups;
    }

    public void setViewGroups(Set<CmsGroup> viewGroups) {
        this.viewGroups = viewGroups;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "content",orphanRemoval = true)
    public Set<ContentCheck> getContentCheckSet() {
        return contentCheckSet;
    }

    public void setContentCheckSet(Set<ContentCheck> contentCheckSet) {
        this.contentCheckSet = contentCheckSet;
    }
    @ManyToMany
    @JoinTable(name = "c_content_topic",
            joinColumns = {@JoinColumn(name = "content_id")},
            inverseJoinColumns = {@JoinColumn(name = "topic_id")})
    public Set<CmsTopic> getTopics() {
        return topics;
    }

    public void setTopics(Set<CmsTopic> topics) {
        this.topics = topics;
    }

    @ManyToMany
    @JoinTable(name = "c_content_channel",
            joinColumns = {@JoinColumn(name = "content_id")},
            inverseJoinColumns = {@JoinColumn(name = "channel_id")})
    public Set<Channel> getChannels() {
        return channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }
    private DateFormat df = new SimpleDateFormat("/yyyyMMdd");

    public void init() {
        short zero = 0;
        byte bzero = 0;
        if (getViewsDay() == null) {
            setViewsDay(0);
        }
        if (getCommentsDay() == null) {
            setCommentsDay(zero);
        }
        if (getDownloadsDay() == null) {
            setDownloadsDay(zero);
        }
        if (getUpsDay() == null) {
            setUpsDay(zero);
        }
        if (getHasTitleImg() == null) {
            setHasTitleImg(false);
        }
        if (getIsRecommend() == null) {
            setIsRecommend(false);
        }
        if (getSortDate() == null) {
            setSortDate(new Timestamp(System.currentTimeMillis()));
        }
        if (getTopLevel() == null) {
            setTopLevel(bzero);
        }
        // 保存后立即生成静态化，如果这些值为null，则需要在模板中增加判断，使模板编写变得复杂。
        if (getChannels() == null) {
            setChannels(new HashSet<Channel>());
        }
        if (getTopics() == null) {
            setTopics(new HashSet<CmsTopic>());
        }
        if (getViewGroups() == null) {
            setViewGroups(new HashSet<CmsGroup>());
        }
        if (getTags() == null) {
            setTags(new ArrayList<ContentTag>());
        }
        if (getPictures() == null) {
            setPictures(new ArrayList<ContentPicture>());
        }
        if (getAttachments() == null) {
            setAttachments(new ArrayList<ContentAttachment>());
        }
        if(getScore()==null){
            setScore(0);
        }
        if(getRecommendLevel()==null){
            setRecommendLevel(bzero);
        }
    }

    /**
     * 状态
     */
    public enum ContentStatus {
        /**
         * 所有
         */
        all,
        /**
         * 草稿
         */
        draft,
        /**
         * 待审核
         */
        prepared,
        /**
         * 已审
         */
        passed,
        /**
         * 终审
         */
        checked,
        /**
         * 退回
         */
        rejected,
        /**
         * 回收
         */
        recycle,
        /**
         * 投稿
         */
        contribute,
        /**
         * 归档
         */
        pigeonhole
    };
    public void addToChannels(Channel channel) {
        Set<Channel> channels = getChannels();
        if (channels == null) {
            channels = new HashSet<Channel>();
            setChannels(channels);
        }
        channels.add(channel);
    }
    public void addToTopics(CmsTopic topic) {
        Set<CmsTopic> topics = getTopics();
        if (topics == null) {
            topics = new HashSet<CmsTopic>();
            setTopics(topics);
        }
        topics.add(topic);
    }
    @Transient
    public ContentCheck getContentCheck() {
        Set<ContentCheck> set = getContentCheckSet();
        if (set != null && set.size() > 0) {
            return set.iterator().next();
        } else {
            return null;
        }
    }
    public void addToGroups(CmsGroup group) {
        Set<CmsGroup> groups = getViewGroups();
        if (groups == null) {
            groups = new HashSet<CmsGroup>();
            setViewGroups(groups);
        }
        groups.add(group);
    }
    public void addToAttachmemts(String path, String name, String filename) {
        List<ContentAttachment> list = getAttachments();
        if (list == null) {
            list = new ArrayList<ContentAttachment>();
            setAttachments(list);
        }
        ContentAttachment ca = new ContentAttachment(path, name, 0);
        if (!StringUtils.isBlank(filename)) {
            ca.setFilename(filename);
        }
        list.add(ca);
    }
    public void addToPictures(String path, String desc) {
        List<ContentPicture> list = getPictures();
        if (list == null) {
            list = new ArrayList<ContentPicture>();
            setPictures(list);
        }
        ContentPicture cp = new ContentPicture();
        cp.setImgPath(path);
        cp.setDescription(desc);
        list.add(cp);
    }
    @Transient
    public Byte getCheckStep() {
        ContentCheck check = getContentCheck();
        if (check != null) {
            return check.getCheckStep();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getRejected() {
        ContentCheck check = getContentCheck();
        if (check != null) {
            return check.getIsRejected();
        } else {
            return null;
        }
    }
    @Transient
    public void clear(){
        getCollectUsers().clear();
    }
    @Transient
    public String getTitle() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getTitle();
        } else {
            return null;
        }
    }
    @Transient
    public String getShortTitle() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getShortTitle();
        } else {
            return null;
        }
    }
    @Transient
    public String getTitleColor() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getTitleColor();
        } else {
            return null;
        }
    }
    @Transient
    public String getTagStr() {
        List<ContentTag> tags = getTags();
        if (tags != null && tags.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (ContentTag tag : tags) {
                if(tag!=null&&tag.getTagName()!=null){
                    sb.append(tag.getTagName()).append(',');
                }
            }
            return sb.substring(0, sb.length() - 1);
        } else {
            return null;
        }
    }
    public void setContentTxt(ContentTxt txt) {
        Set<ContentTxt> set = getContentTxtSet();
        if (set == null) {
            set = new HashSet<ContentTxt>();
            setContentTxtSet(set);
        }
        if (!set.isEmpty()) {
            set.clear();
        }
        set.add(txt);
    }
    public void setContentCheck(ContentCheck check) {
        Set<ContentCheck> set = getContentCheckSet();
        if (set == null) {
            set = new HashSet<ContentCheck>();
            setContentCheckSet(set);
        }
        if (!set.isEmpty()) {
            set.clear();
        }
        set.add(check);
    }
    @Transient
    public Set<Channel> getChannelsWithoutMain() {
        Set<Channel> set = new HashSet<Channel>(getChannels());
        set.remove(getChannel());
        return set;
    }
    @Transient
    public int getPageCount() {
        int txtCount = getTxtCount();
        return txtCount;
    }
    @Transient
    public int getTxtCount() {
        ContentTxt txt = getContentTxt();
        if (txt != null) {
            return txt.getTxtCount();
        } else {
            return 1;
        }
    }
    @Transient
    public ContentTxt getContentTxt() {
        Set<ContentTxt> set = getContentTxtSet();
        if (set != null && set.size() > 0) {
            return set.iterator().next();
        } else {
            return null;
        }
    }
    @Transient
    public String getStaticFilename(int pageNo) {
        CmsSite site = getSite();
        StringBuilder url = new StringBuilder();
        String staticDir = site.getStaticDir();
        if (!StringUtils.isBlank(staticDir)) {
            url.append(staticDir);
        }
        String filename = getStaticFilenameByRule();
        if (!StringUtils.isBlank(filename)) {
            int index = filename.indexOf(".", filename.lastIndexOf("/"));
            if (pageNo > 1) {
                if (index != -1) {
                    url.append(filename.substring(0, index));
                    url.append("_").append(pageNo);
                    url.append(filename.substring(index));
                } else {
                    url.append(filename).append("_").append(pageNo);
                }
            } else {
                url.append(filename);
            }
        } else {
            // 默认静态路径
            url.append(SPT).append(getChannel().getChannelPath());
            url.append(df.format(getReleaseDate()));
            url.append(SPT).append(getContentId());
            if (pageNo > 1) {
                url.append("_").append(pageNo);
            }
            url.append(site.getStaticSuffix());
        }
        return url.toString();
    }
    @Transient
    public String getStaticFilenameByRule() {
        Channel channel = getChannel();
        CmsModel model = channel.getModel();
        String rule = channel.getContentRule();
        if (StringUtils.isBlank(rule)) {
            return null;
        }
        String url = StaticPageUtils.staticUrlRule(rule, model.getModelId(), model.getModelPath(), channel.getChannelId(), channel.getChannelPath(), getContentId(),
                getReleaseDate());
        return url;
    }
    @Transient
    public Date getReleaseDate() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getReleaseDate();
        } else {
            return null;
        }
    }
    //获取手机静态页面文件名
    public String getMobileStaticFilename(int pageNo) {
        CmsSite site = getSite();
        StringBuilder url = new StringBuilder();
        String staticDir = site.getMobileSolutionPath();
        if (!StringUtils.isBlank(staticDir)) {
            url.append(staticDir);
        }
        String filename = getStaticFilenameByRule();
        if (!StringUtils.isBlank(filename)) {
            int index = filename.indexOf(".", filename.lastIndexOf("/"));
            if (pageNo > 1) {
                if (index != -1) {
                    url.append(filename.substring(0, index));
                    url.append("_").append(pageNo);
                    url.append(filename.substring(index));
                } else {
                    url.append(filename).append("_").append(pageNo);
                }
            } else {
                url.append(filename);
            }
        } else {
            // 默认静态路径
            url.append(SPT).append(getChannel().getChannelPath());
            url.append(df.format(getReleaseDate()));
            url.append(SPT).append(getContentId());
            if (pageNo > 1) {
                url.append("_").append(pageNo);
            }
            url.append(site.getStaticSuffix());
        }
        return url.toString();
    }
    @Transient
    public String getLink() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getLink();
        } else {
            return null;
        }
    }
    public String getTplContentOrDef(CmsModel model) {
        String tpl = getTplContent();
        if (!StringUtils.isBlank(tpl)) {
            return tpl;
        } else {
            return getChannel().getTplContentOrDef(model);
        }
    }
    @Transient
    public String getTplContent() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getTplContent();
        } else {
            return null;
        }
    }
    public String getMobileTplContentOrDef(CmsModel model) {
        String tpl = getMobileTplContent();
        if (!StringUtils.isBlank(tpl)) {
            return tpl;
        } else {
            return getChannel().getMobileTplContentOrDef(model);
        }
    }
    @Transient
    public String getMobileTplContent() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getTplMobileContent();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getNeedRegenerate() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getNeedRegenerate();
        } else {
            return null;
        }
    }
    @Transient
    public void setNeedRegenerate(Boolean isNeed) {
        ContentExt ext = getContentExt();
        if (ext != null) {
            ext.setNeedRegenerate(isNeed);
        }
    }
    public String getTxtByNo(int pageNo) {
        ContentTxt txt = getContentTxt();
        if (txt != null) {
            return txt.getTxtByNo(pageNo);
        } else {
            return null;
        }
    }
    public String getUrlStatic(int pageNo) {
        return getUrlStatic(null, pageNo);
    }
    public String getUrlStatic(Boolean whole, int pageNo) {
        if (!StringUtils.isBlank(getLink())) {
            return getLink();
        }
        CmsSite site = getSite();
        StringBuilder url = site.getUrlBuffer(false, whole, false);
        String filename = getStaticFilenameByRule();
        if (!StringUtils.isBlank(filename)) {
            if (pageNo > 1) {
                int index = filename.indexOf(".", filename.lastIndexOf("/"));
                if (index != -1) {
                    url.append(filename.subSequence(0, index)).append("_");
                    url.append(pageNo).append(
                            filename.subSequence(index, filename.length()));
                } else {
                    url.append(filename).append("_").append(pageNo);
                }
            } else {
                url.append(filename);
            }
        } else {
            // 默认静态路径
            url.append(SPT).append(getChannel().getChannelPath());
            url.append(df.format(getReleaseDate()));
            url.append(SPT).append(getContentId());
            if (pageNo > 1) {
                url.append("_").append(pageNo);
            }
            url.append(site.getStaticSuffix());

        }
        return url.toString();
    }
    public String getTitleByNo(int pageNo) {
        ContentTxt txt = getContentTxt();
        if (txt != null) {
            return txt.getTitleByNo(pageNo);
        } else {
            return getTitle();
        }
    }
    public ContentPicture getPictureByNo(int pageNo) {
        List<ContentPicture> list = getPictures();
        if (pageNo >= 1 && list != null && list.size() >= pageNo) {
            return list.get(pageNo - 1);
        } else {
            return null;
        }
    }
    /**
     * 是否终审通过
     *
     * @return
     */
    @Transient
    public boolean isChecked() {
        return ContentCheck.CHECKED == getStatus();
    }
    @Transient
    public ContentCharge getContentCharge() {
        Set<ContentCharge> set = getContentChargeSet();
        if (set != null && set.size() > 0) {
            return set.iterator().next();
        } else {
            return null;
        }
    }
    @Transient
    public Date getLastBuyTime() {
        ContentCharge charge= getContentCharge();
        if(charge!=null){
            return charge.getLastBuyTime();
        }else{
            return null;
        }
    }
    public void setContentCharge(ContentCharge charge) {
        Set<ContentCharge> set = getContentChargeSet();
        if (set == null) {
            set = new HashSet<ContentCharge>();
            setContentChargeSet(set);
        }
        if (!set.isEmpty()) {
            set.clear();
        }
        set.add(charge);
    }
    @Transient
    public Set<CmsGroup> getViewGroupsExt() {
        Set<CmsGroup> set = getViewGroups();
        if (set != null && set.size() > 0) {
            return set;
        } else {
            return getChannel().getViewGroups();
        }
    }
    @Transient
    public boolean getCharge() {
        ContentCharge c=getContentCharge();
        return c!=null&&c.getChargeAmount()>0&&c.getChargeReward().equals(ContentCharge.MODEL_CHARGE);
    }
    @Transient
    public String getTxt() {
        ContentTxt txt = getContentTxt();
        if (txt != null) {
            return txt.getTxt();
        } else {
            return null;
        }
    }
    @Transient
    public String getTxt1() {
        ContentTxt txt = getContentTxt();
        if (txt != null) {
            return txt.getTxt1();
        } else {
            return null;
        }
    }
    @Transient
    public String getTxt2() {
        ContentTxt txt = getContentTxt();
        if (txt != null) {
            return txt.getTxt2();
        } else {
            return null;
        }
    }
    @Transient
    public String getTxt3() {
        ContentTxt txt = getContentTxt();
        if (txt != null) {
            return txt.getTxt3();
        } else {
            return null;
        }
    }
    @Transient
    public void removeSelfAddToChannels(Channel channel) {
        Set<Channel> channels = getChannels();
        if (channels == null) {
            channels = new HashSet<Channel>();
            setChannels(channels);
        }
        channels.remove(getChannel());
        channels.add(channel);
    }
    @Transient
    public Content cloneWithoutSet() {
        Content content = new Content();
        content.setSortDate(getSortDate());
        content.setTopLevel(getTopLevel());
        content.setHasTitleImg(getHasTitleImg());
        content.setIsRecommend(getIsRecommend());
        content.setStatus(getStatus());
        content.setViewsDay(getViewsDay());
        content.setCommentsDay(getCommentsDay());
        content.setDownloadsDay(getDownloadsDay());
        content.setUpsDay(getUpsDay());
        content.setType(getType());
        content.setSite(getSite());
        content.setUser(getUser());
        content.setChannel(getChannel());
        content.setModel(getModel());
        Map<String,String>attrs=getAttr();
        if(attrs!=null&&!attrs.isEmpty()){
            Map<String,String>newAttrs=new HashMap<String, String>();
            String key;
            Set<String>keyset=attrs.keySet();
            Iterator<String>keyIt=keyset.iterator();
            while(keyIt.hasNext()){
                key=keyIt.next();
                newAttrs.put(key, attrs.get(key));
            }
            content.setAttr(newAttrs);
        }
        content.setContentExt(getContentExt());
        return content;
    }
    @Transient
    public Integer[]getTopicIds(){
        Set<CmsTopic>topics=getTopics();
        return CmsTopic.fetchIds(topics);
    }

    @Transient
    public Integer[]getViewGroupIds(){
        Set<CmsGroup>groups =getViewGroups();
        return CmsGroup.fetchIds(groups);
    }

    @Transient
    public String[]getAttachmentPaths(){
        List<ContentAttachment>attList=getAttachments();
        if(attList==null||attList.size()<=0){
            return null;
        }
        String[]attachmentPaths=new String[attList.size()];
        for(int i=0;i<attachmentPaths.length;i++){
            attachmentPaths[i]=attList.get(i).getAttachmentPath();
        }
        return attachmentPaths;
    }

    @Transient
    public String[]getAttachmentNames(){
        List<ContentAttachment>attList=getAttachments();
        if(attList==null||attList.size()<=0){
            return null;
        }
        String[]attachmentNames=new String[attList.size()];
        for(int i=0;i<attachmentNames.length;i++){
            attachmentNames[i]=attList.get(i).getAttachmentName();
        }
        return attachmentNames;
    }

    @Transient
    public String[]getAttachmentFileNames(){
        List<ContentAttachment>attList=getAttachments();
        if(attList==null||attList.size()<=0){
            return null;
        }
        String[]attachmentFileNames=new String[attList.size()];
        for(int i=0;i<attachmentFileNames.length;i++){
            attachmentFileNames[i]=attList.get(i).getFilename();
        }
        return attachmentFileNames;
    }

    @Transient
    public String[]getPicPaths(){
        List<ContentPicture>pics=getPictures();
        if(pics==null||pics.size()<=0){
            return null;
        }
        String[]picPaths=new String[pics.size()];
        for(int i=0;i<picPaths.length;i++){
            picPaths[i]=pics.get(i).getImgPath();
        }
        return picPaths;
    }

    @Transient
    public String[]getPicDescs(){
        List<ContentPicture>pics=getPictures();
        if(pics==null||pics.size()<=0){
            return null;
        }
        String[]picDescs=new String[pics.size()];
        for(int i=0;i<picDescs.length;i++){
            picDescs[i]=pics.get(i).getDescription();
        }
        return picDescs;
    }

    @Transient
    public String[]getTagArray(){
        List<ContentTag>tags=getTags();
        if(tags==null||tags.size()<=0){
            return null;
        }
        String[]tagArrar=new String[tags.size()];
        for(int i=0;i<tagArrar.length;i++){
            tagArrar[i]=tags.get(i).getTagName();
        }
        return tagArrar;
    }


    @Transient
    public Short getChargeModel() {
        ContentCharge c=getContentCharge();
        if(c==null){
            return ContentCharge.MODEL_FREE;
        }else{
            return c.getChargeReward();
        }
    }
    @Transient
    public Double getChargeAmount() {
        ContentCharge charge= getContentCharge();
        if(charge!=null){
            return charge.getChargeAmount();
        }else{
            return 0d;
        }
    }
    /**
     * 是否有审核后的编辑权限。从CmsThread中获得当前用户。
     *
     * @return
     */
    @Transient
    public boolean isHasUpdateRight() {
        CmsUser user = CmsThreadVariable.getUser();
        if (user == null) {
            throw new IllegalStateException("CmsUser not found in CmsThread");
        }
        return isHasUpdateRight(user);
    }
    /**
     * 是否有审核后的编辑权限
     *
     * @param user
     * @return
     */
    @Transient
    public boolean isHasUpdateRight(CmsUser user) {
        Channel.AfterCheckEnum after = getChannel().getAfterCheckEnum();
        if (Channel.AfterCheckEnum.CANNOT_UPDATE == after) {
            CmsSite site = getSite();
            Byte userStep = user.getCheckStep(site.getSiteId()
            );
            Byte channelStep = getChannel().getFinalStepExtends();
            boolean checked = getStatus() == ContentCheck.CHECKED;
            // 如果内容审核级别大于用户审核级别，或者内容已经审核且用户审核级别小于栏目审核级别。
            if (getCheckStep() > userStep
                    || (checked && userStep < channelStep)) {
                return false;
            } else {
                return true;
            }
        } else if (Channel.AfterCheckEnum.BACK_UPDATE == after
                || Channel.AfterCheckEnum.KEEP_UPDATE == after) {
            return true;
        } else {
            throw new RuntimeException("AfterCheckEnum '" + after
                    + "' did not handled");
        }
    }

    /**
     * 是否有审核后的删除权限。从CmsThread中获得当前用户。
     *
     * @return
     */
    @Transient
    public boolean isHasDeleteRight() {
        CmsUser user = CmsThreadVariable.getUser();
        if (user == null) {
            throw new IllegalStateException("CmsUser not found in CmsThread");
        }
        return isHasDeleteRight(user);
    }

    /**
     * 是否有审核后的删除权限
     *
     * @param user
     * @return
     */
    @Transient
    public boolean isHasDeleteRight(CmsUser user) {
        Channel.AfterCheckEnum after = getChannel().getAfterCheckEnum();
        if (Channel.AfterCheckEnum.CANNOT_UPDATE == after) {
            CmsSite site = getSite();
            Byte userStep = user.getCheckStep(site.getSiteId());
            Byte channelStep = getChannel().getFinalStepExtends();
            boolean checked = getStatus() == ContentCheck.CHECKED;
            // 如果内容审核级别大于用户审核级别，或者内容已经审核且用户审核级别小于栏目审核级别。
            if (getCheckStep() > userStep
                    || (checked && userStep < channelStep)) {
                return false;
            } else {
                return true;
            }
        } else if (Channel.AfterCheckEnum.BACK_UPDATE == after
                || Channel.AfterCheckEnum.KEEP_UPDATE == after) {
            return true;
        } else {
            throw new RuntimeException("AfterCheckEnum '" + after
                    + "' did not handled");
        }
    }


    /**
     * 获得URL地址
     *
     * @return
     */
    @Transient
    public String getUrl() {
        if (!StringUtils.isBlank(getLink())) {
            return getLink();
        }
        if (getStaticContent()) {
            return getUrlStatic(false, 1);
        } else if(!StringUtils.isBlank(getSite().getDomainAlias())){
            return getUrlDynamic(null);
        }else{
            return getUrlDynamic(true);
        }
    }

    @Transient
    public Boolean getStaticContent() {
        Channel channel = getChannel();
        if (channel != null) {
            return channel.getStaticContent();
        } else {
            return null;
        }
    }
    @Transient
    public String getUrlDynamic() {
        return getUrlDynamic(null);
    }
    @Transient
    public String getUrlDynamic(Boolean whole) {
        if (!StringUtils.isBlank(getLink())) {
            return getLink();
        }
        CmsSite site = getSite();
        StringBuilder url = site.getUrlBuffer(true, whole, false);
        url.append(SPT).append(getChannel().getChannelPath());
        url.append(SPT).append(getContentId()).append(site.getDynamicSuffix());
        return url.toString();
    }
    @Transient
    public Integer getViews() {
        ContentCount count = getContentCount();
        if (count != null) {
            return count.getViews();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getBold() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getIsBold();
        } else {
            return null;
        }
    }
    @Transient
    public boolean isDraft() {
        return ContentCheck.DRAFT == getStatus();
    }
    @Transient
    public String getDescription() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getDescription();
        } else {
            return null;
        }
    }
    @Transient
    public String getAuthor() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getAuthor();
        } else {
            return null;
        }
    }
    @Transient
    public String getOrigin() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getOrigin();
        } else {
            return null;
        }
    }
    @Transient
    public String getTypeImg() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getTypeImg();
        } else {
            return null;
        }
    }
    @Transient
    public String getTitleImg() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getTitleImg();
        } else {
            return null;
        }
    }
    @Transient
    public String getContentImg() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getContentImg();
        } else {
            return null;
        }
    }
    @Transient
    public String getOriginUrl() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getOriginUrl();
        } else {
            return null;
        }
    }
    @Transient
    public Date getPigeonholeDate() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getPigeonholeDate();
        } else {
            return null;
        }
    }

    @Transient
    public Integer getSiteId() {
        return getSite().getSiteId();
    }

    @Transient
    public Date getToplevelDate() {
        ContentExt ext = getContentExt();
        if (ext != null) {
            return ext.getToplevelDate();
        } else {
            return null;
        }
    }
    @Transient
    public Integer getDowns() {
        ContentCount count = getContentCount();
        if (count != null) {
            return count.getDowns();
        } else {
            return null;
        }
    }
    @Transient
    public Integer getUps() {
        ContentCount count = getContentCount();
        if (count != null) {
            return count.getUps();
        } else {
            return null;
        }
    }
    @Transient
    public Integer getCommentsCheckedNum() {
        Set<CmsComment> comments = getComments();
        int num=0;
        if (comments != null) {
            for(CmsComment comment:comments){
                if(comment.getIsChecked()){
                    num++;
                }
            }
            return num;
        } else {
            return 0;
        }
    }
    //顶级评论数
    @Transient
    public Integer getCommentsNum() {
        Set<CmsComment> comments = getComments();
        int num=0;
        if (comments != null) {
            for(CmsComment comment:comments){
                if(comment.getTopParentId() == null)
                num++;
            }
            return num;
        } else {
            return 0;
        }
    }
    @Transient
    public String getCtgName() {
        return getChannel().getName();
    }
    @Transient
    public String getCtgUrl() {
        return getChannel().getUrl();
    }
    public boolean hasCommentUser(CmsUser user){
        Set<CmsComment>comments=getComments();
        if(comments==null){
            return false;
        }
        Iterator<CmsComment>it=comments.iterator();
        while(it.hasNext()){
            CmsComment comment=it.next();
            if(comment.getCommentUser()!=null&&comment.getCommentUser().equals(user)){
                return true;
            }
        }
        return false;
    }
    /**
     * 2017/02/20 新增获取前台静态页URL --用户填写
     * @return
     */
    @Transient
    public String getMemberUrl() {
        return getMemberUrlStatic(false, 1);
    }

    @Transient
    public String getMemberUrlStatic(int pageNo) {
        return getMemberUrlStatic(null, pageNo);
    }

    @Transient
    public String getMemberUrlStatic(Boolean whole, int pageNo) {
        if (!StringUtils.isBlank(getLink())) {
            return getLink();
        }
        CmsSite site = getSite();
        StringBuilder url = site.getUrlBuffer(false, whole, false);
        // 默认静态路径
        url.append(SPT).append("member" + getChannel().getChannelPath());
        url.append(df.format(getReleaseDate()));
        url.append(SPT).append(getContentId());
        if (pageNo > 1) {
            url.append("_").append(pageNo);
        }
        url.append(site.getStaticSuffix());

        return url.toString();
    }

    @Transient
    public String getMemberStaticFilename(int pageNo) {
        CmsSite site = getSite();
        StringBuilder url = new StringBuilder();
        String staticDir = site.getStaticDir();
        if (!StringUtils.isBlank(staticDir)) {
            url.append(staticDir);
        }
        // 默认静态路径
        url.append(SPT).append("member" + getChannel().getChannelPath());
        url.append(df.format(getReleaseDate()));
        url.append(SPT).append(getContentId());
        if (pageNo > 1) {
            url.append("_").append(pageNo);
        }
        url.append(site.getStaticSuffix());
        return url.toString();
    }


}
