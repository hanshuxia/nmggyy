package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.icloud.dao.commservice.SAmplePolicyDao;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import com.anchorcms.icloud.service.commservice.SAmplePolicyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 惠补政策业务实现类
 */
@Service
@Transactional
public class SAmplePolicyServiceImpl implements SAmplePolicyService {

	public SAmplePolicy findById(int id) {
		SAmplePolicy entity = dao.findById(id);
		return entity;
	}

	public List<SAmplePolicy> getSpareDemandResList(Integer count, Integer orderBy, Integer listType){
		return dao.getSpareDemandResList(count, orderBy, listType);
	}

	@Resource
	private SAmplePolicyDao dao;
}