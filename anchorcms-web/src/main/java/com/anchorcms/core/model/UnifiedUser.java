package com.anchorcms.core.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-2
 * @Desc
 */
@Entity
@Table(name = "o_user")
public class UnifiedUser implements Serializable {
    private static final long serialVersionUID = 7848447426568871408L;
    private int userId;
    private String username;
    private String email;
    private String password;
    private Date registerTime;
    private String registerIp;
    private Date lastLoginTime;
    private String lastLoginIp;
    private Integer loginCount;
    private String resetKey;
    private String resetPwd;
    private Date errorTime;
    private Integer errorCount;
    private String errorIp;
    private boolean activation;
    private String activationCode;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "register_time")
    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Basic
    @Column(name = "register_ip")
    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    @Basic
    @Column(name = "last_login_time")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "last_login_ip")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Basic
    @Column(name = "login_count")
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Basic
    @Column(name = "reset_key")
    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    @Basic
    @Column(name = "reset_pwd")
    public String getResetPwd() {
        return resetPwd;
    }

    public void setResetPwd(String resetPwd) {
        this.resetPwd = resetPwd;
    }

    @Basic
    @Column(name = "error_time")
    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    @Basic
    @Column(name = "error_count")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Basic
    @Column(name = "error_ip")
    public String getErrorIp() {
        return errorIp;
    }

    public void setErrorIp(String errorIp) {
        this.errorIp = errorIp;
    }

    @Basic
    @Column(name = "activation")
    public boolean getActivation() {
        return activation;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    @Basic
    @Column(name = "activation_code")
    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnifiedUser unifiedUser = (UnifiedUser) o;

        if (userId != unifiedUser.userId) return false;
        if (loginCount != unifiedUser.loginCount) return false;
        if (errorCount != unifiedUser.errorCount) return false;
        if (activation != unifiedUser.activation) return false;
        if (username != null ? !username.equals(unifiedUser.username) : unifiedUser.username != null) return false;
        if (email != null ? !email.equals(unifiedUser.email) : unifiedUser.email != null) return false;
        if (password != null ? !password.equals(unifiedUser.password) : unifiedUser.password != null) return false;
        if (registerTime != null ? !registerTime.equals(unifiedUser.registerTime) : unifiedUser.registerTime != null) return false;
        if (registerIp != null ? !registerIp.equals(unifiedUser.registerIp) : unifiedUser.registerIp != null) return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(unifiedUser.lastLoginTime) : unifiedUser.lastLoginTime != null)
            return false;
        if (lastLoginIp != null ? !lastLoginIp.equals(unifiedUser.lastLoginIp) : unifiedUser.lastLoginIp != null) return false;
        if (resetKey != null ? !resetKey.equals(unifiedUser.resetKey) : unifiedUser.resetKey != null) return false;
        if (resetPwd != null ? !resetPwd.equals(unifiedUser.resetPwd) : unifiedUser.resetPwd != null) return false;
        if (errorTime != null ? !errorTime.equals(unifiedUser.errorTime) : unifiedUser.errorTime != null) return false;
        if (errorIp != null ? !errorIp.equals(unifiedUser.errorIp) : unifiedUser.errorIp != null) return false;
        if (activationCode != null ? !activationCode.equals(unifiedUser.activationCode) : unifiedUser.activationCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (registerTime != null ? registerTime.hashCode() : 0);
        result = 31 * result + (registerIp != null ? registerIp.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (lastLoginIp != null ? lastLoginIp.hashCode() : 0);
        result = 31 * result + loginCount;
        result = 31 * result + (resetKey != null ? resetKey.hashCode() : 0);
        result = 31 * result + (resetPwd != null ? resetPwd.hashCode() : 0);
        result = 31 * result + (errorTime != null ? errorTime.hashCode() : 0);
        result = 31 * result + errorCount;
        result = 31 * result + (errorIp != null ? errorIp.hashCode() : 0);
        result = 31 * result + (activation !=true ? 0 : 1);
        result = 31 * result + (activationCode != null ? activationCode.hashCode() : 0);
        return result;
    }
    public void init(){
        if(getLoginCount()==null){
            setLoginCount(0);
        }
        if(getErrorCount()==null){
            setErrorCount(0);
        }
    }
}
