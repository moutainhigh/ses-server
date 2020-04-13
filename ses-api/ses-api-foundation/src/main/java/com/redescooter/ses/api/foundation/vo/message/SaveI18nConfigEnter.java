package com.redescooter.ses.api.foundation.vo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * description: SaveI18nConfigEnter
 * author jerry.li
 * create: 2019-05-07 16:38
 */
@Data
public class SaveI18nConfigEnter extends GeneralEnter {

    // id为0/null  时表示插入 反之 更新
    private Long id;

    private String group;

    private String key;

    private String type;

    private String country;

    private String value;

    private String desc;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date updateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;

    private Boolean deleted;
}
