package com.anchorcms.icloud.model.commservice;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @Author zhouyq
 * @Date 2017/01/14
 * @Desc 政府网站实体类
 */
@Entity
@Table(name = "s_stite_manager")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SStiteManager implements Serializable{
    private static final long serialVersionUID = -3579798929932104501L;
    private String stiteId; // 网站id
    private String stiteName; // 网站名称
    private String addrProvince; // 地址（省）
    private String stiteAddress; // 网站地址
    private String address; // 地址（地级市）
    private Date createrDt; // 创建时间
    private Date updateDt; // 更新时间
    private String deFlag; // 逻辑删除
    private String status; // 网站发布状态
  //  private Integer contentId;

    @Id
    @Column(name = "STITE_ID")
    public String getStiteId() {
        return stiteId;
    }

    public void setStiteId(String stiteId) {
        this.stiteId = stiteId;
    }

    @Basic
    @Column(name = "STITE_NAME")
    public String getStiteName() {
        return stiteName;
    }

    public void setStiteName(String stiteName) {
        this.stiteName = stiteName;
    }

    @Basic
    @Column(name = "STITE_address")
    public String getStiteAddress() {
        return stiteAddress;
    }

    public void setStiteAddress(String stiteAddress) {
        this.stiteAddress = stiteAddress;
    }

    @Basic
    @Column(name = "ADDRPROVINCE")
    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    @Basic
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "CREATER_DT")
    public Date getCreaterDt() {
        return createrDt;
    }

    public void setCreaterDt(Date createrDt) {
        this.createrDt = createrDt;
    }

    @Basic
    @Column(name = "UPDATE_DT")
    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    @Basic
    @Column(name = "DE_FLAG")
    public String getDeFlag() {
        return deFlag;
    }

    public void setDeFlag(String deFlag) {
        this.deFlag = deFlag;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SStiteManager that = (SStiteManager) o;

        if (stiteId != that.stiteId) return false;
        if (stiteName != null ? !stiteName.equals(that.stiteName) : that.stiteName != null) return false;
        if (stiteAddress != null ? !stiteAddress.equals(that.stiteAddress) : that.stiteAddress != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (createrDt != null ? !createrDt.equals(that.createrDt) : that.createrDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stiteId != null ? stiteId.hashCode() : 0;
        result = 31 * result + (stiteName != null ? stiteName.hashCode() : 0);
        result = 31 * result + (stiteAddress != null ? stiteAddress.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (createrDt != null ? createrDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
    private Content content;
    @OneToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "content_id")
    public Content getContent(){
        return content;
    }
    public void setContent(Content content){
        this.content = content;
    }
}
