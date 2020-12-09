package com.redescooter.ses.service.hub.source.operation.dao;

import com.redescooter.ses.api.common.vo.scooter.ColorDTO;

import java.util.List;

/**
 * 颜色信息 Mapper接口
 * @author assert
 * @date 2020/12/9 11:07
 */
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
     * 查询颜色信息列表
     * @param
     * @return java.util.List<com.redescooter.ses.api.common.vo.scooter.ColorDTO>
     * @author assert
     * @date 2020/12/9
    */
    List<ColorDTO> getColorList();

}
