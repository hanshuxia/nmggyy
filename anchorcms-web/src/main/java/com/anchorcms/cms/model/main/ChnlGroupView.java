package com.anchorcms.cms.model.main;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-14
 * @Desc CMS栏目浏览会员组关联表
 */
@Entity
@Table(name = "c_chnl_group_view")
@IdClass(ChnlGroupViewPK.class)
public class ChnlGroupView implements Serializable {

    private static final long serialVersionUID = -2069401484422002106L;
    private int channelId;
    private int groupId;

    @Id
    @Column(name = "channel_id")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Id
    @Column(name = "group_id")
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

        ChnlGroupView that = (ChnlGroupView) o;

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
