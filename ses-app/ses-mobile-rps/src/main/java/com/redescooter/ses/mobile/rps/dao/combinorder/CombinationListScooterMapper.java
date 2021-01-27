package com.redescooter.ses.mobile.rps.dao.combinorder;

import com.redescooter.ses.mobile.rps.dm.OpeCombinListScooterB;
import com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDTO;
import com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组装单车辆清单 Mapper接口
 * @author assert
 * @date 2021/1/27 14:17
 */
public interface CombinationListScooterMapper {

    /**
     * 根据combinId查询组装单车辆清单信息
     * @param combinId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDTO>
     * @author assert
     * @date 2021/1/27
    */
    List<CombinationListDTO> getCombinationListScooterByCombinId(Long combinId);

    /**
     * 根据id查询组装单车辆清单详情
     * @param id
     * @return com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDetailDTO
     * @author assert
     * @date 2021/1/27
    */
    CombinationListDetailDTO getCombinationListScooterDetailById(Long id);

    /**
     * 根据id查询组装单车辆清单信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeCombinListScooterB
     * @author assert
     * @date 2021/1/27
    */
    OpeCombinListScooterB getCombinationListScooterById(Long id);

    /**
     * 修改组装单车辆清单信息
     * @param opeCombinListScooterB
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int updateCombinationListScooter(OpeCombinListScooterB opeCombinListScooterB);

    /**
     * 获取车辆待组装数量
     * @param
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int getQuantityToBeAssembled();

    /**
     * 批量新增组装单车辆清单信息
     * @param opeCombinListScooterList
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int batchInsertCombinationListScooter(@Param("opeCombinListScooterList") List<OpeCombinListScooterB> opeCombinListScooterList);

}
