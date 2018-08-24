package com.anchorcms.cms.model.assist;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smt on 2016/11/17.
 */
@Entity
@Table(name = "c_webservice")
public class CmsWebservice implements Serializable{
    private static final long serialVersionUID = 141411039455649528L;
    private Integer serviceId;
    private String serviceAddress;
    private String targetNamespace;
    private String successResult;
    private String serviceType;
    private String serviceOperate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "service_address", nullable = false, length = 255)
    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    @Basic
    @Column(name = "target_namespace", nullable = true, length = 255)
    public String getTargetNamespace() {
        return targetNamespace;
    }

    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

    @Basic
    @Column(name = "success_result", nullable = true, length = 255)
    public String getSuccessResult() {
        return successResult;
    }

    public void setSuccessResult(String successResult) {
        this.successResult = successResult;
    }

    @Basic
    @Column(name = "service_type", nullable = true, length = 50)
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Basic
    @Column(name = "service_operate", nullable = true, length = 50)
    public String getServiceOperate() {
        return serviceOperate;
    }

    public void setServiceOperate(String serviceOperate) {
        this.serviceOperate = serviceOperate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsWebservice that = (CmsWebservice) o;

        if (serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null) return false;
        if (serviceAddress != null ? !serviceAddress.equals(that.serviceAddress) : that.serviceAddress != null)
            return false;
        if (targetNamespace != null ? !targetNamespace.equals(that.targetNamespace) : that.targetNamespace != null)
            return false;
        if (successResult != null ? !successResult.equals(that.successResult) : that.successResult != null)
            return false;
        if (serviceType != null ? !serviceType.equals(that.serviceType) : that.serviceType != null) return false;
        if (serviceOperate != null ? !serviceOperate.equals(that.serviceOperate) : that.serviceOperate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceId != null ? serviceId.hashCode() : 0;
        result = 31 * result + (serviceAddress != null ? serviceAddress.hashCode() : 0);
        result = 31 * result + (targetNamespace != null ? targetNamespace.hashCode() : 0);
        result = 31 * result + (successResult != null ? successResult.hashCode() : 0);
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        result = 31 * result + (serviceOperate != null ? serviceOperate.hashCode() : 0);
        return result;
    }

    public static final String SERVICE_TYPE_ADD_USER = "addUser";
    public static final String SERVICE_TYPE_UPDATE_USER = "updateUser";
    public static final String SERVICE_TYPE_DELETE_USER = "deleteUser";

    private List<CmsWebserviceParam> params;
    @ElementCollection
    @CollectionTable(name="c_webservice_param", joinColumns=@JoinColumn(name="service_id"))
    @OrderColumn(name = "priority")

    public List<CmsWebserviceParam> getParams() {
        return params;
    }

    public void setParams(List<CmsWebserviceParam> params) {
        this.params = params;
    }

    public void addToParams(String name, String defaultValue) {
        List<CmsWebserviceParam> list = getParams();
        if (list == null) {
            list = new ArrayList<CmsWebserviceParam>();
            setParams(list);
        }
        CmsWebserviceParam param = new CmsWebserviceParam();
        param.setParamName(name);
        param.setDefaultValue(defaultValue);
        list.add(param);
    }
}
