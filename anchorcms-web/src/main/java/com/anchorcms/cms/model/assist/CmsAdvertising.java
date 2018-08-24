package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Map;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-9
 * @Desc CMS广告表
 */
@Entity
@Table(name = "c_advertising")
public class CmsAdvertising implements Serializable{
    private static final long serialVersionUID = 2634669966072994885L;
    private int advertisingId;
    private String adName;
    private String category;
    private String adCode;
    private Integer adWeight;
    private Long displayCount;
    private Long clickCount;
    private Date startTime;
    private Date endTime;
    private Boolean isEnabled;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advertising_id")
    public int getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(int advertisingId) {
        this.advertisingId = advertisingId;
    }

    @Basic
    @Column(name = "ad_name")
    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "ad_code")
    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    @Basic
    @Column(name = "ad_weight")
    public Integer getAdWeight() {
        return adWeight;
    }

    public void setAdWeight(Integer adWeight) {
        this.adWeight = adWeight;
    }

    @Basic
    @Column(name = "display_count")
    public Long getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(Long displayCount) {
        this.displayCount = displayCount;
    }

    @Basic
    @Column(name = "click_count")
    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    @Basic
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "is_enabled")
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsAdvertising that = (CmsAdvertising) o;

        if (advertisingId != that.advertisingId) return false;
        if (adWeight != that.adWeight) return false;
        if (displayCount != that.displayCount) return false;
        if (clickCount != that.clickCount) return false;
        if (adName != null ? !adName.equals(that.adName) : that.adName != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (adCode != null ? !adCode.equals(that.adCode) : that.adCode != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (isEnabled != null ? !isEnabled.equals(that.isEnabled) : that.isEnabled != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = advertisingId;
        result = 31 * result + (adName != null ? adName.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (adCode != null ? adCode.hashCode() : 0);
        result = 31 * result + adWeight;
        result = 31 * result + (int) (displayCount ^ (displayCount >>> 32));
        result = 31 * result + (int) (clickCount ^ (clickCount >>> 32));
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (isEnabled != null ? isEnabled.hashCode() : 0);
        return result;
    }

    private CmsAdvertisingSpace adspace;

    private Map<String,String> attr;

    private CmsSite site;
    public static String REF = "CmsAdvertising";
    public static String PROP_END_TIME = "endTime";
    public static String PROP_START_TIME = "startTime";
    public static String PROP_WEIGHT = "weight";
    public static String PROP_SITE = "site";
    public static String PROP_ENABLED = "enabled";
    public static String PROP_CODE = "code";
    public static String PROP_CATEGORY = "category";
    public static String PROP_ADSPACE = "adspace";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
    public static String PROP_CLICK_COUNT = "clickCount";
    public static String PROP_DISPLAY_COUNT = "displayCount";
    @ManyToOne
    @JoinColumn(name = "site_id",insertable = true,updatable = true)
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }

    @ElementCollection
    @CollectionTable(name = "c_advertising_attr",joinColumns={ @JoinColumn(nullable=false, name="advertising_id")})
    @MapKeyColumn(name="attr_name")//指定map的key生成的列
    @Column(name ="attr_value")
    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
    @ManyToOne
    @JoinColumn(name = "adspace_id")
    public CmsAdvertisingSpace getAdspace() {
        return adspace;
    }

    public void setAdspace(CmsAdvertisingSpace adspace) {
        this.adspace = adspace;
    }
    public void init() {
        if (getClickCount() == null) {
            setClickCount(0L);
        }
        if (getDisplayCount() == null) {
            setDisplayCount(0L);
        }
        if (getIsEnabled() == null) {
            setIsEnabled(true);
        }
        if (getAdWeight() == null) {
            setAdWeight(1);
        }
        blankToNull();
    }
    public void blankToNull() {
        if (StringUtils.isBlank(getAdCode())) {
            setAdCode(null);
        }
    }

    @Transient
   public int getPercent(){
       if(getDisplayCount()<=0) {
           return 0;
       }else {
            return (int)(getClickCount()*100/getDisplayCount());
       }
   }
}
