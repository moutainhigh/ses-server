package com.redescooter.ses.api.foundation.vo.common;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: SysConfig
 * @author: Darren
 * @create: 2019/03/01 11:15
 */
@Data
public class SysConfigVO implements Serializable {


    private String group;

    private String key;

    private String name;

    private String value;

    private String desc;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date createTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date updateTime;

    private boolean deleted;
}
