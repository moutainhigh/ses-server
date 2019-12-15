/**
 *
 */
package com.redescooter.ses.api.common.vo.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "分页公共参数", description = "分页公共参数")
public class PageEnter<T> extends GeneralEnter {
    @ApiModelProperty(value = "序列化ID", hidden = true)
    private static final long serialVersionUID = 1L;

    /**
     * 页号
     */
    @ApiModelProperty(value = "页码")
    private int pageNo = 1;

    /**
     * 页大小,默认10条记录
     */
    @ApiModelProperty(value = "页大小")
    private int pageSize = 10;

    /**
     * 查询条件
     */
    @ApiModelProperty(value = "查询条件")
    private T queryParam;


    @ApiModelProperty(value = "起始条数", hidden = true)
    private int startRow = 0;

    public int getStartRow() {
        int ret = (pageNo - 1) * pageSize;
        return ret < 0 ? 0 : ret;
    }

    /**
     * return the pageNo
     *
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * the pageNo to set
     *
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * return the pageSize
     *
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * the pageSize to set
     *
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * the startRow to set
     *
     * @param startRow the startRow to set
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * @return return query param
     */
    public T getQueryParam() {
        return queryParam;
    }

    /**
     * the queryParam to set
     *
     * @param queryParam the queryParam to set
     */
    public void setQueryParam(T queryParam) {
        this.queryParam = queryParam;
    }
}
