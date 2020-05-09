package com.redescooter.ses.api.common.vo.scooter;

import java.math.BigDecimal;
import java.util.Date;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:BaseScooterEnter
 * @description: BaseScooterEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/26 21:04
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class BaseScooterEnter extends GeneralEnter {
    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="车辆编号")
    private String scooterNo;

    @ApiModelProperty(value="状态:LOCKED;UNLOCKED")
    private String status;

    @ApiModelProperty(value="AVAILABLE;CHARGING;REPAIR;FAULT;USING")
    private String availableStatus;

    @ApiModelProperty(value="后备箱状态")
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
