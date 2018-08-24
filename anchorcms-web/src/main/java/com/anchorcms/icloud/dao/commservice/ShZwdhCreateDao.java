package com.anchorcms.icloud.dao.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.commservice.SStiteManager;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
	/**
		 * @author suhe
		 * @Date 2017/2/22 0022 上午 9:11
		 * @return
		 * 政务导航
		 */
public interface ShZwdhCreateDao {
//    public Pagination getPageBySelf(String status);

		List ZwdhManagelist(String status);
}
