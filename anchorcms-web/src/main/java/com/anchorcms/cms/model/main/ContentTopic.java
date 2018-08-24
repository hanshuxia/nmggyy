package com.anchorcms.cms.model.main;

import javax.persistence.*;
import javax.xml.rpc.encoding.Serializer;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-14
 * @Desc
 */
@Entity
@Table(name = "c_content_topic")
@IdClass(ContentTopicPK.class)
public class ContentTopic implements Serializable {
    private static final long serialVersionUID = -5253461575553547269L;
    private int contentId;
    private int topicId;

    @Id
    @Column(name = "content_id")
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    @Id
    @Column(name = "topic_id")
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

        ContentTopic that = (ContentTopic) o;

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
