package com.redescooter.ses.mobile.rps.dao.production;

import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;
import org.apache.ibatis.annotations.Param;

/**
 * 部件 Mapper接口
 * @author assert
 * @date 2021/1/13 11:25
 */
public interface ProductionPartsMapper {

    /**
     * 根据bomId查询部件信息
     * @param bomId
     * @return com.redescooter.ses.mobile.rps.dm.OpeProductionParts
     * @author assert
     * @date 2021/1/13
    */
    OpeProductionParts getProductionPartsByBomId(Long bomId);

    /**
     * 根据id查询部件中文名称
     * @param id
     * @return java.lang.String
     * @author assert
     * @date 2021/1/20
    */
    String getPartsCnNameById(Long id);

    /**
     * 根据id查询部件是否有序列号标识
     * @param id
     * @param partsNo
     * @return boolean
     * @author assert
     * @date 2021/2/2
    */
    Integer getPartsIdClassById(@Param("id") Long id, @Param("partsNo") String partsNo);

}
