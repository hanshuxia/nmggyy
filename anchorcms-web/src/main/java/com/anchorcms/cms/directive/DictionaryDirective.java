package com.anchorcms.cms.directive;

import com.anchorcms.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.anchorcms.common.web.freemarker.DirectiveUtils;
import com.anchorcms.common.web.freemarker.ParamsRequiredException;
import com.anchorcms.core.model.CmsDictionary;
import com.anchorcms.core.service.CmsDictionaryMng;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.anchorcms.common.web.freemarker.DirectiveUtils.OUT_BEAN;

/**
 * Created by smt on 2016/12/9.
 */
@Component("dic_name")
public class DictionaryDirective implements TemplateDirectiveModel {
    /**
     * 字典 值。
     */
    public static final String PARAM_VALUE = "value";
    /**
     * 字典 类型。
     */
    public static final String PARAM_TYPE = "type";
    @Override
    public void execute(Environment env, Map map, TemplateModel[] templateModels, TemplateDirectiveBody body) throws TemplateException, IOException {
        String type =getType(map);
        String value = getValue(map);
        CmsDictionary dic = dictionaryMng.findByValue(type,value);
        if(dic==null){
            throw new ParamsRequiredException("dic not exist");
        }
        Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(map);
        paramWrap.put(OUT_BEAN, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(dic));
        Map<String, TemplateModel> origMap = DirectiveUtils
                .addParamsToVariable(env, paramWrap);
        body.render(env.getOut());
        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
    }

    private String getType(Map<String, TemplateModel> params)
            throws TemplateException {
        String type = DirectiveUtils.getString(PARAM_TYPE, params);
        if (type != null) {
            return type;
        } else {
            throw new ParamsRequiredException(PARAM_TYPE);
        }
    }

    private String getValue(Map<String, TemplateModel> params)
            throws TemplateException {
        String type = DirectiveUtils.getString(PARAM_VALUE, params);
        if (type != null) {
            return type;
        } else {
            throw new ParamsRequiredException(PARAM_VALUE);
        }
    }

    @Resource
    CmsDictionaryMng dictionaryMng;
}
