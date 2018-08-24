package com.anchorcms.icloud.model.commservice;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 刘洋 on 2018/4/16.
 */
@Entity
@Table(name = "s_bigdata_news_up",catalog = "")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SBigDataNews {
    private Integer bigdataId; // 唯一标识
    private String newsName; // 新闻标题
    private String newsDesc; //新闻内容
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
    @Column(name = "NEWS_NAME")
    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }
    @Basic
    @Column(name = "NEWS_DESC")
    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
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

        SBigDataNews that = (SBigDataNews) o;

        if (bigdataId != that.bigdataId) return false;
        if (newsName != null ? !newsName.equals(that.newsName) : that.newsName != null) return false;
        if (newsDesc != null ? !newsDesc.equals(that.newsDesc) : that.newsDesc != null) return false;
        if (creatDate != null ? !creatDate.equals(that.creatDate) : that.creatDate != null) return false;

        return true;
    }

    public int hashCode() {
        int result = bigdataId;
        result = 31 * result + (newsName != null ? newsName.hashCode() : 0);
        result = 31 * result + (newsDesc != null ? newsDesc.hashCode() : 0);
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
