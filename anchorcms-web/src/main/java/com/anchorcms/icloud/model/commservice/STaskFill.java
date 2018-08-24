package com.anchorcms.icloud.model.commservice;

import com.anchorcms.core.model.CmsUser;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/12
 * @Desc 任务上报主表
 */
@Entity
@Table(name = "s_task_fill")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class STaskFill implements Serializable{
    private static final long serialVersionUID = 2082767671366647805L;
    private int taskFillId;
    //private Integer taskId;
    //private Integer createrId;
    private String status;
    private Date createrDt;
    private Date updateDt;
    private String deFlag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_FILL_ID")
    public int getTaskFillId() {
        return taskFillId;
    }

    public void setTaskFillId(int taskFillId) {
        this.taskFillId = taskFillId;
    }

   /* @Basic
    @Column(name = "TASK_ID")
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "CREATER_ID")
    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }*/

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        STaskFill sTaskFill = (STaskFill) o;

        if (taskFillId != sTaskFill.taskFillId) return false;
       // if (taskId != null ? !taskId.equals(sTaskFill.taskId) : sTaskFill.taskId != null) return false;
        //if (createrId != null ? !createrId.equals(sTaskFill.createrId) : sTaskFill.createrId != null) return false;
        if (status != null ? !status.equals(sTaskFill.status) : sTaskFill.status != null) return false;
        if (createrDt != null ? !createrDt.equals(sTaskFill.createrDt) : sTaskFill.createrDt != null) return false;
        if (updateDt != null ? !updateDt.equals(sTaskFill.updateDt) : sTaskFill.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(sTaskFill.deFlag) : sTaskFill.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskFillId;
        /*result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);*/
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createrDt != null ? createrDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }

    private STask sTaskList;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TASK_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    public STask getsTaskList() {
        return sTaskList;
    }

    public void setsTaskList(STask sTaskList) {
        this.sTaskList = sTaskList;
    }

    private CmsUser user;
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name="CREATER_ID")

    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
}
