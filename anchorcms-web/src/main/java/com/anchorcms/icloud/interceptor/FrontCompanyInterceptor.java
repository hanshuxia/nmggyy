package com.anchorcms.icloud.interceptor;

import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.service.CmsUserMng;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 11239 on 2017/2/20.
 */
public class FrontCompanyInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws ServletException {
        ModelMap model=new ModelMap();
        Subject subject = SecurityUtils.getSubject();
        CmsUser user =null;
        if (subject.isAuthenticated()|| subject.isRemembered()) {
            String username =  (String) subject.getPrincipal();
            user= cmsUserMng.findByUsername(username);
        }
       String url = request.getRequestURL().toString();
            String str[]=url.split("/");
        String strUrl=str[str.length-2]+"/"+str[str.length-1];
        String lu=str[str.length-1];
        String s[]=lu.split("\\.");
        String lastS="";
        if(s.length>1 && s[1].length()>3) {
            lastS = s[1].substring(0, 4);
        }
        if("member".equals(str[str.length-2])&&"jspx".equals(lastS)) {
            if("member/index.jspx".equals(strUrl)||"member/company_edit.jspx".equals(strUrl)||"member/company_base_info.jspx".equals(strUrl)
                    ||"member/message_countUnreadMsg.jspx".equals(strUrl)   ||"member/company.jspx".equals(strUrl)
                    ||"member/company_baseinfo_save.jspx".equals(strUrl)
                     /*"member/company_detail_info.jspx".equals(strUrl)
                    ||"member/ability_list.jspx".equals(strUrl)||"member/company_diploma_list.jspx".equals(strUrl)
                    ||"member/company_device_list.jspx".equals(strUrl)*/) {
                return true;
            }else
            {
                if (user != null) {
                    //判断是否维护了信息
                    if (user.getCompany() == null) {
                        try {
                            response.setCharacterEncoding("UTF-8");
                            request.getRequestDispatcher("/member/company.jspx").forward(request, response);
                            return false;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //判断维护了信息是否通过
                    if (!user.getIsAdmin()&&user.getCompany() != null&&(user.getCompany().getStatus()==null||user.getCompany().getStatus().equals("2"))){
                        try {
                            response.setCharacterEncoding("UTF-8");
                            request.getRequestDispatcher("/member/company.jspx").forward(request, response);
                            return false;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return true;
    }
    @Resource
    private CmsUserMng cmsUserMng;
}
