package com.anchorcms.cms.model.assist;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-7
 * @Desc CMS评论扩展表
 */
@Entity
@Table(name = "c_comment_ext")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsCommentExt implements Serializable{
    private static final long serialVersionUID = 8480038545660260991L;
    private int commentId;
    private String ip;
    private String text;
    private String reply;

    @Id
    @Column(name = "comment_id")
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "reply")
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsCommentExt that = (CmsCommentExt) o;

        if (commentId != that.commentId) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (reply != null ? !reply.equals(that.reply) : that.reply != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (reply != null ? reply.hashCode() : 0);
        return result;
    }

    private CmsComment comment;
    @OneToOne
    @MapsId
    @JoinColumn(name="comment_id",insertable = false,updatable = false)
    public CmsComment getComment() {
        return comment;
    }

    public void setComment(CmsComment comment) {
        this.comment = comment;
    }
}
