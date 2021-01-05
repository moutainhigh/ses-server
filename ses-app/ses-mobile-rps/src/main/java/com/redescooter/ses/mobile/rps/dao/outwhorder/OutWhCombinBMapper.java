package com.redescooter.ses.mobile.rps.dao.outwhorder;

import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWhOrderProductDetailDTO;

import java.util.List;

/**
 * 出库单组装件产品相关 Mapper接口
 * @author assert
 * @date 2021/1/5 15:42
 */
public interface OutWhCombinBMapper {

    /**
     * 根据outWhId查询出库单组装件信息
     * @param outWhId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderProductDTO>
     * @author assert
     * @date 2021/1/5
     */
    List<OutWarehouseOrderProductDTO> getOutWhOrderCombinByOutWhId(Long outWhId);

    /**
     * 根据productId查询组装件产品详情
     * @param productId
     * @return com.redescooter.ses.mobile.rps.vo.outwhorder.OutWhOrderProductDetailDTO
     * @author assert
     * @date 2021/1/5
     */
    OutWhOrderProductDetailDTO getCombinProductDetailByProductId(Long productId);

}
