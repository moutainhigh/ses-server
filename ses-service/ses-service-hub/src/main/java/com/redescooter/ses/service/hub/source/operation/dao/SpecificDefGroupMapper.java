package com.redescooter.ses.service.hub.source.operation.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.common.vo.specification.SpecificDefGroupDTO;

import java.util.List;

/**
 * 自定义项分组 Mapper接口
 * @author assert
 * @date 2020/12/15 17:20
 */
@DS("operation")
public interface SpecificDefGroupMapper {

    /**
     * 根据specificId查询自定义项分组信息
     * @param specificId
     * @return java.util.List<com.redescooter.ses.api.common.vo.specification.SpecificDefGroupDTO>
     * @author assert
     * @date 2020/12/15
     */
    List<SpecificDefGroupDTO> getSpecificDefGroupBySpecificId(Long specificId);

}
