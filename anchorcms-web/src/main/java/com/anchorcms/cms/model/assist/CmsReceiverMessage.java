package com.anchorcms.cms.model.assist;

import com.anchorcms.common.utils.StrUtil;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-16
 * @Desc 发信的信件id
 */
@Entity
@Table(name = "c_receiver_message")
public class CmsReceiverMessage implements Serializable{
    private static final long serialVersionUID = 1440780072112690391L;
    private int msgReId;
    private String msgTitle;
    private String msgContent;
    private Date sendTime;
    private Boolean msgStatus;
    private int msgBox;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_re_id")
    public int getMsgReId() {
        return msgReId;
    }

    public void setMsgReId(int msgReId) {
        this.msgReId = msgReId;
    }


    @Basic
    @Column(name = "msg_title")
    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    @Basic
    @Column(name = "msg_content")
    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    @Basic
    @Column(name = "send_time")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Basic
    @Column(name = "msg_status")
    public Boolean getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(Boolean msgStatus) {
        this.msgStatus = msgStatus;
    }

    @Basic
    @Column(name = "msg_box")
    public int getMsgBox() {
        return msgBox;
    }

    public void setMsgBox(int msgBox) {
        this.msgBox = msgBox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsReceiverMessage that = (CmsReceiverMessage) o;

        if (msgReId != that.msgReId) return false;
        if (msgStatus != that.msgStatus) return false;
        if (msgBox != that.msgBox) return false;
        if (msgTitle != null ? !msgTitle.equals(that.msgTitle) : that.msgTitle != null) return false;
        if (msgContent != null ? !msgContent.equals(that.msgContent) : that.msgContent != null) return false;
        if (sendTime != null ? !sendTime.equals(that.sendTime) : that.sendTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = msgReId;
        result = 31 * result + (msgTitle != null ? msgTitle.hashCode() : 0);
        result = 31 * result + (msgContent != null ? msgContent.hashCode() : 0);
        result = 31 * result + (sendTime != null ? sendTime.hashCode() : 0);
        result = 31 * result + msgBox;
        return result;
    }
    private CmsSite site;

    private CmsMessage message;

    @ManyToOne
    @JoinColumn(name = "msg_id")
    public CmsMessage getMessage() {
        return message;
    }

    public void setMessage(CmsMessage message) {
        this.message = message;
    }

    @ManyToOne
    @JoinColumn(name = "site_id")
    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }

    public CmsReceiverMessage(CmsMessage message) {
        setMsgReId(message.getMsgId());
        setMsgReceiverUser(message.getMsgReceiverUser());
        setMsgSendUser(message.getMsgSendUser());
        setSite(message.getSite());
        setMsgTitle(message.getMsgTitle());
        setMsgContent(message.getMsgContent());
        setSendTime(message.getSendTime());
        setMsgStatus(message.getMsgStatus());
        setMsgBox(message.getMsgBox());
    }
    public CmsReceiverMessage() {
        initialize();
    }
    protected void initialize () {}

    private CmsUser msgReceiverUser;
    private CmsUser msgSendUser;

    @ManyToOne
    @JoinColumn(name = "msg_receiver_user")
    public CmsUser getMsgReceiverUser() {
        return msgReceiverUser;
    }

    public void setMsgReceiverUser(CmsUser msgReceiverUser) {
        this.msgReceiverUser = msgReceiverUser;
    }

    @ManyToOne
    @JoinColumn(name = "msg_send_user")
    public CmsUser getMsgSendUser() {
        return msgSendUser;
    }

    public void setMsgSendUser(CmsUser msgSendUser) {
        this.msgSendUser = msgSendUser;
    }

    @Transient
    public String getTitleHtml() {
        return StrUtil.txt2htm(getMsgTitle());
    }
    @Transient
    public String getContentHtml() {
        return StrUtil.txt2htm(getMsgContent());
    }
    @Transient
    public int getMsgId() {
        return msgReId;
    }
}
