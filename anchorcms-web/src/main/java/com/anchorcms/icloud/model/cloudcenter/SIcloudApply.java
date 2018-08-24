package com.anchorcms.icloud.model.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.core.model.CmsUser;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3
 * @Desc 云计算政策申请表
 */
@Entity
@Table(name = "s_icloud_apply")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SIcloudApply implements Serializable{
    private static final long serialVersionUID = -7653209601023838761L;
    private Integer applyId;
    private String policyName;
    private String policyLevel;
    private String applyReason;
    private String applyPeriod;
    private String policyType;
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private String contact;
    private String mobile;
    private String policyCode;
    private String classifyCode;
    private String releaseId;
    private String companyId;
    private String operatorId;
    private Date createDt;
    private Date applyDt;
    private String state;
    private String deFlag;
    private String backReason;

    @Id
    @Column(name = "APPLY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }
    @Basic
    @Column(name = "POLICY_NAME")
    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    @Basic
    @Column(name = "POLICY_LEVEL")
    public String getPolicyLevel() {
        return policyLevel;
    }

    public void setPolicyLevel(String policyLevel) {
        this.policyLevel = policyLevel;
    }

    @Basic
    @Column(name = "APPLY_REASON")
    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    @Basic
    @Column(name = "APPLY_PERIOD")
    public String getApplyPeriod() {
        return applyPeriod;
    }

    public void setApplyPeriod(String applyPeriod) {
        this.applyPeriod = applyPeriod;
    }

    @Basic
    @Column(name = "POLICY_TYPE")
    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
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
    @Column(name = "POLICY_CODE")
    public String getPolicyCode() {
        return policyCode;
    }

    public void setPolicyCode(String policyCode) {
        this.policyCode = policyCode;
    }

    @Basic
    @Column(name = "CLASSIFY_CODE")
    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
    }

    @Basic
    @Column(name = "RELEASE_ID")
    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    @Basic
    @Column(name = "COMPANY_ID")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    @Basic
    @Column(name = "OPERATOR_ID")
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
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
    @Column(name = "APPLY_DT")
    public Date getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(Date applyDt) {
        this.applyDt = applyDt;
    }

    @Basic
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

        SIcloudApply that = (SIcloudApply) o;

        if (applyId != null ? !applyId.equals(that.applyId) : that.applyId != null) return false;
        if (policyName != null ? !policyName.equals(that.policyName) : that.policyName != null) return false;
        if (policyLevel != null ? !policyLevel.equals(that.policyLevel) : that.policyLevel != null) return false;
        if (applyReason != null ? !applyReason.equals(that.applyReason) : that.applyReason != null) return false;
        if (applyPeriod != null ? !applyPeriod.equals(that.applyPeriod) : that.applyPeriod != null) return false;
        if (policyType != null ? !policyType.equals(that.policyType) : that.policyType != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (policyCode != null ? !policyCode.equals(that.policyCode) : that.policyCode != null) return false;
        if (classifyCode != null ? !classifyCode.equals(that.classifyCode) : that.classifyCode != null) return false;
        if (releaseId != null ? !releaseId.equals(that.releaseId) : that.releaseId != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (applyDt != null ? !applyDt.equals(that.applyDt) : that.applyDt != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = applyId != null ? applyId.hashCode() : 0;
        result = 31 * result + (policyName != null ? policyName.hashCode() : 0);
        result = 31 * result + (policyLevel != null ? policyLevel.hashCode() : 0);
        result = 31 * result + (applyReason != null ? applyReason.hashCode() : 0);
        result = 31 * result + (applyPeriod != null ? applyPeriod.hashCode() : 0);
        result = 31 * result + (policyType != null ? policyType.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (policyCode != null ? policyCode.hashCode() : 0);
        result = 31 * result + (classifyCode != null ? classifyCode.hashCode() : 0);
        result = 31 * result + (releaseId != null ? releaseId.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (applyDt != null ? applyDt.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
    @Transient
    public String getCompany() {
        return user.getCompany().getCompanyName();
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

    private SIcloudPolicy policy;
    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "PAARENTAPPLY_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    public SIcloudPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(SIcloudPolicy policy) {
        this.policy = policy;
    }

    private CmsUser user;
    @OneToOne
    @JoinColumn(name = "CREATER_ID")

    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
}
