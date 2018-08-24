package com.anchorcms.icloud.model.synergy;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/26
 * @Desc 企业证书表
 */
@Entity
@Table(name = "s_company_diploma")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SCompanyDiploma implements Serializable{
    private static final long serialVersionUID = -1361583846917277109L;
    private int diplomaId;
    private String companyId;
    private String diplomaType;
    private String issuser;
    private String diplomaPic;
    private Date operationDt;
    private Date deadlineDt;
    private String createId;
    private Date createDt;
    private Date updateDt;
    private Date releaseDt;
    private String deFlag;
    private String diplomaDiscribe;

    @Id
    @Column(name = "DIPLOMA_ID")
    public int getDiplomaId() {
        return diplomaId;
    }

    public void setDiplomaId(int diplomaId) {
        this.diplomaId = diplomaId;
    }

    @Basic
    @Column(name = "DIPLOMA_DISCRIBE")
    public String getDiplomaDiscribe() {
        return diplomaDiscribe;
    }

    public void setDiplomaDiscribe(String diplomaDiscribe) {
        this.diplomaDiscribe = diplomaDiscribe;
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
    @Column(name = "DIPLOMA_TYPE")
    public String getDiplomaType() {
        return diplomaType;
    }

    public void setDiplomaType(String diplomaType) {
        this.diplomaType = diplomaType;
    }

    @Basic
    @Column(name = "ISSUSER")
    public String getIssuser() {
        return issuser;
    }

    public void setIssuser(String issuser) {
        this.issuser = issuser;
    }

    @Basic
    @Column(name = "DIPLOMA_PIC")
    public String getDiplomaPic() {
        return diplomaPic;
    }

    public void setDiplomaPic(String diplomaPic) {
        this.diplomaPic = diplomaPic;
    }

    @Basic
    @Column(name = "OPERATION_DT")
    public Date getOperationDt() {
        return operationDt;
    }

    public void setOperationDt(Date operationDt) {
        this.operationDt = operationDt;
    }

    @Basic
    @Column(name = "DEADLINE_DT")
    public Date getDeadlineDt() {
        return deadlineDt;
    }

    public void setDeadlineDt(Date deadlineDt) {
        this.deadlineDt = deadlineDt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SCompanyDiploma that = (SCompanyDiploma) o;

        if (diplomaId != that.diplomaId) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (diplomaType != null ? !diplomaType.equals(that.diplomaType) : that.diplomaType != null) return false;
        if (issuser != null ? !issuser.equals(that.issuser) : that.issuser != null) return false;
        if (diplomaPic != null ? !diplomaPic.equals(that.diplomaPic) : that.diplomaPic != null) return false;
        if (operationDt != null ? !operationDt.equals(that.operationDt) : that.operationDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (createId != null ? !createId.equals(that.createId) : that.createId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = diplomaId;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (diplomaType != null ? diplomaType.hashCode() : 0);
        result = 31 * result + (issuser != null ? issuser.hashCode() : 0);
        result = 31 * result + (diplomaPic != null ? diplomaPic.hashCode() : 0);
        result = 31 * result + (operationDt != null ? operationDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (createId != null ? createId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
}
