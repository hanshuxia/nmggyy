package com.anchorcms.icloud.model.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.core.model.CmsUser;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/20
 * @Desc 能力信息表
 */
@Entity
@Table(name = "s_ability")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SAbility implements Serializable{
    public static  final String MODEL_PATH = "ability";
    private static final long serialVersionUID = -2297016465643465398L;
    private Integer abilityId;
    /*private Integer contentId;*/
    private String classifyCode;
    private Double price;
    private String abilityType;
    private String abilityName;
    private String abilityCode;
    private String unit;
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private Double referPrice;
    private String detailDesc;
    private String contact;
    private String mobile;
//    private String releaseId;
//    private String companyId;
    private String operatorId;
//    private String createrId;
    private Date releaseDt;
    private Date createDt;
    private Date updateDt;
    private String statusType;
    private String status;
    private String putawayType;
    private String deFlag;
    private String backReason;
    private String postCode;//邮编

    @Basic
    @Column(name = "CLASSIFY_CODE")
    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ABILITY_ID")
    public Integer getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(Integer abilityId) {
        this.abilityId = abilityId;
    }

  /*  @Basic
    @Column(name = "CONTENT_ID")
    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }*/
//邮编
    @Basic
    @Column(name = "POSTCODE")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Basic
    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "ABILITY_TYPE")
    public String getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(String abilityType) {
        this.abilityType = abilityType;
    }

    @Basic
    @Column(name = "ABILITY_NAME")
    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    @Basic
    @Column(name = "ABILITY_CODE")
    public String getAbilityCode() {
        return abilityCode;
    }

    public void setAbilityCode(String abilityCode) {
        this.abilityCode = abilityCode;
    }

    @Basic
    @Column(name = "UNIT")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
    @Column(name = "REFER_PRICE")
    public Double getReferPrice() {
        return referPrice;
    }

    public void setReferPrice(Double referPrice) {
        this.referPrice = referPrice;
    }

    @Basic
    @Column(name = "DETAIL_DESC")
    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
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

    @Transient
    public String getReleaseId() {
        return releaseUser.getUserId().toString();
    }

    @Transient
    public String getCompanyId() {
        return this.company.getCompanyId();
    }

    @Basic
    @Column(name = "OPERATOR_ID")
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

/*    @Basic
    @Column(name = "CREATER_ID")
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }*/

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
    @Column(name = "STATUS_TYPE")
    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "PUTAWAY_TYPE")
    public String getPutawayType() {
        return putawayType;
    }

    public void setPutawayType(String putawayType) {
        this.putawayType = putawayType;
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
    @Column(name = "BACKREASON")
    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SAbility sAbility = (SAbility) o;
        if (postCode != null ? !postCode.equals(sAbility.postCode) : sAbility.postCode != null)
            return false;
        if (abilityId != sAbility.abilityId) return false;
        if (classifyCode != null ? !classifyCode.equals(sAbility.classifyCode) : sAbility.classifyCode != null)
            return false;
        if (price != null ? !price.equals(sAbility.price) : sAbility.price != null) return false;
        if (abilityType != null ? !abilityType.equals(sAbility.abilityType) : sAbility.abilityType != null)
            return false;
        if (abilityName != null ? !abilityName.equals(sAbility.abilityName) : sAbility.abilityName != null)
            return false;
        if (unit != null ? !unit.equals(sAbility.unit) : sAbility.unit != null) return false;
        if (addrProvince != null ? !addrProvince.equals(sAbility.addrProvince) : sAbility.addrProvince != null)
            return false;
        if (addrCity != null ? !addrCity.equals(sAbility.addrCity) : sAbility.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(sAbility.addrCounty) : sAbility.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(sAbility.addrStreet) : sAbility.addrStreet != null) return false;
        if (referPrice != null ? !referPrice.equals(sAbility.referPrice) : sAbility.referPrice != null) return false;
        if (detailDesc != null ? !detailDesc.equals(sAbility.detailDesc) : sAbility.detailDesc != null) return false;
        if (contact != null ? !contact.equals(sAbility.contact) : sAbility.contact != null) return false;
        if (mobile != null ? !mobile.equals(sAbility.mobile) : sAbility.mobile != null) return false;
        //if (releaseId != null ? !releaseId.equals(sAbility.releaseId) : sAbility.releaseId != null) return false;
       // if (companyId != null ? !companyId.equals(sAbility.companyId) : sAbility.companyId != null) return false;
        if (operatorId != null ? !operatorId.equals(sAbility.operatorId) : sAbility.operatorId != null) return false;
//        if (createrId != null ? !createrId.equals(sAbility.createrId) : sAbility.createrId != null) return false;
        if (releaseDt != null ? !releaseDt.equals(sAbility.releaseDt) : sAbility.releaseDt != null) return false;
        if (createDt != null ? !createDt.equals(sAbility.createDt) : sAbility.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(sAbility.updateDt) : sAbility.updateDt != null) return false;
        if (statusType != null ? !statusType.equals(sAbility.statusType) : sAbility.statusType != null) return false;
        if (status != null ? !status.equals(sAbility.status) : sAbility.status != null) return false;
        if (putawayType != null ? !putawayType.equals(sAbility.putawayType) : sAbility.putawayType != null)
            return false;
        if (deFlag != null ? !deFlag.equals(sAbility.deFlag) : sAbility.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = abilityId;
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (classifyCode != null ? classifyCode.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (abilityType != null ? abilityType.hashCode() : 0);
        result = 31 * result + (abilityName != null ? abilityName.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (referPrice != null ? referPrice.hashCode() : 0);
        result = 31 * result + (detailDesc != null ? detailDesc.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        //result = 31 * result + (releaseId != null ? releaseId.hashCode() : 0);
        //result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
//        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (statusType != null ? statusType.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (putawayType != null ? putawayType.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
    private Content content;

    private CmsUser releaseUser;

    private List<SBigdataDemandQuote> bigQuoteList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RELEASE_ID")
    public CmsUser getReleaseUser() {
        return releaseUser;
    }

    public void setReleaseUser(CmsUser releaseUser) {
        this.releaseUser = releaseUser;
    }

    private CmsUser createUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATER_ID")
    public CmsUser getCreateUser() {
        return createUser;
    }

    public void setCreateUser(CmsUser createUser) {
        this.createUser = createUser;
    }

    /*    private Set<SAbilityInquiry> inquirySet;

    @OneToMany
    @MapsId
    @JoinColumn(name = "ability_id",insertable = false,updatable = false)
    public Set<SAbilityInquiry> getInquirySet() {
        return inquirySet;
    }

    public void setInquirySet(Set<SAbilityInquiry> inquirySet) {
        this.inquirySet = inquirySet;
    }*/

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    private SCompany company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    public SCompany getCompany() {
        return company;
    }

    public void setCompany(SCompany company) {
        this.company = company;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn( name = "ABILITY_ID")
        public List<SBigdataDemandQuote> getBigQuoteList() {
            return bigQuoteList;
    }

    public void setBigQuoteList(List<SBigdataDemandQuote> sBigdataDemandQuote) {
        this.bigQuoteList = sBigdataDemandQuote;
    }

    @Transient
    public Integer getBigdataQuoteCount(){
        List<SBigdataDemandQuote> list = getBigQuoteList();
        if (list != null) {
            return list.size();
        }else{
            return 0;
        }
    }

}
