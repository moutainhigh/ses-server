package com.redescooter.ses.mobile.rps.dao.inwhorder;

import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;
import org.apache.ibatis.annotations.Param;

/**
 * 入库单部件 Mapper接口
 * @author assert
 * @date 2021/1/13 15:40
 */
public interface InWhousePartsBMapper {

    /**
     * 根据partsNo、inWhId查询入库单部件信息
     * @param partsNo
     * @param inWhId
     * @return com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB
     * @author assert
     * @date 2021/1/13
    */
    OpeInWhousePartsB getInWhousePartsByPartsNoAndInWhId(@Param("partsNo") String partsNo, @Param("inWhId") Long inWhId);

}
