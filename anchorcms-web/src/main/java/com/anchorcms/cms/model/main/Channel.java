package com.anchorcms.cms.model.main;

import com.anchorcms.common.hibernate.HibernateTree;
import com.anchorcms.common.hibernate.PriorityComparator;
import com.anchorcms.common.hibernate.PriorityInterface;
import com.anchorcms.common.utils.StaticPageUtils;
import com.anchorcms.core.model.CmsGroup;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

import static com.anchorcms.common.constants.Constants.INDEX;
import static com.anchorcms.common.constants.Constants.SPT;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-2
 * @Desc CMS栏目表
 */
@Entity
@Table(name = "c_channel")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class Channel implements Serializable,PriorityInterface, HibernateTree<Integer> {

    private static final long serialVersionUID = -9066154332731793820L;
    private Integer channelId;
    private String channelPath;
    private Integer lft;
    private Integer rgt;
    private Integer priority;
    private Boolean hasContent;
    private Boolean isDisplay;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    @Basic
    @Column(name = "channel_path")
    public String getChannelPath() {
        return channelPath;
    }

    public void setChannelPath(String channelPath) {
        this.channelPath = channelPath;
    }

    @Basic
    @Column(name = "lft")
    public Integer getLft() {
        return lft;
    }



    public void setLft(Integer lft) {
        this.lft = lft;
    }

    @Basic
    @Column(name = "rgt")
    public Integer getRgt() {
        return rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    @Basic
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }



    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "has_content")
    public Boolean getHasContent() {
        return hasContent;
    }

    public void setHasContent(Boolean hasContent) {
        this.hasContent = hasContent;
    }

    @Basic
    @Column(name = "is_display")
    public Boolean getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Channel channel = (Channel) o;

        if (channelId != channel.channelId) return false;
        if (lft != channel.lft) return false;
        if (rgt != channel.rgt) return false;
        if (priority != channel.priority) return false;
        if (hasContent != channel.hasContent) return false;
        if (isDisplay != channel.isDisplay) return false;
        if (channelPath != null ? !channelPath.equals(channel.channelPath) : channel.channelPath != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = channelId;
        result = 31 * result + (channelPath != null ? channelPath.hashCode() : 0);
        result = 31 * result + lft;
        result = 31 * result + rgt;
        result = 31 * result + priority;
        return result;
    }

    private java.util.Set<Channel> child;

    private Set<CmsGroup> viewGroups;

    private Set<CmsGroup> contriGroups;

    private Set<CmsUser> users;

    private ChannelExt channelExt;

    private Channel parent;

    private CmsSite site;

    private CmsModel model;

    private List<ChannelModel> channelModels;

    private Set<ChannelTxt> channelTxtSet;

    private ChannelCount channelCount;

    private Map<String,String> attr;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="parent",orphanRemoval = true)
    public Set<Channel> getChild() {
        return child;
    }

    public void setChild(Set<Channel> child) {
        this.child = child;
    }

    @ElementCollection
    @CollectionTable(name = "c_channel_attr",joinColumns={ @JoinColumn(nullable=false, name="channel_id")})
    @MapKeyColumn(name="attr_name")//指定map的key生成的列
    @Column(name ="attr_value")
    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    @OneToOne(cascade = CascadeType.REMOVE,mappedBy = "channel")
    public ChannelCount getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(ChannelCount channelCount) {
        this.channelCount = channelCount;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "channel",orphanRemoval = true)
    public Set<ChannelTxt> getChannelTxtSet() {
        return channelTxtSet;
    }

    public void setChannelTxtSet(Set<ChannelTxt> channelTxtSet) {
        this.channelTxtSet = channelTxtSet;
    }

//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "channel",orphanRemoval = true)
    @ElementCollection
    @CollectionTable(name="c_channel_model", joinColumns=@JoinColumn(name="channel_id"))
    @OrderColumn(name = "priority")
    public List<ChannelModel> getChannelModels() {
        return channelModels;
    }

    public void setChannelModels(List<ChannelModel> channelModels) {
        this.channelModels = channelModels;
    }
    @OneToOne
    @JoinColumn(name = "model_id",insertable = true,updatable = true)
    public CmsModel getModel() {
        return model;
    }

    public void setModel(CmsModel model) {
        this.model = model;
    }
    @ManyToOne
    @JoinColumn(name = "site_id",insertable = true,updatable = true)
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Channel getParent() {
        return parent;
    }

    public void setParent(Channel parent) {
        this.parent = parent;
    }

    @OneToOne(cascade = CascadeType.REMOVE,mappedBy = "channel")
    public ChannelExt getChannelExt() {
        return channelExt;
    }

    public void setChannelExt(ChannelExt channelExt) {
        this.channelExt = channelExt;
    }
    @ManyToMany
    @JoinTable(name="c_channel_user",
            joinColumns={@JoinColumn(name="channel_id")},
            inverseJoinColumns={@JoinColumn(name="user_id")})
    public Set<CmsUser> getUsers() {
        return users;
    }

    public void setUsers(Set<CmsUser> users) {
        this.users = users;
    }
    @ManyToMany
    @JoinTable(name = "c_chnl_group_view",
            joinColumns = {@JoinColumn(name = "channel_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    public Set<CmsGroup> getViewGroups() {
        return viewGroups;
    }

    public void setViewGroups(Set<CmsGroup> viewGroups) {
        this.viewGroups = viewGroups;
    }
    @ManyToMany
    @JoinTable(name = "c_chnl_group_contri",
            joinColumns = {@JoinColumn(name = "channel_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    public Set<CmsGroup> getContriGroups() {
        return contriGroups;
    }

    public void setContriGroups(Set<CmsGroup> contriGroups) {
        this.contriGroups = contriGroups;
    }

    /**
     * 审核后内容修改方式
     */
    public static enum AfterCheckEnum {
        /**
         * 不能修改，不能删除。
         */
        CANNOT_UPDATE,
        /**
         * 可以修改，可以删除。 修改后文章的审核级别将退回到修改人级别的状态。如果修改人的级别高于当前文章的审核级别，那么文章审核级别将保持不变。
         */
        BACK_UPDATE,
        /**
         * 可以修改，可以删除。 修改后文章保持原状态。
         */
        KEEP_UPDATE
    }
    public  void removeViewGroup(CmsGroup group) {
        Set<CmsGroup>viewGroups=getViewGroups();
        viewGroups.remove(group);
    }
    public  void removeContriGroup(CmsGroup group) {
        Set<CmsGroup>contriGroups=getContriGroups();
        contriGroups.remove(group);
    }
    public void addToUsers(CmsUser user) {
        Set<CmsUser> set = getUsers();
        if (set == null) {
            set = new TreeSet<CmsUser>((Collection<? extends CmsUser>) new PriorityComparator());
            setUsers(set);
        }
        set.add(user);
        user.addToChannels(this);
    }
    /**
     * 获得栏目终审级别
     *
     * @return
     */
    @Transient
    public Byte getFinalStepExtends() {
        Byte step = getFinalStep();
        if (step == null) {
            Channel parent = getParent();
            if (parent == null) {
                return getSite().getFinalStep();
            } else {
                return parent.getFinalStepExtends();
            }
        } else {
            return step;
        }
    }
    @Transient
    public Byte getFinalStep() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getFinalStep();
        } else {
            return null;
        }
    }
    /**
     * 获得审核后修改方式的枚举值。如果该值为null则取父级栏目，父栏目为null则取站点相关设置。
     *
     * @return
     */
    @Transient
    public AfterCheckEnum getAfterCheckEnum() {
        Byte after = getChannelExt().getAfterCheck();
        Channel channel = getParent();
        // 如果为null，则查找父栏目。
        while (after == null && channel != null) {
            after = channel.getAfterCheck();
            channel = channel.getParent();
        }
        // 如果依然为null，则查找站点设置
        if (after == null) {
            after = getSite().getAfterCheck();
        }
        if (after == 1) {
            return AfterCheckEnum.CANNOT_UPDATE;
        } else if (after == 2) {
            return AfterCheckEnum.BACK_UPDATE;
        } else if (after == 3) {
            return AfterCheckEnum.KEEP_UPDATE;
        } else {
            // 默认为不可改、不可删
            return AfterCheckEnum.CANNOT_UPDATE;
        }
    }
    @Transient
    public Byte getAfterCheck() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getAfterCheck();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getStaticContent() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getIsStaticContent();
        } else {
            return null;
        }
    }
    /**
     * 获得节点列表ID。从父节点到自身。
     *
     * @return
     */
    @Transient
    public Integer[] getNodeIds() {
        List<Channel> channels = getNodeList();
        Integer[] ids = new Integer[channels.size()];
        int i = 0;
        for (Channel c : channels) {
            ids[i++] = c.getChannelId();
        }
        return ids;
    }
    /**
     * 获得节点列表。从父节点到自身。
     *
     * @return
     */
    @Transient
    public List<Channel> getNodeList() {
        LinkedList<Channel> list = new LinkedList<Channel>();
        Channel node = this;
        while (node != null) {
            list.addFirst(node);
            node = node.getParent();
        }
        return list;
    }
    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
        if (getIsDisplay() == null) {
            setIsDisplay(true);
        }
    }
    public void addToViewGroups(CmsGroup group) {
        Set<CmsGroup> groups = getViewGroups();
        if (groups == null) {
            groups = new TreeSet<CmsGroup>(new PriorityComparator());
            setViewGroups(groups);
        }
        groups.add(group);
        group.getViewChannels().add(this);
    }
    public void addToContriGroups(CmsGroup group) {
        Set<CmsGroup> groups = getContriGroups();
        if (groups == null) {
            groups = new TreeSet<CmsGroup>(new PriorityComparator());
            setContriGroups(groups);
        }
        groups.add(group);
        group.getContriChannels().add(this);
    }
    public void addToChannelModels(Channel channel,CmsModel model, String tpl, String mtpl) {
        List<ChannelModel> list = getChannelModels();
        if (list == null) {
            list = new ArrayList<ChannelModel>();
            setChannelModels(list);
        }
        ChannelModel cm = new ChannelModel();
        cm.setTplContent(tpl);
        cm.setTplMobileContent(mtpl);
        cm.setModel(model);
        list.add(cm);
    }
    @Transient
    public String getContentRule() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getContentRule();
        } else {
            return null;
        }
    }
    @Transient
    public String getLink() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getLink();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getStaticChannel() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getIsStaticChannel();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getListChild() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getIsListChild();
        } else {
            return null;
        }
    }
    @Transient
    public Integer getPageSize() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getPageSize();
        } else {
            return null;
        }
    }
    public String getStaticFilename(int pageNo) {
        CmsSite site = getSite();
        StringBuilder url = new StringBuilder();
        String staticDir = site.getStaticDir();
        if (!StringUtils.isBlank(staticDir)) {
            url.append(staticDir);
        }
        String filename = getStaticFilenameByRule();
        if (!StringUtils.isBlank(filename)) {
            int index = filename.indexOf(".", filename.lastIndexOf("/"));
            if (pageNo > 1) {
                if (index != -1) {
                    url.append(filename.substring(0, index)).append("_")
                            .append(pageNo).append(filename.substring(index));
                } else {
                    url.append(filename).append("_").append(pageNo);
                }
            } else {
                url.append(filename);
            }
        } else {
            // 默认静态页面访问路径
            url.append(SPT).append(getChannelPath());
            String suffix = site.getStaticSuffix();
            if (getHasContent()) {
                url.append(SPT).append(INDEX);
                if (pageNo > 1) {
                    url.append("_").append(pageNo);
                }
                url.append(suffix);
            } else {
                if (pageNo > 1) {
                    url.append("_").append(pageNo);
                }
                url.append(suffix);
            }
        }
        return url.toString();
    }
    @Transient
    public String getStaticFilenameByRule() {
        String rule = getChannelRule();
        if (StringUtils.isBlank(rule)) {
            return null;
        }
        CmsModel model = getModel();
        String url = StaticPageUtils.staticUrlRule(rule, model.getModelId(), model
                .getModelPath(), getChannelId(), getChannelPath(), null, null);
        return url;
    }
    @Transient
    public String getChannelRule() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getChannelRule();
        } else {
            return null;
        }
    }
    public String getMobileStaticFilename(int pageNo) {
        CmsSite site = getSite();
        StringBuilder url = new StringBuilder();
        String staticDir = site.getMobileStaticDir();
        if (!StringUtils.isBlank(staticDir)) {
            url.append(staticDir);
        }
        String filename = getStaticFilenameByRule();
        if (!StringUtils.isBlank(filename)) {
            int index = filename.indexOf(".", filename.lastIndexOf("/"));
            if (pageNo > 1) {
                if (index != -1) {
                    url.append(filename.substring(0, index)).append("_")
                            .append(pageNo).append(filename.substring(index));
                } else {
                    url.append(filename).append("_").append(pageNo);
                }
            } else {
                url.append(filename);
            }
        } else {
            // 默认静态页面访问路径
            url.append(SPT).append(getChannelPath());
            String suffix = site.getStaticSuffix();
            if (getHasContent()) {
                url.append(SPT).append(INDEX);
                if (pageNo > 1) {
                    url.append("_").append(pageNo);
                }
                url.append(suffix);
            } else {
                if (pageNo > 1) {
                    url.append("_").append(pageNo);
                }
                url.append(suffix);
            }
        }
        return url.toString();
    }
    public String getTplContentOrDef(CmsModel contentModel) {
        String tpl = getModelTpl(contentModel);
        if (!StringUtils.isBlank(tpl)) {
            return tpl;
        } else {
            String sol = getSite().getSolutionPath();
            return contentModel.getTplContent(sol, true);
        }
    }
    public String getModelTpl(CmsModel model){
        List<ChannelModel>list=getChannelModelsExtend();
        if(list!=null){
            for(ChannelModel cm:list){
                if(cm.getModel().equals(model)){
                    return cm.getTplContent();
                }
            }
        }
        return null;
    }
    @Transient
    public List<ChannelModel> getChannelModelsExtend() {
        List<ChannelModel>list=getChannelModels();
        //没有配置栏目模型默认父栏目配置
        if (list == null||list.size()<=0) {
            Channel parent = getParent();
            if (parent == null) {
                return null;
            } else {
                return parent.getChannelModelsExtend();
            }
        } else {
            return list;
        }
    }
    public String getMobileTplContentOrDef(CmsModel contentModel) {
        String tpl = getModelMobileTpl(contentModel);
        if (!StringUtils.isBlank(tpl)) {
            return tpl;
        } else {
            String sol = getSite().getMobileSolutionPath();
            return contentModel.getTplContent(sol, true);
        }
    }
    @Transient
    public Integer[] getUserIds() {
        Set<CmsUser> users = getUsers();
        return CmsUser.fetchIds(users);
    }
    //
    public String getModelMobileTpl(CmsModel model){
        List<ChannelModel>list=getChannelModelsExtend();
        if(list!=null){
            for(ChannelModel cm:list){
                if(cm.getModel().equals(model)){
                    return cm.getTplMobileContent();
                }
            }
        }
        return null;
    }
    @Transient
    public String getMobileTplChannelOrDef() {
        String tpl = getMobileTplChannel();
        if (!StringUtils.isBlank(tpl)) {
            return tpl;
        } else {
            String sol = getSite().getMobileSolutionPath();
            return getModel().getTplChannel(sol, true);
        }
    }
    @Transient
    public String getMobileTplChannel() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getTplMobileChannel();
        } else {
            return null;
        }
    }
    @Transient
    public String getTplChannelOrDef() {
        String tpl = getTplChannel();
        if (!StringUtils.isBlank(tpl)) {
            return tpl;
        } else {
            String sol = getSite().getSolutionPath();
            return getModel().getTplChannel(sol, true);
        }
    }
    @Transient
    public String getTplChannel() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getTplChannel();
        } else {
            return null;
        }
    }
    public String getUrlStatic(int pageNo) {
        return getUrlStatic(null, pageNo);
    }
    public String getUrlStatic(Boolean whole, int pageNo) {
        if (!StringUtils.isBlank(getLink())) {
            return getLink();
        }
        CmsSite site = getSite();
        StringBuilder url = site.getUrlBuffer(false, whole, false);
        String filename = getStaticFilenameByRule();
        if (!StringUtils.isBlank(filename)) {
            if (pageNo > 1) {
                int index = filename.indexOf(".", filename.lastIndexOf("/"));
                if (index != -1) {
                    url.append(filename.substring(0, index));
                    url.append("_").append(pageNo);
                    url.append(filename.substring(index));
                } else {
                    url.append("_").append(pageNo);
                }
            } else {
                if (getAccessByDir()) {
                    url.append(filename.substring(0,
                            filename.lastIndexOf("/") + 1));
                } else {
                    url.append(filename);
                }
            }
        } else {
            // 默认静态页面访问路径
            url.append(SPT).append(getChannelPath());
            if (pageNo > 1) {
                url.append("_").append(pageNo);
                url.append(site.getStaticSuffix());
            } else {
                if (getHasContent()) {
                    url.append(SPT);
                } else {
                    url.append(site.getStaticSuffix());
                }
            }
        }
        return url.toString();
    }
    @Transient
    public Boolean getAccessByDir() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getIsAccessByDir();
        } else {
            return null;
        }
    }
    /**
     * 获得列表用于下拉选择。条件：有内容的栏目。
     *
     * @param topList
     *            顶级栏目
     * @return
     */
    public static List<Channel> getListForSelect(List<Channel> topList,
                                                 Set<Channel> rights, boolean hasContentOnly) {
        return getListForSelect(topList, rights, null, hasContentOnly);
    }
    public static List<Channel> getListForSelect(List<Channel> topList,
                                                 Set<Channel> rights, Channel exclude, boolean hasContentOnly) {
        List<Channel> list = new ArrayList<Channel>();
        for (Channel c : topList) {
            addChildToList(list, c, rights, exclude, hasContentOnly);
        }
        return list;
    }

    /**
     * 获得列表用于下拉选择。条件：有内容的栏目。
     *
     * @return
     */
    public List<Channel> getListForSelect(Set<Channel> rights,
                                          boolean hasContentOnly) {
        return getListForSelect(rights, null, hasContentOnly);
    }

    public List<Channel> getListForSelect(Set<Channel> rights, Channel exclude,
                                          boolean hasContentOnly) {
        List<Channel> list = new ArrayList<Channel>((getRgt() - getLft()) / 2);
        addChildToList(list, this, rights, exclude, hasContentOnly);
        return list;
    }

    /**
     * 获取栏目下总浏览量
     * @return
     */
    @Transient
    public int getViewTotal() {
        Integer totalView=0;
        List<Channel> list = new ArrayList<Channel>();
        addChildToList(list, this, true);
        for(Channel c:list){
            totalView+=c.getChannelCount().getViews();
        }
        return totalView;
    }
    @Transient
    public int getViewsDayTotal() {
        Integer totalView=0;
        List<Channel> list = new ArrayList<Channel>();
        addChildToList(list, this, true);
        for(Channel c:list){
            totalView+=c.getChannelCount().getViewsDay();
        }
        return totalView;
    }
    @Transient
    public int getViewsMonthTotal() {
        Integer totalView=0;
        List<Channel> list = new ArrayList<Channel>();
        addChildToList(list, this, true);
        for(Channel c:list){
            totalView+=c.getChannelCount().getViewsMonth();
        }
        return totalView;
    }
    @Transient
    public int getViewsWeekTotal() {
        Integer totalView=0;
        List<Channel> list = new ArrayList<Channel>();
        addChildToList(list, this, true);
        for(Channel c:list){
            totalView+=c.getChannelCount().getViewsWeek();
        }
        return totalView;
    }

    private static void addChildToList(List<Channel> list, Channel channel, boolean hasContentOnly) {
        list.add(channel);
        Set<Channel> child = channel.getChild();
        for (Channel c : child) {
            if (hasContentOnly) {
                if (c.getHasContent()) {
                    addChildToList(list, c,  hasContentOnly);
                }
            } else {
                addChildToList(list, c, hasContentOnly);
            }
        }
    }

    /**
     * 递归将子栏目加入列表。条件：有内容的栏目。
     *
     * @param list
     *            栏目容器
     * @param channel
     *            待添加的栏目，且递归添加子栏目
     * @param rights
     *            有权限的栏目，为null不控制权限。
     */
    private static void addChildToList(List<Channel> list, Channel channel,
                                       Set<Channel> rights, Channel exclude, boolean hasContentOnly) {
        if ((rights != null && !rights.contains(channel))
                || (exclude != null && exclude.equals(channel))) {
            return;
        }
        list.add(channel);
        Set<Channel> child = channel.getChild();
        for (Channel c : child) {
            if (hasContentOnly) {
                if (c.getHasContent()) {
                    addChildToList(list, c, rights, exclude, hasContentOnly);
                }
            } else {
                addChildToList(list, c, rights, exclude, hasContentOnly);
            }
        }
    }
    @Transient
    public List<CmsModel> getModels(){
        List<ChannelModel>list=getChannelModelsExtend();
        if (list == null) {
            return null;
        }
        List<CmsModel>models=new ArrayList<CmsModel>();
        for(ChannelModel cm:list){
            models.add(cm.getModel());
        }
        return models;
    }
    @Transient
    public List<CmsModel>getModels(List<CmsModel>allModels){
        List<ChannelModel>list=getChannelModelsExtend();
        //顶层栏目没有配置默认所有可用模型
        if (list == null) {
            return allModels;
        }
        List<CmsModel>models=new ArrayList<CmsModel>();
        for(ChannelModel cm:list){
            models.add(cm.getModel());
        }
        return models;
    }
    @Transient
    public List<String>getModelIds(){
        List<String>ids=new ArrayList<String>();
        List<CmsModel>models=getModels();
        if(models!=null){
            for(CmsModel model:models){
                ids.add(model.getModelId()+"");
            }
        }
        return ids;
    }
    @Transient
    public String getName() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getChannelName();
        } else {
            return null;
        }
    }

    public static Integer[] fetchIds(Collection<Channel> channels) {
        if (channels == null) {
            return null;
        }
        Integer[] ids = new Integer[channels.size()];
        int i = 0;
        for (Channel c : channels) {
            ids[i++] = c.getChannelId();
        }
        return ids;
    }

    @Transient
    public String getUrlDynamic(Boolean whole) {
        if (!StringUtils.isBlank(getLink())) {
            return getLink();
        }
        CmsSite site = getSite();
        StringBuilder url = site.getUrlBuffer(true, whole, false);
        url.append(SPT).append(getChannelPath());
        if (getHasContent()) {
            url.append(SPT).append(INDEX);
        }
        url.append(site.getDynamicSuffix());
        return url.toString();
    }

    /**
     * 获得URL地址
     *
     * @return
     */
    @Transient
    public String getUrl() {
        if (!StringUtils.isBlank(getLink())) {
            return getLink();
        }
        if (getStaticChannel()) {
            return getUrlStatic(false, 1);
        } else if(!StringUtils.isBlank(getSite().getDomainAlias())){
            return getUrlDynamic(null);
        }else {
            return getUrlDynamic(true);
        }
    }

    /**
     * 获得深度
     *
     * @return 第一层为0，第二层为1，以此类推。
     */
    @Transient
    public int getDeep() {
        int deep = 0;
        Channel parent = getParent();
        while (parent != null) {
            deep++;
            parent = parent.getParent();
        }
        return deep;
    }

    @Transient
    public Integer getTitleImgWidth() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getTitleImgWidth();
        } else {
            return null;
        }
    }
    @Transient
    public Integer getTitleImgHeight() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getTitleImgHeight();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getHasContentImg() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getHasContentImg();
        } else {
            return null;
        }
    }
    @Transient
    public Integer getContentImgWidth() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getContentImgWidth();
        } else {
            return null;
        }
    }
    @Transient
    public Integer getContentImgHeight() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getContentImgHeight();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getHasTitleImg() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getHasTitleImg();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getBlank() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getIsBlank();
        } else {
            return null;
        }
    }
    @Transient
    public List<String>getModelTpls(){
        List<ChannelModel>list=getChannelModelsExtend();
        List<String>tpls=new ArrayList<String>();
        // 当前模板，去除基本路径
        int tplPathLength = getSite().getTplPath().length();
        if(list!=null){
            for(ChannelModel cm:list){
                String tpl=cm.getTplContent();
                if(StringUtils.isNotBlank(tpl)){
                    tpls.add(tpl.substring(tplPathLength));
                }
            }
        }
        return tpls;
    }
    @Transient
    public List<String>getMobileModelTpls(){
        List<ChannelModel>list=getChannelModelsExtend();
        List<String>tpls=new ArrayList<String>();
        // 当前模板，去除基本路径
        int tplPathLength = getSite().getTplPath().length();
        if(list!=null){
            for(ChannelModel cm:list){
                String tpl=cm.getTplMobileContent();
                if(StringUtils.isNotBlank(tpl)){
                    tpls.add(tpl.substring(tplPathLength));
                }
            }
        }
        return tpls;
    }

    /**
     * 获得栏目内容
     *
     * @return
     */
    @Transient
    public String getTxt() {
        ChannelTxt txt = getChannelTxt();
        if (txt != null) {
            return txt.getTxt();
        } else {
            return null;
        }
    }
    @Transient
    public ChannelTxt getChannelTxt() {
        Set<ChannelTxt> set = getChannelTxtSet();
        if (set != null && set.size() > 0) {
            return set.iterator().next();
        } else {
            return null;
        }
    }

    @Transient
    public Integer getSiteId(){
        return  site.getSiteId();
    }
    @Transient
    public Integer getId() {
        return this.channelId;
    }

    /**
     * @see HibernateTree#getLftName()
     */
    @Transient
    public String getLftName() {
        return DEF_LEFT_NAME;
    }

    /**
     * @see HibernateTree#getParentId()
     */
    @Transient
    public Integer getParentId() {
        Channel parent = getParent();
        if (parent != null) {
            return parent.getId();
        } else {
            return null;
        }
    }
    /**
     * @see HibernateTree#getParentName()
     */
    @Transient
    public String getParentName() {
        return DEF_PARENT_NAME;
    }

    /**
     * @see HibernateTree#getRgtName()
     */
    @Transient
    public String getRgtName() {
        return DEF_RIGHT_NAME;
    }
    /**
     * 每个站点各自维护独立的树结构
     *
     * @see HibernateTree#getTreeCondition()
     */
    @Transient
    public String getTreeCondition() {
        return "bean.site.id=" + getSite().getSiteId();
    }
    @Transient
    public Integer getCommentControl() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getCommentControl();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getAllowUpdown() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getAllowUpdown();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getAllowShare() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getAllowShare();
        } else {
            return null;
        }
    }
    @Transient
    public Boolean getAllowScore() {
        ChannelExt ext = getChannelExt();
        if (ext != null) {
            return ext.getAllowScore();
        } else {
            return null;
        }
    }
}
