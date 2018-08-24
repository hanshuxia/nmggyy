package com.anchorcms.core.model;

import com.anchorcms.common.utils.MyFileUtils;
import com.anchorcms.common.utils.UploadUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.*;
import java.net.SocketException;
import java.util.List;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-5
 * @Desc FTP表
 */
@Entity
@Table(name = "o_ftp")
public class Ftp implements Serializable{
    private static final long serialVersionUID = -5536010781200223541L;
    private static final Logger log = LoggerFactory.getLogger(Ftp.class);
    private int ftpId;
    private String ftpName;
    private String ip;
    private int port;
    private String username;
    private String password;
    private String encoding;
    private Integer timeout;
    private String ftpPath;
    private String url;


    @Id
    @Column(name = "ftp_id")
    public int getFtpId() {
        return ftpId;
    }

    public void setFtpId(int ftpId) {
        this.ftpId = ftpId;
    }

    @Basic
    @Column(name = "ftp_name")
    public String getFtpName() {
        return ftpName;
    }

    public void setFtpName(String ftpName) {
        this.ftpName = ftpName;
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
    @Column(name = "port")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "encoding")
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Basic
    @Column(name = "timeout")
    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Basic
    @Column(name = "ftp_path")
    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ftp ftp = (Ftp) o;

        if (ftpId != ftp.ftpId) return false;
        if (port != ftp.port) return false;
        if (ftpName != null ? !ftpName.equals(ftp.ftpName) : ftp.ftpName != null) return false;
        if (ip != null ? !ip.equals(ftp.ip) : ftp.ip != null) return false;
        if (username != null ? !username.equals(ftp.username) : ftp.username != null) return false;
        if (password != null ? !password.equals(ftp.password) : ftp.password != null) return false;
        if (encoding != null ? !encoding.equals(ftp.encoding) : ftp.encoding != null) return false;
        if (timeout != null ? !timeout.equals(ftp.timeout) : ftp.timeout != null) return false;
        if (ftpPath != null ? !ftpPath.equals(ftp.ftpPath) : ftp.ftpPath != null) return false;
        if (url != null ? !url.equals(ftp.url) : ftp.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ftpId;
        result = 31 * result + (ftpName != null ? ftpName.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + port;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (encoding != null ? encoding.hashCode() : 0);
        result = 31 * result + (timeout != null ? timeout.hashCode() : 0);
        result = 31 * result + (ftpPath != null ? ftpPath.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
    public void storeByExt(String path, InputStream in)throws IOException {
        store(path, in);
    }
    private int store(String remote, InputStream in) {
        try {
            FTPClient ftp = getClient();
            if (ftp != null) {
                String filename = getFtpPath() + remote;
                String name = FilenameUtils.getName(filename);
                String path = FilenameUtils.getFullPath(filename);
                if (!ftp.changeWorkingDirectory(path)) {
                    String[] ps = StringUtils.split(path, '/');
                    String p = "/";
                    ftp.changeWorkingDirectory(p);
                    for (String s : ps) {
                        p += s + "/";
                        if (!ftp.changeWorkingDirectory(p)) {
                            ftp.makeDirectory(s);
                            ftp.changeWorkingDirectory(p);
                        }
                    }
                }
                ftp.storeFile(name, in);
                ftp.logout();
                ftp.disconnect();
            }
            in.close();
            return 0;
        } catch (SocketException e) {
            log.error("ftp store error", e);
            return 3;
        } catch (IOException e) {
            log.error("ftp store error", e);
            return 4;
        }
    }
    @Transient
    private FTPClient getClient() throws SocketException, IOException {
        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(
                new PrintWriter(System.out)));
        ftp.setDefaultPort(getPort());
        ftp.connect(getIp());
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            log.warn("FTP server refused connection: {}", getIp());
            ftp.disconnect();
            return null;
        }
        if (!ftp.login(getUsername(), getPassword())) {
            log.warn("FTP server refused login: {}, user: {}", getIp(),
                    getUsername());
            ftp.logout();
            ftp.disconnect();
            return null;
        }
        ftp.setControlEncoding(getEncoding());
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        return ftp;
    }
    @Transient
    public String storeByFilename(String filename, InputStream in)
            throws IOException {
        store(filename, in);
        return filename;
    }
    @Transient
    public String storeByExt(String path, String ext, InputStream in)
            throws IOException {
        String filename = UploadUtils.generateFilename(path, ext);
        store(filename, in);
        return filename;
    }

    public File retrieve(String name,String fileName) throws IOException {
        String path = System.getProperty("java.io.tmpdir");
        File file = new File(path, fileName);
        file = UploadUtils.getUniqueFile(file);
        FTPClient ftp = getClient();
        OutputStream output = new FileOutputStream(file);
        ftp.retrieveFile(getFtpPath() + name, output);
        output.close();
        ftp.logout();
        ftp.disconnect();
        return file;
    }

    public boolean restore(String name, File file) throws IOException {
        store(name, FileUtils.openInputStream(file));
        file.deleteOnExit();
        return true;
    }
    public int storeByFloder(String folder,String rootPath){
        String fileAbsolutePath;
        String fileRelativePath;
        try {
            FTPClient ftp = getClient();
            if (ftp != null) {
                List<File> files= MyFileUtils.getFiles(new File(folder));
                for(File file:files){
                    String filename = getFtpPath() + file.getName();
                    String name = FilenameUtils.getName(filename);
                    String path = FilenameUtils.getFullPath(filename);
                    fileAbsolutePath=file.getAbsolutePath();
                    fileRelativePath=fileAbsolutePath.substring(rootPath.length()+1,fileAbsolutePath.indexOf(name));
                    path+=fileRelativePath.replace("\\", "/");
                    if (!ftp.changeWorkingDirectory(path)) {
                        String[] ps = StringUtils.split(path, '/');
                        String p = "/";
                        ftp.changeWorkingDirectory(p);
                        for (String s : ps) {
                            p += s + "/";
                            if (!ftp.changeWorkingDirectory(p)) {
                                ftp.makeDirectory(s);
                                ftp.changeWorkingDirectory(p);
                            }
                        }
                    }
                    //解决中文乱码问题
                    name=new String(name.getBytes(getEncoding()),"iso-8859-1");
                    if(!file.isFile()){
                        ftp.makeDirectory(name);
                    }else{
                        InputStream in=new FileInputStream(file.getAbsolutePath());
                        ftp.storeFile(name, in);
                        in.close();
                    }
                }
                ftp.logout();
                ftp.disconnect();
            }
            return 0;
        } catch (SocketException e) {
            log.error("ftp store error", e);
            return 3;
        } catch (IOException e) {
            log.error("ftp store error", e);
            return 4;
        }
    }


}
