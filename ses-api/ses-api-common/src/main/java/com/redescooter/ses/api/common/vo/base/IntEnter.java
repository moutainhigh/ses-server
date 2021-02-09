package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:IdEnter
 * @description: IdEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 16:32
 */
@ApiModel(value = "Primary key parameters", description = "Primary key parameters")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class IntEnter extends GeneralEnter {

    @ApiModelProperty(value = "num", required = true)
    private Integer num;
}
