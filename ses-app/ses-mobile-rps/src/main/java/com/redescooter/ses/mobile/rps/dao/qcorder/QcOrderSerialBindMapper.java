package com.redescooter.ses.mobile.rps.dao.qcorder;

import com.redescooter.ses.mobile.rps.dm.OpeQcOrderSerialBind;
import org.apache.ibatis.annotations.Param;

/**
 * 质检单产品序列号绑定 Mapper接口
 * @author assert
 * @date 2021/1/25 15:17
 */
public interface QcOrderSerialBindMapper {

    /**
     * 根据serialNum查询部件本身序列号
     * @param serialNum
     * @return java.lang.String
     * @author assert
     * @date 2021/1/25
    */
    String getDefaultSerialNumBySerialNum(String serialNum);

    /**
     * 新增质检单产品序列号信息
     * @param opeQcOrderSerialBind
     * @return int
     * @author assert
     * @date 2021/1/26
    */
    int insertQcOrderSerialBind(OpeQcOrderSerialBind opeQcOrderSerialBind);

    /**
     * 根据serialNum查询质检单产品序列号信息
     * @param serialNum
     * @return com.redescooter.ses.mobile.rps.dm.OpeQcOrderSerialBind
     * @author assert
     * @date 2021/1/27
    */
    OpeQcOrderSerialBind getQcOrderSerialBindBySerialNum(String serialNum);

    /**
     * 检查是否存在序列号
     * @param serialNum
     * @param productId
     * @param relationOrderType
     * @return int
     * @author assert
     * @date 2021/1/29
    */
    int isExistsSerialNum(@Param("serialNum") String serialNum, @Param("productId") Long productId,
                          @Param("relationOrderType") Integer relationOrderType);

}
