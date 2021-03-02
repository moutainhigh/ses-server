package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.hub.vo.SysUserStaffDTO;

/**
 * 用户相关业务接口SysUserService
 * @author assert
 * @date 2020/12/10 10:43
 */
public interface SysUserService {

    /**
     * 根据userId查询用户员工信息(员工id和用户id是一致的)
     * @param userId
     * @return com.redescooter.ses.api.hub.vo.SysUserStaffDTO
     * @author assert
     * @date 2020/12/10
    */
    SysUserStaffDTO getSysUserStaffByUserId(Long userId);

}
