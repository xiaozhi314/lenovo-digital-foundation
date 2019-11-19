package com.lenovo.pojo;

import java.util.List;

public class RtPageInfo {
    private List Data;
    private Integer PageNum;
    private Integer PageSize;

    public List getData() {
        return Data;
    }

    public void setData(List data) {
        Data = data;
    }

    public Integer getPageNum() {
        return PageNum;
    }

    public void setPageNum(Integer pageNum) {
        PageNum = pageNum;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    public long getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(long totalCount) {
        TotalCount = totalCount;
    }

    private long TotalCount;

}