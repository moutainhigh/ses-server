package com.redescooter.ses.admin.dev.dao.scooter;

import com.redescooter.ses.admin.dev.dm.AdmScooter;
import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.QueryAdminScooterParamDTO;

import java.util.List;

/**
 * 车辆 Mapper接口
 * @author assert
 * @date 2020/12/9 10:23
 */
public interface AdminScooterMapper {

    /**
     * 根据平板序列号查询车辆信息
     * @param sn
     * @return com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO
     * @author assert
     * @date 2020/12/10
    */
    AdminScooterDTO getAdminScooterBySn(String sn);

    /**
     * 新增车辆信息
     * @param admScooter
     * @return int
     * @author assert
     * @date 2020/12/10
    */
    int insertAdminScooter(AdmScooter admScooter);

    /**
     * 获取车辆数量
     * @param paramDTO
     * @return int
     * @author assert
     * @date 2020/12/10
    */
    int countByAdminScooter(QueryAdminScooterParamDTO paramDTO);

    /**
     * 查询车辆列表
     * @param paramDTO
     * @return java.util.List<com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO>
     * @author assert
     * @date 2020/12/10
    */
    List<AdminScooterDTO> queryAdminScooter(QueryAdminScooterParamDTO paramDTO);

    /**
     * 根据id查询车辆详情
     * @param id
     * @return com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO
     * @author assert
     * @date 2020/12/10
    */
    AdminScooterDTO getAdminScooterDetailById(Long id);

    /**
     * 根据id查询车辆信息
     * @param id
     * @return com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO
     * @author assert
     * @date 2020/12/14
    */
    AdminScooterDTO getAdminScooterById(Long id);

}
