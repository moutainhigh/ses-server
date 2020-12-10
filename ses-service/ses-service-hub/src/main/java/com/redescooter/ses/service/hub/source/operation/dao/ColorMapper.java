package com.redescooter.ses.service.hub.source.operation.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.scooter.ColorDTO;

import java.util.List;

/**
 * 颜色信息 Mapper接口
 * @author assert
 * @date 2020/12/9 11:07
 */
@DS("operation")
public interface ColorMapper {

    /**
     * 根据id查询颜色信息
     * @param id
     * @return com.redescooter.ses.api.common.vo.scooter.ColorDTO
     * @author assert
     * @date 2020/12/9
    */
    ColorDTO getColorInfoById(Long id);

    /**
     * 查询颜色信息列表(下拉列表使用)
     * @param
     * @return java.util.List<com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO>
     * @author assert
     * @date 2020/12/10
     */
    List<SelectBaseResultDTO> getScooterColorList();

}
