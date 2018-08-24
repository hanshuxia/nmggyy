package com.anchorcms.common.web;

import net.sf.ehcache.CacheManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.hibernate.cfg.Settings;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-10-31
 * @Desc 缓存处理类
 */
public class WebSingletonEhCacheRegionFactory extends SingletonEhCacheRegionFactory {

    private static final long serialVersionUID = 7656587512419690194L;
    protected final Log logger = LogFactory.getLog(getClass());
    private static final AtomicInteger REFERENCE_COUNT = new AtomicInteger();

    public WebSingletonEhCacheRegionFactory() {
    }

    public WebSingletonEhCacheRegionFactory(Properties prop) {
        super(prop);
    }

    @Override
    public void start(Settings settings, Properties properties) throws CacheException {
        this.settings = settings;
        try {
            //返回已经存在的单例CacheManager
            manager =	CacheManager.getInstance();
            mbeanRegistrationHelper.registerMBean( manager, properties );
        }
        catch (net.sf.ehcache.CacheException e) {
            throw new CacheException( e );
        }
    }

    @Override
    public void stop() {
        try {
            if ( manager != null ) {
                if ( REFERENCE_COUNT.decrementAndGet() == 0 ) {
                    manager.shutdown();
                }
                manager = null;
            }
        }
        catch (net.sf.ehcache.CacheException e) {
            throw new CacheException( e );
        }
    }
}
