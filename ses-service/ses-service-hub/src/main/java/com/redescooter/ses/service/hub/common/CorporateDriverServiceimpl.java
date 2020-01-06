package com.redescooter.ses.service.hub.common;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.service.corporate.CorporateDriverService;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDriver;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorDriverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 10:48 上午
 * @ClassName: CorporateDriverServiceimpl
 * @Function: TODO
 */
@Slf4j
@Service
public class CorporateDriverServiceimpl implements CorporateDriverService {

    @Autowired
    private CorDriverService driverService;

    /**
     * @param enter
     * @return
     */
    @Override
    public GeneralResult updateDriverDef1(List<IdEnter> enter) {

        if (enter.size() > 0) {
            enter.forEach(idEnter -> {
                CorDriver driver = new CorDriver();
                driver.setDef1(String.valueOf(Boolean.TRUE));
                driver.setUpdatedBy(new Long("0"));
                driver.setUpdatedTime(new Date());

                UpdateWrapper<CorDriver> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq(CorDriver.COL_TENANT_ID, idEnter.getTenantId());
                updateWrapper.eq(CorDriver.COL_USER_ID, idEnter.getUserId());
                updateWrapper.eq(CorDriver.COL_DR, 0);
                driverService.update(updateWrapper);
            });
        }

        return new GeneralResult();
    }
}
