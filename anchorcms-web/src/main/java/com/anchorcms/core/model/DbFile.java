package com.anchorcms.core.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-8
 * @Desc
 */
@Entity
@Table(name = "o_upload")
public class DbFile implements Serializable{
    private static final long serialVersionUID = -2039717184969319198L;
    private String filename;
    private int length;
    private long lastModified;
    private byte[] content;

    @Id
    @Column(name = "filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Basic
    @Column(name = "length")
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Basic
    @Column(name = "last_modified")
    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    @Basic
    @Column(name = "content")
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DbFile dbFile = (DbFile) o;

        if (length != dbFile.length) return false;
        if (lastModified != dbFile.lastModified) return false;
        if (filename != null ? !filename.equals(dbFile.filename) : dbFile.filename != null) return false;
        if (!Arrays.equals(content, dbFile.content)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filename != null ? filename.hashCode() : 0;
        result = 31 * result + length;
        result = 31 * result + (int) (lastModified ^ (lastModified >>> 32));
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
    /**
     * Constructor for required fields
     */
    public DbFile (
            java.lang.String id,
            java.lang.Integer length,
            java.lang.Long lastModified,
            byte[] content) {
        this.setFilename(id);
        this.setLength(length);
        this.setLastModified(lastModified);
        this.setContent(content);
        initialize();
    }

    public DbFile() {

    }
    protected void initialize () {}

}
