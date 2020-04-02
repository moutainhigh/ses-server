package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:AssemblyListEnter
 * @description: AssemblyListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 13:15
 */
@ApiModel(value = "组装列表", description = "组装列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyListEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "类型")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "类型为空")
    private String type;

    @ApiModelProperty(value = "工厂Id")
    private Long factoryId;

    @ApiModelProperty(value = "付款方式")
    private String paymentType;

    @ApiModelProperty(value = "创建开始时间")
    private Date createdStartTime;

    @ApiModelProperty(value = "创建结束时间")
    private Date createdEndTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
