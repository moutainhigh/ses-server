package com.redescooter.ses.mobile.rps.dao.combinorder;

import com.redescooter.ses.mobile.rps.dm.OpeCombinListCombinB;
import com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDTO;
import com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组装单组装件清单 Mapper接口
 * @author assert
 * @date 2021/1/27 14:17
 */
public interface CombinationListCombinMapper {

    /**
     * 根据combinId查询组装单组装件清单信息
     * @param combinId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDTO>
     * @author assert
     * @date 2021/1/27
    */
    List<CombinationListDTO> getCombinationListCombinByCombinId(Long combinId);

    /**
     * 根据id查询组装单组装件清单详情
     * @param id
     * @return com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDetailDTO
     * @author assert
     * @date 2021/1/27
    */
    CombinationListDetailDTO getCombinationListCombinDetailById(Long id);

    /**
     * 根据id查询组装单组装件清单信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeCombinListCombinB
     * @author assert
     * @date 2021/1/27
    */
    OpeCombinListCombinB getCombinationListCombinationById(Long id);

    /**
     * 修改组装单组装件清单信息
     * @param opeCombinListCombinB
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int updateCombinationListCombination(OpeCombinListCombinB opeCombinListCombinB);

    /**
     * 根据combinationId获取组装件待组装数量
     * @param combinationId
     * @return int
     * @author assert
     * @date 2021/1/27
     */
    int getQuantityToBeAssembledByCombinationId(Long combinationId);

    /**
     * 批量新增组装单组装件清单信息
     * @param opeCombinListCombinList
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int batchInsertCombinationListCombination(@Param("opeCombinListCombinList") List<OpeCombinListCombinB> opeCombinListCombinList);

}
