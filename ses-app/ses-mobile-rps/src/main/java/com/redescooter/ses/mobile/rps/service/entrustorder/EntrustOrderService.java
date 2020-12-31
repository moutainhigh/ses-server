package com.redescooter.ses.mobile.rps.service.entrustorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import java.util.Map;

/**
 * 委托单业务接口
 * @author assert
 * @date 2020/12/30 18:11
 */
public interface EntrustOrderService {

    /**
     * 委托单类型数量统计
     * @param enter
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2020/12/30
    */
    Map<Integer, Integer> getEntrustOrderTypeCount(GeneralEnter enter);

}
