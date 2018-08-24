package com.anchorcms.icloud.model.common;

import com.anchorcms.cms.model.main.Content;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author machao
 * @Date 2017/3/30 13:49
 * @return
 * 电子签章申请支付
 */
@Entity
@Table(name = "s_cfca_pay")
public class SCfcaPay implements Serializable{
    private static final long serialVersionUID = -6205629590371358366L;
    private Integer policyId;
    private String policyNumber;  //付款单号
    private String policyName;     //付款账号
    private String postCode;
    private String policyLevel;    //备注


    @Id
    @Column(name = "POLICY_ID")
    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
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
    @Column(name = "POLICY_NAME")
    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SCfcaPay sCfcaPay = (SCfcaPay) o;

        if (policyId != null ? !policyId.equals(sCfcaPay.policyId) : sCfcaPay.policyId != null) return false;
        if (policyNumber != null ? !policyNumber.equals(sCfcaPay.policyNumber) : sCfcaPay.policyNumber != null)
            return false;
        if (policyName != null ? !policyName.equals(sCfcaPay.policyName) : sCfcaPay.policyName != null) return false;
        if (postCode != null ? !postCode.equals(sCfcaPay.postCode) : sCfcaPay.postCode != null) return false;
        if (policyLevel != null ? !policyLevel.equals(sCfcaPay.policyLevel) : sCfcaPay.policyLevel != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = policyId != null ? policyId.hashCode() : 0;
        result = 31 * result + (policyNumber != null ? policyNumber.hashCode() : 0);
        result = 31 * result + (policyName != null ? policyName.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (policyLevel != null ? policyLevel.hashCode() : 0);
        return result;
    }
    private Content content;
    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
