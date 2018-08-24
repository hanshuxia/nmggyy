package com.anchorcms.cms.model.assist;

import javax.persistence.*;

/**
 * Created by zth on 2016/12/7.
 */
@Entity
@Table(name = "c_origin")
public class CmsOrigin {
    private Integer originId;
    private String originName;
    private Integer refCount;

    @Id
    @Column(name = "origin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    @Basic
    @Column(name = "origin_name")
    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    @Basic
    @Column(name = "ref_count")
    public Integer getRefCount() {
        return refCount;
    }

    public void setRefCount(Integer refCount) {
        this.refCount = refCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsOrigin that = (CmsOrigin) o;

        if (originId != null ? !originId.equals(that.originId) : that.originId != null) return false;
        if (originName != null ? !originName.equals(that.originName) : that.originName != null) return false;
        if (refCount != null ? !refCount.equals(that.refCount) : that.refCount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = originId != null ? originId.hashCode() : 0;
        result = 31 * result + (originName != null ? originName.hashCode() : 0);
        result = 31 * result + (refCount != null ? refCount.hashCode() : 0);
        return result;
    }
}
