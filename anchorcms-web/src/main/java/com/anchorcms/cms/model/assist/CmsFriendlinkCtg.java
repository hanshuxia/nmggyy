package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-8
 * @Desc 友链类别
 */
@Entity
@Table(name = "c_friendlink_ctg")
public class CmsFriendlinkCtg implements Serializable{
    private static final long serialVersionUID = -2832122385195714400L;
    private int friendlinkctgId;
    private String friendlinkctgName;
    private int priority;

    @Id
    @Column(name = "friendlinkctg_id")
    public int getFriendlinkctgId() {
        return friendlinkctgId;
    }

    public void setFriendlinkctgId(int friendlinkctgId) {
        this.friendlinkctgId = friendlinkctgId;
    }


    @Basic
    @Column(name = "friendlinkctg_name")
    public String getFriendlinkctgName() {
        return friendlinkctgName;
    }

    public void setFriendlinkctgName(String friendlinkctgName) {
        this.friendlinkctgName = friendlinkctgName;
    }

    @Basic
    @Column(name = "priority")
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsFriendlinkCtg that = (CmsFriendlinkCtg) o;

        if (friendlinkctgId != that.friendlinkctgId) return false;
        if (priority != that.priority) return false;
        if (friendlinkctgName != null ? !friendlinkctgName.equals(that.friendlinkctgName) : that.friendlinkctgName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = friendlinkctgId;
        result = 31 * result + (friendlinkctgName != null ? friendlinkctgName.hashCode() : 0);
        result = 31 * result + priority;
        return result;
    }

    private CmsSite site;
    @ManyToOne
    @JoinColumn(name="site_id")
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
}
