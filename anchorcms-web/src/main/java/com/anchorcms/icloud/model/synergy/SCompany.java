package com.anchorcms.icloud.model.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/26
 * @Desc 企业信息表
 */
@Entity
@Table(name = "s_company")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SCompany implements Serializable{
    private static final long serialVersionUID = -6918770265219858339L;
    private String companyId;
    private String companyCode;
    private String companyName;
    private String companyType;
    private String companyScale;
    private String serverType; //服务类型
    private String industryType; //行业分类
    private String operateType;
    private String mainProduct;
    private Integer deviceCount;
    private String sites;
    private String email;
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


    @Id
    @Column(name = "COMPANY_ID")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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
    @Column(name = "SITES")
    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SCompany sCompany = (SCompany) o;

        if (companyId != sCompany.companyId) return false;
        if (postCode != null ? !postCode.equals(sCompany.postCode) : sCompany.postCode != null)
            return false;
        if (companyCode != null ? !companyCode.equals(sCompany.companyCode) : sCompany.companyCode != null)
            return false;
        if (companyName != null ? !companyName.equals(sCompany.companyName) : sCompany.companyName != null)
            return false;
        if (companyType != null ? !companyType.equals(sCompany.companyType) : sCompany.companyType != null)
            return false;
        if (serverType != null ? !serverType.equals(sCompany.serverType) : sCompany.serverType != null)
            return false;
        if (industryType != null ? !industryType.equals(sCompany.industryType) : sCompany.industryType != null)
            return false;
        if (operateType != null ? !operateType.equals(sCompany.operateType) : sCompany.operateType != null)
            return false;
        if (mainProduct != null ? !mainProduct.equals(sCompany.mainProduct) : sCompany.mainProduct != null)
            return false;
        if (deviceCount != null ? !deviceCount.equals(sCompany.deviceCount) : sCompany.deviceCount != null)
            return false;
        if (sites != null ? !sites.equals(sCompany.sites) : sCompany.sites != null) return false;
        if (email != null ? !email.equals(sCompany.email) : sCompany.email != null) return false;
        if (mobile != null ? !mobile.equals(sCompany.mobile) : sCompany.mobile != null) return false;
        if (addrProvince != null ? !addrProvince.equals(sCompany.addrProvince) : sCompany.addrProvince != null)
            return false;
        if (addrCity != null ? !addrCity.equals(sCompany.addrCity) : sCompany.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(sCompany.addrCounty) : sCompany.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(sCompany.addrStreet) : sCompany.addrStreet != null) return false;
        if (registerDt != null ? !registerDt.equals(sCompany.registerDt) : sCompany.registerDt != null) return false;
        if (createId != null ? !createId.equals(sCompany.createId) : sCompany.createId != null) return false;
        if (createDt != null ? !createDt.equals(sCompany.createDt) : sCompany.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(sCompany.updateDt) : sCompany.updateDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(sCompany.releaseDt) : sCompany.releaseDt != null) return false;
        if (deFlag != null ? !deFlag.equals(sCompany.deFlag) : sCompany.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyId != null ? companyId.hashCode() : 0;
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (companyCode != null ? companyCode.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (companyType != null ? companyType.hashCode() : 0);
        result = 31 * result + (serverType != null ? serverType.hashCode() : 0);
        result = 31 * result + (industryType != null ? industryType.hashCode() : 0);
        result = 31 * result + (operateType != null ? operateType.hashCode() : 0);
        result = 31 * result + (mainProduct != null ? mainProduct.hashCode() : 0);
        result = 31 * result + (deviceCount != null ? deviceCount.hashCode() : 0);
        result = 31 * result + (sites != null ? sites.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
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
        return result;
    }
    private Set<CmsUser> users;
    private Set<SAbility> abilities;
    private Set<SCompanyDevice> devices;
    private Set<SCompanyDiploma> diplomas;
    private Set<SRepairRes> sRepairRes;
    private Set<SCompanyAptitude> aptitude;
    private Set<SDemand> demands;
    private Set<SSpare> spares;
    private Set<SSpareDemand> spareDemands;
    private Set<SRepairDemand> repairDemands;

    @OneToMany
    @JoinColumn(name = "company_id")
    public Set<SCompanyDiploma> getDiplomas() {
        return diplomas;
    }

    public void setDiplomas(Set<SCompanyDiploma> diplomas) {
        this.diplomas = diplomas;
    }

    @OneToMany
    @JoinColumn(name = "company_id")
    public Set<SDemand> getDemands() {
        return demands;
    }

    public void setDemands(Set<SDemand> demands) {
        this.demands = demands;
    }

    @OneToMany
    @JoinColumn(name = "company_id")
    public Set<SSpare> getSpares() {
        return spares;
    }

    public void setSpares(Set<SSpare> spares) {
        this.spares = spares;
    }

    @OneToMany
    @JoinColumn(name = "company_id")
    public Set<SSpareDemand> getSpareDemands() {
        return spareDemands;
    }

    public void setSpareDemands(Set<SSpareDemand> spareDemands) {
        this.spareDemands = spareDemands;
    }

    @OneToMany
    @JoinColumn(name = "company_id")
    public Set<SRepairDemand> getRepairDemands() {
        return repairDemands;
    }

    public void setRepairDemands(Set<SRepairDemand> repairDemands) {
        this.repairDemands = repairDemands;
    }

    @OneToMany
    @JoinColumn(name = "company_id")
    public Set<SCompanyDevice> getDevices() {
        return devices;
    }

    public void setDevices(Set<SCompanyDevice> devices) {
        this.devices = devices;
    }



    @OneToMany
    @JoinColumn(name = "company_id")
    public Set<SAbility> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<SAbility> abilities) {
        this.abilities = abilities;
    }

    @OneToMany
    @JoinColumn(name = "company_id")
    public Set<SRepairRes> getsRepairRes() {
        return sRepairRes;
    }

    public void setsRepairRes(Set<SRepairRes> sRepairRes) {
        this.sRepairRes = sRepairRes;
    }

    @OneToMany
    @JoinColumn(name = "company_id",insertable = false,updatable = false)
    public Set<CmsUser> getUsers() {
        return users;
    }

    public void setUsers(Set<CmsUser> users) {
        this.users = users;
    }

    @OneToMany
    @JoinColumn(name = "COMPANY_ID")
    public Set<SCompanyAptitude> getAptitude() {
        return aptitude;
    }

    public void setAptitude(Set<SCompanyAptitude> aptitude) {
        this.aptitude = aptitude;
    }

    private Content content;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTENT_ID")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Transient
    public Integer getAbilityCount(){
        Set<SAbility> set = getAbilities();
        if(set != null){
            return set.size();
        }else {
            return 0;
        }
    }

    @Transient
    public Integer getDemandCount(){
        Set<SDemand> set = getDemands();
        if(set != null){
            return set.size();
        }else {
            return 0;
        }
    }



}
