package com.redescooter.ses.service.foundation.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.jiguang.JobResult;
import com.redescooter.ses.api.foundation.job.RunDriverActivationStatusTaskExecutorServiceJob;
import com.redescooter.ses.api.hub.service.corporate.CorporateDriverService;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.service.base.PlaUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 9:52 上午
 * @ClassName: RunDriverActivationStatusTaskExecutorServiceJob
 * @Function: TODO
 */
@Slf4j
@Service
public class RunDriverActivationStatusTaskExecutorServiceJobimpl implements RunDriverActivationStatusTaskExecutorServiceJob {

    @Autowired
    private PlaUserService userService;
    @Autowired
    private CorporateDriverService corporateDriverService;

    /**
     * @param enter
     * @return
     */
    @Override
    public JobResult DriverActivationStatusTask(GeneralEnter enter) {

        QueryWrapper<PlaUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(PlaUser.COL_DR, 0);
        queryWrapper.isNull(PlaUser.COL_LAST_LOGIN_TIME);
        queryWrapper.isNull(PlaUser.COL_LAST_LOGIN_TOKEN);
        List<PlaUser> list = userService.list(queryWrapper);

        if (list.size() > 0) {
            List<IdEnter> idEnterList = new ArrayList<>();
            list.forEach(user -> {
                GeneralEnter general = new GeneralEnter();
                general.setUserId(user.getId());
                general.setTenantId(user.getTenantId());
            });
            corporateDriverService.updateDriverDef1(idEnterList);
        }
        return JobResult.success();
    }
}
