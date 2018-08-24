package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.icloud.model.commservice.SBigDataDemandSign;
import com.anchorcms.icloud.model.commservice.SBigDataNews;
import com.anchorcms.icloud.model.commservice.SBigDataPolicy;
import com.anchorcms.icloud.model.commservice.SBigDataSign;
import com.anchorcms.icloud.model.synergy.SDemand;

/**
 * Created by Gao JN on 2016/12/22 0022.
 */
public interface SDemandCreateDao {
    public SDemand save(SDemand bean);

    public SBigDataSign save2(SBigDataSign bean);
    public SBigDataDemandSign save3(SBigDataDemandSign bean);
    public SBigDataNews saveBigdataNews(SBigDataNews bean);
    public SBigDataPolicy saveBigdataPolicy(SBigDataPolicy bean);
    /**
     * @Author 闫浩芮
     * @param demandId
     * @Date 2017/01/04
     * @Desc 通过ID获取需求信息
     */
    public SDemand bySDemandId(Integer demandId);

    /**
     * 手动删除子表被去掉关系的对象
     * @return
     */
    void deleteOrphan();
}


