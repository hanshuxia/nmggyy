package com.anchorcms.icloud.model.commservice;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by ${user} on 2017/1/15.
 */
@Entity
@Table(name = "s_bid_notice")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SBidNotice implements Serializable{
    private static final long serialVersionUID = -327667914650192945L;


    private int bidNoticeId;                //主键
    private String projectName;             //项目名称
    private Integer tenderNoticeId;         //招标公告id
    private String bidInfo;                   //中标方信息
    private String bidName;                   //中标方名称
    private String bidSum;                    //  中标金额
    private Date pactDt;                      //  合同履行如期
    private String tenderCode;   //招标编号
    private String purchaseOwner;//采购业主
    private String bidCompany;//招标公司
    private String contact;//联系人
    private String mobile;//联系电话
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;//通讯地址
    private Date deadlineDt;//截止日期
    private String bidContact;//中标方联系人
    private String bidTel;//中标方电话
    private String bidEml;//中标方邮箱
    private String bidFax;//中标方传真
    private String bidaddrProvince;
    private String bidaddrCity;
    private String bidaddrCounty;//通讯地址
    private String bidAdd;//中标方地址街道
    private Date releaseDt;//发布日期

    @Id
    @Column(name = "BID_NOTICE_ID")
    public int getBidNoticeId() {
        return bidNoticeId;
    }

    public void setBidNoticeId(int bidNoticeId) {
        this.bidNoticeId = bidNoticeId;
    }


    @Basic
    @Column(name = "PROJECTNAME")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @Column(name = "TENDER_NOTICE_ID")
    public Integer getTenderNoticeId() {
        return tenderNoticeId;
    }

    public void setTenderNoticeId(Integer tenderNoticeId) {
        this.tenderNoticeId = tenderNoticeId;
    }


    @Basic
    @Column(name = "BID_INFO")
    public String getBidInfo() {
        return bidInfo;
    }

    public void setBidInfo(String bidInfo) {
        this.bidInfo = bidInfo;
    }

    @Basic
    @Column(name = "BID_NAME")
    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName;
    }

    @Basic
    @Column(name = "BID_SUM")
    public String getBidSum() {
        return bidSum;
    }

    public void setBidSum(String bidSum) {
        this.bidSum = bidSum;
    }

    @Basic
    @Column(name = "PACT_DT")
    public Date getPactDt() {
        return pactDt;
    }

    public void setPactDt(Date pactDt) {
        this.pactDt = pactDt;
    }

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
    @Column(name = "BID_CONTACT")
    public String getBidContact() {
        return bidContact;
    }

    public void setBidContact(String bidContact) {
        this.bidContact = bidContact;
    }

    @Basic
    @Column(name = "BID_TEL")
    public String getBidTel() {
        return bidTel;
    }

    public void setBidTel(String bidTel) {
        this.bidTel = bidTel;
    }

    @Basic
    @Column(name = "BID_EML")
    public String getBidEml() {
        return bidEml;
    }

    public void setBidEml(String bidEml) {
        this.bidEml = bidEml;
    }

    @Basic
    @Column(name = "BID_FAX")
    public String getBidFax() {
        return bidFax;
    }

    public void setBidFax(String bidFax) {
        this.bidFax = bidFax;
    }

    @Basic
    @Column(name = "BID_ADD")
    public String getBidAdd() {
        return bidAdd;
    }

    public void setBidAdd(String bidAdd) {
        this.bidAdd = bidAdd;
    }
    @Basic
    @Column(name = "BID_ADDR_PROVINCE")
    public String getBidaddrProvince() {
        return bidaddrProvince;
    }

    public void setBidaddrProvince(String bidaddrProvince) {
        this.bidaddrProvince = bidaddrProvince;
    }
    @Basic
    @Column(name = "BID_ADDR_CITY")
    public String getBidaddrCity() {
        return bidaddrCity;
    }

    public void setBidaddrCity(String bidaddrCity) {
        this.bidaddrCity = bidaddrCity;
    }
    @Basic
    @Column(name = "BID_ADDR_COUNTRY")
    public String getBidaddrCounty() {
        return bidaddrCounty;
    }

    public void setBidaddrCounty(String bidaddrCounty) {
        this.bidaddrCounty = bidaddrCounty;
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

    public void setAddrCounty(String addrCounty) {this.addrCounty = addrCounty;}
    @Basic
    @Column(name = "RELEASE_DT")
    public Date getReleaseDt() {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SBidNotice that = (SBidNotice) o;

        if (bidNoticeId != that.bidNoticeId) return false;
        if (tenderNoticeId != null ? !tenderNoticeId.equals(that.tenderNoticeId) : that.tenderNoticeId != null)
            return false;
        if (bidInfo != null ? !bidInfo.equals(that.bidInfo) : that.bidInfo != null) return false;
        if (bidName != null ? !bidName.equals(that.bidName) : that.bidName != null) return false;
        if (bidSum != null ? !bidSum.equals(that.bidSum) : that.bidSum != null) return false;
        if (pactDt != null ? !pactDt.equals(that.pactDt) : that.pactDt != null) return false;
        if (tenderCode != null ? !tenderCode.equals(that.tenderCode) : that.tenderCode != null) return false;
        if (purchaseOwner != null ? !purchaseOwner.equals(that.purchaseOwner) : that.purchaseOwner != null)
            return false;
        if (bidCompany != null ? !bidCompany.equals(that.bidCompany) : that.bidCompany != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (bidContact != null ? !bidContact.equals(that.bidContact) : that.bidContact != null) return false;
        if (bidTel != null ? !bidTel.equals(that.bidTel) : that.bidTel != null) return false;
        if (bidEml != null ? !bidEml.equals(that.bidEml) : that.bidEml != null) return false;
        if (bidFax != null ? !bidFax.equals(that.bidFax) : that.bidFax != null) return false;
        if (bidAdd != null ? !bidAdd.equals(that.bidAdd) : that.bidAdd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bidNoticeId;
        result = 31 * result + (tenderNoticeId != null ? tenderNoticeId.hashCode() : 0);
        result = 31 * result + (bidInfo != null ? bidInfo.hashCode() : 0);
        result = 31 * result + (bidName != null ? bidName.hashCode() : 0);
        result = 31 * result + (bidSum != null ? bidSum.hashCode() : 0);
        result = 31 * result + (pactDt != null ? pactDt.hashCode() : 0);
        result = 31 * result + (tenderCode != null ? tenderCode.hashCode() : 0);
        result = 31 * result + (purchaseOwner != null ? purchaseOwner.hashCode() : 0);
        result = 31 * result + (bidCompany != null ? bidCompany.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (bidContact != null ? bidContact.hashCode() : 0);
        result = 31 * result + (bidTel != null ? bidTel.hashCode() : 0);
        result = 31 * result + (bidEml != null ? bidEml.hashCode() : 0);
        result = 31 * result + (bidFax != null ? bidFax.hashCode() : 0);
        result = 31 * result + (bidAdd != null ? bidAdd.hashCode() : 0);
        return result;
    }

    private Content content;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

}
