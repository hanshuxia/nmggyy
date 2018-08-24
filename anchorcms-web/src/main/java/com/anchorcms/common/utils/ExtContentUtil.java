package com.anchorcms.common.utils;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.ImageSvc;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.image.ImageUtils;
import com.anchorcms.common.web.RequestUtils;
import com.anchorcms.common.web.mvc.MessageResolver;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 扩展内容使用的工具
 * Created by smt on 2016/12/6.
 */
public class ExtContentUtil {
    public static Object[] PreSave(Content bean, ContentExt ext, ContentTxt txt,
                                    Boolean copyimg,Integer channelId, String tagStr,
                                    HttpServletRequest request, ImageSvc imageSvc,ChannelMng channelMng,WebErrors errors){
        ValidateSave(bean, channelId, request,channelMng,errors);
        if (errors.hasErrors()) {
            return null;
        }
        // 加上模板前缀
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String tplPath = site.getTplPath();
        if (!StringUtils.isBlank(ext.getTplContent())) {
            ext.setTplContent(tplPath + ext.getTplContent());
        }
        if (!StringUtils.isBlank(ext.getTplMobileContent())) {
            ext.setTplMobileContent(tplPath + ext.getTplMobileContent());
        }
        bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
        String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", MessageResolver.getMessage(request, "content.tagStr.split"));
        if(txt!=null&&copyimg!=null&&copyimg){
            ExtContentUtil.CopyContentTxtImg(txt, site,imageSvc);
        }
        return new Object[]{user,tagArr};
    }

    public static void AfterSave(Content bean, ContentExt ext, String[] attachmentPaths, String[] picPaths,Integer cid, CmsFileMng fileMng, ModelMap model){
        //处理附件
        fileMng.updateFileByPaths(attachmentPaths,picPaths,ext.getMediaPath(),ext.getTitleImg(),ext.getTypeImg(),ext.getContentImg(),true,bean);

        if (cid != null) {
            model.addAttribute("cid", cid);
        }
        model.addAttribute("message", "global.success");
    }

    public static void CopyContentTxtImg(ContentTxt txt, CmsSite site, ImageSvc imageSvc){
        if(StringUtils.isNotBlank(txt.getTxt())){
            txt.setTxt(CopyTxtHmtlImg(txt.getTxt(), site,imageSvc));
        }
        if(StringUtils.isNotBlank(txt.getTxt1())){
            txt.setTxt1(CopyTxtHmtlImg(txt.getTxt1(), site,imageSvc));
        }
        if(StringUtils.isNotBlank(txt.getTxt2())){
            txt.setTxt2(CopyTxtHmtlImg(txt.getTxt2(), site,imageSvc));
        }
        if(StringUtils.isNotBlank(txt.getTxt3())){
            txt.setTxt3(CopyTxtHmtlImg(txt.getTxt3(), site,imageSvc));
        }
    }

    private static String CopyTxtHmtlImg(String txtHtml, CmsSite site, ImageSvc imageSvc){
        List<String> imgUrls= ImageUtils.getImageSrc(txtHtml);
        for(String img:imgUrls){
            txtHtml=txtHtml.replace(img, imageSvc.crawlImg(img,site));
        }
        return txtHtml;
    }

    public static WebErrors ValidateSave(Content bean, Integer channelId,
                                         HttpServletRequest request, ChannelMng channelMng,WebErrors errors) {
        CmsSite site = CmsUtils.getSite(request);
        bean.setSite(site);
        if (errors.ifNull(channelId, "channelId")) {
            return errors;
        }
        Channel channel = channelMng.findById(channelId);
        if (errors.ifNotExist(channel, Channel.class, channelId)) {
            return errors;
        }
        if (channel.getChild().size() > 0) {
            errors.addErrorCode("content.error.notLeafChannel");
        }
        //所选发布内容模型不在栏目模型范围内
        if(bean.getModel().getModelId()!=null){
            CmsModel m=bean.getModel();
            if(errors.ifNotExist(m, CmsModel.class, bean.getModel().getModelId())){
                return errors;
            }
            //默认没有配置的情况下modelIds为空 则允许添加
            if(channel.getModelIds().size()>0&&!channel.getModelIds().contains(bean.getModel().getModelId().toString())){
                errors.addErrorCode("channel.modelError", channel.getName(),m.getModelName());
            }
        }
        return errors;
    }
    public static boolean vldExist(Integer id, Integer siteId, WebErrors errors,ContentMng manager) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        Content entity = manager.findById(id);
        if (errors.ifNotExist(entity, Content.class, id)) {
            return true;
        }
        if (!entity.getSite().getSiteId().equals(siteId)) {
            errors.notInSite(Content.class, id);
            return true;
        }
        return false;
    }


    public static WebErrors validateUpdate(Integer id, HttpServletRequest request,ContentMng manager)
    {
        WebErrors errors = WebErrors.create(request);
        CmsSite site = CmsUtils.getSite(request);
        if (vldExist(id, site.getSiteId(), errors,manager)) {
            return errors;
        }
        Content content = manager.findById(id);
        // TODO 是否有编辑的数据权限。
        // 是否有审核后更新权限。
        if (!content.isHasUpdateRight()) {
            errors.addErrorCode("content.error.afterCheckUpdate");
            return errors;
        }
        return errors;
    }

}
