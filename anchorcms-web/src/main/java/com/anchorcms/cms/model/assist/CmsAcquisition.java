package com.anchorcms.cms.model.assist;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.ContentType;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-8
 * @Desc CMS采集表
 */
@Entity
@Table(name = "c_acquisition")
public class CmsAcquisition implements Serializable{
    private static final long serialVersionUID = -8727601992975290258L;
    private int acquisitionId;
    private int siteId;
    private int channelId;
    private int typeId;
    private int userId;
    private String acqName;
    private Serializable startTime;
    private Serializable endTime;
    private Integer status;
    private Integer currNum;
    private Integer currItem;
    private int totalItem;
    private int pauseTime;
    private String pageEncoding;
    private String planList;
    private String dynamicAddr;
    private Integer dynamicStart;
    private Integer dynamicEnd;
    private String linksetStart;
    private String linksetEnd;
    private String linkStart;
    private String linkEnd;
    private String titleStart;
    private String titleEnd;
    private String keywordsStart;
    private String keywordsEnd;
    private String descriptionStart;
    private String descriptionEnd;
    private String contentStart;
    private String contentEnd;
    private String paginationStart;
    private String paginationEnd;
    private int queue;
    private String repeatCheckType;
    private Boolean imgAcqu;
    private String contentPrefix;
    private String imgPrefix;
    private String viewStart;
    private String viewEnd;
    private String viewIdStart;
    private String viewIdEnd;
    private String viewLink;
    private String releaseTimeStart;
    private String releaseTimeEnd;
    private String authorStart;
    private String authorEnd;
    private String originStart;
    private String originEnd;
    private String releaseTimeFormat;
    private String originAppoint;

    @Id
    @Column(name = "acquisition_id")
    public int getAcquisitionId() {
        return acquisitionId;
    }

    public void setAcquisitionId(int acquisitionId) {
        this.acquisitionId = acquisitionId;
    }

    @Basic
    @Column(name = "site_id")
    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    @Basic
    @Column(name = "channel_id")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Basic
    @Column(name = "type_id")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "acq_name")
    public String getAcqName() {
        return acqName;
    }

    public void setAcqName(String acqName) {
        this.acqName = acqName;
    }

    @Basic
    @Column(name = "start_time")
    public Serializable getStartTime() {
        return startTime;
    }

    public void setStartTime(Serializable startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Serializable getEndTime() {
        return endTime;
    }

    public void setEndTime(Serializable endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "curr_num")
    public Integer getCurrNum() {
        return currNum;
    }

    public void setCurrNum(Integer currNum) {
        this.currNum = currNum;
    }

    @Basic
    @Column(name = "curr_item")
    public Integer getCurrItem() {
        return currItem;
    }

    public void setCurrItem(Integer currItem) {
        this.currItem = currItem;
    }

    @Basic
    @Column(name = "total_item")
    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    @Basic
    @Column(name = "pause_time")
    public Integer getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(Integer pauseTime) {
        this.pauseTime = pauseTime;
    }

    @Basic
    @Column(name = "page_encoding")
    public String getPageEncoding() {
        return pageEncoding;
    }

    public void setPageEncoding(String pageEncoding) {
        this.pageEncoding = pageEncoding;
    }

    @Basic
    @Column(name = "plan_list")
    public String getPlanList() {
        return planList;
    }

    public void setPlanList(String planList) {
        this.planList = planList;
    }

    @Basic
    @Column(name = "dynamic_addr")
    public String getDynamicAddr() {
        return dynamicAddr;
    }

    public void setDynamicAddr(String dynamicAddr) {
        this.dynamicAddr = dynamicAddr;
    }

    @Basic
    @Column(name = "dynamic_start")
    public Integer getDynamicStart() {
        return dynamicStart;
    }

    public void setDynamicStart(Integer dynamicStart) {
        this.dynamicStart = dynamicStart;
    }

    @Basic
    @Column(name = "dynamic_end")
    public Integer getDynamicEnd() {
        return dynamicEnd;
    }

    public void setDynamicEnd(Integer dynamicEnd) {
        this.dynamicEnd = dynamicEnd;
    }

    @Basic
    @Column(name = "linkset_start")
    public String getLinksetStart() {
        return linksetStart;
    }

    public void setLinksetStart(String linksetStart) {
        this.linksetStart = linksetStart;
    }

    @Basic
    @Column(name = "linkset_end")
    public String getLinksetEnd() {
        return linksetEnd;
    }

    public void setLinksetEnd(String linksetEnd) {
        this.linksetEnd = linksetEnd;
    }

    @Basic
    @Column(name = "link_start")
    public String getLinkStart() {
        return linkStart;
    }

    public void setLinkStart(String linkStart) {
        this.linkStart = linkStart;
    }

    @Basic
    @Column(name = "link_end")
    public String getLinkEnd() {
        return linkEnd;
    }

    public void setLinkEnd(String linkEnd) {
        this.linkEnd = linkEnd;
    }

    @Basic
    @Column(name = "title_start")
    public String getTitleStart() {
        return titleStart;
    }

    public void setTitleStart(String titleStart) {
        this.titleStart = titleStart;
    }

    @Basic
    @Column(name = "title_end")
    public String getTitleEnd() {
        return titleEnd;
    }

    public void setTitleEnd(String titleEnd) {
        this.titleEnd = titleEnd;
    }

    @Basic
    @Column(name = "keywords_start")
    public String getKeywordsStart() {
        return keywordsStart;
    }

    public void setKeywordsStart(String keywordsStart) {
        this.keywordsStart = keywordsStart;
    }

    @Basic
    @Column(name = "keywords_end")
    public String getKeywordsEnd() {
        return keywordsEnd;
    }

    public void setKeywordsEnd(String keywordsEnd) {
        this.keywordsEnd = keywordsEnd;
    }

    @Basic
    @Column(name = "description_start")
    public String getDescriptionStart() {
        return descriptionStart;
    }

    public void setDescriptionStart(String descriptionStart) {
        this.descriptionStart = descriptionStart;
    }

    @Basic
    @Column(name = "description_end")
    public String getDescriptionEnd() {
        return descriptionEnd;
    }

    public void setDescriptionEnd(String descriptionEnd) {
        this.descriptionEnd = descriptionEnd;
    }

    @Basic
    @Column(name = "content_start")
    public String getContentStart() {
        return contentStart;
    }

    public void setContentStart(String contentStart) {
        this.contentStart = contentStart;
    }

    @Basic
    @Column(name = "content_end")
    public String getContentEnd() {
        return contentEnd;
    }

    public void setContentEnd(String contentEnd) {
        this.contentEnd = contentEnd;
    }

    @Basic
    @Column(name = "pagination_start")
    public String getPaginationStart() {
        return paginationStart;
    }

    public void setPaginationStart(String paginationStart) {
        this.paginationStart = paginationStart;
    }

    @Basic
    @Column(name = "pagination_end")
    public String getPaginationEnd() {
        return paginationEnd;
    }

    public void setPaginationEnd(String paginationEnd) {
        this.paginationEnd = paginationEnd;
    }

    @Basic
    @Column(name = "queue")
    public Integer getQueue() {
        return queue;
    }

    public void setQueue(Integer queue) {
        this.queue = queue;
    }

    @Basic
    @Column(name = "repeat_check_type")
    public String getRepeatCheckType() {
        return repeatCheckType;
    }

    public void setRepeatCheckType(String repeatCheckType) {
        this.repeatCheckType = repeatCheckType;
    }

    @Basic
    @Column(name = "img_acqu")
    public Boolean getImgAcqu() {
        return imgAcqu;
    }

    public void setImgAcqu(Boolean imgAcqu) {
        this.imgAcqu = imgAcqu;
    }

    @Basic
    @Column(name = "content_prefix")
    public String getContentPrefix() {
        return contentPrefix;
    }

    public void setContentPrefix(String contentPrefix) {
        this.contentPrefix = contentPrefix;
    }

    @Basic
    @Column(name = "img_prefix")
    public String getImgPrefix() {
        return imgPrefix;
    }

    public void setImgPrefix(String imgPrefix) {
        this.imgPrefix = imgPrefix;
    }

    @Basic
    @Column(name = "view_start")
    public String getViewStart() {
        return viewStart;
    }

    public void setViewStart(String viewStart) {
        this.viewStart = viewStart;
    }

    @Basic
    @Column(name = "view_end")
    public String getViewEnd() {
        return viewEnd;
    }

    public void setViewEnd(String viewEnd) {
        this.viewEnd = viewEnd;
    }

    @Basic
    @Column(name = "view_id_start")
    public String getViewIdStart() {
        return viewIdStart;
    }

    public void setViewIdStart(String viewIdStart) {
        this.viewIdStart = viewIdStart;
    }

    @Basic
    @Column(name = "view_id_end")
    public String getViewIdEnd() {
        return viewIdEnd;
    }

    public void setViewIdEnd(String viewIdEnd) {
        this.viewIdEnd = viewIdEnd;
    }

    @Basic
    @Column(name = "view_link")
    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    @Basic
    @Column(name = "releaseTime_start")
    public String getReleaseTimeStart() {
        return releaseTimeStart;
    }

    public void setReleaseTimeStart(String releaseTimeStart) {
        this.releaseTimeStart = releaseTimeStart;
    }

    @Basic
    @Column(name = "releaseTime_end")
    public String getReleaseTimeEnd() {
        return releaseTimeEnd;
    }

    public void setReleaseTimeEnd(String releaseTimeEnd) {
        this.releaseTimeEnd = releaseTimeEnd;
    }

    @Basic
    @Column(name = "author_start")
    public String getAuthorStart() {
        return authorStart;
    }

    public void setAuthorStart(String authorStart) {
        this.authorStart = authorStart;
    }

    @Basic
    @Column(name = "author_end")
    public String getAuthorEnd() {
        return authorEnd;
    }

    public void setAuthorEnd(String authorEnd) {
        this.authorEnd = authorEnd;
    }

    @Basic
    @Column(name = "origin_start")
    public String getOriginStart() {
        return originStart;
    }

    public void setOriginStart(String originStart) {
        this.originStart = originStart;
    }

    @Basic
    @Column(name = "origin_end")
    public String getOriginEnd() {
        return originEnd;
    }

    public void setOriginEnd(String originEnd) {
        this.originEnd = originEnd;
    }

    @Basic
    @Column(name = "releaseTime_format")
    public String getReleaseTimeFormat() {
        return releaseTimeFormat;
    }

    public void setReleaseTimeFormat(String releaseTimeFormat) {
        this.releaseTimeFormat = releaseTimeFormat;
    }

    @Basic
    @Column(name = "origin_appoint")
    public String getOriginAppoint() {
        return originAppoint;
    }

    public void setOriginAppoint(String originAppoint) {
        this.originAppoint = originAppoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsAcquisition that = (CmsAcquisition) o;

        if (acquisitionId != that.acquisitionId) return false;
        if (siteId != that.siteId) return false;
        if (channelId != that.channelId) return false;
        if (typeId != that.typeId) return false;
        if (userId != that.userId) return false;
        if (status != that.status) return false;
        if (currNum != that.currNum) return false;
        if (currItem != that.currItem) return false;
        if (totalItem != that.totalItem) return false;
        if (pauseTime != that.pauseTime) return false;
        if (queue != that.queue) return false;
        if (imgAcqu != that.imgAcqu) return false;
        if (acqName != null ? !acqName.equals(that.acqName) : that.acqName != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (pageEncoding != null ? !pageEncoding.equals(that.pageEncoding) : that.pageEncoding != null) return false;
        if (planList != null ? !planList.equals(that.planList) : that.planList != null) return false;
        if (dynamicAddr != null ? !dynamicAddr.equals(that.dynamicAddr) : that.dynamicAddr != null) return false;
        if (dynamicStart != null ? !dynamicStart.equals(that.dynamicStart) : that.dynamicStart != null) return false;
        if (dynamicEnd != null ? !dynamicEnd.equals(that.dynamicEnd) : that.dynamicEnd != null) return false;
        if (linksetStart != null ? !linksetStart.equals(that.linksetStart) : that.linksetStart != null) return false;
        if (linksetEnd != null ? !linksetEnd.equals(that.linksetEnd) : that.linksetEnd != null) return false;
        if (linkStart != null ? !linkStart.equals(that.linkStart) : that.linkStart != null) return false;
        if (linkEnd != null ? !linkEnd.equals(that.linkEnd) : that.linkEnd != null) return false;
        if (titleStart != null ? !titleStart.equals(that.titleStart) : that.titleStart != null) return false;
        if (titleEnd != null ? !titleEnd.equals(that.titleEnd) : that.titleEnd != null) return false;
        if (keywordsStart != null ? !keywordsStart.equals(that.keywordsStart) : that.keywordsStart != null)
            return false;
        if (keywordsEnd != null ? !keywordsEnd.equals(that.keywordsEnd) : that.keywordsEnd != null) return false;
        if (descriptionStart != null ? !descriptionStart.equals(that.descriptionStart) : that.descriptionStart != null)
            return false;
        if (descriptionEnd != null ? !descriptionEnd.equals(that.descriptionEnd) : that.descriptionEnd != null)
            return false;
        if (contentStart != null ? !contentStart.equals(that.contentStart) : that.contentStart != null) return false;
        if (contentEnd != null ? !contentEnd.equals(that.contentEnd) : that.contentEnd != null) return false;
        if (paginationStart != null ? !paginationStart.equals(that.paginationStart) : that.paginationStart != null)
            return false;
        if (paginationEnd != null ? !paginationEnd.equals(that.paginationEnd) : that.paginationEnd != null)
            return false;
        if (repeatCheckType != null ? !repeatCheckType.equals(that.repeatCheckType) : that.repeatCheckType != null)
            return false;
        if (contentPrefix != null ? !contentPrefix.equals(that.contentPrefix) : that.contentPrefix != null)
            return false;
        if (imgPrefix != null ? !imgPrefix.equals(that.imgPrefix) : that.imgPrefix != null) return false;
        if (viewStart != null ? !viewStart.equals(that.viewStart) : that.viewStart != null) return false;
        if (viewEnd != null ? !viewEnd.equals(that.viewEnd) : that.viewEnd != null) return false;
        if (viewIdStart != null ? !viewIdStart.equals(that.viewIdStart) : that.viewIdStart != null) return false;
        if (viewIdEnd != null ? !viewIdEnd.equals(that.viewIdEnd) : that.viewIdEnd != null) return false;
        if (viewLink != null ? !viewLink.equals(that.viewLink) : that.viewLink != null) return false;
        if (releaseTimeStart != null ? !releaseTimeStart.equals(that.releaseTimeStart) : that.releaseTimeStart != null)
            return false;
        if (releaseTimeEnd != null ? !releaseTimeEnd.equals(that.releaseTimeEnd) : that.releaseTimeEnd != null)
            return false;
        if (authorStart != null ? !authorStart.equals(that.authorStart) : that.authorStart != null) return false;
        if (authorEnd != null ? !authorEnd.equals(that.authorEnd) : that.authorEnd != null) return false;
        if (originStart != null ? !originStart.equals(that.originStart) : that.originStart != null) return false;
        if (originEnd != null ? !originEnd.equals(that.originEnd) : that.originEnd != null) return false;
        if (releaseTimeFormat != null ? !releaseTimeFormat.equals(that.releaseTimeFormat) : that.releaseTimeFormat != null)
            return false;
        if (originAppoint != null ? !originAppoint.equals(that.originAppoint) : that.originAppoint != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = acquisitionId;
        result = 31 * result + siteId;
        result = 31 * result + channelId;
        result = 31 * result + typeId;
        result = 31 * result + userId;
        result = 31 * result + (acqName != null ? acqName.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + currNum;
        result = 31 * result + currItem;
        result = 31 * result + totalItem;
        result = 31 * result + pauseTime;
        result = 31 * result + (pageEncoding != null ? pageEncoding.hashCode() : 0);
        result = 31 * result + (planList != null ? planList.hashCode() : 0);
        result = 31 * result + (dynamicAddr != null ? dynamicAddr.hashCode() : 0);
        result = 31 * result + (dynamicStart != null ? dynamicStart.hashCode() : 0);
        result = 31 * result + (dynamicEnd != null ? dynamicEnd.hashCode() : 0);
        result = 31 * result + (linksetStart != null ? linksetStart.hashCode() : 0);
        result = 31 * result + (linksetEnd != null ? linksetEnd.hashCode() : 0);
        result = 31 * result + (linkStart != null ? linkStart.hashCode() : 0);
        result = 31 * result + (linkEnd != null ? linkEnd.hashCode() : 0);
        result = 31 * result + (titleStart != null ? titleStart.hashCode() : 0);
        result = 31 * result + (titleEnd != null ? titleEnd.hashCode() : 0);
        result = 31 * result + (keywordsStart != null ? keywordsStart.hashCode() : 0);
        result = 31 * result + (keywordsEnd != null ? keywordsEnd.hashCode() : 0);
        result = 31 * result + (descriptionStart != null ? descriptionStart.hashCode() : 0);
        result = 31 * result + (descriptionEnd != null ? descriptionEnd.hashCode() : 0);
        result = 31 * result + (contentStart != null ? contentStart.hashCode() : 0);
        result = 31 * result + (contentEnd != null ? contentEnd.hashCode() : 0);
        result = 31 * result + (paginationStart != null ? paginationStart.hashCode() : 0);
        result = 31 * result + (paginationEnd != null ? paginationEnd.hashCode() : 0);
        result = 31 * result + queue;
        result = 31 * result + (repeatCheckType != null ? repeatCheckType.hashCode() : 0);
        result = 31 * result + (imgAcqu != null ? imgAcqu.hashCode() : 0);
        result = 31 * result + (contentPrefix != null ? contentPrefix.hashCode() : 0);
        result = 31 * result + (imgPrefix != null ? imgPrefix.hashCode() : 0);
        result = 31 * result + (viewStart != null ? viewStart.hashCode() : 0);
        result = 31 * result + (viewEnd != null ? viewEnd.hashCode() : 0);
        result = 31 * result + (viewIdStart != null ? viewIdStart.hashCode() : 0);
        result = 31 * result + (viewIdEnd != null ? viewIdEnd.hashCode() : 0);
        result = 31 * result + (viewLink != null ? viewLink.hashCode() : 0);
        result = 31 * result + (releaseTimeStart != null ? releaseTimeStart.hashCode() : 0);
        result = 31 * result + (releaseTimeEnd != null ? releaseTimeEnd.hashCode() : 0);
        result = 31 * result + (authorStart != null ? authorStart.hashCode() : 0);
        result = 31 * result + (authorEnd != null ? authorEnd.hashCode() : 0);
        result = 31 * result + (originStart != null ? originStart.hashCode() : 0);
        result = 31 * result + (originEnd != null ? originEnd.hashCode() : 0);
        result = 31 * result + (releaseTimeFormat != null ? releaseTimeFormat.hashCode() : 0);
        result = 31 * result + (originAppoint != null ? originAppoint.hashCode() : 0);
        return result;
    }

    private Channel channel;

    private CmsUser user;

    private ContentType type;

    private CmsSite site;

    @ManyToOne
    @JoinColumn(name = "user_Id",insertable = false,updatable = false)
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
    @ManyToOne
    @JoinColumn(name = "type_id",insertable = false,updatable = false)
    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }
    @ManyToOne
    @JoinColumn(name = "site_id",insertable = false,updatable = false)
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
    @ManyToOne
    @JoinColumn(name = "channel_id",insertable = false,updatable = false)
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * 动态页翻页页号
     */
    public static final String PAGE = "[page]";
    /**
     * 停止状态
     */
    public static final int STOP = 0;
    /**
     * 采集状态
     */
    public static final int START = 1;
    /**
     * 暂停状态
     */
    public static final int PAUSE = 2;

    public static enum AcquisitionResultType {
        SUCCESS, TITLESTARTNOTFOUND, TITLEENDNOTFOUND, CONTENTSTARTNOTFOUND, CONTENTENDNOTFOUND,VIEWSTARTNOTFOUND,VIEWENDNOTFOUND,AUTHORSTARTNOTFOUND,AUTHORENDNOTFOUND,ORIGINSTARTNOTFOUND,ORIGINENDNOTFOUND,DESCRISTARTNOTFOUND,DESCRIENDNOTFOUND, RELEASESTARTNOTFOUND,RELEASEENDNOTFOUND,VIEWIDSTARTNOTFOUND,VIEWIDENDNOTFOUND,TITLEEXIST, URLEXIST, UNKNOW
    }

    public static enum AcquisitionRepeatCheckType{
        NONE, TITLE, URL
    }
    /**
     * 是否暂停
     *
     * @return
     */
    @Transient
    public boolean isPuase() {
        int status = getStatus();
        return status == 2;
    }
    /**
     * 是否停止
     *
     * @return
     */
    @Transient
    public boolean isStop() {
        int status = getStatus();
        return status == 0;
    }
    public void init() {
        if (getStatus() == null) {
            setStatus(STOP);
        }
        if (getCurrNum() == null) {
            setCurrNum(0);
        }
        if (getCurrItem() == null) {
            setCurrItem(0);
        }
        if (getTotalItem() == null) {
            setTotalItem(0);
        }
        if (getPauseTime() == null) {
            setPauseTime(0);
        }
        if(getQueue()==null){
            setQueue(0);
        }
    }

    @Transient
    public String[] getAllPlans() {
        String[] plans = getPlans();
        Integer start = getDynamicStart();
        Integer end = getDynamicEnd();
        if (!StringUtils.isBlank(getDynamicAddr()) && start != null
                && end != null && start >= 0 && end >= start) {
            int plansLen = plans.length;
            String[] allPlans = new String[plansLen + end - start + 1];
            for (int i = 0; i < plansLen; i++) {
                allPlans[i] = plans[i];
            }
            for (int i = 0, len = end - start + 1; i < len; i++) {
                allPlans[plansLen + i] = getDynamicAddrByNum(start + i);
            }
            return allPlans;
        } else {
            return plans;
        }
    }

    @Transient
    public String[] getPlans() {
        String plan = getPlanList();
        if (!StringUtils.isBlank(plan)) {
            return StringUtils.split(plan);
        } else {
            return new String[0];
        }
    }

    @Transient
    public String getDynamicAddrByNum(int num) {
        return StringUtils.replace(getDynamicAddr(), PAGE, String.valueOf(num));
    }
}
