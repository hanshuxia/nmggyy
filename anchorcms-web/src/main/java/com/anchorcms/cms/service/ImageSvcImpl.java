package com.anchorcms.cms.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import com.anchorcms.common.file.FileNameUtils;
import com.anchorcms.common.image.ImageUtils;
import com.anchorcms.common.upload.FileRepository;
import com.anchorcms.common.utils.UploadUtils;
import com.anchorcms.common.web.mvc.RealPathResolver;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.Ftp;
import com.anchorcms.core.service.DbFileMng;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Tom
 */
@Service
public class ImageSvcImpl implements ImageSvc {
	@Transactional(readOnly = true)
	public String crawlImg(String imgUrl,CmsSite site) {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
	    CloseableHttpClient client = httpClientBuilder.build();  
		String outFileName="";
		String fileUrl="";
		try{
			if(validUrl(imgUrl)){
				URI uri=new URI(imgUrl);
				HttpGet httpget = new HttpGet(uri);
				HttpResponse response = client.execute(httpget);
				InputStream is = null;
				OutputStream os = null;
				HttpEntity entity = null;
				entity = response.getEntity();
				is = entity.getContent();
				String ctx = site.getContextPath();
				String ext= FileNameUtils.getFileSufix(imgUrl);
				if (site.getUploadFtp() != null) {
					Ftp ftp = site.getUploadFtp();
					String ftpUrl = ftp.getUrl();
					fileUrl = ftp.storeByExt(site.getUploadPath(), ext, is);
					// 加上url前缀
					fileUrl = ftpUrl + fileUrl;
				} else {
					outFileName= UploadUtils.generateFilename(site.getUploadPath(), FileNameUtils.getFileSufix(imgUrl));
					File outFile=new File(realPathResolver.get(outFileName));
					UploadUtils.checkDirAndCreate(outFile.getParentFile());
					os = new FileOutputStream(outFile);
					IOUtils.copy(is, os);
					// 加上部署路径
					if(ctx!=null&&StringUtils.isNotBlank(ctx)){
						fileUrl = ctx + outFileName;
					}else{
						fileUrl = outFileName;
					}
				}
			}
		}catch (Exception e) {
		}finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
       }
		return fileUrl;
	}
	@Transactional(readOnly = true)
	public String crawlImg(String imgUrl,String ctx,Ftp ftp,String uploadPath) {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
	    CloseableHttpClient client = httpClientBuilder.build();  
		String outFileName="";
		String fileUrl="";
		try{
			if(validUrl(imgUrl)){
				HttpGet httpget = new HttpGet(new URI(imgUrl));
				HttpResponse response = client.execute(httpget);
				InputStream is = null;
				OutputStream os = null;
				HttpEntity entity = null;
				entity = response.getEntity();
				is = entity.getContent();
				String ext=FileNameUtils.getFileSufix(imgUrl);
				if (ftp!=null) {
					String ftpUrl = ftp.getUrl();
					fileUrl = ftp.storeByExt(uploadPath, ext, is);
					// 加上url前缀
					fileUrl = ftpUrl + fileUrl;
				} else {
					outFileName=UploadUtils.generateFilename(uploadPath, FileNameUtils.getFileSufix(imgUrl));
					File outFile=new File(realPathResolver.get(outFileName));
					UploadUtils.checkDirAndCreate(outFile.getParentFile());
					os = new FileOutputStream(outFile);
					IOUtils.copy(is, os);
					// 加上部署路径
					if(ctx!=null&&StringUtils.isNotBlank(ctx)){
						fileUrl = ctx + outFileName;
					}else{
						fileUrl = outFileName;
					}
				}
			}
		}catch (Exception e) {
		}finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
       }
		return fileUrl;
	}
	
	private boolean  validUrl(String imgUrl){
		URL url;
		boolean isImage=true;
		try {
			url = new URL(imgUrl);
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			isImage= ImageUtils.isImage(inputStream);
			inputStream.close();
		} catch (Exception e) {
			isImage= false;
		}
		return isImage;
	}
	@Autowired
	protected DbFileMng dbFileMng;
	@Autowired
	protected FileRepository fileRepository;
	@Autowired
	private RealPathResolver realPathResolver;
}
