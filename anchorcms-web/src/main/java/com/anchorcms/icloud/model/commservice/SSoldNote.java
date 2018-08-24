package com.anchorcms.icloud.model.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SDemandQuote;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.swing.text.AbstractDocument;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @author machao
 * @Date 2017/1/14 17:52
 * @return
 * 销售记录信息表
 */
@Entity
@Table(name = "s_sold_note")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SSoldNote implements Serializable {
    private static final long serialVersionUID = -7598337379606480439L;
    private int soldNoteId;//唯一标示
    private String companyName;//公司名称
    private String wareType;//销售产品种类
    private String amount;//订单销售额
    private String charger;//订单负责人
    private String transportInfo;//发货运输信息
    private String salesArea;//销往地区
    private String companyInfo;//客户公司基础信息
    private String contact;//联系人
    private String mobile;//联系电话
    private String remark;//备注
    private String hardCopyPath;//合同发票复印件
    private Date dealDt;//订单成交日期
    private Integer createrId;//创建人
    private Date createrDt;//创建时间
    private Date updateDt;//更新时间
    private String deFlag;//逻辑判断
    private Integer contentId;
    private String addrProvince;//地址（省）
    private String addrCity;//地址（地级市）
    private String addrCounty;//地址（市、县级）
    private String addrStreet;//街道信息
    private String fax;//传真
    private String email;//邮箱
    private String telephone;//TELEPHONE


    @Id
    @Column(name = "SOLD_NOTE_ID")
    public int getSoldNoteId() {
        return soldNoteId;
    }

    public void setSoldNoteId(int soldNoteId) {
        this.soldNoteId = soldNoteId;
    }

    @Basic
    @Column(name = "COMPANY")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "WARE_TYPE")
    public String getWareType() {
        return wareType;
    }

    public void setWareType(String wareType) {
        this.wareType = wareType;
    }

    @Basic
    @Column(name = "AMOUNT")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "CHARGER")
    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    @Basic
    @Column(name = "TRANSPORT_INFO")
    public String getTransportInfo() {
        return transportInfo;
    }

    public void setTransportInfo(String transportInfo) {
        this.transportInfo = transportInfo;
    }

    @Basic
    @Column(name = "SALES_AREA")
    public String getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
    }

    @Basic
    @Column(name = "COMPANY_INFO")
    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
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
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "HARD_COPY_PATH")
    public String getHardCopyPath() {
        return hardCopyPath;
    }

    public void setHardCopyPath(String hardCopyPath) {
        this.hardCopyPath = hardCopyPath;
    }

    @Basic
    @Column(name = "DEAL_DT")
    public Date getDealDt() {
        return dealDt;
    }

    public void setDealDt(Date dealDt) {
        this.dealDt = dealDt;
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
    @Column(name = "ADDR_PROVINCE", nullable = true, length = 6)
    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    @Basic
    @Column(name = "ADDR_CITY", nullable = true, length = 6)
    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    @Basic
    @Column(name = "ADDR_COUNTY", nullable = true, length = 6)
    public String getAddrCounty() {
        return addrCounty;
    }

    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }

    @Basic
    @Column(name = "ADDR_STREET", nullable = true, length = 100)
    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

    @Basic
    @Column(name = "FAX", nullable = true, length = 32)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "EMAIL", nullable = true, length = 32)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SSoldNote sSoldNote = (SSoldNote) o;

        if (soldNoteId != sSoldNote.soldNoteId) return false;
        if (company != null ? !company.equals(sSoldNote.company) : sSoldNote.company != null) return false;
        if (wareType != null ? !wareType.equals(sSoldNote.wareType) : sSoldNote.wareType != null) return false;
        if (amount != null ? !amount.equals(sSoldNote.amount) : sSoldNote.amount != null) return false;
        if (charger != null ? !charger.equals(sSoldNote.charger) : sSoldNote.charger != null) return false;
        if (transportInfo != null ? !transportInfo.equals(sSoldNote.transportInfo) : sSoldNote.transportInfo != null)
            return false;
        if (salesArea != null ? !salesArea.equals(sSoldNote.salesArea) : sSoldNote.salesArea != null) return false;
        if (companyInfo != null ? !companyInfo.equals(sSoldNote.companyInfo) : sSoldNote.companyInfo != null)
            return false;
        if (contact != null ? !contact.equals(sSoldNote.contact) : sSoldNote.contact != null) return false;
        if (mobile != null ? !mobile.equals(sSoldNote.mobile) : sSoldNote.mobile != null) return false;
        if (remark != null ? !remark.equals(sSoldNote.remark) : sSoldNote.remark != null) return false;
        if (hardCopyPath != null ? !hardCopyPath.equals(sSoldNote.hardCopyPath) : sSoldNote.hardCopyPath != null)
            return false;
        if (dealDt != null ? !dealDt.equals(sSoldNote.dealDt) : sSoldNote.dealDt != null) return false;
        if (createrId != null ? !createrId.equals(sSoldNote.createrId) : sSoldNote.createrId != null) return false;
        if (createrDt != null ? !createrDt.equals(sSoldNote.createrDt) : sSoldNote.createrDt != null) return false;
        if (updateDt != null ? !updateDt.equals(sSoldNote.updateDt) : sSoldNote.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(sSoldNote.deFlag) : sSoldNote.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = soldNoteId;
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (wareType != null ? wareType.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (charger != null ? charger.hashCode() : 0);
        result = 31 * result + (transportInfo != null ? transportInfo.hashCode() : 0);
        result = 31 * result + (salesArea != null ? salesArea.hashCode() : 0);
        result = 31 * result + (companyInfo != null ? companyInfo.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (hardCopyPath != null ? hardCopyPath.hashCode() : 0);
        result = 31 * result + (dealDt != null ? dealDt.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createrDt != null ? createrDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }

    private Content content;
    private String status;//状态判断

    @OneToOne
    @JoinColumn(name = "content_id",insertable = false,updatable = false)
    public Content getContent() {
        return content;
    }
    public void setContent(Content content) {
        this.content = content;
    }

    @Basic
    @Column(name = "CONTENT_ID")
    public Integer getContentId() {return contentId; }

    public void setContentId(Integer contentId) { this.contentId = contentId; }

    @Basic
    @Column(name = "STATUS", nullable = false, length = 60)
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    private SCompany company;

    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() { return company; }

    public void setCompany(SCompany company) { this.company = company; }
}
