package com.anchorcms.core.model;

import com.anchorcms.common.constants.Constants;
import com.anchorcms.core.tpl.Tpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.anchorcms.common.constants.Constants.SPT;
/**
 * Created by Cank on 2016/11/16.
 */
@Entity
@Table(name = "o_template")
public class DbTpl implements Tpl {
    private static final long serialVersionUID = -45645L;
    private String tplName;
    private String tplSource;
    private long lastModified;
    private boolean isDirectory;

    @Id
    @Column(name = "tpl_name")
    public String getId() {
        return tplName;
    }

    public void setId(String tplName) {
        this.tplName = tplName;
    }


    @Basic
    @Column(name = "tpl_source")
    public String getSource() {
        return tplSource;
    }

    public void setSource(String tplSource) {
        this.tplSource = tplSource;
    }

    @Basic
    @Column(name = "last_modified")
    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    @Basic
    @Column(name = "is_directory")
    public boolean getDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DbTpl that = (DbTpl) o;

        if (tplName != null ? !tplName.equals(that.tplName) : that.tplName != null) return false;
        if (tplSource != null ? !tplSource.equals(that.tplSource) : that.tplSource != null) return false;
        //if (lastModified != null ? !lastModified.equals(that.lastModified) : that.lastModified != null) return false;
      //  if (isDirectory != null ? !isDirectory.equals(that.isDirectory) : that.isDirectory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tplName != null ? tplName.hashCode() : 0;
        result = 31 * result + (tplSource != null ? tplSource.hashCode() : 0);
        //result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
      //  result = 31 * result + (isDirectory != null ? isDirectory.hashCode() : 0);
        return result;
    }

    @Transient
    public String getName() {
        return getId();
    }
    @Transient
    public String getPath() {
        String name = getId();
        return getId().substring(0, name.lastIndexOf("/"));
    }
    @Transient
    public String getFilename() {
        String name = getId();
        if (!StringUtils.isBlank(name)) {
            int index = name.lastIndexOf(Constants.SPT);
            if (index != -1) {
                return name.substring(index + 1, name.length());
            }
        }
        return name;
    }
    @Transient
    public long getLength() {
        if (isDirectory() || getSource() == null) {
            return 128;
        } else {
            // 一个英文字符占1个字节，而一个中文则占3-4字节，这里取折中一个字符1.5个字节
            return (long) (getSource().length() * 1.5);
        }
    }
    @Transient
    public int getSize() {
        return (int) (getLength() / 1024) + 1;
    }
    @Transient
    public boolean isDirectory() {
        return false;
    }
    @Transient
    public Date getLastModifiedDate() {
        return new Timestamp(getLastModified());
    }
    @Transient
    public static String[] getParentDir(String path) {
        Assert.notNull(path);
        if (!path.startsWith(SPT)) {
            throw new IllegalArgumentException("path must start with /");
        }
        List<String> list = new ArrayList<String>();
        int index = path.indexOf(SPT, 1);
        while (index >= 0) {
            list.add(path.substring(0, index));
            index = path.indexOf(SPT, index + 1);
        }
        String[] arr = new String[list.size()];
        return list.toArray(arr);
    }

}
