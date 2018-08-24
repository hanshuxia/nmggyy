package com.anchorcms.cms.model.assist;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-9
 * @Desc CMS投票文本回复表
 */
@Entity
@Table(name = "c_vote_reply")
public class CmsVoteReply implements Serializable{
    private static final long serialVersionUID = 8467906464623724198L;
    private int votereplyId;
    private String reply;
    private Integer subtopicId;

    @Id
    @Column(name = "votereply_id")
    public int getVotereplyId() {
        return votereplyId;
    }

    public void setVotereplyId(int votereplyId) {
        this.votereplyId = votereplyId;
    }

    @Basic
    @Column(name = "reply")
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Basic
    @Column(name = "subtopic_id")
    public Integer getSubtopicId() {
        return subtopicId;
    }

    public void setSubtopicId(Integer subtopicId) {
        this.subtopicId = subtopicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsVoteReply that = (CmsVoteReply) o;

        if (votereplyId != that.votereplyId) return false;
        if (reply != null ? !reply.equals(that.reply) : that.reply != null) return false;
        if (subtopicId != null ? !subtopicId.equals(that.subtopicId) : that.subtopicId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = votereplyId;
        result = 31 * result + (reply != null ? reply.hashCode() : 0);
        result = 31 * result + (subtopicId != null ? subtopicId.hashCode() : 0);
        return result;
    }

    private CmsVoteSubTopic subTopic;
    @ManyToOne
    @JoinColumn(name = "subtopic_id",insertable = false,updatable = false)
    public CmsVoteSubTopic getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(CmsVoteSubTopic subTopic) {
        this.subTopic = subTopic;
    }
}
