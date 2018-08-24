package com.anchorcms.icloud.model.commservice;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author machao
 * @Date 2017/1/14 17:51
 * @return
 * S_AMPLE_POLICY_APPLY
 */
@Entity
@Table(name = "s_ample_policy_apply")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SAmplePolicyApply implements Serializable {
    private static final long serialVersionUID = -8664992733604353316L;
    private int amplePolicyApplyId;//主键
  //  private int amplePolicyId;
    private String soldNoteId;//销售记录id
    private String companyId;//公司id
    private String companyName;//公司名称
    private String contact;//联系人
    private String mobile;//联系电话
    private String comInfoPath;//企业基本信息附件
    private String applyDatumPaht;//申请材料附件
    private String createrId;//创建人id
    private Date createDt;//创建时间
    private Date updateDt;//更新时间
    private String deFlag;//逻辑判断
    private String status;//状态
    private String fax;//传真
    private String email;//邮箱
    private String telephone;//邮箱
    private String backReason;

    @Id
    @Column(name = "AMPLE_POLICY_APPLY_ID")
    public int getAmplePolicyApplyId() {
        return amplePolicyApplyId;
    }

    public void setAmplePolicyApplyId(int amplePolicyApplyId) {
        this.amplePolicyApplyId = amplePolicyApplyId;
    }

//    @Basic
//    @Column(name = "AMPLE_POLICY_ID")
//    public Integer getAmplePolicyId() {return amplePolicyId; }
//
//    public void setAmplePolicyId(Integer amplePolicyId) {
//        this.amplePolicyId = amplePolicyId;
//    }

    @Basic
    @Column(name = "SOLD_NOTE_ID")
    public String getSoldNoteId() {
        return soldNoteId;
    }

    public void setSoldNoteId(String soldNoteId) {
        this.soldNoteId = soldNoteId;
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
    @Column(name = "COMPANY_NAME")
    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) {this.companyName = companyName;}

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
    @Column(name = "COM_INFO_PATH")
    public String getComInfoPath() {
        return comInfoPath;
    }

    public void setComInfoPath(String comInfoPath) {
        this.comInfoPath = comInfoPath;
    }

    @Basic
    @Column(name = "APPLY_DATUM_PAHT")
    public String getApplyDatumPaht() {
        return applyDatumPaht;
    }

    public void setApplyDatumPaht(String applyDatumPaht) {
        this.applyDatumPaht = applyDatumPaht;
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
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

        SAmplePolicyApply that = (SAmplePolicyApply) o;

        if (amplePolicyApplyId != that.amplePolicyApplyId) return false;

        if (soldNoteId != null ? !soldNoteId.equals(that.soldNoteId) : that.soldNoteId != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (comInfoPath != null ? !comInfoPath.equals(that.comInfoPath) : that.comInfoPath != null) return false;
        if (applyDatumPaht != null ? !applyDatumPaht.equals(that.applyDatumPaht) : that.applyDatumPaht != null)
            return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = amplePolicyApplyId;
        result = 31 * result + (soldNoteId != null ? soldNoteId.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (comInfoPath != null ? comInfoPath.hashCode() : 0);
        result = 31 * result + (applyDatumPaht != null ? applyDatumPaht.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
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


    private SAmplePolicy sAmplePolicy;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AMPLE_POLICY_ID")

    public SAmplePolicy getsAmplePolicy() {return sAmplePolicy; }

    public void setsAmplePolicy(SAmplePolicy sAmplePolicy) {this.sAmplePolicy = sAmplePolicy; }

}
