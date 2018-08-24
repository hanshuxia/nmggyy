package com.anchorcms.icloud.controller.casicloud;
import java.net.URLDecoder;
import java.util.Map;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;


public class AssertionListener implements HttpSessionAttributeListener {
    public void attributeAdded(HttpSessionBindingEvent se) {
        if (se.getName().equals("ua")) {
            System.err.println("添加了某属性.....");
            //不可以使用工具类，只可以使用session获取对象
            Assertion ass = (Assertion) se.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
            Map<String, Object> user = ass.getPrincipal().getAttributes();
            se.getSession().setAttribute("user", user);
            try {
                for (String key : user.keySet()) {
                    //因为服务器上转码，所以此外解码
                    user.put(key,
                            URLDecoder.decode("" + user.get(key), "UTF-8"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void attributeRemoved(HttpSessionBindingEvent se) {
    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
    }
}
