package com.anchorcms.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.io.*;
import java.util.*;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-10-31
 * @Desc properties文件工具类
 */
public class PropertyUtil implements BeanFactoryAware{
    private BeanFactory beanFactory;
    private Properties properties;
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 13:06
     * @Desc 获取properties文件列表
     */
    public List<String> getList(String prefix) {
        if (properties == null || prefix == null) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<String>();
        Enumeration<?> en = properties.propertyNames();
        String key;
        while (en.hasMoreElements()) {
            key = (String) en.nextElement();
            if (key.startsWith(prefix)) {
                list.add(properties.getProperty(key));
            }
        }
        return list;
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 13:07
     * @Desc 获取值
     */
    public Set<String> getSet(String prefix) {
        if (properties == null || prefix == null) {
            return Collections.emptySet();
        }
        Set<String>set=new TreeSet<String>();
        Enumeration<?> en = properties.propertyNames();
        String key;
        while (en.hasMoreElements()) {
            key = (String) en.nextElement();
            if (key.startsWith(prefix)) {
                set.add(properties.getProperty(key));
            }
        }
        return set;
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 13:07
     * @Desc 获取map
     */
    public Map<String, String> getMap(String prefix) {
        if (properties == null || prefix == null) {
            return Collections.emptyMap();
        }
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<?> en = properties.propertyNames();
        String key;
        int len = prefix.length();
        while (en.hasMoreElements()) {
            key = (String) en.nextElement();
            if (key.startsWith(prefix)) {
                map.put(key.substring(len), properties.getProperty(key));
            }
        }
        return map;
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 13:08
     * @Desc 获取properties对象
     */
    public Properties getProperties(String prefix) {
        Properties props = new Properties();
        if (properties == null || prefix == null) {
            return props;
        }
        Enumeration<?> en = properties.propertyNames();
        String key;
        int len = prefix.length();
        while (en.hasMoreElements()) {
            key = (String) en.nextElement();
            if (key.startsWith(prefix)) {
                props.put(key.substring(len), properties.getProperty(key));
            }
        }
        return props;
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 13:08
     * @Desc 获取properties内容
     */
    public String getPropertiesString(String prefix) {
        String property = "";
        if (properties == null || prefix == null) {
            return property;
        }
        Enumeration<?> en = properties.propertyNames();
        String key;
        while (en.hasMoreElements()) {
            key = (String) en.nextElement();
            if (key.equals(prefix)) {
                return properties.getProperty(key);
            }
        }
        return property;
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 13:09
     * @Desc 获取bean
     */
    public Map<String, Object> getBeanMap(String prefix) {
        Map<String, String> keyMap = getMap(prefix);
        if (keyMap.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>(keyMap.size());
        String key, value;
        for (Map.Entry<String, String> entry : keyMap.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            resultMap.put(key, beanFactory.getBean(value, Object.class));
        }
        return resultMap;
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 13:10
     * @Desc IO流读入配置文件
     */
    public static Properties getProperties(File file) {
        Properties props = new Properties();
        InputStream in;
        try {
            in = new FileInputStream(file);
            props.load(in);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return props;
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 13:11
     * @Desc 获取value值
     */
    public static String getPropertyValue(File file,String key) {
        Properties props=getProperties(file);
        return (String) props.get(key);
    }
}
