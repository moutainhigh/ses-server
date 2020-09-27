package com.redescooter.ses.web.ros.vo.restproduct.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RosProductionProductReleaseEnter extends GeneralEnter {

    @ApiModelProperty(value = "直接发布", required = true)
    private Boolean directRelease;

    @ApiModelProperty(value = "产品类型", required = true)
    private Integer productionProductType;

    @ApiModelProperty(value = "id", required = false)
    private Long id;

    @ApiModelProperty(value = "产品编号", required = true)
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
    private Date effectiverDate;
}
