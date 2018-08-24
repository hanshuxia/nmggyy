package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-9
 * @Desc CMS投票主题
 */
@Entity
@Table(name = "c_vote_topic")
public class CmsVoteTopic implements Serializable{
    private static final long serialVersionUID = -1455077097445141581L;
    private int votetopicId;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private Integer repeateHour;
    private Integer totalCount;
    private Integer multiSelect;
    private Boolean isRestrictMember;
    private Boolean isRestrictIp;
    private Boolean isRestrictCookie;
    private Boolean isDisabled;
    private Boolean isDef;
    private Byte limitWeixin;
    private Integer voteDay;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "votetopic_id")
    public int getVotetopicId() {
        return votetopicId;
    }

    public void setVotetopicId(int votetopicId) {
        this.votetopicId = votetopicId;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "repeate_hour")
    public Integer getRepeateHour() {
        return repeateHour;
    }

    public void setRepeateHour(Integer repeateHour) {
        this.repeateHour = repeateHour;
    }

    @Basic
    @Column(name = "total_count")
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Basic
    @Column(name = "multi_select")
    public Integer getMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(Integer multiSelect) {
        this.multiSelect = multiSelect;
    }

    @Basic
    @Column(name = "is_restrict_member")
    public Boolean getIsRestrictMember() {
        return isRestrictMember;
    }

    public void setIsRestrictMember(Boolean isRestrictMember) {
        this.isRestrictMember = isRestrictMember;
    }

    @Basic
    @Column(name = "is_restrict_ip")
    public Boolean getIsRestrictIp() {
        return isRestrictIp;
    }

    public void setIsRestrictIp(Boolean isRestrictIp) {
        this.isRestrictIp = isRestrictIp;
    }

    @Basic
    @Column(name = "is_restrict_cookie")
    public Boolean getIsRestrictCookie() {
        return isRestrictCookie;
    }

    public void setIsRestrictCookie(Boolean isRestrictCookie) {
        this.isRestrictCookie = isRestrictCookie;
    }

    @Basic
    @Column(name = "is_disabled")
    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Basic
    @Column(name = "is_def")
    public Boolean getIsDef() {
        return isDef;
    }

    public void setIsDef(Boolean isDef) {
        this.isDef = isDef;
    }

    @Basic
    @Column(name = "limit_weixin")
    public Byte getLimitWeixin() {
        return limitWeixin;
    }

    public void setLimitWeixin(Byte limitWeixin) {
        this.limitWeixin = limitWeixin;
    }

    @Basic
    @Column(name = "vote_day")
    public Integer getVoteDay() {
        return voteDay;
    }

    public void setVoteDay(Integer voteDay) {
        this.voteDay = voteDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsVoteTopic that = (CmsVoteTopic) o;

        if (votetopicId != that.votetopicId) return false;
        if (totalCount != that.totalCount) return false;
        if (multiSelect != that.multiSelect) return false;
        if (isRestrictMember != that.isRestrictMember) return false;
        if (isRestrictIp != that.isRestrictIp) return false;
        if (isRestrictCookie != that.isRestrictCookie) return false;
        if (isDisabled != that.isDisabled) return false;
        if (isDef != that.isDef) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (repeateHour != null ? !repeateHour.equals(that.repeateHour) : that.repeateHour != null) return false;
        if (limitWeixin != null ? !limitWeixin.equals(that.limitWeixin) : that.limitWeixin != null) return false;
        if (voteDay != null ? !voteDay.equals(that.voteDay) : that.voteDay != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = votetopicId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (repeateHour != null ? repeateHour.hashCode() : 0);
        result = 31 * result + totalCount;
        result = 31 * result + multiSelect;
        result = 31 * result + (limitWeixin != null ? limitWeixin.hashCode() : 0);
        result = 31 * result + (voteDay != null ? voteDay.hashCode() : 0);
        return result;
    }

    private Set<CmsVoteItem> items;

    private Set<CmsVoteSubTopic> subtopics;

    @OneToMany(mappedBy = "voteTopic",orphanRemoval = true)
    public Set<CmsVoteSubTopic> getSubtopics() {
        return subtopics;
    }

    public void setSubtopics(Set<CmsVoteSubTopic> subtopics) {
        this.subtopics = subtopics;
    }
    @OneToMany(mappedBy = "topic",orphanRemoval = true)
    public Set<CmsVoteItem> getItems() {
        return items;
    }

    public void setItems(Set<CmsVoteItem> items) {
        this.items = items;
    }


    private CmsSite site;

    @ManyToOne
    @JoinColumn(name = "site_id")
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }




    public static String REF = "CmsVoteTopic";
    public static String PROP_MULTI_SELECT = "multiSelect";
    public static String PROP_RESTRICT_COOKIE = "restrictCookie";
    public static String PROP_SITE = "site";
    public static String PROP_DISABLED = "disabled";
    public static String PROP_DEF = "def";
    public static String PROP_RESTRICT_MEMBER = "restrictMember";
    public static String PROP_RESTRICT_IP = "restrictIp";
    public static String PROP_TOTAL_COUNT = "totalCount";
    public static String PROP_REPEATE_HOUR = "repeateHour";
    public static String PROP_END_TIME = "endTime";
    public static String PROP_START_TIME = "startTime";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_TITLE = "title";
    public static String PROP_ID = "id";
    public void init() {
        if (getTotalCount() == null) {
            setTotalCount(0);
        }
        if (getMultiSelect() == null) {
            setMultiSelect(1);
        }
        if (getIsDef() == null) {
            setIsDef(false);
        }
        if (getIsDisabled() == null) {
            setIsDisabled(false);
        }
        if (getIsRestrictMember() == null) {
            setIsRestrictMember(false);
        }
        if (getIsRestrictIp() == null) {
            setIsRestrictIp(false);
        }
        if (getIsRestrictCookie() == null) {
            setIsRestrictCookie(true);
        }
    }

}
