package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.tool.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:QcInfoItemResult
 * @description: QcInfoItemResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 10:51
 */
@ApiModel(value = "QC质检条目", description = "QC质检条目")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class QcInfoResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部品号")
    private String partsN;

    @ApiModelProperty(value = "名字")
    private String enName;

    @ApiModelProperty(value = "名字")
    private String cnName;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "通过个数")
    private Integer passQty;

    @ApiModelProperty(value = "总数")
    private Integer totalQty;

    @ApiModelProperty(value = "批次号")
    private String batchN;

    @ApiModelProperty(value = "质检时间")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT, timezone = DateUtil.UTC)
    private Date qcDate;

    @ApiModelProperty(value = "分批质检详情")
    private List<QcItemDetailResult> qcItemDetailResultList;
}
