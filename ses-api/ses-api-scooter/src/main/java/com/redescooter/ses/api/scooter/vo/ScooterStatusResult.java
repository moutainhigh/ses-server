package com.redescooter.ses.api.scooter.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: ScooterStatusResult
 * @author: Alex
 * @create: 2019/04/19 14:00
 */
@Getter
@Setter
public class ScooterStatusResult extends GeneralEnter {

    /**
     * ID
     */
    private Long id;

    /**
     * 车辆编号
     */
    private String scooterNo;

    /**
     * 状态:LOCKED;UNLOCKED
     */
    private String status;

    /**
     * 总里程
     */
    private Long totalMileage;

    /**
     * AVAILABLE;CHARGING;REPAIR;FAULT;USING
     */
    private String availableStatus;

    /**
     * 后备箱状态
     */
    private String boxStatus;

    /**
     * 型号
     */
    private String model;

    /**
     * 牌照
     */
    private String licensePlate;

    /**
     * 牌照图片
     */
    private String licensePlatePicture;

    /**
     * 上牌时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date licensePlateTime;

    /**
     * SCOOTER_ID图片
     */
    private String scooterIdPicture;

    /**
     * 投保时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date insureTime;

    /**
     * 保险
     */
    private String insurance;

    /**
     * 乐观锁
     */
    private Integer revision;


}
