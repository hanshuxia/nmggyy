package com.anchorcms.icloud.model.commservice;

import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SCompany;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/12
 * @Desc 任务主表
 */
@Entity
@Table(name = "s_task")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class STask implements Serializable{
    private static final long serialVersionUID = 4792596376827031812L;
    private int taskId;
    private String taskName;
    private String taskExplain;
    private String status;
    private Date startDt;
    private Date deadlineDt;
    private Date releaseDt;
    private Date createrDt;
    private Date updateDt;
    private String deFlag;
    private String fileName;
    private String filePath;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "TASK_NAME")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Basic
    @Column(name = "TASK_EXPLAIN")
    public String getTaskExplain() {
        return taskExplain;
    }

    public void setTaskExplain(String taskExplain) {
        this.taskExplain = taskExplain;
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
    @Column(name = "START_DT")
    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
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
    @Column(name = "DE_FLAG")
    public String getDeFlag() {
        return deFlag;
    }

    public void setDeFlag(String deFlag) {
        this.deFlag = deFlag;
    }

    @Basic
    @Column(name = "FILE_NAME")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "FILE_PATH")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        STask sTask = (STask) o;

        if (taskId != sTask.taskId) return false;
        if (taskName != null ? !taskName.equals(sTask.taskName) : sTask.taskName != null) return false;
        if (taskExplain != null ? !taskExplain.equals(sTask.taskExplain) : sTask.taskExplain != null) return false;
        if (status != null ? !status.equals(sTask.status) : sTask.status != null) return false;
        if (startDt != null ? !startDt.equals(sTask.startDt) : sTask.startDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(sTask.deadlineDt) : sTask.deadlineDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(sTask.releaseDt) : sTask.releaseDt != null) return false;
        if (createrDt != null ? !createrDt.equals(sTask.createrDt) : sTask.createrDt != null) return false;
        if (updateDt != null ? !updateDt.equals(sTask.updateDt) : sTask.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(sTask.deFlag) : sTask.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskId;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (taskExplain != null ? taskExplain.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (startDt != null ? startDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (createrDt != null ? createrDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }

    private SCompany company;
    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() { return company; }
    public void setCompany(SCompany company) { this.company = company; }

    private CmsUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATER_ID")
    public CmsUser getUser() {
        return user;
    }
    public void setUser(CmsUser user) {
        this.user = user;
    }

    private List<STaskModel> modelList;

    @OneToMany
    @JoinColumn(name = "TASK_ID")
    public List<STaskModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<STaskModel> modelList) {
        this.modelList = modelList;
    }



}
