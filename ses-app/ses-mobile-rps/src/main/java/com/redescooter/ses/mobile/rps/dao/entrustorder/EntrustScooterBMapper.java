package com.redescooter.ses.mobile.rps.dao.entrustorder;


import com.redescooter.ses.mobile.rps.dm.OpeEntrustScooterB;
import com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderProductDTO;

import java.util.List;

/**
 * 委托单车辆相关 Mapper接口
 * @author assert
 * @date 2021/1/7 19:36
 */
public interface EntrustScooterBMapper {

    /**
     * 根据entrustId查询委托单车辆信息
     * @param entrustId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderProductDTO>
     * @author assert
     * @date 2021/1/4
     */
    List<EntrustOrderProductDTO> getEntrustOrderScooterByEntrustId(Long entrustId);

    /**
     * 修改委托单车辆信息
     * @param opeEntrustScooterB
     * @return int
     * @author assert
     * @date 2021/1/8
    */
    int updateEntrustScooter(OpeEntrustScooterB opeEntrustScooterB);

    /**
     * 根据id查询委托单车辆信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeEntrustScooterB
     * @author assert
     * @date 2021/1/8
    */
    OpeEntrustScooterB getEntrustScooterById(Long id);

}
