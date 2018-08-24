package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.SAmplePolicyApplyDao;
import com.anchorcms.icloud.model.commservice.SAmplePolicyApply;
import com.anchorcms.icloud.service.commservice.SAmplePolicyApplyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * Created by dxc on 2017/1/13 0013.
 *
 * 招标预告信息业务实现类
 */
@Service
@Transactional
public class SAmplePolicyApplyServiceImpl implements SAmplePolicyApplyService {

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/20 11:02
	 * @return
	 * @description保存SAmplePolicyApply及其相关表的信息
	 */
	public Content supplychain_save(SAmplePolicyApply sAmplePolicyApply, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean b, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs) {
		Content bean=saveContent(c, ext, t, channelId, typeId, null, true, user, b);
		// 主栏目也作为副栏目一并保存，方便查询，提高效率。

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
		//表里没有content暂时注释掉/
		sAmplePolicyApply.setContent(c);
		Channel channel = channelMng.findById(channelId);
		c.addToChannels(channel);
		SAmplePolicyApply sA=sAmplePolicyApplyDao.insert(sAmplePolicyApply);
		afterSave(c);
		return c;
	}

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/20 11:01
	 * @return
	 * @description编辑SAmplePolicyApply表中的信息
	 */
	public void update(SAmplePolicyApply sAmplePolicyApply, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b, String demandObjListJsonString) {
		//更新cms表内容
		Content nc=contentMng.update(c,ext,t,null,null,null,null,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,
				picDescs,null,channelId,typeId,null,charge,null,user,b);
		//更新需求内容
//		List<SDemandObj> sDemandObjList =convertJStringToList(demandObjListJsonString);
//		sDemand.setDemandObjList(sDemandObjList);
		sAmplePolicyApply.setContent(nc);
//		Updater<SAmplePolicyApply> updater = new Updater<SAmplePolicyApply>(sAmplePolicyApply);
//		sAmplePolicyApplyDao.updateByUpdater(updater);
		sAmplePolicyApplyDao.updatemc(sAmplePolicyApply);
	}

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/20 11:07
	 * @return
	 * @description更新政策申请的销售id功能
	 */
	public void updatesoldNoteIds(String soldNoteIds,Integer id) {
		sAmplePolicyApplyDao.updatesoldNoteIds(soldNoteIds,id);
	}

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/20 11:08
	 * @return
	 * @description
	 */
	public void updateSAmplePolicyApply(SAmplePolicyApply sAmplePolicyApply){
		Updater<SAmplePolicyApply> updater = new Updater<SAmplePolicyApply>(sAmplePolicyApply);
		sAmplePolicyApplyDao.updateByUpdater(updater);
	}


	/**
	 * @author dongxuecheng
	 * @Date 2017/1/7 10:55
	 * @return
	 * @description 获取分页以及SRepairRes表中的数据（惠补政策管理也没面）
	 */
	public Pagination getPageForMember(Integer siteId, Boolean isadmin, int pageNo, int pageSize, String inquiryTheme, Integer UserId, String status) {
		return sAmplePolicyApplyDao.getPageBySelf( siteId,  isadmin,  pageNo,  pageSize, inquiryTheme, UserId,status);
	}
	/**
	 * @author gengwenlong
	 * @Date 2017/2/24 16:49
	 * @return
	 * 惠补政策申请审核获取分页以及SAmplePolicyApply的数据
	 */
	public Pagination getPageForMember1(Integer siteId, Boolean isadmin, int pageNo, int pageSize, String inquiryTheme, Integer UserId, String status) {
		return sAmplePolicyApplyDao.getPageBySelf1( siteId,  isadmin,  pageNo,  pageSize, inquiryTheme, UserId,status);
	}

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/8 12:32
	 * @return
	 * @description惠补政策申请审核页面
	 */
	public Pagination getZxxqList(String repairName, Integer pageNo, Integer pageSize) {
		Pagination page = sAmplePolicyApplyDao.getZxxqList(repairName, pageNo, pageSize);
		return page;
	}


	/**
	 * @author dongxuecheng
	 * @Date 2017/1/16 9:00
	 * @return
	 * @description惠补政策审核功能（通过，驳回）
	 */
	public void setStatus(String amplePolicyApplyId, String status,String backReason) {
		sAmplePolicyApplyDao.setStatus(amplePolicyApplyId, status,backReason);
	}
	/**
	 * @author dongxuecheng
	 * @Date 2017/1/16 9:01
	 * @return
	 * @description惠补政策申请删除功能
	 */
	public void delete(String repairId) {
		sAmplePolicyApplyDao.delete(repairId);
	}

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/16 9:01
	 * @return
	 * @description惠补政策批量通过驳回功能
	 */
	public void setall(String repairId, String status,String backReason) {
		sAmplePolicyApplyDao.setRepairDemandstatus(repairId, status,backReason);
	}


	/**
	 * @author dongxuecheng
	 * @Date 2017/1/17 19:27
	 * @return
	 * @description惠补政策申请发布功能
	 */

	public void relece(Integer id){
		sAmplePolicyApplyDao.relece(id);
	}


	/**
	 * @author zhouyq
	 * @Date 2017/2/16
	 * @return
	 * @description 惠补政策撤回功能
	 */
	public void reback(Integer id){
		sAmplePolicyApplyDao.reback(id);
	}

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/16 9:02
	 * @return
	 * @description保存Content功能
	 */
	private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
								Integer channelId,Integer typeId, Boolean draft,
								Boolean contribute,CmsUser user, boolean forMember){
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

	private void afterDelete(Content content) {
		if (listenerList != null) {
			for (ContentListener listener : listenerList) {
				listener.afterDelete(content);
			}
		}
	}

	public Content deleteContentById(Integer id) {
		Content bean = contentDao.findById(id);
		// 执行监听器
		preDelete(bean);
		bean = contentDao.deleteById(id);
		// 执行监听器
		return bean;
	}
	private void preDelete(Content content) {
		if (listenerList != null) {
			for (ContentListener listener : listenerList) {
				listener.preDelete(content);
			}
		}
	}

	/**
	 * @Author dxc
	 * 删除需求信息
	 * @Date 2016/12/29 0029 10:55
	 */
	public SAmplePolicyApply  delete(Integer id){
		SAmplePolicyApply bean = sAmplePolicyApplyDao.findById(id);//获取能力实体类
		//表中还没有content暂时注释掉
		Content contentBean = deleteContentById(bean.getContent().getContentId());
		bean = sAmplePolicyApplyDao.delete(bean);// 执行删除
		afterDelete(contentBean);
		return bean;
	}

	/**
	 * @author dongxuecheng
	 * @Date 2017/1/16 9:02
	 * @return
	 * @description通过Id获取SAmplePolicyApply对象信息
	 */
	public SAmplePolicyApply findbyId(Integer sAmplePolicyApplyId) {
		return sAmplePolicyApplyDao.findById(sAmplePolicyApplyId);
	}




	@Resource
	private SAmplePolicyApplyDao sAmplePolicyApplyDao ;
	@Resource
	private ChannelMng channelMng;
	@Resource
	private ContentTypeMng contentTypeMng;
	@Resource
	private List<ContentListener> listenerList;
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
	@Resource
	private ContentMng contentMng;

}