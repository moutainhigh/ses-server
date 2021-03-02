package com.redescooter.ses.mobile.rps.service.home;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import java.util.Map;

/**
 * RPS首页业务接口
 * @author assert
 * @date 2021/2/3 9:42
 */
public interface RpsHomeService {

    /**
     * 获取所有单据数量
     * @param enter
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2021/2/3
    */
    Map<Integer, Integer> getAllOrderCount(GeneralEnter enter);

}
