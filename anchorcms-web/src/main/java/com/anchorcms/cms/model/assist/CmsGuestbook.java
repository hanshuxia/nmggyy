package com.anchorcms.cms.model.assist;

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
 * @Desc CMS留言表
 */
@Entity
@Table(name = "c_guestbook")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsGuestbook implements Serializable{
    private static final long serialVersionUID = 1994521213873425521L;
    private int guestbookId;
   // private int guestbookctgId;
    private Integer memberId;
    private Integer adminId;
    private String ip;
    private Date createTime;
    private Date replayTime;
    private Boolean isChecked;
    private Boolean isRecommend;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guestbook_id")
    public int getGuestbookId() {
        return guestbookId;
    }

    public void setGuestbookId(int guestbookId) {
        this.guestbookId = guestbookId;
    }


   /* @Basic
    @Column(name = "guestbookctg_id")
    public int getGuestbookctgId() {
        return guestbookctgId;
    }

    public void setGuestbookctgId(int guestbookctgId) {
        this.guestbookctgId = guestbookctgId;
    }*/

    @Basic
    @Column(name = "member_id" ,insertable = false  ,updatable = false)
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "admin_id" ,insertable = false  ,updatable = false )
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "replay_time")
    public Date getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(Date replayTime) {
        this.replayTime = replayTime;
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
    @Column(name = "is_recommend")
    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsGuestbook that = (CmsGuestbook) o;

        if (guestbookId != that.guestbookId) return false;
       // if (guestbookctgId != that.guestbookctgId) return false;
        if (isChecked != that.isChecked) return false;
        if (isRecommend != that.isRecommend) return false;
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        if (adminId != null ? !adminId.equals(that.adminId) : that.adminId != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (replayTime != null ? !replayTime.equals(that.replayTime) : that.replayTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = guestbookId;
       // result = 31 * result + guestbookctgId;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (adminId != null ? adminId.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (replayTime != null ? replayTime.hashCode() : 0);
        return result;
    }

    private CmsGuestbookCtg ctg;

    private CmsUser member;

    private CmsSite site;

    private CmsUser admin;

    private CmsGuestbookExt ext;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guestbook_id")
    public CmsGuestbookExt getExt() {
        return ext;
    }

    public void setExt(CmsGuestbookExt ext) {
        this.ext = ext;
    }
    @ManyToOne
    @JoinColumn(name = "admin_id")
    public CmsUser getAdmin() {
        return admin;
    }
    public void setAdmin(CmsUser admin) {
        this.admin = admin;
    }


    private CmsGuestbookCtg CmsGuestbookCtg;
    @ManyToOne
    @JoinColumn(name = "guestbookctg_id" ,insertable = false  ,updatable = false)
    public CmsGuestbookCtg getCmsGuestbookCtg() {
        return CmsGuestbookCtg;
    }

    public void setCmsGuestbookCtg(CmsGuestbookCtg CmsGuestbookCtg) {
        this.CmsGuestbookCtg = CmsGuestbookCtg;
    }




    @ManyToOne
    @JoinColumn(name = "site_id")
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
    @ManyToOne
    @JoinColumn(name = "member_id")
    public CmsUser getMember() {
        return member;
    }

    public void setMember(CmsUser member) {
        this.member = member;
    }
    @ManyToOne
    @JoinColumn(name = "guestbookctg_id")
    public CmsGuestbookCtg getCtg() {
        return ctg;
    }

    public void setCtg(CmsGuestbookCtg ctg) {
        this.ctg = ctg;
    }
    public void init() {
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
}
