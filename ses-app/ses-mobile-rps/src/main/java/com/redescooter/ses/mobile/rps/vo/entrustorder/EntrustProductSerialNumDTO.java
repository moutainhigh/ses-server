package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 委托单产品序列号信息 DTO
 * @author assert
 * @date 2021/1/8 10:46
 */
@Data
@ApiModel(value = "委托单产品序列号信息")
public class EntrustProductSerialNumDTO extends GeneralResult {

    @ApiModelProperty(value="主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value="关联的id(委托单产品id)", dataType = "Long")
    private Long relationId;

    @ApiModelProperty(value="关联的类型，1：车辆，2：组装件，3：部件", dataType = "Integer")
    private Integer relationType;

    @ApiModelProperty(value="批次号", dataType = "String")
    private String lot;

    @ApiModelProperty(value="序列号", dataType = "String")
    private String serialNum;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATE_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATE_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value="到货时间", dataType = "Date")
    private Date arrivalTime;

    @ApiModelProperty(value="备注", dataType = "String")
    private String remark;

}
