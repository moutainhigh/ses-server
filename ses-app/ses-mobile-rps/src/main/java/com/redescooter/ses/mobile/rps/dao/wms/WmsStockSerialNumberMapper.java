package com.redescooter.ses.mobile.rps.dao.wms;

import com.redescooter.ses.mobile.rps.dm.OpeWmsStockSerialNumber;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 库存产品序列号信息 Mapper接口
 * @author assert
 * @date 2021/1/25 11:40
 */
public interface WmsStockSerialNumberMapper {

    /**
     * 新增库存产品序列号信息
     * @param opeWmsStockSerialNumber
     * @return int
     * @author assert
     * @date 2021/1/25
    */
    int insertWmsStockSerialNumber(OpeWmsStockSerialNumber opeWmsStockSerialNumber);

    /**
     * 批量根据rsn修改库存产品序列号信息
     * @param opeWmsStockSerialNumber
     * @return int
     * @author assert
     * @date 2021/1/25
    */
    int updateWmsStockSerialNumberByRSn(OpeWmsStockSerialNumber opeWmsStockSerialNumber);

    /**
     * 批量根据serialNum删除库存产品序列号信息
     * @param serialNums
     * @return int
     * @author assert
     * @date 2021/1/25
    */
    int batchDeleteWmsStockSerialNumberBySerialNum(@Param("serialNums") List<String> serialNums);

    /**
     * 批量根据serialNum修改库存产品状态
     * @param serialNums
     * @param userId
     * @param currentTime
     * @return int
     * @author assert
     * @date 2021/1/25
    */
    int batchUpdateStockStatusByRsnList(@Param("serialNums") List<String> serialNums, @Param("userId") Long userId,
                                        @Param("currentTime") Date currentTime);

}
