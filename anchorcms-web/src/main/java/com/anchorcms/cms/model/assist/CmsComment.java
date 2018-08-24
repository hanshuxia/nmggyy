package com.anchorcms.cms.model.assist;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.utils.StrUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-7
 * @Desc CMS评论表
 */
@Entity
@Table(name = "c_comment")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsComment implements Serializable{
    private static final long serialVersionUID = 850793090492422718L;
    private int commentId;
    private Date createTime;
    private Date replyTime;
    private Short ups;
    private Short downs;
    private Boolean isRecommend;
    private Boolean isChecked;
    private Integer score;
    private Integer replyCount;
    private Integer topParentId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }



    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "reply_time")
    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    @Basic
    @Column(name = "ups")
    public Short getUps() {
        return ups;
    }

    public void setUps(Short ups) {
        this.ups = ups;
    }

    @Basic
    @Column(name = "downs")
    public Short getDowns() {
        return downs;
    }

    public void setDowns(Short downs) {
        this.downs = downs;
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
    @Column(name = "is_checked")
    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
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
    @Column(name = "reply_count")
    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    @Basic
    @Column(name = "top_parent_id")
    public Integer getTopParentId() {
        return topParentId;
    }

    public void setTopParentId(Integer topParentId) {
        this.topParentId = topParentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsComment cmsComment = (CmsComment) o;

        if (commentId != cmsComment.commentId) return false;
        if (ups != cmsComment.ups) return false;
        if (downs != cmsComment.downs) return false;
        if (isRecommend != cmsComment.isRecommend) return false;
        if (isChecked != cmsComment.isChecked) return false;
        if (createTime != null ? !createTime.equals(cmsComment.createTime) : cmsComment.createTime != null) return false;
        if (replyTime != null ? !replyTime.equals(cmsComment.replyTime) : cmsComment.replyTime != null) return false;
        if (score != null ? !score.equals(cmsComment.score) : cmsComment.score != null) return false;
        if (replyCount != null ? !replyCount.equals(cmsComment.replyCount) : cmsComment.replyCount != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (replyTime != null ? replyTime.hashCode() : 0);
        result = 31 * result + (int) ups;
        result = 31 * result + (int) downs;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (replyCount != null ? replyCount.hashCode() : 0);
        return result;
    }
    private CmsCommentExt commentExt;
    private Content content;
    private CmsUser replayUser;
    private CmsUser commentUser;
    private CmsSite site;
    private CmsComment parent;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="comment_id")
    public CmsCommentExt getCommentExt() {
        return commentExt;
    }
    public void setCommentExt(CmsCommentExt commentExt) {
        this.commentExt = commentExt;
    }
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="parent_id")
    public CmsComment getParent() {
        return parent;
    }

    public void setParent(CmsComment parent) {
        this.parent = parent;
    }
    @ManyToOne
    @JoinColumn(name="comment_user_id")
    public CmsUser getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(CmsUser commentUser) {
        this.commentUser = commentUser;
    }
    @ManyToOne
    @JoinColumn(name="reply_user_id")
        public CmsUser getReplayUser(){return replayUser;}
        public  void  setReplayUser(CmsUser replayUser){ this.replayUser = replayUser;}


    @ManyToOne
    @JoinColumn(name="site_id")
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
    @ManyToOne
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
            setDowns(zero);
        }
        if (getUps() == null) {
            setUps(zero);
        }
        if(getReplyCount()==null){
            setReplyCount(0);
        }
        if (getIsChecked() == null) {
            setIsChecked(false);
        }
        if (getIsRecommend() == null) {
            setIsRecommend(false);
        }
        if (getCreateTime() == null) {
            setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
    }
    @Transient
    public String getReplayHtml() {
        return StrUtils.txt2htm(getReply());
    }
    @Transient
    public String getReply() {
        return getCommentExt().getReply();
    }
    @Transient
    public String getText() {
        return getCommentExt().getText();
    }

    @Transient
    public void setTopParentIds(Integer parentId) {
        CmsComment parent = null;
        CmsComment check = getParent();
        while (check!=null){
            parent = check;
            check = check.getParent();
        }
        if(parent != null) {
            setTopParentId(parent.commentId);
        }
    }

}
