package com.redescooter.ses.web.ros.vo.bom.parts;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassName HistoryPartsResult
 * @Author Jerry
 * @date 2020/02/26 23:09
 * @Description:
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class HistoryPartsDto extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "部品号")
    private String partsNumber;

    @ApiModelProperty(value = "时间")
    private Date createdTime;
}
