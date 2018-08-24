package com.anchorcms.common.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 用来进行服务器对用户的认证
 */
public class Email_Autherticator extends Authenticator {
	String username = "";
	String password = "";
    public Email_Autherticator() {
        super();
    }

    public Email_Autherticator(String user, String pwd) {
        super();
        username = user;
        password = pwd;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
