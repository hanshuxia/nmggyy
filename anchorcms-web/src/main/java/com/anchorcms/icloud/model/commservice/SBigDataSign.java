package com.anchorcms.icloud.model.commservice;


import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ly on 2017/8/3.
 */
@Entity
@Table(name = "s_bigdata_sign_up",catalog = "")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SBigDataSign {
    private static final long serialVersionUID = -2463881652702568118L;
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
    private String isInBigdata; // 是否加入大数据组织
    private String organizationalLevel; // 组织级别
    private String organizationalName; // 组织名称
    private String serviceDirection; // 产业融合服务方向
    private String serviceCompanyType; // 服务提供方企业类型
    private String dataSourse; // 数据源
    private String dataCirculationPlatform; // 数据流通平台
    private String hardware; // 硬件
    private String basicSoftware; // 基础软件
    private String applicationSoftware; // 应用软件
    private String infrastructure; // 基础设施
    private String systemIntegration; // 系统集成和软件开发
    private String enterpriseApplication; // 企业应用
    private String enterpriseIntroduction; // 企业简介
    private String excellentCase; // 企业优秀案例
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
    @Column(name = "IS_IN_BIGDATA")
    public String getIsInBigdata() {
        return isInBigdata;
    }

    public void setIsInBigdata(String isInBigdata) {
        this.isInBigdata = isInBigdata;
    }

    @Basic
    @Column(name = "ORGANIZATIONAL_LEVEL")
    public String getOrganizationalLevel() {
        return organizationalLevel;
    }

    public void setOrganizationalLevel(String organizationalLevel) {
        this.organizationalLevel = organizationalLevel;
    }

    @Basic
    @Column(name = "ORGANIZATIONAL_NAME")
    public String getOrganizationalName() {
        return organizationalName;
    }

    public void setOrganizationalName(String organizationalName) {
        this.organizationalName = organizationalName;
    }

    @Basic
    @Column(name = "SERVICE_DIRECTION")
    public String getServiceDirection() {
        return serviceDirection;
    }

    public void setServiceDirection(String serviceDirection) {
        this.serviceDirection = serviceDirection;
    }

    @Basic
    @Column(name = "SERVICE_COMPANY_TYPE")
    public String getServiceCompanyType() {
        return serviceCompanyType;
    }

    public void setServiceCompanyType(String serviceCompanyType) {
        this.serviceCompanyType = serviceCompanyType;
    }

    @Basic
    @Column(name = "DATA_SOURCE")
    public String getDataSourse() {
        return dataSourse;
    }

    public void setDataSourse(String dataSourse) {
        this.dataSourse = dataSourse;
    }

    @Basic
    @Column(name = "DATA_CIRCULATION_PLATFORM")
    public String getDataCirculationPlatform() {
        return dataCirculationPlatform;
    }

    public void setDataCirculationPlatform(String dataCirculationPlatform) {
        this.dataCirculationPlatform = dataCirculationPlatform;
    }

    @Basic
    @Column(name = "HARDWARE")
    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    @Basic
    @Column(name = "BASIC_SOFTWARE")
    public String getBasicSoftware() {
        return basicSoftware;
    }

    public void setBasicSoftware(String basicSoftware) {
        this.basicSoftware = basicSoftware;
    }

    @Basic
    @Column(name = "APPLICATION_SOFTWARE")
    public String getApplicationSoftware() {
        return applicationSoftware;
    }

    public void setApplicationSoftware(String applicationSoftware) {
        this.applicationSoftware = applicationSoftware;
    }

    @Basic
    @Column(name = "INFRASTRUCTURE")
    public String getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(String infrastructure) {
        this.infrastructure = infrastructure;
    }

    @Basic
    @Column(name = "SYSTEM_INTEGRATION")
    public String getSystemIntegration() {
        return systemIntegration;
    }

    public void setSystemIntegration(String systemIntegration) {
        this.systemIntegration = systemIntegration;
    }


    @Basic
    @Column(name = "ENTERPRISE_APPLICATION")
    public String getEnterpriseApplication() {
        return enterpriseApplication;
    }

    public void setEnterpriseApplication(String enterpriseApplication) {
        this.enterpriseApplication = enterpriseApplication;
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
    @Column(name = "EXCELLENT_CASE")
    public String getExcellentCase() {
        return excellentCase;
    }

    public void setExcellentCase(String excellentCase) {
        this.excellentCase = excellentCase;
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

        SBigDataSign that = (SBigDataSign) o;

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
        if (isInBigdata != null ? !isInBigdata.equals(that.isInBigdata) : that.isInBigdata != null) return false;
        if (organizationalLevel != null ? !organizationalLevel.equals(that.organizationalLevel) : that.organizationalLevel != null) return false;
        if (organizationalName != null ? !organizationalName.equals(that.organizationalName) : that.organizationalName != null) return false;
        if (serviceDirection != null ? !serviceDirection.equals(that.serviceDirection) : that.serviceDirection != null) return false;
        if (serviceCompanyType != null ? !serviceCompanyType.equals(that.serviceCompanyType) : that.serviceCompanyType != null) return false;
        if (dataSourse != null ? !dataSourse.equals(that.dataSourse) : that.dataSourse != null) return false;
        if (dataCirculationPlatform != null ? !dataCirculationPlatform.equals(that.dataCirculationPlatform) : that.dataCirculationPlatform != null) return false;
        if (hardware != null ? !hardware.equals(that.hardware) : that.hardware != null) return false;
        if (basicSoftware != null ? !basicSoftware.equals(that.basicSoftware) : that.basicSoftware != null) return false;
        if (applicationSoftware != null ? !applicationSoftware.equals(that.applicationSoftware) : that.applicationSoftware != null) return false;
        if (infrastructure != null ? !infrastructure.equals(that.infrastructure) : that.infrastructure != null) return false;
        if (systemIntegration != null ? !systemIntegration.equals(that.systemIntegration) : that.systemIntegration != null) return false;
        if (enterpriseApplication != null ? !enterpriseApplication.equals(that.enterpriseApplication) : that.enterpriseApplication != null) return false;
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
        result = 31 * result + (isInBigdata != null ? isInBigdata.hashCode() : 0);
        result = 31 * result + (organizationalLevel != null ? organizationalLevel.hashCode() : 0);
        result = 31 * result + (organizationalName != null ? organizationalName.hashCode() : 0);
        result = 31 * result + (serviceDirection != null ? serviceDirection.hashCode() : 0);
        result = 31 * result + (serviceCompanyType != null ? serviceCompanyType.hashCode() : 0);
        result = 31 * result + (dataSourse != null ? dataSourse.hashCode() : 0);
        result = 31 * result + (dataCirculationPlatform != null ? dataCirculationPlatform.hashCode() : 0);
        result = 31 * result + (hardware != null ? hardware.hashCode() : 0);
        result = 31 * result + (basicSoftware != null ? basicSoftware.hashCode() : 0);
        result = 31 * result + (applicationSoftware != null ? applicationSoftware.hashCode() : 0);
        result = 31 * result + (infrastructure != null ? infrastructure.hashCode() : 0);
        result = 31 * result + (systemIntegration != null ? systemIntegration.hashCode() : 0);
        result = 31 * result + (enterpriseApplication != null ? enterpriseApplication.hashCode() : 0);
        result = 31 * result + (enterpriseIntroduction != null ? enterpriseIntroduction.hashCode() : 0);
        result = 31 * result + (excellentCase != null ? excellentCase.hashCode() : 0);
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
