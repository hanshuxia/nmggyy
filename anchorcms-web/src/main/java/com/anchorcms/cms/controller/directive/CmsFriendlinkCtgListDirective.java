package com.anchorcms.cms.controller.directive;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anchorcms.cms.model.assist.CmsFriendlinkCtg;
import com.anchorcms.cms.service.assist.CmsFriendlinkCtgMng;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.anchorcms.common.web.freemarker.DirectiveUtils;


import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import javax.annotation.Resource;

import static com.anchorcms.common.web.freemarker.DirectiveUtils.OUT_LIST;

/**
 * 友情链接类别列表标签
 */
public class CmsFriendlinkCtgListDirective implements TemplateDirectiveModel {
	/**
	 * 输入参数，站点ID。
	 */
	public static final String PARAM_SITE_ID = "siteId";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer siteId = getSiteId(params);
		if (siteId == null) {
			siteId = FrontUtils.getSite(env).getSiteId();
		}
		List<CmsFriendlinkCtg> list = cmsFriendlinkCtgMng.getList(siteId);

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_LIST, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	private Integer getSiteId(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getInt(PARAM_SITE_ID, params);
	}

	@Resource
	private CmsFriendlinkCtgMng cmsFriendlinkCtgMng;
}
