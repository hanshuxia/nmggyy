package com.anchorcms.cms.controller.admin.assist;

import com.anchorcms.cms.model.assist.CmsAdvertising;
import com.anchorcms.cms.model.assist.CmsAdvertisingSpace;
import com.anchorcms.cms.service.assist.CmsAdvertisingMng;
import com.anchorcms.cms.service.assist.CmsAdvertisingSpaceMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.upload.FileRepository;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.common.web.RequestUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.Ftp;
import com.anchorcms.core.service.CmsLogMng;
import com.anchorcms.core.service.DbFileMng;
import com.anchorcms.core.web.WebErrors;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import static com.anchorcms.common.page.SimplePage.cpn;

@Controller
public class CmsAdvertisingController {
	private static final Logger log = LoggerFactory
			.getLogger(CmsAdvertisingController.class);

	@RequiresPermissions("advertising:v_list")
	@RequestMapping("/advertising/v_list.do")
	public String list(Integer queryAdspaceId, Boolean queryEnabled,
			Integer pageNo, HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Pagination pagination = manager.getPage(site.getSiteId(), queryAdspaceId,
				queryEnabled, cpn(pageNo), CookieUtils.getPageSize(request));
		List<CmsAdvertisingSpace> adspaceList = cmsAdvertisingSpaceMng
				.getList(site.getSiteId());
		model.addAttribute("pagination", pagination);
		model.addAttribute("adspaceList", adspaceList);
		model.addAttribute("pageNo", pagination.getPageNo());
		if (queryAdspaceId != null) {
			model.addAttribute("queryAdspaceId", queryAdspaceId);
		}
		if (queryEnabled != null) {
			model.addAttribute("queryEnabled", queryEnabled);
		}
		return "advertising/list";
	}

	@RequiresPermissions("advertising:v_add")
	@RequestMapping("/advertising/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<CmsAdvertisingSpace> adspaceList = cmsAdvertisingSpaceMng
				.getList(site.getSiteId());
		model.addAttribute("adspaceList", adspaceList);
		return "advertising/add";
	}

	@RequiresPermissions("advertising:v_edit")
	@RequestMapping("/advertising/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsAdvertising cmsAdvertising = manager.findById(id);
		model.addAttribute("cmsAdvertising", cmsAdvertising);
		model.addAttribute("attr", cmsAdvertising.getAttr());
		model.addAttribute("adspaceList", cmsAdvertisingSpaceMng.getList(site
				.getSiteId()));
		model.addAttribute("pageNo", pageNo);
		return "advertising/edit";
	}

	@RequiresPermissions("advertising:o_save")
	@RequestMapping("/advertising/o_save.do")
	public String save(CmsAdvertising bean, Integer adspaceId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
		// 去除为空串的属性
		Set<String> toRemove = new HashSet<String>();
		for (Entry<String, String> entry : attr.entrySet()) {
			if (StringUtils.isBlank(entry.getValue())) {
				toRemove.add(entry.getKey());
			}
		}
		for (String key : toRemove) {
			attr.remove(key);
		}
		bean = manager.save(bean, adspaceId, attr);
		log.info("save CmsAdvertising id={}", bean.getAdvertisingId());
		cmsLogMng.operating(request, "cmsAdvertising.log.save", "id="
				+ bean.getAdvertisingId() + ";name=" + bean.getAdName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("advertising:o_update")
	@RequestMapping("/advertising/o_update.do")
	public String update(Integer queryAdspaceId, Boolean queryEnabled,
			CmsAdvertising bean, Integer adspaceId, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getAdvertisingId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
		// 去除为空串的属性
		Set<String> toRemove = new HashSet<String>();
		for (Entry<String, String> entry : attr.entrySet()) {
			if (StringUtils.isBlank(entry.getValue())) {
				toRemove.add(entry.getKey());
			}
		}
		for (String key : toRemove) {
			attr.remove(key);
		}
		bean = manager.update(bean, adspaceId, attr);
		log.info("update CmsAdvertising id={}.", bean.getAdvertisingId());
		cmsLogMng.operating(request, "cmsAdvertising.log.update", "id="
				+ bean.getAdvertisingId() + ";name=" + bean.getAdName());
		return list(queryAdspaceId, queryEnabled, pageNo, request, model);
	}

	@RequiresPermissions("advertising:o_delete")
	@RequestMapping("/advertising/o_delete.do")
	public String delete(Integer[] ids, Integer queryAdspaceId,
			Boolean queryEnabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsAdvertising[] beans = manager.deleteByIds(ids);
		for (CmsAdvertising bean : beans) {
			log.info("delete CmsAdvertising id={}", bean.getAdvertisingId());
			cmsLogMng.operating(request, "cmsAdvertising.log.delete", "id="
					+ bean.getAdvertisingId() + ";name=" + bean.getAdName());
		}
		return list(queryAdspaceId, queryEnabled, pageNo, request, model);
	}

	@RequiresPermissions("advertising:o_upload_flash")
	@RequestMapping("/advertising/o_upload_flash.do")
	public String uploadFlash(
			@RequestParam(value = "flashFile", required = false) MultipartFile file,
			String flashNum, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpload(file, request);
		if (errors.hasErrors()) {
			model.addAttribute("error", errors.getErrors().get(0));
			return "advertising/flash_iframe";
		}
		CmsSite site = CmsUtils.getSite(request);
		String origName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(origName).toLowerCase(
				Locale.ENGLISH);
		// TODO 检查允许上传的后缀
		try {
			String fileUrl;
			if (site.getUploadFtp() != null) {
				Ftp ftp = site.getUploadFtp();
				String ftpUrl = ftp.getUrl();
				fileUrl = ftp.storeByExt(site.getUploadPath(), ext, file
						.getInputStream());
				// 加上url前缀
				fileUrl = ftpUrl + fileUrl;
			} else {
				String ctx = request.getContextPath();
				fileUrl = fileRepository.storeByExt(site.getUploadPath(), ext,
						file);
				// 加上部署路径
				fileUrl = ctx + fileUrl;
			}
			model.addAttribute("flashPath", fileUrl);
			model.addAttribute("flashName", origName);
			model.addAttribute("flashNum", flashNum);
		} catch (IllegalStateException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		} catch (IOException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		}
		return "advertising/flash_iframe";
	}

	private WebErrors validateSave(CmsAdvertising bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getSiteId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getSiteId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, site.getSiteId(), errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsAdvertising entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsAdvertising.class, id)) {
			return true;
		}
		if (!entity.getSite().getSiteId().equals(siteId)) {
			errors.notInSite(CmsAdvertising.class, id);
			return true;
		}
		return false;
	}

	private WebErrors validateUpload(MultipartFile file,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifNull(file, "file")) {
			return errors;
		}
		return errors;
	}

	@Resource
	private CmsAdvertisingSpaceMng cmsAdvertisingSpaceMng;
	@Resource
	private FileRepository fileRepository;
	@Resource
	private DbFileMng dbFileMng;
	@Resource
	private CmsLogMng cmsLogMng;
	@Resource
	private CmsAdvertisingMng manager;
}