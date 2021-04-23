package com.redescooter.ses.service.foundation.service.impl.scooter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.foundation.service.scooter.AssignScooterService;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.service.base.PlaUserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author Chris
 * @Date 2021/3/26 12:01
 */
@DubboService
public class AssignScooterServiceImpl implements AssignScooterService {

    @Autowired
    private PlaUserService plaUserService;

    /**
     * 根据email判断是否在pla_user表存在
     */
    @Override
    public Boolean getPlaUserIsExistByEmail(String email) {
        LambdaQueryWrapper<PlaUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PlaUser::getDr, Constant.DR_FALSE);
        wrapper.eq(PlaUser::getLoginName, email);
        wrapper.orderByDesc(PlaUser::getCreatedTime);
        wrapper.last("limit 1");
        PlaUser plaUser = plaUserService.getOne(wrapper);
        if (null != plaUser) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
