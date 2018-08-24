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
public class ContentTopicPK implements Serializable {
    private static final long serialVersionUID = -6729352645431134756L;
    private int contentId;
    private int topicId;

    @Column(name = "content_id")
    @Id
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    @Column(name = "topic_id")
    @Id
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentTopicPK that = (ContentTopicPK) o;

        if (contentId != that.contentId) return false;
        if (topicId != that.topicId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contentId;
        result = 31 * result + topicId;
        return result;
    }
}
