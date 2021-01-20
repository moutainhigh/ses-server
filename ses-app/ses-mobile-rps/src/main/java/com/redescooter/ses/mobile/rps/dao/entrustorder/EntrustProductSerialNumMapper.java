package com.redescooter.ses.mobile.rps.dao.entrustorder;

import com.redescooter.ses.mobile.rps.dm.OpeEntrustProductSerialNum;
import org.apache.ibatis.annotations.Param;

/**
 * 委托单产品序列号相关 Mapper接口
 * @author assert
 * @date 2021/1/20 12:01
 */
public interface EntrustProductSerialNumMapper {

    /**
     * 根据relationId、relationType查询委托单产品序列号信息
     * @param relationId, relationType
     * @return com.redescooter.ses.mobile.rps.dm.OpeInvoiceProductSerialNum
     * @author assert
     * @date 2021/1/20
    */
    OpeEntrustProductSerialNum getEntrustProductSerialNumByRelationIdAndType(@Param("relationId") Long relationId,
                                                                             @Param("relationType") Integer relationType);

    /**
     * 新增委托单产品序列号信息
     * @param opeEntrustProductSerialNum
     * @return int
     * @author assert
     * @date 2021/1/20
    */
    int insertEntrustProductSerialNum(OpeEntrustProductSerialNum opeEntrustProductSerialNum);

}
