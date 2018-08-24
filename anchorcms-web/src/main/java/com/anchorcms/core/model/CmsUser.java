package com.anchorcms.core.model;

import com.anchorcms.cms.model.assist.CmsUserResume;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.synergy.SCompany;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-2
 * @Desc 用户实体类
 */
@Entity
@Table(name = "c_user")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class CmsUser implements Serializable{
    private static final long serialVersionUID = -2256496850453558311L;
    private Integer userId;
    private String username;
    private String email;
    private Date registerTime;
    private String registerIp;
    private Date lastLoginTime;
    private String lastLoginIp;
    private int loginCount;
    private Integer rank;
    private Long uploadTotal;
    private Integer uploadSize;
    private Date uploadDate;
    private Boolean isAdmin;
    private Boolean isSelfAdmin;
    private Boolean isDisabled;
    private String sessionId;
    private String isMain;

    @Id
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "is_main")
    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "register_time")
    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Basic
    @Column(name = "register_ip")
    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    @Basic
    @Column(name = "last_login_time")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "last_login_ip")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Basic
    @Column(name = "login_count")
    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    @Basic
    @Column(name = "rank")
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "upload_total")
    public Long getUploadTotal() {
        return uploadTotal;
    }

    public void setUploadTotal(Long uploadTotal) {
        this.uploadTotal = uploadTotal;
    }

    @Basic
    @Column(name = "upload_size")
    public Integer getUploadSize() {
        return uploadSize;
    }

    public void setUploadSize(Integer uploadSize) {
        this.uploadSize = uploadSize;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "upload_date")
    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Basic
    @Column(name = "is_admin")
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Basic
    @Column(name = "is_self_admin")
    public Boolean getIsSelfAdmin() {
        return isSelfAdmin;
    }

    public void setIsSelfAdmin(Boolean isSelfAdmin) {
        this.isSelfAdmin = isSelfAdmin;
    }

    @Basic
    @Column(name = "is_disabled")
    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Basic
    @Column(name = "session_id")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsUser cmsUser = (CmsUser) o;

        if (userId != cmsUser.userId) return false;
        if (loginCount != cmsUser.loginCount) return false;
        if (!rank.equals(cmsUser.rank)) return false;
        if (!uploadTotal.equals(cmsUser.uploadTotal)) return false;
        if (!uploadSize.equals(cmsUser.uploadSize)) return false;
        if (isAdmin != cmsUser.isAdmin) return false;
        if (isSelfAdmin != cmsUser.isSelfAdmin) return false;
        if (isDisabled != cmsUser.isDisabled) return false;
        if (username != null ? !username.equals(cmsUser.username) : cmsUser.username != null) return false;
        if (email != null ? !email.equals(cmsUser.email) : cmsUser.email != null) return false;
        if (registerTime != null ? !registerTime.equals(cmsUser.registerTime) : cmsUser.registerTime != null) return false;
        if (registerIp != null ? !registerIp.equals(cmsUser.registerIp) : cmsUser.registerIp != null) return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(cmsUser.lastLoginTime) : cmsUser.lastLoginTime != null)
            return false;
        if (lastLoginIp != null ? !lastLoginIp.equals(cmsUser.lastLoginIp) : cmsUser.lastLoginIp != null) return false;
        if (uploadDate != null ? !uploadDate.equals(cmsUser.uploadDate) : cmsUser.uploadDate != null) return false;
        if (sessionId != null ? !sessionId.equals(cmsUser.sessionId) : cmsUser.sessionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (registerTime != null ? registerTime.hashCode() : 0);
        result = 31 * result + (registerIp != null ? registerIp.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (lastLoginIp != null ? lastLoginIp.hashCode() : 0);
        result = 31 * result + loginCount;
        result = 31 * result + rank;
        result = 31 * result + (int) (uploadTotal ^ (uploadTotal >>> 32));
        result = 31 * result + uploadSize;
        result = 31 * result + (uploadDate != null ? uploadDate.hashCode() : 0);
        result = 31 * result + (!isAdmin ? 0 :1);
        result = 31 * result + (!isSelfAdmin ? 0 :1);
        result = 31 * result + (!isDisabled ? 0 :1);
        return result;
    }

    private Map<String, String> attr;

    private Set<CmsRole> roles;

    private CmsGroup group;

    @ElementCollection
    @CollectionTable(name = "c_user_attr",joinColumns={ @JoinColumn(nullable=false, name="user_id")})
    @MapKeyColumn(name="attr_name")//指定map的key生成的列
    @Column(name ="attr_value")
    public Map<String, String> getAttr() {
        return attr;
    }

    private Set<CmsUserExt> userExtSet;

    private Set<Channel> channels;

    private Set<Content> collectContents;

    private Set<CmsUserSite> userSites;

    private Set<CmsUserResume> userResumeSet;

    private SCompany company;

    private CmsUserExt userExtObj;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public CmsUserExt getUserExtObj() {
        return userExtObj;
    }

    public void setUserExtObj(CmsUserExt userExtObj) {
        this.userExtObj = userExtObj;
    }

    @ManyToOne
    @JoinColumn(name = "company_id",insertable = true,updatable = true)
    public SCompany getCompany() {
        return company;
    }

    public void setCompany(SCompany company) {
        this.company = company;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",orphanRemoval = true)
    //@JoinColumn(name="user_id",insertable = false,updatable = false)
    public Set<CmsUserSite> getUserSites() {
        return userSites;
    }

    public void setUserSites(Set<CmsUserSite> userSites) {
        this.userSites = userSites;
    }
    @OneToMany
    @JoinColumn(name="user_id",insertable = false,updatable = false)
    public Set<Content> getCollectContents() {
        return collectContents;
    }

    public void setCollectContents(Set<Content> collectContents) {
        this.collectContents = collectContents;
    }
   /* @ManyToMany
    @JoinTable(name="c_channel_user",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="channel_id")})*/
    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE)
    public Set<Channel> getChannels() {
        return channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",orphanRemoval = true)
    public Set<CmsUserExt> getUserExtSet() {
        return userExtSet;
    }
    @ManyToMany
    @JoinTable(name="c_user_role",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="role_id")})
    public Set<CmsRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<CmsRole> roles) {
        this.roles = roles;
    }

    public void setUserExtSet(Set<CmsUserExt> userExtSet) {
        this.userExtSet = userExtSet;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
    @ManyToOne
    @JoinColumn(name="group_id")
    public CmsGroup getGroup() {
        return group;
    }

    public void setGroup(CmsGroup group) {
        this.group = group;
    }

    private Set<CmsUserAccount> userAccountSet;

    @OneToMany
    @JoinColumn(name="user_id",insertable = false,updatable = false)
    public Set<CmsUserAccount> getUserAccountSet() {
        return userAccountSet;
    }

    public void setUserAccountSet(Set<CmsUserAccount> userAccountSet) {
        this.userAccountSet = userAccountSet;
    }

    @OneToMany(mappedBy = "user",orphanRemoval = true)
    public Set<CmsUserResume> getUserResumeSet() {
        return userResumeSet;
    }

    public void setUserResumeSet(Set<CmsUserResume> userResumeSet) {
        this.userResumeSet = userResumeSet;
    }

    public void forMember(UnifiedUser u) {
        forUser(u);
        setIsAdmin(false);
        setRank(0);
        setIsSelfAdmin(false);
    }
    public void forAdmin(UnifiedUser u, boolean viewonly, boolean selfAdmin,int rank) {
        forUser(u);
        setIsAdmin(true);
        setRank(rank);
        setIsSelfAdmin(selfAdmin);
    }
    public void forUser(UnifiedUser u) {
        setIsDisabled(false);
        setUserId(u.getUserId());
        setUsername(u.getUsername());
        setEmail(u.getEmail());
        setRegisterIp(u.getRegisterIp());
        setRegisterTime(u.getRegisterTime());
        setLastLoginIp(u.getLastLoginIp());
        setLastLoginTime(u.getLastLoginTime());
        setLoginCount(0);
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-11-2 14:56
     * @Desc 初始化
     */
    public void init() {
        if (getUploadTotal() == null) {
            setUploadTotal(0L);
        }
        if (getUploadSize() == null) {
            setUploadSize(0);
        }
        if (getUploadDate() == null) {
            setUploadDate(new java.sql.Date(System.currentTimeMillis()));
        }
        if (getIsAdmin() == null) {
            setIsAdmin(false);
        }
        if (getRank() == null) {
            setRank(0);
        }
        if (getIsSelfAdmin() == null) {
            setIsSelfAdmin(false);
        }
        if (getIsDisabled() == null) {
            setIsDisabled(false);
        }
    }
    public static Integer[] fetchIds(Collection<CmsUser> users) {
        if (users == null) {
            return null;
        }
        Integer[] ids = new Integer[users.size()];
        int i = 0;
        for (CmsUser u : users) {
            ids[i++] = u.getUserId();
        }
        return ids;
    }
    /**
     * 是否是今天。根据System.currentTimeMillis() / 1000 / 60 / 60 / 24计算。
     *
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        long day = date.getTime() / 1000 / 60 / 60 / 24;
        long currentDay = System.currentTimeMillis() / 1000 / 60 / 60 / 24;
        return day==(currentDay-1);
    }

    public void addToRoles(CmsRole role) {
        if (role == null) {
            return;
        }
        Set<CmsRole> set = getRoles();
        if (set == null) {
            set = new HashSet<CmsRole>();
            setRoles(set);
        }
        set.add(role);
    }

    public void addToChannels(Channel channel) {
        if (channel == null) {
            return;
        }
        Set<Channel> set = getChannels();
        if (set == null) {
            set = new HashSet<Channel>();
            setChannels(set);
        }
        set.add(channel);
    }
    @Transient
    public CmsUserExt getUserExt() {
        Set<CmsUserExt> set = getUserExtSet();
        if (set != null && set.size() > 0) {
            return set.iterator().next();
        } else {
            return null;
        }
    }

    public void addToCollection(Content content) {
        if (content == null) {
            return;
        }
        Set<Content> set =getCollectContents();
        if (set == null) {
            set = new HashSet<Content>();
            setCollectContents(set);
        }
        set.add(content);
    }
    public void delFromCollection(Content content) {
        if (content == null) {
            return;
        }
        Set<Content> set =getCollectContents();
        if (set == null) {
            return;
        }else{
            set.remove(content);
        }
    }
    public void clearCollection() {
        getCollectContents().clear();
    }
    @Transient
    public CmsUserSite getUserSite(Integer siteId) {
        Set<CmsUserSite> set = getUserSites();
        for (CmsUserSite us : set) {
            if (us.getSite().getSiteId().equals(siteId)) {
                return us;
            }
        }
        return null;
    }
    @Transient
    public Byte getCheckStep(Integer siteId) {
        CmsUserSite us = getUserSite(siteId);
        if (us != null) {
            return getUserSite(siteId).getCheckStep();
        } else {
            return null;
        }
    }
    @Transient
    public Set<String> getPerms(Integer siteId,Set<String>perms) {
        if(getIsDisabled()){
            return null;
        }
        Set<CmsUserSite> userSits=getUserSites();
        if(userSits==null){
            return null;
        }
        Set<CmsRole> roles = getRoles();
        if (roles == null) {
            return null;
        }
        boolean hasSitePermission=false;
        for(CmsUserSite cus:userSits){
            if(cus.getSite().getSiteId().equals(siteId)){
                hasSitePermission=true;
            }
        }
        if(!hasSitePermission){
            return null;
        }
        boolean isSuper = false;
        Set<String> allPerms = new HashSet<String>();
        for (CmsRole role : getRoles()) {
            if(role.getIsSuper()){
                isSuper=true;
                break;
            }
            allPerms.addAll(role.getPerms());
        }
        if (isSuper) {
            allPerms.clear();
            allPerms.add("*");
        }
        return allPerms;
    }
    @Transient
    public Set<CmsSite> getSites() {
        Set<CmsUserSite> userSites = getUserSites();
        Set<CmsSite> sites = new HashSet<CmsSite>(userSites.size());
        for (CmsUserSite us : userSites) {
            sites.add(us.getSite());
        }
        return sites;
    }

    @Transient
    public String getRealname() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getRealname();
        } else {
            return null;
        }
    }

    @Transient
    public Integer[] getRoleIds() {
        Set<CmsRole> roles = getRoles();
        return CmsRole.fetchIds(roles);
    }

    @Transient
    public Integer[] getSiteIds() {
        Set<CmsSite> sites = getSites();
        return CmsSite.fetchIds(sites);
    }

    @Transient
    public Integer[] getChannelIds() {
        Set<Channel> channels = getChannels();
        return Channel.fetchIds(channels);
    }

    @Transient
    public Set<Integer> getChannelIds(Integer siteId) {
        Set<Channel> channels = getChannels();
        Set<Integer> ids = new HashSet<Integer>();
        for (Channel c : channels) {
            if (c.getSite().getSiteId().equals(siteId)) {
                ids.add(c.getChannelId());
            }
        }
        return ids;
    }
    @Transient
    public Set<Channel> getChannels(Integer siteId) {
        Set<Channel> set = getChannels();
        Set<Channel> results = new HashSet<Channel>();
        for (Channel c : set) {
            if (c.getSite().getSiteId().equals(siteId)) {
                results.add(c);
            }
        }
        return results;
    }


    /**
     * 是否允许上传后缀
     *
     * @param ext
     * @return
     */
    public boolean isAllowSuffix(String ext) {
        return getGroup().isAllowSuffix(ext);
    }
    /**
     * 是否允许上传，根据文件大小
     *
     * @param size
     *            文件大小，单位KB
     * @return
     */
    public boolean isAllowMaxFile(int size) {
        int allowPerFile = getGroup().getAllowMaxFile();
        if (allowPerFile == 0) {
            return true;
        } else {
            return allowPerFile >= size;
        }
    }
    /**
     * 是否允许上传，根据每日限额
     *
     * @param size
     * @return
     */
    public boolean isAllowPerDay(int size) {
        int allowPerDay = getGroup().getAllowPerDay();
        if (allowPerDay == 0) {
            return true;
        }
        if (getUploadDate() != null) {
            if (isToday(getUploadDate())) {
                size += getUploadSize();
            }
        }
        return allowPerDay >= size;
    }
    @Transient
    public CmsUserAccount getUserAccount() {
        Set<CmsUserAccount> set = getUserAccountSet();
        if (set != null && set.size() > 0) {
            return set.iterator().next();
        } else {
            return null;
        }
    }
    @Transient
    public Short getDrawAccount() {
        CmsUserAccount ext = getUserAccount();
        if (ext != null) {
            return ext.getDrawAccount();
        } else {
            return 0;
        }
    }
    @Transient
    public String getAccountWeixin() {
        CmsUserAccount ext = getUserAccount();
        if (ext != null) {
            return ext.getAccountWeixin();
        } else {
            return null;
        }
    }
    @Transient
    public String getAccountAlipy() {
        CmsUserAccount ext = getUserAccount();
        if (ext != null) {
            return ext.getAccountAlipy();
        } else {
            return null;
        }
    }

    @Transient
    public Boolean getGender() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getGender();
        } else {
            return null;
        }
    }
    @Transient
    public Date getBirthday() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getBirthday();
        } else {
            return null;
        }
    }
    @Transient
    public String getIntro() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getIntro();
        } else {
            return null;
        }
    }
    @Transient
    public String getComefrom() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getComefrom();
        } else {
            return null;
        }
    }
    @Transient
    public String getQq() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getQq();
        } else {
            return null;
        }
    }
    @Transient
    public String getMsn() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getMsn();
        } else {
            return null;
        }
    }
    @Transient
    public String getPhone() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getPhone();
        } else {
            return null;
        }
    }
    @Transient
    public String getMobile() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getMobile();
        } else {
            return null;
        }
    }
    @Transient
    public String getUserImg() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getUserImg();
        } else {
            return null;
        }
    }
    @Transient
    public String getUserSignature() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getUserSignature();
        } else {
            return null;
        }
    }

    @Transient
    public CmsUserResume getUserResume(){
        Set<CmsUserResume>set=getUserResumeSet();
        if(set!=null&&set.size()>0){
            return set.iterator().next();
        }else{
            return null;
        }
    }
}
