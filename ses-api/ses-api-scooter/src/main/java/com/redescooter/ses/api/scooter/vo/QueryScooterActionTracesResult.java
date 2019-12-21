package com.redescooter.ses.api.scooter.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: QueryScooterActionTracesResult
 * @author: Darren
 * @create: 2019/02/20 10:34
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryScooterActionTracesResult extends GeneralResult {

	private Long id;

	private Integer tenantId;

	private Integer userId;

	private Integer scooterId;

	private BigDecimal userLongitule;

	private BigDecimal userLatitude;

	private String userLocationGeohash;

	private String actionType;

	private String actionResult;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
	private Date actionTime;

	private Integer battery;

	private BigDecimal longitule;

	private BigDecimal latitude;

	private String geohash;

	private Integer createdBy;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
	private Date createdTime;

	private Integer updatedBy;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
	private Date updatedTime;
}
