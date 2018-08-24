package com.anchorcms.cms.model.main;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc CMS内容TAG表
 */
@Entity
@Table(name = "c_content_tag")
@NamedNativeQueries({
        @NamedNativeQuery(name="ContentTag.deleteContentRef",
                query = "delete from c_contenttag where tag_id=?"),
        @NamedNativeQuery(name="ContentTag.countContentRef",
                query = "select count(*) from c_contenttag where tag_id=?")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class ContentTag implements Serializable{
    private static final long serialVersionUID = 7928904425697695411L;
    private Integer tagId;
    private String tagName;
    private Integer refCounter;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "tag_id")
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Basic
    @Column(name = "tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Basic
    @Column(name = "ref_counter")
    public Integer getRefCounter() {
        return refCounter;
    }

    public void setRefCounter(Integer refCounter) {
        this.refCounter = refCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentTag that = (ContentTag) o;

        if (tagId != that.tagId) return false;
        if (refCounter != that.refCounter) return false;
        if (tagName != null ? !tagName.equals(that.tagName) : that.tagName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tagId;
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        result = 31 * result + refCounter;
        return result;
    }
    public void init() {
        if (getRefCounter() == null) {
            setRefCounter(1);
        }
    }
}
