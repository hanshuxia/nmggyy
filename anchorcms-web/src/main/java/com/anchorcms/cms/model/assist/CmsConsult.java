package com.anchorcms.cms.model.assist;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.utils.StrUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ly on 2017/1/13.
 */
@Entity
@Table(name = "c_consult")
public class CmsConsult implements Serializable {
    private static final long serialVersionUID = 7684609625782949834L;
    private int consultId;
    private Date createTime;
    private Date replyTime;
    private Short ups;
    private Short downs;
    private Boolean isRecommend;
    private Boolean isChecked;
    private Integer score;
    private Integer topParentId;
    private Integer replyCount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consult_id")
    public int getConsultId() {
        return consultId;
    }

    public void setConsultId(int consultId) {
        this.consultId = consultId;
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
    @Column(name = "top_parent_id")
    public Integer getTopParentId() {
        return topParentId;
    }

    public void setTopParentId(Integer topParentId) {
        this.topParentId = topParentId;
    }

    @Basic
    @Column(name = "reply_count")
    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsConsult that = (CmsConsult) o;

        if (consultId != that.consultId) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (replyTime != null ? !replyTime.equals(that.replyTime) : that.replyTime != null) return false;
        if (ups != null ? !ups.equals(that.ups) : that.ups != null) return false;
        if (downs != null ? !downs.equals(that.downs) : that.downs != null) return false;
        if (isRecommend != null ? !isRecommend.equals(that.isRecommend) : that.isRecommend != null) return false;
        if (isChecked != null ? !isChecked.equals(that.isChecked) : that.isChecked != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (topParentId != null ? !topParentId.equals(that.topParentId) : that.topParentId != null) return false;
        if (replyCount != null ? !replyCount.equals(that.replyCount) : that.replyCount != null) return false;
        if (consultExt != null ? !consultExt.equals(that.consultExt) : that.consultExt != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (replayUser != null ? !replayUser.equals(that.replayUser) : that.replayUser != null) return false;
        if (consultUser != null ? !consultUser.equals(that.consultUser) : that.consultUser != null) return false;
        if (site != null ? !site.equals(that.site) : that.site != null) return false;
        if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = consultId;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (replyTime != null ? replyTime.hashCode() : 0);
        result = 31 * result + (ups != null ? ups.hashCode() : 0);
        result = 31 * result + (downs != null ? downs.hashCode() : 0);
        result = 31 * result + (isRecommend != null ? isRecommend.hashCode() : 0);
        result = 31 * result + (isChecked != null ? isChecked.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (topParentId != null ? topParentId.hashCode() : 0);
        result = 31 * result + (replyCount != null ? replyCount.hashCode() : 0);
        result = 31 * result + (consultExt != null ? consultExt.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (replayUser != null ? replayUser.hashCode() : 0);
        result = 31 * result + (consultUser != null ? consultUser.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }

    private CmsConsultExt consultExt;
    private Content content;
    private CmsUser replayUser;
    private CmsUser consultUser;
    private CmsSite site;
    private CmsConsult parent;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="consult_id")
    public CmsConsultExt getConsultExt() {
        return consultExt;
    }

    public void setConsultExt(CmsConsultExt consultExt) {
        this.consultExt = consultExt;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="parent_id")
    public CmsConsult getParent() {
        return parent;
    }

    public void setParent(CmsConsult parent) {
        this.parent = parent;
    }
    @ManyToOne
    @JoinColumn(name="consult_user_id")
    public CmsUser getConsultUser() {
        return consultUser;
    }

    public void setConsultUser(CmsUser consultUser) {
        this.consultUser = consultUser;
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
        return getConsultExt().getReply();
    }
    @Transient
    public String getText() {
        return getConsultExt().getText();
    }

    @Transient
    public void setTopParentIds(Integer parentId) {
        CmsConsult parent = null;
        CmsConsult check = getParent();
        while (check!=null){
            parent = check;
            check = check.getParent();
        }
        if(parent != null) {
            setTopParentId(parent.consultId);
        }
    }

}
