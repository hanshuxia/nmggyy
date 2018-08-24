package com.anchorcms.cms.model.assist;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by smt on 2016/11/17.
 */
@Entity
@Table(name = "c_webservice_auth")
public class CmsWebserviceAuth implements Serializable{
    private static final long serialVersionUID = -4927535325087711821L;
    private Integer authId;
    private String username;
    private String password;
    private String system;
    private Boolean isEnable;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id", nullable = false)
    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "system", nullable = false, length = 100)
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Basic
    @Column(name = "is_enable", nullable = false)
    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsWebserviceAuth that = (CmsWebserviceAuth) o;

        if (authId != null ? !authId.equals(that.authId) : that.authId != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (system != null ? !system.equals(that.system) : that.system != null) return false;
        if (isEnable != null ? !isEnable.equals(that.isEnable) : that.isEnable != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = authId != null ? authId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        result = 31 * result + (isEnable != null ? isEnable.hashCode() : 0);
        return result;
    }
}
