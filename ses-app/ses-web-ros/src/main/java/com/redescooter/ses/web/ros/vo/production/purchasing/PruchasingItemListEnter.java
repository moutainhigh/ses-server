package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PruchasingItemListEnter
 * @description: PruchasingItemListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 10:27
 */
@ApiModel(value = "采购商品条目列表", description = "采购商品条目列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PruchasingItemListEnter extends PageEnter {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 为空")
    private Long id;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "供应商")
    private Long supplierId;

    @ApiModelProperty(value = "关键字")
    private String keyword;


}
