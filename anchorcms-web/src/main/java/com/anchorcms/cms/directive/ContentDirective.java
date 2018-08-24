package com.anchorcms.cms.directive;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.anchorcms.common.web.freemarker.DirectiveUtils;
import com.anchorcms.common.web.freemarker.ParamsRequiredException;
import com.anchorcms.core.model.CmsSite;


import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import javax.annotation.Resource;

import static com.anchorcms.common.web.freemarker.DirectiveUtils.OUT_BEAN;

/**
 * 内容对象标签
 */
public class ContentDirective implements TemplateDirectiveModel {
	/**
	 * 输入参数，栏目ID。
	 */
	public static final String PARAM_ID = "id";
	/**
	 * 输入参数，下一篇。
	 */
	public static final String PRAMA_NEXT = "next";
	/**
	 * 输入参数，栏目ID。
	 */
	public static final String PARAM_CHANNEL_ID = "channelId";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer id = getId(params);
		Boolean next = DirectiveUtils.getBool(PRAMA_NEXT, params);
		Content content;
		if (next == null) {
			content = contentMng.findById(id);
		} else {
			CmsSite site = FrontUtils.getSite(env);
			Integer channelId = DirectiveUtils.getInt(PARAM_CHANNEL_ID, params);
			content = contentMng.getSide(id, site.getSiteId(), channelId, next);
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_BEAN, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(content));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	private Integer getId(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer id = DirectiveUtils.getInt(PARAM_ID, params);
		if (id != null) {
			return id;
		} else {
			throw new ParamsRequiredException(PARAM_ID);
		}
	}

	@Resource
	private ContentMng contentMng;
}
