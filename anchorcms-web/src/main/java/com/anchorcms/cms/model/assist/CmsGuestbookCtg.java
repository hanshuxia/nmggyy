package com.anchorcms.cms.model.assist;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import com.anchorcms.core.model.CmsSite;
import org.hibernate.annotations.*;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-7
 * @Desc CMS留言类别
 */
@Entity
@Table(name = "c_guestbook_ctg")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsGuestbookCtg implements Serializable{
    private static final long serialVersionUID = 6861658165437643760L;
    private int guestbookctgId;
    private String ctgName;
    private int priority;
    private String description;
    private CmsSite site;

    /**
     * Return the value associated with the column: site_id
     */
    @ManyToOne
    @JoinColumn(name = "site_id",insertable = true,updatable = true)
    public CmsSite getSite () {
        return site;
    }
    public void setSite (CmsSite site) {
        this.site = site;
    }
    @Id
    @Column(name = "guestbookctg_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getGuestbookctgId() {
        return guestbookctgId;
    }

    public void setGuestbookctgId(int guestbookctgId) {
        this.guestbookctgId = guestbookctgId;
    }
    @Basic
    @Column(name = "ctg_name")
    public String getCtgName() {
        return ctgName;
    }

    public void setCtgName(String ctgName) {
        this.ctgName = ctgName;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsGuestbookCtg that = (CmsGuestbookCtg) o;

        if (guestbookctgId != that.guestbookctgId) return false;
        if (priority != that.priority) return false;
        if (ctgName != null ? !ctgName.equals(that.ctgName) : that.ctgName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = guestbookctgId;
        result = 31 * result + (ctgName != null ? ctgName.hashCode() : 0);
        result = 31 * result + priority;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
    public static Integer[] fetchIds(Collection<CmsGuestbookCtg> guestBookCtgs) {
        if (guestBookCtgs == null) {
            return null;
        }
        Integer[] ids = new Integer[guestBookCtgs.size()];
        int i = 0;
        for (CmsGuestbookCtg c : guestBookCtgs) {
            ids[i++] = c.getGuestbookctgId();
        }
        return ids;
    }


   /* private CmsGuestbook guestbook;
    @ManyToOne
    @JoinColumn(name = "guestbookctg_id",insertable = false,updatable = false)
    public CmsGuestbook getCmsGuestbook () {
        return guestbook;
    }
    public void setgetCmsGuestbook (CmsSite site) {
        this.guestbook = guestbook;
    }*/

}
