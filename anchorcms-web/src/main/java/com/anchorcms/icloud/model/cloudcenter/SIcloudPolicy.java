package com.anchorcms.icloud.model.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.core.model.CmsUser;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3
 * @Desc 云计算政策表
 */
@Entity
@Table(name = "s_icloud_policy")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SIcloudPolicy implements Serializable{
    private Integer policyId;
    private String policyNumber;
    private String policyName;
    private String postCode;
    private Date endApplyDt;
    private String policyLevel;
    private String contact;
    private String isSupport;
    private String createrId;
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private String phone;
    private String mobile;
    private Date createDt;
    private Date releaseDt;
    private String state;
    private String deFlag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POLICY_ID")
    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
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
    @Column(name = "POLICY_NUMBER")
    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
    @Basic
    @Column(name = "MOBILE")
    public String getMobile() {
        return mobile;
    }
    @Basic
    @Column(name = "ENDAPPLY_DT")
    public Date getEndApplyDt() {
        return endApplyDt;
    }

    public void setEndApplyDt(Date endApplyDt) {
        this.endApplyDt = endApplyDt;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "POST_CODE")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
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
    @Column(name = "IS_SUPPORT")
    public String getIsSupport() {
        return isSupport;
    }

    public void setIsSupport(String isSupport) {
        this.isSupport = isSupport;
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
    @Column(name = "CREATE_DT")
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SIcloudPolicy that = (SIcloudPolicy) o;

        if (policyId != null ? !policyId.equals(that.policyId) : that.policyId != null) return false;
        if (policyNumber != null ? !policyNumber.equals(that.policyNumber) : that.policyNumber != null) return false;
        if (policyName != null ? !policyName.equals(that.policyName) : that.policyName != null) return false;
        if (postCode != null ? !postCode.equals(that.postCode) : that.postCode != null) return false;
        if (policyLevel != null ? !policyLevel.equals(that.policyLevel) : that.policyLevel != null) return false;
        if (isSupport != null ? !isSupport.equals(that.isSupport) : that.isSupport != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = policyId != null ? policyId.hashCode() : 0;
        result = 31 * result + (policyNumber != null ? policyNumber.hashCode() : 0);
        result = 31 * result + (policyName != null ? policyName.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (policyLevel != null ? policyLevel.hashCode() : 0);
        result = 31 * result + (isSupport != null ? isSupport.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }

    private CmsUser user;
    @ManyToOne
    @JoinColumn(name = "USER_ID")

    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
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
    //查询剩余天数
    @Transient
    public Integer getBetween() throws ParseException {
        java.util.Date date = getEndApplyDt();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long time1 = cal.getTimeInMillis();
        cal.setTime(new java.sql.Date(System.currentTimeMillis()));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time2 = cal.getTimeInMillis();
        long between_days=(time1-time2)/(1000*3600);
        if(between_days>=24){
            between_days=(time1-time2)/(1000*3600*24);
        }else if(between_days<0){
            between_days=-1;
        }else{
            between_days=0;
        }
        return Integer.parseInt(String.valueOf(between_days));
    }
}
