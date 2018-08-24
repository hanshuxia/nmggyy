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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright @ 2017 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2017/1/20 0020
 * @Desc 根据typecode，key取出分类的value，连带父
 */
@Component("s_typevalue")
public class TypekeyToValueDirective implements TemplateDirectiveModel {
    public static final String PARAM_TYPECODE = "typecode";
    public static final String PARAM_TYPEKEY = "typekey";
    public static final String PARAM_JOIN = "join";
    public static final String PARAM_ISFULL = "isfull";
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        String tCode = DirectiveUtils.getString(PARAM_TYPECODE, params);
        String tKey = DirectiveUtils.getString(PARAM_TYPEKEY, params);
        String joinStr = DirectiveUtils.getString(PARAM_JOIN, params);
        String isfull = DirectiveUtils.getString(PARAM_ISFULL, params);

        List<PubCode> pubCodesList= new ArrayList<PubCode>();
        Writer out = env.getOut();

        if(joinStr==null){
            joinStr = "-";
        }

        if(isfull == null){
            isfull = "0";
        }

        if(tKey!=null && tCode!=null){
            PubCode pc = pubCodeService.findUniqueCode(tCode,tKey);
            if(pc != null) {
                pubCodesList.add(pc);
                int whileCount=0;
                //限制级联层数，防止死循环
                if("1".equals(isfull) && whileCount<10) {
                    while (pc.getParentCodeId() != null) {
                        pc = pubCodeService.findById(pc.getParentCodeId());
                        pubCodesList.add(pc);
                        whileCount++;
                    }
                }
            }
        }

        if(pubCodesList.size()>0){
            int len = pubCodesList.size();
            if("1".equals(isfull)) {
                StringBuffer value = new StringBuffer("");
                for (int i = len - 1; i >= 0; i--) {
                    if(i==10){
                        value.append("(overflow)..");
                    }
                    value.append(pubCodesList.get(i).getValue());
                    if (i > 0) {
                        value.append(joinStr);
                    }
                }
                out.append(value);
            }else{
                out.append(pubCodesList.get(0).getValue());
            }
        }
    }

    @Resource
    private PubCodeService pubCodeService;
}
