package com.anchorcms.cms.directive;

import com.anchorcms.cms.directive.abs.AbstractExtContentDirective;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.anchorcms.common.web.freemarker.DirectiveUtils;
import com.anchorcms.common.web.freemarker.ParamsRequiredException;
import com.anchorcms.core.model.CmsSite;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateSequenceModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.anchorcms.common.constants.Constants.*;
import static com.anchorcms.common.utils.FrontUtils.PARAM_STYLE_LIST;
import static com.anchorcms.common.web.freemarker.DirectiveUtils.OUT_LIST;
import static com.anchorcms.common.web.freemarker.DirectiveUtils.OUT_PAGINATION;

/**
 * Created by 11239 on 2016/12/18.
 */
@Component("ext_content_page")
public class ExtContentPageDirective extends AbstractExtContentDirective {

    /**
     * 模板名称
     */

    public static final String TPL_NAME = "content_page";

    /**
     * 输入参数，文章ID。允许多个文章ID，用","分开。排斥其他所有筛选参数。
     */
    public static final String PARAM_IDS = "ids";

    public static final String PARAM_MODEL_PATH = "modelPath";

    public static final String PARAM_QUERY = "q";
    @SuppressWarnings("unchecked")
        public void execute(Environment env, Map params, TemplateModel[] loopVars,
                TemplateDirectiveBody body) throws TemplateException, IOException {
            String modelPath = DirectiveUtils.getString(PARAM_MODEL_PATH, params);
            if(modelPath==null){
                throw new ParamsRequiredException(PARAM_MODEL_PATH);
            }
            TemplateSequenceModel model = (TemplateSequenceModel)params.get(PARAM_QUERY);
            String [] queryParams = null;
            if(model==null){
                queryParams = new String[0];
            }else {
                queryParams= new String[model.size()];
                for(int i=0;i<model.size();i++){
                    if(model.get(i)!=null) {
                        queryParams[i] = DirectiveUtils.convertToString(PARAM_QUERY + i, model.get(i));
                    }else{
                        queryParams[i]=null;
                    }
                }
            }
            CmsSite site = FrontUtils.getSite(env);
            Pagination page = getPage(params, env,queryParams,modelPath);

            Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
                    params);
            paramWrap.put(OUT_PAGINATION, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(page));
            paramWrap.put(OUT_LIST, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(page.getList()));
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

        protected Pagination getPage(Map<String, TemplateModel> params,
                                     Environment env, String[] queryParams, String modelPath) throws TemplateException {
            Integer[] ids = DirectiveUtils.getIntArray(PARAM_IDS, params);
            if (ids != null) {
                int count = FrontUtils.getCount(params);
                int pageNo = FrontUtils.getPageNo(env);
                return (Pagination)extContentService.getPageByIdsForTag(ids, getOrderBy(params),pageNo, count,queryParams,modelPath);
                //
            } else {
                return (Pagination) super.getData(params, env,queryParams,modelPath);
            }
        }
    protected boolean isPage() {
        return true;
    }
}
