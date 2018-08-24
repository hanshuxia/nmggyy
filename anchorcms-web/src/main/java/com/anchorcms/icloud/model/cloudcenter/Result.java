package com.anchorcms.icloud.model.cloudcenter;


import java.io.Serializable;
import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1014:08
 */
public class Result implements Serializable {
    private int status;//状态,0正常
    private String msg;//消息
    private Object data;//数据
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}
