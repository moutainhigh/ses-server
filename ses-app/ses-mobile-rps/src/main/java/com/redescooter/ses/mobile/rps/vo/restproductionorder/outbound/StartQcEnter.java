package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:StartQcEnter
 * @description: StartQcEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 16:35 
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class StartQcEnter extends GeneralEnter {
    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "")
    private Integer productType;
}
