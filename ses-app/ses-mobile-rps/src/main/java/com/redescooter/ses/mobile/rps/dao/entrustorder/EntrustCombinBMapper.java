package com.redescooter.ses.mobile.rps.dao.entrustorder;


import com.redescooter.ses.mobile.rps.dm.OpeEntrustCombinB;
import com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderProductDTO;

import java.util.List;

/**
 * 委托单组装件相关 Mapper接口
 * @author assert
 * @date 2021/1/7 19:36
 */
public interface EntrustCombinBMapper {

    /**
     * 根据entrustId查询委托单组装件信息
     * @param entrustId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderProductDTO>
     * @author assert
     * @date 2021/1/4
     */
    List<EntrustOrderProductDTO> getEntrustOrderCombinByEntrustId(Long entrustId);

    /**
     * 修改委托单组装件信息
     * @param opeEntrustCombinB
     * @return int
     * @author assert
     * @date 2021/1/8
    */
    int updateEntrustCombin(OpeEntrustCombinB opeEntrustCombinB);

    /**
     * 根据id查询委托单组装件信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeEntrustCombinB
     * @author assert
     * @date 2021/1/8
    */
    OpeEntrustCombinB getEntrustCombinById(Long id);

    /**
     * 根据entrustId统计委托单组装件实际发货数量
     * @param entrustId
     * @return int
     * @author assert
     * @date 2021-01-10
     */
    int countEntrustCombinConsignorQtyByEntrustId(Long entrustId);

}
