package com.anchorcms.cms.directive;

import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.anchorcms.common.web.freemarker.DirectiveUtils;
import com.anchorcms.common.web.freemarker.ParamsRequiredException;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.icloud.model.synergy.SCompanyDevice;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.anchorcms.common.constants.Constants.*;
import static com.anchorcms.common.utils.FrontUtils.PARAM_STYLE_LIST;
import static com.anchorcms.common.web.freemarker.DirectiveUtils.OUT_LIST;


/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/1/10
 * @Desc 企业能力展示荣誉资质自定义标签
 */
@Component("s_synergy_device_list")
public class SynergyCompanyDeviceListDirective implements TemplateDirectiveModel {

    public static final String TPL_NAME = "content_list";

    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        CmsSite site = FrontUtils.getSite(env);
        Integer count = DirectiveUtils.getInt("count", params);
        Integer orderBy = DirectiveUtils.getInt("orderBy", params);
        Integer listType = DirectiveUtils.getInt("type", params);

        List<SCompanyDevice> list = synergyCompanyService.getDeviceList(count, orderBy, listType);
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
    private SynergyCompanyService synergyCompanyService;

}
