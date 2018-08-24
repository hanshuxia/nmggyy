package com.anchorcms.core.service.impl;

import com.anchorcms.common.email.EmailSendTool;
import com.anchorcms.common.email.EmailSender;
import com.anchorcms.common.email.MessageTemplate;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.security.BadCredentialsException;
import com.anchorcms.common.security.UsernameNotFoundException;
import com.anchorcms.common.security.encoder.PwdEncoder;
import com.anchorcms.core.dao.UnifiedUserDao;
import com.anchorcms.core.model.Config.ConfigLogin;
import com.anchorcms.core.model.UnifiedUser;
import com.anchorcms.core.service.ConfigMng;
import com.anchorcms.core.service.UnifiedUserMng;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


@Service
@Transactional
public class UnifiedUserMngImpl implements UnifiedUserMng {

	public UnifiedUser passwordForgotten(Integer userId, EmailSender email,
										 MessageTemplate tpl) {
		UnifiedUser user = findById(userId);
		String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
		user.setResetKey(uuid);
		String resetPwd = RandomStringUtils.randomNumeric(10);
		user.setResetPwd(resetPwd);
		senderEmail(user.getUserId(), user.getUsername(), user.getEmail(), user
				.getResetKey(), user.getResetPwd(), email, tpl);
		return user;
	}

	private void senderEmail(final Integer uid, final String username,
							 final String to, final String resetKey, final String resetPwd,
							 final EmailSender email, final MessageTemplate tpl) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();

		sender.setHost(email.getHost());
		if(email.getPort()!=null){
			sender.setPort(email.getPort());
		}
		sender.setUsername( email.getUsername().indexOf("@")>-1?email.getUsername().substring(0,email.getUsername().indexOf("@")):email.getUsername());
		sender.setPassword(email.getPassword());
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", email.getHost());
		props.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(props);
		//session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		try {
			Transport transport = session.getTransport();
			message.setSubject(tpl.getForgotPasswordSubject());
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to, "系统用户", "UTF-8"));
			message.setFrom(new InternetAddress(email.getUsername(), "管理员", "UTF-8"));
			String text = tpl.getForgotPasswordText();
			text = StringUtils.replace(text, "${uid}", String.valueOf(uid));
			text = StringUtils.replace(text, "${username}", username);
			text = StringUtils.replace(text, "${resetKey}", resetKey);
			text = StringUtils.replace(text, "${resetPwd}", resetPwd);
			message.setText(text);
			message.setSentDate(new Date());

			transport.connect(email.getUsername(), email.getPassword());
			transport.sendMessage(message, message.getAllRecipients());

			transport.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void senderEmail(final String username, final String to,
							 final String activationCode, final EmailSender email,
							 final MessageTemplate tpl) throws UnsupportedEncodingException, MessagingException {
		String text = tpl.getRegisterText();
		text = StringUtils.replace(text, "${username}", username);
		text = StringUtils.replace(text, "${activationCode}", activationCode);
		EmailSendTool sendEmail = new EmailSendTool(email.getHost(), email
				.getUsername(), email.getPassword(), to, tpl
				.getRegisterSubject(), text, email.getPersonal(), "", "");
		sendEmail.send();
	}

	public UnifiedUser resetPassword(Integer userId) {
		UnifiedUser user = findById(userId);
		//对忘记密码进行修改
		user.setPassword(pwdEncoder.encodePassword(pwdEncoder.encodePassword(user.getResetPwd())));
		//user.setPassword(pwdEncoder.encodePassword(user.getResetPwd()));
		user.setResetKey(null);
		user.setResetPwd(null);
		return user;
	}

	public Integer errorRemaining(String username) {
		if (StringUtils.isBlank(username)) {
			return null;
		}
		UnifiedUser user = getByUsername(username);
		if (user == null) {
			return null;
		}
		long now = System.currentTimeMillis();
		ConfigLogin configLogin = configMng.getConfigLogin();
		int maxErrorTimes = configLogin.getErrorTimes();
		int maxErrorInterval = configLogin.getErrorInterval() * 60 * 1000;
		Integer errorCount = user.getErrorCount();
		Date errorTime = user.getErrorTime();
		if (errorCount <= 0 || errorTime == null
				|| errorTime.getTime() + maxErrorInterval < now) {
			return maxErrorTimes;
		}
		return maxErrorTimes - errorCount;
	}

	public UnifiedUser login(String username, String password, String ip)
			throws UsernameNotFoundException, BadCredentialsException {
		UnifiedUser user = getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("username not found: "
					+ username);
		}
		if (!pwdEncoder.isPasswordValid(user.getPassword(), password)) {
			updateLoginError(user.getUserId(), ip);
			throw new BadCredentialsException("password invalid");
		}
		if (!user.getActivation()) {
			throw new BadCredentialsException("account not activated");
		}
		updateLoginSuccess(user.getUserId(), ip);
		return user;
	}

	public void updateLoginSuccess(Integer userId, String ip) {
		UnifiedUser user = findById(userId);
		Date now = new Timestamp(System.currentTimeMillis());

		user.setLoginCount(user.getLoginCount() + 1);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);

		user.setErrorCount(0);
		user.setErrorTime(null);
		user.setErrorIp(null);
	}

	public void updateLoginError(Integer userId, String ip) {
		UnifiedUser user = findById(userId);
		Date now = new Timestamp(System.currentTimeMillis());
		ConfigLogin configLogin = configMng.getConfigLogin();
		int errorInterval = configLogin.getErrorInterval();
		Date errorTime = user.getErrorTime();

		user.setErrorIp(ip);
		if (errorTime == null
				|| errorTime.getTime() + errorInterval * 60 * 1000 < now
				.getTime()) {
			user.setErrorTime(now);
			user.setErrorCount(1);
		} else {
			user.setErrorCount(user.getErrorCount() + 1);
		}
	}

	public boolean usernameExist(String username) {
		return getByUsername(username) != null;
	}

	public boolean emailExist(String email) {
		return dao.countByEmail(email) > 0;
	}

	public UnifiedUser getByUsername(String username) {
		return dao.getByUsername(username);
	}

	public List<UnifiedUser> getByEmail(String email) {
		return dao.getByEmail(email);
	}

	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public UnifiedUser findById(Integer id) {
		UnifiedUser entity = dao.findById(id);
		return entity;
	}

	public UnifiedUser save(String username, String email, String password,
							String ip)  {
		Date now = new Timestamp(System.currentTimeMillis());
		UnifiedUser user = new UnifiedUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(pwdEncoder.encodePassword(password));
		user.setRegisterIp(ip);
		user.setRegisterTime(now);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);
		//不强制验证邮箱直接激活
		user.setActivation(true);
		user.init();
		dao.save(user);
		return user;
	}

	public UnifiedUser save(String username, String email, String password,
							String ip, Boolean activation, EmailSender sender,
							MessageTemplate msgTpl) throws UnsupportedEncodingException, MessagingException {
		Date now = new Timestamp(System.currentTimeMillis());
		UnifiedUser user = new UnifiedUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(pwdEncoder.encodePassword(password));
		user.setRegisterIp(ip);
		user.setRegisterTime(now);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);
		user.setActivation(activation);
		user.init();
		if (!activation) {
			String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
			user.setActivationCode(uuid);
			senderEmail(username, email, uuid, sender, msgTpl);
		}
		dao.save(user);
		return user;
	}

	/**
	 * @see UnifiedUserMng#update(Integer, String, String)
	 */
	public UnifiedUser update(Integer id, String password, String email) {
		UnifiedUser user = findById(id);
		if (!StringUtils.isBlank(email)) {
			user.setEmail(email);
		} else {
			user.setEmail(null);
		}
		if (!StringUtils.isBlank(password)) {
			user.setPassword(pwdEncoder.encodePassword(password));
		}
		return user;
	}

	public boolean isPasswordValid(Integer id, String password) {
		UnifiedUser user = findById(id);
		return pwdEncoder.isPasswordValid(user.getPassword(), password);
	}

	public UnifiedUser deleteById(Integer id) {
		UnifiedUser bean = dao.deleteById(id);
		return bean;
	}
	public void updateUser(UnifiedUser user){
		Updater<UnifiedUser> updater=new Updater<UnifiedUser>(user);
		dao.updateByUpdater(updater);
	}

	public UnifiedUser[] deleteByIds(Integer[] ids) {
		UnifiedUser[] beans = new UnifiedUser[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public UnifiedUser active(String username, String activationCode) {
		UnifiedUser bean = getByUsername(username);
		bean.setActivation(true);
		bean.setActivationCode(null);
		return bean;
	}

	public UnifiedUser activeLogin(UnifiedUser user, String ip) {
		updateLoginSuccess(user.getUserId(), ip);
		return user;
	}

	@Resource
	private ConfigMng configMng;
	@Resource
	private PwdEncoder pwdEncoder;
	@Resource
	private UnifiedUserDao dao;

}