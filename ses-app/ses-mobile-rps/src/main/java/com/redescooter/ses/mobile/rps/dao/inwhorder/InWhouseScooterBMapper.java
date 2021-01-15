package com.redescooter.ses.mobile.rps.dao.inwhorder;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseScooterB;

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

}
