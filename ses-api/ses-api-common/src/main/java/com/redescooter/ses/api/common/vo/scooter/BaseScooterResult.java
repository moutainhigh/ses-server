package com.redescooter.ses.api.common.vo.scooter;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:BaseScooterResult
 * @description: BaseScooterResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/26 20:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class BaseScooterResult extends GeneralResult {

    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="车辆编号")
    private String scooterNo;

    @ApiModelProperty(value = "状态: 1 LOCKED; 2 UNLOCKED")
    private String status;

    @ApiModelProperty(value = "车辆状态，1 不存在，2 已分配，3 使用中，4 归还 ，5 维修中")
    private String availableStatus;

    @ApiModelProperty(value = "后备箱状态 1 LOCKED; 2 UNLOCKED")
    private String boxStatus;

    @ApiModelProperty(value="型号")
    private String model;

    @ApiModelProperty(value="牌照")
    private String licensePlate;

    @ApiModelProperty(value="牌照图片")
    private String licensePlatePicture;

    @ApiModelProperty(value="上牌时间")
    private Date licensePlateTime;

    @ApiModelProperty(value="SCOOTER_ID图片")
    private String scooterIdPicture;

    @ApiModelProperty(value="投保时间")
    private Date insureTime;

    @ApiModelProperty(value="保险")
    private String insurance;

    @ApiModelProperty(value="乐观锁")
    private Integer revision;

    @ApiModelProperty(value="智能控制系统ID")
    private Long scooterEcuId;

    @ApiModelProperty(value="经度")
    private BigDecimal longitule;

    @ApiModelProperty(value="纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value="电池电量")
    private Integer battery;
}
