package com.redescooter.ses.mobile.rps.dao.inwhorder;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB;

/**
 * 入库单组装件 Mapper接口
 * @author assert
 * @date 2021/1/13 15:40
 */
public interface InWhouseCombinBMapper {

    /**
     * 根据id查询入库单组装件信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB
     * @author assert
     * @date 2021/1/15
    */
    OpeInWhouseCombinB getInWhouseCombinById(Long id);

    /**
     * 修改入库单组装件信息
     * @param opeInWhouseCombinB
     * @return int
     * @author assert
     * @date 2021/1/15
    */
    int updateInWhouseCombin(OpeInWhouseCombinB opeInWhouseCombinB);

}
