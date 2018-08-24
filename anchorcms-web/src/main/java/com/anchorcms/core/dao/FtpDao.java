package com.anchorcms.core.dao;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.model.Ftp;

import java.util.List;


public interface FtpDao {
	public List<Ftp> getList();

	public Ftp findById(Integer id);

	public Ftp save(Ftp bean);

	public Ftp updateByUpdater(Updater<Ftp> updater);

	public Ftp deleteById(Integer id);
}