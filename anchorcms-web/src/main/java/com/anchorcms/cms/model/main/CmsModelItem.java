package com.anchorcms.cms.model.main;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by smt on 2016/11/16.
 */
@Entity
@Table(name = "c_model_item")
public class CmsModelItem implements Serializable{
    private static final long serialVersionUID = -6631454947604892847L;
    private int modelitemId;
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
    private Boolean isSingle;
    private Boolean isChannel;
    private Boolean isCustom;
    private Boolean isDisplay;
    private Boolean isRequired;
    private Integer imageWidth;
    private Integer imageHeight;
    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
        if (getIsSingle() == null) {
            setIsSingle(true);
        }
        if (getIsCustom() == null) {
            setIsCustom(true);
        }
        if (getIsDisplay() == null) {
            setIsDisplay(true);
        }
    }
    /**
     * Set the value related to the column: is_single
     * @param single the is_single value
     */
    public void setSingle (java.lang.Boolean single) {
        this.isSingle = single;
    }
    /**
     * Set the value related to the column: is_display
     * @param display the is_display value
     */
    public void setDisplay (java.lang.Boolean display) {
        this.isDisplay = display;
    }
    /**
     * Set the value related to the column: item_label
     * @param label the item_label value
     */
    public void setLabel (java.lang.String label) {
        this.itemLabel = label;
    }
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
    @Column(name = "is_single", nullable = false)
    public Boolean getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(Boolean isSingle) {
        this.isSingle = isSingle;
    }

    @Basic
    @Column(name = "is_channel", nullable = false)
    public Boolean getIsChannel() {
        return isChannel;
    }

    public void setIsChannel(Boolean isChannel) {
        this.isChannel = isChannel;
    }

    @Basic
    @Column(name = "is_custom", nullable = false)
    public Boolean getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(Boolean isCustom) {
        this.isCustom = isCustom;
    }

    @Basic
    @Column(name = "is_display", nullable = false)
    public Boolean getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Boolean isDisplay) {
        this.isDisplay = isDisplay;
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
    @Column(name = "image_width", nullable = true)
    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    @Basic
    @Column(name = "image_height", nullable = true)
    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsModelItem that = (CmsModelItem) o;

       // if (modelitemId != null ? !modelitemId.equals(that.modelitemId) : that.modelitemId != null) return false;
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
        if (isSingle != null ? !isSingle.equals(that.isSingle) : that.isSingle != null) return false;
        if (isChannel != null ? !isChannel.equals(that.isChannel) : that.isChannel != null) return false;
        if (isCustom != null ? !isCustom.equals(that.isCustom) : that.isCustom != null) return false;
        if (isDisplay != null ? !isDisplay.equals(that.isDisplay) : that.isDisplay != null) return false;
        if (isRequired != null ? !isRequired.equals(that.isRequired) : that.isRequired != null) return false;
        if (imageWidth != null ? !imageWidth.equals(that.imageWidth) : that.imageWidth != null) return false;
        if (imageHeight != null ? !imageHeight.equals(that.imageHeight) : that.imageHeight != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result =  0;
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
        result = 31 * result + (isSingle != null ? isSingle.hashCode() : 0);
        result = 31 * result + (isChannel != null ? isChannel.hashCode() : 0);
        result = 31 * result + (isCustom != null ? isCustom.hashCode() : 0);
        result = 31 * result + (isDisplay != null ? isDisplay.hashCode() : 0);
        result = 31 * result + (isRequired != null ? isRequired.hashCode() : 0);
        result = 31 * result + (imageWidth != null ? imageWidth.hashCode() : 0);
        result = 31 * result + (imageHeight != null ? imageHeight.hashCode() : 0);
        return result;
    }

    private CmsModel model;

    @ManyToOne
    //@MapsId
    @JoinColumn(name = "model_id")
    public CmsModel getModel() {
        return model;
    }

    public void setModel(CmsModel model) {
        this.model = model;
    }

    public static String REF = "CmsModelItem";
    public static String PROP_SINGLE = "single";
    public static String PROP_DATA_TYPE = "dataType";
    public static String PROP_CUSTOM = "custom";
    public static String PROP_OPT_VALUE = "optValue";
    public static String PROP_HELP = "help";
    public static String PROP_PRIORITY = "priority";
    public static String PROP_FIELD = "field";
    public static String PROP_LABEL = "label";
    public static String PROP_COLS = "cols";
    public static String PROP_MODEL = "model";
    public static String PROP_DEF_VALUE = "defValue";
    public static String PROP_HELP_POSITION = "helpPosition";
    public static String PROP_ROWS = "rows";
    public static String PROP_SIZE = "size";
    public static String PROP_DISPLAY = "display";
    public static String PROP_CHANNEL = "channel";
    public static String PROP_ID = "id";


}
