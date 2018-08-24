package com.anchorcms.icloud.service.staticpage.impl;

import com.anchorcms.cms.lucene.LuceneContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.constants.Constants;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.staticpage.StaticpageDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.model.cloudcenter.SIcloudPolicy;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.model.software.SSoftware;
import com.anchorcms.icloud.model.supplychain.*;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.service.staticpage.StaticpageService;
import com.anchorcms.icloud.service.supplychain.RepairDemandObjService;
import com.anchorcms.icloud.service.supplychain.SpareDemandObjService;
import com.anchorcms.icloud.service.synergy.InteractAreaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by jxd on 2017/2/17.
 */
@Service
public class StaticpageServiceImpl implements StaticpageService {
    /*
     * 103,104,105,106,107,108,109,110,112*2,114,115,116,117,119,122,123,124,125,    [120> 105下级]  栏目列
     * 一级栏目名      > >     栏目名       ID(内容页面数量)
     * 协同制造        > >     能力池       103(1)        需求池展示104(2)       企业制造能力展示 105(1)-120企业设备展示                                                   ✔
     * 供应链协同      > >     备品备件     106(1)        备品备件求购 107(1)    众修资源108(2)             众修需求109(2)       金牌老师傅推荐列表110(2)   ✔
     * 云资源交易中心  > >     云计算政策   112(2)        扶持政策 112(2)        云资源查看  114(1)         云需求 115(2)                                   ✔
     * 公共服务        > >     招标列表     116(3)        惠补政策列表 117(2)
     * 软件应用        > >     软件检索页面 119(1)                                                                                                         ✔
     * 互动专区        > >     问题求助     122(1)        头脑风暴 123(1)        意见建议 124(1)            系统公告125(1)
     */

    /**
     * 栏目判断：以下栏目内容生成静态页
     * 103,104,105,106,107,108,109,110,112*2,114,115,116,117,119,122,123,124,125,
     *
     * @param channelId
     * @return
     */
    public Boolean belong(Integer channelId) {
        Boolean falg = false;
        String str = "103,104,105,106,107,108,109,110,112,112,114,115,116,117,119,120,122,123,124,125";
        if (str.contains(channelId.toString())) falg = true;
        return falg;
    }

    /**
     * 按照ChannelId获取栏目模板路径、数据，按相关内容页数量（部分栏目只有展示页，部分存在展示页与填写页str2中，
     * 116情况较为特殊），进行划分
     *
     * @param contentId
     * @param channelId
     * @param site
     * @param data      装载模板数据
     * @return list 装载模板路径，以及模板类型（展示页、填写页）
     */
    public List<HashMap> tplList(Integer contentId, Integer channelId, CmsSite site, Map<String, Object> data) {
        List<HashMap> list = new ArrayList();
        try {
            String str1 = "103,105,106,107,119,120,122,123,124,125";                              //只生成展示页
            String str2 = "104,108,109,110,112,112,114,115,117";                                  //生成展示页、填写页
            String str3 = "116";                                                                  //生成Tab页
            if (str1.contains(channelId.toString())) {
                list = tplOne(contentId, channelId, site, data);
            } else if (str2.contains(channelId.toString())) {
                list = tplTwo(contentId, channelId, site, data);

            } else if (str3.contains(channelId.toString())) {
                list = tplThree(contentId, channelId, site, data);
            }
        } catch (Exception e) {

        }
        return list;
    }

    /**
     * 只有展示页,获取栏目路径与返回页面数据，判断类型type表明展示页
     * nextUrl  模板
     * dir 模板上级文件夹
     */
    public List<HashMap> tplOne(Integer contentId, Integer channelId, CmsSite site, Map<String, Object> data) {
        List<HashMap> list = new ArrayList<HashMap>();
        HashMap m = new HashMap();
        String nextUrl = null;
        String dir = null;
        /* 协同制造 */
        if (channelId == 103) {
            // 协同制造 > 能力池 /xtzzNlczs/xtzz_ability_preview.jspx?
            nextUrl = "news_icloud_xtzz_abilityPreview";
            dir = "content";
            SAbility ability = luceneContentDao.findAbilityByContentId(contentId);
            if (ability == null) return null;
            data.put("ability", ability);
        } else if (channelId == 105 || channelId == 120) {
            // 协同制造 > 企业制造能力展示 /xtzzNlczs/xtzz_synergy_company_preview
            nextUrl = "company_message";
            dir = "content";
            SCompany company = luceneContentDao.findCompanyByContentId(contentId);
            if (company == null) return null;
            data.put("company", company);
        }
        /* 供应链协同 */
        else if (channelId == 106) {
            //供应链协同>> 备品备件 spare/detail.jspx
            nextUrl = "spare_detail";
            dir = "supplychain";
            SSpare spare = luceneContentDao.findSSpareByContentId(contentId);
            if (spare == null) return null;
            SCompany company = spare.getCompany();
            data.put("spare", spare);
            data.put("company", company);
        } else if (channelId == 107) {
            // 供应链协同>>备品备件求购  /spareDemand.jspx?flag=1  /spareDemand.jspx  /supplychain/news_icloud_gylxt_bpbjqgdetail.html
            nextUrl = "news_icloud_gylxt_bpbjqgdetail";
            dir = "supplychain";
            SSpareDemand sSpareDemand = luceneContentDao.findSSpareDemandByContentId(contentId);
            if (sSpareDemand == null) return null;
            Content content = sSpareDemand.getContent();
            List<SSpareDemandObj> objEntityList = spareDemandObjService.selectSSpareDemandObjEntity(sSpareDemand.getDemandId());
            data.put("sSpareDemand", sSpareDemand);
            data.put("objEntityList", objEntityList);
        }
        /* 软件应用 */
        else if (channelId == 119) {
            //软件应用 >>软件检索页面  rjyyRjjsym/software_info.jspx
            nextUrl = "software_info";
            dir = "content";
            SSoftware software = luceneContentDao.findSoftwareDemandByContentId(contentId);
            if (software == null) return null;
            data.put("software", software);//1
        }
        /*互动专区*/
        else if (channelId == 122) {
            //互动专区 > > 问题求助
            nextUrl = "";
            dir = "";
        } else if (channelId == 123) {
            //互动专区 > > 头脑风暴 /member/brain_storm_preview.html  /hdzq/brain_storm_preview.jspx
            dir = "member";
            nextUrl = "brain_storm_preview";
            Content content = interactAreaService.byContentId(contentId);
            if (content == null) return null;
            data.put("content", content);
        } else if (channelId == 124) {
            //互动专区 > > 意见建议 /member/suggestion_Preview.html  hdzq/suggestion_preview.jspx
            nextUrl = "suggestion_Preview";
            dir = "member";
            Content content = interactAreaService.byContentId(contentId);
            if (content == null) return null;
            data.put("content", content);
        } else if (channelId == 125) {
            //互动专区 > > 系统公告  /member/company_notice_preview.html
            dir = "member";
            nextUrl = "company_notice_preview";
            Content content = interactAreaService.byContentId(contentId);
            if (content == null) return null;
            data.put("content", content);
        }
        if (dir == null || "".equals(dir)) return list;
        m.put("tpl", site.getSolutionPath() + "/" + dir + "/" + nextUrl + Constants.TPL_SUFFIX);
        m.put("type", "1");
        list.add(m);
        return list;
    }

    /**
     * 存在展示页与填写页,获取栏目路径与返回页面数据，判断类型type 1 表明展示页，2 表示填写页
     * nextUrl  模板
     * dir 模板上级文件夹
     * memberNextUrl 填写页模板
     * memberDir 填写页模板上级文件夹
     */
    public List tplTwo(Integer contentId, Integer channelId, CmsSite site, Map<String, Object> data) {
        List<HashMap> list = new ArrayList<HashMap>();
        HashMap m = new HashMap();
        HashMap mm = new HashMap();
        String dir = null;
        String nextUrl = null;
        String memberDir = null;
        String memberNextUrl = null;
        /*协同制造*/
        if (channelId == 104) {
            // 协同制造 > 需求池展示 查看 /xtzzNlczs/xtzz_synergy_demand_preview.jspx
            dir = "content";
            nextUrl = "xtzz_demand_preview";
            SDemand demand = luceneContentDao.findDemandByContentId(contentId);
            if (demand == null) return null;
            CmsUser cmsuser = userDao.findById(Integer.parseInt(demand.getCreaterId()));
            data.put("userName", cmsuser.getUsername());
            data.put("demand", demand);//同时使用
            //协同制造> 需求池展示>我要报价）/member/demand_quote.html  /member/synergy_demand_quote.jsp
            memberDir = "member";
            memberNextUrl = "demand_quote";
        }
        /*云资源交易中心 */
        else if (channelId == 112) {
            //云资源交易中心>>云计算政策
            nextUrl = "cloudcenter_policy_info";
            dir = "content";
            SIcloudPolicy policy = luceneContentDao.findPolicyByContentId(contentId);
            if (policy == null) return null;
            CmsUser cmsuser = userDao.findById(Integer.parseInt(policy.getCreaterId()));
            data.put("userName", cmsuser.getUsername());
            data.put("sIcloudPolicy", policy);//展示
            //云资源交易中心>>云计算政策>政策申请  模板  /member/cloudCenter_apply_add.html   URL /member/cloudCenter_apply_add.jspx
            memberDir = "member";
            memberNextUrl = "cloudCenter_apply_add";
            String name = policy.getPolicyName();
            data.put("name", name);
            data.put("policy", policy);
            data.put("id", policy.getPolicyId());
        } else if (channelId == 115) {
            //资源交易中心>>云需求  yzyjyzxYxq/cloudcenter_demand_detail.jspx
            dir = "content";
            nextUrl = "cloudcenter_demand_detail";
            SIcloudDemand sIcloudDemand = luceneContentDao.findIcloudDemandByContentId(contentId);
            if (sIcloudDemand == null) return null;
            CmsUser cmsuser = userDao.findById(Integer.parseInt(sIcloudDemand.getCreaterId()));
            data.put("sIcloudDemand", sIcloudDemand);//1  展示
            data.put("userName", cmsuser.getUsername());//2  展示
            //云资源交易中心>云需求>我要报价  /member/clouddemand_quote.html  /member/cloudCenter_Cdemand_quote.jspx
            memberDir = "member";
            memberNextUrl = "clouddemand_quote";
            data.put("demand", sIcloudDemand);//1 填写
            //data.put("user", cmsuser);//2 填写
        }
        /*云资源交易中心*/
        else if (channelId == 114) {
            // 云资源交易中心云资源查看   /yzyjyzxYzyck/cloudcenter_resource_manager_info.jspx
            nextUrl = "cloudcenter_resource_info";
            dir = "content";
            SIcloudManage resource = luceneContentDao.findResourceByContentId(contentId);
            if (resource == null) return null;
            CmsUser cmsuser = userDao.findById(Integer.parseInt(resource.getCreaterId()));
            data.put("sIcloudManage", resource);//1
            data.put("userName", cmsuser.getUsername());//2
            //云资源交易中心云资源查看 租赁 /member/cloudcenter_resource_rent.html /member/cloudcenter_managerrent_add.jspx
            memberDir = "member";
            memberNextUrl = "cloudcenter_resource_rent";
            SCompany company = resource.getCompany();
            CmsUser cmsUser = resource.getContent().getUser();
            data.put("cloudManger", resource);
            data.put("company", company);
            data.put("user", cmsUser);
        }
        /*公共服务*/
        else if (channelId == 117) {
            //公共服务>惠补政策列表>展示  /commservice/sAmplePolicyDetail.html  /commservice/sAmplePolicyDetail.jspx
            dir = "commservice";
            nextUrl = "sAmplePolicyDetail";
            SAmplePolicy sAmplePolicy = staticpageDao.findSAmplePolicyByContentId(contentId);
            if (sAmplePolicy == null) return null;
            data.put("obj", sAmplePolicy);

            //公共服务>惠补政策列表>填写  /commservice/SAmplePolicy_Apply.htmll  /member/SAmplePolicyApply.jspx
            memberDir = "commservice";
            memberNextUrl = "SAmplePolicy_Apply";
            data.put("sAmplePolicy", sAmplePolicy);//1 填写
        }
        /*供应链协同*/
        else if (channelId == 108) {
            // 供应链协同>>众修资源>>展示 /supplychain/Repair_manager_preview.html   /Repair_manager_preview.jspx
            nextUrl = "Repair_manager_preview";
            dir = "supplychain";
            SRepairRes sRepairRes = luceneContentDao.findSRepairResByContentId(contentId);
            if (sRepairRes == null) return null;
            Content content = sRepairRes.getContent();
            data.put("sRepairRes", sRepairRes);
            data.put("repairId", sRepairRes.getRepairId());
            //供应链协同>>众修资源>>填写 /supplychain/repairres_inquiryprice.html  /member/inquiryprice.jspx?
            data.put("repairres", sRepairRes);
            memberDir = "supplychain";
            memberNextUrl = "repairres_inquiryprice";
        } else if (channelId == 109) {
            //供应链协同>>众修需求>>展示 /supplychain/repairDemandDetail.html  /repairDemand.jspx?flag=1
            dir = "supplychain";
            nextUrl = "repairDemandDetail";
            SRepairDemand sRepairDemand = luceneContentDao.findSRepairDemandByContentId(contentId);
            if (sRepairDemand == null) return null;
            List<SRepairDemandObj> sRepairDemandObjList = repairDemandObjService.selcetByRepairDemandId(sRepairDemand.getRepairId());
            data.put("sRepairDemand", sRepairDemand);
            data.put("sRepairDemandObjList", sRepairDemandObjList);
            //供应链协同>>众修需求>>填写  /supplychain/quotePage.html  /member/showUser.jspx
            memberDir = "supplychain";
            memberNextUrl = "quotePage";
            List<SRepairDemand> slist = new ArrayList<SRepairDemand>();
            slist.add(sRepairDemand);
            data.put("testtt", slist);
            data.put("test", "1");
        } else if (channelId == 110) {
            //供应链协同>>金牌老师傅推荐>>展示  /supplychain/jplsf_nlxq.html  /ability.jspx
            dir = "supplychain";
            nextUrl = "jplsf_nlxq";
            SRepairRes sRepairRes = luceneContentDao.findSRepairResByContentId(contentId);
            if (sRepairRes == null) return null;
            List<SRepairRes> slist = new ArrayList<SRepairRes>();
            slist.add(sRepairRes);
            data.put("testtt", slist);
            //供应链协同>>金牌老师傅推荐>>填写  /supplychain/repairres_inquiryprice.html  /member/inquiryprice.jspx?
            data.put("repairres", sRepairRes);
            memberDir = "supplychain";
            memberNextUrl = "repairres_inquiryprice";
        }
        String tpl = site.getSolutionPath() + "/" + dir + "/" + nextUrl + Constants.TPL_SUFFIX;
        String tplM = site.getSolutionPath() + "/" + memberDir + "/" + memberNextUrl + Constants.TPL_SUFFIX;
        if (dir == null || "".equals(dir)) return list;
        m.put("tpl", tpl);
        m.put("type", "1");
        mm.put("tpl", tplM);
        mm.put("type", "2");
        //展示页面
        list.add(m);
        //填写页面
        //list.add(mm);
        return list;
    }

    /**
     * 只有展示页,获取栏目路径与返回页面数据，判断类型type表明展示页
     * nextUrl  模板
     * dir 模板上级文件夹
     */
    public List tplThree(Integer contentId, Integer channelId, CmsSite site, Map<String, Object> data) {
        List<HashMap> list = new ArrayList<HashMap>();
        HashMap m = new HashMap();
        String dir = null;
        String nextUrl = null;
        String type = staticpageDao.type(contentId);
        if (StringUtils.isBlank(type)) {
            return null;
        }
        /*公共服务*/
        if ("STenderTrailer".equals(type)) {
            // 公共服务>>招标预告 / commservice / sTenderTrailerDetail.jspx 116 / commservice / sTenderTrailerDetail.html
            dir = "commservice";
            nextUrl = "sTenderTrailerDetail";
            STenderTrailer sTenderTrailer = staticpageDao.findSTenderTrailerByContentId(contentId);
            if (sTenderTrailer == null) return null;
            data.put("obj", sTenderTrailer);
        } else if ("STenderNotice".equals(type)) {
            //  公共服务>>招标公告 / commservice / sTenderNoticeDetail.jspx 116 / commservice / sTenderNoticeDetail.html
            dir = "commservice";
            nextUrl = "sTenderNoticeDetail";
            STenderNotice sTenderNotice = staticpageDao.findSTenderNoticeByContentId(contentId);
            if (sTenderNotice == null) return null;
            data.put("obj", sTenderNotice);
        } else if ("SBidNotice".equals(type)) {
            // 公共服务>>中标公告 / commservice / sBidNoticeDetail.jspx 116 / commservice / sBidNoticeDetail.html
            dir = "commservice";
            nextUrl = "sBidNoticeDetail";
            SBidNotice sBidNotice = staticpageDao.findSBidNoticeByContentId(contentId);
            if (sBidNotice == null) return null;
            data.put("obj", sBidNotice);
        }
        if (dir == null || "".equals(dir)) return list;
        m.put("tpl", site.getSolutionPath() + "/" + dir + "/" + nextUrl + Constants.TPL_SUFFIX);
        m.put("type", "1");
        list.add(m);
        return list;
    }


    @Resource
    private LuceneContentDao luceneContentDao;
    @Resource
    private CmsUserDao userDao;
    @Resource
    private SpareDemandObjService spareDemandObjService;
    @Resource
    private RepairDemandObjService repairDemandObjService;
    @Resource
    private StaticpageDao staticpageDao;
    @Resource
    protected InteractAreaService interactAreaService;

}
