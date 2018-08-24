package com.anchorcms.core.model;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-2
 * @Desc 用户扩展信息实体类
 */
@Entity
@Table(name = "c_user_ext")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsUserExt implements Serializable{
    private static final long serialVersionUID = 2500700798321238641L;
    private int userId;
    private String realname;
    private Boolean gender;
    private Date birthday;
    private String intro;
    private String comefrom;
    private String qq;
    private String msn;
    private String phone;
    private String mobile;
    private String userImg;
    private String userSignature;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "realname")
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Basic
    @Column(name = "gender")
    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "intro")
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Basic
    @Column(name = "comefrom")
    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    @Basic
    @Column(name = "qq")
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Basic
    @Column(name = "msn")
    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
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
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "user_img")
    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    @Basic
    @Column(name = "user_signature")
    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsUserExt cmsUserExt = (CmsUserExt) o;

        if (userId != cmsUserExt.userId) return false;
        if (realname != null ? !realname.equals(cmsUserExt.realname) : cmsUserExt.realname != null) return false;
        if (gender != null ? !gender.equals(cmsUserExt.gender) : cmsUserExt.gender != null) return false;
        if (birthday != null ? !birthday.equals(cmsUserExt.birthday) : cmsUserExt.birthday != null) return false;
        if (intro != null ? !intro.equals(cmsUserExt.intro) : cmsUserExt.intro != null) return false;
        if (comefrom != null ? !comefrom.equals(cmsUserExt.comefrom) : cmsUserExt.comefrom != null) return false;
        if (qq != null ? !qq.equals(cmsUserExt.qq) : cmsUserExt.qq != null) return false;
        if (msn != null ? !msn.equals(cmsUserExt.msn) : cmsUserExt.msn != null) return false;
        if (phone != null ? !phone.equals(cmsUserExt.phone) : cmsUserExt.phone != null) return false;
        if (mobile != null ? !mobile.equals(cmsUserExt.mobile) : cmsUserExt.mobile != null) return false;
        if (userImg != null ? !userImg.equals(cmsUserExt.userImg) : cmsUserExt.userImg != null) return false;
        if (userSignature != null ? !userSignature.equals(cmsUserExt.userSignature) : cmsUserExt.userSignature != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (realname != null ? realname.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (comefrom != null ? comefrom.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        result = 31 * result + (msn != null ? msn.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (userImg != null ? userImg.hashCode() : 0);
        result = 31 * result + (userSignature != null ? userSignature.hashCode() : 0);
        return result;
    }

    private CmsUser user;

    @ManyToOne
    @MapsId
    @JoinColumn(name="user_id")
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }

    public void blankToNull() {
        // 将空串设置为null，便于前台处理。
        if (StringUtils.isBlank(getRealname())) {
            setRealname(null);
        }
        if (StringUtils.isBlank(getIntro())) {
            setIntro(null);
        }
        if (StringUtils.isBlank(getComefrom())) {
            setComefrom(null);
        }
        if (StringUtils.isBlank(getMobile())) {
            setMobile(null);
        }
        if (StringUtils.isBlank(getPhone())) {
            setPhone(null);
        }
        if (StringUtils.isBlank(getMsn())) {
            setMsn(null);
        }
        if (StringUtils.isBlank(getQq())) {
            setQq(null);
        }
    }
}
