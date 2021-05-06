package com.redescooter.ses.api.common.vo.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "分页公共参数", description = "分页公共参数")
public class OperatingEnter<T> extends PageEnter {
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
    @ApiModelProperty(value = "查询条件使用者")
    private String operatingUser;

    /**
     * 查询条件
     */
    @ApiModelProperty(value = "查询条件部门")
    private String operatingDepartment;


    /**
     * 查询条件
     */
    @ApiModelProperty(value = "查询邮箱")
    private String operatingEmail;


    /**
     * 查询条件
     */
    @ApiModelProperty(value = "查询状态")
    private Integer status;


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


}
