package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-9
 * @Desc 搜索热词表
 */
@Entity
@Table(name = "c_search_words")
public class CmsSearchWords implements Serializable{
    private static final long serialVersionUID = 7833593978534118913L;
    private int wordId;
    private String name;
    private int hitCount;
    private int priority;
    private String nameInitial;
    private Boolean isRecommend;

    @Id
    @Column(name = "word_id")
    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "hit_count")
    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    @Basic
    @Column(name = "priority")
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "name_initial")
    public String getNameInitial() {
        return nameInitial;
    }

    public void setNameInitial(String nameInitial) {
        this.nameInitial = nameInitial;
    }

    @Basic
    @Column(name = "is_recommend")
    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsSearchWords that = (CmsSearchWords) o;

        if (wordId != that.wordId) return false;
        if (hitCount != that.hitCount) return false;
        if (priority != that.priority) return false;
        if (isRecommend != that.isRecommend) return false;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nameInitial != null ? !nameInitial.equals(that.nameInitial) : that.nameInitial != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wordId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + hitCount;
        result = 31 * result + priority;
        result = 31 * result + (nameInitial != null ? nameInitial.hashCode() : 0);
        return result;
    }

    private CmsSite site;
    @ManyToOne
    @JoinColumn(name = "site_id")
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }

    /**
     * 搜索次数降序
     */
    public static final int HIT_DESC=1;
    /**
     * 优先级降序
     */
    public static final int PRIORITY_DESC=3;
    /**
     * 搜索次数升序
     */
    public static final int HIT_ASC=2;
    /**
     * 优先级升序
     */
    public static final int PRIORITY_ASC=4;

    public static final int DEFAULT_PRIORITY=10;
}
