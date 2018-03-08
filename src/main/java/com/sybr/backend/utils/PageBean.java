package com.sybr.backend.utils;

import java.util.List;

/**
 * 封装分页数据
 * @param <T>
 */
public class PageBean<T> {
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 总记录条数
     */
    private Integer totalCount;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    /**
     * 数据信息
     */
    private List<T> data;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {

            return true;
        }
        if (o == null || getClass() != o.getClass()) {

            return false;
        }

        PageBean<?> pageBean = (PageBean<?>) o;

        if (pageNum != null ? !pageNum.equals(pageBean.pageNum) : pageBean.pageNum != null) {

            return false;
        }
        if (totalPage != null ? !totalPage.equals(pageBean.totalPage) : pageBean.totalPage != null) {

            return false;
        }
        if (totalCount != null ? !totalCount.equals(pageBean.totalCount) : pageBean.totalCount != null) {

            return false;
        }
        if (pageSize != null ? !pageSize.equals(pageBean.pageSize) : pageBean.pageSize != null) {

            return false;
        }
        return data != null ? data.equals(pageBean.data) : pageBean.data == null;
    }

    @Override
    public int hashCode() {
        int result = pageNum != null ? pageNum.hashCode() : 0;
        result = 31 * result + (totalPage != null ? totalPage.hashCode() : 0);
        result = 31 * result + (totalCount != null ? totalCount.hashCode() : 0);
        result = 31 * result + (pageSize != null ? pageSize.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public static PageBean getNullPageBean(QueryListBean queryListBean) {
        PageBean pageBean = new PageBean();
        Integer pageNum = queryListBean.getPageNum();
        Integer pageSize = queryListBean.getPageSize();
        if (pageNum != null && pageSize != null) {
            pageBean.setPageSize(pageSize);
            pageBean.setPageNum(pageNum);
        }
        pageBean.setTotalPage(0);
        pageBean.setTotalCount(0);
        pageBean.setData(null);
        return pageBean;
    }
}
