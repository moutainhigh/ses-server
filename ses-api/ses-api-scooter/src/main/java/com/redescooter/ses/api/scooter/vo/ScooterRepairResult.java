package com.redescooter.ses.api.scooter.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: ScooterRepairResult
 * @author: Alex
 * @create: 2019/03/22 16:13
 */
@Getter
@Setter
public class ScooterRepairResult extends GeneralResult {

    private String type;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date beginTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date completeTime;

    private String contectPicture;
}
