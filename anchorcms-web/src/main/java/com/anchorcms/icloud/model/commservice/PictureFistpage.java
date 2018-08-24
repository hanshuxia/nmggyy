package com.anchorcms.icloud.model.commservice;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author machao
 * @Date 2017/2/10 11:37
 * @return
 * 首页轮播图
 */
@Entity
@Table(name = "picture_fistpage")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class PictureFistpage implements Serializable {
    private static final long serialVersionUID = 4845868774421886120L;
    private int picId;
    private String imgPath;
    private Date createDate;
    private Integer num;
    private String nwebpath;
    private String status;

    @Id
    @Column(name = "pic_id")
    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    @Basic
    @Column(name = "img_path")
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Basic
    @Column(name = "createDate")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Basic
    @Column(name = "nwebpath")
    public String getNwebpath() {
        return nwebpath;
    }

    public void setNwebpath(String nwebpath) {
        this.nwebpath = nwebpath;
    }

    @Basic
    @Column(name = "status")

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PictureFistpage that = (PictureFistpage) o;

        if (picId != that.picId) return false;
        if (imgPath != null ? !imgPath.equals(that.imgPath) : that.imgPath != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (num != null ? !num.equals(that.num) : that.num != null) return false;
        if (nwebpath != null ? !nwebpath.equals(that.nwebpath) : that.nwebpath != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = picId;
        result = 31 * result + (imgPath != null ? imgPath.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (nwebpath != null ? nwebpath.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
