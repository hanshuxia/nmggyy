package com.anchorcms.icloud.model.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.supplychain.SRepairInquiry;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * @author machao
 * @Date 2017/1/14 17:51
 * @return
 * 惠补政策信息表
 */
@Entity
@Table(name = "s_ample_policy")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SAmplePolicy implements Serializable{
    private static final long serialVersionUID = -210154443888644838L;
    private int amplePolicyId;      //主键
    private String policyName;     //政策名称
    private String releaseLevel;  //发布层面
    private String address;        //地区
    private String tradeType;      //行业类型
    private String flowExplain;    //流程说明
    private Integer createrId;      //创建人
    private Date releaseDt;         //发布日期
    private Date createrDt;         //创建日期
    private Date updateDt;          //更新日期
    private String deFlag;          //
    private String selectedStatus;  //状态 1 未发布  2已发布

    @Id
    @Column(name = "AMPLE_POLICY_ID")
    public int getAmplePolicyId() {
        return amplePolicyId;
    }

    public void setAmplePolicyId(int amplePolicyId) {
        this.amplePolicyId = amplePolicyId;
    }

    // @Basic
    // @Column(name = "CONTENT_ID")
    // public Integer getContentId() {
    //     return contentId;
    // }
    //
    // public void setContentId(Integer contentId) {
    //     this.contentId = contentId;
    // }

    @Basic
    @Column(name = "POLICY_NAME")
    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    @Basic
    @Column(name = "RELEASE_LEVEL")
    public String getReleaseLevel() {
        return releaseLevel;
    }

    public void setReleaseLevel(String releaseLevel) {
        this.releaseLevel = releaseLevel;
    }

    @Basic
    @Column(name = "TRADE_TYPE")
    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    @Basic
    @Column(name = "FLOW_EXPLAIN")
    public String getFlowExplain() {
        return flowExplain;
    }

    public void setFlowExplain(String flowExplain) {
        this.flowExplain = flowExplain;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
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
    @Column(name = "CREATER_DT")
    public Date getCreaterDt() {
        return createrDt;
    }

    public void setCreaterDt(Date createrDt) {
        this.createrDt = createrDt;
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
    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    @Column(name = "SELECTED_STATUS")
    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SAmplePolicy that = (SAmplePolicy) o;

        if (amplePolicyId != that.amplePolicyId) return false;
        // if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;
        if (policyName != null ? !policyName.equals(that.policyName) : that.policyName != null) return false;
        if (releaseLevel != null ? !releaseLevel.equals(that.releaseLevel) : that.releaseLevel != null) return false;
        if (tradeType != null ? !tradeType.equals(that.tradeType) : that.tradeType != null) return false;
        if (flowExplain != null ? !flowExplain.equals(that.flowExplain) : that.flowExplain != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (createrDt != null ? !createrDt.equals(that.createrDt) : that.createrDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = amplePolicyId;
        // result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        result = 31 * result + (policyName != null ? policyName.hashCode() : 0);
        result = 31 * result + (releaseLevel != null ? releaseLevel.hashCode() : 0);
        result = 31 * result + (tradeType != null ? tradeType.hashCode() : 0);
        result = 31 * result + (flowExplain != null ? flowExplain.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (createrDt != null ? createrDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
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


//    private List<SAmplePolicyApply> sAmplePolicyApply;
//    @OneToMany
//    @JoinColumn(name = "AMPLE_POLICY_ID",insertable = false,updatable = false)
//
//    public List<SAmplePolicyApply> getsAmplePolicyApply() {
//        return sAmplePolicyApply;
//    }
//
//    public void setsAmplePolicyApply(List<SAmplePolicyApply> sAmplePolicyApply) {
//        this.sAmplePolicyApply = sAmplePolicyApply;
//    }
}
