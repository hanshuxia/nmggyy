package com.anchorcms.cms.model.main;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-14
 * @Desc 用户收藏管理表
 */
@Entity
@Table(name = "c_user_collection")
@IdClass(UserCollectionPK.class)
public class UserCollection implements Serializable {
    private static final long serialVersionUID = 2011278671790485112L;
    private int userId;
    private int contentId;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "content_id")
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

        UserCollection that = (UserCollection) o;

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
