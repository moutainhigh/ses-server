package com.redescooter.ses.mobile.rps.dao.inwhorder;

import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据id查询入库单部件信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB
     * @author assert
     * @date 2021/1/15
    */
    OpeInWhousePartsB getInWhousePartsById(Long id);

    /**
     * 修改入库单部件信息
     * @param opeInWhousePartsB
     * @return int
     * @author assert
     * @date 2021/1/15
    */
    int updateInWhouseParts(OpeInWhousePartsB opeInWhousePartsB);

    /**
     * 根据inWhId查询入库单部件信息
     * @param inWhId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDTO>
     * @author assert
     * @date 2021/1/18
    */
    List<InWhOrderProductDTO> getInWhOrderPartsByInWhId(Long inWhId);

    /**
     * 根据id查询入库单部件信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDetailDTO
     * @author assert
     * @date 2021/1/19
    */
    InWhOrderProductDetailDTO getInWhOrderPartsById(Long id);

}
