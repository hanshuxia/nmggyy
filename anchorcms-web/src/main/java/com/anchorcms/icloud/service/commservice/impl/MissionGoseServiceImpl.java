package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.icloud.dao.commservice.STaskModelDao;
import com.anchorcms.icloud.dao.commservice.TaskDao;
import com.anchorcms.icloud.model.commservice.STask;
import com.anchorcms.icloud.model.commservice.STaskModel;
import com.anchorcms.icloud.service.commservice.MissionGoseService;
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
 * @data 2017/1/1216:45
 */
@Service
@Transactional
public class MissionGoseServiceImpl implements MissionGoseService {
    public void save(STask sd, String sTaskModels) {
        STask stask = taskDao.save(sd);
         Integer taskId = stask.getTaskId();
        List<STaskModel> models = convertJStringToList(sTaskModels,taskId);

    }

    public STask getByTaskId(Integer taskId) {
        STask stask = taskDao.getByTaskId(taskId);
        return stask;
    }

    public List<STaskModel> getModelById(Integer taskId) {
        List<STaskModel> list = sTaskModelDao.getById(taskId);
        return list;
    }

    public List getModelByIdUserId(Integer taskId, Integer userId) {
        List list = sTaskModelDao.getByIdUserId(taskId,userId);
        return list;
    }

    /**
     * @author: lilimin
     * @desciption 将前台传入的json String转化为需求对象的List
     */
    private List<STaskModel> convertJStringToList(String SDemandObjs,Integer taslId){
        JSONObject jsonObj = new JSONObject(SDemandObjs);
        JSONArray jsonArr = (JSONArray) jsonObj.get("STaskModels");
        List<STaskModel> sTaskModelList = new ArrayList<STaskModel>();
        if(jsonArr==null||jsonArr.length()==0){
            return null;
        }
        Date createDate = new Date(System.currentTimeMillis());
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
    private TaskDao taskDao;
    @Resource
    private STaskModelDao sTaskModelDao;
}
