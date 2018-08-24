package com.anchorcms.cms.service.assist.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.anchorcms.cms.dao.assist.CmsAccountDrawDao;
import com.anchorcms.cms.service.assist.CmsAccountDrawMng;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsAccountDraw;
import com.anchorcms.core.model.CmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CmsAccountDrawMngImpl implements CmsAccountDrawMng {
	
	public CmsAccountDraw draw(CmsUser user, Double amount, String applyAccount){
		CmsAccountDraw apply=new CmsAccountDraw();
		apply.setApplyAccount(applyAccount);
		apply.setApplyAmount(amount);
		apply.setApplyStatus(CmsAccountDraw.CHECKING);
		apply.setApplyTime((Timestamp) Calendar.getInstance().getTime());
		apply.setCmsUser(user);
		return save(apply);
	}
	
	public Double getAppliedSum(Integer userId){
		Short[]status={0,1};
		Double applyAmoutTotal=0d;
		List<CmsAccountDraw>list=dao.getList(userId, status, 1000);
		for(CmsAccountDraw d:list){
			applyAmoutTotal+=d.getApplyAmount();
		}
		return applyAmoutTotal;
	}
	
	@Transactional(readOnly = true)
	public Pagination getPage(Integer userId, Short applyStatus,
							  Date applyTimeBegin, Date applyTimeEnd, int pageNo, int pageSize) {
		Pagination page = dao.getPage(userId,applyStatus
				,applyTimeBegin,applyTimeEnd,pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public CmsAccountDraw findById(Integer id) {
		CmsAccountDraw entity = dao.findById(id);
		return entity;
	}

	public CmsAccountDraw save(CmsAccountDraw bean) {
		dao.save(bean);
		return bean;
	}

	public CmsAccountDraw update(CmsAccountDraw bean) {
		Updater<CmsAccountDraw> updater = new Updater<CmsAccountDraw>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsAccountDraw deleteById(Integer id) {
		CmsAccountDraw bean = dao.deleteById(id);
		return bean;
	}
	
	public CmsAccountDraw[] deleteByIds(Integer[] ids) {
		CmsAccountDraw[] beans = new CmsAccountDraw[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsAccountDrawDao dao;

	@Autowired
	public void setDao(CmsAccountDrawDao dao) {
		this.dao = dao;
	}
}