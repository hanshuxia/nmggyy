package com.anchorcms.cms.dao;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.service.staticpage.StaticpageService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public interface StaticPageDao {

    public int channelStatic(Integer siteId, Integer channelId,
                             boolean containChild, Configuration conf, Map<String, Object> data)
            throws IOException, TemplateException;

    public void channelStatic(Channel c, boolean firstOnly, Configuration conf,
                              Map<String, Object> data) throws IOException, TemplateException;

    public int contentStatic(Integer siteId, Integer channelId, Date start,
                             Configuration conf, StaticpageService staticpage, Map<String, Object> data) throws IOException,
            TemplateException;

    public boolean contentStatic(Content c, Configuration conf,
                                 Map<String, Object> data) throws IOException, TemplateException;

    public int contentsOfChannel(Integer channelId);

    public int childsOfChannel(Integer channelId);
}