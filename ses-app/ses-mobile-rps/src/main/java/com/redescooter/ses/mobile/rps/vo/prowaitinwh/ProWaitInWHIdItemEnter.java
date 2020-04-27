package com.redescooter.ses.mobile.rps.vo.prowaitinwh;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameProWaitInWHIdEnter
 * @Description
 * @Author kyle
 * @Date2020/4/18 10:27
 * @Version V1.0
 **/
@ApiModel(value = "整车部件入库项操作入参", description = "整车部件入库项操作入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProWaitInWHIdItemEnter extends PageEnter {

    @ApiModelProperty(value = "子单id")
    private Long id;

    @ApiModelProperty(value = "产品id")
    private Long partId;

    @ApiModelProperty(value = "仓库类型")
    private String type;//wHType定义失效

    @ApiModelProperty(value = "有无id")
    private Boolean idFlag;

    @ApiModelProperty(value = "本次应该入库数量")
    private Integer shouldInWhNum;

    @ApiModelProperty(value = "本次入库数量")
    private Integer inWhNum;


}
