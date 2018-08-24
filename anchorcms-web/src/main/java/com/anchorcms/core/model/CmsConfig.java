package com.anchorcms.core.model;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-4
 * @Desc
 */
@Entity
@Table(name = "c_config")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsConfig implements Serializable{
    private static final long serialVersionUID = 9078672120815388158L;
    private int configId;
    private String contextPath;
    private String servletPoint;
    private Integer port;
    private String dbFileUri;
    private Boolean isUploadToDb;
    private String defImg;
    private String loginUrl;
    private String processUrl;
    private Date countClearTime;
    private Date countCopyTime;
    private String downloadCode;
    private int downloadTime;
    private String emailHost;
    private String emailEncoding;
    private String emailUsername;
    private String emailPassword;
    private String emailPersonal;
    private Boolean emailValidate;
    private Boolean viewOnlyChecked;
    private Date flowClearTime;
    private Date channelCountClearTime;

    @Id
    @Column(name = "config_id")
    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    @Basic
    @Column(name = "context_path")
    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Basic
    @Column(name = "servlet_point")
    public String getServletPoint() {
        return servletPoint;
    }

    public void setServletPoint(String servletPoint) {
        this.servletPoint = servletPoint;
    }

    @Basic
    @Column(name = "port")
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Basic
    @Column(name = "db_file_uri")
    public String getDbFileUri() {
        return dbFileUri;
    }

    public void setDbFileUri(String dbFileUri) {
        this.dbFileUri = dbFileUri;
    }

    @Basic
    @Column(name = "is_upload_to_db")
    public Boolean getIsUploadToDb() {
        return isUploadToDb;
    }

    public void setIsUploadToDb(Boolean isUploadToDb) {
        this.isUploadToDb = isUploadToDb;
    }

    @Basic
    @Column(name = "def_img")
    public String getDefImg() {
        return defImg;
    }

    public void setDefImg(String defImg) {
        this.defImg = defImg;
    }

    @Basic
    @Column(name = "login_url")
    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Basic
    @Column(name = "process_url")
    public String getProcessUrl() {
        return processUrl;
    }

    public void setProcessUrl(String processUrl) {
        this.processUrl = processUrl;
    }

    @Basic
    @Column(name = "count_clear_time")
    public Date getCountClearTime() {
        return countClearTime;
    }

    public void setCountClearTime(Date countClearTime) {
        this.countClearTime = countClearTime;
    }

    @Basic
    @Column(name = "count_copy_time")
    public Date getCountCopyTime() {
        return countCopyTime;
    }

    public void setCountCopyTime(Date countCopyTime) {
        this.countCopyTime = countCopyTime;
    }

    @Basic
    @Column(name = "download_code")
    public String getDownloadCode() {
        return downloadCode;
    }

    public void setDownloadCode(String downloadCode) {
        this.downloadCode = downloadCode;
    }

    @Basic
    @Column(name = "download_time")
    public int getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(int downloadTime) {
        this.downloadTime = downloadTime;
    }

    @Basic
    @Column(name = "email_host")
    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    @Basic
    @Column(name = "email_encoding")
    public String getEmailEncoding() {
        return emailEncoding;
    }

    public void setEmailEncoding(String emailEncoding) {
        this.emailEncoding = emailEncoding;
    }

    @Basic
    @Column(name = "email_username")
    public String getEmailUsername() {
        return emailUsername;
    }

    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }

    @Basic
    @Column(name = "email_password")
    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    @Basic
    @Column(name = "email_personal")
    public String getEmailPersonal() {
        return emailPersonal;
    }

    public void setEmailPersonal(String emailPersonal) {
        this.emailPersonal = emailPersonal;
    }

    @Basic
    @Column(name = "email_validate")
    public Boolean getEmailValidate() {
        return emailValidate;
    }

    public void setEmailValidate(Boolean emailValidate) {
        this.emailValidate = emailValidate;
    }

    @Basic
    @Column(name = "view_only_checked")
    public Boolean getViewOnlyChecked() {
        return viewOnlyChecked;
    }

    public void setViewOnlyChecked(Boolean viewOnlyChecked) {
        this.viewOnlyChecked = viewOnlyChecked;
    }

    @Basic
    @Column(name = "flow_clear_time")
    public Date getFlowClearTime() {
        return flowClearTime;
    }

    public void setFlowClearTime(Date flowClearTime) {
        this.flowClearTime = flowClearTime;
    }

    @Basic
    @Column(name = "channel_count_clear_time")
    public Date getChannelCountClearTime() {
        return channelCountClearTime;
    }

    public void setChannelCountClearTime(Date channelCountClearTime) {
        this.channelCountClearTime = channelCountClearTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsConfig cmsConfig = (CmsConfig) o;

        if (configId != cmsConfig.configId) return false;
        if (isUploadToDb != cmsConfig.isUploadToDb) return false;
        if (downloadTime != cmsConfig.downloadTime) return false;
        if (viewOnlyChecked != cmsConfig.viewOnlyChecked) return false;
        if (contextPath != null ? !contextPath.equals(cmsConfig.contextPath) : cmsConfig.contextPath != null) return false;
        if (servletPoint != null ? !servletPoint.equals(cmsConfig.servletPoint) : cmsConfig.servletPoint != null)
            return false;
        if (port != null ? !port.equals(cmsConfig.port) : cmsConfig.port != null) return false;
        if (dbFileUri != null ? !dbFileUri.equals(cmsConfig.dbFileUri) : cmsConfig.dbFileUri != null) return false;
        if (defImg != null ? !defImg.equals(cmsConfig.defImg) : cmsConfig.defImg != null) return false;
        if (loginUrl != null ? !loginUrl.equals(cmsConfig.loginUrl) : cmsConfig.loginUrl != null) return false;
        if (processUrl != null ? !processUrl.equals(cmsConfig.processUrl) : cmsConfig.processUrl != null) return false;
        if (countClearTime != null ? !countClearTime.equals(cmsConfig.countClearTime) : cmsConfig.countClearTime != null)
            return false;
        if (countCopyTime != null ? !countCopyTime.equals(cmsConfig.countCopyTime) : cmsConfig.countCopyTime != null)
            return false;
        if (downloadCode != null ? !downloadCode.equals(cmsConfig.downloadCode) : cmsConfig.downloadCode != null)
            return false;
        if (emailHost != null ? !emailHost.equals(cmsConfig.emailHost) : cmsConfig.emailHost != null) return false;
        if (emailEncoding != null ? !emailEncoding.equals(cmsConfig.emailEncoding) : cmsConfig.emailEncoding != null)
            return false;
        if (emailUsername != null ? !emailUsername.equals(cmsConfig.emailUsername) : cmsConfig.emailUsername != null)
            return false;
        if (emailPassword != null ? !emailPassword.equals(cmsConfig.emailPassword) : cmsConfig.emailPassword != null)
            return false;
        if (emailPersonal != null ? !emailPersonal.equals(cmsConfig.emailPersonal) : cmsConfig.emailPersonal != null)
            return false;
        if (emailValidate != null ? !emailValidate.equals(cmsConfig.emailValidate) : cmsConfig.emailValidate != null)
            return false;
        if (flowClearTime != null ? !flowClearTime.equals(cmsConfig.flowClearTime) : cmsConfig.flowClearTime != null)
            return false;
        if (channelCountClearTime != null ? !channelCountClearTime.equals(cmsConfig.channelCountClearTime) : cmsConfig.channelCountClearTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = configId;
        result = 31 * result + (contextPath != null ? contextPath.hashCode() : 0);
        result = 31 * result + (servletPoint != null ? servletPoint.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (dbFileUri != null ? dbFileUri.hashCode() : 0);
        // result = 31 * result + (int) isUploadToDb;
        result = 31 * result + (defImg != null ? defImg.hashCode() : 0);
        result = 31 * result + (loginUrl != null ? loginUrl.hashCode() : 0);
        result = 31 * result + (processUrl != null ? processUrl.hashCode() : 0);
        result = 31 * result + (countClearTime != null ? countClearTime.hashCode() : 0);
        result = 31 * result + (countCopyTime != null ? countCopyTime.hashCode() : 0);
        result = 31 * result + (downloadCode != null ? downloadCode.hashCode() : 0);
        result = 31 * result + downloadTime;
        result = 31 * result + (emailHost != null ? emailHost.hashCode() : 0);
        result = 31 * result + (emailEncoding != null ? emailEncoding.hashCode() : 0);
        result = 31 * result + (emailUsername != null ? emailUsername.hashCode() : 0);
        result = 31 * result + (emailPassword != null ? emailPassword.hashCode() : 0);
        result = 31 * result + (emailPersonal != null ? emailPersonal.hashCode() : 0);
        result = 31 * result + (emailValidate != null ? emailValidate.hashCode() : 0);
        result = 31 * result + (flowClearTime != null ? flowClearTime.hashCode() : 0);
        result = 31 * result + (channelCountClearTime != null ? channelCountClearTime.hashCode() : 0);
        return result;
    }

    private Map<String,String> attr;

    private MarkConfig m_markConfig;
    @Embedded
    public MarkConfig getMarkConfig() {
        return m_markConfig;
    }

    public void setMarkConfig(MarkConfig m_markConfig) {
        this.m_markConfig = m_markConfig;
    }

    @ElementCollection
    @CollectionTable(name = "c_config_attr",joinColumns={ @JoinColumn(nullable=false, name="config_id")})
    @MapKeyColumn(name="attr_name")//指定map的key生成的列
    @Column(name ="attr_value")
    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
    @Transient
    public Integer getContentFreshMinute(){
        CmsConfigAttr configAttr=getConfigAttr();
        return configAttr.getContentFreshMinute();
    }
    @Transient
    public CmsConfigAttr getConfigAttr() {
        CmsConfigAttr configAttr = new CmsConfigAttr(getAttr());
        return configAttr;
    }
    public void blankToNull() {
        // oracle varchar2把空串当作null处理，这里为了统一这个特征，特做此处理。
        if (StringUtils.isBlank(getProcessUrl())) {
            setProcessUrl(null);
        }
        if (StringUtils.isBlank(getContextPath())) {
            setContextPath(null);
        }
        if (StringUtils.isBlank(getServletPoint())) {
            setServletPoint(null);
        }
    }
    @Transient
    public Boolean getSsoEnable(){
        CmsConfigAttr configAttr=getConfigAttr();
        return configAttr.getSsoEnable();
    }
    @Transient
    public MemberConfig getMemberConfig() {
        return new MemberConfig(getAttr());
    }
    @Transient
    public Map<String,String> getSsoAttr() {
        Map<String,String>ssoMap=new HashMap<String, String>();
        Map<String,String>attr=getAttr();
        for(String ssoKey:attr.keySet()){
            if(ssoKey.startsWith("sso_")){
                ssoMap.put(ssoKey, attr.get(ssoKey));
            }
        }
        return ssoMap;
    }
    private static final String VERSION = "version";
    @Transient
    public String getVersion() {
        return getAttr().get(VERSION);
    }

    @Transient
    public List<String> getSsoAuthenticateUrls() {
        Map<String,String>ssoMap=getSsoAttr();
        List<String>values=new ArrayList<String>();
        for(String key:ssoMap.keySet()){
            values.add(ssoMap.get(key));
        }
        return values;
    }

    @Transient
    public Boolean getFlowSwitch(){
        CmsConfigAttr configAttr=getConfigAttr();
        return configAttr.getFlowSwitch();
    }

    @Transient
    public Boolean getWeixinEnable(){
        CmsConfigAttr configAttr=getConfigAttr();
        return configAttr.getWeixinEnable();
    }
    @Transient
    public String getWeixinID(){
        CmsConfigAttr configAttr=getConfigAttr();
        return configAttr.getWeixinID();
    }
    @Transient
    public String getWeixinKey(){
        CmsConfigAttr configAttr=getConfigAttr();
        return configAttr.getWeixinKey();
    }
}
