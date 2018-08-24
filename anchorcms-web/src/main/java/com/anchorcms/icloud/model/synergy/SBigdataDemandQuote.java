package com.anchorcms.icloud.model.synergy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/29 0029
 * @Desc 报价信息表
 */
@Entity
@Table(name = "s_bigdata_demand_quote")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SBigdataDemandQuote {
    private int demandQuoteId;
    //private int demandId;
    private String companyCode;
    private String companyName;
    private String companyType;
    private String companyScale;
    private String serverType; //服务类型
    private String industryType; //行业分类
    private String operateType;
    private String mainProduct;
    private Integer deviceCount;
    private String mobile;
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private Date registerDt;
    private String createId;
    private Date createDt;
    private Date updateDt;
    private Date releaseDt;
    private String deFlag;
    private Integer productCount;

    private String isRecommend;
    private String status;
    private String backReason;
    private String postCode;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEMAND_QUOTE_ID")
    public int getDemandQuoteId() {
        return demandQuoteId;
    }

    public void setDemandQuoteId(int demandQuoteId) {
        this.demandQuoteId = demandQuoteId;
    }
    @Basic
    @Column(name = "POSTCODE")
    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Basic
    @Column(name = "ISRECOMMEND")
    public String getIsRecommend() {
        return isRecommend;
    }

    @Basic
    @Column(name = "ISRECOMMEND")
    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }



    @Basic
    @Column(name = "COMPANY_CODE")
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Basic
    @Column(name = "COMPANY_NAME")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "COMPANY_TYPE")
    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    @Basic
    @Column(name = "COMPANY_SCALE")
    public String getCompanyScale() {
        return companyScale;
    }

    public void setCompanyScale(String companyScale) {
        this.companyScale = companyScale;
    }

    @Basic
    @Column(name = "SERVER_TYPE")
    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    @Basic
    @Column(name = "OPERATE_TYPE")
    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    @Basic
    @Column(name = "INDUSTRY_TYPE")
    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    @Basic
    @Column(name = "MAIN_PRODUCT")
    public String getMainProduct() {
        return mainProduct;
    }

    public void setMainProduct(String mainProduct) {
        this.mainProduct = mainProduct;
    }

    @Basic
    @Column(name = "DEVICE_COUNT")
    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
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
    @Column(name = "REGISTER_DT")
    public Date getRegisterDt() {
        return registerDt;
    }

    public void setRegisterDt(Date registerDt) {
        this.registerDt = registerDt;
    }

    @Basic
    @Column(name = "CREATE_ID")
    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
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
    @Column(name = "RELEASE_DT")
    public Date getReleaseDt() {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
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
    @Column(name = "PRODUCT_COUNT")
    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }


    @Basic
    @Column(name = "STATUS")
    public String getStatus() { return status;}

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "BACKREASON")
    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) { this.backReason = backReason;}

    @Transient
    public int getDemandId() {
        return demand.getDemandId();
    }
    @Transient
    public int getAbilityId() {
        return ability.getAbilityId();
    }
    @Transient
    public String getCompanyId() {
        return this.getCompany().getCompanyId();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SBigdataDemandQuote sBigdataDemandQuote = (SBigdataDemandQuote) o;

        if (demandQuoteId != sBigdataDemandQuote.demandQuoteId) return false;
        //if (demandId != that.demandId) return false;
        if (postCode != null ? !postCode.equals(sBigdataDemandQuote.postCode) : sBigdataDemandQuote.postCode != null)
            return false;
        if (companyCode != null ? !companyCode.equals(sBigdataDemandQuote.companyCode) : sBigdataDemandQuote.companyCode != null)
            return false;
        if (companyName != null ? !companyName.equals(sBigdataDemandQuote.companyName) : sBigdataDemandQuote.companyName != null)
            return false;
        if (companyType != null ? !companyType.equals(sBigdataDemandQuote.companyType) : sBigdataDemandQuote.companyType != null)
            return false;
        if (serverType != null ? !serverType.equals(sBigdataDemandQuote.serverType) : sBigdataDemandQuote.serverType != null)
            return false;
        if (industryType != null ? !industryType.equals(sBigdataDemandQuote.industryType) : sBigdataDemandQuote.industryType != null)
            return false;
        if (operateType != null ? !operateType.equals(sBigdataDemandQuote.operateType) : sBigdataDemandQuote.operateType != null)
            return false;
        if (mainProduct != null ? !mainProduct.equals(sBigdataDemandQuote.mainProduct) : sBigdataDemandQuote.mainProduct != null)
            return false;
        if (deviceCount != null ? !deviceCount.equals(sBigdataDemandQuote.deviceCount) : sBigdataDemandQuote.deviceCount != null)
            return false;
        if (mobile != null ? !mobile.equals(sBigdataDemandQuote.mobile) : sBigdataDemandQuote.mobile != null) return false;
        if (addrProvince != null ? !addrProvince.equals(sBigdataDemandQuote.addrProvince) : sBigdataDemandQuote.addrProvince != null)
            return false;
        if (addrCity != null ? !addrCity.equals(sBigdataDemandQuote.addrCity) : sBigdataDemandQuote.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(sBigdataDemandQuote.addrCounty) : sBigdataDemandQuote.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(sBigdataDemandQuote.addrStreet) : sBigdataDemandQuote.addrStreet != null) return false;
        if (registerDt != null ? !registerDt.equals(sBigdataDemandQuote.registerDt) : sBigdataDemandQuote.registerDt != null) return false;
        if (createId != null ? !createId.equals(sBigdataDemandQuote.createId) : sBigdataDemandQuote.createId != null) return false;
        if (createDt != null ? !createDt.equals(sBigdataDemandQuote.createDt) : sBigdataDemandQuote.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(sBigdataDemandQuote.updateDt) : sBigdataDemandQuote.updateDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(sBigdataDemandQuote.releaseDt) : sBigdataDemandQuote.releaseDt != null) return false;
        if (deFlag != null ? !deFlag.equals(sBigdataDemandQuote.deFlag) : sBigdataDemandQuote.deFlag != null) return false;
        if (status != null ? !status.equals(sBigdataDemandQuote.status) : sBigdataDemandQuote.status != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = demandQuoteId;
        //result = 31 * result + demandId;
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (companyCode != null ? companyCode.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (companyType != null ? companyType.hashCode() : 0);
        result = 31 * result + (serverType != null ? serverType.hashCode() : 0);
        result = 31 * result + (industryType != null ? industryType.hashCode() : 0);
        result = 31 * result + (operateType != null ? operateType.hashCode() : 0);
        result = 31 * result + (mainProduct != null ? mainProduct.hashCode() : 0);
        result = 31 * result + (deviceCount != null ? deviceCount.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (registerDt != null ? registerDt.hashCode() : 0);
        result = 31 * result + (createId != null ? createId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
//        result = 31 * result + (ability != null ? ability.hashCode() : 0);
        return result;
    }
    private SDemand demand;
    private SCompany company;
    private SAbility ability;

    private List<SBigdataDemandQuote> bigQuoteList;

    @ManyToOne(targetEntity = SDemand.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "DEMAND_ID")
    public SDemand getDemand() {
        return demand;
    }

    public void setDemand(SDemand demand) {
        this.demand = demand;
    }

    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() { return company; }

    public void setCompany(SCompany company) { this.company = company; }
    @ManyToOne
    @JoinColumn(name = "ABILITY_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    public SAbility getAbility() {
        return ability;
    }

    public void setAbility(SAbility ability) {
        this.ability = ability;
    }
}







