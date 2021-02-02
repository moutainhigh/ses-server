package com.redescooter.ses.mobile.rps.dao.inwhorder;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 入库单产品序列号绑定信息 Mapper接口
 * @author assert
 * @date 2021/1/25 11:17
 */
public interface InWhouseOrderSerialBindMapper {

    /**
     * 新增入库单产品序列号绑定信息
     * @param opeInWhouseOrderSerialBind
     * @return int
     * @author assert
     * @date 2021/1/25
    */
    int insertInWhouseOrderSerialBind(OpeInWhouseOrderSerialBind opeInWhouseOrderSerialBind);

    /**
     * 根据serialNum查询入库单产品序列号信息
     * @param serialNum
     * @return com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind
     * @author assert
     * @date 2021/1/25
    */
    OpeInWhouseOrderSerialBind getInWhouseOrderSerialBindBySerialNum(String serialNum);

    /**
     * 批量根据orderBId查询入库单产品序列号信息
     * @param orderBIds
     * @return java.util.List<com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind>
     * @author assert
     * @date 2021/1/25
    */
    List<OpeInWhouseOrderSerialBind> batchGetInWhouseOrderSerialBindByOrderBIds(@Param("orderBIds") List<Long> orderBIds);

    /**
     * 根据orderBId查询入库单产品序列号信息
     * @param orderBId
     * @return java.util.List<com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind>
     * @author assert
     * @date 2021/1/29
    */
    List<OpeInWhouseOrderSerialBind> getInWhouseOrderSerialBindByOrderBId(Long orderBId);

}
