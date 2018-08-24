package com.anchorcms.icloud.model.commservice;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 刘洋 on 2018/4/13.
 */
@Entity
@Table(name = "s_bigdata_demand_sign_up",catalog = "")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SBigDataDemandSign {
    private Integer bigdataId; // 唯一标识
    private String companyName; // 公司名称
    private String companyAddress; // 公司地址
    private String companySite; // 企业网址
    private String registrationArea; // 工商注册业务区域
    private String scopeOfBusiness; // 工商注册业务范围
    private Date registrationTime; // 成立时间
    private String registeredCapital; // 注册资金
    private String staffNumber; //职工人数
    private String legalPerson; // 企业法人
    private String duties; // 职务
    private String enterpriseNature; // 企业性质
    private String contact; // 联系人
    private String department; // 部门
    private String contactDuties; // 职务
    private String telphone; // 办公电话
    private String mobilphone; //手机
    private String email; // 邮箱

    private String enterpriseInformationization; // 企业信息化程度
    private String enterpriseIntroduction; // 企业简介
    private String companyDemand; // 企业需求
    private String agriculture; // 农牧业
    private String industry; // 工业（制造业）
    private String service; // 服务业
    private String energy; // 能源行业
    private String multipartyDataFusion; // 多方数据融合创新
    private String largeDataPublicService; // 大数据公共服务平台
    private Date creatDate; // 填表日期



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BIGDATA_ID")
    public Integer getBigdataId() {
        return bigdataId;
    }

    public void setBigdataId(Integer bigdataId) {
        this.bigdataId = bigdataId;
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
    @Column(name = "COMPANY_ADDRESS")
    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    @Basic
    @Column(name = "COMPANY_SITE")
    public String getCompanySite() {
        return companySite;
    }

    public void setCompanySite(String companySite) {
        this.companySite = companySite;
    }

    @Basic
    @Column(name = "REGISTRATION_AREA")
    public String getRegistrationArea() {
        return registrationArea;
    }

    public void setRegistrationArea(String registrationArea) {
        this.registrationArea = registrationArea;
    }

    @Basic
    @Column(name = "SCOPE_OF_BUSINESS")
    public String getScopeOfBusiness() {
        return scopeOfBusiness;
    }

    public void setScopeOfBusiness(String scopeOfBusiness) {
        this.scopeOfBusiness = scopeOfBusiness;
    }

    @Basic
    @Column(name = "REGISTRATION_TIME")
    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }


    @Basic
    @Column(name = "REGISTERED_CAPITAL")
    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }



    @Basic
    @Column(name = "STAFF_NUMBER")
    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }
    @Basic
    @Column(name = "LEGAL_PERSON")
    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    @Basic
    @Column(name = "DUTIES")
    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    @Basic
    @Column(name = "ENTERPRISE_NATURE")
    public String getEnterpriseNature() {
        return enterpriseNature;
    }

    public void setEnterpriseNature(String enterpriseNature) {
        this.enterpriseNature = enterpriseNature;
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
    @Column(name = "DEPARTMENT")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "CONTACT_DUTIES")
    public String getContactDuties() {
        return contactDuties;
    }

    public void setContactDuties(String contactDuties) {
        this.contactDuties = contactDuties;
    }
    @Basic
    @Column(name = "TELPHONE")
    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    @Basic
    @Column(name = "MOBILEPHONE")
    public String getMobilphone() {
        return mobilphone;
    }

    public void setMobilphone(String mobilphone) {
        this.mobilphone = mobilphone;
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
    @Column(name = "ENTERPRISE_INFORMATIONIZATION")
    public String getEnterpriseInformationization() {
        return enterpriseInformationization;
    }

    public void setEnterpriseInformationization(String enterpriseInformationization) {
        this.enterpriseInformationization = enterpriseInformationization;
    }

    @Basic
    @Column(name = "ENTERPRISE_INTRODUCTION")
    public String getEnterpriseIntroduction() {
        return enterpriseIntroduction;
    }

    public void setEnterpriseIntroduction(String enterpriseIntroduction) {
        this.enterpriseIntroduction = enterpriseIntroduction;
    }

    @Basic
    @Column(name = "COMPANY_DEMAND")
    public String getCompanyDemand() {
        return companyDemand;
    }

    public void setCompanyDemand(String companyDemand) {
        this.companyDemand = companyDemand;
    }

    @Basic
    @Column(name = "AGRICULTURE")
    public String getAgriculture() {
        return agriculture;
    }

    public void setAgriculture(String agriculture) {
        this.agriculture = agriculture;
    }

    @Basic
    @Column(name = "INDUSTRY")
    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Basic
    @Column(name = "SERVICE")
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Basic
    @Column(name = "ENERGY")
    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }


    @Basic
    @Column(name = "MULTIPARTY_DATA_FUSION")
    public String getMultipartyDataFusion() {
        return multipartyDataFusion;
    }

    public void setMultipartyDataFusion(String multipartyDataFusion) {
        this.multipartyDataFusion = multipartyDataFusion;
    }

    @Basic
    @Column(name = "LARGE_DATA_PUBLIC_SERVICE")
    public String getLargeDataPublicService() {
        return largeDataPublicService;
    }

    public void setLargeDataPublicService(String largeDataPublicService) {
        this.largeDataPublicService = largeDataPublicService;
    }


    @Basic
    @Column(name = "CREAT_DATA")
    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SBigDataDemandSign that = (SBigDataDemandSign) o;

        if (bigdataId != that.bigdataId) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (companyAddress != null ? !companyAddress.equals(that.companyAddress) : that.companyAddress != null) return false;
        if (companySite != null ? !companySite.equals(that.companySite) : that.companySite != null) return false;
        if (registrationArea != null ? !registrationArea.equals(that.registrationArea) : that.registrationArea != null) return false;
        if (scopeOfBusiness != null ? !scopeOfBusiness.equals(that.scopeOfBusiness) : that.scopeOfBusiness != null) return false;
        if (registrationTime != null ? !registrationTime.equals(that.registrationTime) : that.registrationTime != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (registeredCapital != null ? !registeredCapital.equals(that.registeredCapital) : that.registeredCapital != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (staffNumber != null ? !staffNumber.equals(that.staffNumber) : that.staffNumber != null) return false;
        if (legalPerson != null ? !legalPerson.equals(that.legalPerson) : that.legalPerson != null) return false;
        if (duties != null ? !duties.equals(that.duties) : that.duties != null) return false;
        if (enterpriseNature != null ? !enterpriseNature.equals(that.enterpriseNature) : that.enterpriseNature != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        if (contactDuties != null ? !contactDuties.equals(that.contactDuties) : that.contactDuties != null) return false;
        if (telphone != null ? !telphone.equals(that.telphone) : that.telphone != null) return false;
        if (mobilphone != null ? !mobilphone.equals(that.mobilphone) : that.mobilphone != null) return false;
        if (enterpriseInformationization != null ? !enterpriseInformationization.equals(that.enterpriseInformationization) : that.enterpriseInformationization != null) return false;
        if (enterpriseIntroduction != null ? !enterpriseIntroduction.equals(that.enterpriseIntroduction) : that.enterpriseIntroduction != null) return false;
        if (companyDemand != null ? !companyDemand.equals(that.companyDemand) : that.companyDemand != null) return false;
        if (agriculture != null ? !agriculture.equals(that.agriculture) : that.agriculture != null) return false;
        if (industry != null ? !industry.equals(that.industry) : that.industry != null) return false;
        if (service != null ? !service.equals(that.service) : that.service != null) return false;
        if (energy != null ? !energy.equals(that.energy) : that.energy != null) return false;
        if (multipartyDataFusion != null ? !multipartyDataFusion.equals(that.multipartyDataFusion) : that.multipartyDataFusion != null) return false;
        if (largeDataPublicService != null ? !largeDataPublicService.equals(that.largeDataPublicService) : that.largeDataPublicService != null) return false;
        if (enterpriseIntroduction != null ? !enterpriseIntroduction.equals(that.enterpriseIntroduction) : that.enterpriseIntroduction != null) return false;
        if (creatDate != null ? !creatDate.equals(that.creatDate) : that.creatDate != null) return false;

        return true;
    }

    public int hashCode() {
        int result = bigdataId;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (companyAddress != null ? companyAddress.hashCode() : 0);
        result = 31 * result + (companySite != null ? companySite.hashCode() : 0);
        result = 31 * result + (registrationArea != null ? registrationArea.hashCode() : 0);
        result = 31 * result + (scopeOfBusiness != null ? scopeOfBusiness.hashCode() : 0);
        result = 31 * result + (registrationTime != null ? registrationTime.hashCode() : 0);
        result = 31 * result + (registeredCapital != null ? registeredCapital.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (staffNumber != null ? staffNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (legalPerson != null ? legalPerson.hashCode() : 0);
        result = 31 * result + (duties != null ? duties.hashCode() : 0);
        result = 31 * result + (enterpriseNature != null ? enterpriseNature.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (contactDuties != null ? contactDuties.hashCode() : 0);
        result = 31 * result + (telphone != null ? telphone.hashCode() : 0);
        result = 31 * result + (mobilphone != null ? mobilphone.hashCode() : 0);
        result = 31 * result + (enterpriseInformationization != null ? enterpriseInformationization.hashCode() : 0);
        result = 31 * result + (enterpriseIntroduction != null ? enterpriseIntroduction.hashCode() : 0);
        result = 31 * result + (companyDemand != null ? companyDemand.hashCode() : 0);
        result = 31 * result + (agriculture != null ? agriculture.hashCode() : 0);
        result = 31 * result + (industry != null ? industry.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (energy != null ? energy.hashCode() : 0);
        result = 31 * result + (multipartyDataFusion != null ? multipartyDataFusion.hashCode() : 0);
        result = 31 * result + (largeDataPublicService != null ? largeDataPublicService.hashCode() : 0);
        result = 31 * result + (enterpriseIntroduction != null ? enterpriseIntroduction.hashCode() : 0);
        result = 31 * result + (creatDate != null ? creatDate.hashCode() : 0);
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

