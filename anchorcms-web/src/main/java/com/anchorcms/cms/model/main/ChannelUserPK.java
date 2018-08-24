package com.anchorcms.cms.model.main;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-14
 * @Desc
 */
public class ChannelUserPK implements Serializable {
    private static final long serialVersionUID = -5868755875206619785L;
    private int channelId;
    private int userId;

    @Column(name = "channel_id")
    @Id
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Column(name = "user_id")
    @Id
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

        ChannelUserPK that = (ChannelUserPK) o;

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
