package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.icloud.dao.commservice.ShZwdhCreateDao;
import com.anchorcms.icloud.model.commservice.SStiteManager;
import com.anchorcms.icloud.service.commservice.ShZwdhCreateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
@Service
@Transactional
public class ShZwdhCreateServiceImpl implements ShZwdhCreateService {
//    public Pagination getPageForMember(String status) {
//        return ShZwdhCreateDaoImpl.getPageBySelf(status);
//    }




    public List manageList(String status) {
        return ShZwdhCreateDao.ZwdhManagelist(status);
    }

    public String getJson(String status) {
        List list = ShZwdhCreateDao.ZwdhManagelist(status);
        return convertSiteInfoList(list);
    }

    public String convertSiteInfoList(List qList ) {
        StringBuffer json = new StringBuffer();
        json.append("{\"SStiteManagers\":[");
        if (qList != null && qList.size() > 0) {
            for(int i=0 ;i<qList.size();i++){
                Object[] obj = (Object[]) qList.get(i);
                json.append("{\"stiteName\":\"").append(obj[0]==null?"":obj[0].toString()).append("\",");
                json.append("\"stiteAddress\":\"").append(obj[1]==null?"":obj[1].toString()).append("\"},");

            }
            json.deleteCharAt(json.length() - 1);//删除最后的逗号
        }
        json.append("]").append("}");
        return json.toString();
    }

//    public String convertSiteInfoList(List<SStiteManager> qList ) {
//        StringBuffer json = new StringBuffer();
//        json.append("{\"SStiteManagers\":[");
//        if (qList != null && qList.size() > 0) {
//            for (SStiteManager sq : qList) {
//                json.append("{\"stiteName\":\"").append(sq.getStiteName()).append("\",");
//                json.append("\"stiteAddress\":\"").append(sq.getStiteAddress()).append("\"},");
//            }
//            json.deleteCharAt(json.length() - 1);//删除最后的逗号
//        }
//        json.append("]").append("}");
//        return json.toString();
//    }


    @Resource
    ShZwdhCreateDao ShZwdhCreateDao;
}
