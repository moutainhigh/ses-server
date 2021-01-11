package com.redescooter.ses.mobile.rps.dao.entrustorder;


import com.redescooter.ses.mobile.rps.dm.OpeEntrustPartsB;
import com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderProductDTO;

import java.util.List;

/**
 * 委托单部件相关 Mapper接口
 * @author assert
 * @date 2021/1/7 19:36
 */
public interface EntrustPartsBMapper {

    /**
     * 根据entrustId查询委托单部件信息
     * @param entrustId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderProductDTO>
     * @author assert
     * @date 2021/1/4
     */
    List<EntrustOrderProductDTO> getEntrustOrderPartsByEntrustId(Long entrustId);

    /**
     * 根据id查询委托单部件信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeEntrustPartsB
     * @author assert
     * @date 2021/1/8
    */
    OpeEntrustPartsB getEntrustPartsById(Long id);

    /**
     * 修改委托单部件信息
     * @param entrustPartsB
     * @return int
     * @author assert
     * @date 2021/1/8
    */
    int updateEntrustPartsB(OpeEntrustPartsB entrustPartsB);

    /**
     * 根据entrustId统计委托单部件实际发货数量
     * @param entrustId
     * @return int
     * @author assert
     * @date 2021-01-10
     */
    int countEntrustPartsConsignorQtyByEntrustId(Long entrustId);

}
