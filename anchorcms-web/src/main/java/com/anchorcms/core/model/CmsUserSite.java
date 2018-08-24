package com.anchorcms.core.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc CMS管理员站点表
 */
@Entity
@Table(name = "c_user_site")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsUserSite implements Serializable{
    private static final long serialVersionUID = 2314702830912783727L;
    private int usersiteId;

    private byte checkStep;
    private Boolean isAllChannel;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usersite_id")
    public int getUsersiteId() {
        return usersiteId;
    }

    public void setUsersiteId(int usersiteId) {
        this.usersiteId = usersiteId;
    }




    @Basic
    @Column(name = "check_step")
    public byte getCheckStep() {
        return checkStep;
    }

    public void setCheckStep(byte checkStep) {
        this.checkStep = checkStep;
    }

    @Basic
    @Column(name = "is_all_channel")
    public Boolean getIsAllChannel() {
        return isAllChannel;
    }

    public void setIsAllChannel(Boolean isAllChannel) {
        this.isAllChannel = isAllChannel;
    }

    private CmsSite site;

    private CmsUser user;
    @ManyToOne
    @JoinColumn(name = "user_id")
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "site_id")
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
}
