package com.anchorcms.icloud.controller.common;

import com.anchorcms.icloud.service.common.PubCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Copyright @ 2017 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2017/1/13 0013
 * @Desc 获取分类等信息的controller
 */
@Controller
public class TypeCatagoryController {

    @RequestMapping(value = "/dict/getTypeCatagory.jsp")
    public void getTypeCatagoryAjax(String typeCode, String parentKey, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        String cataListJson = "";
        if(parentKey !=null && !"".equals(parentKey) && !"undefined".equals(parentKey) && "grand".equals(parentKey)){
            cataListJson = pubCodeService.getGrandCataJson(typeCode);
        }else if(typeCode!=null && !"".equals(typeCode) && !"undefined".equals(typeCode)){
            cataListJson = pubCodeService.getCatagoryJson(typeCode,parentKey);
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.print(cataListJson);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Resource
    private PubCodeService pubCodeService;
}
