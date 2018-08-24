package com.anchorcms.icloud.dao.supplychain.impl;


import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.supplychain.SSpareDetailDao;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareStorage;
import org.springframework.stereotype.Repository;

/**
 * Created by hansx on 2016/12/20.
 * 备品备件Dao实现类
 */
@Repository
public class SSpareDetailDaoImpl extends
		HibernateBaseDao<SSpare, String> implements SSpareDetailDao {

	/**
	 * 根据id获取备品备件信息
	 * @param id
	 * @return
     */
		public SSpare findById(String id) {
		SSpare entity =get(id);
		return entity;
	}


	/**
	 * @author hansx 2016/6/9
	 * @return 库存记录保存
	 */
	public void save(SSpareStorage sSpareStorage) {
		getSession().save(sSpareStorage);
	}

	public void updateInvnetCount (SSpare sSpare){
		getSession().update(sSpare);
	}

	@Override
	protected Class<SSpare> getEntityClass() {
		return SSpare.class;
	}
}