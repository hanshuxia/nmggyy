package com.anchorcms.cms.model.main;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-14
 * @Desc CMS专题内容关联表
 */
public class UserCollectionPK implements Serializable {
    private static final long serialVersionUID = 6822777884669396852L;
    private int userId;
    private int contentId;

    @Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "content_id")
    @Id
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCollectionPK that = (UserCollectionPK) o;

        if (userId != that.userId) return false;
        if (contentId != that.contentId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + contentId;
        return result;
    }
}
