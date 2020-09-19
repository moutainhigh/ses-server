package com.redescooter.ses.web.ros.vo.wms.cn;

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
 * @ClassName:WhOutListEnter
 * @description: WhOutListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 10:42
 */
@ApiModel(value = "出库单列表入参", description = "出库单列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutOrderListEnter extends PageEnter {

    @ApiModelProperty(value = "类表类型",required = true)
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY,message = "类型为空")
    private String classType;

    @ApiModelProperty(value = "产品状态")
    private String status;

    @ApiModelProperty(value = "物流方式")
    private String consignType;

    @ApiModelProperty(value = "创建开始时间")
    private String createStartDateTime;

    @ApiModelProperty(value = "创建结束时间")
    private String createEndDateTime;

    @ApiModelProperty(value = "关键字搜索")
    private String keyword;
}
