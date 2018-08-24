package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.STenderNoticeDao;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.service.commservice.STenderNoticeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 招标公告信息业务实现类
 */
@Service
@Transactional
public class STenderNoticeServiceImpl implements STenderNoticeService {

	public STenderNotice findById(int id) {
		STenderNotice entity = dao.findById(id);
		return entity;
	}
	/**
	 * @author gengwenlong
	 * @Date 2017/1/14 23:27
	 * @return
	 * 发布招标公告
	 */
	public Content tenderNoticeRelese(STenderNotice stn, HttpServletRequest request, Content c, ContentExt ext, ContentTxt t, String nextUrl, Integer modelId,
									  Integer channelId, String textarea1, CmsUser user, Short charge, Integer typeId, boolean forMember,
									  HttpServletResponse response, ModelMap model, String[] attachmentPaths, String[] attachmentNames,
									  String[] attachmentFilenames, String[] picPaths, String[] picDescs)
	{
		if(charge==null){
			charge= ContentCharge.MODEL_FREE;
		}
		saveContent(c, ext, t, channelId, typeId, null,true,user, forMember);
		// 主栏目也作为副栏目一并保存，方便查询，提高效率。
		Channel channel=channelMng.findById(channelId);
		c.addToChannels(channel);
		// 保存附件
		if (attachmentPaths != null && attachmentPaths.length > 0) {
			for (int i = 0, len = attachmentPaths.length; i < len; i++) {
				if (!StringUtils.isBlank(attachmentPaths[i])) {
					c.addToAttachmemts(attachmentPaths[i],
							attachmentNames[i], attachmentFilenames[i]);
				}
			}
		}
		// 保存图片集
		if (picPaths != null && picPaths.length > 0) {
			for (int i = 0, len = picPaths.length; i < len; i++) {
				if (!StringUtils.isBlank(picPaths[i])) {
					c.addToPictures(picPaths[i], picDescs[i]);
				}
			}
		}
		stn.setContent(c);
		/*stn.setTenderTrailerId(c.getContentId());*/
		dao.tenderNoticeRelese(stn);
		afterSave(c);
		return c;
	}

	private void preSave(Content content) {
		if (listenerList != null) {
			for (ContentListener listener : listenerList) {
				listener.preSave(content);
			}
		}
	}
	private void afterSave(Content content) {
		if (listenerList != null) {
			for (ContentListener listener : listenerList) {
				listener.afterSave(content);
			}
		}
	}
	private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
								Integer channelId, Integer typeId, Boolean draft,
								Boolean contribute, CmsUser user, boolean forMember){
		Channel channel = channelMng.findById(channelId);
		bean.setChannel(channel);
		bean.setType(contentTypeMng.findById(typeId));
		bean.setUser(user);
		Byte userStep;
		if (forMember) {
			// 会员的审核级别按0处理
			userStep = 0;
		} else {
			CmsSite site = bean.getSite();
			userStep = user.getCheckStep(site.getSiteId());
		}
		// 流程处理
		if(contribute!=null&&contribute){
			bean.setStatus(ContentCheck.CONTRIBUTE);
		}else if (draft != null && draft) {
			// 草稿
			bean.setStatus(DRAFT);
		} else {
			if (userStep >= bean.getChannel().getFinalStepExtends()) {
				bean.setStatus(ContentCheck.CHECKED);
			} else {
				bean.setStatus(ContentCheck.CHECKING);
			}
		}
//暂时在新增时将content审核状态改为通过来生成全文检索索引
		bean.setStatus(ContentCheck.CHECKED);
		// 是否有标题图
		bean.setHasTitleImg(!StringUtils.isBlank(ext.getTitleImg()));
		bean.init();
		// 执行监听器
		preSave(bean);
		contentDao.save(bean);
		contentExtMng.save(ext, bean);
		contentTxtMng.save(txt, bean);
		ContentCheck check = new ContentCheck();
		check.setCheckStep(userStep);
		contentCheckMng.save(check, bean);
		contentCountMng.save(new ContentCount(), bean);
		return bean;
	}

	@Resource
	private STenderNoticeDao dao;
	@Resource
	private List<ContentListener> listenerList;
	@Resource
	private ContentTypeMng contentTypeMng;
	@Resource
	private ChannelMng channelMng;
	@Resource
	private ContentTxtMng contentTxtMng;
	@Resource
	private ContentExtMng contentExtMng;
	@Resource
	private ContentCountMng contentCountMng;
	@Resource
	private ContentCheckMng contentCheckMng;
	@Resource
	private ContentDao contentDao;
}