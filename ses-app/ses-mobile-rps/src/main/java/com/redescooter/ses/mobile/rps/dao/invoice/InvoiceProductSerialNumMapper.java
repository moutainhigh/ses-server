package com.redescooter.ses.mobile.rps.dao.invoice;

import com.redescooter.ses.mobile.rps.vo.outwhorder.ProductSerialNumberDTO;

import java.util.List;

/**
 * 发货单产品序列号相关 Mapper接口
 * @author assert
 * @date 2021/1/5 19:00
 */
public interface InvoiceProductSerialNumMapper {

    /**
     * 根据productId查询车辆出库单序列号绑定信息
     * @param productId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.outwhorder.ProductSerialNumberDTO>
     * @author assert
     * @date 2021/1/5
     */
    List<ProductSerialNumberDTO> getOutWhOrderScooterByProductId(Long productId);

    /**
     * 根据productId查询部件出库单序列号绑定信息
     * @param productId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.outwhorder.ProductSerialNumberDTO>
     * @author assert
     * @date 2021/1/5
     */
    List<ProductSerialNumberDTO> getOutWhOrderPartsByProductId(Long productId);

}
