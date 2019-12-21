package com.redescooter.ses.api.scooter.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: SaveScooterRepairRecordEnter
 * @author: Alex
 * @create: 2019/04/15 14:44
 */
@Getter
@Setter
public class SaveScooterRepairRecordEnter extends GeneralEnter {
    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "scooter id is empty.")
    private Long scooterId;

    @NotNull(code = ValidationExceptionCode.BOOKTIME_IS_EMPTY, message = "Booktime is not empty.")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date bookTime;

    @NotEmpty(code = ValidationExceptionCode.REPAIR_TYPE_IS_EMPTY, message = "repair type is not empty.")
    private String type;

}
