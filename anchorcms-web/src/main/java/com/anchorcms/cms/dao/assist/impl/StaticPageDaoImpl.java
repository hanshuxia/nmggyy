package com.anchorcms.cms.dao.assist.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.anchorcms.cms.dao.StaticPageDao;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.CmsModel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.main.CmsKeywordMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.staticpage.DistributionThread;
import com.anchorcms.common.constants.Constants;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateSimpleDao;
import com.anchorcms.common.model.PageInfo;
import com.anchorcms.common.page.Paginable;
import com.anchorcms.common.page.SimplePage;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.utils.URLHelper;
import com.anchorcms.common.web.mvc.RealPathResolver;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.Ftp;
import com.anchorcms.core.service.FtpMng;
import com.anchorcms.icloud.service.staticpage.impl.StaticpageServiceImpl;
import com.anchorcms.icloud.service.staticpage.StaticpageService;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import static com.anchorcms.common.constants.Constants.UTF8;

@Repository
public class StaticPageDaoImpl extends HibernateSimpleDao implements
        StaticPageDao {
    public int channelStatic(Integer siteId, Integer channelId,
                             boolean containChild, Configuration conf, Map<String, Object> data)
            throws IOException, TemplateException {
        Finder finder = Finder.create("select bean from Channel bean");
        if (channelId != null) {
            if (containChild) {
                finder.append(",Channel parent where").append(
                        " bean.lft between parent.lft and parent.rgt").append(
                        " and parent.site.id=bean.site.id").append(
                        " and parent.id=:channelId");
                finder.setParam("channelId", channelId);
            } else {
                finder.append(" where bean.id=:channelId");
                finder.setParam("channelId", channelId);
            }
        } else if (siteId != null) {
            finder.append(" where bean.site.id=:siteId");
            finder.setParam("siteId", siteId);
        }
        Session session = getSession();
        ScrollableResults channels = finder.createQuery(session).setCacheMode(
                CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
        int count = 0;
        CmsSite site;
        Channel c;
        String filename;
        int quantity, totalPage;
        boolean mobileStaticSync = false;
        ExecutorService es = null;
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        while (channels.next()) {
            c = (Channel) channels.get(0);
            site = c.getSite();
            mobileStaticSync = site.getMobileStaticSync();
            FrontUtils.frontData(data, site, null, null, null);
            // 如果是外部链接或者不需要生产静态页，则不生成
            if (!StringUtils.isBlank(c.getLink()) || !c.getStaticChannel()) {
                continue;
            }
            // 没有内容或者有子栏目，则只生成一页
            int childs = childsOfChannel(c.getChannelId());
            if (!c.getModel().getHasContent()) {
                totalPage = 1;
            } else {
                if (c.getListChild()) {
                    quantity = childs;
                } else {
                    if (!c.getListChild() && childs > 0) {
                        quantity = contentsOfParentChannel(c.getChannelId());
                    } else {
                        quantity = contentsOfChannel(c.getChannelId());
                    }
                }
                if (quantity <= 0) {
                    totalPage = 1;
                } else {
                    totalPage = (quantity - 1) / c.getPageSize() + 1;
                }
            }
            //初始化线程池
            if (site.getPageIsSyncFtp() && es == null) {
                es = Executors.newFixedThreadPool(Constants.DISTRIBUTE_THREAD_COUNT);
            }
            for (int i = 1; i <= totalPage; i++) {
                filename = c.getStaticFilename(i);
                createChannelPage(es, site, conf, data, c, filename, i, false);
                //手机静态页页面
                if (mobileStaticSync) {
                    filename = c.getMobileStaticFilename(i);
                    createChannelPage(es, site, conf, data, c, filename, i, true);
                }
            }
            if (++count % 20 == 0) {
                session.clear();
            }
        }
        if (es != null) {
            es.shutdown();
        }
        return count;
    }


    public void channelStatic(Channel c, boolean firstOnly, Configuration conf,
                              Map<String, Object> data) throws IOException, TemplateException {
        // 如果是外部链接或者不需要生产静态页，则不生成
        if (!StringUtils.isBlank(c.getLink()) || !c.getStaticChannel()) {
            return;
        }
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        // 没有内容或者有子栏目，则只生成一页
        int childs = childsOfChannel(c.getChannelId());
        int quantity, totalPage;
        if (firstOnly || !c.getModel().getHasContent()
                || (!c.getListChild() && childs > 0)) {
            totalPage = 1;
        } else {
            if (c.getListChild()) {
                quantity = childs;
            } else {
                quantity = contentsOfChannel(c.getChannelId());
            }
            if (quantity <= 0) {
                totalPage = 1;
            } else {
                totalPage = (quantity - 1) / c.getPageSize() + 1;
            }
        }
        String filename;
        CmsSite site = c.getSite();
        boolean mobileStaticSync = site.getMobileStaticSync();
        FrontUtils.frontData(data, site, null, null, null);
        ExecutorService es = null;
        if (site.getPageIsSyncFtp()) {
            es = Executors.newFixedThreadPool(Constants.DISTRIBUTE_THREAD_COUNT);
        }
        for (int i = 1; i <= totalPage; i++) {
            filename = c.getStaticFilename(i);
            createChannelPage(es, site, conf, data, c, filename, i, false);
            //手机静态页页面
            if (mobileStaticSync) {
                filename = c.getMobileStaticFilename(i);
                createChannelPage(es, site, conf, data, c, filename, i, true);
            }
        }
        if (es != null) {
            es.shutdown();
        }
    }

    public int contentsOfChannel(Integer channelId) {
        String hql = "select count(*) from Content bean"
                + " join bean.channels as channel"
                + " where channel.id=:channelId and bean.status="
                + ContentCheck.CHECKED;
        Query query = getSession().createQuery(hql);
        query.setParameter("channelId", channelId);
        return ((Number) query.iterate().next()).intValue();
    }

    public int contentsOfParentChannel(Integer channelId) {
        String hql = "select count(*) from Content bean"
                + " join bean.channel channel,Channel parent"
                + "  where channel.lft between parent.lft and parent.rgt and channel.site.id=parent.site.id and parent.id=:parentId and bean.status="
                + ContentCheck.CHECKED;
        Query query = getSession().createQuery(hql);
        query.setParameter("parentId", channelId);
        return ((Number) query.iterate().next()).intValue();
    }

    public int childsOfChannel(Integer channelId) {
        String hql = "select count(*) from Channel bean"
                + " where bean.parent.id=:channelId";
        Query query = getSession().createQuery(hql);
        query.setParameter("channelId", channelId);
        return ((Number) query.iterate().next()).intValue();
    }

    public int contentStatic(Integer siteId, Integer channelId, Date start,
                             Configuration conf, StaticpageService staticpage, Map<String, Object> data) throws IOException,
            TemplateException {
        Finder f = Finder.create("select bean from Content bean");
        if (channelId != null) {
            f.append(" join bean.channel node,Channel parent");
            f.append(" where node.lft between parent.lft and parent.rgt");
            f.append(" and parent.id=:channelId");
            f.append(" and node.site.id=parent.site.id");
            f.setParam("channelId", channelId);
        } else if (siteId != null) {
            f.append(" where bean.site.id=:siteId");
            f.setParam("siteId", siteId);
        } else {
            f.append(" where 1=1");
        }
        if (start != null) {
            f.append(" and bean.sortDate>=:start");
            f.setParam("start", start);
        }
        f.append(" and ((bean.status=" + ContentCheck.CHECKED + ")");
        // 105 112 119 无需管理员审核，特殊处理
        f.append("     or (bean.status=" + ContentCheck.CONTRIBUTE + " and bean.channel.channelId in ( 105,112,119) ))");
        // isAdd 判断是否为增量生成
        if (data.get("isAdd") != null && "1".equals(data.get("isAdd").toString())) {
            f.append(" and bean.contentExt.needRegenerate = 1 ");
        }
        Session session = getSession();
        ScrollableResults contents = f.createQuery(session).setCacheMode(
                CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
        int count = 0;
        int totalPage;
        Content c;
        Channel chnl;
        CmsSite site;
        Template tpl, mobileTpl;
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        ExecutorService es = null;
        while (contents.next()) {
            c = (Content) contents.get(0);
            chnl = c.getChannel();
            // 如果是外部链接或者不生成静态页面，则不生成
            if (!StringUtils.isBlank(c.getLink()) || !chnl.getStaticContent()) {
                continue;
            }
            site = c.getSite();
            //初始化线程池-----无FTP
            if (site.getPageIsSyncFtp() && es == null) {
                es = Executors.newFixedThreadPool(Constants.DISTRIBUTE_THREAD_COUNT);
            }
            CmsModel model = modelMng.findById(c.getModel().getModelId());


            FrontUtils.frontData(data, site, null, null, null);
            data.put("content", c);
            data.put("channel", c.getChannel());
            totalPage = c.getPageCount();
            /*
             * 获取模板路径 同时获取相应的实体类数据 判断使用哪一个模板 内容页区分 （写死）
			 * 按 channelId 区分内容页 参考 LuceneContentSvcImpl.searchPage URL
			 */
            if (!staticpage.belong(c.getChannel().getChannelId())) continue;
            List<HashMap> conTplList = staticpage.tplList(c.getContentId(), c.getChannel().getChannelId(), site, data);
            if (conTplList == null || conTplList.size() == 0) continue;

            for (HashMap conTplMap : conTplList) {
                //获取模板类型  1 展示页 ，2 填写页
                tpl = conf.getTemplate(conTplMap.get("tpl").toString());
                String type = conTplMap.get("type").toString();
                data.put("conTplType", type);
                for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
                    createContentPage(es, data, tpl, false, c, pageNo);
                    //手机静态页页面----无移动端去掉
                    if (site.getMobileStaticSync()) {
                    }
                }
            }
            c.setNeedRegenerate(false);
            if (++count % 20 == 0) {
                session.flush();
                session.clear();
            }
        }
        if (es != null) {
            es.shutdown();
        }
        return count;
    }

    public boolean contentStatic(Content c, Configuration conf,
                                 Map<String, Object> data) throws IOException, TemplateException {
        // 如果是外部链接或者不生成静态页面，则不生成
        Channel chnl = c.getChannel();
        if (!StringUtils.isBlank(c.getLink()) || !chnl.getStaticContent()) {
            return false;
        }
        // 如果不需要生成静态页面，则不生成
        /* 是否需要生成静态页这里判断过于简单话，模板变换 站点名称等参数变换均需重新生成
        if(!c.getNeedRegenerate()){
			return false;
		}
		*/
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        int totalPage;
        CmsSite site;
        Template tpl, mobileTpl;
        site = c.getSite();
        CmsModel model = modelMng.findById(c.getModel().getModelId());
        tpl = conf.getTemplate(c.getTplContentOrDef(model));
        mobileTpl = conf.getTemplate(c.getMobileTplContentOrDef(model));
        FrontUtils.frontData(data, site, null, null, null);
        data.put("content", c);
        data.put("channel", chnl);
        totalPage = c.getPageCount();
        ExecutorService es = null;
        if (site.getPageIsSyncFtp()) {
            es = Executors.newFixedThreadPool(Constants.DISTRIBUTE_THREAD_COUNT);
        }
        for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
            createContentPage(es, data, tpl, false, c, pageNo);
            //手机静态页页面
            if (site.getMobileStaticSync()) {
                createContentPage(es, data, mobileTpl, true, c, pageNo);
            }
        }
        if (es != null) {
            es.shutdown();
        }
        c.setNeedRegenerate(false);
        return true;
    }

    private void createContentPage(ExecutorService es, Map<String, Object> data, Template tpl, boolean mobile, Content c, Integer pageNo) throws TemplateException, IOException {
        String url, real;
        File file, parent;
        CmsSite site;
        PageInfo info;
        Writer out = null;
        site = c.getSite();
        Ftp syncPageFtp = null;
        syncPageFtp = site.getSyncPageFtp();
        if (syncPageFtp != null) {
            syncPageFtp = ftpMng.findById(syncPageFtp.getFtpId());
        }
        String txt = c.getTxtByNo(pageNo);
        // 内容加上关键字
        txt = cmsKeywordMng.attachKeyword(site.getSiteId(), txt);
        Paginable pagination = new SimplePage(pageNo, 1, c.getPageCount());
        data.put("pagination", pagination);
        //获取模板类型 1 展示页 ，2 填写页 ，调用不同获取路径方法
        if ("2".equals(data.get("conTplType"))) {
            url = c.getMemberUrlStatic(pageNo);
        } else {
            url = c.getUrlStatic(pageNo);
        }
        info = URLHelper.getPageInfo(url.substring(url.lastIndexOf("/")),
                null);
        FrontUtils.putLocation(data, url);

        FrontUtils.frontPageData(pageNo, info.getHref(), info
                .getHrefFormer(), info.getHrefLatter(), data);
        data.put("title", c.getTitleByNo(pageNo));
        data.put("txt", txt);
        data.put("pic", c.getPictureByNo(pageNo));
        if (mobile) {
            real = realPathResolver.get(c.getMobileStaticFilename(pageNo));
        } else {
            //获取模板类型 1 展示页 ，2 填写页 ，调用不同获取路径方法
            if ("2".equals(data.get("conTplType"))) {
                real = realPathResolver.get(c.getMemberStaticFilename(pageNo));
            } else {
                real = realPathResolver.get(c.getStaticFilename(pageNo));
            }
        }
        file = new File(real);
        if (pageNo == 1) {
            parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
        }
        try {
            // FileWriter不能指定编码确实是个问题，只能用这个代替了。
            out = new OutputStreamWriter(new FileOutputStream(file), UTF8);
            tpl.process(data, out);
            log.info("create static file: {}", file.getAbsolutePath());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        String filename;
        if (mobile) {
            filename = c.getMobileStaticFilename(pageNo);
        } else {
            filename = c.getStaticFilename(pageNo);
        }
        if (es != null && syncPageFtp != null) {
            if (es.isTerminated()) {
                es = Executors.newFixedThreadPool(Constants.DISTRIBUTE_THREAD_COUNT);
            }
            es.execute(new DistributionThread(syncPageFtp, filename, new FileInputStream(file)));
        }
    }

    private void createChannelPage(ExecutorService es, CmsSite site, Configuration conf, Map<String, Object> data,
                                   Channel c, String filename, Integer page, boolean mobile) throws IOException, TemplateException {
        PageInfo info;
        Writer out = null;
        Template tpl;
        String real;
        File f, parent;
        real = realPathResolver.get(filename.toString());
        f = new File(real);
        parent = f.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (mobile) {
            tpl = conf.getTemplate(c.getMobileTplChannelOrDef());
        } else {
            tpl = conf.getTemplate(c.getTplChannelOrDef());
        }
        String urlStatic = c.getUrlStatic(page);
        info = URLHelper.getPageInfo(filename.substring(filename
                .lastIndexOf("/")), null);
        FrontUtils.frontPageData(page, info.getHref(), info
                .getHrefFormer(), info.getHrefLatter(), data);
        FrontUtils.putLocation(data, urlStatic);
        data.put("channel", c);
        Ftp syncPageFtp = null;
        syncPageFtp = site.getSyncPageFtp();
        if (syncPageFtp != null) {
            syncPageFtp = ftpMng.findById(syncPageFtp.getFtpId());
        }
        try {
            // FileWriter不能指定编码确实是个问题，只能用这个代替了。
            out = new OutputStreamWriter(new FileOutputStream(f), UTF8);
            tpl.process(data, out);
            log.info("create static file: {}", f.getAbsolutePath());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        if (es != null && syncPageFtp != null) {
            if (es.isTerminated()) {
                es = Executors.newFixedThreadPool(Constants.DISTRIBUTE_THREAD_COUNT);
            }
            es.execute(new DistributionThread(syncPageFtp, filename, new FileInputStream(f)));
        }
    }

    private CmsKeywordMng cmsKeywordMng;
    private RealPathResolver realPathResolver;
    @Resource
    private CmsModelMng modelMng;
    @Resource
    private FtpMng ftpMng;

    @Resource
    public void setCmsKeywordMng(CmsKeywordMng cmsKeywordMng) {
        this.cmsKeywordMng = cmsKeywordMng;
    }

    @Resource(name = "realPathResolver")
    public void setRealPathResolver(RealPathResolver realPathResolver) {
        this.realPathResolver = realPathResolver;
    }


}
