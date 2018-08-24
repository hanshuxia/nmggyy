package com.anchorcms.icloud.model.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.synergy.SCompany;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * DESCRIPTION:
 * Author: DELL
 * Date:2016/12/23.
 */
@Entity
@Table(name = "s_repair_res")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SRepairRes implements Serializable {
    public static  final String MODEL_PATH = "repairRes";
    private static final long serialVersionUID = -8313138471660785491L;
    private String repairId;// 唯一标识
    private String contentId;
    private String repairName; //维修资源名称
    private String repairPrice;//维修价格
    private String addrProvince;//地址（省）
    private String addrCity;//地址（地级市）
    private String addrCounty;//地址（市、县级）
    private String addrStreet;//街道信息
    private String goodAt;//擅长领域
    private String workYear;//从业年限
    private String mobile;//个人联系方式
    private String description;//补充说明
    private String releaseId;//发布人ID
    private Date releaseDt;//发布日期
    private String repairMachine;//维修机型
    private String createrId;//创建人ID
    private Date createDt;//创建时间
    private Date deadlineDt;//截止时间
    private String status;//状态
    private String deFlag;//逻辑删除
    private String releaseName;//发布人ID
    private String orgName;//所属单位
    private String orgId;//所属单位ID
    private String isRecommend;//是否推荐
    private String contact;//联系人
    private String fax;//传真
    private String email;//邮箱
    private String repairType;//维修分类
    private String telephone;//维修分类
    private String backReason; // 驳回原因

    @Id
    @Column(name = "REPAIR_ID", nullable = false, length = 100)
    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    @Basic
    @Column(name = "REPAIR_PRICE", nullable = true, length = 100)
    public String getRepairPrice() {
        return repairPrice;
    }

    public void setRepairPrice(String repairPrice) {
        this.repairPrice = repairPrice;
    }

    @Basic
    @Column(name = "CONTENT_ID", nullable = false, length = 60)
    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Basic
    @Column(name = "REPAIR_NAME", nullable = true, length = 128)
    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
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
    @Column(name = "GOOD_AT", nullable = true, length = 64)
    public String getGoodAt() {
        return goodAt;
    }

    public void setGoodAt(String goodAt) {
        this.goodAt = goodAt;
    }

    @Basic
    @Column(name = "WORK_YEAR", nullable = true, length = 64)
    public String getWorkYear() {
        return workYear;
    }

    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    @Basic
    @Column(name = "MOBILE", nullable = true, length = 24)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "RELEASE_NAME", nullable = true, length = 100)
    public String getReleaseName() {
        return releaseName;
    }


    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    @Basic
    @Column(name = "ORG_NAME", nullable = true, length = 100)
    public String getOrgName() {return orgName;}

    public void setOrgName(String orgName) {this.orgName = orgName;}

    @Basic
    @Column(name = "ORG_ID", nullable = true, length = 100)
    public String getOrgId() {return orgId;}

    public void setOrgId(String orgId) {this.orgId = orgId;}


    @Basic
    @Column(name = "RELEASE_ID", nullable = true, length = 32)
    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    @Basic
    @Column(name = "RELEASE_DT", nullable = true)
    public Date getReleaseDt() {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
    }

    @Basic
    @Column(name = "REPAIR_MACHINE", nullable = true, length = 32)
    public String getRepairMachine() {
        return repairMachine;
    }

    public void setRepairMachine(String repairMachine) {
        this.repairMachine = repairMachine;
    }

    @Basic
    @Column(name = "CREATER_ID", nullable = true, length = 32)
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    @Basic
    @Column(name = "CREATE_DT", nullable = true)
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    @Basic
    @Column(name = "DEADLINE_DT", nullable = true)
    public Date getDeadlineDt() {
        return deadlineDt;
    }

    public void setDeadlineDt(Date deadlineDt) {
        this.deadlineDt = deadlineDt;
    }

    @Basic
    @Column(name = "STATUS", nullable = true, length = 2)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "DE_FLAG", nullable = true, length = 2)
    public String getDeFlag() {
        return deFlag;
    }

    public void setDeFlag(String deFlag) {
        this.deFlag = deFlag;
    }

    @Basic
    @Column(name = "IS_RECOMMEND", nullable = true, length = 2)
    public String getIsRecommend() {
        return isRecommend;
    }
    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }


    @Basic
    @Column(name = "CONTACT", nullable = true, length = 32)
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
    @Column(name = "REPAIR_TYPE", nullable = true, length = 100)
    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    @Basic
    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

        SRepairRes that = (SRepairRes) o;

        if (repairId != null ? !repairId.equals(that.repairId) : that.repairId != null) return false;
        if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;
        if (repairName != null ? !repairName.equals(that.repairName) : that.repairName != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (goodAt != null ? !goodAt.equals(that.goodAt) : that.goodAt != null) return false;
        if (workYear != null ? !workYear.equals(that.workYear) : that.workYear != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (releaseName != null ? !releaseName.equals(that.releaseName) : that.releaseName != null) return false;
        if (releaseId != null ? !releaseId.equals(that.releaseId) : that.releaseId != null) return false;

        if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) return false;
        if (orgId != null ? !orgId.equals(that.orgId) : that.orgId != null) return false;

        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (repairMachine != null ? !repairMachine.equals(that.repairMachine) : that.repairMachine != null)
            return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;
        if (isRecommend != null ? !isRecommend.equals(that.isRecommend) : that.isRecommend != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (repairType != null ? !repairType.equals(that.repairType) : that.repairType != null) return false;
        if (backReason != null ? !backReason.equals(that.backReason) : that.backReason != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = repairId != null ? repairId.hashCode() : 0;
        result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        result = 31 * result + (repairName != null ? repairName.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (goodAt != null ? goodAt.hashCode() : 0);
        result = 31 * result + (workYear != null ? workYear.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (releaseName != null ? releaseName.hashCode() : 0);
        result = 31 * result + (releaseId != null ? releaseId.hashCode() : 0);
        result = 31 * result + (orgId != null ? orgId.hashCode() : 0);
        result = 31 * result + (orgName != null ? orgName.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (repairMachine != null ? repairMachine.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (isRecommend != null ? isRecommend.hashCode() : 0);

        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (repairType != null ? repairType.hashCode() : 0);
        result = 31 * result + (backReason != null ? backReason.hashCode() : 0);
        return result;
    }
    private Content content;
    private SCompany scompany;

    @OneToOne
    @JoinColumn(name = "content_id",insertable = false,updatable = false)
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }


    private  List<SRepairInquiry> sRepairInquiry;

    @OneToMany(targetEntity = SRepairInquiry.class, cascade = CascadeType.ALL)
    @JoinColumns(value = {@JoinColumn(name = "INQUIRY_INFO_ID", referencedColumnName = "REPAIR_ID")})
    public List<SRepairInquiry> getsRepairInquiry() {
        return sRepairInquiry;
    }
    public void setsRepairInquiry(List<SRepairInquiry> sRepairInquiry) {
        this.sRepairInquiry = sRepairInquiry;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    public SCompany getScompany() {
        return scompany;
    }

    public void setScompany(SCompany scompany) {
        this.scompany = scompany;
    }
    /**
     * @author gengwenlong
     * @Date 2017/2/15 9:46
     * @return
     * 顾客好评率计算方法
     */
    @Transient
    public double getRepairInquiryNum(){
        List <SRepairInquiry> list = getsRepairInquiry();
        int nums = 0;//数据总和
        int num = 0;//差评的总数
        int avg ;//好评的百分数
        if (list != null && list.size()>0) {
            for (int i=0;i<list.size();i++){
                nums++;
                if(list.get(i).getEvaluate()=="3"){
                    num++;
                }
            }
            avg = (1-num/nums)*100;
            return avg;
        }else{
            return 100;
        }
    }
}
