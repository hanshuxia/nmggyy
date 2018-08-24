package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-9
 * @Desc CMS投票记录表
 */
@Entity
@Table(name = "c_vote_record")
public class CmsVoteRecord implements Serializable{
    private static final long serialVersionUID = 6146953192804439912L;
    private int voterecoredId;
    private Integer userId;
    private int votetopicId;
    private Date voteTime;
    private String voteIp;
    private String voteCookie;
    private String wxOpenId;

    @Id
    @Column(name = "voterecored_id")
    public int getVoterecoredId() {
        return voterecoredId;
    }

    public void setVoterecoredId(int voterecoredId) {
        this.voterecoredId = voterecoredId;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "votetopic_id")
    public int getVotetopicId() {
        return votetopicId;
    }

    public void setVotetopicId(int votetopicId) {
        this.votetopicId = votetopicId;
    }

    @Basic
    @Column(name = "vote_time")
    public Date getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Date voteTime) {
        this.voteTime = voteTime;
    }

    @Basic
    @Column(name = "vote_ip")
    public String getVoteIp() {
        return voteIp;
    }

    public void setVoteIp(String voteIp) {
        this.voteIp = voteIp;
    }

    @Basic
    @Column(name = "vote_cookie")
    public String getVoteCookie() {
        return voteCookie;
    }

    public void setVoteCookie(String voteCookie) {
        this.voteCookie = voteCookie;
    }

    @Basic
    @Column(name = "wx_open_id")
    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsVoteRecord that = (CmsVoteRecord) o;

        if (voterecoredId != that.voterecoredId) return false;
        if (votetopicId != that.votetopicId) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (voteTime != null ? !voteTime.equals(that.voteTime) : that.voteTime != null) return false;
        if (voteIp != null ? !voteIp.equals(that.voteIp) : that.voteIp != null) return false;
        if (voteCookie != null ? !voteCookie.equals(that.voteCookie) : that.voteCookie != null) return false;
        if (wxOpenId != null ? !wxOpenId.equals(that.wxOpenId) : that.wxOpenId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = voterecoredId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + votetopicId;
        result = 31 * result + (voteTime != null ? voteTime.hashCode() : 0);
        result = 31 * result + (voteIp != null ? voteIp.hashCode() : 0);
        result = 31 * result + (voteCookie != null ? voteCookie.hashCode() : 0);
        result = 31 * result + (wxOpenId != null ? wxOpenId.hashCode() : 0);
        return result;
    }

    private CmsVoteTopic topic;

    private CmsUser user;
    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
    @ManyToOne
    @JoinColumn(name = "votetopic_id",insertable = false,updatable = false)
    public CmsVoteTopic getTopic() {
        return topic;
    }

    public void setTopic(CmsVoteTopic topic) {
        this.topic = topic;
    }
}
