package com.anchorcms.icloud.model.supplychain;

import java.io.Serializable;

/**
 * Created by
 * 维修需求对象信息表页面用
 */

public class SRepairDemandObjPageBean implements Serializable {

    private static final long serialVersionUID = 8418744569371480273L;
    private String repairNameStr;
    private String machineTypeStr;
    private String repairNumStr;
    private String repairRequestStr;
    private String expectPriceStr;

    public String getRepairNameStr() {
        return repairNameStr;
    }

    public void setRepairNameStr(String repairNameStr) {
        this.repairNameStr = repairNameStr;
    }

    public String getMachineTypeStr() {
        return machineTypeStr;
    }

    public void setMachineTypeStr(String machineTypeStr) {
        this.machineTypeStr = machineTypeStr;
    }

    public String getRepairNumStr() {
        return repairNumStr;
    }

    public void setRepairNumStr(String repairNumStr) {
        this.repairNumStr = repairNumStr;
    }

    public String getExpectPriceStr() {
        return expectPriceStr;
    }

    public void setExpectPriceStr(String expectPriceStr) {
        this.expectPriceStr = expectPriceStr;
    }

    public String getRepairRequestStr() {
        return repairRequestStr;
    }

    public void setRepairRequestStr(String repairRequestStr) {
        this.repairRequestStr = repairRequestStr;
    }
}
