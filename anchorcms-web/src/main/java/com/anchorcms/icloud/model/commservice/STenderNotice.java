package com.anchorcms.icloud.model.commservice;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

import static org.apache.axis.i18n.MessagesConstants.projectName;

/**
 * @author machao
 * @Date 2017/1/14 17:53
 * @return
 * 招标公告信息主表
 */
@Entity
@Table(name = "s_tender_notice")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class STenderNotice implements Serializable{
    private static final long serialVersionUID = 7696874321665288024L;
    private int tenderNoticeId;                // 主键
    private Integer tenderTrailerId;            //招标预告id
    private String projectName;                 //项目名称
    private String tenderCode;                  //招标代码
    private String purchaseOwner;               //采购业主
    private String bidCompany;                   //招标公司
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private Date deadlineDt;                    //截止日期
    private String contact;                     //联系人
    private String mobile;                      //电话
    private String createrId;                   //创建人
    private Date releaseDt;                     //发布日期
    private Date createDt;                      //创建日期
    private Date updateDt;                      //更新日期
    private String deFlag;
    private String bidType;                     //招标类型

    @Id
    @Column(name = "TENDER_NOTICE_ID")
    public int getTenderNoticeId() {
        return tenderNoticeId;
    }

    public void setTenderNoticeId(int tenderNoticeId) {
        this.tenderNoticeId = tenderNoticeId;
    }


    @Basic
    @Column(name = "TENDER_TRAILER_ID")
    public Integer getTenderTrailerId() {
        return tenderTrailerId;
    }

    public void setTenderTrailerId(Integer tenderTrailerId) {
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
    @Column(name = "TENDER_CODE")
    public String getTenderCode() {
        return tenderCode;
    }

    public void setTenderCode(String tenderCode) {
        this.tenderCode = tenderCode;
    }

    @Basic
    @Column(name = "PURCHASE_OWNER")
    public String getPurchaseOwner() {
        return purchaseOwner;
    }

    public void setPurchaseOwner(String purchaseOwner) {
        this.purchaseOwner = purchaseOwner;
    }

    @Basic
    @Column(name = "BID_COMPANY")
    public String getBidCompany() {
        return bidCompany;
    }

    public void setBidCompany(String bidCompany) {
        this.bidCompany = bidCompany;
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

    @Basic
    @Column(name = "DEADLINE_DT")
    public Date getDeadlineDt() {
        return deadlineDt;
    }

    public void setDeadlineDt(Date deadlineDt) {
        this.deadlineDt = deadlineDt;
    }

    @Basic
    @Column(name = "CONTACT")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "MOBILE")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "CREATER_ID")
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
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
    @Column(name = "CREATE_DT")
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
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
    @Column(name = "BID_TYPE")
    public String getBidType() {
        return bidType;
    }

    public void setBidType(String bidType) {
        this.bidType = bidType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        STenderNotice that = (STenderNotice) o;

        if (tenderNoticeId != that.tenderNoticeId) return false;
        if (tenderTrailerId != null ? !tenderTrailerId.equals(that.tenderTrailerId) : that.tenderTrailerId != null)
            return false;
        // if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;
        if (tenderCode != null ? !tenderCode.equals(that.tenderCode) : that.tenderCode != null) return false;
        if (purchaseOwner != null ? !purchaseOwner.equals(that.purchaseOwner) : that.purchaseOwner != null)
            return false;
        if (bidCompany != null ? !bidCompany.equals(that.bidCompany) : that.bidCompany != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tenderNoticeId;
        result = 31 * result + (tenderTrailerId != null ? tenderTrailerId.hashCode() : 0);
        // result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        result = 31 * result + (tenderCode != null ? tenderCode.hashCode() : 0);
        result = 31 * result + (purchaseOwner != null ? purchaseOwner.hashCode() : 0);
        result = 31 * result + (bidCompany != null ? bidCompany.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
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
