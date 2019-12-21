package com.redescooter.ses.api.scooter.vo;


import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.*;

/**
 * @description: GetScooterStatusEnter
 * @author: Darren
 * @create: 2019/03/07 09:54
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetScooterStatusEnter extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "scooter id is not empty.")
    private Long scooterId;
}
