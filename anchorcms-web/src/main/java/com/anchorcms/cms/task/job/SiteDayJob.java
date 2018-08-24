package com.anchorcms.cms.task.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.service.CmsSiteMng;
import net.sf.ehcache.Ehcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;


/**
 * @Description 每日任务(执行每日pv、每日访客量清空)
 * @author tom
 */
public class SiteDayJob{
	private static final Logger log = LoggerFactory.getLogger(SiteDayJob.class);
	
	public void execute() {
		clearDayPvAndVisitor();
	}
	
	private void clearDayPvAndVisitor(){
		List<CmsSite>sites=cmsSiteMng.getList();
		Map<String,String>dayPv=new HashMap<String, String>();
		dayPv.put(CmsSite.DAY_PV_TOTAL, "0");
		Map<String,String>dayVisitor=new HashMap<String, String>();
		dayVisitor.put(CmsSite.DAY_VISITORS, "0");
		for(CmsSite site:sites){
			cmsSiteMng.updateAttr(site.getSiteId(), dayPv,dayVisitor);
		}
		dayPvTotalCache.removeAll();
		dayVisitorTotalCache.removeAll();
		log.info("Clear Day Pv And Visitor Job success!");
	}
	
@Resource
	private CmsSiteMng cmsSiteMng;
	@Resource
	@Qualifier("cmsDayPvTotalCache")
	private Ehcache dayPvTotalCache;
	@Resource
	@Qualifier("cmsDayVisitorTotalCache")
	private Ehcache dayVisitorTotalCache;
}
