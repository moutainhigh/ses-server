package com.redescooter.ses.web.ros.vo.bom;

import java.util.List;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ScooterDetailResult
 * @description: ScooterDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 13:07
 */
@ApiModel(value = "整车详情出参", description = "整车详情出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterDetailResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "产品名字")
    private String productCnName;

    @ApiModelProperty(value = "生产周期")
    private String procurementCycle;

    @ApiModelProperty(value = "配件列表")
    private List<PartListEnter> partsList;
}
