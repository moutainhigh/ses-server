package com.redescooter.ses.api.scooter.vo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: AddScooterEnter
 * @author: Alex
 * @create: 2019/03/13 18:15
 */
@Getter
@Setter
public class SaveScooterBo extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "scooter id is not empty.")
    private Long scooterId;

    @NotNull(code =ValidationExceptionCode.SCOOTER_NO_IS_EMPTY, message = "scooter no is not empty.")
    private String scooterNo;

    @NotNull(code = ValidationExceptionCode.SCOOTER_IS_MODEL_EMPTY, message = "scooter model is not empty.")
    private String model;

    @NotNull(code = ValidationExceptionCode.SCOOTER_LICENSE_IS_EMPTY, message = "scooter licensePlate is not empty.")
    private String licensePlate;

    @NotNull(code = ValidationExceptionCode.SCOOTER_LICENSE_TIME_IS_EMPTY , message = "scooter licensePlateTime is not empty.")
    private Date licensePlateTime;

    @NotNull(code = ValidationExceptionCode.SCOOTER_LICENSE_PICTURE_IS_EMPTY , message = "scooter License Plate Picture is not empty.")
    private String scooterLicensePlatePicture;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    @NotNull(code = ValidationExceptionCode.SCOOTER_INSURANCE_TIME_IS_EMPTY , message = "scooter insure time is not empty.")
    private Date insureTime;

    @NotNull(code = ValidationExceptionCode.SCOOTER_INSURANCE_IS_EMPTY , message = "scooter insurance is not empty.")
    private String insurance;
}
