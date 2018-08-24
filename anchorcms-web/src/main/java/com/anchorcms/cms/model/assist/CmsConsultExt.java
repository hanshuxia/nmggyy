package com.anchorcms.cms.model.assist;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ly on 2017/1/13.
 */
@Entity
@Table(name = "c_consult_ext")
public class CmsConsultExt implements Serializable{
    private static final long serialVersionUID = -7427602193850954475L;
    private int consultId;
    private String ip;
    private String text;
    private String reply;

    @Id
    @Column(name = "consult_id")
    public int getConsultId() {
        return consultId;
    }

    public void setConsultId(int consultId) {
        this.consultId = consultId;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "reply")
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsConsultExt that = (CmsConsultExt) o;

        if (consultId != that.consultId) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (reply != null ? !reply.equals(that.reply) : that.reply != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = consultId;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (reply != null ? reply.hashCode() : 0);
        return result;
    }

    private CmsConsult consult;
    @OneToOne
    @MapsId
    @JoinColumn(name="consult_id",insertable = false,updatable = false)
    public CmsConsult getConsult() {
        return consult;
    }

    public void setConsult(CmsConsult consult) {
        this.consult = consult;
    }
}
