package com.anchorcms.cms.service.assist.impl;

import java.sql.Timestamp;
import java.util.List;

import com.anchorcms.cms.dao.assist.CmsGuestbookDao;
import com.anchorcms.cms.model.assist.CmsGuestbook;
import com.anchorcms.cms.model.assist.CmsGuestbookExt;
import com.anchorcms.cms.service.assist.CmsGuestbookCtgMng;
import com.anchorcms.cms.service.assist.CmsGuestbookExtMng;
import com.anchorcms.cms.service.assist.CmsGuestbookMng;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.service.CmsSiteMng;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CmsGuestbookMngImpl implements CmsGuestbookMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer ctgId, Integer ctgIds[],
							  Integer userId, Boolean recommend, Boolean checked,
							  boolean desc, boolean cacheable, int pageNo, int pageSize) {
		return dao.getPage(siteId, ctgId,ctgIds,userId, recommend, checked, desc, cacheable,
				pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public List<CmsGuestbook> getList(Integer siteId, Integer ctgId,
									  Boolean recommend, Boolean checked, boolean desc,
									  boolean cacheable, int first, int max) {
		return dao.getList(siteId, ctgId, recommend, checked, desc, cacheable,
				first, max);
	}

	@Transactional(readOnly = true)
	public CmsGuestbook findById(Integer id) {
		CmsGuestbook entity = dao.findById(id);
		return entity;
	}

	public CmsGuestbook save(CmsGuestbook bean, CmsGuestbookExt ext,
			Integer ctgId, String ip) {
		bean.setCtg(cmsGuestbookCtgMng.findById(ctgId));
		bean.setIp(ip);
		bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
		bean.init();
		dao.save(bean);
		cmsGuestbookExtMng.save(ext, bean);
		return bean;
	}

	public CmsGuestbook save(CmsUser member, Integer siteId, Integer ctgId,
			String ip, String title, String content, String email,
			String phone, String qq) {
		CmsGuestbook guestbook = new CmsGuestbook();
		guestbook.setMember(member);
		guestbook.setSite(cmsSiteMng.findById(siteId));
		guestbook.setIp(ip);
		CmsGuestbookExt ext = new CmsGuestbookExt();
		ext.setTitle(title);
		ext.setContent(content);
		ext.setEmail(email);
		ext.setPhone(phone);
		ext.setQq(qq);
		return save(guestbook, ext, ctgId, ip);
	}

	public CmsGuestbook update(CmsGuestbook bean, CmsGuestbookExt ext,
			Integer ctgId) {
		Updater<CmsGuestbook> updater = new Updater<CmsGuestbook>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setCtg(cmsGuestbookCtgMng.findById(ctgId));
		cmsGuestbookExtMng.update(ext);
		return bean;
	}

	public CmsGuestbook deleteById(Integer id) {
		CmsGuestbook bean = dao.deleteById(id);
		return bean;
	}

	public CmsGuestbook[] deleteByIds(Integer[] ids) {
		CmsGuestbook[] beans = new CmsGuestbook[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	public CmsGuestbook[] checkByIds(Integer[] ids,CmsUser checkUser,Boolean checkStatus) {
		CmsGuestbook[] beans = new CmsGuestbook[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = checkById(ids[i],checkUser,checkStatus);
		}
		return beans;
	}
	
	private CmsGuestbook checkById(Integer id, CmsUser checkUser, Boolean checkStatus){
		CmsGuestbook bean=findById(id);
		Updater<CmsGuestbook> updater = new Updater<CmsGuestbook>(bean);
		bean = dao.updateByUpdater(updater);
		if(checkStatus){
			bean.setAdmin(checkUser);
		}
		bean.setIsChecked(checkStatus);
		return bean;
	}
	

	@Resource
	private CmsGuestbookCtgMng cmsGuestbookCtgMng;
	@Resource
	private CmsGuestbookExtMng cmsGuestbookExtMng;
	@Resource
	private CmsSiteMng cmsSiteMng;
	@Resource
	private CmsGuestbookDao dao;

}