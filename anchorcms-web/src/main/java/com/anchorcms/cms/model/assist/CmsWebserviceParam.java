package com.anchorcms.cms.model.assist;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by smt on 2016/11/17.
 */
@Embeddable
public class CmsWebserviceParam implements Serializable{
    private static final long serialVersionUID = -3876785730367045606L;

    private String paramName;
    private String defaultValue;




    @Basic
    @Column(name = "param_name", nullable = false, length = 100)
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Basic
    @Column(name = "default_value", nullable = true, length = 255)
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }



}
