package com.redescooter.ses.service.hub.source.operation.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.hub.vo.SysUserStaffDTO;

/**
 * 用户相关 Mapper接口
 * @author assert
 * @date 2020/12/10 11:20
 */
@DS("operation")
public interface SysUserMapper {

    /**
     * 根据userId查询用户员工信息(员工id和用户id是一致的)
     * @param userId
     * @return com.redescooter.ses.api.hub.vo.SysUserStaffDTO
     * @author assert
     * @date 2020/12/10
     */
    SysUserStaffDTO getSysUserStaffByUserId(Long userId);

}
