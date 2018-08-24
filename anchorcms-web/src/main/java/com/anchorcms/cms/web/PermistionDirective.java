package com.anchorcms.cms.web;

import com.anchorcms.common.web.freemarker.DirectiveUtils;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Map;

import static com.anchorcms.cms.web.AdminContextInterceptor.PERMISSION_MODEL;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-11-1
 * @Desc 后台管理员权限许可
 */
public class PermistionDirective implements TemplateDirectiveModel {
    /**
     * 此url必须和perm中url一致。
     */
    public static final String PARAM_URL = "url";

    @SuppressWarnings("unchecked")
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        String url = DirectiveUtils.getString(PARAM_URL, params);
        boolean pass = false;
        if (StringUtils.isBlank(url)) {
            // url为空，则认为有权限。
            pass = true;
        } else {
            TemplateSequenceModel perms = getPerms(env);
            if (perms == null) {
                // perms为null，则代表不需要判断权限。
                pass = true;
            } else {
                String perm;
                if(url.contains(":")){
                    url="/"+url.replace(":", "/");
                }
                for (int i = 0, len = perms.size(); i < len; i++) {
                    perm = ((TemplateScalarModel) perms.get(i)).getAsString();
                    if (url.startsWith(perm)||perm.startsWith(url)) {
                        pass = true;
                        break;
                    }
                    for(String u:url.split("@")){
                        if(perm.startsWith(u)){
                            pass = true;
                            break;
                        }
                    }
                }
            }
        }
        if (pass) {
            body.render(env.getOut());
        }
    }

    private TemplateSequenceModel getPerms(Environment env)
            throws TemplateModelException {
        TemplateModel model = env.getDataModel().get(PERMISSION_MODEL);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateSequenceModel) {
            return (TemplateSequenceModel) model;
        } else {
            throw new TemplateModelException(
                    "'perms' in data model not a TemplateSequenceModel");
        }

    }
}