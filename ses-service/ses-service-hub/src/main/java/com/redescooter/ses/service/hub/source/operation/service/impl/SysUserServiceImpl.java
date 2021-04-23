package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.hub.service.operation.SysUserService;
import com.redescooter.ses.api.hub.vo.SysUserStaffDTO;
import com.redescooter.ses.service.hub.source.operation.dao.SysUserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2020/12/10 11:19
 */
@DubboService
@DS("operation")
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUserStaffDTO getSysUserStaffByUserId(Long userId) {
        return sysUserMapper.getSysUserStaffByUserId(userId);
    }

}
