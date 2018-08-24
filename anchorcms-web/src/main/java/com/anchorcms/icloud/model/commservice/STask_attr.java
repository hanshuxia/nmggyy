package com.anchorcms.icloud.model.commservice;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/13.
 */
@Entity
@Table(name = "s_task_attr")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class STask_attr implements Serializable {
    private Integer task_attr_id;
    private Integer task_model_id;
    private Integer task_file_id;
    private String task_name;
    private String task_value;
    private String group_id;
    private Integer user_id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ATTR_ID")
    public Integer getTask_attr_id() {
        return task_attr_id;
    }

    public void setTask_attr_id(Integer task_attr_id) {
        this.task_attr_id = task_attr_id;
    }
   @Basic
   @Column(name = "TASK_MODEL_ID")
    public void setTask_model_id(Integer task_model_id) {
        this.task_model_id = task_model_id;
    }


    public Integer getTask_model_id() {
        return task_model_id;
    }
    @Basic
    @Column(name = "TASK_FILE_ID")
    public void setTask_file_id(Integer task_file_id) {
        this.task_file_id = task_file_id;
    }

    public Integer getTask_file_id() {
        return task_file_id;
    }


    @Basic
    @Column(name = "TASK_NAME")
    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_name() {
        return task_name;
    }
    @Basic
    @Column(name = "TASK_VALUE")
    public void setTask_value(String task_value) {
        this.task_value = task_value;
    }
    public String getTask_value() {
        return task_value;
    }

    @Basic
    @Column(name = "GROUP_ID")
    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    @Basic
    @Column(name = "USER_ID")
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        STask_attr that = (STask_attr) o;

        if (task_model_id != null ? !task_model_id.equals(that.task_model_id) : that.task_model_id != null)
            return false;
        if (task_file_id != null ? !task_file_id.equals(that.task_file_id) : that.task_file_id != null) return false;
        if (task_name != null ? !task_name.equals(that.task_name) : that.task_name != null) return false;
        if (task_value != null ? !task_value.equals(that.task_value) : that.task_value != null) return false;
        return group_id != null ? group_id.equals(that.group_id) : that.group_id == null;

    }

    @Override
    public int hashCode() {
        int result = task_model_id != null ? task_model_id.hashCode() : 0;
        result = 31 * result + (task_file_id != null ? task_file_id.hashCode() : 0);
        result = 31 * result + (task_name != null ? task_name.hashCode() : 0);
        result = 31 * result + (task_value != null ? task_value.hashCode() : 0);
        result = 31 * result + (group_id != null ? group_id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "STask_attr{" +
                "task_model_id=" + task_model_id +
                ", TASK_FILE_ID=" + task_file_id +
                ", task_name='" + task_name + '\'' +
                ", task_value='" + task_value + '\'' +
                ", group='" + group_id + '\'' +
                '}';
    }
}
