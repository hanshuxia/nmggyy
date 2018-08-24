package com.anchorcms.cms.model.assist;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-7
 * @Desc CMS搜索热词表
 */
@Entity
@Table(name = "c_sensitivity")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsSensitivity implements Serializable{
    private static final long serialVersionUID = 4648686106027585148L;
    private int sensitivityId;
    private String search;
    private String replacement;

    @Id
    @Column(name = "sensitivity_id")
    public int getSensitivityId() {
        return sensitivityId;
    }

    public void setSensitivityId(int sensitivityId) {
        this.sensitivityId = sensitivityId;
    }

    @Basic
    @Column(name = "search")
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Basic
    @Column(name = "replacement")
    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsSensitivity that = (CmsSensitivity) o;

        if (sensitivityId != that.sensitivityId) return false;
        if (search != null ? !search.equals(that.search) : that.search != null) return false;
        if (replacement != null ? !replacement.equals(that.replacement) : that.replacement != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sensitivityId;
        result = 31 * result + (search != null ? search.hashCode() : 0);
        result = 31 * result + (replacement != null ? replacement.hashCode() : 0);
        return result;
    }
}
