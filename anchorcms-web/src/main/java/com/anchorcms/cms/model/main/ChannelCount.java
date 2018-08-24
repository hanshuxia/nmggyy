package com.anchorcms.cms.model.main;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-5
 * @Desc CMS栏目访问量计数表
 */
@Entity
@Table(name = "c_channel_count")
public class ChannelCount implements Serializable{
    private static final long serialVersionUID = -5149623885967766059L;
    private int channelId;
    private Integer views;
    private Integer viewsMonth;
    private Integer viewsWeek;
    private Integer viewsDay;
    private Integer contentCountTotal;
    private Integer contentCountDay;
    private Integer contentCountWeek;
    private Integer contentCountMonth;
    private Integer contentCountYear;

    @Id
    @Column(name = "channel_id")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

            @Basic
            @Column(name = "views")
            public Integer getViews() {
                return views;
            }

        public void setViews(Integer views) {
            this.views = views;
        }

        @Basic
        @Column(name = "views_month")
        public Integer getViewsMonth() {
            return viewsMonth;
        }

        public void setViewsMonth(Integer viewsMonth) {
            this.viewsMonth = viewsMonth;
        }

        @Basic
        @Column(name = "views_week")
        public Integer getViewsWeek() {
            return viewsWeek;
        }

        public void setViewsWeek(Integer viewsWeek) {
            this.viewsWeek = viewsWeek;
        }

        @Basic
        @Column(name = "views_day")
        public Integer getViewsDay() {
            return viewsDay;
        }

        public void setViewsDay(Integer viewsDay) {
            this.viewsDay = viewsDay;
        }

        @Basic
        @Column(name = "content_count_total")
        public Integer getContentCountTotal() {
            return contentCountTotal;
        }

        public void setContentCountTotal(Integer contentCountTotal) {
            this.contentCountTotal = contentCountTotal;
        }

        @Basic
        @Column(name = "content_count_day")
        public Integer getContentCountDay() {
            return contentCountDay;
        }

        public void setContentCountDay(Integer contentCountDay) {
            this.contentCountDay = contentCountDay;
        }

        @Basic
        @Column(name = "content_count_week")
        public Integer getContentCountWeek() {
            return contentCountWeek;
        }

        public void setContentCountWeek(Integer contentCountWeek) {
            this.contentCountWeek = contentCountWeek;
        }

        @Basic
        @Column(name = "content_count_month")
        public Integer getContentCountMonth() {
            return contentCountMonth;
        }

        public void setContentCountMonth(Integer contentCountMonth) {
            this.contentCountMonth = contentCountMonth;
        }

        @Basic
        @Column(name = "content_count_year")
        public Integer getContentCountYear() {
            return contentCountYear;
        }

        public void setContentCountYear(Integer contentCountYear) {
            this.contentCountYear = contentCountYear;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ChannelCount that = (ChannelCount) o;

            if (channelId != that.channelId) return false;
            if (views != that.views) return false;
            if (viewsMonth != that.viewsMonth) return false;
            if (viewsWeek != that.viewsWeek) return false;
            if (viewsDay != that.viewsDay) return false;
            if (contentCountTotal != that.contentCountTotal) return false;
            if (contentCountDay != that.contentCountDay) return false;
            if (contentCountWeek != that.contentCountWeek) return false;
            if (contentCountMonth != that.contentCountMonth) return false;
            if (contentCountYear != that.contentCountYear) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = channelId;
            result = 31 * result + views;
            result = 31 * result + viewsMonth;
            result = 31 * result + viewsWeek;
            result = 31 * result + viewsDay;
            result = 31 * result + contentCountTotal;
            result = 31 * result + contentCountDay;
            result = 31 * result + contentCountWeek;
            result = 31 * result + contentCountMonth;
            result = 31 * result + contentCountYear;
            return result;
        }

        private Channel channel;
        @OneToOne
        @MapsId
        @JoinColumn(name="channel_id")
        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }
        public void init() {
            if (getViews() == null) {
                setViews(0);
            }
            if (getViewsMonth() == null) {
                setViewsMonth(0);
            }
            if (getViewsWeek() == null) {
                setViewsWeek(0);
            }
            if (getViewsDay() == null) {
            setViewsDay(0);
        }
        if(getContentCountTotal()==null){
            setContentCountTotal(0);
        }
        if(getContentCountDay()==null){
            setContentCountDay(0);
        }
        if(getContentCountMonth()==null){
            setContentCountMonth(0);
        }
        if(getContentCountWeek()==null){
            setContentCountWeek(0);
        }
        if(getContentCountYear()==null){
            setContentCountYear(0);
        }
    }
}
