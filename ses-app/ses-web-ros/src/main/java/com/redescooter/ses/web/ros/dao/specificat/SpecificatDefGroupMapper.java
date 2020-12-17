package com.redescooter.ses.web.ros.dao.specificat;

import com.redescooter.ses.api.common.vo.specification.SpecificDefGroupDTO;
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

    /**
     * 批量修改规格自定义项分组信息
     * @param specificDefGroupList
     * @return int
     * @author assert
     * @date 2020/12/17
    */
    int batchUpdateSpecificatDefGroup(@Param("specificDefGroupList") List<SpecificDefGroupDTO> specificDefGroupList);

    /**
     * 根据specificId查询自定义项分组详情
     * @param specificId
     * @return java.util.List<com.redescooter.ses.api.common.vo.specification.SpecificDefGroupDTO>
     * @author assert
     * @date 2020/12/17
    */
    List<SpecificDefGroupDTO> getSpecificDefGroupBySpecificId(Long specificId);

}
