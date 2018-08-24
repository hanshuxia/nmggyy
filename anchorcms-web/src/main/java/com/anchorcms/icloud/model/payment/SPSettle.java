package com.anchorcms.icloud.model.payment;

import javax.persistence.*;

/**
 * Created by DELL on 2017/5/1.
 */
@Entity
@Table(name = "s_p_settle", catalog = "")
public class SPSettle {
    private String id;          //id
    private String institutionId;   //机构号
    private String serialNumber;      //流水号
    private String orderNo;           //订单号
    private Double amount;           //结算金额
    private Integer accountType;    //账户类型    11个人账户   12企业账户
    private String bankName;       //银行名称
    private String bankId;             //银行编码
    private String acountName;           //账户名称
    private String acountNumber;          //账户号码
    private String branchName;           //分行名称
    private String province;              //分支行省份
    private String city;                  //分支行城市
    private String flag;  //标志位0为退款   1为结算
    private String resultFlag;           //标志位

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "INSTITUTION_ID")
    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    @Basic
    @Column(name = "SERIAL_NUMBER")
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Basic
    @Column(name = "ORDER_NO")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "AMOUNT")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "ACCOUNT_TYPE")
    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    @Basic
    @Column(name = "BANK_NAME")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Basic
    @Column(name = "BANK_ID")
    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    @Basic
    @Column(name = "ACOUNT_NAME")
    public String getAcountName() {
        return acountName;
    }

    public void setAcountName(String acountName) {
        this.acountName = acountName;
    }

    @Basic
    @Column(name = "ACOUNT_NUMBER")
    public String getAcountNumber() {
        return acountNumber;
    }

    public void setAcountNumber(String acountNumber) {
        this.acountNumber = acountNumber;
    }

    @Basic
    @Column(name = "BRANCH_NAME")
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Basic
    @Column(name = "PROVINCE")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "Flag")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Basic
    @Column(name = "RESULT_FLAG")
    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SPSettle spSettle = (SPSettle) o;

        if (id != null ? !id.equals(spSettle.id) : spSettle.id != null) return false;
        if (institutionId != null ? !institutionId.equals(spSettle.institutionId) : spSettle.institutionId != null)
            return false;
        if (serialNumber != null ? !serialNumber.equals(spSettle.serialNumber) : spSettle.serialNumber != null)
            return false;
        if (orderNo != null ? !orderNo.equals(spSettle.orderNo) : spSettle.orderNo != null) return false;
        if (amount != null ? !amount.equals(spSettle.amount) : spSettle.amount != null) return false;
        if (accountType != null ? !accountType.equals(spSettle.accountType) : spSettle.accountType != null)
            return false;
        if (bankName != null ? !bankName.equals(spSettle.bankName) : spSettle.bankName != null) return false;
        if (bankId != null ? !bankId.equals(spSettle.bankId) : spSettle.bankId != null) return false;
        if (acountName != null ? !acountName.equals(spSettle.acountName) : spSettle.acountName != null) return false;
        if (acountNumber != null ? !acountNumber.equals(spSettle.acountNumber) : spSettle.acountNumber != null)
            return false;
        if (branchName != null ? !branchName.equals(spSettle.branchName) : spSettle.branchName != null) return false;
        if (province != null ? !province.equals(spSettle.province) : spSettle.province != null) return false;
        if (city != null ? !city.equals(spSettle.city) : spSettle.city != null) return false;
        if (flag != null ? !flag.equals(spSettle.flag) : spSettle.flag != null) return false;
        if (resultFlag != null ? !resultFlag.equals(spSettle.resultFlag) : spSettle.resultFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (institutionId != null ? institutionId.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + (orderNo != null ? orderNo.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (bankId != null ? bankId.hashCode() : 0);
        result = 31 * result + (acountName != null ? acountName.hashCode() : 0);
        result = 31 * result + (acountNumber != null ? acountNumber.hashCode() : 0);
        result = 31 * result + (branchName != null ? branchName.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (flag != null ? flag.hashCode() : 0);
        result = 31 * result + (resultFlag != null ? resultFlag.hashCode() : 0);
        return result;
    }
}
