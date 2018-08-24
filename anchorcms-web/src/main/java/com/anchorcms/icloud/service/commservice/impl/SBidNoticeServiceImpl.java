package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.icloud.dao.commservice.SBidNoticeDao;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.service.commservice.SBidNoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 中标公告信息业务实现类
 */
@Service
@Transactional
public class SBidNoticeServiceImpl implements SBidNoticeService {

	public SBidNotice findById(int id) {
		SBidNotice entity = dao.findById(id);
		return entity;
	}


	@Resource
	private SBidNoticeDao dao;
}