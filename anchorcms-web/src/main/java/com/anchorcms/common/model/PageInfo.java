package com.anchorcms.common.model;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-10-31
 * @Desc 页面信息对象
 */
public class PageInfo {
    /**
     * 页面地址
     */
    private String href;
    /**
     * href前半部（相对于分页）
     */
    private String hrefFormer;
    /**
     * href后半部（相对于分页）
     */
    private String hrefLatter;

    public PageInfo(String href, String hrefFormer, String hrefLatter) {
        this.href = href;
        this.hrefFormer = hrefFormer;
        this.hrefLatter = hrefLatter;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getHrefFormer() {
        return hrefFormer;
    }

    public void setHrefFormer(String hrefFormer) {
        this.hrefFormer = hrefFormer;
    }

    public String getHrefLatter() {
        return hrefLatter;
    }

    public void setHrefLatter(String hrefLatter) {
        this.hrefLatter = hrefLatter;
    }

}
