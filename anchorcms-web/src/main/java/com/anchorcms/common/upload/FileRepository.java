package com.anchorcms.common.upload;

import com.anchorcms.common.utils.UploadUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 本地文件存储
 */
@Service
public class FileRepository implements ServletContextAware {
    private Logger log = LoggerFactory.getLogger(FileRepository.class);

    //修改上传路径 02/24
    private static final String UPLOAD_FILENAME = "upload.properties";
    private static String uploadPath;
    //修改上传路径 02/24
    public FileRepository() throws IOException {
        InputStream in = FileRepository.class.getClassLoader().getResourceAsStream(UPLOAD_FILENAME);
        Properties prop = new Properties();
        prop.load(in);
        uploadPath = prop.getProperty("upload.path");
    }


    public String storeByExt(String path, String ext, MultipartFile file)
            throws IOException {
        String filename = UploadUtils.generateFilename(path, ext);
        File dest = new File(getRealPath(filename));
        dest = UploadUtils.getUniqueFile(dest);
        store(file, dest);
        return filename;
    }

    public String storeByFilename(String filename, MultipartFile file)
            throws IOException {
        if (filename != null && (filename.contains("/") || filename.contains("\\") || filename.indexOf("\0") != -1)) {
            return "";
        }
        File dest = new File(getRealPath(filename));
        store(file, dest);
        return filename;
    }

    public String storeByFilePath(String path, String filename, MultipartFile file)
            throws IOException {
        if (filename != null && (filename.contains("/") || filename.contains("\\") || filename.indexOf("\0") != -1)) {
            return "";
        }
        File dest = new File(getRealPath(path + filename));
        store(file, dest);
        return path + filename;
    }

    public String storeByExt(String path, String ext, File file)
            throws IOException {
        String filename = UploadUtils.generateFilename(path, ext);
        File dest = new File(getRealPath(filename));
        dest = UploadUtils.getUniqueFile(dest);
        store(file, dest);
        return filename;
    }

    public String storeByFilename(String filename, File file)
            throws IOException {
        File dest = new File(getRealPath(filename));
        store(file, dest);
        return filename;
    }

    private void store(MultipartFile file, File dest) throws IOException {
        try {
            UploadUtils.checkDirAndCreate(dest.getParentFile());
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("Transfer file error when upload file", e);
            throw e;
        }
    }

    private void store(File file, File dest) throws IOException {
        try {
            UploadUtils.checkDirAndCreate(dest.getParentFile());
            FileUtils.copyFile(file, dest);
        } catch (IOException e) {
            log.error("Transfer file error when upload file", e);
            throw e;
        }
    }

    public File retrieve(String name) {
        return new File(ctx.getRealPath(name));
    }

    //修改上传路径 02/24
    public String getRealPath(String name) {
        return uploadPath + name;
    }

    private ServletContext ctx;

    public void setServletContext(ServletContext servletContext) {
        this.ctx = servletContext;
    }
}
