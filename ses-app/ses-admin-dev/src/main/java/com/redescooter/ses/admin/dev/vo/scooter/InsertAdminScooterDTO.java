package com.redescooter.ses.admin.dev.vo.scooter;

import com.redescooter.ses.admin.dev.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增车辆信息 DTO
 * @author assert
 * @date 2020/12/8 19:55
 */
@Data
@ApiModel(value = "新增车辆信息对象")
public class InsertAdminScooterDTO extends GeneralEnter {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    /**
     * 序列号
     */
    @NotNull(code = ValidationExceptionCode.TABLET_SN_IS_NOT_EMPTY, message = "平板序列号不能为空")
    @ApiModelProperty(value = "平板序列号", dataType = "String", required = true)
    private String sn;

    /**
     * 分组id（车型）
     */
    @NotNull(code = ValidationExceptionCode.GROUP_IS_NOT_EMPTY, message = "分组不能为空")
    @ApiModelProperty(value="分组id(车型)", dataType = "Long", required = true)
    private Long groupId;

    /**
     * 颜色id
     */
    @NotNull(code = ValidationExceptionCode.COLOR_IS_NOT_EMPTY, message = "颜色不能为空")
    @ApiModelProperty(value="颜色id", dataType = "Long", required = true)
    private Long colorId;

    /**
     * mac名称
     */
    @NotEmpty(code = ValidationExceptionCode.MAC_NAME_IS_NOT_EMPTY, message = "mac名称不能为空")
    @ApiModelProperty(value = "mac名称", dataType = "String", required = true)
    private String macName;

    /**
     * mac地址
     */
    @NotEmpty(code = ValidationExceptionCode.MAC_ADDRESS_IS_NOT_EMPTY, message = "mac地址不能为空")
    @ApiModelProperty(value="mac地址", dataType = "String", required = true)
    private String macAddress;

    /**
     * 车辆的配置控制器 1-E25  2-E50  3-E100  4-E125
     */
    @ApiModelProperty(value="车辆的配置控制器(车辆型号) 1-E25  2-E50  3-E100  4-E125", dataType = "Integer", hidden = true)
    private Integer scooterController;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注", dataType = "String", hidden = true)
    private String remark;

    /**
     * 车辆配件集合
     */
    @NotEmpty(code = ValidationExceptionCode.SCOOTER_PARTS_IS_NOT_EMPTY, message = "车辆配件不能为空")
    @ApiModelProperty(value = "车辆配件集合(json数组对象字段: name、sn、qty)", required = true)
    private String scooterPartsList;

}
