package com.anchorcms.cms.model.main;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-14
 * @Desc CMS栏目用户关联表
 */
@Entity
@Table(name = "c_channel_user")
@IdClass(ChannelUserPK.class)
public class ChannelUser implements Serializable {
    private static final long serialVersionUID = -2006997043829301106L;
    private int channelId;
    private int userId;

    @Id
    @Column(name = "channel_id")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChannelUser that = (ChannelUser) o;

        if (channelId != that.channelId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = channelId;
        result = 31 * result + userId;
        return result;
    }
}
