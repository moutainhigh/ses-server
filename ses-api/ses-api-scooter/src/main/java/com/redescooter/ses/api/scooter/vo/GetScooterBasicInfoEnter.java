package com.redescooter.ses.api.scooter.vo;


import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.*;

/**
 * @description: GetScooterBasicInfoEnter
 * @author: Darren
 * @create: 2019/01/22 15:30
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetScooterBasicInfoEnter extends GeneralEnter {

	@NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "scooterId is empty.")
	private Long scooterId;

}
