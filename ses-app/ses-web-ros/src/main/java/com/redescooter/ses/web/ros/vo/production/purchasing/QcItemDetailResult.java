package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:QcItemDetailResult
 * @description: QcItemDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 10:57
 */
@ApiModel(value = "QcItem 详情", description = "QcItem 详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class QcItemDetailResult extends GeneralResult {

    @ApiModelProperty(value = "通过个数")
    private Integer passQty;

    @ApiModelProperty(value = "失败总数")
    private Integer failQty;

    @ApiModelProperty(value = "批次号")
    private String batchN;

    @ApiModelProperty(value = "质检日期")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date qcDate;

    @ApiModelProperty(value = "质检人Id")
    private Long inspectorId;

    @ApiModelProperty(value = "质检人名字")
    private String inspectorFirstName;

    @ApiModelProperty(value = "质检人名字")
    private String inspectorLastName;

    @ApiModelProperty(value = "质检人名字")
    private String inspectorFullName;

    @ApiModelProperty(value = "采购条目Id")
    private Long pruchasBId;
}
