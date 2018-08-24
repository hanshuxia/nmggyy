package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.icloud.dao.commservice.SCompanyDao;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.service.commservice.SCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hansx on 2017/1/13 0013.
 *
 * 企业信息业务实现类
 */
@Service
@Transactional
public class SCompanyServiceImpl implements SCompanyService {

	public SCompany findById(String id) {
		SCompany entity = dao.findById(id);
		return entity;
	}

	public int updateById(String id,String isRecommend) {

		if(isRecommend!=null &&!isRecommend.equals("")&&isRecommend.equals("1")){//已推荐
			isRecommend="0";//改为未推荐
		}else{//未推荐
			isRecommend="1";//改为已推荐
		}
		return dao.updateStatusById(id,isRecommend);
	}

	public int recommendByIds(String ids) {
		if(',' == (ids.charAt(ids.length()-1))){
			ids = ids.substring(0,ids.length()-1);
		}
		return dao.recommendMany(ids);
	}

	public int revoke(String ids) {
		if(',' == (ids.charAt(ids.length()-1))){
			ids = ids.substring(0,ids.length()-1);
		}
		return dao.revokeMany(ids);
	}

	public int revokeAll(String ids, String backReason, String relstatus) {
		if(',' == (ids.charAt(ids.length()-1))){
			ids = ids.substring(0,ids.length()-1);
		}
		return dao.revokeAll(ids,backReason,relstatus);
	}

	public SCompany Update(SCompany sCompany) {
		return dao.update(sCompany);
	}


	@Resource
	private SCompanyDao dao;
}