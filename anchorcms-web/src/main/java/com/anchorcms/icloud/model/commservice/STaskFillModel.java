package com.anchorcms.icloud.model.commservice;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/12
 * @Desc 任务上报详细信息表
 */
@Entity
@Table(name = "s_task_fill_model")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class STaskFillModel implements Serializable{
    private static final long serialVersionUID = 5469894998181769606L;
    private int taskFillModelId;
    private Integer taskFillId;
    private Integer taskModelId;
    private Integer taskId;
    private String groupId;
    private String propertyName;
    private String propertyValue;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_FILL_MODEL_ID")
    public int getTaskFillModelId() {
        return taskFillModelId;
    }

    public void setTaskFillModelId(int taskFillModelId) {
        this.taskFillModelId = taskFillModelId;
    }

    @Basic
    @Column(name = "TASK_FILL_ID")
    public Integer getTaskFillId() {
        return taskFillId;
    }

    public void setTaskFillId(Integer taskFillId) {
        this.taskFillId = taskFillId;
    }

    @Basic
    @Column(name ="TASK_ID")
    public Integer getTaskId(){
        return taskId;
    }
    public void setTaskId(Integer taskId){
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "TASK_MODEL_ID")
    public Integer getTaskModelId() {
        return taskModelId;
    }

    public void setTaskModelId(Integer taskModelId) {
        this.taskModelId = taskModelId;
    }

    @Basic
    @Column(name = "GROUP_ID")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "PROPERTY_NAME")
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Basic
    @Column(name = "PROPERTY_VALUE")
    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        STaskFillModel that = (STaskFillModel) o;

        if (taskFillModelId != that.taskFillModelId) return false;
        if (taskFillId != null ? !taskFillId.equals(that.taskFillId) : that.taskFillId != null) return false;
        if (taskModelId != null ? !taskModelId.equals(that.taskModelId) : that.taskModelId != null) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (propertyName != null ? !propertyName.equals(that.propertyName) : that.propertyName != null) return false;
        if (propertyValue != null ? !propertyValue.equals(that.propertyValue) : that.propertyValue != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskFillModelId;
        result = 31 * result + (taskFillId != null ? taskFillId.hashCode() : 0);
        result = 31 * result + (taskModelId != null ? taskModelId.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (propertyName != null ? propertyName.hashCode() : 0);
        result = 31 * result + (propertyValue != null ? propertyValue.hashCode() : 0);
        return result;
    }
}
