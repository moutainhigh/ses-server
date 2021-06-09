package com.redescooter.ses.web.ros.dao.delete;

import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 13:57
 */
public interface DeleteMapper {

    /**
     * 删除车辆bom
     */
    int deleteScooterBom(@Param("id") Long id);

    /**
     * 删除组装件bom
     */
    int deleteCombinBom(@Param("id") Long id);

    /**
     * 删除码库relation
     */
    int deleteCodebaseRelation(@Param("id") Long id);


}
