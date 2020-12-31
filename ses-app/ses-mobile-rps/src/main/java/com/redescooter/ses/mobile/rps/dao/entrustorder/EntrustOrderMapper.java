package com.redescooter.ses.mobile.rps.dao.entrustorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;

import java.util.List;

/**
 * 委托单相关 Mapper接口
 * @author assert
 * @date 2020/12/30 18:19
 */
public interface EntrustOrderMapper {

    /**
     * 委托单类型数量统计
     * @param
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2020/12/30
     */
    List<CountByStatusResult> getEntrustOrderTypeCount();

}
