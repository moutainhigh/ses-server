package com.redescooter.ses.service.hub.source.operation.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;

import java.util.List;

/**
 * 规格分组相关 Mapper接口
 * @author assert
 * @date 2020/12/10 17:16
 */
@DS("operation")
public interface SpecificGroupMapper {

    /**
     * 查询车辆型号分组列表(下拉列表使用)
     * @param
     * @return java.util.List<com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO>
     * @author assert
     * @date 2020/12/10
     */
    List<SelectBaseResultDTO> getSpecificGroupList();

    /**
     * 根据id查询规格分组信息
     * @param id
     * @return com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO
     * @author assert
     * @date 2020/12/10
     */
    SpecificGroupDTO getSpecificGroupById(Long id);

}
