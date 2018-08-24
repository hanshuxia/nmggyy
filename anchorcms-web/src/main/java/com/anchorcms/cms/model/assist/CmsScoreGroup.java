package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-9
 * @Desc 得分组
 */
@Entity
@Table(name = "c_score_group")
public class CmsScoreGroup implements Serializable{
    private static final long serialVersionUID = -7026722056007911971L;
    private int scoreGroupId;
    private String name;
    private String description;
    private boolean enable;
    private boolean def;

    @Id
    @Column(name = "score_group_id")
    public int getScoreGroupId() {
        return scoreGroupId;
    }

    public void setScoreGroupId(int scoreGroupId) {
        this.scoreGroupId = scoreGroupId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "enable")
    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "def")
    public boolean getDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsScoreGroup that = (CmsScoreGroup) o;

        if (scoreGroupId != that.scoreGroupId) return false;
        if (enable != that.enable) return false;
        if (def != that.def) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = scoreGroupId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + ((enable)?1:0);
        result = 31 * result + ((def)?1:0);
        return result;
    }
    private CmsSite site;

    @ManyToOne
    @JoinColumn(name = "site_id")
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
}
