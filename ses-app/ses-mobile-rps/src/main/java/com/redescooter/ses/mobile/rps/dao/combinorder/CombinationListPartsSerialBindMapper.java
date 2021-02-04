package com.redescooter.ses.mobile.rps.dao.combinorder;

import com.redescooter.ses.mobile.rps.dm.OpeCombinListPartsSerialBind;

/**
 * 组装单清单部件序列号 Mapper接口
 * @author assert
 * @date 2021/1/27 17:39
 */
public interface CombinationListPartsSerialBindMapper {

    /**
     * 根据serialNum查询组装单清单部件序列号信息
     * @param serialNum
     * @return com.redescooter.ses.mobile.rps.dm.OpeCombinListPartsSerialBind
     * @author assert
     * @date 2021/1/27
    */
    OpeCombinListPartsSerialBind getCombinListPartsSerialBySerialNum(String serialNum);

    /**
     * 新增组装单清单部件序列号信息
     * @param opeCombinListPartsSerialBind
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int insertCombinListPartsSerialBind(OpeCombinListPartsSerialBind opeCombinListPartsSerialBind);

    /**
     * 根据orderBId查询ECU仪表序列号信息
     * @param orderBId
     * @return com.redescooter.ses.mobile.rps.dm.OpeCombinListPartsSerialBind
     * @author assert
     * @date 2021/1/27
    */
    OpeCombinListPartsSerialBind getEcuPartsSerialBindByOrderBId(Long orderBId);

}
