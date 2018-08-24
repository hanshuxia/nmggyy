package com.anchorcms.icloud.model.commservice;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

import static org.apache.axis.i18n.MessagesConstants.projectName;

/**
 * @author machao
 * @Date 2017/1/14 17:54
 * @return
 * 招标预告信息主表
 */
@Entity
@Table(name = "s_tender_trailer")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class STenderTrailer implements Serializable {
    private static final long serialVersionUID = -4401247496637665428L;
    private int tenderTrailerId;
    private String projectName;                 //项目名称
    private String bidParty;                     //招标方
    private String bidType;                     //招标类型
    private Date bidDt;                         //招标时间
    private Integer createrId;
    private Date releaseDt;
    private Date createrDt;
    private Date updateDt;
    private String deFlag;
    private String addrProvince;                 //地区
    private String addrCity;
    private String addrCounty;
    private String addrStreet;

    @Id
    @Column(name = "TENDER_TRAILER_ID")
    public int getTenderTrailerId() {
        return tenderTrailerId;
    }

    public void setTenderTrailerId(int tenderTrailerId) {
        this.tenderTrailerId = tenderTrailerId;
    }

    @Basic
    @Column(name = "PROJECT_NAME")
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    // @Basic
    // @Column(name = "CONTENT_ID")
    // public Integer getContentId() {
    //     return contentId;
    // }
    //
    // public void setContentId(Integer contentId) {
    //     this.contentId = contentId;
    // }

    @Basic
    @Column(name = "BID_PARTY")
    public String getBidParty() {
        return bidParty;
    }

    public void setBidParty(String bidParty) {
        this.bidParty = bidParty;
    }

    @Basic
    @Column(name = "BID_TYPE")
    public String getBidType() {
        return bidType;
    }

    public void setBidType(String bidType) {
        this.bidType = bidType;
    }

    @Basic
    @Column(name = "CREATER_ID")
    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    @Basic
    @Column(name = "RELEASE_DT")
    public Date getReleaseDt() {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
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
    @Column(name = "BID_DT")
    public Date getBidDt() {
        return bidDt;
    }

    public void setBidDt(Date bidDt) {
        this.bidDt = bidDt;
    }
    @Basic
    @Column(name = "ADDR_PROVINCE")
    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    @Basic
    @Column(name = "ADDR_CITY")
    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    @Basic
    @Column(name = "ADDR_COUNTY")
    public String getAddrCounty() {
        return addrCounty;
    }

    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }

    @Basic
    @Column(name = "ADDR_STREET")
    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        STenderTrailer that = (STenderTrailer) o;

        if (tenderTrailerId != that.tenderTrailerId) return false;
        // if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;
        if (bidParty != null ? !bidParty.equals(that.bidParty) : that.bidParty != null) return false;
        if (bidType != null ? !bidType.equals(that.bidType) : that.bidType != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (createrDt != null ? !createrDt.equals(that.createrDt) : that.createrDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tenderTrailerId;
        // result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        result = 31 * result + (bidParty != null ? bidParty.hashCode() : 0);
        result = 31 * result + (bidType != null ? bidType.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (createrDt != null ? createrDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
    private Content content;
    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "content_id")
    public Content getContent(){
        return content;
    }
    public void setContent(Content content){
        this.content = content;
    }
}
