package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.commservice.SStiteManager;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import com.anchorcms.icloud.service.commservice.ShZwdhCreateService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;


import static com.anchorcms.common.page.SimplePage.cpn;
import static com.anchorcms.common.utils.FrontUtils.frontData;
import static com.anchorcms.common.utils.FrontUtils.getTplPath;

/**
 * Created by suhe on 2017/2/21 0021.
 */
@Controller
@RequestMapping(value = "/commservice")
public class ShZwdhController {

    public static final String Zwdhh = "channel";
    public static final String Zwdh = "tpl.Zwdh";

    /**
     * @return 政务导航
     * @author suhe
     * @Date 2017/2/22 0022 上午 9:10
     */
    @RequestMapping(value = "/Zwdh.jspx")
    public String list(Integer modelId, String status, String address,
                       HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        frontData(request, model, site);
        //获取分页对象，
        //gjn:前台获取的status参数应该为statusType，在此依然用status传递，不过dao层已修改为用statusType过滤
//        Pagination p = ShZwdhCreateService.getPageForMember(status);
//        model.addAttribute("pagination", p);
        List list = ShZwdhCreateService.manageList(status);
        model.addAttribute("zwdh", list);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("address", address);
        model.addAttribute("status", status);
        return getTplPath(request, site.getSolutionPath(),
                Zwdhh, Zwdh);
    }

    @RequestMapping(value = "/Zwdh_ajax.jspx", method = RequestMethod.POST)
    public void getJsonlist(Integer modelId, String status, String address,
                       HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        response.addHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
        // 解决前台传入的中文乱码参数
        String jsonStr = ShZwdhCreateService.getJson(status);
        try {
            PrintWriter writer = response.getWriter();
            writer.print(jsonStr);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Resource
    private ShZwdhCreateService ShZwdhCreateService;

}

