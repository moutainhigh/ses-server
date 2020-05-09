package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:CheckScooterSerilaNResult
 * @description: CheckScooterSerilaNResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/09 19:23
 */
@ApiModel(value = "检查整车是否已经质检过", description = "检查整车是否已经质检过")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class CheckScooterSerilaNResult extends GeneralResult {

    @ApiModelProperty(value = "是否质检过")
    private Boolean whetherQc;
}
