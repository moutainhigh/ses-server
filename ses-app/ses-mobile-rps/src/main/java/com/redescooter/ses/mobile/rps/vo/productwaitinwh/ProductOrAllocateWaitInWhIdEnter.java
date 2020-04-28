package com.redescooter.ses.mobile.rps.vo.productwaitinwh;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameProWaitInWHIdEnter
 * @Description
 * @Author kyle
 * @Date2020/4/26 15:54
 * @Version V1.0
 **/
@ApiModel(value = "整车部件入库项操作入参", description = "整车部件入库项操作入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductOrAllocateWaitInWhIdEnter extends PageEnter {

    @ApiModelProperty(value = "主单id")
    private Long id;

    @ApiModelProperty(value = "单据类型")
    private String sourceType;



}
