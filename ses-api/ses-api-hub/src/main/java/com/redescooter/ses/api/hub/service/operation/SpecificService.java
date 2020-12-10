package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;

import java.util.List;

/**
 * 车辆规格相关业务接口
 * @author assert
 * @date 2020/12/10 16:29
 */
public interface SpecificService {

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
