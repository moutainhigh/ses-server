package com.redescooter.ses.web.ros.vo.restproduct.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @ClassName:SaveScooterEnter
 * @description: SaveScooterEnter
 * @author: Alex @Version：1.3
 * @create: 2020/02/25 10:33
 */
@ApiModel(value = "Bom车辆保存入参", description = "Bom车辆保存入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RosSaveProductionProductEnter extends GeneralEnter {

    @ApiModelProperty(value = "产品类型", required = true)
    private Integer productionProductType;

    @ApiModelProperty(value = "id", required = false)
    private Long id;

    @ApiModelProperty(value = "产品编号", required = true)
    @NotNull(code = ValidationExceptionCode.PRODUCT_NUM_IS_EMPTY, message = "产品编号为空")
    private String productN;

    @ApiModelProperty(value = "产品名字", required = true)
    private String enName;

    @ApiModelProperty(value = "产品名字", required = true)
    private String frName;

    @ApiModelProperty(value = "产品名字", required = true)
    private String cnName;

    @ApiModelProperty(value = "生产周期", required = true)
    private Integer procurementCycle;

    @ApiModelProperty(value = "Json配件列表，如[{\"id\":\"已选主键\",\"qty\":\"数量\"}]", required = true)
    private String partList;

    @ApiModelProperty(value = "分组Id", required = true)
    private Long groupId;

    @ApiModelProperty(value = "颜色Id", required = true)
    private Long colorId;

    @ApiModelProperty(value = "生效日期", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date effectiveDate;
}
