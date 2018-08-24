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
public class ChnlGroupContriPK implements Serializable {
    private static final long serialVersionUID = 4563357753303955195L;
    private int channelId;
    private int groupId;

    @Column(name = "channel_id")
    @Id
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Column(name = "group_id")
    @Id
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChnlGroupContriPK that = (ChnlGroupContriPK) o;

        if (channelId != that.channelId) return false;
        if (groupId != that.groupId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = channelId;
        result = 31 * result + groupId;
        return result;
    }
}
