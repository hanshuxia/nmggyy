package com.anchorcms.cms.model.assist;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by smt on 2016/12/12.
 */
@Entity
@Table(name = "c_job_apply")
public class CmsJobApply implements Serializable{
    private static final long serialVersionUID = -528605537475606087L;
    private Integer jobApplyId;
    private Date applyTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_apply_id", nullable = false)
    public Integer getJobApplyId() {
        return jobApplyId;
    }

    public void setJobApplyId(Integer jobApplyId) {
        this.jobApplyId = jobApplyId;
    }

    @Basic
    @Column(name = "apply_time", nullable = false)
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsJobApply that = (CmsJobApply) o;

        if (jobApplyId != null ? !jobApplyId.equals(that.jobApplyId) : that.jobApplyId != null) return false;
        if (applyTime != null ? !applyTime.equals(that.applyTime) : that.applyTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jobApplyId != null ? jobApplyId.hashCode() : 0;
        result = 31 * result + (applyTime != null ? applyTime.hashCode() : 0);
        return result;
    }
}
