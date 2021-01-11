package com.redescooter.ses.mobile.rps.dao.outwhorder;

import com.redescooter.ses.mobile.rps.dm.OpeOutWhScooterB;
import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWhOrderProductDetailDTO;

import java.util.List;

/**
 * 出库单车辆产品相关 Mapper接口
 * @author assert
 * @date 2021/1/5 15:41
 */
public interface OutWhScooterBMapper {

    /**
     * 根据outWhId查询出库单车辆信息
     * @param outWhId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderProductDTO>
     * @author assert
     * @date 2021/1/5
     */
    List<OutWarehouseOrderProductDTO> getOutWhOrderScooterByOutWhId(Long outWhId);

    /**
     * 根据productId查询车辆产品详情
     * @param productId
     * @return com.redescooter.ses.mobile.rps.vo.outwhorder.OutWhOrderProductDetailDTO
     * @author assert
     * @date 2021/1/5
     */
    OutWhOrderProductDetailDTO getScooterProductDetailByProductId(Long productId);

    /**
     * 根据id查询车辆型号
     * @param id
     * @return java.lang.String
     * @author assert
     * @date 2021-01-10
     */
    String getScooterModelById(Long id);

    /**
     * 修改出库单车辆信息
     * @param opeOutWhScooterB
     * @return int
     * @author assert
     * @date 2021/1/11
    */
    int updateOutWhScooterB(OpeOutWhScooterB opeOutWhScooterB);

    /**
     * 根据outWhId统计出库单车辆质检数量
     * @param outWhId
     * @return int
     * @author assert
     * @date 2021/1/11
    */
    int countOutWhScooterQcQtyByOutWhId(Long outWhId);

}
