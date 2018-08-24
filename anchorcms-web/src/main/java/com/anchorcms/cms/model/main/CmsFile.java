package com.anchorcms.cms.model.main;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-3
 * @Desc 附件表
 */
@Entity
@Table(name = "c_file")
public class CmsFile implements Serializable{
    private static final long serialVersionUID = -2158156906148071550L;
    private String filePath;
    private String fileName;
    private Boolean fileIsvalid;
    public CmsFile(){
    }

    @Id
    @Column(name = "file_path")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "file_isvalid")
    public Boolean getFileIsvalid() {
        return fileIsvalid;
    }

    public void setFileIsvalid(Boolean fileIsvalid) {
        this.fileIsvalid = fileIsvalid;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsFile cmsFile = (CmsFile) o;

        if (fileIsvalid != cmsFile.fileIsvalid) return false;
        if (filePath != null ? !filePath.equals(cmsFile.filePath) : cmsFile.filePath != null) return false;
        if (fileName != null ? !fileName.equals(cmsFile.fileName) : cmsFile.fileName != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = filePath != null ? filePath.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        return result;
    }


    private Content content;
    @ManyToOne
    @JoinColumn(name="content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
