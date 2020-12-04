package com.redescooter.ses.api.common.vo.base.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分页统一入参 DTO
 * @author assert
 * @date 2020/12/4 11:59
 */
@ApiModel(value = "分页统一入参")
public class PageDTO implements Serializable {

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    private int pageNo = 1;

    /**
     * 每页大小,默认10条记录
     */
    @ApiModelProperty(value = "每页大小")
    private int pageSize = 10;

    /**
     * 起始条数(隐藏字段)
     */
    @ApiModelProperty(value = "起始条数", hidden = true)
    private int startRow = 0;

    public int getStartRow() {
        int ret = (pageNo - 1) * pageSize;
        return ret < 0 ? 0 : ret;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
