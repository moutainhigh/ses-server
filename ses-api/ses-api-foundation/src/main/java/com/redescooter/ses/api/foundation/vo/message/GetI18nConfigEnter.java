package com.redescooter.ses.api.foundation.vo.message;


import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: GetI18nConfigEnter
 * @author: Darren
 * @create: 2019/04/30 10:54
 */
@Getter
@Setter
public class GetI18nConfigEnter extends GeneralEnter {

    /**
     * 可以为空
     */
    private String group;

    /**
     * 可以为空
     */
    private String key;

    /**
     * 可以为空
     */
    private String type;

}
