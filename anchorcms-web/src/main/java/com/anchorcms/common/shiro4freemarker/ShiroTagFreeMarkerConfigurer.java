package com.anchorcms.common.shiro4freemarker;

import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * Copyright @ 2017 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2017/2/4 0004
 * @Desc
 */
public class ShiroTagFreeMarkerConfigurer extends FreeMarkerConfigurer {

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
        this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }

}
