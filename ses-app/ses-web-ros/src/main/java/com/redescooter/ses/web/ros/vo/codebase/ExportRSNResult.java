package com.redescooter.ses.web.ros.vo.codebase;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/17 18:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExportRSNResult extends GeneralResult {

    @ApiModelProperty(value = "RSN")
    private String rsn;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "生成时间")
    private Date generateDate;

    @ApiModelProperty(value = "更新时间")
    private Date finishDate;
}
