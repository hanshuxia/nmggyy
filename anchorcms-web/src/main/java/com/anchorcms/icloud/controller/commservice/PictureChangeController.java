package com.anchorcms.icloud.controller.commservice;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.icloud.model.commservice.PictureFistpage;
import com.anchorcms.icloud.service.commservice.PictureChangeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.anchorcms.common.constants.Constants.TPLDIR_COMMSERVICE;

/**
 * Created by DELL on 2017/2/10.
 */
@Controller
public class PictureChangeController {
    /**
     * @Author lisong
     * @Date 2017/2/20 10:26
     *对首页轮播图的修改
     */
    public static final String SUPPLYDETAIL_PICTURE = "firstPage_picture";
    @RequiresPermissions("member:Picture_save")
    @RequestMapping("/member/Pictu_save.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,  String nwebpath, String userImg1, Integer modelId, Integer num, String nextUrl, String userImg3, String userImg4, String address,
                       String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String picPaths, String[] picDescs) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());//获取当前时间作为惠补政策的发布时间
        PictureFistpage pictureFistpage=new PictureFistpage();
        pictureFistpage.setCreateDate(date);//获取系统时间
        String s[]=picPaths.split(",");//图片地址拆分
        String a[]=nwebpath.split(",");//图片对应的网址拆分
        if(a[0]!=null){
            String[] flags = s[0].split("/");
            String flag = flags[1];
            if(flag.equals("resources")){//如果是用户上传的就设置status属性为0，如果不是为1，他们的路径不同
                pictureChangeService.update(s[0],date,a[0],1,1);//图片路径，当前时间，数据库ID，status属性
            }else{
                pictureChangeService.update(s[0],date,a[0],1,0);
            }
        }
        if ((a[1]!=null)){
            String[] flags = s[1].split("/");
            String flag = flags[1];
            if(flag.equals("resources")){
                pictureChangeService.update(s[1],date,a[1],2,1);//图片路径，当前时间，数据库ID，status属性
            }else{
                pictureChangeService.update(s[1],date,a[1],2,0);
            }
        }
        if(a[2]!=null){
            String[] flags = s[2].split("/");
            String flag = flags[1];
            if(flag.equals("resources")){
                pictureChangeService.update(s[2],date,a[2],3,1);//图片路径，当前时间，数据库ID，status属性
            }else{
                pictureChangeService.update(s[2],date,a[2],3,0);
            }
        }
        if(a[3]!=null){
            String[] flags = s[3].split("/");
            String flag = flags[1];
            if(flag.equals("resources")){
                pictureChangeService.update(s[3],date,a[3],4,1);//图片路径，当前时间，数据库ID，status属性
            }else{
                pictureChangeService.update(s[3],date,a[3],4,0);
            }
        }else {
            pictureChangeService.update("0",date,a[0],1,1);//图片路径，当前时间，数据库ID，status属性
            pictureChangeService.update("00",date,a[1],2,1);//图片路径，当前时间，数据库ID，status属性
            pictureChangeService.update("00",date,a[2],3,1);//图片路径，当前时间，数据库ID，status属性
            pictureChangeService.update("0",date,a[3],4,1);//图片路径，当前时间，数据库ID，status属性
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author lisong
     * @Date 2017/2/20 10:29
     *跳转到修改轮播图界面并且取出数据
     */
    @RequiresPermissions("member:P_get")
    @RequestMapping("/member/P_get.jspx")
    public String get(HttpServletRequest request, ModelMap model){
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        List<PictureFistpage> list=pictureChangeService.get();
        model.addAttribute("pic",list);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_COMMSERVICE, SUPPLYDETAIL_PICTURE);
    }

    @Resource
    private PictureChangeService pictureChangeService;
}
