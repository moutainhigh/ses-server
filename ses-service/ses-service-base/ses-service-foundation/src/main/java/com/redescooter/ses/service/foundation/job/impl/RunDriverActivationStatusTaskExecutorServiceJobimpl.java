package com.redescooter.ses.service.foundation.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.MaggessConstant;
import com.redescooter.ses.api.common.enums.user.UserStatusEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.foundation.job.RunDriverActivationStatusTaskExecutorServiceJob;
import com.redescooter.ses.api.hub.service.corporate.CorporateDriverService;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.service.base.PlaUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 9:52 上午
 * @ClassName: RunDriverActivationStatusTaskExecutorServiceJob
 * @Function: TODO
 */
@Slf4j
@DubboService
@Component
public class RunDriverActivationStatusTaskExecutorServiceJobimpl implements RunDriverActivationStatusTaskExecutorServiceJob {

    @Autowired
    private PlaUserService userService;
    @Autowired
    private PlaUserMapper userMapper;
    @DubboReference
    private CorporateDriverService corporateDriverService;

    /**
     * @param enter
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobResult DriverActivationStatusTask(GeneralEnter enter) {

        QueryWrapper<PlaUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(PlaUser.COL_DR, 0);
        queryWrapper.eq(PlaUser.COL_DEF1, MaggessConstant.ACCOUNT_ACTIVAT_BEFORE);
        queryWrapper.eq(PlaUser.COL_STATUS, UserStatusEnum.NORMAL.getValue());
        List<PlaUser> list = userService.list(queryWrapper);

        if (list.size() > 0) {
            List<IdEnter> idEnterList = new ArrayList<>();
            list.forEach(user -> {
                IdEnter idEnter = new IdEnter();
                idEnter.setUserId(user.getId());
                idEnter.setTenantId(user.getTenantId());
                idEnterList.add(idEnter);
                user.setDef1(MaggessConstant.ACCOUNT_ACTIVAT_AFTER);
                user.setUpdatedBy(new Long("0"));
                user.setUpdatedTime(new Date());
            });
            corporateDriverService.updateDriverDef1(idEnterList);

            userService.updateBatch(list);
        }
        return JobResult.success();
    }
}
