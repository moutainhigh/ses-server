package com.redescooter.ses.api.foundation.vo.message;


import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.Data;

/**
 * @description: GetI18nConfigResult
 * @author: Darren
 * @create: 2019/04/30 10:54
 */
@Data
public class GetI18nConfigResult extends GeneralResult {

    private String group;

    private String type;

    private String country;

    private String key;

    private String value;


}
