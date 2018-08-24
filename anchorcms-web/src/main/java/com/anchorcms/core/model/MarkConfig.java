package com.anchorcms.core.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-4
 * @Desc
 */
@Embeddable
public class MarkConfig implements Serializable {
    private static final long serialVersionUID = -1815270820905097569L;
    // fields
    private Boolean on;
    private Integer minWidth;
    private Integer minHeight;
    private String imagePath;
    private String content;
    private Integer size;
    private String color;
    private Integer alpha;
    private Integer pos;
    private Integer offsetX;
    private Integer offsetY;

    @Basic
    @Column(name = "mark_on")
    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    @Basic
    @Column(name = "mark_width")
    public Integer getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(Integer minWidth) {
        this.minWidth = minWidth;
    }

    @Basic
    @Column(name = "mark_height")
    public Integer getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Integer minHeight) {
        this.minHeight = minHeight;
    }

    @Basic
    @Column(name = "mark_image")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Basic
    @Column(name = "mark_content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "mark_size")
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Basic
    @Column(name = "mark_color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Basic
    @Column(name = "mark_alpha")
    public Integer getAlpha() {
        return alpha;
    }

    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
    }

    @Basic
    @Column(name = "mark_position")
    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    @Basic
    @Column(name = "mark_offset_x")
    public Integer getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(Integer offsetX) {
        this.offsetX = offsetX;
    }

    @Basic
    @Column(name = "mark_offset_y")
    public Integer getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(Integer offsetY) {
        this.offsetY = offsetY;
    }
}
