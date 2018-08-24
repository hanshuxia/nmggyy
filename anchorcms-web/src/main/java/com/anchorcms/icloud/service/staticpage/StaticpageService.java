package com.anchorcms.icloud.service.staticpage;

import com.anchorcms.core.model.CmsSite;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jxd on 2017/2/17.
 */
public interface StaticpageService {

    public Boolean belong(Integer channelId);

    public List<HashMap> tplList(Integer contentId, Integer channelId, CmsSite site, Map<String, Object> data);

}
