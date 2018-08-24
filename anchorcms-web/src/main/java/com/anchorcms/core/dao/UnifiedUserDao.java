package com.anchorcms.core.dao;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.UnifiedUser;

import java.util.List;


public interface UnifiedUserDao {
	public UnifiedUser getByUsername(String username);

	public List<UnifiedUser> getByEmail(String email);

	public int countByEmail(String email);

	public Pagination getPage(int pageNo, int pageSize);

	public UnifiedUser findById(Integer id);

	public UnifiedUser save(UnifiedUser bean);

	public UnifiedUser updateByUpdater(Updater<UnifiedUser> updater);

	public UnifiedUser deleteById(Integer id);
}