package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.icloud.dao.commservice.RaskAttrDao;
import com.anchorcms.icloud.dao.commservice.TaskDao;
import com.anchorcms.icloud.dao.commservice.TaskFilDao;
import com.anchorcms.icloud.dao.commservice.TaskFileModelDao;
import com.anchorcms.icloud.model.commservice.STask;
import com.anchorcms.icloud.model.commservice.STaskFill;
import com.anchorcms.icloud.model.commservice.STask_attr;
import com.anchorcms.icloud.service.commservice.ReportService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1314:20
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    public void save(Integer models, STaskFill sd, String demandObjListJsonString, Integer userId) {
        STask stask = taskDao.getByTaskId(models);
        sd.setsTaskList(stask);
        STaskFill fill = filDao.save(sd);
        Integer taskFillIdId = fill.getTaskFillId();
        Integer taskId = fill.getsTaskList().getTaskId();
        List<STask_attr> sTask_attrs = convertJStringToList(demandObjListJsonString,taskFillIdId,taskId,userId);

    }

    /**
     * @author: lilimin
     * @desciption 将前台传入的json String转化为需求对象的List
     */
    private List<STask_attr> convertJStringToList(String SDemandObjs, Integer taskFillIdId,Integer taskId,Integer userId){
        JSONObject jsonObj = new JSONObject(SDemandObjs);
        JSONArray jsonArr = (JSONArray) jsonObj.get("STaskFillModels");
        List<STask_attr> sTaskModelList = new ArrayList<STask_attr>();
        if(jsonArr==null||jsonArr.length()==0){
            return null;
        }
        Date createDate = new Date(System.currentTimeMillis());
        //遍历传过来的json对象数组，取出值转换后置入对象，对象再置入list
        for(int i = 0; i<jsonArr.length(); i++){
            JSONObject demandJObj = jsonArr.getJSONObject(i);
            int lengthobj =(demandJObj.length()/2);
            for(int j=0; j<lengthobj;j++){
                STask_attr task_attr = new STask_attr();
                //STaskFillModel stakeModel = new STaskFillModel();
                task_attr.setTask_model_id(Integer.parseInt(demandJObj.getString(""+j+"ID")));
                task_attr.setTask_file_id(taskFillIdId);
                task_attr.setTask_name(""+i+"");
                task_attr.setTask_value(demandJObj.getString(""+j+""));
                task_attr.setGroup_id(""+i+"");
                task_attr.setUser_id(userId);
                sTaskModelList.add(task_attr);
                raskAttrDao.save(task_attr);
            }
        }
        return sTaskModelList;
    }
   @Resource
    private TaskFilDao filDao;
    @Resource
    private TaskFileModelDao taskFileModelDao;
    @Resource
    private RaskAttrDao raskAttrDao;
    @Resource
    private TaskDao taskDao;
}
