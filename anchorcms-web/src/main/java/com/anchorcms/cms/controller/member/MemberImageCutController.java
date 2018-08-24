package com.anchorcms.cms.controller.member;

import com.anchorcms.common.image.ImageScale;
import com.anchorcms.common.upload.FileRepository;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.Ftp;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.service.DbFileMng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;

@Controller
public class MemberImageCutController {
	private static final Logger log = LoggerFactory
			.getLogger(MemberImageCutController.class);
	/**
	 * 图片选择页面
	 */
	public static final String IMAGE_SELECT_RESULT = "tpl.image_area_select";
	/**
	 * 图片裁剪完成页面
	 */
	public static final String IMAGE_CUTED = "tpl.image_cuted";
	/**
	 * 错误信息参数
	 */
	public static final String ERROR = "error";

	@RequestMapping("/member/v_image_area_select.jspx")
	public String imageAreaSelect(String uploadBase, String imgSrcPath,
								  Integer zoomWidth, Integer zoomHeight, Integer uploadNum,
								  HttpServletRequest request, ModelMap model) {
		model.addAttribute("uploadBase", uploadBase);
		model.addAttribute("imgSrcPath", imgSrcPath);
		model.addAttribute("zoomWidth", zoomWidth);
		model.addAttribute("zoomHeight", zoomHeight);
		model.addAttribute("uploadNum", uploadNum);
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, IMAGE_SELECT_RESULT);
	}

	@RequestMapping("/member/o_image_cut.jspx")
	public String imageCut(String imgSrcPath, Integer imgTop, Integer imgLeft,
						   Integer imgWidth, Integer imgHeight, Integer reMinWidth,
						   Integer reMinHeight, Float imgScale, Integer uploadNum,
						   HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		try {
			if (imgWidth > 0) {
				if (site.getConfig().getIsUploadToDb()) {
					String dbFilePath = site.getConfig().getDbFileUri();
					imgSrcPath = imgSrcPath.substring(dbFilePath.length());
					File file = dbFileMng.retrieve(imgSrcPath);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight,
							getLen(imgTop, imgScale),
							getLen(imgLeft, imgScale), getLen(imgWidth,
									imgScale), getLen(imgHeight, imgScale));
					dbFileMng.restore(imgSrcPath, file);
				} else if (site.getUploadFtp() != null) {
					Ftp ftp = site.getUploadFtp();
					String ftpUrl = ftp.getUrl();
					imgSrcPath = imgSrcPath.substring(ftpUrl.length());
					String fileName=imgSrcPath.substring(imgSrcPath.lastIndexOf("/"));
					File file = ftp.retrieve(imgSrcPath,fileName);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight,
							getLen(imgTop, imgScale),
							getLen(imgLeft, imgScale), getLen(imgWidth,
									imgScale), getLen(imgHeight, imgScale));
					ftp.restore(imgSrcPath, file);
				} else {
					String ctx = request.getContextPath();
					imgSrcPath = imgSrcPath.substring(ctx.length());
					File file = fileRepository.retrieve(imgSrcPath);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight,
							getLen(imgTop, imgScale),
							getLen(imgLeft, imgScale), getLen(imgWidth,
									imgScale), getLen(imgHeight, imgScale));
				}
			} else {
				if (site.getConfig().getIsUploadToDb()) {
					String dbFilePath = site.getConfig().getDbFileUri();
					imgSrcPath = imgSrcPath.substring(dbFilePath.length());
					File file = dbFileMng.retrieve(imgSrcPath);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
					dbFileMng.restore(imgSrcPath, file);
				} else if (site.getUploadFtp() != null) {
					Ftp ftp = site.getUploadFtp();
					String ftpUrl = ftp.getUrl();
					imgSrcPath = imgSrcPath.substring(ftpUrl.length());
					String fileName=imgSrcPath.substring(imgSrcPath.lastIndexOf("/"));
					File file = ftp.retrieve(imgSrcPath,fileName);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
					ftp.restore(imgSrcPath, file);
				} else {
					String ctx = request.getContextPath();
					imgSrcPath = imgSrcPath.substring(ctx.length());
					File file = fileRepository.retrieve(imgSrcPath);
					imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
				}
			}
			model.addAttribute("uploadNum", uploadNum);
		} catch (Exception e) {
			log.error("cut image error", e);
			model.addAttribute(ERROR, e.getMessage());
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, IMAGE_CUTED);
	}

	private int getLen(int len, float imgScale) {
		return Math.round(len / imgScale);
	}

	@Resource
	private ImageScale imageScale;
	@Resource
	private FileRepository fileRepository;
	@Resource
	private DbFileMng dbFileMng;
}
