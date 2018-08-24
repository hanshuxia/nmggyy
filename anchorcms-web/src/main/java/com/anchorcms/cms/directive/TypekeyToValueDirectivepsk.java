package com.anchorcms.cms.directive;

import com.anchorcms.common.web.freemarker.DirectiveUtils;
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.service.common.PubCodeService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Copyright @ 2017 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2017/1/20 0020
 * @Desc 根据typecode，key取出分类的value，连带父
 */
@Component("s_typevaluepsk")
public class TypekeyToValueDirectivepsk implements TemplateDirectiveModel {
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        String tKey = DirectiveUtils.getString("tkey", params);
        String tCode = DirectiveUtils.getString("tcode", params);
        PubCode pc = pubCodeService.findUniqueCode("NLFL", tKey);
        Integer parentcode = pc.getParentCodeId();
        if (parentcode != null) {
            pc = pubCodeService.findById(parentcode);
            String pk = pc.getKey();
            Writer out = env.getOut();
            out.append(pk);
        }
    }

    @Resource
    private PubCodeService pubCodeService;
}
