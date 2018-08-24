package com.anchorcms.cms.service.main.impl;

import com.anchorcms.cms.dao.main.ContentBuyDao;
import com.anchorcms.cms.model.main.ContentBuy;
import com.anchorcms.cms.model.main.ContentCharge;
import com.anchorcms.cms.service.main.ContentBuyMng;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class ContentBuyMngImpl implements ContentBuyMng {
	@Transactional(readOnly = true)
	public Pagination getPage(String orderNum, Integer buyUserId, Integer authorUserId,
							  Short payMode, int pageNo, int pageSize) {
		Pagination page = dao.getPage(orderNum,buyUserId,
				authorUserId,payMode,pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public Pagination getPageByContent(Integer contentId,
			Short payMode,int pageNo, int pageSize){
		return dao.getPageByContent(contentId,payMode,pageNo,pageSize);
	}

	@Transactional(readOnly = true)
	public ContentBuy findById(Long id) {
		ContentBuy entity = dao.findById(id);
		return entity;
	}
	
	@Transactional(readOnly = true)
	public ContentBuy findByOrderNumber(String orderNumber){
		return dao.findByOrderNumber(orderNumber);
	}
	
	@Transactional(readOnly = true)
	public boolean hasBuyContent(Integer buyUserId,Integer contentId){
		ContentBuy buy=dao.find(buyUserId, contentId);
		//用户已经购买并且是收费订单非打赏订单
		if(buy!=null&&buy.getUserHasPaid()&&buy.getChargeReward()== ContentCharge.MODEL_CHARGE){
			return true;
		}else{
			return false;
		}
	}

	public ContentBuy save(ContentBuy bean) {
		dao.save(bean);
		return bean;
	}

	public ContentBuy update(ContentBuy bean) {
		Updater<ContentBuy> updater = new Updater<ContentBuy>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public ContentBuy deleteById(Long id) {
		ContentBuy bean = dao.deleteById(id);
		return bean;
	}
	
	public ContentBuy[] deleteByIds(Long[] ids) {
		ContentBuy[] beans = new ContentBuy[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	@Resource
	private ContentBuyDao dao;
}