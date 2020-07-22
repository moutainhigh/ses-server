package com.redescooter.ses.web.ros.vo.wms.cn;


import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNamewmsEnter
 * @Description
 * @Author Joan
 * @Date2020/7/16 18:54
 * @Version V1.0
 **/
@ApiModel(value = "仓储可用入参", description = "仓储入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsStockEnter extends PageEnter {
@ApiModelProperty(value = "类型")
@NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "类型为空")
private String type;

@ApiModelProperty(value = "库存类型")
private String stockType;

@ApiModelProperty(value = "关键字,部件号、产品号、中文名、英文名")
private  String keyword;
}
