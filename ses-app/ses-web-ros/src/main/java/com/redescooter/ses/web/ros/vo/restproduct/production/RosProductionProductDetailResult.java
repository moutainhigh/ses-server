package com.redescooter.ses.web.ros.vo.restproduct.production;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproduct.RosProductionProductPartListResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@ApiModel(value = "产品详情", description = "产品详情")
@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RosProductionProductDetailResult extends GeneralResult {

    @ApiModelProperty(value = "产品Id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "分组Id")
    private Long groupId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "颜色Id")
    private Long colorId;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    @ApiModelProperty(value = "生产周期")
    private Integer procurementCycle;

    @ApiModelProperty(value = "产品的名称")
    private String enName;

    @ApiModelProperty(value = "产品状态")
    private String status;

    @ApiModelProperty(value = "版本结构")
    private List<RosProductionProdductVersionResult> versionList;

    @ApiModelProperty(value = "生效时间")
    private Date effectiverDate;

    @ApiModelProperty(value = "创建时间")
    private Long createById;

    @ApiModelProperty(value = "创建人")
    private String createByFirstName;

    @ApiModelProperty(value = "创建人")
    private String createByLastName;

    @ApiModelProperty(value = "部件列表")
    private List<RosProductionProductPartListResult> rosProductPartListResult;
}
