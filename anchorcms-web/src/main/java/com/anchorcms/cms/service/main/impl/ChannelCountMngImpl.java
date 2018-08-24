package com.anchorcms.cms.service.main.impl;

import java.util.Calendar;

import com.anchorcms.cms.dao.main.ChannelCountDao;
import com.anchorcms.cms.service.main.ChannelCountMng;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.ChannelCount;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.service.CmsConfigMng;
import com.anchorcms.core.model.CmsConfig;
import net.sf.ehcache.Ehcache;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class ChannelCountMngImpl implements ChannelCountMng {
	public int freshCacheToDB(Ehcache cache) {
		CmsConfig config = cmsConfigMng.get();
		clearCount(config);
		int count = dao.freshCacheToDB(cache);
		return count;
	}

	private int clearCount(CmsConfig config) {
		Calendar curr = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		last.setTime(config.getCountClearTime());
		int currDay = curr.get(Calendar.DAY_OF_YEAR);
		int lastDay = last.get(Calendar.DAY_OF_YEAR);
		if (currDay != lastDay) {
			int currWeek = curr.get(Calendar.WEEK_OF_YEAR);
			int lastWeek = last.get(Calendar.WEEK_OF_YEAR);
			int currMonth = curr.get(Calendar.MONTH);
			int lastMonth = last.get(Calendar.MONTH);
			//清除时间不更新也可以，在内容计数器中已经更新过
			cmsConfigMng.updateCountClearTime(curr.getTime());
			return dao.clearCount(currWeek != lastWeek, currMonth != lastMonth);
		} else {
			return 0;
		}
	}

	@Transactional(readOnly = true)
	public ChannelCount findById(Integer id) {
		ChannelCount entity = dao.findById(id);
		return entity;
	}

	public ChannelCount save(ChannelCount count, Channel channel) {
		count.setChannel(channel);
		count.init();
		dao.save(count);
		channel.setChannelCount(count);
		return count;
	}

	public void afterSaveContent(Channel channel) {
		ChannelCount c=channel.getChannelCount();
		CmsConfig config = cmsConfigMng.get();
		clearContentCount(config);
		c.setContentCountDay(c.getContentCountDay()+1);
		c.setContentCountMonth(c.getContentCountMonth()+1);
		c.setContentCountWeek(c.getContentCountWeek()+1);
		c.setContentCountYear(c.getContentCountYear()+1);
		c.setContentCountTotal(c.getContentCountTotal()+1);
		update(c);
		if(channel.getParent()!=null){
			afterSaveContent(channel.getParent());
		}
	}
	
	public void afterDelContent(Channel channel) {
		ChannelCount c=channel.getChannelCount();
		CmsConfig config = cmsConfigMng.get();
		clearContentCount(config);
		c.setContentCountDay(c.getContentCountDay()-1);
		c.setContentCountMonth(c.getContentCountMonth()-1);
		c.setContentCountWeek(c.getContentCountWeek()-1);
		c.setContentCountYear(c.getContentCountYear()-1);
		c.setContentCountTotal(c.getContentCountTotal()-1);
		if(c.getContentCountDay()<0){
			c.setContentCountDay(0);
		}
		if(c.getContentCountWeek()<0){
			c.setContentCountWeek(0);
		}
		if(c.getContentCountMonth()<0){
			c.setContentCountMonth(0);
		}
		if(c.getContentCountYear()<0){
			c.setContentCountYear(0);
		}
		if(c.getContentCountTotal()<0){
			c.setContentCountTotal(0);
		}
		update(c);
		if(channel.getParent()!=null){
			afterDelContent(channel.getParent());
		}
	}
	
	private int clearContentCount(CmsConfig config) {
		Calendar curr = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		last.setTime(config.getChannelCountClearTime());
		int currDay = curr.get(Calendar.DAY_OF_YEAR);
		int lastDay = last.get(Calendar.DAY_OF_YEAR);
		if (currDay != lastDay) {
			int currWeek = curr.get(Calendar.WEEK_OF_YEAR);
			int lastWeek = last.get(Calendar.WEEK_OF_YEAR);
			int currMonth = curr.get(Calendar.MONTH);
			int lastMonth = last.get(Calendar.MONTH);
			int currYear = curr.get(Calendar.YEAR);
			int lastYear = last.get(Calendar.YEAR);
			cmsConfigMng.updateChannelCountClearTime(curr.getTime());
			return dao.clearContentCount(currDay!=lastDay,currWeek != lastWeek,currMonth != lastMonth,currYear != lastYear);
		} else {
			return 0;
		}
	}
	
	public ChannelCount update(ChannelCount bean) {
		Updater<ChannelCount> updater = new Updater<ChannelCount>(bean);
		ChannelCount entity = dao.updateByUpdater(updater);
		return entity;
	}
	@Resource
	private CmsConfigMng cmsConfigMng;
	@Resource
	private ChannelCountDao dao;

}