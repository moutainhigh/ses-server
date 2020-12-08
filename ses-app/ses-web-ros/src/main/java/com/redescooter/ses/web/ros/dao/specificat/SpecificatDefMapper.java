package com.redescooter.ses.web.ros.dao.specificat;

import com.redescooter.ses.web.ros.vo.specificat.dto.SpecificDefDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 规格自定义项相关 Mapper接口
 * @author assert
 * @date 2020/12/7 18:59
 */
public interface SpecificatDefMapper {

    /**
     * 新增规格自定义项
     * @param specificDefList
     * @return int
     * @author assert
     * @date 2020/12/7
    */
    int batchInsertSpecificatDef(@Param("specificDefList") List<SpecificDefDTO> specificDefList);

}
