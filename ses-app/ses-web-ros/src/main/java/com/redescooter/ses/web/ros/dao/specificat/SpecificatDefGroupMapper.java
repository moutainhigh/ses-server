package com.redescooter.ses.web.ros.dao.specificat;

import com.redescooter.ses.web.ros.vo.specificat.dto.SpecificDefGroupDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 规格自定义项分组相关 Mapper接口
 * @author assert
 * @date 2020/12/7 18:56
 */
public interface SpecificatDefGroupMapper {

    /**
     * 新增规格自定义项分组
     * @param specificDefGroupList
     * @return int
     * @author assert
     * @date 2020/12/7
    */
    int batchInsertSpecificatDefGroup(@Param("specificDefGroupList") List<SpecificDefGroupDTO> specificDefGroupList);

}
