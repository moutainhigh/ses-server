package com.redescooter.ses.admin.dev.service.scooter;

import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.InsertAdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.QueryAdminScooterParamDTO;
import com.redescooter.ses.api.common.vo.base.PageResult;

/**
 * 车辆管理业务层接口
 * @author assert
 * @date 2020/12/8 18:45
 */
public interface AdminScooterService {

    /**
     * 查询车辆列表
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO>
     * @author assert
     * @date 2020/12/9
    */
    PageResult<AdminScooterDTO> queryAdminScooter(QueryAdminScooterParamDTO paramDTO);

    /**
     * 创建车辆信息
     * @param adminScooterDTO
     * @return int
     * @author assert
     * @date 2020/12/9
    */
    int insertAdminScooter(InsertAdminScooterDTO adminScooterDTO);

    /**
     * 根据id查询车辆详情
     * @param id
     * @return com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO
     * @author assert
     * @date 2020/12/10
    */
    AdminScooterDTO getAdminScooterDetailById(Long id);

}
