package com.anchorcms.icloud.service.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Hansx on 2017/1/13 0013.
 *
 *  招标公告信息
 */
public interface STenderNoticeService {

    /**
     * @author hansx
     * @Date 2017/1/13 0013 上午 11:30
     * @return
     *  根据id获取招标公告信息
     */
    public STenderNotice findById(int id);
    /**
     * @author gengwenlong
     * @Date 2017/1/14 23:25
     * @return
     * 发布招标公告
     */
    public Content tenderNoticeRelese(STenderNotice stn, HttpServletRequest request, Content c, ContentExt ext, ContentTxt t, String nextUrl, Integer modelId,
                                      Integer channelId, String textarea1, CmsUser user, Short charge, Integer typeId, boolean forMember,
                                      HttpServletResponse response, ModelMap model, String[] attachmentPaths, String[] attachmentNames,
                                      String[] attachmentFilenames, String[] picPaths, String[] picDescs
    );

}
