package com.redescooter.ses.mobile.rps.dao.outwhorder;

import com.redescooter.ses.mobile.rps.dm.OpeOutWhPartsB;
import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWhOrderProductDetailDTO;

import java.util.List;

/**
 * 出库单部件产品相关 Mapper接口
 * @author assert
 * @date 2021/1/5 15:42
 */
public interface OutWhPartsBMapper {

    /**
     * 根据outWhId查询出库单部件信息
     * @param outWhId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderProductDTO>
     * @author assert
     * @date 2021/1/5
     */
    List<OutWarehouseOrderProductDTO> getOutWhOrderPartsByOutWhId(Long outWhId);

    /**
     * 根据productId查询部件产品详情
     * @param productId
     * @return com.redescooter.ses.mobile.rps.vo.outwhorder.OutWhOrderProductDetailDTO
     * @author assert
     * @date 2021/1/5
     */
    OutWhOrderProductDetailDTO getPartsProductDetailByProductId(Long productId);
    
    /**
     * 修改出库单部件信息
     * @param opeOutWhPartsB
     * @return int
     * @author assert
     * @date 2021-01-10
     */
    int updateOutWhPartsB(OpeOutWhPartsB opeOutWhPartsB);

    /**
     * 根据id查询部件名称
     * @param id
     * @return java.lang.String
     * @author assert
     * @date 2021-01-10
     */
    String getPartsNameById(Long id);

    /**
     * 根据id查询出库单部件信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeOutWhPartsB
     * @author assert
     * @date 2021/1/20
    */
    OpeOutWhPartsB getOutWhOrderPartsById(Long id);

}
