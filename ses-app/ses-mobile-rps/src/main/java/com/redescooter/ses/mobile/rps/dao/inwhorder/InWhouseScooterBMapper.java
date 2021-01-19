package com.redescooter.ses.mobile.rps.dao.inwhorder;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseScooterB;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDetailDTO;

import java.util.List;

/**
 * 入库单车辆信息 Mapper接口
 * @author assert
 * @date 2021/1/13 15:39
 */
public interface InWhouseScooterBMapper {

    /**
     * 根据id查询入库单车辆信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeInWhouseScooterB
     * @author assert
     * @date 2021/1/15
    */
    OpeInWhouseScooterB getInWhouseScooterById(Long id);

    /**
     * 修改入库单车辆信息
     * @param opeInWhouseScooterB
     * @return int
     * @author assert
     * @date 2021/1/15
    */
    int updateInWhouseScooter(OpeInWhouseScooterB opeInWhouseScooterB);

    /**
     * 根据inWhId查询入库单车辆信息
     * @param inWhId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDTO>
     * @author assert
     * @date 2021/1/18
    */
    List<InWhOrderProductDTO> getInWhOrderScooterByInWhId(Long inWhId);

    /**
     * 根据id查询入库单车辆信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDetailDTO
     * @author assert
     * @date 2021/1/19
    */
    InWhOrderProductDetailDTO getInWhOrderScooterById(Long id);

}
