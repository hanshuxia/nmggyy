package com.anchorcms.cms.model.assist;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-9
 * @Desc CMS投票选项表
 */
@Entity
@Table(name = "c_vote_item")
public class CmsVoteItem implements Serializable{
    private static final long serialVersionUID = -7791001304876501162L;
    private int voteitemId;
    private String title;
    private Integer voteCount;
    private Integer priority;
    private String picture;

    @Id
    @Column(name = "voteitem_id")
    public int getVoteitemId() {
        return voteitemId;
    }

    public void setVoteitemId(int voteitemId) {
        this.voteitemId = voteitemId;
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
    @Column(name = "vote_count")
    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Basic
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "picture")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsVoteItem cmsVoteItem = (CmsVoteItem) o;

        if (voteitemId != cmsVoteItem.voteitemId) return false;

        if (voteCount != cmsVoteItem.voteCount) return false;
        if (priority != cmsVoteItem.priority) return false;
        if (title != null ? !title.equals(cmsVoteItem.title) : cmsVoteItem.title != null) return false;

        if (picture != null ? !picture.equals(cmsVoteItem.picture) : cmsVoteItem.picture != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = voteitemId;

        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + voteCount;
        result = 31 * result + priority;

        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        return result;
    }

    private CmsVoteSubTopic subTopic;

    private CmsVoteTopic topic;
    @ManyToOne
    @JoinColumn(name = "votetopic_id")
    public CmsVoteTopic getTopic() {
        return topic;
    }

    public void setTopic(CmsVoteTopic topic) {
        this.topic = topic;
    }
    @ManyToOne
    @JoinColumn(name = "subtopic_id")
    public CmsVoteSubTopic getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(CmsVoteSubTopic subTopic) {
        this.subTopic = subTopic;
    }
    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
        if (getVoteCount() == null) {
            setVoteCount(0);
        }
    }
}
