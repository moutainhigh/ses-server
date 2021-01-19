package com.redescooter.ses.mobile.rps.dao.invoice;

import com.redescooter.ses.mobile.rps.dm.OpeInvoiceProductSerialNum;
import com.redescooter.ses.mobile.rps.vo.outwhorder.ProductSerialNumberDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 发货单产品序列号相关 Mapper接口
 * @author assert
 * @date 2021/1/5 19:00
 */
public interface InvoiceProductSerialNumMapper {

    /**
     * 根据productId查询车辆出库单序列号信息
     * @param productId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.outwhorder.ProductSerialNumberDTO>
     * @author assert
     * @date 2021/1/5
     */
    List<ProductSerialNumberDTO> getOutWhOrderScooterByProductId(Long productId);

    /**
     * 根据productId查询部件出库单序列号信息
     * @param productId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.outwhorder.ProductSerialNumberDTO>
     * @author assert
     * @date 2021/1/5
     */
    List<ProductSerialNumberDTO> getOutWhOrderPartsByProductId(Long productId);

    /**
     * 根据serialNum查询出库单序列号信息
     * @param serialNum
     * @return com.redescooter.ses.mobile.rps.vo.outwhorder.ProductSerialNumberDTO
     * @author assert
     * @date 2021-01-10
     */
    ProductSerialNumberDTO getInvoiceProductSerialNumBySerialNum(String serialNum);

    /**
     * 添加出库单产品序列号信息
     * @param opeInvoiceProductSerialNum
     * @return int
     * @author assert
     * @date 2021-01-10
     */
    int insertInvoiceProductSerialNum(OpeInvoiceProductSerialNum opeInvoiceProductSerialNum);

    /**
     * 根据relationId、relationType查询出库单序列号信息
     * @param relationId, relationType
     * @return com.redescooter.ses.mobile.rps.dm.OpeInvoiceProductSerialNum
     * @author assert
     * @date 2021/1/19
    */
    OpeInvoiceProductSerialNum getInvoiceProductSerialNumByRelationIdAndType(@Param("relationId") Long relationId,
                                                                             @Param("relationType") Integer relationType);

}
