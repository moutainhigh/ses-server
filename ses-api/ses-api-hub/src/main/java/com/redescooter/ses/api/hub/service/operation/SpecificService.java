package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefGroupDTO;
import com.redescooter.ses.api.hub.vo.operation.SpecificTypeDTO;

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
     * 根据specificId查询自定义项分组信息
     * @param specificId
     * @return java.util.List<com.redescooter.ses.api.common.vo.specification.SpecificDefGroupDTO>
     * @author assert
     * @date 2020/12/15
    */
    List<SpecificDefGroupDTO> getSpecificDefGroupBySpecificId(Long specificId);

    /**
     * 根据specificId查询自定义项信息
     * @param specificId
     * @return java.util.List<com.redescooter.ses.api.common.vo.specification.SpecificDefDTO>
     * @author assert
     * @date 2020/12/15
    */
    List<SpecificDefDTO> getSpecificDefBySpecificId(Long specificId);

}
