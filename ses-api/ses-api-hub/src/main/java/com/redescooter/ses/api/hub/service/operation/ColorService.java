package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.scooter.ColorDTO;

import java.util.List;

/**
 * 颜色相关业务接口
 * @author assert
 * @date 2020/12/9 10:52
 */
public interface ColorService {

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
