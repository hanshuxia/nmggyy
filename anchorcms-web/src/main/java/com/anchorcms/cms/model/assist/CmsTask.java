package com.anchorcms.cms.model.assist;

import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by hasee on 2016/12/12.
 */
@Entity
@Table(name = "c_task")
public class CmsTask {

    /**
     * 任务执行周期cron表达式
     */

    CmsSite site;
    CmsUser user;
    public static int EXECYCLE_CRON=2;
    /**
     * 任务执行周期自定义
     */
    public static int EXECYCLE_DEFINE=1;
    /**
     * 执行周期-分钟
     */
    public static int EXECYCLE_MINUTE=1;
    /**
     * 执行周期-小时
     */
    public static int EXECYCLE_HOUR=2;
    /**
     * 执行周期-日
     */
    public static int EXECYCLE_DAY=3;
    /**
     * 执行周期-月
     */
    public static int EXECYCLE_WEEK=4;
    /**
     * 执行周期-月
     */
    public static int EXECYCLE_MONTH=5;
    /**
     * 首页静态任务
     */
    public static int TASK_STATIC_INDEX=1;
    /**
     * 栏目页静态化任务
     */
    public static int TASK_STATIC_CHANNEL=2;
    /**
     * 内容页静态化任务
     */
    public static int TASK_STATIC_CONTENT=3;
    /**
     * 采集类任务
     */
    public static int TASK_ACQU=4;
    /**
     * 分发类任务
     */
    public static int TASK_DISTRIBUTE=5;
    /**
     * 采集源ID
     */
    public static String TASK_PARAM_ACQU_ID="acqu_id";
    /**
     * 分发FTP ID
     */
    public static String TASK_PARAM_FTP_ID="ftp_id";
    /**
     * 站点 ID
     */
    public static String TASK_PARAM_SITE_ID="site_id";
    /**
     * 栏目 ID
     */
    public static String TASK_PARAM_CHANNEL_ID="channel_id";
    /**
     * 分发到FTP 的文件夹参数前缀
     */
    public static String TASK_PARAM_FOLDER_PREFIX="floder_";  private int taskId;
    private String taskCode;
    private byte taskType;
    private String taskName;
    private String jobClass;
    private byte execycle;
    private Integer dayOfMonth;
    private Byte dayOfWeek;
    private Integer hour;
    private Integer minute;
    private Integer intervalHour;
    private Integer intervalMinute;
    private Byte taskIntervalUnit;
    private String cronExpression;
    private byte isEnable;
    private String taskRemark;
    private int siteId;
    private int userId;
    private Date createTime;

    @Id
    @Column(name = "task_id")
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "task_code")
    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    @Basic
    @Column(name = "task_type")
    public byte getTaskType() {
        return taskType;
    }

    public void setTaskType(byte taskType) {
        this.taskType = taskType;
    }

    @Basic
    @Column(name = "task_name")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Basic
    @Column(name = "job_class")
    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    @Basic
    @Column(name = "execycle")
    public byte getExecycle() {
        return execycle;
    }

    public void setExecycle(byte execycle) {
        this.execycle = execycle;
    }

    @Basic
    @Column(name = "day_of_month")
    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @Basic
    @Column(name = "day_of_week")
    public Byte getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Byte dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Basic
    @Column(name = "hour")
    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    @Basic
    @Column(name = "minute")
    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    @Basic
    @Column(name = "interval_hour")
    public Integer getIntervalHour() {
        return intervalHour;
    }

    public void setIntervalHour(Integer intervalHour) {
        this.intervalHour = intervalHour;
    }

    @Basic
    @Column(name = "interval_minute")
    public Integer getIntervalMinute() {
        return intervalMinute;
    }

    public void setIntervalMinute(Integer intervalMinute) {
        this.intervalMinute = intervalMinute;
    }

    @Basic
    @Column(name = "task_interval_unit")
    public Byte getTaskIntervalUnit() {
        return taskIntervalUnit;
    }

    public void setTaskIntervalUnit(Byte taskIntervalUnit) {
        this.taskIntervalUnit = taskIntervalUnit;
    }

    @Basic
    @Column(name = "cron_expression")
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Basic
    @Column(name = "is_enable")
    public byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(byte isEnable) {
        this.isEnable = isEnable;
    }

    @Basic
    @Column(name = "task_remark")
    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
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
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsTask that = (CmsTask) o;

        if (taskId != that.taskId) return false;
        if (taskType != that.taskType) return false;
        if (execycle != that.execycle) return false;
        if (isEnable != that.isEnable) return false;
        if (siteId != that.siteId) return false;
        if (userId != that.userId) return false;
        if (taskCode != null ? !taskCode.equals(that.taskCode) : that.taskCode != null) return false;
        if (taskName != null ? !taskName.equals(that.taskName) : that.taskName != null) return false;
        if (jobClass != null ? !jobClass.equals(that.jobClass) : that.jobClass != null) return false;
        if (dayOfMonth != null ? !dayOfMonth.equals(that.dayOfMonth) : that.dayOfMonth != null) return false;
        if (dayOfWeek != null ? !dayOfWeek.equals(that.dayOfWeek) : that.dayOfWeek != null) return false;
        if (hour != null ? !hour.equals(that.hour) : that.hour != null) return false;
        if (minute != null ? !minute.equals(that.minute) : that.minute != null) return false;
        if (intervalHour != null ? !intervalHour.equals(that.intervalHour) : that.intervalHour != null) return false;
        if (intervalMinute != null ? !intervalMinute.equals(that.intervalMinute) : that.intervalMinute != null)
            return false;
        if (taskIntervalUnit != null ? !taskIntervalUnit.equals(that.taskIntervalUnit) : that.taskIntervalUnit != null)
            return false;
        if (cronExpression != null ? !cronExpression.equals(that.cronExpression) : that.cronExpression != null)
            return false;
        if (taskRemark != null ? !taskRemark.equals(that.taskRemark) : that.taskRemark != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskId;
        result = 31 * result + (taskCode != null ? taskCode.hashCode() : 0);
        result = 31 * result + (int) taskType;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (jobClass != null ? jobClass.hashCode() : 0);
        result = 31 * result + (int) execycle;
        result = 31 * result + (dayOfMonth != null ? dayOfMonth.hashCode() : 0);
        result = 31 * result + (dayOfWeek != null ? dayOfWeek.hashCode() : 0);
        result = 31 * result + (hour != null ? hour.hashCode() : 0);
        result = 31 * result + (minute != null ? minute.hashCode() : 0);
        result = 31 * result + (intervalHour != null ? intervalHour.hashCode() : 0);
        result = 31 * result + (intervalMinute != null ? intervalMinute.hashCode() : 0);
        result = 31 * result + (taskIntervalUnit != null ? taskIntervalUnit.hashCode() : 0);
        result = 31 * result + (cronExpression != null ? cronExpression.hashCode() : 0);
        result = 31 * result + (int) isEnable;
        result = 31 * result + (taskRemark != null ? taskRemark.hashCode() : 0);
        result = 31 * result + siteId;
        result = 31 * result + userId;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    private Integer intervalUnit;

    @Transient
    public java.lang.Integer getIntervalUnit () {
        return intervalUnit;
    }
    public void setIntervalUnit (Integer intervalUnit) {
        this.intervalUnit = intervalUnit;
    }
    private Map<String, String> attr;
    @Transient
    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
    @Transient
    public CmsUser getUser () {
        return user;
    }

    /**
     * Set the value related to the column: user_id
     * @param user the user_id value
     */
    public void setUser (CmsUser user) {
        this.user = user;
    }


    /**
     * Return the value associated with the column: site_id
     */
    @Transient
    public CmsSite getSite () {
        return site;
    }

    /**
     * Set the value related to the column: site_id
     * @param site the site_id value
     */
    public void setSite (CmsSite site) {
        this.site = site;
    }

    public void init(CmsSite site, CmsUser user){
        if(getCreateTime()==null){
            setCreateTime(Calendar.getInstance().getTime());
        }
        if(getUser()==null){
            setUser(user);
        }
        if(getSite()==null){
            setSite(site);
        }
    }
}
