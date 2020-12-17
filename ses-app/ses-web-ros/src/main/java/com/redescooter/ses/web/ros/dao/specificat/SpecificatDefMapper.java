package com.redescooter.ses.web.ros.dao.specificat;

import com.redescooter.ses.api.common.vo.specification.SpecificDefDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 规格自定义项相关 Mapper接口
 * @author assert
 * @date 2020/12/7 18:59
 */
public interface SpecificatDefMapper {

    /**
     * 批量新增规格自定义项
     * @param specificDefList
     * @return int
     * @author assert
     * @date 2020/12/7
    */
    int batchInsertSpecificatDef(@Param("specificDefList") List<SpecificDefDTO> specificDefList);

    /**
     * 批量修改自定义项信息
     * @param specificDefList
     * @return int
     * @author assert
     * @date 2020/12/17
    */
    int batchUpdateSpecificatDef(@Param("specificDefList") List<SpecificDefDTO> specificDefList);

}
