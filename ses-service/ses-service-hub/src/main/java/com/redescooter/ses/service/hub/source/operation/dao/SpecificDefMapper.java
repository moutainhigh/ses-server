package com.redescooter.ses.service.hub.source.operation.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.common.vo.specification.SpecificDefDTO;

import java.util.List;

/**
 * 自定义项 Mapper接口
 * @author assert
 * @date 2020/12/15 18:01
 */
@DS("operation")
public interface SpecificDefMapper {

    /**
     * 根据specificId查询自定义项信息
     * @param specificId
     * @return java.util.List<com.redescooter.ses.api.common.vo.specification.SpecificDefDTO>
     * @author assert
     * @date 2020/12/15
     */
    List<SpecificDefDTO> getSpecificDefBySpecificId(Long specificId);

}
