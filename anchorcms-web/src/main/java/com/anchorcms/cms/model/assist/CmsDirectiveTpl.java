package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsUser;

import javax.persistence.*;

/**
 * Created by smt on 2016/11/18.
 */
@Entity
@Table(name = "c_directive_tpl", schema = "anchorcms", catalog = "")
public class CmsDirectiveTpl {
    private Integer tplId;
    private String name;
    private String description;
    private String code;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tpl_id", nullable = false)
    public Integer getTplId() {
        return tplId;
    }

    public void setTplId(Integer tplId) {
        this.tplId = tplId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "code", nullable = true, length = -1)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsDirectiveTpl that = (CmsDirectiveTpl) o;

        if (tplId != null ? !tplId.equals(that.tplId) : that.tplId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tplId != null ? tplId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    private CmsUser user;

    @ManyToOne
    @JoinColumn(name="user_id")
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
}
