package com.anchorcms.cms.model.main;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-5
 * @Desc 栏目可选内容模型表
 */
@Embeddable
public class ChannelModel implements Serializable{
    private static final long serialVersionUID = -3949115942501407989L;
    private String tplContent;
    private String tplMobileContent;

    @Basic
    @Column(name = "tpl_content")
    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
    }

    @Basic
    @Column(name = "tpl_mobile_content")
    public String getTplMobileContent() {
        return tplMobileContent;
    }

    public void setTplMobileContent(String tplMobileContent) {
        this.tplMobileContent = tplMobileContent;
    }

    private CmsModel model;

    @ManyToOne
    @JoinColumn(name = "model_id",insertable = true,updatable = true)
    public CmsModel getModel() {
        return model;
    }

    public void setModel(CmsModel model) {
        this.model = model;
    }

}
