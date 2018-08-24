package com.anchorcms.cms.model.assist;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-7
 * @Desc CMS留言内容表
 */
@Entity
@Table(name = "c_guestbook_ext")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsGuestbookExt implements Serializable{
    private static final long serialVersionUID = -8683662484276877098L;
    private int guestbookId;
    private String title;
    private String content;
    private String reply;
    private String email;
    private String phone;
    private String qq;

    @Id
    @Column(name = "guestbook_id")
    public int getGuestbookId() {
        return guestbookId;
    }

    public void setGuestbookId(int guestbookId) {
        this.guestbookId = guestbookId;
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
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "qq")
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsGuestbookExt that = (CmsGuestbookExt) o;

        if (guestbookId != that.guestbookId) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (reply != null ? !reply.equals(that.reply) : that.reply != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (qq != null ? !qq.equals(that.qq) : that.qq != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = guestbookId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (reply != null ? reply.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        return result;
    }

    private CmsGuestbook guestbook;
    @MapsId
    @OneToOne
    @JoinColumn(name = "guestbook_id")
    public CmsGuestbook getGuestbook() {
        return guestbook;
    }

    public void setGuestbook(CmsGuestbook guestbook) {
        this.guestbook = guestbook;
    }
    public void init() {
        blankToNull();
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getContent())) {
            setContent(null);
        }
        if (StringUtils.isBlank(getReply())) {
            setReply(null);
        }
        if (StringUtils.isBlank(getTitle())) {
            setTitle(null);
        }
        if (StringUtils.isBlank(getEmail())) {
            setEmail(null);
        }
        if (StringUtils.isBlank(getPhone())) {
            setPhone(null);
        }
        if (StringUtils.isBlank(getQq())) {
            setQq(null);
        }
    }
}
