package com.anchorcms.cms.model.main;

import com.anchorcms.core.model.CmsUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc 文章操作记录表
 */
@Entity
@Table(name = "c_content_record")
public class ContentRecord implements Serializable {
    private static final long serialVersionUID = -6494448955871009821L;
    private long contentRecordId;
    //private int contentId;
    // private int userId;
    private Date operateTime;
    private byte operateType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_record_id")
    public long getContentRecordId() {
        return contentRecordId;
    }

    public void setContentRecordId(long contentRecordId) {
        this.contentRecordId = contentRecordId;
    }

/*    @Basic
    @Column(name = "content_id")
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }*/

    @Basic
    @Column(name = "operate_time")
    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    @Basic
    @Column(name = "operate_type")
    public byte getOperateType() {
        return operateType;
    }

    public void setOperateType(byte operateType) {
        this.operateType = operateType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentRecord that = (ContentRecord) o;

        if (contentRecordId != that.contentRecordId) return false;
        if (operateType != that.operateType) return false;
        if (operateTime != null ? !operateTime.equals(that.operateTime) : that.operateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (contentRecordId ^ (contentRecordId >>> 32));
        result = 31 * result + (operateTime != null ? operateTime.hashCode() : 0);
        result = 31 * result + (int) operateType;
        return result;
    }

    private Content content;

    private CmsUser user;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = true, updatable = true)
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "content_id", insertable = true, updatable = true)
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public enum ContentOperateType {
        /**
         * 新增
         */
        add,
        /**
         * 修改
         */
        edit,
        /**
         * 审核
         */
        check,
        /**
         * 退回
         */
        rejected,
        /**
         * 移动
         */
        move,
        /**
         * 生成静态页
         */
        createPage,
        /**
         * 回收
         */
        cycle,
        /**
         * 归档
         */
        pigeonhole,
        /**
         * 出档
         */
        reuse,
        /**
         * 共享
         */
        shared
    }

    ;

    public static final byte add = 0;
    public static final byte edit = 1;
    public static final byte check = 2;
    public static final byte rejected = 3;
    public static final byte move = 4;
    public static final byte createPage = 5;
    public static final byte cycle = 6;
    public static final byte pigeonhole = 7;
    public static final byte reuse = 8;
    public static final byte shared = 9;
}
