package com.anchorcms.core.model;

import com.anchorcms.cms.model.assist.CmsSiteAccessCount;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.*;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-2
 * @Desc CMS站点信息实体类
 */
@Entity
@Table(name = "c_site")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsSite implements Serializable {
    private static final long serialVersionUID = -5898275025484020911L;
    private Integer siteId;
    private String domain;
    private String sitePath;
    private String siteName;
    private String shortName;
    private String protocol;
    private String dynamicSuffix;
    private String staticSuffix;
    private String staticDir;
    private Boolean isIndexToRoot;
    private Boolean isStaticIndex;
    private String localeAdmin;
    private String localeFront;
    private String tplSolution;
    private Byte finalStep;
    private byte afterCheck;
    private Boolean isRelativePath;
    private Boolean isRecycleOn;
    private String domainAlias;
    private String domainRedirect;
    private String tplIndex;
    private String keywords;
    private String description;
    private String tplMobileSolution;
    private String mobileStaticDir;
    private Boolean mobileStaticSync;
    private Integer ftpSyncPageId;
    private Boolean pageIsSyncFtp;
    private Boolean resouceIsSyncFtp;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_id")
    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @Basic
    @Column(name = "domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Basic
    @Column(name = "site_path")
    public String getSitePath() {
        return sitePath;
    }

    public void setSitePath(String sitePath) {
        this.sitePath = sitePath;
    }

    @Basic
    @Column(name = "site_name")
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Basic
    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "protocol")
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Basic
    @Column(name = "dynamic_suffix")
    public String getDynamicSuffix() {
        return dynamicSuffix;
    }

    public void setDynamicSuffix(String dynamicSuffix) {
        this.dynamicSuffix = dynamicSuffix;
    }

    @Basic
    @Column(name = "static_suffix")
    public String getStaticSuffix() {
        return staticSuffix;
    }

    public void setStaticSuffix(String staticSuffix) {
        this.staticSuffix = staticSuffix;
    }

    @Basic
    @Column(name = "static_dir")
    public String getStaticDir() {
        return staticDir;
    }

    public void setStaticDir(String staticDir) {
        this.staticDir = staticDir;
    }

    @Basic
    @Column(name = "is_index_to_root")
    public Boolean getIsIndexToRoot() {
        return isIndexToRoot;
    }

    public void setIsIndexToRoot(Boolean isIndexToRoot) {
        this.isIndexToRoot = isIndexToRoot;
    }

    @Basic
    @Column(name = "is_static_index")
    public Boolean getIsStaticIndex() {
        return isStaticIndex;
    }

    public void setIsStaticIndex(Boolean isStaticIndex) {
        this.isStaticIndex = isStaticIndex;
    }

    @Basic
    @Column(name = "locale_admin")
    public String getLocaleAdmin() {
        return localeAdmin;
    }

    public void setLocaleAdmin(String localeAdmin) {
        this.localeAdmin = localeAdmin;
    }

    @Basic
    @Column(name = "locale_front")
    public String getLocaleFront() {
        return localeFront;
    }

    public void setLocaleFront(String localeFront) {
        this.localeFront = localeFront;
    }

    @Basic
    @Column(name = "tpl_solution")
    public String getTplSolution() {
        return tplSolution;
    }

    public void setTplSolution(String tplSolution) {
        this.tplSolution = tplSolution;
    }

    @Basic
    @Column(name = "final_step")
    public Byte getFinalStep() {
        return finalStep;
    }

    public void setFinalStep(Byte finalStep) {
        this.finalStep = finalStep;
    }

    @Basic
    @Column(name = "after_check")
    public byte getAfterCheck() {
        return afterCheck;
    }

    public void setAfterCheck(byte afterCheck) {
        this.afterCheck = afterCheck;
    }

    @Basic
    @Column(name = "is_relative_path")
    public Boolean getIsRelativePath() {
        return isRelativePath;
    }

    public void setIsRelativePath(Boolean isRelativePath) {
        this.isRelativePath = isRelativePath;
    }

    @Basic
    @Column(name = "is_recycle_on")
    public Boolean getIsRecycleOn() {
        return isRecycleOn;
    }

    public void setIsRecycleOn(Boolean isRecycleOn) {
        this.isRecycleOn = isRecycleOn;
    }

    @Basic
    @Column(name = "domain_alias")
    public String getDomainAlias() {
        return domainAlias;
    }

    public void setDomainAlias(String domainAlias) {
        this.domainAlias = domainAlias;
    }

    @Basic
    @Column(name = "domain_redirect")
    public String getDomainRedirect() {
        return domainRedirect;
    }

    public void setDomainRedirect(String domainRedirect) {
        this.domainRedirect = domainRedirect;
    }

    @Basic
    @Column(name = "tpl_index")
    public String getTplIndex() {
        return tplIndex;
    }

    public void setTplIndex(String tplIndex) {
        this.tplIndex = tplIndex;
    }

    @Basic
    @Column(name = "keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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
    @Column(name = "tpl_mobile_solution")
    public String getTplMobileSolution() {
        return tplMobileSolution;
    }

    public void setTplMobileSolution(String tplMobileSolution) {
        this.tplMobileSolution = tplMobileSolution;
    }

    @Basic
    @Column(name = "mobile_static_dir")
    public String getMobileStaticDir() {
        return mobileStaticDir;
    }

    public void setMobileStaticDir(String mobileStaticDir) {
        this.mobileStaticDir = mobileStaticDir;
    }

    @Basic
    @Column(name = "mobile_static_sync")
    public Boolean getMobileStaticSync() {
        return mobileStaticSync;
    }

    public void setMobileStaticSync(Boolean mobileStaticSync) {
        this.mobileStaticSync = mobileStaticSync;
    }

    @Basic
    @Column(name = "ftp_sync_page_id")
    public Integer getFtpSyncPageId() {
        return ftpSyncPageId;
    }

    public void setFtpSyncPageId(Integer ftpSyncPageId) {
        this.ftpSyncPageId = ftpSyncPageId;
    }

    @Basic
    @Column(name = "page_is_sync_ftp")
    public Boolean getPageIsSyncFtp() {
        return pageIsSyncFtp;
    }

    public void setPageIsSyncFtp(Boolean pageIsSyncFtp) {
        this.pageIsSyncFtp = pageIsSyncFtp;
    }

    @Basic
    @Column(name = "resouce_is_sync_ftp")
    public Boolean getResouceIsSyncFtp() {
        return resouceIsSyncFtp;
    }

    public void setResouceIsSyncFtp(Boolean resouceIsSyncFtp) {
        this.resouceIsSyncFtp = resouceIsSyncFtp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsSite cmsSite = (CmsSite) o;

        if (!siteId.equals(cmsSite.siteId)) return false;
        if (!finalStep.equals(cmsSite.finalStep)) return false;
        if (afterCheck != cmsSite.afterCheck) return false;
        if (mobileStaticSync != cmsSite.mobileStaticSync) return false;
        if (pageIsSyncFtp != cmsSite.pageIsSyncFtp) return false;
        if (resouceIsSyncFtp != cmsSite.resouceIsSyncFtp) return false;
        if (domain != null ? !domain.equals(cmsSite.domain) : cmsSite.domain != null) return false;
        if (sitePath != null ? !sitePath.equals(cmsSite.sitePath) : cmsSite.sitePath != null) return false;
        if (siteName != null ? !siteName.equals(cmsSite.siteName) : cmsSite.siteName != null) return false;
        if (shortName != null ? !shortName.equals(cmsSite.shortName) : cmsSite.shortName != null) return false;
        if (protocol != null ? !protocol.equals(cmsSite.protocol) : cmsSite.protocol != null) return false;
        if (dynamicSuffix != null ? !dynamicSuffix.equals(cmsSite.dynamicSuffix) : cmsSite.dynamicSuffix != null)
            return false;
        if (staticSuffix != null ? !staticSuffix.equals(cmsSite.staticSuffix) : cmsSite.staticSuffix != null) return false;
        if (staticDir != null ? !staticDir.equals(cmsSite.staticDir) : cmsSite.staticDir != null) return false;
        if (isIndexToRoot != null ? !isIndexToRoot.equals(cmsSite.isIndexToRoot) : cmsSite.isIndexToRoot != null)
            return false;
        if (isStaticIndex != null ? !isStaticIndex.equals(cmsSite.isStaticIndex) : cmsSite.isStaticIndex != null)
            return false;
        if (localeAdmin != null ? !localeAdmin.equals(cmsSite.localeAdmin) : cmsSite.localeAdmin != null) return false;
        if (localeFront != null ? !localeFront.equals(cmsSite.localeFront) : cmsSite.localeFront != null) return false;
        if (tplSolution != null ? !tplSolution.equals(cmsSite.tplSolution) : cmsSite.tplSolution != null) return false;
        if (isRelativePath != null ? !isRelativePath.equals(cmsSite.isRelativePath) : cmsSite.isRelativePath != null)
            return false;
        if (isRecycleOn != null ? !isRecycleOn.equals(cmsSite.isRecycleOn) : cmsSite.isRecycleOn != null) return false;
        if (domainAlias != null ? !domainAlias.equals(cmsSite.domainAlias) : cmsSite.domainAlias != null) return false;
        if (domainRedirect != null ? !domainRedirect.equals(cmsSite.domainRedirect) : cmsSite.domainRedirect != null)
            return false;
        if (tplIndex != null ? !tplIndex.equals(cmsSite.tplIndex) : cmsSite.tplIndex != null) return false;
        if (keywords != null ? !keywords.equals(cmsSite.keywords) : cmsSite.keywords != null) return false;
        if (description != null ? !description.equals(cmsSite.description) : cmsSite.description != null) return false;
        if (tplMobileSolution != null ? !tplMobileSolution.equals(cmsSite.tplMobileSolution) : cmsSite.tplMobileSolution != null)
            return false;
        if (mobileStaticDir != null ? !mobileStaticDir.equals(cmsSite.mobileStaticDir) : cmsSite.mobileStaticDir != null)
            return false;
        if (ftpSyncPageId != null ? !ftpSyncPageId.equals(cmsSite.ftpSyncPageId) : cmsSite.ftpSyncPageId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = siteId;
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        result = 31 * result + (sitePath != null ? sitePath.hashCode() : 0);
        result = 31 * result + (siteName != null ? siteName.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        result = 31 * result + (dynamicSuffix != null ? dynamicSuffix.hashCode() : 0);
        result = 31 * result + (staticSuffix != null ? staticSuffix.hashCode() : 0);
        result = 31 * result + (staticDir != null ? staticDir.hashCode() : 0);
        result = 31 * result + (isIndexToRoot != null ? isIndexToRoot.hashCode() : 0);
        result = 31 * result + (isStaticIndex != null ? isStaticIndex.hashCode() : 0);
        result = 31 * result + (localeAdmin != null ? localeAdmin.hashCode() : 0);
        result = 31 * result + (localeFront != null ? localeFront.hashCode() : 0);
        result = 31 * result + (tplSolution != null ? tplSolution.hashCode() : 0);
        result = 31 * result + (int) finalStep;
        result = 31 * result + (int) afterCheck;
        result = 31 * result + (isRelativePath != null ? isRelativePath.hashCode() : 0);
        result = 31 * result + (isRecycleOn != null ? isRecycleOn.hashCode() : 0);
        result = 31 * result + (domainAlias != null ? domainAlias.hashCode() : 0);
        result = 31 * result + (domainRedirect != null ? domainRedirect.hashCode() : 0);
        result = 31 * result + (tplIndex != null ? tplIndex.hashCode() : 0);
        result = 31 * result + (keywords != null ? keywords.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tplMobileSolution != null ? tplMobileSolution.hashCode() : 0);
        result = 31 * result + (mobileStaticDir != null ? mobileStaticDir.hashCode() : 0);
        result = 31 * result + (ftpSyncPageId != null ? ftpSyncPageId.hashCode() : 0);
        return result;
    }

    private Ftp uploadFtp;

    private Ftp syncPageFtp;

    private Map<String,String> attr;

    private Map<String,String> txt;

    private Map<String,String> cfg;

    private CmsConfig config;

    private Set<CmsSiteAccessCount> accessCounts;
    private Set<CmsUserSite> userSites;

    @OneToMany
    @JoinColumn(name = "site_id",insertable = false,unique = false)
    public Set<CmsSiteAccessCount> getAccessCounts() {
        return accessCounts;
    }

    public void setAccessCounts(Set<CmsSiteAccessCount> accessCounts) {
        this.accessCounts = accessCounts;
    }
    @OneToMany
    @JoinColumn(name = "site_id")
    public Set<CmsUserSite> getUserSites() {
        return userSites;
    }

    public void setUserSites(Set<CmsUserSite> userSites) {
        this.userSites = userSites;
    }




    @ElementCollection
    @CollectionTable(name = "c_site_cfg",joinColumns={ @JoinColumn(nullable=false, name="site_id")})
    @MapKeyColumn(name="cfg_name")//指定map的key生成的列
    @Column(name ="cfg_value")
    public Map<String, String> getCfg() {
        return cfg;
    }

    public void setCfg(Map<String, String> cfg) {
        this.cfg = cfg;
    }

    @ElementCollection
    @CollectionTable(name = "c_site_txt",joinColumns={ @JoinColumn(nullable=false, name="site_id")})
    @MapKeyColumn(name="txt_name")//指定map的key生成的列
    @Column(name ="txt_value")
    public Map<String, String> getTxt() {
        return txt;
    }

    public void setTxt(Map<String, String> txt) {
        this.txt = txt;
    }

    @ManyToOne
    @JoinColumn(name = "config_id")
    public CmsConfig getConfig() {
        return config;
    }

    public void setConfig(CmsConfig config) {
        this.config = config;
    }

    @ElementCollection
    @CollectionTable(name = "c_site_attr",joinColumns={ @JoinColumn(nullable=false, name="site_id")})
    @MapKeyColumn(name="attr_name")//指定map的key生成的列
    @Column(name ="attr_value")
    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
    @ManyToOne
    @JoinColumn(name = "ftp_upload_id",insertable = false,updatable = false)
    public Ftp getSyncPageFtp() {
        return syncPageFtp;
    }

    public void setSyncPageFtp(Ftp syncPageFtp) {
        this.syncPageFtp = syncPageFtp;
    }
    @ManyToOne
    @JoinColumn(name = "ftp_upload_id",insertable = false,updatable = false)
    public Ftp getUploadFtp() {
        return uploadFtp;
    }

    public void setUploadFtp(Ftp uploadFtp) {
        this.uploadFtp = uploadFtp;
    }


    public static final String PV_TOTAL="pvTotal";
    public static final String VISITORS="visitors";
    public static final String DAY_PV_TOTAL="dayPvTotal";
    public static final String DAY_VISITORS="dayVisitors";
    public static final String WEEK_PV_TOTAL="weekPvTotal";
    public static final String WEEK_VISITORS="weekVisitors";
    public static final String MONTH_PV_TOTAL="monthPvTotal";
    public static final String MONTH_VISITORS="monthVisitors";
    @Transient
    public Long getPvTotal(){
        String pv=getAttr().get(PV_TOTAL);
        if(StringUtils.isNotBlank(pv)){
            return Long.decode(pv);
        }else{
            return 0l;
        }
    }
    @Transient
    public Long getVisitorTotal(){
        String visitorNum=getAttr().get(VISITORS);
        if(StringUtils.isNotBlank(visitorNum)){
            return Long.decode(visitorNum);
        }else{
            return 0l;
        }
    }
    @Transient
    public Long getDayPvTotal(){
        String pv=getAttr().get(DAY_PV_TOTAL);
        if(StringUtils.isNotBlank(pv)){
            return Long.decode(pv);
        }else{
            return 0l;
        }
    }
    @Transient
    public Long getDayVisitorTotal(){
        String visitorNum=getAttr().get(DAY_VISITORS);
        if(StringUtils.isNotBlank(visitorNum)){
            return Long.decode(visitorNum);
        }else{
            return 0l;
        }
    }
    @Transient
    public Long getWeekPvTotal(){
        String pv=getAttr().get(WEEK_PV_TOTAL);
        if(StringUtils.isNotBlank(pv)){
            return Long.decode(pv);
        }else{
            return 0l;
        }
    }
    @Transient
    public Long getWeekVisitorTotal(){
        String visitorNum=getAttr().get(WEEK_VISITORS);
        if(StringUtils.isNotBlank(visitorNum)){
            return Long.decode(visitorNum);
        }else{
            return 0l;
        }
    }
    @Transient
    public Long getMonthPvTotal(){
        String pv=getAttr().get(MONTH_PV_TOTAL);
        if(StringUtils.isNotBlank(pv)){
            return Long.decode(pv);
        }else{
            return 0l;
        }
    }
    @Transient
    public Long getMonthVisitorTotal(){
        String visitorNum=getAttr().get(MONTH_VISITORS);
        if(StringUtils.isNotBlank(visitorNum)){
            return Long.decode(visitorNum);
        }else{
            return 0l;
        }
    }
    public void init() {
        if (StringUtils.isBlank(getProtocol())) {
            setProtocol("http://");
        }
        if (StringUtils.isBlank(getTplSolution())) {
            //默认路径名作为方案名
            setTplSolution(getSitePath());
            //setTplSolution(DEFAULT);
        }
        if (StringUtils.isBlank(getTplMobileSolution())) {
            //默认路径名作为方案名
            setTplMobileSolution(getSitePath());
        }
        if (getFinalStep() == null) {
            byte step = 2;
            setFinalStep(step);
        }
    }
    @Transient
    public String getTplPath() {
        return TPL_BASE + "/" + getSitePath();
    }
    /**
     * 获得模板资源路径。如：/r/cms/www
     *
     * @return
     */
    @Transient
    public String getResPath() {
        return RES_PATH + "/" + getSitePath();
    }
    @Transient
    public String getMobileSolutionPath() {
        return TPL_BASE + "/" + getSitePath() + "/" + getTplMobileSolution();
    }
    @Transient
    public String getSolutionPath() {
        return TPL_BASE + "/" + getSitePath() + "/" + getTplSolution();
    }
    @Transient
    public String getLoginUrl() {
        CmsConfig config = getConfig();
        if (config != null) {
            return config.getLoginUrl();
        } else {
            return null;
        }
    }
    @Transient
    public String getProcessUrl() {
        CmsConfig config = getConfig();
        if (config != null) {
            return config.getProcessUrl();
        } else {
            return null;
        }
    }
    @Transient
    public String getContextPath() {
        CmsConfig config = getConfig();
        if (config != null) {
            return config.getContextPath();
        } else {
            return null;
        }
    }
    @Transient
    public String getAdminUrl() {
        StringBuilder url = new StringBuilder();
        url.append(getUrlDynamic());
        url.append(ADMIN_SUFFIX);
        return url.toString();
    }
    @Transient
    public String getUrlDynamic() {
        return getUrlBuffer(true, null, false).append("/").toString();
    }
    public StringBuilder getUrlBuffer(boolean dynamic, Boolean whole,
                                      boolean forIndex) {
        boolean relative = whole != null ? !whole : getIsRelativePath();
        String ctx = getContextPath();
        StringBuilder url = new StringBuilder();
        if (!relative) {
            url.append(getProtocol()).append(getDomain());
            if (getPort() != null) {
                url.append(":").append(getPort());
            }
        }
        if (!StringUtils.isBlank(ctx)) {
            url.append(ctx);
        }
        if (dynamic) {
            String servlet = getServletPoint();
            if (!StringUtils.isBlank(servlet)) {
                url.append(servlet);
            }
        } else {
            if (!forIndex) {
                String staticDir = getStaticDir();
                if (!StringUtils.isBlank(staticDir)) {
                    url.append(staticDir);
                }
            }
        }
        return url;
    }
    @Transient
    public Integer getPort() {
        CmsConfig config = getConfig();
        if (config != null) {
            return config.getPort();
        } else {
            return null;
        }
    }
    @Transient
    public String getServletPoint() {
        CmsConfig config = getConfig();
        if (config != null) {
            return config.getServletPoint();
        } else {
            return null;
        }
    }
    @Transient
    public String getUrlStatic() {
        return getUrlBuffer(false, null, true).append("/").toString();
    }
    /**
     * 返回首页模板
     * @return
     */
    @Transient
    public String getTplIndexOrDef() {
        String tpl = getTplIndex();
        if (!StringUtils.isBlank(tpl)) {
            return tpl;
        } else {
            return getTplIndexDefault();
        }
    }
    /**
     * 返回首页默认模板(类似/WEB-INF/t/cms/www/default/index/index.html)
     * @return
     */
    @Transient
    private String getTplIndexDefault() {
        StringBuilder t = new StringBuilder();
        t.append(getTplIndexPrefix(TPLDIR_INDEX));
        t.append(TPL_SUFFIX);
        return t.toString();
    }
    /**
     * 返回完整前缀(类似/WEB-INF/t/cms/www/default/index/index)
     * @param prefix
     * @return
     */
    @Transient
    public String getTplIndexPrefix(String prefix) {
        StringBuilder t = new StringBuilder();
        t.append(getSolutionPath()).append("/");
        t.append(TPLDIR_INDEX).append("/");
        if (!StringUtils.isBlank(prefix)) {
            t.append(prefix);
        }
        return t.toString();
    }
    /**
     * 返回手机首页模板
     * @return
     */
    @Transient
    public String getMobileTplIndexOrDef() {
        StringBuilder t = new StringBuilder();
        t.append(getMobileSolutionPath()).append("/");
        t.append(TPLDIR_INDEX).append("/");
        t.append(TPLDIR_INDEX);
        t.append(TPL_SUFFIX);
        return t.toString();
    }
    public static Integer[] fetchIds(Collection<CmsSite> sites) {
        if (sites == null) {
            return null;
        }
        Integer[] ids = new Integer[sites.size()];
        int i = 0;
        for (CmsSite s : sites) {
            ids[i++] = s.getSiteId();
        }
        return ids;
    }
    @Transient
    public Boolean getMark(){
        return getConfig().getMarkConfig().getOn();
    }
    @Transient
    public String getUploadPath() {
        return UPLOAD_PATH + getSitePath();
    }
    /**
     * 获得站点url
     *
     * @return
     */
    @Transient
    public String getUrl() {
        StringBuilder url = new StringBuilder();
        if (getIsStaticIndex()) {
            url.append(getUrlStatic());
            if (!getIsIndexToRoot()) {
                if (!StringUtils.isBlank(getStaticDir())) {
                    url.append(getStaticDir());
                }
            }
            url.append(SPT).append(INDEX).append(getStaticSuffix());
        } else {
            url.append(getUrlDynamic());
        }
        return url.toString();
		/*
		if (getStaticIndex()) {
			return getUrlStatic();
		} else {
			return getUrlDynamic();
		}
		*/
    }
    @Transient
    public int getUsernameMinLen() {
        return getConfig().getMemberConfig().getUsernameMinLen();
    }
    @Transient
    public int getPasswordMinLen() {
        return getConfig().getMemberConfig().getPasswordMinLen();
    }
}
