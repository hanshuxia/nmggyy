package com.anchorcms.core.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-14
 * @Desc
 */
public class ContentGroupViewPK implements Serializable {
    private static final long serialVersionUID = 2405187554904768944L;
    private int contentId;
    private int groupId;

    @Column(name = "content_id")
    @Id
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
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

        ContentGroupViewPK that = (ContentGroupViewPK) o;

        if (contentId != that.contentId) return false;
        if (groupId != that.groupId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contentId;
        result = 31 * result + groupId;
        return result;
    }
}
