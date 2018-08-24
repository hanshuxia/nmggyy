package com.anchorcms.cms.lucene;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.constants.Constants;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.anchorcms.common.web.freemarker.DirectiveUtils;
import com.anchorcms.common.web.freemarker.ParamsRequiredException;
import com.anchorcms.common.web.mvc.RealPathResolver;
import com.anchorcms.core.model.CmsSite;
import org.apache.commons.lang.StringUtils;


import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.lucene.queryParser.ParseException;

import javax.annotation.Resource;

import static com.anchorcms.common.constants.Constants.TPL_STYLE_LIST;
import static com.anchorcms.common.constants.Constants.TPL_SUFFIX;
import static com.anchorcms.common.constants.Constants.UTF8;
import static com.anchorcms.common.utils.FrontUtils.PARAM_STYLE_LIST;
import static com.anchorcms.common.web.freemarker.DirectiveUtils.OUT_LIST;

public class LuceneDirectiveList extends LuceneDirectiveAbstract {
	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "lucene_list";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		CmsSite site = FrontUtils.getSite(env);
		int first = FrontUtils.getFirst(params);
		int count = FrontUtils.getCount(params);
		String query = getQuery(params);
		String workplace= getWorkplace(params);
		String category= getCategory(params);
		Integer siteId = getSiteId(params);
		Integer channelId = getChannelId(params);
		Date startDate = getStartDate(params);
		Date endDate = getEndDate(params);
		List<Content> list;
		try {
			String path = realPathResolver.get(Constants.LUCENE_PATH);
			list = luceneContentSvc.searchList(path, query,category,workplace, siteId, channelId,
					startDate, endDate, first, count);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_LIST, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		DirectiveUtils.InvokeType type = DirectiveUtils.getInvokeType(params);
		String listStyle = DirectiveUtils.getString(PARAM_STYLE_LIST, params);
		if (DirectiveUtils.InvokeType.sysDefined == type) {
			if (StringUtils.isBlank(listStyle)) {
				throw new ParamsRequiredException(PARAM_STYLE_LIST);
			}
			env.include(TPL_STYLE_LIST + listStyle + TPL_SUFFIX, UTF8, true);
		} else if (DirectiveUtils.InvokeType.userDefined == type) {
			if (StringUtils.isBlank(listStyle)) {
				throw new ParamsRequiredException(PARAM_STYLE_LIST);
			}
			FrontUtils.includeTpl(TPL_STYLE_LIST, site, env);
		} else if (DirectiveUtils.InvokeType.custom == type) {
			FrontUtils.includeTpl(TPL_NAME, site, params, env);
		} else if (DirectiveUtils.InvokeType.body == type) {
			body.render(env.getOut());
		} else {
			throw new RuntimeException("invoke type not handled: " + type);
		}
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	@Resource
	private LuceneContentSvc luceneContentSvc;
	@Resource
	private RealPathResolver realPathResolver;
}
