package com.anchorcms.common.utils;

import com.anchorcms.common.model.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-10-31
 * @Desc URL帮助类
 */
public class URLHelper {

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:14
     * @Desc 获得页号
     */

    public static int getPageNo(HttpServletRequest request) {
        return getPageNo(getURI(request));
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:14
     * @Desc 获得路径信息
     */
    public static String[] getPaths(HttpServletRequest request) {
        return getPaths(getURI(request));
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:14
     * @Desc 获得路径参数
     */
    public static String[] getParams(HttpServletRequest request) {
        return getParams(getURI(request));
    }

    public static String getURI(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctx = helper.getOriginatingContextPath(request);
        if (!StringUtils.isBlank(ctx)) {
            uri= uri.substring(ctx.length());
        }
        //跨路径伪请求
        if(uri.contains("../")||uri.contains("..\\")){
            return "";
        }
        return uri;
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:15
     * @Desc 获得翻页信息
     */
    public static PageInfo getPageInfo(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String queryString = helper.getOriginatingQueryString(request);
        return getPageInfo(uri, queryString);
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:15
     * @Desc 获得页号
     */

    public static int getPageNo(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("URI不可为空！");
        }
        if (!uri.startsWith("/")) {
            throw new IllegalArgumentException("URI必须包含 '/'");
        }
        int pageNo = 1;
        int bi = uri.lastIndexOf("_");

        //此处更改注释：页码在"_"和第一个"-"之间比较符合逻辑，也不会污染参数
        //int mi = uri.lastIndexOf("-");
        int mi = uri.indexOf("-");

        int pi = uri.lastIndexOf(".");
        if (bi != -1) {
            String pageNoStr;
            if (mi != -1) {
                pageNoStr = uri.substring(bi + 1, mi);
            } else {
                if (pi != -1) {
                    pageNoStr = uri.substring(bi + 1, pi);
                } else {
                    pageNoStr = uri.substring(bi + 1);
                }
            }
            try {
                pageNo = Integer.valueOf(pageNoStr);
            } catch (Exception e) {
            }
        }
        return pageNo;
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:16
     * @Desc 获得路径数组
     */

    public static String[] getPaths(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("URI不可为空！");
        }
        if (!uri.startsWith("/")) {
            throw new IllegalArgumentException("URI必须包含 '/'");
        }
        int bi = uri.lastIndexOf("_");
        int mi = uri.indexOf("-");
        int pi = uri.lastIndexOf(".");
        // 获得路径信息
        String pathStr;
        if (bi != -1) {
            pathStr = uri.substring(0, bi);
        } else if (mi != -1) {
            pathStr = uri.substring(0, mi);
        } else if (pi != -1) {
            pathStr = uri.substring(0, pi);
        } else {
            pathStr = uri;
        }
        String[] paths = StringUtils.split(pathStr, '/');
        return paths;
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:16
     * @Desc 获得路径参数
     */
    public static String[] getParams(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("URI不可为空");
        }
        if (!uri.startsWith("/")) {
            throw new IllegalArgumentException("URI必须包含 '/'");
        }
        int mi = uri.indexOf("-");
        int pi = uri.lastIndexOf(".");
        String[] params;
        if (mi != -1) {
            String paramStr;
            if (pi != -1) {
                paramStr = uri.substring(mi, pi);
            } else {
                paramStr = uri.substring(mi);
            }
            params = new String[StringUtils.countMatches(paramStr, "-")];
            int fromIndex = 1;
            int nextIndex = 0;
            int i = 0;
            while ((nextIndex = paramStr.indexOf("-", fromIndex)) != -1) {
                params[i++] = paramStr.substring(fromIndex, nextIndex);
                fromIndex = nextIndex + 1;
            }
            params[i++] = paramStr.substring(fromIndex);
        } else {
            params = new String[0];
        }
        return params;
    }


    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:17
     * @Desc 获得URL信息
     */
    public static PageInfo getPageInfo(String uri, String queryString) {
        if (uri == null) {
            return null;
        }
        if (!uri.startsWith("/")) {
            throw new IllegalArgumentException("URI必须包含 '/'");
        }
        int bi = uri.lastIndexOf("_");

        //此处更改注释：
        //URL的结构：index_[页码]-[参数1]-...-[参数n].jhtml
        //拆分URL不留下页码，所以拆"_"前面的和【第一个】"-"后面的出来，
        //int mi = uri.lastIndexOf("-");
        int mi = uri.indexOf("-");

        int pi = uri.lastIndexOf(".");
        int lastSpt = uri.lastIndexOf("/") + 1;
        String url;
        if (!StringUtils.isBlank(queryString)) {
            url = uri + "?" + queryString;
        } else {
            url = uri;
        }
        // 翻页前半部
        String urlFormer;
        if (bi != -1) {
            urlFormer = uri.substring(lastSpt, bi);
        } else if (mi != -1) {
            urlFormer = uri.substring(lastSpt, mi);
        } else if (pi != -1) {
            urlFormer = uri.substring(lastSpt, pi);
        } else {
            urlFormer = uri.substring(lastSpt);
        }
        // 翻页后半部
        String urlLater;
        if (mi != -1) {
            urlLater = url.substring(mi);
        } else if (pi != -1) {
            urlLater = url.substring(pi);
        } else {
            urlLater = url.substring(uri.length());
        }
        String href = url.substring(lastSpt);
        return new PageInfo(href, urlFormer, urlLater);
    }

}
