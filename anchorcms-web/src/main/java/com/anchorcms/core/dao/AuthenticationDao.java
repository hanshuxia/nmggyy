package com.anchorcms.core.dao;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.Authentication;

import java.util.Date;


public interface AuthenticationDao {

	public int deleteExpire(Date d);

	public Authentication getByUserId(Long userId);

	public Pagination getPage(int pageNo, int pageSize);

	public Authentication findById(String id);

	public Authentication save(Authentication bean);

	public Authentication updateByUpdater(Updater<Authentication> updater);

	public Authentication deleteById(String id);
}