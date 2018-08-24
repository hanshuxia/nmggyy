package com.anchorcms.cms.model.main;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc CMS内容审核信息表
 */
@Entity
@Table(name = "c_content_check")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class ContentCheck implements Serializable{
    private static final long serialVersionUID = -7016750542440581747L;
    private int contentId;
    private Byte checkStep;
    private String checkOpinion;
    private Boolean isRejected;
    private Integer reviewer;
    private Date checkDate;

    @Id
    @Column(name = "content_id")
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    @Basic
    @Column(name = "check_step")
    public Byte getCheckStep() {
        return checkStep;
    }

    public void setCheckStep(Byte checkStep) {
        this.checkStep = checkStep;
    }

    @Basic
    @Column(name = "check_opinion")
    public String getCheckOpinion() {
        return checkOpinion;
    }

    public void setCheckOpinion(String checkOpinion) {
        this.checkOpinion = checkOpinion;
    }

    @Basic
    @Column(name = "is_rejected")
    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

    @Basic
    @Column(name = "reviewer")
    public Integer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Integer reviewer) {
        this.reviewer = reviewer;
    }

    @Basic
    @Column(name = "check_date")
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentCheck that = (ContentCheck) o;

        if (contentId != that.contentId) return false;
        if (checkStep != that.checkStep) return false;
        if (isRejected != that.isRejected) return false;
        if (checkOpinion != null ? !checkOpinion.equals(that.checkOpinion) : that.checkOpinion != null) return false;
        if (reviewer != null ? !reviewer.equals(that.reviewer) : that.reviewer != null) return false;
        if (checkDate != null ? !checkDate.equals(that.checkDate) : that.checkDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contentId;
        result = 31 * result + (int) checkStep;
        result = 31 * result + (checkOpinion != null ? checkOpinion.hashCode() : 0);
        result = 31 * result + (reviewer != null ? reviewer.hashCode() : 0);
        result = 31 * result + (checkDate != null ? checkDate.hashCode() : 0);
        return result;
    }
    /**
     * 草稿
     */
    public static final byte DRAFT = 0;
    /**
     * 审核中
     */
    public static final byte CHECKING = 1;
    /**
     * 退回
     */
    public static final byte REJECT = -1;
    /**
     * 已审核
     */
    public static final byte CHECKED = 2;
    /**
     * 回收站
     */
    public static final byte RECYCLE = 3;
    /**
     * 投稿
     */
    public static final byte CONTRIBUTE = 4;
    /**
     * 归档
     */
    public static final byte PIGEONHOLE = 5;

    private Content content;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name="content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void init() {
        byte zero = 0;
        if (getCheckStep() == null) {
            setCheckStep(zero);
        }
        if (getIsRejected() == null) {
            setIsRejected(false);
        }
    }
}
