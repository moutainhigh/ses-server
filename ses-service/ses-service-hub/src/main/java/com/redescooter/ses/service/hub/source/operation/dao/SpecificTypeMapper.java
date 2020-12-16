package com.redescooter.ses.service.hub.source.operation.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.specification.QuerySpecificTypeDetailResultDTO;
import com.redescooter.ses.api.hub.vo.operation.SpecificTypeDTO;

import java.util.List;

/**
 * 规格类型相关 Mapper接口
 * @author assert
 * @date 2020/12/15 11:42
 */
@DS("operation")
public interface SpecificTypeMapper {

    /**
     * 查询车辆型号分组列表(下拉列表使用)
     * @param
     * @return java.util.List<com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO>
     * @author assert
     * @date 2020/12/15
     */
    List<SelectBaseResultDTO> getScooterModelList();

    /**
     * 根据id查询规格类型信息
     * @param id
     * @return com.redescooter.ses.api.common.vo.specification.QuerySpecificTypeDetailResultDTO
     * @author assert
     * @date 2020/12/15
     */
    SpecificTypeDTO getSpecificTypeById(Long id);

    /**
     * 根据name查询规格类型信息
     * @param name
     * @return com.redescooter.ses.api.hub.vo.operation.SpecificTypeDTO
     * @author assert
     * @date 2020/12/16
     */
    SpecificTypeDTO getSpecificTypeByName(String name);

}
