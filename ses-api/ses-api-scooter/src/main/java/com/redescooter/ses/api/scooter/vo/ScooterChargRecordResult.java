package com.redescooter.ses.api.scooter.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: ScooterChargRecordResult
 * @author: Alex
 * @create: 2019/03/22 15:45
 */
@Getter
@Setter
public class ScooterChargRecordResult extends GeneralResult {
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date date;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date benginTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date endTime;

    private int before;

    private int after;
}
