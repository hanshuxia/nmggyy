package com.anchorcms.core.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-14
 * @Desc 用户角色关联表
 */
@Entity
@Table(name = "c_user_role")
@IdClass(UserRolePK.class)
public class UserRole implements Serializable {
    private static final long serialVersionUID = 2208705031142214393L;
    private int roleId;
    private int userId;

    @Id
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (roleId != userRole.roleId) return false;
        if (userId != userRole.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + userId;
        return result;
    }
}
