package com.redescooter.ses.api.scooter.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: QueryScooterActionTracesEnter
 * @author: Darren
 * @create: 2019/02/20 10:34
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryScooterActionTracesEnter extends PageEnter {
	@NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "Scooter id cannot be empty.")
	private int scooterId;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
	@NotNull(code = ValidationExceptionCode.BEGINTIME_IS_EMPTY, message = "beginTime cannot be empty.")
	private Date beginTime;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
	@NotNull(code = ValidationExceptionCode.ENDTIME_IS_EMPTY, message = "endTime cannot be empty.")
	private Date endTime;
}
