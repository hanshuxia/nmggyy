package com.anchorcms.icloud.service.commservice.impl;


import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.TaskFilDao;
import com.anchorcms.icloud.dao.commservice.SUploadStaskDao;
import com.anchorcms.icloud.model.commservice.STask;
import com.anchorcms.icloud.model.commservice.STaskFill;
import com.anchorcms.icloud.service.commservice.ManagerUploadTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author Yhr
 * @Date 2017/1/12 0012 16:48
 */
@Service
@Transactional
public class ManagerUploadTaskServiceImpl implements ManagerUploadTaskService {
    /**
     * @Author 闫浩芮
     * 查询任务列表
     * @Date 2017/1/13 0013 9:57
     */
    public Pagination getPageForMember(int pageNo, int pageSize, Date startDt, Date deadlineDt, String taskName, String status,CmsUser user) {
        //第一步拿到所有的下达的信息记录
        STask sTask;
        Pagination  page = uploaddao.getPageBySelf(pageNo, 20, startDt, deadlineDt, taskName, status,user);
//        Pagination copyPage = new Pagination();

//        try {
//            copyPage =cloneObject(page);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        copyPage.setTotalCount(page.getTotalCount());
//        copyPage.setPageNo(page.getPageNo());
//        if(status == null){
//            return copyPage;
//        }
        //第二步拿到所有的已经上报的信息记录
//        for(int i=0;i<page.getList().size();i++){
//            sTask = (STask)(page.getList().get(i));
//            STaskFill taskFile = taskFilDao.getTasKFileById(user.getUserId(),sTask.getTaskId());
//            //第三步 当能查询到时说明已经上报则为已上报
//            if(taskFile == null) {
//                //当状态为2时 查询已经上报的,把没有上报的删掉
//                if (status.equals("2")) {
//                    //移除掉多余的对象
//                    copyPage = deleteSurplus(i,page,copyPage);
//                }else{
//                    copyPage = changeStatue(i,page,copyPage,"1");
//
//                }
//            }else {
//
//                if(status.equals("1")){
//                    //移除掉多余的对象
//                    copyPage = deleteSurplus(i,page,copyPage);
//                }else {
//                    copyPage = changeStatue(i,page,copyPage,"2");
//                }
//            }
//        }
////        copyPage.setTotalCount(copyPage.getList().size());
        return page;
    }

    //更改状态
    private Pagination changeStatue(int i, Pagination page, Pagination copyPage, String s) {
        int taskID= ((STask)(page.getList().get(i))).getTaskId();
        for(int j=0;j<copyPage.getList().size();j++){
            int copyStake = (((STask)(copyPage.getList().get(j))).getTaskId());
            if(copyStake == taskID){
                ((STask)((copyPage.getList()).get(j))).setStatus(s);
            }
        }
        return copyPage;
    }


    /**
     * @param pageNo
     * @param pageSize
     * @param startDt
     * @param deadlineDt
     * @param taskName
     * @param status
     * @param user
     * @return
     * @Authwr lilimin
     * 分页查询所有的已经上报的信息
     */
    public Pagination getPageForTaskFill(int pageNo, int pageSize, Date startDt, Date deadlineDt, String taskName, String status, CmsUser user) {
        Pagination  page = taskFilDao.getPageByTaskFIle(pageNo, 20, startDt, deadlineDt, taskName, status,user);
        return page;
    }
    public Pagination getPageForTaskFill2(int pageNo, int pageSize, Date startDt, Date deadlineDt, String taskName, String status, CmsUser user) {
        Pagination  page = taskFilDao.getPageByTaskFIle2(pageNo, 20, startDt, deadlineDt, taskName, status,user);
        return page;
    }

    /**
     * 去除需要去除的对象
     * @param i
     * @param page
     * @param copyPage
     * @return
     */
    public Pagination deleteSurplus(int i, Pagination page, Pagination copyPage){
        int taskID= ((STask)(page.getList().get(i))).getTaskId();
        for(int j=0;j<copyPage.getList().size();j++){
            int copyStake = (((STask)(copyPage.getList().get(j))).getTaskId());
            if(copyStake == taskID){
                copyPage.getList().remove(j);

            }
        }
        return copyPage;
    }
    /**
     * @auther lilimin
     * 对象深度克隆
     * @param obj
     * @return
     * @throws Exception
     */
    public static Pagination cloneObject(Pagination obj) throws Exception{
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(obj);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in =new ObjectInputStream(byteIn);
        return (Pagination) in.readObject();
    }
    @Resource
    private SUploadStaskDao uploaddao;
    @Resource
    private TaskFilDao taskFilDao;
}