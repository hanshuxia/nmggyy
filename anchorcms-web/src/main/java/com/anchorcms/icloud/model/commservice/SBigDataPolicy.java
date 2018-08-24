package com.anchorcms.icloud.model.commservice;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by DELL on 2018/4/26.
 */
@Entity
@Table(name = "s_bigdata_policy_up",catalog = "")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SBigDataPolicy {
    private Integer bigdataId; // 唯一标识
    private String policyName; // 新闻标题
    private Date creatDate; // 填表日期

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BIGDATA_ID")
    public Integer getBigdataId() {
        return bigdataId;
    }

    public void setBigdataId(Integer bigdataId) {
        this.bigdataId = bigdataId;
    }
    @Basic
    @Column(name = "POLICY_NAME")
    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }
    @Basic
    @Column(name = "CREAT_DATA")
    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SBigDataPolicy that = (SBigDataPolicy) o;

        if (bigdataId != that.bigdataId) return false;
        if (policyName != null ? !policyName.equals(that.policyName) : that.policyName != null) return false;
        if (creatDate != null ? !creatDate.equals(that.creatDate) : that.creatDate != null) return false;

        return true;
    }

    public int hashCode() {
        int result = bigdataId;
        result = 31 * result + (policyName != null ? policyName.hashCode() : 0);
        result = 31 * result + (creatDate != null ? creatDate.hashCode() : 0);
        return result;
    }

    private Content content;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
