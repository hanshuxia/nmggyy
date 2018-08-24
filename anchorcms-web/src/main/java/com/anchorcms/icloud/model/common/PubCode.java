package com.anchorcms.icloud.model.common;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/13
 * @Desc 业务公共字典表
 */
@Entity
@Table(name = "pub_code")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class PubCode implements Serializable{
    private static final long serialVersionUID = -5431881048614878913L;
    private int id;
    private String typeCode;
    private String typeName;
    private String key;
    private String value;
    private String sortNo;
    private Integer level;
    private Integer parentCodeId;
    private Timestamp sjc;
    private String isenable;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TYPE_CODE")
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "TYPE_NAME")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "KEY")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "VALUE")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "SORT_NO")
    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    @Basic
    @Column(name = "LEVEL")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "PARENT_CODE_ID")
    public Integer getParentCodeId() {
        return parentCodeId;
    }

    public void setParentCodeId(Integer parentCodeId) {
        this.parentCodeId = parentCodeId;
    }

    @Basic
    @Column(name = "SJC")
    public Timestamp getSjc() {
        return sjc;
    }

    public void setSjc(Timestamp sjc) {
        this.sjc = sjc;
    }

    @Basic
    @Column(name = "ISENABLE")
    public String getIsenable() {
        return isenable;
    }

    public void setIsenable(String isenable) {
        this.isenable = isenable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PubCode pubCode = (PubCode) o;

        if (id != pubCode.id) return false;
        if (typeCode != null ? !typeCode.equals(pubCode.typeCode) : pubCode.typeCode != null) return false;
        if (typeName != null ? !typeName.equals(pubCode.typeName) : pubCode.typeName != null) return false;
        if (key != null ? !key.equals(pubCode.key) : pubCode.key != null) return false;
        if (value != null ? !value.equals(pubCode.value) : pubCode.value != null) return false;
        if (sortNo != null ? !sortNo.equals(pubCode.sortNo) : pubCode.sortNo != null) return false;
        if (parentCodeId != null ? !parentCodeId.equals(pubCode.parentCodeId) : pubCode.parentCodeId != null)
            return false;
        if (sjc != null ? !sjc.equals(pubCode.sjc) : pubCode.sjc != null) return false;
        if (isenable != null ? !isenable.equals(pubCode.isenable) : pubCode.isenable != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (typeCode != null ? typeCode.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (sortNo != null ? sortNo.hashCode() : 0);
        result = 31 * result + (parentCodeId != null ? parentCodeId.hashCode() : 0);
        result = 31 * result + (sjc != null ? sjc.hashCode() : 0);
        result = 31 * result + (isenable != null ? isenable.hashCode() : 0);
        return result;
    }
}
