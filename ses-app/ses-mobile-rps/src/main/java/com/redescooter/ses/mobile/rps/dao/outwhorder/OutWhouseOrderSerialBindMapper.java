package com.redescooter.ses.mobile.rps.dao.outwhorder;

import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrderSerialBind;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出库单产品序列号绑定信息 Mapper接口
 * @author assert
 * @date 2021/1/25 14:22
 */
public interface OutWhouseOrderSerialBindMapper {

    /**
     * 新增出库单产品序列号信息
     * @param opeOutWhouseOrderSerialBind
     * @return int
     * @author assert
     * @date 2021/1/25
    */
    int insertOutWhouseOrderSerialBind(OpeOutWhouseOrderSerialBind opeOutWhouseOrderSerialBind);

    /**
     * 批量根据orderBId查询序列号信息
     * @param orderBIds
     * @return java.util.List<java.lang.String>
     * @author assert
     * @date 2021/1/25
    */
    List<String> batchGetSerialNumByOrderBIds(@Param("orderBIds") List<Long> orderBIds);

    /**
     * 根据serialNum查询出库单产品序列号信息
     * @param serialNum
     * @return com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrderSerialBind
     * @author assert
     * @date 2021/1/25
    */
    OpeOutWhouseOrderSerialBind getOutWhouseOrderSerialBindBySerialNum(String serialNum);

}
