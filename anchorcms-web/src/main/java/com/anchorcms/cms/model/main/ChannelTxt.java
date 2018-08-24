package com.anchorcms.cms.model.main;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-2
 * @Desc CMS栏目文本表
 */
@Entity
@Table(name = "c_channel_txt")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class ChannelTxt implements Serializable{
    private static final long serialVersionUID = 3413371645124382009L;
    private int channelId;
    private String txt;
    private String txt1;
    private String txt2;
    private String txt3;

    @Id
    @Column(name = "channel_id")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Basic
    @Column(name = "txt")
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Basic
    @Column(name = "txt1")
    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    @Basic
    @Column(name = "txt2")
    public String getTxt2() {
        return txt2;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }

    @Basic
    @Column(name = "txt3")
    public String getTxt3() {
        return txt3;
    }

    public void setTxt3(String txt3) {
        this.txt3 = txt3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChannelTxt that = (ChannelTxt) o;

        if (channelId != that.channelId) return false;
        if (txt != null ? !txt.equals(that.txt) : that.txt != null) return false;
        if (txt1 != null ? !txt1.equals(that.txt1) : that.txt1 != null) return false;
        if (txt2 != null ? !txt2.equals(that.txt2) : that.txt2 != null) return false;
        if (txt3 != null ? !txt3.equals(that.txt3) : that.txt3 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = channelId;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + (txt1 != null ? txt1.hashCode() : 0);
        result = 31 * result + (txt2 != null ? txt2.hashCode() : 0);
        result = 31 * result + (txt3 != null ? txt3.hashCode() : 0);
        return result;
    }

    private Channel channel;
//    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @ManyToOne
    @MapsId
    @JoinColumn(name="channel_id")
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    public void init() {
        blankToNull();
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getTxt())) {
            setTxt(null);
        }
        if (StringUtils.isBlank(getTxt1())) {
            setTxt1(null);
        }
        if (StringUtils.isBlank(getTxt2())) {
            setTxt2(null);
        }
        if (StringUtils.isBlank(getTxt3())) {
            setTxt3(null);
        }
    }
}
