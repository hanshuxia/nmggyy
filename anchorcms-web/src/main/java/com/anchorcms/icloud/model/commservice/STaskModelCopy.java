package com.anchorcms.icloud.model.commservice;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/12
 * @Desc 任务模块信息表
 */
public class STaskModelCopy implements Serializable{
    private int taskModelId;
    private Integer taskId;
    private String modelName;
    private String modelType;
    private String modelLength;
    private Integer sortNum;
    private String remark;


    public int getTaskModelId() {
        return taskModelId;
    }

    public void setTaskModelId(int taskModelId) {
        this.taskModelId = taskModelId;
    }


    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }


    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getModelLength() {
        return modelLength;
    }

    public void setModelLength(String modelLength) {
        this.modelLength = modelLength;
    }


    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        STaskModelCopy that = (STaskModelCopy) o;

        if (taskModelId != that.taskModelId) return false;
        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) return false;
        if (modelName != null ? !modelName.equals(that.modelName) : that.modelName != null) return false;
        if (modelType != null ? !modelType.equals(that.modelType) : that.modelType != null) return false;
        if (modelLength != null ? !modelLength.equals(that.modelLength) : that.modelLength != null) return false;
        if (sortNum != null ? !sortNum.equals(that.sortNum) : that.sortNum != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskModelId;
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + (modelType != null ? modelType.hashCode() : 0);
        result = 31 * result + (modelLength != null ? modelLength.hashCode() : 0);
        result = 31 * result + (sortNum != null ? sortNum.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

}
