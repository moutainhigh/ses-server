package com.redescooter.ses.mobile.rps.dao.entrustorder;

import com.redescooter.ses.mobile.rps.dm.OpeEntrustProductSerialNum;

/**
 * 委托单产品序列号相关 Mapper接口
 * @author assert
 * @date 2021/1/20 12:01
 */
public interface EntrustProductSerialNumMapper {

    /**
     * 新增委托单产品序列号信息
     * @param opeEntrustProductSerialNum
     * @return int
     * @author assert
     * @date 2021/1/20
    */
    int insertEntrustProductSerialNum(OpeEntrustProductSerialNum opeEntrustProductSerialNum);

    /**
     * 根据serialNum查询委托单产品序列号信息
     * @param serialNum
     * @return com.redescooter.ses.mobile.rps.dm.OpeEntrustProductSerialNum
     * @author assert
     * @date 2021/1/26
    */
    OpeEntrustProductSerialNum getEntrustProductSerialNumBySerialNum(String serialNum);

}
