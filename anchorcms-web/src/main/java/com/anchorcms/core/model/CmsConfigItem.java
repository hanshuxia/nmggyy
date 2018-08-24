package com.anchorcms.core.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by smt on 2016/11/14.
 */
@Entity
@Table(name = "c_config_item")
public class CmsConfigItem implements Serializable {
    private static final long serialVersionUID = 6750656457111988963L;
    private Integer modelitemId;
    private String field;
    private String itemLabel;
    private Integer priority;
    private String defValue;
    private String optValue;
    private String textSize;
    private String areaRows;
    private String areaCols;
    private String help;
    private String helpPosition;
    private Integer dataType;
    private Boolean isRequired;
    private Integer category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modelitem_id", nullable = false)
    public Integer getModelitemId() {
        return modelitemId;
    }

    public void setModelitemId(Integer modelitemId) {
        this.modelitemId = modelitemId;
    }

    @Basic
    @Column(name = "field", nullable = false, length = 50)
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Basic
    @Column(name = "item_label", nullable = false, length = 100)
    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    @Basic
    @Column(name = "priority", nullable = false)
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "def_value", nullable = true, length = 255)
    public String getDefValue() {
        return defValue;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    @Basic
    @Column(name = "opt_value", nullable = true, length = 255)
    public String getOptValue() {
        return optValue;
    }

    public void setOptValue(String optValue) {
        this.optValue = optValue;
    }

    @Basic
    @Column(name = "text_size", nullable = true, length = 20)
    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    @Basic
    @Column(name = "area_rows", nullable = true, length = 3)
    public String getAreaRows() {
        return areaRows;
    }

    public void setAreaRows(String areaRows) {
        this.areaRows = areaRows;
    }

    @Basic
    @Column(name = "area_cols", nullable = true, length = 3)
    public String getAreaCols() {
        return areaCols;
    }

    public void setAreaCols(String areaCols) {
        this.areaCols = areaCols;
    }

    @Basic
    @Column(name = "help", nullable = true, length = 255)
    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    @Basic
    @Column(name = "help_position", nullable = true, length = 1)
    public String getHelpPosition() {
        return helpPosition;
    }

    public void setHelpPosition(String helpPosition) {
        this.helpPosition = helpPosition;
    }

    @Basic
    @Column(name = "data_type", nullable = false)
    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    @Basic
    @Column(name = "is_required", nullable = false)
    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    @Basic
    @Column(name = "category", nullable = false)
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsConfigItem that = (CmsConfigItem) o;

        if (modelitemId != null ? !modelitemId.equals(that.modelitemId) : that.modelitemId != null) return false;
        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (itemLabel != null ? !itemLabel.equals(that.itemLabel) : that.itemLabel != null) return false;
        if (priority != null ? !priority.equals(that.priority) : that.priority != null) return false;
        if (defValue != null ? !defValue.equals(that.defValue) : that.defValue != null) return false;
        if (optValue != null ? !optValue.equals(that.optValue) : that.optValue != null) return false;
        if (textSize != null ? !textSize.equals(that.textSize) : that.textSize != null) return false;
        if (areaRows != null ? !areaRows.equals(that.areaRows) : that.areaRows != null) return false;
        if (areaCols != null ? !areaCols.equals(that.areaCols) : that.areaCols != null) return false;
        if (help != null ? !help.equals(that.help) : that.help != null) return false;
        if (helpPosition != null ? !helpPosition.equals(that.helpPosition) : that.helpPosition != null) return false;
        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) return false;
        if (isRequired != null ? !isRequired.equals(that.isRequired) : that.isRequired != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = modelitemId != null ? modelitemId.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (itemLabel != null ? itemLabel.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (defValue != null ? defValue.hashCode() : 0);
        result = 31 * result + (optValue != null ? optValue.hashCode() : 0);
        result = 31 * result + (textSize != null ? textSize.hashCode() : 0);
        result = 31 * result + (areaRows != null ? areaRows.hashCode() : 0);
        result = 31 * result + (areaCols != null ? areaCols.hashCode() : 0);
        result = 31 * result + (help != null ? help.hashCode() : 0);
        result = 31 * result + (helpPosition != null ? helpPosition.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (isRequired != null ? isRequired.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    private CmsConfig config;

    @ManyToOne
    @JoinColumn(name = "config_id")
    public CmsConfig getConfig() {
        return config;
    }

    public void setConfig(CmsConfig config) {
        this.config = config;
    }

    //会员注册模型
    public static final int CATEGORY_REGISTER=1;

    // 将字符串字段全部设置为非null，方便判断。
    public void emptyToNull() {
        if (StringUtils.isBlank(getDefValue())) {
            setDefValue(null);
        }
        if (StringUtils.isBlank(getOptValue())) {
            setOptValue(null);
        }
        if (StringUtils.isBlank(getTextSize())) {
            setTextSize(null);
        }
        if (StringUtils.isBlank(getAreaRows())) {
            setAreaRows(null);
        }
        if (StringUtils.isBlank(getAreaCols())) {
            setAreaCols(null);
        }
        if (StringUtils.isBlank(getHelp())) {
            setHelp(null);
        }
        if (StringUtils.isBlank(getHelpPosition())) {
            setHelpPosition(null);
        }
    }

    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
    }
}
