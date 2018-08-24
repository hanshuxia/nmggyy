package com.anchorcms.icloud.service.commservice.impl;


import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.STaskModelDao;
import com.anchorcms.icloud.dao.commservice.TaskDao;
import com.anchorcms.icloud.dao.commservice.TaskFilDao;
import com.anchorcms.icloud.dao.commservice.SStaskDao;
import com.anchorcms.icloud.model.commservice.STask;
import com.anchorcms.icloud.model.commservice.STaskModel;
import com.anchorcms.icloud.service.commservice.ManagerReleaseTaskService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Yhr
 * @Date 2017/1/12 0012 16:48
 */
@Service
@Transactional
public class ManagerReleaseTaskServiceImpl implements ManagerReleaseTaskService {
    /**
     * @Author 闫浩芮
     * 查询任务列表
     * @Date 2017/1/13 0013 9:57
     */
    public Pagination getPageForMember(int pageNo, int pageSize, Date startDt, Date deadlineDt, String taskName, String status, CmsUser user) {
        return dao.getPageBySelf(pageNo,20,startDt, deadlineDt, taskName,status,user);
    }
    /**
     * @Author 闫浩芮
     * 更新任务状态
     * @Date 2017/1/13 0013 9:57
     */
    public void update(Integer taskId) {
        STask sTask =dao.findById(taskId);
        if (sTask.getStatus() != null) {
            switch (Integer.parseInt(sTask.getStatus())) {
                case 0:
                    sTask.setStatus("1");
                    break;
                case 1:
                    sTask.setStatus("0");
                    break;
            }
        }
        Updater<STask> updater = new Updater<STask>(sTask);
        dao.updateByUpdater(updater);
    }
    /**
     * @Author 闫浩芮
     * 根据id查找
     * @Date 2017/1/13 0013 13:51
     */
    public STask byTaskId(Integer taskId) {
        return dao.findById(taskId);
    }

    public void updateSave(STask sTask, String sTaskModels) {
        STask stask = taskDao.Update(sTask);
        Integer taskId = stask.getTaskId();
        List<STaskModel> list = sTaskModelDao.getById(taskId);
        for(int i=0;i<list.size();i++){
            sTaskModelDao.delete(list.get(i));
        }

        List<STaskModel> models = convertJStringToList(sTaskModels,taskId);
    }

    public STask delete(Integer taskId) {
        STask sTask =taskDao.getByTaskId(taskId);
        sTask=dao.delete(sTask);
        return sTask;
    }

    public List getFileTaskByTaskId(Integer taskId) {
        List takefile = filDao.getByTaskId(taskId);
        return takefile;
    }


    public List<STaskModel> getModelById(Integer taskId) {
        List<STaskModel> list = sTaskModelDao.getById(taskId);
        return list;
    }

    /**
     * @author: gao jiangning
     * @desciption 将前台传入的json String转化为需求对象的List
     */
    private List<STaskModel> convertJStringToList(String SDemandObjs,Integer taslId){
        JSONObject jsonObj = new JSONObject(SDemandObjs);
        JSONArray jsonArr = (JSONArray) jsonObj.get("STaskModels");
        List<STaskModel> sTaskModelList = new ArrayList<STaskModel>();
        if(jsonArr==null||jsonArr.length()==0){
            return null;
        }
        java.sql.Date createDate = new java.sql.Date(System.currentTimeMillis());
        //遍历传过来的json对象数组，取出值转换后置入对象，对象再置入list
        for(int i = 0; i<jsonArr.length(); i++){
            JSONObject demandJObj = jsonArr.getJSONObject(i);
            STaskModel stakeModel = new STaskModel();
            stakeModel.setModelName(demandJObj.getString("modelName"));
            stakeModel.setModelType(demandJObj.getString("modelType"));
            stakeModel.setModelLength(demandJObj.getString("modelLength"));
            stakeModel.setRemark(demandJObj.getString("remark"));
            stakeModel.setSortNum(i);
            stakeModel.setTaskId(taslId);
            sTaskModelList.add(stakeModel);
            sTaskModelDao.save(stakeModel);
        }
        return sTaskModelList;
    }
    @Resource
    private SStaskDao dao;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentDao contentDao;
    @Resource
    private TaskDao taskDao;
    @Resource
    private STaskModelDao sTaskModelDao;
    @Resource
    private TaskFilDao filDao;
}
