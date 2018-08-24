package com.anchorcms.cms.directive;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.anchorcms.cms.model.assist.CmsAdvertising;
import com.anchorcms.cms.service.assist.CmsAdvertisingMng;
import com.anchorcms.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.anchorcms.common.web.freemarker.DirectiveUtils;


import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import javax.annotation.Resource;

import static com.anchorcms.common.web.freemarker.DirectiveUtils.OUT_BEAN;

/**
 * 广告对象标签
 */
public class CmsAdvertisingDirective implements TemplateDirectiveModel {
	/**
	 * 输入参数，广告ID。
	 */
	public static final String PARAM_ID = "id";

	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer id = DirectiveUtils.getInt(PARAM_ID, params);
		CmsAdvertising ad = null;
		if (id != null) {
			ad = cmsAdvertisingMng.findById(id);
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_BEAN, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(ad));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	@Resource
	private CmsAdvertisingMng cmsAdvertisingMng;
}
