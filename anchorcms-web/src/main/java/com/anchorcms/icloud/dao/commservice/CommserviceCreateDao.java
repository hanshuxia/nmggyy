package com.anchorcms.icloud.dao.commservice;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.SStiteManager;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;

import java.util.List;
import java.util.Map;

/**
 * Created by ${user} on 2017/1/13.
 */
public interface CommserviceCreateDao {
    /**
     * @author gengwenlong
     * @Date 2017/1/13 10:27
     * @return
     * 惠补政策展示页面
     */
    Pagination getSearchHuibu(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                            Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /**
     * @author liuyang
     * @Date 2017/12/11 10:53
     * @return
     * 政务导航展示页面
     */
    Pagination getSearchZhengWu(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                              Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);


    /**
     * @author gengwenlong
     * @Date 2017/1/13 15:17
     * @return
     * 项目招投标展示页面
     *
     */
    Pagination getSearchTender(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                              Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
    /**
     * @author machao
     * @Date 2017/1/15 12:39
     * @return
     */
    public SBidNotice save(SBidNotice bean);


    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-招标公告
     */
    public List<STenderNotice> getStenderNoticeList(Integer count, Integer orderBy, Integer listType);

    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-招标预告
     */
    public List<STenderTrailer> getStenderTrailerList(Integer count, Integer orderBy, Integer listType);

    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-中标公告
     */
    public List<SBidNotice> getSbidNoticeList(Integer count, Integer orderBy, Integer listType);
    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-政务导航
     */
    public List<SStiteManager> getSstiteManagerList(Integer count, Integer orderBy, Integer listType);
}
