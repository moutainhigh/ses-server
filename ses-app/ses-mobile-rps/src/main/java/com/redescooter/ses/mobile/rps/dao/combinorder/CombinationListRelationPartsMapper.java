package com.redescooter.ses.mobile.rps.dao.combinorder;

import com.redescooter.ses.mobile.rps.dm.OpeCombinListRelationParts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组装单清单关联部件 Mapper接口
 * @author assert
 * @date 2021/1/27 15:17
 */
public interface CombinationListRelationPartsMapper {

    /**
     * 根据relationId、bomId查询组装单清单关联部件信息
     * @param relationId, bomId
     * @return com.redescooter.ses.mobile.rps.dm.OpeCombinListRelationParts
     * @author assert
     * @date 2021/1/27
    */
    OpeCombinListRelationParts getCombinationListRelationPartsByRIdAndBId(@Param("relationId") Long relationId,
                                                                          @Param("bomId") Long bomId);

    /**
     * 修改组装单清单关联部件信息
     * @param opeCombinListRelationParts
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int updateCombinListRelationParts(OpeCombinListRelationParts opeCombinListRelationParts);

    /**
     * 根据relationId查询部件扫码数量
     * @param relationId
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int getScanCodeQtyByRelationId(Long relationId);

    /**
     * 批量新增组装单清单关联部件信息
     * @param opeCombinListRelationPartsList
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int batchInsertCombinationListRelationParts(@Param("opeCombinListRelationPartsList")
                                                        List<OpeCombinListRelationParts> opeCombinListRelationPartsList);

}
