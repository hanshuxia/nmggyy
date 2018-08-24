package com.anchorcms.core.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc
 */
@Entity
@Table(name = "c_role")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsRole implements Serializable {
    private static final long serialVersionUID = 472436618198907875L;
    private int roleId;
    private Integer siteId;
    private String roleName;
    private int priority;
    private Boolean isSuper;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "site_id")
    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @Basic
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "priority")
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "is_super")
    public Boolean getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Boolean isSuper) {
        this.isSuper = isSuper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsRole cmsRole = (CmsRole) o;

        if (roleId != cmsRole.roleId) return false;
        if (priority != cmsRole.priority) return false;
        if (siteId != null ? !siteId.equals(cmsRole.siteId) : cmsRole.siteId != null) return false;
        if (roleName != null ? !roleName.equals(cmsRole.roleName) : cmsRole.roleName != null) return false;
        if (isSuper != null ? !isSuper.equals(cmsRole.isSuper) : cmsRole.isSuper != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + (siteId != null ? siteId.hashCode() : 0);
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + priority;
        result = 31 * result + (isSuper != null ? isSuper.hashCode() : 0);
        return result;
    }

    private Set<String> perms;

    private Set<CmsUser> users;
    @ManyToMany
    @JoinTable(name="c_user_role",
            joinColumns={@JoinColumn(name="role_id")},
            inverseJoinColumns={@JoinColumn(name="user_id")})
    public Set<CmsUser> getUsers() {
        return users;
    }

    public void setUsers(Set<CmsUser> users) {
        this.users = users;
    }

    @ElementCollection
    @CollectionTable(name = "c_role_permission",joinColumns={ @JoinColumn(nullable=false, name="role_id")})
    @Column(name ="uri")
    public Set<String> getPerms() {
        return perms;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }
    public void delFromUsers(CmsUser user) {
        if (user == null) {
            return;
        }
        Set<CmsUser> set = getUsers();
        if (set == null) {
            return;
        }
        set.remove(user);
    }

    public static Integer[] fetchIds(Collection<CmsRole> roles) {
        if (roles == null) {
            return null;
        }
        Integer[] ids = new Integer[roles.size()];
        int i = 0;
        for (CmsRole r : roles) {
            ids[i++] = r.getRoleId();
        }
        return ids;
    }
}
