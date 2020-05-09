package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveAssemblyEnter
 * @description: SaveAssemblyEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 13:07
 */
@ApiModel(value = "组装单保存", description = "组装单保存")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveAssemblyEnter extends GeneralEnter {

    @ApiModelProperty(value = "收货人Id", required = true)
    @NotNull(code = ValidationExceptionCode.CONSIGNEE_ID__IS_EMPTY, message = "收件人为空")
    private Long consigneeId;

    @ApiModelProperty(value = "工厂Id", required = true)
    @NotNull(code = ValidationExceptionCode.FACTORY_ID_EMPTY, message = "工厂为空")
    private Long factoryId;

    @ApiModelProperty(value = "组装产品 格式：id：123，qty：123", required = true)
    @NotNull(code = ValidationExceptionCode.PRODUCT_IS_EMPTY, message = "产品为空")
    private String productList;
}
