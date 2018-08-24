package com.anchorcms.core.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-14
 * @Desc CMS内容浏览会员组关联表
 */
@Entity
@Table(name = "c_content_group_view")
@IdClass(ContentGroupViewPK.class)
public class ContentGroupView implements Serializable {
    private static final long serialVersionUID = -459948044628172149L;
    private int contentId;
    private int groupId;

    @Id
    @Column(name = "content_id")
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
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

        ContentGroupView that = (ContentGroupView) o;

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
