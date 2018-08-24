package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudCenter;
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterService;
import com.anchorcms.icloud.service.cloudcenter.CloudMangerService;
import com.anchorcms.icloud.service.common.PubCodeService;
import com.anchorcms.icloud.service.supplychain.SSpareDetailMng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.anchorcms.common.constants.Constants.TPLDIR_CONTENT;
import static com.anchorcms.common.constants.Constants.TPLDIR_SUPPLYCHAIN;

/**
 * Created by hansx on 2016/12/20.
 *
 * 备品备件详情
 */
@Controller
public class SSpareDetailController {
	private static final Logger log = LoggerFactory.getLogger(SSpareDetailController.class);

	public static final String TPL_SPAREDETAIL = "tpl.spareDetail";
	public static final String TPL_CLOUDCENTER_DETAIL = "tpl.cloudcenter_detail";

	/**
	 * @author hansx
	 * @Date 2016/12/20 0010 下午 5:23
	 * @return
	 * 根据id获取备品备件信息
	 */
	@Resource
	private PubCodeService pubCodeService;
	@RequestMapping(value = "/spare/detail.jspx")
	public String findById(String id, HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if (id != null&&!"".equals(id)) {
			SSpare spare = sSpareMng.findById(id);
			model.addAttribute("spare", spare);
			String userId = spare.getCreaterId();
			CmsUser cmsuser = userDao.findById(Integer.parseInt(userId));
			String userName = cmsuser.getUsername();
			model.addAttribute("userName",userName);

			String spare_type = spare.getSpareType();
			PubCode pc = pubCodeService.findUniqueCode("BPBJLX",spare_type);
			if (pc.getParentCodeId() == null) {
				pc = pubCodeService.findById(pc.getId());
			} else {
				pc = pubCodeService.findById(pc.getParentCodeId());
			}
			String pk = pc.getKey();
			model.addAttribute("pck", pk);
			Content content = spare.getContent();
			model.addAttribute("content", content);
			SCompany company = spare.getCompany();
			model.addAttribute("channel",channelMng.findById(99));
			model.addAttribute("company", company);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SUPPLYCHAIN, TPL_SPAREDETAIL);
	}

	/**
	 * @author hansx
	 * @Date 2017/3/2 0002 上午 11:02
	 * @return
	 * 根据id获取云计算中心信息
	 */
	@RequestMapping(value = "/icloudcenterdetail.jspx")
	public String findIcloudCenterById(String id, HttpServletRequest request,
						    ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		if (id != null&&!"".equals(id)) {
			SIcloudCenter s_icloud_center = cloudCenterService.findById(Integer.valueOf(id));
//			List <sIcloud> list = cloudMangerService.getsIcloudManage(Integer.valueOf(id));
			model.addAttribute("bean", s_icloud_center);
			String userId = s_icloud_center.getCreaterId();
			CmsUser cmsuser = userDao.findById(Integer.parseInt(userId));
			String userName = cmsuser.getUsername();
			model.addAttribute("userName",userName);
			model.addAttribute("channel",channelMng.findById(100));

		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CONTENT, TPL_CLOUDCENTER_DETAIL);
	}

	@Resource
	private SSpareDetailMng sSpareMng;
	@Resource
	private ChannelMng channelMng;
	@Resource
	private CmsUserDao userDao;
	@Resource
	CloudCenterService cloudCenterService;
	@Resource
	private CloudMangerService cloudMangerService;
}
