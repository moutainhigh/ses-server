package com.redescooter.ses.mobile.rps.dao.order;

import com.redescooter.ses.mobile.rps.dm.OpeOpTrace;

/**
 * 单据操作记录相关 Mapper接口
 * @author assert
 * @date 2021/1/4 19:01
 */
public interface OpTraceMapper {

    /**
     * 新增单据操作记录
     * @param opTrace
     * @return int
     * @author assert
     * @date 2021/1/5
    */
    int insertOpTrace(OpeOpTrace opTrace);

}
