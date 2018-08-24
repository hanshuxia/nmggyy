package com.anchorcms.cms.model.main;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc CMS内容类型表
 */
@Entity
@Table(name = "c_content_type")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class ContentType implements Serializable{
    private static final long serialVersionUID = -1203930204513587253L;
    private int typeId;
    private String typeName;
    private Integer imgWidth;
    private Integer imgHeight;
    private Boolean hasImage;
    private boolean isDisabled;

    @Id
    @Column(name = "type_id")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "img_width")
    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    @Basic
    @Column(name = "img_height")
    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

    @Basic
    @Column(name = "has_image")
    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }

    @Basic
    @Column(name = "is_disabled")
    public boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentType that = (ContentType) o;

        if (typeId != that.typeId) return false;
        //if (hasImage != that.hasImage) return false;
        if (isDisabled != that.isDisabled) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (imgWidth != null ? !imgWidth.equals(that.imgWidth) : that.imgWidth != null) return false;
        if (imgHeight != null ? !imgHeight.equals(that.imgHeight) : that.imgHeight != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeId;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (imgWidth != null ? imgWidth.hashCode() : 0);
        result = 31 * result + (imgHeight != null ? imgHeight.hashCode() : 0);
       // result = 31 * result + (int) hasImage;
        return result;
    }
}
