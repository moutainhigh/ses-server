package com.redescooter.ses.mobile.rps.dao.qcorder;

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

}
