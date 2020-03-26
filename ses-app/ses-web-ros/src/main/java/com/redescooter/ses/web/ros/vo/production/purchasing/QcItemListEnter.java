package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
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
 * @ClassName:QcListEnter
 * @description: QcListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 11:11
 */
@ApiModel(value = "QC 条目列表", description = "QC 条目列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class QcItemListEnter extends GeneralEnter {

    @ApiModelProperty(value = "id 采购单id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 不为空")
    private Long id;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "开始QC时间")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT, timezone = DateUtil.UTC)
    private Date qcStartTime;

    @ApiModelProperty(value = "结束QC 时间")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT, timezone = DateUtil.UTC)
    private Date qcEndTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
