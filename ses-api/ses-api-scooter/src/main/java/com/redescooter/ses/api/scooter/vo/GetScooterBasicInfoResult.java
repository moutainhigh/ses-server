package com.redescooter.ses.api.scooter.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: GetScooterBasicInfoResult
 * @author: Darren
 * @create: 2019/01/22 15:30
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetScooterBasicInfoResult extends GeneralResult {

	private Long id;

	private String scooterNo;

	private Long scooterEcuId;

	private String scooterIdPicture;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
	private Date insureancetime;

	private String insureance;

	private String lockStatus;

	private String topBoxStatus;

	private BigDecimal longitule;

	private BigDecimal latitude;

	private String geohash;

	private Integer battery;

	private Integer revision;

	private String licensePlate;

	private String nextMaintenanceMileage;

	private String model;

	private String availableStatus;

	private Long totalMileage;

	private String licensePlatePicture;
}
